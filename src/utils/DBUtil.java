package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.SQLRuntimeException;


/*
 * DB(コネクション関係)のユーティリティ
 */
public class DBUtil {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/BBS";
	private static final String USER = "root";
	private static final String PASSWORD = "artonelico";

	static{

		try{
			Class.forName(DRIVER);
		}catch (ClassNotFoundException e){
			throw new RuntimeException (e);
		}
	}

	/*
	 * コネクションを取得します
	 *
	 * @return
	 */

	public static Connection getConnection(){

		try{
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

			connection.setAutoCommit(false);

			return connection;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	/*
	 * コミットします。
	 *
	 * @param connection
	 */

	public static void commit(Connection connection){
		try{
			connection.commit();
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

	/*
	 * ロールバックします。
	 *
	 * @param connection
	 */

	public static void rollback(Connection connection){
		try{
			connection.rollback();
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}
	}

}
