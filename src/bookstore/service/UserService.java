package bookstore.service;

import java.util.Set;

import bookstroe.dao.BookDAO;
import bookstroe.dao.TradeDAO;
import bookstroe.dao.TradeItemDAO;
import bookstroe.dao.UserDAO;
import bookstroe.dao.impl.BookDaoImpl;
import bookstroe.dao.impl.TradeDaoImpl;
import bookstroe.dao.impl.TradeItemDaoImpl;
import bookstroe.dao.impl.UserDaoImpl;
import bookstroe.domain.Trade;
import bookstroe.domain.TradeItem;
import bookstroe.domain.User;

public class UserService {
	private UserDAO userDao = new UserDaoImpl();
	private TradeDAO tradeDao = new TradeDaoImpl();
	private TradeItemDAO tradeItemDao = new TradeItemDaoImpl();
	private BookDAO bookDao = new BookDaoImpl();
	public User getUserByUserName(String username) {
		return userDao.getUser(username);

	}

	public User getUserWithTrades(String username) {
		User user = userDao.getUser(username);
		//查询到没有该用户
		if(user == null){
			return null;
		}
		
		
		Set<Trade> trades = tradeDao.getTradesWithUserId(user.getUserId());
		// 将trades集合遍历，把每一次trade的交易项集合数据赋给trade
		for (Trade trade : trades) {
			// 从数据表中查得一次交易有多条交易项
			Set<TradeItem> items = tradeItemDao.getTradeItemsWithTeadeId(trade.getTradeId());
			if(items != null ){
				for(TradeItem item:items){
					item.setBook(bookDao.getBook(item.getBookId()));
				}
			}
			// 将这些交易项数据赋给本次交易的实例
			trade.setItems(items);
		}
		// 循环完成后，trades装配就绪，在赋给user的集合属性
		user.setTrades(trades);
		return user;
	}

}
