package model;

public class DbConnection {
	//Declaring variables
	private String url;
	private String userName;
	private String password;
	
	/**
	 * Database connection setup
	 */
	
	public DbConnection() {
		//Instantiating variables
		this.url = "jdbc:mysql://databases.aii.avans.nl:3306/aaalbert_db";
		this.userName = "aaalbert";
		this.password = "6T1DbpT%";
	}
	
	//Getters
	String getUrl() {
		return url;
	}
	
	String getUserName() {
		return userName;
	}

	String getPassword() {
		return password;
	}
}
