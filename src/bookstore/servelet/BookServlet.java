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

		// ������Ҫ��֤errors ���� ��������մ���ʼֵ,���errorsֵ�����ı� ˵��û��ͨ����֤��
		StringBuffer errors = new StringBuffer("");
		errors = volidateFormField(username, accountId);
		
		// ��StringBufferתΪString������֤
		if (errors.toString().trim().equals("")) {
			errors = volidateUser(username, accountId);
			if (errors.toString().trim().equals("")) {
				errors = volidateStoreNumber(request);
				if (errors.toString().trim().equals("")) {
					errors = volidateBalance(request, accountId);
				}
			}
		}
		//���û��ͨ����֤���򷵻���ʾ��Ϣ,��������
		if (!errors.toString().trim().equals("")) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("WEB-INF/pages/cash.jsp").forward(request, response);
			//һ����Ҫ����  
			return;
		}
		//ͨ����֤����߼�����.
		bookService.cash(BookStoreWebUtils.getShoppingCart(request),username,accountId);
		
		
		
		response.sendRedirect(request.getContextPath()+"/success.jsp");
	}

	/**
	 * ��֤�û����
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
			errors.append("�˻�����");
		}
		return errors;
	}

	/**
	 * ��֤����Ƿ�����Ҫ��
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
				errors.append("��" + sci.getBook().getTitle() + "��" + "��治��<br>");
			}
		}
		return errors;
	}

	/**
	 * ��֤�û������˺��Ƿ�ƥ��
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
			errors.append("�û��������벻ƥ��");
		}
		return errors;
	}

	/**
	 * ��֤����Ϊ��
	 * 
	 * @return
	 */
	public StringBuffer volidateFormField(String username, String accountId) {
		StringBuffer errors = new StringBuffer("");
		// ȡ�õ��ı����ֵΪ�� ������ ���մ�
		if (username == null || username.trim().equals("")) {
			errors.append("�û���Ϊ��<br>");
		}
		if (accountId == null || accountId.trim().equals("")) {
			errors.append("�˺�Ϊ��");
		}
		return errors;
	}

	private void updateQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 4.��updateItemQuery�ķ����У���ȡquantity,id �ڻ�ȡ���ﳵ���󣬵���service�����޸�
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
		// 5.����Json���ݣ�bookNumber,totalMoney
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
	 * ��չ��ﳵ
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
	 * ɾ�����ﳵ����鱾��Ϣ
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.��ȡid��Ϣ
		String idStr = request.getParameter("id");
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}

		// 2.��ȡ���ﳵ
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		// 3.ɾ�����ﳵ�������Ϣ
		bookService.removeItemFromCart(id, sc);
		if (sc.isEmpty()) {
			request.getRequestDispatcher("WEB-INF/pages/emptyCart.jsp").forward(request, response);
			return;
		}
		// 4.���ع��ﳵ�鿴ҳ��
		request.getRequestDispatcher("WEB-INF/pages/cart.jsp").forward(request, response);

	}

	/**
	 * Ӧ�ø÷�����ת�����ﳵ��
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forwardPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		// WEB-INF�µ�ҳ�治��ֱ�ӷ��ʣ�������ʵ������ת��ַ��������Ҫͨ������ת������ʽվ����ҳ��
		request.getRequestDispatcher("WEB-INF/pages/" + page + ".jsp").forward(request, response);

	}

	private void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.��ȡ��ı��
		String idStr = request.getParameter("id");
		int id = -1;
		boolean flag = false;
		try {
			id = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
		}
		if (id > 0) {

			// 2.��ȡ�򴴽����ﳵ
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			// 3.����ӵ����ﳵ�ﲢ ���� ���ﳵ
			flag = bookService.addToCart(id, sc);
		}
		if (flag) {
			// 4.�ص�ԭ��ҳ�� ��������ӹ��ﳵ�������Ϣ
			// ���ﲻ��ʹ������ת����ʽ����Ȼ��request�д�����Ϣ����û��Я��list(book)�����Ϣ��ֱ�ӵ��÷���������
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
			//��ȡֵ һ��Ҫ�ǵ�trim�������ַ���  ������ת������ ��bug,����һ�ν��������Ϊ�գ�Ӧ��trim�������ᷢ����ָ���쳣
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
