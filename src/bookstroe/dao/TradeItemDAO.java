package bookstroe.dao;

import java.util.Collection;
import java.util.Set;

import bookstroe.domain.TradeItem;

public interface TradeItemDAO {
	/**
	 * ���������û�һ�ν��׵����н�����
	 * @param items
	 */
	public abstract void batchSave(Collection<TradeItem> items);
	/**
	 * ͨ��һ�ν��׼�¼id���õ��ôν��׵����н�����
	 * @param tradeId
	 * @return
	 */
	public abstract Set<TradeItem> getTradeItemsWithTeadeId(Integer tradeId);
}
