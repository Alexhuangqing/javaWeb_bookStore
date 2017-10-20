package bookstroe.dao.impl;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import bookstroe.dao.TradeDAO;
import bookstroe.domain.Trade;

public class TradeDaoImpl extends BaseDao<Trade> implements TradeDAO {
	/**
	 * 向trade中插入交易记录，并且返回主键（tradeId）值，
	 * 给表对应的实体类对象的id赋值
	 */
	@Override
	public void insert(Trade trade) {
		String sql = " insert into trade(userId,tradeTime) values(?,?)";
		long tradeId = insert(sql, trade.getUserId(), trade.getTradeTime());
		trade.setTradeId((int)tradeId);
	}

	@Override
	public Set<Trade> getTradesWithUserId(Integer userId) {
		String sql = "select tradeId,userId,tradeTime from trade where userId = ? "
				+ "order by tradeTime desc";
		List<Trade> list = queryForList(sql, userId);
		return new LinkedHashSet<Trade>(list);
	}

}
