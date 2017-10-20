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
		//��ѯ��û�и��û�
		if(user == null){
			return null;
		}
		
		
		Set<Trade> trades = tradeDao.getTradesWithUserId(user.getUserId());
		// ��trades���ϱ�������ÿһ��trade�Ľ���������ݸ���trade
		for (Trade trade : trades) {
			// �����ݱ��в��һ�ν����ж���������
			Set<TradeItem> items = tradeItemDao.getTradeItemsWithTeadeId(trade.getTradeId());
			if(items != null ){
				for(TradeItem item:items){
					item.setBook(bookDao.getBook(item.getBookId()));
				}
			}
			// ����Щ���������ݸ������ν��׵�ʵ��
			trade.setItems(items);
		}
		// ѭ����ɺ�tradesװ��������ڸ���user�ļ�������
		user.setTrades(trades);
		return user;
	}

}
