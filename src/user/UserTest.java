package user;

import common.db.ConnectMySQL;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		ArrayList<HashMap<String, String>> rsList = new ArrayList<HashMap<String, String>>();
		String fn = "Brandon";
		String ln = "LEE";
		int gid = 0;
		User usr = new User();
		
		/*
		rsList = usr.addUser(fn, ln, gid);
		for(Map<String, String> map: rsList)
		{
			String result = map.get(0);
			System.out.println(result);
		}
		*/
		
		
		String wildCard = "Lee";
		rsList = usr.searchUser(wildCard);
		for(Map<String, String> map: rsList)
		{
			String firstName = map.get("firstName");
			String lastName = map.get("lastName");
			System.out.println(firstName+" "+lastName);
		}
		
		
	}
	


}
