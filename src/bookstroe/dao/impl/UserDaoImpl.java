package bookstroe.dao.impl;

import bookstroe.dao.UserDAO;
import bookstroe.domain.User;

public class UserDaoImpl extends BaseDao<User> implements UserDAO {

	@Override
	public User getUser(String username) {
		String sql = "select userId,username,accountId from userinfo where username = ?";
		return query(sql, username);
	}
	

}
