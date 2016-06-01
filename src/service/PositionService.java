package service;

import java.util.List;

import dao.PositionDao;

public class PositionService {

	public List<String> getPosition(){
		PositionDao positionDao = new PositionDao();
		List<String> position = positionDao.getPosition();
		return position;
	}

}


