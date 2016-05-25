package beans;

import java.io.Serializable;

public class UserView implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String account;
	private String name;
	private String branch_name;
	private String position_name;
	private int freeze;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranchName() {
		return branch_name;
	}

	public void setBranchName(String branch_name) {
		this.branch_name = branch_name;
	}


	public String getPositionName() {
		return position_name;
	}

	public void setPositionName(String position_name) {
		this.position_name = position_name;
	}

	public int getFreeze() {
		return freeze;
	}

	public void setFreeze(int freeze) {
		this.freeze = freeze;
	}

}
