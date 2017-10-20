package bookstore.test;

import org.junit.Test;

import bookstroe.dao.AccountDAO;
import bookstroe.dao.impl.AccountDAOImpl;
import bookstroe.domain.Account;

public class AccountDAOImplTest {
	AccountDAO accountDao = new AccountDAOImpl();
	@Test
	public void testGet() {
		Account account = accountDao.get(1);
		System.out.println(account.getBalance());
	}

	@Test
	public void testUpdateBalance() {
		accountDao.updateBalance(1,740);
	}

}
