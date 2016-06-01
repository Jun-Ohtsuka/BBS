package service;

import java.util.List;

import dao.BranchDao;

public class BranchService {

	public List<String> getBranch(){
		BranchDao branchDao = new BranchDao();
		List<String> branch = branchDao.getBranch();
		return branch;
	}

}


