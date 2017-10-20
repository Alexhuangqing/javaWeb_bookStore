package bookstore.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import bookstroe.dao.impl.BookDaoImpl;
import bookstroe.domain.Book;
import bookstroe.domain.ShoppingCartItem;
import bookstroe.web.CriteriaBook;

public class BookDaoImplTest {
	BookDaoImpl bookDao = new BookDaoImpl();

	@Test
	public void testGetBook() {
		Book book = bookDao.getBook(5);
		System.out.println(book);
	}

	@Test
	public void testGetPage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTotalBookNumber() {
		CriteriaBook cb = new CriteriaBook(0, Integer.MAX_VALUE, 1);
		long totalBookNumber = bookDao.getTotalBookNumber(cb);
		System.out.println(totalBookNumber);
	}

	@Test
	public void testGetPageList() {
		CriteriaBook cb = new CriteriaBook(50, 60, 5);
		List<Book> books = bookDao.getPageList(cb, 3);
		System.out.println(books);
	}

	@Test
	public void testGetStoreNumber() {
		int storeNumber = bookDao.getStoreNumber(10);
		System.out.println(storeNumber);
	}

	@Test
	public void testBatchUpdateStoreNumberAndSalesAmount() {
		Collection<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();
		Book book = null;
		ShoppingCartItem sci = null;
		
		book = new Book();
		book.setId(1);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(1);
		items.add(sci);
		
		book = new Book();
		book.setId(2);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(2);
		items.add(sci);
		
		book = new Book();
		book.setId(3);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(3);
		items.add(sci);
		
		bookDao.batchUpdateStoreNumberAndSalesAmount(items);
	}

}
