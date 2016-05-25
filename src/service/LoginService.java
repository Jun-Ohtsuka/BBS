package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;

import beans.User;
import dao.UserDao;
import utils.CipherUtil;

public class LoginService {
	public User login(String account, String password){

		Connection connection = null;

		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			User user = userDao.getLoginUser(connection, account, encPassword);

			if(user == null){
				return user;
			}

			commit(connection);
			return user;

		}catch (RuntimeException e){
			rollback(connection);
			throw e;
		}catch (Error e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}

	public User changeUserLogin(String account){

		Connection connection = null;

		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			User user = userDao.getChangeUser(connection, account);

			commit(connection);
			return user;
		}catch (RuntimeException e){
			rollback(connection);
			throw e;
		}catch (Error e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}
}

