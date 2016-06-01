package service;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import beans.Thread;
import beans.UserComment;
import dao.CommentDao;
import dao.UserCommentDao;

public class CommentService {

	private static final int LIMIT_NUM = 1000;

	public List<UserComment> getUserComment(){

		Connection connection = null;
		try{
			connection = getConnection();

			UserCommentDao commentDao = new UserCommentDao();
			List<UserComment> ret = commentDao.getUserComments(connection);

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

	public void registerComment(Thread thread, int id){

		Connection connection = null;

		try{
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.insertNewComment(connection, thread, id);

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

	public void deleteComment(int id){
		Connection connection = null;
		try{
			connection = getConnection();
			CommentDao commentDao = new CommentDao();
			commentDao.deleteComment(connection, id);

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

