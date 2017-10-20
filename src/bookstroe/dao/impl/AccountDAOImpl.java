package bookstroe.dao.impl;

import bookstroe.dao.AccountDAO;
import bookstroe.domain.Account;

public class AccountDAOImpl extends BaseDao<Account> implements AccountDAO {

	@Override
	public Account get(Integer accountId) {
		String sql = "select accountId,balance from account where accountId = ?";
		return query(sql, accountId);
	}

	@Override
	public void updateBalance(Integer accountId, float amount) {
		String sql = "update account set balance = balance - ? where accountId = ?";
		update(sql, amount,accountId);
	}

}
