package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private Connection connection;
	private Statement statement;
	private DbConnection dbc = new DbConnection();

	/**
	 * Creates new connection
	 * 
	 * @return Connection connection
	 */
	private Connection getConnection() {
		try {

			connection = DriverManager.getConnection(dbc.getUrl(), dbc.getUserName(), dbc.getPassword());
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("The application can't create a connection to the server");
		}
	}

	/**
	 * Closes connection
	 */
	private void close() {
		if (statement != null) {
			rmConnection(null);
		}
		if (connection != null) {
			rmConnection(null);
		}
	}

	/**
	 * Closes result set
	 * 
	 * @param lResultSet ResultSet
	 */

	private void rmConnection(ResultSet lResultSet) {
		try {
			try {
				if (lResultSet != null || !connection.isClosed()) {
					connection.close();

					if (lResultSet != null && !lResultSet.isClosed()) {
						lResultSet.close();
					}
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executes select statement
	 * 
	 * @param query String containing query
	 * @return resultset
	 */
	private ResultSet select(String query) {
		ResultSet resultSet = null;

		try {
			getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			close();
		}
		return resultSet;
	}

	/**
	 * Update or insert
	 * 
	 * @param query String containing query
	 * @return resultset
	 */

	private int executeUpsert(String query) {
		try {
			getConnection();
			statement = connection.createStatement();
			int rowsAffected = statement.executeUpdate(query);

			connection.close();

			return rowsAffected;
		} catch (SQLException e) {
			e.printStackTrace();
			close();
		}
		return 0;
	}

	/**
	 * Executes insert statement
	 * 
	 * @param query String containing query
	 * @return method call
	 */

	private int insert(String query) {
		return executeUpsert(query);
	}

	/**
	 * Executes update statement
	 * 
	 * @param query String containing query
	 * @return method call
	 */

	private int update(String query) {
		return executeUpsert(query);
	}

	/**
	 * Get first row from dataset/resultset
	 * 
	 * @param rowSet resultset
	 * @return null||error/stacktrace
	 */

	private ResultSet goToFirstRow(ResultSet rowSet) {
		try {
			if (rowSet.next()) {
				rowSet.first();
				return rowSet;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getBoek(String ISBN) {
		try {
			String query = "SELECT Titel FROM Boek WHERE ISBN = '" + ISBN + "'";
			ResultSet resultSet = select(query);

			if (goToFirstRow(resultSet) == null) {
				rmConnection(resultSet);
				return null;
			}

			return resultSet.getString("Titel");

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
