package bookstroe.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bookstroe.domain.ShoppingCart;

public class BookStoreWebUtils {
	/**
	 * 获取或者是创建购物车
	 * 
	 * @param request
	 * @return
	 */
	public static ShoppingCart getShoppingCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingCart");
		if (sc == null) {
			sc = new ShoppingCart();
			session.setAttribute("shoppingCart",sc);
		}
		return sc;
	}

}
