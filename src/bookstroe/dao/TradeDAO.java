package bookstroe.dao;

import java.util.Set;

import bookstroe.domain.Trade;

public interface TradeDAO {
	/**
	 * ��¼������
	 * @param trade
	 */
	public abstract void insert(Trade trade);
	/**
	 * ͨ���û��� ���ظ��û������н���
	 * @param userId
	 * @return
	 */
	public abstract Set<Trade> getTradesWithUserId(Integer userId);
}
