package dao;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Message;
import exception.SQLRuntimeException;

public class ThreadDao {

	public void insertNewThread(Connection connection, Message message){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO `bbs`.`threads`( ");
			sql.append(" title");
			sql.append(", text");
			sql.append(", category");
			sql.append(", user_id");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append(" ?");//title
			sql.append(", ?");//text
			sql.append(", ?");//category
			sql.append(", ?");//user_id
			sql.append(", NOW()");//insert_date
			sql.append(", NOW()");//update_date
			sql.append(");");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message.getTitle());
			ps.setString(2, message.getText());
			ps.setString(3, message.getCategory());
			ps.setInt(4, message.getUserId());

			ps.executeUpdate();
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void deleteThread(Connection connection,int id){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM `bbs`.`threads` WHERE `threads`.`id` = ? ;");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, id);

			ps.executeUpdate();
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public List<String> getCategory(){
		Connection connection = null;
		PreparedStatement ps = null;
		try{
			connection = getConnection();
			String sql = "SELECT category FROM `bbs`.`threads`";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<String> stockList = new ArrayList<>();
			while (rs.next()) {
				String category = rs.getString("category");
				if(stockList.contains(category) == false){
					stockList.add(category);
				}
			}
			return stockList;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

}
