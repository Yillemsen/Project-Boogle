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
		this.url = "jdbc:mysql://37.48.109.246:3306/ardjan";
		this.userName = "webshop";
		this.password = "!webshop!";
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
