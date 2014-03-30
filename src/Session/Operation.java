package Session;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Operation {

	private static ArrayList<UserNRecords> list = new ArrayList<UserNRecords>();
	private static ArrayList<UserNBrands> userNBrands_list = new ArrayList<UserNBrands>();
	private static Record temp_record = new Record();

	public void get_file() throws IOException {
		// Source
		//File f = new File("D://Alibaba//test.csv");
		File f = new File("D://Alibaba//t_alibaba_data.csv");
		InputStream is = new FileInputStream(f);
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			String lineString = reader.readLine(); // filter title
			UserNRecords userNRecords = null;
			ArrayList<Record> brandRecords = null;
			String tempUserID = "00000";

			for (;;) {
				lineString = reader.readLine();
				if (lineString == null) {
					break;
				}

				Record r = new Record();
				String tokens[] = lineString.split(",");
				String userID = tokens[0];
				r.setBrand(tokens[1]);
				r.setType(tokens[2]);
				r.setDate(tokens[3]);
				if (!userID.equals(tempUserID)) {
					userNRecords = new UserNRecords();
					brandRecords = new ArrayList<Record>();
					userNRecords.setUserID(userID);
					userNRecords.setRecordList(brandRecords);
					list.add(userNRecords);
				}
				brandRecords.add(r);
				tempUserID = userID;

			}

			is.close();
		} catch (Exception e) {
		}
	}

	public void get_expected_list(ExpectedList expectedList) {

		for (int i = 0; i < list.size(); i++) { // all users - all brands
			UserNRecords userNRecords = list.get(i); // get a user with related
														// records
			UserNBrands newUserNBrands = new UserNBrands();
			newUserNBrands.setUserID(userNRecords.getUserID()); // store userID
			int count = 0;
			ArrayList<Record> records = userNRecords.getRecordList();

			ArrayList<String> new_brand_list = new ArrayList<String>();

			for (int j = 0; j < records.size(); j++) { // a user - related records
														
				Record r = records.get(j);
				String brand_id = r.getBrand();
				String type = r.getType();
				String date = r.getDate();
				
				temp_record.setBrand(brand_id);
				temp_record.setDate(date);

				if (count >= 3) {
					new_brand_list.add(brand_id);
					count = 0;
				} else if (count < 3 && type.equals("1") || type.equals("2")
						|| type.equals("3")) {
					new_brand_list.add(brand_id);
					count = 0;
				} else if (count < 3 && type.equals("0")
						&& !date.equals(temp_record.getDate())) {
					new_brand_list.add(brand_id);
					count = 0;
				}
				
				if(!new_brand_list.contains(brand_id)){
					count++;
					temp_record.setBrand(brand_id);
					temp_record.setDate(date);
				}				
			}
			newUserNBrands.setBrand_list(new_brand_list); // store brands
			userNBrands_list.add(newUserNBrands); // store a user with brands
		}
		expectedList.setExpectedList(userNBrands_list);
	}

	public void print_file(ExpectedList expectedList) throws IOException {
		ArrayList<UserNBrands> list = expectedList.getExpectedList();
		//File f = new File("D://alibaba//test.txt");
		File f = new File("D://alibaba//expectedlist.txt");
		FileWriter fos = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fos);

		for (int i = 0; i < list.size(); i++) {
			UserNBrands userNBrands = list.get(i);
			bw.write(userNBrands.getUserID() + "\t");

			ArrayList<String> brand_list = userNBrands.getBrand_list();
			for (int j = 0; j < brand_list.size(); j++) {
				bw.write(brand_list.get(j));
				if ( j+1 != brand_list.size()){
					bw.write(",");
				}
			}
			bw.newLine();

		}
		bw.close();
	}

}
