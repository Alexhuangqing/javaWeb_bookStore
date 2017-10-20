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
 * �������ҵ���ʵ��
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

	//ҵ�񷽷�
	public void cash(ShoppingCart shoppingCart, String username, String accountId) {
		// 1.�����޸Ŀ������������
		bookDao.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
		// 2.�����˻����
		accountDao.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		// 3.����һ��trade��¼���ý��׼�¼ͬʱҲ�����ʵ�������tradeId�ֶΣ��ĸ��£�
		Trade trade = new Trade(new Date(new java.util.Date().getTime()), userDao.getUser(username).getAccountId());
		tradeDao.insert(trade);
		// 4.���������tradeId�Ĺ����tradeItem����¼
		Collection<TradeItem> items = new ArrayList<TradeItem>();
		// ���˹��ﳵ�����ÿ�������� �Ͷ�Ӧ�� һ�ν��׵Ľ�����
		for (ShoppingCartItem sci : shoppingCart.getItems()) {
			TradeItem tradeItem = new TradeItem(sci.getBook().getId(), sci.getQuantity(), trade.getTradeId());
			items.add(tradeItem);
		}
		tradeItemDao.batchSave(items);
		// 5.��չ��ﳵ
		clearShoppingCart(shoppingCart);
	}

}
