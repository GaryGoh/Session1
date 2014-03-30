package Session;

import java.util.ArrayList;

public class UserNRecords {
	private String userID;
	private ArrayList<Record> record_list;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public ArrayList<Record> getRecordList() {
		return record_list;
	}
	public void setRecordList(ArrayList<Record> record_list) {
		this.record_list = record_list;
	}
}
