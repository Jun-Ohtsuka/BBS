package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Message;
import beans.UserThread;
import dao.CommentDao;
import dao.ThreadDao;
import dao.UserThreadDao;

public class ThreadService {

	private static final int LIMIT_NUM = 1000;

	public List<UserThread> getThread(String category, String startYear, String startMonth,
			String startDay, String endYear, String endMonth, String endDay){

		Connection connection = null;
		try{
			connection = getConnection();

			UserThreadDao userThreadDao = new UserThreadDao();
			List<UserThread> ret = userThreadDao.getSearchThreads(connection, category, startYear,
					startMonth, startDay, endYear, endMonth, endDay);

			commit(connection);

			return ret;
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

	public void register(Message message){

		Connection connection = null;

		try{
			connection = getConnection();

			ThreadDao threadDao = new ThreadDao();
			threadDao.insertNewThread(connection, message);

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

	public void deleteThread(int id){
		Connection connection = null;
		try{
			connection = getConnection();
			ThreadDao threadDao = new ThreadDao();
			threadDao.deleteThread(connection, id);
			CommentDao commentDao = new CommentDao();
			commentDao.deleteThreadComment(connection, id);

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

