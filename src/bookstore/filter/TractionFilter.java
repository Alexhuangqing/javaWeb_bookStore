package bookstore.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.db.JDBCUtils;
import bookstroe.web.ConnectionContext;

/**
 * Servlet Filter implementation class TractionFilter
 */
public class TractionFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Connection connection = null;
		try {
			// 1.获取链接
			connection = JDBCUtils.getConnection();
			// 2.开启事物
			connection.setAutoCommit(false);

			// 3.利用ThreadLocal把链接和当前线程绑定
			ConnectionContext.getInstance().bind(connection);
			// 4.事务处理，把请求转给servlet,利用该请求线程的connection进行数据链接
			chain.doFilter(request, response);
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 6.处理事务出现异常 ，回滚事物
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 并给出错误页面提示
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect(req.getContextPath() + "/error-1.jsp");

		} finally {
			// 7.解除绑定
			ConnectionContext.getInstance().remove();
			// 8.关闭连接
			JDBCUtils.release(connection);
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
