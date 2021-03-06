package dao;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.SQLRuntimeException;

public class PositionDao {

	public List<String> getPosition(){
		Connection connection = null;
		PreparedStatement ps = null;
		try{
			connection = getConnection();
			String sql = "SELECT * FROM `positions`";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<String> stockList = new ArrayList<>();
			while (rs.next()) {
				String position = rs.getString("name");
				stockList.add(position);
			}
			return stockList;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}
}
