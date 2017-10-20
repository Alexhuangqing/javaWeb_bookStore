package bookstroe.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bookstore.db.JDBCUtils;
import bookstroe.dao.DAO;
import bookstroe.web.ConnectionContext;

public class BaseDao<T> implements DAO<T> {
	QueryRunner queryRunner = new QueryRunner();
	Class<T> clazz = null;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		// �õ������͵ĸ���
		Type superClass = getClass().getGenericSuperclass();
		// ���͸����Ǵ��в�����ʽ such as Collection<String>.
		if (superClass instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) superClass;
			// ����ת�� �õ���Щ����
			Type[] args = type.getActualTypeArguments();
			// ���õ��Ĳ���������ʼ���ֶ�
			if (args != null && args.length > 0) {
				clazz = (Class<T>) args[0];
			}
		}

	}

	@Override
	public long insert(String sql, Object... args) {
		Long id = 0L;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionContext.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i + 1, args[i]);
				}
			}
			preparedStatement.executeUpdate();
			// ��ȡ���ɵ�����ֵ��
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				id = resultSet.getLong(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//connection�����ڹ������йر�  ��ͬ
			JDBCUtils.release(resultSet, preparedStatement);
		}
		return id;
	}

	@Override
	public void update(String sql, Object... args) {
		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().getConnection();
			queryRunner.update(connection, sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public T query(String sql, Object... args) {
		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().getConnection();
			return queryRunner.query(connection, sql, new BeanHandler<T>(clazz), args);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> queryForList(String sql, Object... args) {
		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<T>(clazz), args);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <V> V getSingleVal(String sql, Object... args) {
		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().getConnection();
			return queryRunner.query(connection, sql, new ScalarHandler<V>(), args);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void batch(String sql, Object[]... params) {
		Connection connection = null;
		try {
			connection = ConnectionContext.getInstance().getConnection();
			queryRunner.batch(connection, sql, params);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
