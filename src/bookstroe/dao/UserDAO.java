package bookstroe.dao;

import bookstroe.domain.User;

public interface UserDAO {
	/**
	 * ͨ���û��������û���Ϣ
	 * @param username
	 * @return
	 */
	public abstract User getUser(String username);
	
}
