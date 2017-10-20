package bookstroe.dao;

import bookstroe.domain.User;

public interface UserDAO {
	/**
	 * 通过用户名返回用户信息
	 * @param username
	 * @return
	 */
	public abstract User getUser(String username);
	
}
