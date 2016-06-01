package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.UserComment;
import exception.SQLRuntimeException;
import utils.CalculateTimeUtil;

public class UserCommentDao {

	public List<UserComment> getUserComments(Connection connection){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM `comments_users` ORDER BY insert_date ;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);

			return ret;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	private List<UserComment> toUserCommentList(ResultSet rs) throws SQLException{

		List<UserComment> ret = new ArrayList<>();
		try{
			while (rs.next()){
				int id = rs.getInt("comment_id");
				String name = rs.getString("name");
				int userId = rs.getInt("user_id");
				int threadId = rs.getInt("thread_id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				int userBranchId = rs.getInt("user_branch_id");
				String differenceTime = new CalculateTimeUtil().calculateTime(insertDate);

				UserComment message = new UserComment();
				message.setCommentId(id);
				message.setName(name);
				message.setUserId(userId);
				message.setThreadId(threadId);
				message.setText(text);
				message.setInsertDate(insertDate);
				message.setUserBranchId(userBranchId);
				message.setDifferenceTime(differenceTime);

				ret.add(message);
			}
			return ret;
		}finally{
			close(rs);
		}
	}

}
