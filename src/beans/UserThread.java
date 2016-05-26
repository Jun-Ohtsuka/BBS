package beans;

import java.io.Serializable;
import java.util.Date;

public class UserThread implements Serializable {
	private static final long serialVersionUID = 1L;

	private String account;
	private String name;
	private int userId;
	private int thread_id;
	private String title;
	private String category;
	private String text;
	private Date insertDate;
	private int freeze;
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

	public int getUserId() {
		return userId;
	}

	public void setThreadId(int thread_id) {
		this.thread_id = thread_id;
	}

	public int getThreadId() {
		return thread_id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public int getFreeze() {
		return freeze;
	}

	public void setFreeze(int freeze) {
		this.freeze = freeze;
	}

	public String getDifferenceTime() {
		return differenceTime;
	}

	public void setDifferenceTime(String differenceTime) {
		this.differenceTime = differenceTime;
	}

}
