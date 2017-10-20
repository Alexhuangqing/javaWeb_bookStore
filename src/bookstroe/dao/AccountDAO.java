package bookstroe.dao;

import bookstroe.domain.Account;

public interface AccountDAO {
	/**
	 * ͨ�����ÿ�id����һ���˺�
	 * @param accountId
	 * @return
	 */
	public abstract Account get(Integer accountId) ;
	/**
	 * �����˻����
	 * @param accountId
	 * @param amount
	 */
	public abstract void updateBalance(Integer accountId,float amount);
	
}
