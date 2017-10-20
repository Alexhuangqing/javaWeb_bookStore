package bookstroe.dao;

import java.util.List;

/**
 * 
 * Dao�ӿڣ�����Dao �Ļ�����������BaseDao �ṩʵ��
 *
 * @param <T>��Dao ʵ�ʲ����ķ�������
 */
public interface DAO<T> {
	
	/**
	 * ִ��INSERT���������ز�����¼��id
	 * @param sql
	 * @param args
	 * @return �����¼�¼��id
	 */
	long insert(String sql,Object ...args);
	/**
	 * ִ��update����������INSERT����û�з���ֵ����UPDATE,DELETE
	 * @param sql
	 * @param args
	 */
	void update(String sql,Object ... args);
	/**
	 * ִ�е�����¼�ò�ѯ�������������¼��Ӧ���һ������
	 * @param sql
	 * @param args
	 * @return
	 */
	T query(String sql , Object ...args);
	/**
	 * ִ�ж�����¼�ò�ѯ���������ؼ�¼�����List
	 * @param sql
	 * @param args
	 * @return
	 */
	List<T> queryForList(String sql,Object ... args);
	/**
	 * ִ��һ�����Ի�ֵ�ò�ѯ�����������ѯĳһ����¼��һ���ֶΣ�
	 * ������ͳ����Ϣ������Ҫ��ѯ��ֵ
	 * @param sql
	 * @param args
	 * @return
	 */
	<V> V getSingleVal(String sql, Object ... args);
	/**
	 * ����ִ�и��²���
	 * @param sql
	 * @param params
	 */
	void batch(String sql, Object[]... params);
	
}
