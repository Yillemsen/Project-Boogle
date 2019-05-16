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
		this.url = "";
		this.userName = "";
		this.password = "";
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
