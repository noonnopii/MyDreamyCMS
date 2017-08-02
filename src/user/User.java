package user;

import common.db.ConnectMySQL;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

public class User {

	
	
	public ArrayList<HashMap<String, String>> searchUser(String wc) throws Exception{

		ArrayList<HashMap<String, String>> rsList = new ArrayList<HashMap<String, String>>();

		StringBuffer buf = new StringBuffer();
		buf.append("SELECT * 																\n");
		buf.append("	FROM users															\n");
		buf.append("	WHERE UPPER(firstName) LIKE '%"+wc.toUpperCase()+"%'				\n");
		buf.append("	OR UPPER(lastName) LIKE '%"+wc.toUpperCase()+"%'					\n");
		buf.append("	ORDER BY firstName, lastName 										\n");
		
		ConnectMySQL querySet = new ConnectMySQL(); 
		rsList = querySet.StartQuery(buf.toString());
		return rsList;
	}

	public ArrayList<HashMap<String, String>> addUser(String fn, String ln, int gid) throws Exception{

		ArrayList<HashMap<String, String>> rsList = new ArrayList<HashMap<String, String>>();

		StringBuffer buf = new StringBuffer();
		buf.append("INSERT	 																\n");
		buf.append("	INTO users															\n");
		buf.append("	(fk_groupID_users, firstName, lastName, userStatus)					\n");
		buf.append("	VALUES (															\n");
		if (gid!=0) buf.append(gid); else buf.append("NULL"); 
		buf.append("	,'"+fn.trim()+"','"+ln.trim()+"','A')								\n");
		
		String test = buf.toString();
		ConnectMySQL querySet = new ConnectMySQL(); 
		rsList = querySet.StartQuery(buf.toString());
		return rsList;
	}
}
