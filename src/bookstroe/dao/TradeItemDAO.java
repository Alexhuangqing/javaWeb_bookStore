package bookstroe.dao;

import java.util.Collection;
import java.util.Set;

import bookstroe.domain.TradeItem;

public interface TradeItemDAO {
	/**
	 * 批量保存用户一次交易的所有交易项
	 * @param items
	 */
	public abstract void batchSave(Collection<TradeItem> items);
	/**
	 * 通过一次交易记录id，得到该次交易的所有交易项
	 * @param tradeId
	 * @return
	 */
	public abstract Set<TradeItem> getTradeItemsWithTeadeId(Integer tradeId);
}
