package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.User;
import beans.UserView;
import dao.UserDao;
import utils.CipherUtil;

public class UserService {

	public void register(User user){

		Connection connection = null;

		try{
			connection = getConnection();
			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.insert(connection, user);

			commit(connection);
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


	public void update(User user, int id){

		Connection connection = null;
		try{
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.update(connection, user, id);

			commit(connection);
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

	public void updateNonPassword(User user, int id){

		Connection connection = null;
		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			userDao.updateNonPassword(connection, user,id);

			commit(connection);
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

	public List<UserView> getUserView(){

		Connection connection = null;
		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			List<UserView> user = userDao.getUserView(connection);

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

	public List<User> getUser(){

		Connection connection = null;
		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			List<User> user = userDao.getUser(connection);

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

	public User getEditedUser(int userId){

		Connection connection = null;
		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			User user = userDao.getEditedUser(connection, userId);

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

	public void updateFreeze(User user, int id){

		Connection connection = null;
		try{
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.updateFreeze(connection, user, id);

			commit(connection);
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


