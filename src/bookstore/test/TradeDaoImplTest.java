package bookstore.test;

import static org.junit.Assert.fail;

import java.sql.Date;
import java.util.Set;

import org.junit.Test;

import bookstroe.dao.TradeDAO;
import bookstroe.dao.impl.TradeDaoImpl;
import bookstroe.domain.Trade;

public class TradeDaoImplTest {
	
	TradeDAO tradeDao = new TradeDaoImpl();
	@Test
	public void testInsertTrade() {
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(1);
		tradeDao.insert(trade);
	}

	@Test
	public void testGetTradesWithUserId() {
		Set<Trade> trades = tradeDao.getTradesWithUserId(1);
		System.out.println(trades);
	}

}
