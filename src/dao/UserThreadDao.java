package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import beans.UserThread;
import exception.SQLRuntimeException;

public class UserThreadDao {

	public List<UserThread> getUserThreads(Connection connection){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM bbs.threads_users ORDER BY insert_date DESC ;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserThread> ret = toUserMessageList(rs);

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
			Calendar calendar = Calendar.getInstance();
			String categorySQL = " category = category";

			if(category != null && category.equals("") == false){
				categorySQL = " category = '" + category + "'";
			}

			if(startYear == null || startYear.equals("")){
				startYear = "2016";
			}
			if(startMonth == null || startMonth.equals("")){
				startMonth = "1";
			}
			if(startDay == null || startDay.equals("")){
				startDay = "1";
			}

			String startTimeSQL = "'" + startYear + "-" + startMonth + "-" + startDay + "'";

			if(endYear == null || endYear.equals("")){
				endYear = String.valueOf(calendar.get(Calendar.YEAR));
			}
			if(endMonth == null || endMonth.equals("")){
				endMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
			}
			if(endDay == null || endDay.equals("")){
				endDay = String.valueOf(calendar.get(Calendar.DATE) +1);
			}else{
				int day = Integer.valueOf(endDay) + 1;
				endDay = String.valueOf(day);
			}

			String endTimeSQL = "'" + endYear + "-" + endMonth + "-" + endDay + "'";

			sql.append("SELECT * FROM bbs.threads_users"
			+ " WHERE " + categorySQL + " AND insert_date > " + startTimeSQL + " AND insert_date < " + endTimeSQL
					+" ORDER BY insert_date DESC ;");
			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserThread> ret = toUserMessageList(rs);
			return ret;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	private List<UserThread> toUserMessageList(ResultSet rs) throws SQLException{

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
				int freeze = rs.getInt("freeze");
				String differenceTime = calculateTime(insertDate);


				UserThread message = new UserThread();
				message.setAccount(account);
				message.setName(name);
				message.setThreadId(threadId);
				message.setUserId(userId);
				message.setTitle(title);
				message.setCategory(category);
				message.setText(text);
				message.setInsertDate(insertDate);
				message.setFreeze(freeze);
				message.setDifferenceTime(differenceTime);

				ret.add(message);
			}
			return ret;
		}finally{
			close(rs);
		}
	}

	private String calculateTime(Timestamp insertDate){

		Date now = new Date();
		Date nowTime = null;
		Date insert = null;
		long day = 0;
		String differenceTime = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			nowTime = sdf.parse(sdf.format(now));
			insert = sdf.parse(sdf.format(insertDate));
			long dateNowTime = nowTime.getTime();
			long dateInsertDate = insertDate.getTime();

			sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date checkNowTime = sdf.parse(sdf.format(now));
			Date checkInsertDate = sdf.parse(sdf.format(insert));
			if(checkInsertDate.equals(checkNowTime)){
				sdf = new SimpleDateFormat("yyyy-MM-dd HH");
				checkNowTime = sdf.parse(sdf.format(now));
				checkInsertDate = sdf.parse(sdf.format(insert));
				if(checkInsertDate.equals(checkNowTime)){
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					checkNowTime = sdf.parse(sdf.format(now));
					checkInsertDate = sdf.parse(sdf.format(insert));
					if(checkInsertDate.equals(checkNowTime)){
						sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						checkNowTime = sdf.parse(sdf.format(now));
						checkInsertDate = sdf.parse(sdf.format(insert));
						dateNowTime = checkNowTime.getTime();
						dateInsertDate = checkInsertDate.getTime();
						day = (dateNowTime - dateInsertDate)/(1000);
						differenceTime = day +"秒前";
					}else{
						dateNowTime = checkNowTime.getTime();
						dateInsertDate = checkInsertDate.getTime();
						day = (dateNowTime - dateInsertDate)/(1000*60);
						differenceTime = day +"分前";
					}
				}else{
					dateNowTime = checkNowTime.getTime();
					dateInsertDate = checkInsertDate.getTime();
					day = (dateNowTime - dateInsertDate)/(1000*60*60);
					differenceTime = day +"時間前";
				}
			}else{
				dateNowTime = checkNowTime.getTime();
				dateInsertDate = checkInsertDate.getTime();
				day = (dateNowTime - dateInsertDate)/(1000*60*60*24);
				differenceTime = day +"日前";
			}
		}catch(ParseException e){
			e.printStackTrace();
		}
		return differenceTime;
	}

}
