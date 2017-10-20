package bookstroe.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bookstroe.dao.BookDAO;
import bookstroe.domain.Book;
import bookstroe.domain.ShoppingCartItem;
import bookstroe.web.CriteriaBook;
import bookstroe.web.Page;

public class BookDaoImpl extends BaseDao<Book> implements BookDAO {

	@Override
	public Book getBook(int id) {
		String sql = "select id, author,title,price,publishingDate,"
				+ "salesAmount,storeNumber,remark from mybooks where id=?";
		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page<Book> page = new Page<Book>(cb.getPageNo());
		Long totalItemNumber = getTotalBookNumber(cb);
		List<Book> books = getPageList(cb, 3);
		page.setTotalItemNumber(totalItemNumber);
		page.setList(books);
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "select count(id) from mybooks" + " where price >= ?  and price <= ?";
		return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
	}

	/**
	 * ע���ҳ���� ����ҳ��fromIndex=0�� �ʵ�ǰҳ�Ǵӣ�pageNo-1��*pageSize
	 */
	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "select id, author,title,price,publishingDate," + "salesAmount,storeNumber,remark from mybooks "
				+ "where price >= ?  and price <= ? limit ?,?";
		// ���ﲻ��ֱ�Ӵ���CriteriaBook��pageNo,��ΪCritiriaBook��pageNoû�о���У��
		// ���� Ҫ����Page��pageNo
		//���ﲻ�ܴ���pageList �������������ѭ���� ֻ��Ҫpage����У��
		Page<Book> page = new Page<Book>(cb.getPageNo());
		Long totalItemNumber = getTotalBookNumber(cb);
		page.setTotalItemNumber(totalItemNumber);
		
		return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(), (page.getPageNo() - 1) * pageSize, pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "select storeNumber from mybooks where id=?";
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		String sql = "update mybooks set storeNumber = storeNumber - ? ,"
				+ "salesAmount = salesAmount + ? where id = ?";
		Object[][]  params = new Object[items.size()][3];
		
		List<ShoppingCartItem> scis = new ArrayList<ShoppingCartItem>(items);
		for(int i = 0; i < items.size(); i++){
			params[i][0] = scis.get(i).getQuantity();
			params[i][1] = scis.get(i).getQuantity();
			params[i][2] = scis.get(i).getBook().getId();
		}
		batch(sql, params);

	}

}
