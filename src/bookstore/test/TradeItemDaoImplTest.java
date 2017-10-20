package bookstore.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import bookstroe.dao.TradeItemDAO;
import bookstroe.dao.impl.TradeItemDaoImpl;
import bookstroe.domain.TradeItem;

public class TradeItemDaoImplTest {
	TradeItemDAO  tradeItemDao = new TradeItemDaoImpl();
	@Test
	public void testBatchSave() {
		Collection<TradeItem> items = new ArrayList<TradeItem>();
		items.add(new TradeItem(1,10,12));
		items.add(new TradeItem(1,10,12));
		items.add(new TradeItem(1,10,12));
		tradeItemDao.batchSave(items);
	}

	@Test
	public void testGetTradeItemsWithTeadeId() {
		Set<TradeItem> items = tradeItemDao.getTradeItemsWithTeadeId(12);
		System.out.println(items);
	}

}
