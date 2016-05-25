package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import beans.Message;
import exception.SQLRuntimeException;

public class CommentDao {

	public void insertNewComment(Connection connection, Message message, int id){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO `bbs`.`comments`( ");
			sql.append(" thread_id");
			sql.append(", text");
			sql.append(", user_id");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append(" ?");//thread_id
			sql.append(", ?");//text
			sql.append(", ?");//user_id
			sql.append(", NOW()");//insert_date
			sql.append(", NOW()");//update_date
			sql.append(");");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, id);
			ps.setString(2, message.getText());
			ps.setInt(3, message.getUserId());

			ps.executeUpdate();
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void deleteComment(Connection connection,int id){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM `bbs`.`comments` WHERE `comments`.`id` = ? ;");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, id);

			ps.executeUpdate();
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void deleteThreadComment(Connection connection,int id){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM `bbs`.`comments` WHERE `comments`.`thread_id` = ? ;");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, id);

			ps.executeUpdate();
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

}
