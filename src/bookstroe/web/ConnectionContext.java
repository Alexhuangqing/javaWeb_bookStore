package bookstroe.web;

import java.sql.Connection;

/**
 * 
 * ���������ݿ�����connection��ThreadLocal�󶨵�һ�� �İ�����һ��request���󣬿���һ���̣߳�
 * �������filter����ʹ��
 *
 */
public class ConnectionContext {
	
	private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	private Connection connection = null;
	// һ�������һ��connection��ʹ�õ���ģʽ
	private ConnectionContext() {}
	
	private static ConnectionContext instance = new ConnectionContext();
	
	public static ConnectionContext getInstance(){
		return instance;
	}
	/**
	 * 
	 * connection�󶨵������߳�
	 */
	public void bind(Connection connection){
		this.connection = connection;
		tl.set(connection);
	}
	/**
	 * connection�������߳��Ͻ����
	 */
	public void remove(){
		tl.remove();
	}
	/**
	 * ��ȡconnection
	 * @return
	 */
	public Connection getConnection() {
		
		return connection;
	}
	
	
	
	
	

}
