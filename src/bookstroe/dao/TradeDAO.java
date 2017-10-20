package bookstroe.dao;

import java.util.Set;

import bookstroe.domain.Trade;

public interface TradeDAO {
	/**
	 * 记录意向交易
	 * @param trade
	 */
	public abstract void insert(Trade trade);
	/**
	 * 通过用户名 返回该用户的所有交易
	 * @param userId
	 * @return
	 */
	public abstract Set<Trade> getTradesWithUserId(Integer userId);
}
