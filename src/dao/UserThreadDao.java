package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import beans.UserThread;
import exception.SQLRuntimeException;
import utils.CalculateTimeUtil;

public class UserThreadDao {

	public List<UserThread> getUserThreads(Connection connection){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM threads_users ORDER BY insert_date DESC ;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserThread> ret = toUserThreadList(rs);

			return ret;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public List<UserThread> getSearchThreads(Connection connection, String category, String startYear, String startMonth,
			String startDay,String endYear, String endMonth, String endDay){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			String categorySQL = " category = category";

			if(StringUtils.isEmpty(category) == false){
				categorySQL = " category = '" + category + "'";
			}

			String startTimeSQL = "'" + startYear + "-" + startMonth + "-" + startDay + "'";

			int day = Integer.valueOf(endDay) + 1;
			endDay = String.valueOf(day);
			String endTimeSQL = "'" + endYear + "-" + endMonth + "-" + endDay + "'";

			sql.append("SELECT * FROM threads_users"
			+ " WHERE " + categorySQL + " AND insert_date > " + startTimeSQL + " AND insert_date < " + endTimeSQL
					+" ORDER BY insert_date DESC ;");
			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserThread> ret = toUserThreadList(rs);
			return ret;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	private List<UserThread> toUserThreadList(ResultSet rs) throws SQLException{

		List<UserThread> ret = new ArrayList<>();
		try{
			while (rs.next()){
				String account = rs.getString("account");
				String name = rs.getString("name");
				int threadId = rs.getInt("thread_id");
				int userId = rs.getInt("user_id");
				String title = rs.getString("title");
				String category = rs.getString("category");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				int userBranchId = rs.getInt("user_branch_id");
				String differenceTime = new CalculateTimeUtil().calculateTime(insertDate);


				UserThread thread = new UserThread();
				thread.setAccount(account);
				thread.setName(name);
				thread.setThreadId(threadId);
				thread.setUserId(userId);
				thread.setTitle(title);
				thread.setCategory(category);
				thread.setText(text);
				thread.setInsertDate(insertDate);
				thread.setUserBranchId(userBranchId);
				thread.setDifferenceTime(differenceTime);

				ret.add(thread);
			}
			return ret;
		}finally{
			close(rs);
		}
	}
}
