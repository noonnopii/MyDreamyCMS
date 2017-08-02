package common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class ConnectMySQL {

/*
	// constructor
	ConnectMySQL(String type, String qry){
		this.setType(type);
		this.setQuery(qry);
	}
	
	ConnectMySQL(){
		String tempType = "S";
		String tempQuery = "SELECT * FROM user WHERE userID = 1";
		this.setType(tempType);
		this.setType(tempQuery);
	}
	
	
	// setters
	public void setType(String typ){
		typ.toUpperCase();
		this.type = typ;
	}
	
	public void setQuery(String qry){
		this.query = qry;
	}
	*/
	
	// main
	
	//public setHostName (String )
	
	public ArrayList<HashMap<String, String>> StartQuery(String query) throws Exception {

		/*
		String hostName = "nanoomii.org"; 
		String portNum = "3306";
		String dbName = "dbMyDreamyCom";
		String userName = "usrMyDreamyCom";
		String userPassword = "Gene7942";
		*/
		
		String hostName = "mydreamy.ddns.net";
		String portNum = "3306";
		String dbName = "dbMyDreamyCom";
		String userName = "noonnopii";
		String userPassword = "gene7942";
		
		String qry = query;
		
		ArrayList<HashMap<String, String>> rsList = new ArrayList<HashMap<String,String>>();
		
		LoadDriver();		
		rsList = RunQuery(hostName, portNum, dbName, userName, userPassword, qry);
		
		return rsList;
	}
	
	private void LoadDriver (){
		try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        	System.out.println("DB Driver Error");
        }
	}
	
	private ArrayList<HashMap<String, String>> RunQuery (String host, String port, String db, String user, String pw, String qry) throws Exception{
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		ArrayList<HashMap<String, String>> rsList = new ArrayList<HashMap<String,String>>();
		
		try {

			// connection establishment
		    conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"?user="+user+"&password="+pw);
		 
		    // DB use statement
		    String useDBQuery = "USE "+db;
		    stmt = conn.createStatement();
		    stmt.execute(useDBQuery);

		
		    // display query
		    System.out.println(qry);
		    
		    // run query
		    String type = qry.substring(0, 6);
		    ResultSetMetaData meta      = null;
			HashMap<String, String> map = null;
		    switch (type) {
		    case "INSERT":	// insert
		    case "UPDATE":	// update
		    case "DELETE":	// delete
		    			PreparedStatement pStmt = null;
				    	pStmt = conn.prepareStatement(qry);
				        pStmt.executeUpdate();
				        rs = pStmt.getResultSet();
				        meta = rs.getMetaData();
				        map = new HashMap<String, String>();
						convertResultToMap(rs, map, meta);
						rsList.add(map);
		    			break;
		    case "SELECT":	// select
				    	int counter = 0;
				    	
					    if (stmt.execute(qry)) {
					        rs = stmt.getResultSet();
					        
					        while (rs.next())
					        {
					        	counter++;

								if(counter==1)
									meta = rs.getMetaData();
								
					        	if (counter >= 10000)
					        		throw new Exception("Result is over 10000 row, please check condition again.");
					        	
					        	map = new HashMap<String, String>();
								convertResultToMap(rs, map, meta);
								rsList.add(map);
					        }
					        
					        if (counter <= 0){
					        	System.out.println("No result");
					        }
					    }
		    			break;
		    default:
		    			System.out.println("No result");
		    			break;
		    
		    }
		    conn.close();
      		
		    
		} catch (SQLException ex) {
			
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    
		    
		} finally {
			
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore

		        rs = null;
		    }

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore

		        stmt = null;
		    }
		}
		return rsList;
	}
	
	private void convertResultToMap(ResultSet rs, HashMap<String, String> map, ResultSetMetaData meta) throws Exception{
		int colCount = meta.getColumnCount();
		for(int i=0; i<colCount; i++){
			map.put(meta.getColumnName(i+1), rs.getString(i+1));
		}

	}	
}
