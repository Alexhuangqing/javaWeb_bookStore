package bookstore.test;

import java.sql.Date;
import java.util.List;

import org.junit.Test;

import bookstroe.dao.impl.BookDaoImpl;
import bookstroe.domain.Book;

public class BaseDaoTest {
	BookDaoImpl bookDao = new BookDaoImpl();

	@Test
	public void testInsert() {
		String sql = "INSERT INTO trade(tradeid,userid,tradetime) values(?,?,?)";
		Long id = bookDao.insert(sql, 17, 1, new Date(new java.util.Date().getTime()));
		System.out.println(id);
	}

	@Test
	public void testUpdate() {
		String sql = "update mybooks set salesamount = ? where id = ?";
		bookDao.update(sql, 12, 4);
	}

	@Test
	public void testQuery() {
		//这里的sql语句返回的结果集 列名 要按照bean的属性书写，mysql不区分大小写，但是反射给javabean赋值，要有bean的格式
		String sql = "select id, author,title,price,publishingDate,"
				+ "salesAmount,storeNumber,remark from mybooks where id=?";
		Book book = bookDao.query(sql,5);
		System.out.println(book);
	}

	@Test
	public void testQueryForList() {
		String sql = "select id, author,title,price,publishingDate,"
				+ "salesAmount,storeNumber,remark from mybooks where id<?";
		List<Book> books = bookDao.queryForList(sql,5);
		System.out.println(books);
	}

	@Test
	public void testGetSingleVal() {
		String sql = "select title from mybooks where id = ?";
		String title = bookDao.getSingleVal(sql,5);
		System.out.println(title);
	}

	@Test
	public void testBatch() {
		String sql = "update mybooks set salesamount = ?, price = ?  where id= ?";
		bookDao.batch(sql,new Object[]{10,10,1},new Object[]{11,11,2},new Object[]{13,13,3});
	}

}
