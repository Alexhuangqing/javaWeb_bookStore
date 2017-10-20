package bookstroe.dao;

import java.util.List;

/**
 * 
 * Dao接口，定义Dao 的基本操作，有BaseDao 提供实现
 *
 * @param <T>：Dao 实际操作的泛型类型
 */
public interface DAO<T> {
	
	/**
	 * 执行INSERT操作，返回插入后记录得id
	 * @param sql
	 * @param args
	 * @return 插入新纪录的id
	 */
	long insert(String sql,Object ...args);
	/**
	 * 执行update操作，包括INSERT（但没有返回值），UPDATE,DELETE
	 * @param sql
	 * @param args
	 */
	void update(String sql,Object ... args);
	/**
	 * 执行单个记录得查询操作，返回与记录对应类的一个对象
	 * @param sql
	 * @param args
	 * @return
	 */
	T query(String sql , Object ...args);
	/**
	 * 执行多条记录得查询操作，返回记录对象的List
	 * @param sql
	 * @param args
	 * @return
	 */
	List<T> queryForList(String sql,Object ... args);
	/**
	 * 执行一个属性或值得查询操作，例如查询某一条记录的一个字段，
	 * 或者是统计信息，返回要查询的值
	 * @param sql
	 * @param args
	 * @return
	 */
	<V> V getSingleVal(String sql, Object ... args);
	/**
	 * 批量执行更新操作
	 * @param sql
	 * @param params
	 */
	void batch(String sql, Object[]... params);
	
}
