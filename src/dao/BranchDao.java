package dao;

import static utils.CloseableUtil.*;
import static utils.DBUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Branch;
import exception.SQLRuntimeException;

public class BranchDao {

	public List<String> getBranch(){
		Connection connection = null;
		PreparedStatement ps = null;
		try{
			connection = getConnection();
			String sql = "SELECT * FROM `bbs`.`branchs`";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<String> stockList = new ArrayList<>();
			while (rs.next()) {
				String branch = rs.getString("name");
				stockList.add(branch);
			}
			return stockList;
		}catch (SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}

	}

	private List<Branch> toBranchList(ResultSet rs) throws SQLException {

		List<Branch> ret = new ArrayList<>();
		try{
			while (rs.next()){
				String name = rs.getString("name");

				Branch branch = new Branch();
				branch.setName(name);

				ret.add(branch);
			}
			return ret;
		}finally{
			close(rs);
		}
	}

}
