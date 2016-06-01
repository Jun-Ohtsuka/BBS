package utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculateTimeUtil {

	public String calculateTime(Timestamp insertDate){

		Date now = new Date();
		Date nowTime = null;
		Date insert = null;
		long day = 0;
		String differenceTime = null;
		try{
			long dateNowTime = 0;
			long dateInsertDate = 0;
			SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatHour = new SimpleDateFormat("yyyy-MM-dd HH");
			SimpleDateFormat formatMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			SimpleDateFormat formatSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String checkInsertDate = formatSecond.format(insertDate).toString();

			String checkDay = formatDay.format(now).toString();
			String checkHour = formatHour.format(now).toString();
			String checkMinute = formatMinute.format(now).toString();

			if(checkInsertDate.startsWith(checkDay) == false){
				nowTime = formatDay.parse(formatDay.format(now));
				insert = formatDay.parse(formatDay.format(insertDate));
				dateNowTime = nowTime.getTime();
				dateInsertDate = insert.getTime();
				day = (dateNowTime - dateInsertDate)/(1000*60*60*24);
				differenceTime = day +"日前";
			}

			if(checkInsertDate.startsWith(checkDay)){
				nowTime = formatHour.parse(formatHour.format(now));
				insert = formatHour.parse(formatHour.format(insertDate));
				dateNowTime = nowTime.getTime();
				dateInsertDate = insert.getTime();
				day = (dateNowTime - dateInsertDate)/(1000*60*60);
				differenceTime = day +"時間前";
			}

			if(checkInsertDate.startsWith(checkHour)){
				nowTime = formatMinute.parse(formatMinute.format(now));
				insert = formatMinute.parse(formatMinute.format(insertDate));
				dateNowTime = nowTime.getTime();
				dateInsertDate = insert.getTime();
				day = (dateNowTime - dateInsertDate)/(1000*60);
				differenceTime = day +"分前";
			}

			if(checkInsertDate.startsWith(checkMinute)){
				nowTime = formatSecond.parse(formatSecond.format(now));
				insert = formatSecond.parse(formatSecond.format(insertDate));
				dateNowTime = nowTime.getTime();
				dateInsertDate = insert.getTime();
				day = (dateNowTime - dateInsertDate)/(1000);
				differenceTime = day +"秒前";
			}

		}catch(ParseException e){
			e.printStackTrace();
		}
		return differenceTime;
	}


}
