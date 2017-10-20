package bookstroe.dao;

import bookstroe.domain.Account;

public interface AccountDAO {
	/**
	 * 通过信用卡id返回一个账号
	 * @param accountId
	 * @return
	 */
	public abstract Account get(Integer accountId) ;
	/**
	 * 更新账户余额
	 * @param accountId
	 * @param amount
	 */
	public abstract void updateBalance(Integer accountId,float amount);
	
}
