package bookstore.test;

import org.junit.Test;

import bookstroe.dao.UserDAO;
import bookstroe.dao.impl.UserDaoImpl;
import bookstroe.domain.User;

public class UserDaoImplTest {
	UserDAO userDao = new UserDaoImpl();
	@Test
	public void testGetUser() {
		User user = userDao.getUser("Tom");
		System.out.println(user);
	}

}
