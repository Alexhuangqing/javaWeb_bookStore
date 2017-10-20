package bookstroe.dao;

import java.util.Collection;
import java.util.List;

import bookstroe.domain.Book;
import bookstroe.domain.ShoppingCartItem;
import bookstroe.web.CriteriaBook;
import bookstroe.web.Page;

public interface BookDAO {
	/**
	 * ����id ��ȡָ����book����
	 * @param id
	 * @return
	 */
	public abstract Book getBook(int id);
	/**
	 * ���ݴ����CriteriaBook���󷵻ض�Ӧ��page����
	 * @param cb
	 * @return
	 */
	public abstract Page<Book> getPage(CriteriaBook cb);
	/**
	 * ���ݴ����CriteriaBook���󷵻ض�Ӧ�ļ�¼��
	 * @param cb
	 * @return
	 */
	public abstract long getTotalBookNumber(CriteriaBook cb);
	
	/**
	 * ���ݴ���� CriteriaBook �� pageSize ���ص�ǰҳ��Ӧ�� List 
	 * @param cb
	 * @param pageSize
	 * @return
	 */

	public abstract List<Book> getPageList(CriteriaBook cb, int pageSize);
	/**
	 * ����ָ�� id �� book �� storeNumber �ֶε�ֵ
	 * @param id
	 * @return
	 */
	public abstract int getStoreNumber(Integer id);
	/**
	 * ���ݴ���� ShoppingCartItem �ļ���, 
	 * �������� books ���ݱ�� storenumber �� salesnumber �ֶε�ֵ
	 * @param items
	 */
	public abstract void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items);

}