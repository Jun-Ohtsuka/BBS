package beans;

import java.io.Serializable;
import java.util.Date;

public class UserThread implements Serializable {
	private static final long serialVersionUID = 1L;

	private String account;
	private String name;
	private int user_id;
	private int thread_id;
	private String title;
	private String category;
	private String text;
	private Date insertDate;
	private int user_branch_id;
	private String differenceTime;

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

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}

	public int getUserId() {
		return user_id;
	}

	public void setThreadId(int thread_id) {
		this.thread_id = thread_id;
	}

	public int getThreadId() {
		return thread_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public int getUserBranchId() {
		return user_branch_id;
	}

	public void setUserBranchId(int user_branch_id) {
		this.user_branch_id = user_branch_id;
	}

	public String getDifferenceTime() {
		return differenceTime;
	}

	public void setDifferenceTime(String differenceTime) {
		this.differenceTime = differenceTime;
	}

}
