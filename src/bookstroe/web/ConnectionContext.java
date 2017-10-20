package bookstroe.web;

import java.sql.Connection;

/**
 * 
 * 用来将数据库链接connection与ThreadLocal绑定到一起 的绑定器，一个request请求，开启一个线程，
 * 与过滤器filter联合使用
 *
 */
public class ConnectionContext {
	
	private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	private Connection connection = null;
	// 一个请求绑定一个connection，使用单例模式
	private ConnectionContext() {}
	
	private static ConnectionContext instance = new ConnectionContext();
	
	public static ConnectionContext getInstance(){
		return instance;
	}
	/**
	 * 
	 * connection绑定到请求线程
	 */
	public void bind(Connection connection){
		this.connection = connection;
		tl.set(connection);
	}
	/**
	 * connection从请求线程上解除绑定
	 */
	public void remove(){
		tl.remove();
	}
	/**
	 * 获取connection
	 * @return
	 */
	public Connection getConnection() {
		
		return connection;
	}
	
	
	
	
	

}
