package bookstore.service;

import bookstroe.dao.AccountDAO;
import bookstroe.dao.impl.AccountDAOImpl;

public class AccountService {
	AccountDAO accountDao = new AccountDAOImpl();

	public float getBalance(String accountId) {
		
		return accountDao.get(Integer.parseInt(accountId.trim())).getBalance();
	}

}
