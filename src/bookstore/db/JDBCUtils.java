package bookstore.db;
//����sql�����𵼴���
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * ʵ�����ݿ����� �رձ�Ҫ��Դ
 *
 */
public class JDBCUtils {

	private static DataSource dataSource = null;
	static {
		// ��ʼ�����ݿ����ӳ�
		dataSource = new ComboPooledDataSource("bookstore_mysql");
	}

	/**
	 * ��c3p0���ݿ����ӳ��л�ȡ����
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	/**
	 * �ر����ݿ�����
	 * 
	 * @param connection
	 */
	public static void release(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * �ر�rs��ps
	 * 
	 * @param rs
	 * @param ps
	 */
	public static void release(ResultSet rs, PreparedStatement ps) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
