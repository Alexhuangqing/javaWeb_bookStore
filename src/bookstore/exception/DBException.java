package bookstore.exception;

/**
 * 抛出的数据异常
 * 
 * @author Administrator
 *
 */
public class DBException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBException(String message) {
		super(message);
	}

}
