package bookstore.servelet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import bookstore.service.AccountService;
import bookstore.service.BookService;
import bookstore.service.UserService;
import bookstroe.domain.Book;
import bookstroe.domain.ShoppingCart;
import bookstroe.domain.ShoppingCartItem;
import bookstroe.domain.User;
import bookstroe.web.BookStoreWebUtils;
import bookstroe.web.CriteriaBook;
import bookstroe.web.Page;

/**
 * Servlet implementation class BookServlet
 */
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");

		// 后面需要验证errors 所以 这里给个空串初始值,如果errors值发生改变 说明没有通过验证；
		StringBuffer errors = new StringBuffer("");
		errors = volidateFormField(username, accountId);
		
		// 将StringBuffer转为String进行验证
		if (errors.toString().trim().equals("")) {
			errors = volidateUser(username, accountId);
			if (errors.toString().trim().equals("")) {
				errors = volidateStoreNumber(request);
				if (errors.toString().trim().equals("")) {
					errors = volidateBalance(request, accountId);
				}
			}
		}
		//如果没有通过验证，则返回提示信息,方法结束
		if (!errors.toString().trim().equals("")) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("WEB-INF/pages/cash.jsp").forward(request, response);
			//一定不要忘了  
			return;
		}
		//通过验证后的逻辑操作.
		bookService.cash(BookStoreWebUtils.getShoppingCart(request),username,accountId);
		
		
		
		response.sendRedirect(request.getContextPath()+"/success.jsp");
	}

	/**
	 * 验证用户余额
	 * 
	 * @param request
	 * @param accountId
	 * @return
	 */
	private AccountService accountService = new AccountService();

	public StringBuffer volidateBalance(HttpServletRequest request, String accountId) {
		StringBuffer errors = new StringBuffer("");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		if (sc.getTotalMoney() > accountService.getBalance(accountId)) {
			errors.append("账户余额不足");
		}
		return errors;
	}

	/**
	 * 验证库存是否满足要求
	 * 
	 * @param request
	 * @return
	 */
	private BookService bookService = new BookService();

	public StringBuffer volidateStoreNumber(HttpServletRequest request) {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		StringBuffer errors = new StringBuffer("");
		for (ShoppingCartItem sci : sc.getItems()) {
			int quantity = sci.getQuantity();
			int quantityVal = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			if (quantity > quantityVal) {
				errors.append("《" + sci.getBook().getTitle() + "》" + "库存不足<br>");
			}
		}
		return errors;
	}

	/**
	 * 验证用户名和账号是否匹配
	 * 
	 * @param username
	 * @param accountId
	 * @return
	 */
	private UserService userService = new UserService();

	public StringBuffer volidateUser(String username, String accountId) {
		StringBuffer errors = new StringBuffer("");
		User user = userService.getUserByUserName(username);
		boolean flag = false;
		if (user != null) {
			if (accountId.equals(user.getAccountId() + "")) {
				flag = true;
			}
		}
		if (!flag) {
			errors.append("用户名与密码不匹配");
		}
		return errors;
	}

	/**
	 * 验证表单域不为空
	 * 
	 * @return
	 */
	public StringBuffer volidateFormField(String username, String accountId) {
		StringBuffer errors = new StringBuffer("");
		// 取得的文本域的值为空 或者是 个空串
		if (username == null || username.trim().equals("")) {
			errors.append("用户名为空<br>");
		}
		if (accountId == null || accountId.trim().equals("")) {
			errors.append("账号为空");
		}
		return errors;
	}

	private void updateQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 4.在updateItemQuery的方法中，获取quantity,id 在获取购物车对象，调用service方法修改
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		int id = -1;
		int quantity = -1;
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {
			return;
		}
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);

		bookService.updateItemQuantity(sc, id, quantity);
		// 5.传回Json数据，bookNumber,totalMoney
		int bookNumber = bookService.getBookNumber(sc);
		float totalMoney = bookService.getTotalMoney(sc);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("bookNumber", bookNumber);
		result.put("totalMoney", totalMoney);

		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);

		response.setContentType("text/javascript");
		response.getWriter().println(jsonStr);
	}

	/**
	 * 清空购物车
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sc);
		request.getRequestDispatcher("WEB-INF/pages/emptyCart.jsp").forward(request, response);
	}

	/**
	 * 删除购物车里的书本信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.获取id信息
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}

		// 2.获取购物车
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		// 3.删除购物车里的书信息
		bookService.removeItemFromCart(id, sc);
		if (sc.isEmpty()) {
			request.getRequestDispatcher("WEB-INF/pages/emptyCart.jsp").forward(request, response);
			return;
		}
		// 4.返回购物车查看页面
		request.getRequestDispatcher("WEB-INF/pages/cart.jsp").forward(request, response);

	}

	/**
	 * 应用该方法跳转到购物车，
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forwardPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		// WEB-INF下的页面不能直接访问（超链接实质是跳转地址栏），需要通过请求转发的形式站到该页面
		request.getRequestDispatcher("WEB-INF/pages/" + page + ".jsp").forward(request, response);

	}

	private void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.获取书的编号
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		if (id > 0) {

			// 2.获取或创建购物车
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			// 3.将书加到购物车里并 更新 购物车
			flag = bookService.addToCart(id, sc);
		}
		if (flag) {
			// 4.回到原来页面 并反馈添加购物车的相关信息
			// 这里不能使用请求转发形式，虽然该request中带有信息，但没有携带list(book)相关信息，直接调用方法就行了
			getBooks(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath() + "/error-1.jsp");

	}

	
	private void getBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		System.out.println(idStr);
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		Book book = null;
		System.out.println(id);
		if (id > 0) {
			book = bookService.getBook(id);
		}
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		request.setAttribute("book", book);
		request.getRequestDispatcher("WEB-INF/pages/book.jsp").forward(request, response);
	}

	private void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		String pageNoStr = request.getParameter("pageNo");

		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;
		int pageNo = 1;

		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {
		}
		try {
			//表单取值 一定要记得trim（）下字符串  ，否则转成数字 有bug,但第一次进入参数都为空，应用trim（），会发生空指针异常
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {
		}
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}

		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(criteriaBook);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}

}
