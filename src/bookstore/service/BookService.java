package bookstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import bookstroe.dao.AccountDAO;
import bookstroe.dao.BookDAO;
import bookstroe.dao.TradeDAO;
import bookstroe.dao.TradeItemDAO;
import bookstroe.dao.UserDAO;
import bookstroe.dao.impl.AccountDAOImpl;
import bookstroe.dao.impl.BookDaoImpl;
import bookstroe.dao.impl.TradeDaoImpl;
import bookstroe.dao.impl.TradeItemDaoImpl;
import bookstroe.dao.impl.UserDaoImpl;
import bookstroe.domain.Book;
import bookstroe.domain.ShoppingCart;
import bookstroe.domain.ShoppingCartItem;
import bookstroe.domain.Trade;
import bookstroe.domain.TradeItem;
import bookstroe.web.BookStoreWebUtils;
import bookstroe.web.CriteriaBook;
import bookstroe.web.Page;

/**
 * 
 * 进行相关业务的实现
 *
 */
public class BookService {
	private BookDAO bookDao = new BookDaoImpl();

	public Page<Book> getPage(CriteriaBook cb) {
		return bookDao.getPage(cb);
	}

	public Book getBook(int id) {

		return bookDao.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart sc) {
		Book book = bookDao.getBook(id);
		if (book != null) {
			sc.add(book);
			return true;
		}
		return false;
	}

	public void removeItemFromCart(int id, ShoppingCart sc) {
		sc.remove(id);

	}

	public void clearShoppingCart(ShoppingCart sc) {
		sc.clear();

	}

	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.getBooks().get(id).setQuantity(quantity);

	}

	public int getBookNumber(ShoppingCart sc) {
		return sc.getBookNumber();
	}

	public float getTotalMoney(ShoppingCart sc) {

		return sc.getTotalMoney();
	}

	private AccountDAO accountDao = new AccountDAOImpl();
	private TradeDAO tradeDao = new TradeDaoImpl();
	private UserDAO userDao = new UserDaoImpl();
	private TradeItemDAO tradeItemDao = new TradeItemDaoImpl();

	//业务方法
	public void cash(ShoppingCart shoppingCart, String username, String accountId) {
		// 1.批量修改库存与销售数量
		bookDao.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
		// 2.更新账户余额
		accountDao.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		// 3.插入一条trade记录（该交易记录同时也完成了实体类对象（tradeId字段）的更新）
		Trade trade = new Trade(new Date(new java.util.Date().getTime()), userDao.getUser(username).getAccountId());
		tradeDao.insert(trade);
		// 4.批量保存该tradeId的购物项（tradeItem）记录
		Collection<TradeItem> items = new ArrayList<TradeItem>();
		// 结账购物车里面的每个购物项 就对应着 一次交易的交易项
		for (ShoppingCartItem sci : shoppingCart.getItems()) {
			TradeItem tradeItem = new TradeItem(sci.getBook().getId(), sci.getQuantity(), trade.getTradeId());
			items.add(tradeItem);
		}
		tradeItemDao.batchSave(items);
		// 5.清空购物车
		clearShoppingCart(shoppingCart);
	}

}
