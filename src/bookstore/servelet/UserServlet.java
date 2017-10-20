package bookstore.servelet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bookstore.service.UserService;
import bookstroe.domain.User;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserService();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		User user = userService.getUserWithTrades(username);
		
		request.setAttribute("user",user);
		request.getRequestDispatcher("/WEB-INF/pages/trades.jsp").forward(request,response);
		
	}

}
