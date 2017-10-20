package bookstore.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import bookstore.db.JDBCUtils;

public class JDBCUtilsTest {

	@Test
	public void testGetConnection() throws SQLException {
		Connection connection = JDBCUtils.getConnection();
		System.out.println(connection);
	}

}
