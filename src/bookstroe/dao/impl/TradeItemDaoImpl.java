package bookstroe.dao.impl;

import java.util.ArrayList;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bookstroe.dao.TradeItemDAO;
import bookstroe.domain.TradeItem;

public class TradeItemDaoImpl extends BaseDao<TradeItem> implements TradeItemDAO {

	@Override
	public void batchSave(Collection<TradeItem> items) {
		String sql = "insert into tradeItem (bookid,quantity,tradeid) values(?,?,?) ";
		Object[][] params = new Object[items.size()][3];
		//将声明的接口 转为list 使用里面的get（key）方法得到 集合的值
		List<TradeItem> ti = new ArrayList<TradeItem>(items);
		for(int i = 0; i < items.size(); i++){
			params[i][0] = ti.get(i).getBookId();
			params[i][1] = ti.get(i).getQuantity();
			params[i][2] = ti.get(i).getTradeId();
		}
		batch(sql, params);
	}

	@Override
	public Set<TradeItem> getTradeItemsWithTeadeId(Integer tradeId) {
		String sql = "select itemId tradeItemId,bookId,quantity,tradeId "
				+ "from tradeItem where tradeId = ? ";
		List<TradeItem> list = queryForList(sql, tradeId);
		return new HashSet<TradeItem>(list);
	}

}
