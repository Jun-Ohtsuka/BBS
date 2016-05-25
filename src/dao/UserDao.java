package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.User;
import beans.UserView;
import exception.NoRowsUpdatedRuntimeException;
import exception.SQLRuntimeException;

public class UserDao {

	public User getLoginUser(Connection connection, String account, String password){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM `bbs`.`users` WHERE account = ? AND password = ?";

			ps = connection.prepareStatement(sql);

			ps.setString(1, account);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if(userList.isEmpty() == true){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalStateException ("2 <= userList.size()");
			}else{
				return userList.get(0);
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public boolean checkAccount(Connection connection, String account){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT account FROM `bbs`.`users` ;";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<String> stockList = new ArrayList<>();
			while (rs.next()) {
				String checkAccount = rs.getString("account");
				stockList.add(checkAccount);
			}
			if(stockList.contains(account) == true){
				return false;
			}

			return true;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public boolean checkEditAccount(Connection connection, String account, int id){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT id,account FROM `bbs`.`users` ;";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<String> stockList = new ArrayList<>();
			while (rs.next()) {
				String checkAccount = rs.getString("account");
				int checkId = rs.getInt("id");
				if(checkId != id){
					stockList.add(checkAccount);
				}
			}

			if(stockList.contains(account) == true){
				return false;
			}

			return true;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<>();
		try{
			while (rs.next()){
				int id = rs.getInt("id");
				String account = rs.getString("account");
				String name = rs.getString("name");
				String password = rs.getString("password");
				int branchId = rs.getInt("branch_id");
				int positionId = rs.getInt("position_id");
				int freeze = rs.getInt("freeze");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				User user = new User();
				user.setId(id);
				user.setAccount(account);
				user.setName(name);
				user.setPassword(password);
				user.setBranchId(branchId);
				user.setPositionId(positionId);
				user.setFreeze(freeze);
				user.setInsertDate(insertDate);
				user.setUpdateDate(updateDate);

				ret.add(user);
			}
			return ret;
		}finally{
			close(rs);
		}
	}


	private List<UserView> toUserViewList(ResultSet rs) throws SQLException {

		List<UserView> ret = new ArrayList<>();
		try{
			while (rs.next()){
				int id = rs.getInt("id");
				String account = rs.getString("account");
				String name = rs.getString("name");
				String branch = rs.getString("branch_name");
				String position = rs.getString("position_name");
				int freeze = rs.getInt("freeze");

				UserView user = new UserView();
				user.setId(id);
				user.setAccount(account);
				user.setName(name);
				user.setBranchName(branch);
				user.setPositionName(position);
				user.setFreeze(freeze);

				ret.add(user);
			}
			return ret;
		}finally{
			close(rs);
		}
	}

	public void insert(Connection connection, User user){

		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();

			sql.append("INSERT INTO `bbs`.`users` ( ");
			sql.append(" account");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", position_id");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append(" ?");//account
			sql.append(", ?");//password
			sql.append(", ?");//name
			sql.append(", ?");//branch
			sql.append(", ?");//position
			sql.append(", NOW()");//insert_date
			sql.append(", NOW()");//update_date
			sql.append(");");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getPositionId());
			ps.executeUpdate();
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void update(Connection connection, User user, int id){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `bbs`.`users` SET");
			sql.append(" `account` = ?");
			sql.append(", `name` = ?");
			sql.append(", `password` = ?");
			sql.append(", `branch_id` = ?");
			sql.append(", `position_id` = ?");
			sql.append(", `update_date` = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" `id` = ?");
			sql.append(" AND");
			sql.append(" `update_date` = ? ;");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPassword());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getPositionId());
			ps.setInt(6, id);
			User userUpdateDate = getEditedUser(connection, id);
			ps.setTimestamp(7, new Timestamp(userUpdateDate.getUpdateDate().getTime()));

			int count = ps.executeUpdate();
			if(count == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void updateNonPassword(Connection connection, User user, int id){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `bbs`.`users` SET");
			sql.append(" `account` = ?");
			sql.append(", `name` = ?");
			sql.append(", `branch_id` = ?");
			sql.append(", `position_id` = ?");
			sql.append(", `update_date` = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" `id` = ?");
			sql.append(" AND");
			sql.append(" `update_date` = ? ;");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranchId());
			ps.setInt(4, user.getPositionId());
			ps.setInt(5, id);
			User userUpdateDate = getEditedUser(connection, id);
			ps.setTimestamp(6, new Timestamp(userUpdateDate.getUpdateDate().getTime()));

			int count = ps.executeUpdate();
			if(count == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void updateFreeze(Connection connection, User user, int id){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE `bbs`.`users` SET");
			sql.append(" `freeze` = ?");
			sql.append(", `update_date` = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" `id` = ?");
			sql.append(" AND");
			sql.append(" `update_date` = ? ;");

			ps = connection.prepareStatement(sql.toString());
			User userUpdateDate = getEditedUser(connection, id);
			if(user.getFreeze() == 0){
				ps.setInt(1, 1);
			}else{
				ps.setInt(1, 0);

			}
			ps.setInt(2, user.getId());
			ps.setTimestamp(3, new Timestamp(userUpdateDate.getUpdateDate().getTime()));

			int count = ps.executeUpdate();
			if(count == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}


	public User getEditedUser(Connection connection, int id){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM `bbs`.`users` WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1,  id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if(userList.isEmpty() == true){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			}else{
				return userList.get(0);
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public List<User> getUser(Connection connection){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM `bbs`.`users` ;";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if(userList.isEmpty() == true){
				return null;
			}else{
				return userList;
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public List<UserView> getUserView(Connection connection){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM `bbs`.`user_view` ;";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<UserView> userList = toUserViewList(rs);
			if(userList.isEmpty() == true){
				return null;
			}else{
				return userList;
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public User getChangeUser(Connection connection, String account){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM `bbs`.`users` WHERE account = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, account);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if(userList.isEmpty() == true){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			}else{
				return userList.get(0);
			}
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}


}
