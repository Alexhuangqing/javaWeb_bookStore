package bookstore.db;
//导入sql包，别导错了
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 
 * 实现数据库链接 关闭必要资源
 *
 */
public class JDBCUtils {

	private static DataSource dataSource = null;
	static {
		// 初始化数据库链接池
		dataSource = new ComboPooledDataSource("bookstore_mysql");
	}

	/**
	 * 从c3p0数据库链接池中获取链接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	/**
	 * 关闭数据库链接
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
	 * 关闭rs，ps
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
