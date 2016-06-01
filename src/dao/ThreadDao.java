package dao;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beans.Thread;
import exception.SQLRuntimeException;

public class ThreadDao {

	public void insertNewThread(Connection connection, Thread thread){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO `threads`( ");
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

			ps.setString(1, thread.getTitle());
			ps.setString(2, thread.getText());
			ps.setString(3, thread.getCategory());
			ps.setInt(4, thread.getUserId());

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
			sql.append("DELETE FROM `threads` WHERE `threads`.`id` = ? ;");

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
			String sql = "SELECT DISTINCT category FROM `threads` ;";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<String> stockList = new ArrayList<>();
			while (rs.next()) {
				String category = rs.getString("category");
				stockList.add(category);
			}
			return stockList;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public String[] getThreadStartTime(){
		Connection connection = null;
		PreparedStatement ps = null;
		try{
			connection = getConnection();
			String sql = "SELECT * FROM `threads` ORDER BY insert_date LIMIT 1 ;";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			String[] startTime = null;
			while (rs.next()){
				Timestamp insertDate = rs.getTimestamp("insert_date");
				SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
				String time = formatDay.format(insertDate).toString();
				startTime = time.split("-");
			}
			return startTime;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public String[] getThreadEndTime(){
		Connection connection = null;
		PreparedStatement ps = null;
		try{
			connection = getConnection();
			String sql = "SELECT * FROM `threads` ORDER BY insert_date DESC LIMIT 1 ;";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			String[] endTime = null;
			while (rs.next()){
				Timestamp insertDate = rs.getTimestamp("insert_date");
				SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
				String time = formatDay.format(insertDate).toString();
				endTime = time.split("-");
			}
			return endTime;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
}
