package bookstroe.dao;

import java.util.Collection;
import java.util.List;

import bookstroe.domain.Book;
import bookstroe.domain.ShoppingCartItem;
import bookstroe.web.CriteriaBook;
import bookstroe.web.Page;

public interface BookDAO {
	/**
	 * 根据id 获取指定的book对象
	 * @param id
	 * @return
	 */
	public abstract Book getBook(int id);
	/**
	 * 根据传入的CriteriaBook对象返回对应的page对象
	 * @param cb
	 * @return
	 */
	public abstract Page<Book> getPage(CriteriaBook cb);
	/**
	 * 根据传入的CriteriaBook对象返回对应的记录数
	 * @param cb
	 * @return
	 */
	public abstract long getTotalBookNumber(CriteriaBook cb);
	
	/**
	 * 根据传入的 CriteriaBook 和 pageSize 返回当前页对应的 List 
	 * @param cb
	 * @param pageSize
	 * @return
	 */

	public abstract List<Book> getPageList(CriteriaBook cb, int pageSize);
	/**
	 * 返回指定 id 的 book 的 storeNumber 字段的值
	 * @param id
	 * @return
	 */
	public abstract int getStoreNumber(Integer id);
	/**
	 * 根据传入的 ShoppingCartItem 的集合, 
	 * 批量更新 books 数据表的 storenumber 和 salesnumber 字段的值
	 * @param items
	 */
	public abstract void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items);

}