package bookstroe.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Administrator
 *
 */
public class ShoppingCart {
	private Map<Integer, ShoppingCartItem> books = new HashMap<Integer, ShoppingCartItem>();

	public void add(Book book) {
		ShoppingCartItem sci = books.get(book.getId());
		if (sci != null) {
			sci.increment();
		} else {
			books.put(book.getId(), new ShoppingCartItem(book));
		}
	}

	/**
	 * 根据map集合中 是否存在该键 来判断是否有键值对
	 * 判断是否有这种书
	 * @param id
	 * @return
	 */
	public boolean hasBook(Integer id) {
		return books.containsKey(id);
	}

	/**
	 * 
	 * @return
	 */
	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}

	/**
	 * 获取书的种类集合
	 * 
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems() {
		return books.values();
	}

	/**
	 * 获取书的总数量
	 * 
	 * @return
	 */
	public int getBookNumber() {
		int total = 0;
		for (ShoppingCartItem sci : books.values()) {
			total += sci.getQuantity();
		}
		return total;
	}

	/**
	 * 购物车的总钱数
	 * 
	 * @return
	 */
	public float getTotalMoney() {
		float sum = 0;
		for (ShoppingCartItem sci : books.values()) {
			sum += sci.getQuantityMoney();
		}
		return sum;
	}

	public boolean isEmpty() {
		return books.isEmpty();
	}
	public void remove(Integer id){
		books.remove(id);
	}
	public void clear() {
		books.clear();
	}

}
