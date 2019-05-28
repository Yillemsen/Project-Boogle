package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	private Connection connection;
	private Statement statement;
	private DbConnection dbc = new DbConnection();

	// Methods that are used basically anywhere
	// ///////////////////////////////////////////////////////////////////////////
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

	public ResultSet getData(String strSQL) {
		ResultSet result = null;
		try {
			Statement stmt = getConnection().createStatement();
			result = stmt.executeQuery(strSQL);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	public int executeDML(String strSQL) {
		int result = 0;
		try {
			Statement stmt = getConnection().createStatement();
			result = stmt.executeUpdate(strSQL);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

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

	/**
	 * Method that returns booktitle from given ISBN
	 * 
	 * @param ISBN
	 * @return String booktitle
	 */

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

	/**
	 * 
	 * public void newBibliotheek(String name, String adres, String location, String
	 * cell) { String query = "INSERT INTO
	 * bibliotheek(`naam`,`adres`,`plaats`,`telefoon`)" + "VALUES ('" + name + "',
	 * '" + adres + "', '" + location + "', '" + cell + "');";
	 * System.out.println(query); insert(query);
	 * 
	 * }
	 * 
	 * public void newActeur(String name, String birth, String death) { String query
	 * = "INSERT INTO acteur(`naam`,`geboortedatum`,`overlijdensdatum`)" + "VALUES
	 * ('" + name + "', '" + birth + "', '" + death + "');";
	 * System.out.println(query); insert(query); }
	 * 
	 * public void newAuteur(String name, String birth, String death) { String query
	 * = "INSERT INTO auteur(`naam`,`geboortedatum`,`overlijdensdatum`)" + "VALUES
	 * ('" + name + "', '" + birth + "', '" + death + "');";
	 * System.out.println(query); insert(query); }
	 * 
	 * public void newBoek(String ISBN, String title, String language, String
	 * releaseDate, String intTitle, String description, String genre, String image)
	 * { String query = "INSERT INTO
	 * boek(`ISBN`,`taal`,`title`,`Datumuitgave`,`InternationaleTitel`,`genreNaam`,`Image`,`beschrijving`)"
	 * + "VALUES ('" + ISBN + "', '" + language + "', '" + title + "', '" +
	 * releaseDate + "', '" + intTitle + "', '" + genre + "', '" + description + "',
	 * '" + image + "');"; System.out.println(query); insert(query); }
	 * 
	 * public void deleteBoek(String isbn) { String query = "DELETE FROM boek WHERE
	 * isbn = '" + isbn + "';"; System.out.println(query); update(query); }
	 * 
	 * public void deleteBibliotheek(String name) { String query = "DELETE FROM
	 * bibliotheek WHERE naam = '" + name + "';"; System.out.println(query);
	 * update(query); }
	 */

	/**
	 * Method that returns an arraylist with boekmodels from specific library
	 * 
	 * @param name
	 * @return ArrayList<BoekModel>
	 */

	// Methods that insert data into
	// database///////////////////////////////////////////////////////////////

	public int newBibliotheek(String name, String adres, String location, String cell) {
		String query = "INSERT INTO bibliotheek(`naam`,`adres`,`plaats`,`telefoon`)" + "VALUES ('" + name + "', '"
				+ adres + "', '" + location + "', '" + cell + "');";
		System.out.println(query);
		return (insert(query));

	}

	public int newActeur(String name, String birth, String death) {
		String query = "INSERT INTO acteur(`naam`,`geboortedatum`,`overlijdensdatum`)" + "VALUES ('" + name + "', '"
				+ birth + "', '" + death + "');";
		System.out.println(query);
		return (insert(query));
	}

	public int newAuteur(String name, String birth, String death) {
		String query = "INSERT INTO auteur(`naam`,`geboortedatum`,`overlijdensdatum`)" + "VALUES ('" + name + "', '"
				+ birth + "', '" + death + "');";
		System.out.println(query);
		return (insert(query));
	}

	public int newBoek(String ISBN, String title, String language, String releaseDate, String intTitle,
			String description, String genre, String image) {
		String query = "INSERT INTO boek(`ISBN`,`taal`,`title`,`Datumuitgave`,`InternationaleTitel`,`genreNaam`,`Image`,`beschrijving`)"
				+ "VALUES ('" + ISBN + "', '" + language + "', '" + title + "', '" + releaseDate + "', '" + intTitle
				+ "', '" + genre + "', '" + description + "', '" + image + "');";
		System.out.println(query);
		return (insert(query));
	}

	/**
	 * Method that inserts bookcaseNr into database
	 * 
	 * @param String libraryName
	 * @param String bookCaseNr
	 */
	public void insertBookCase(String libraryName, String bookCaseNr) {
		String query = "INSERT INTO boekenkast (BibliotheekNaam, KastNummer) VALUES (" + libraryName + ", " + bookCaseNr
				+ ")";
		insert(query);
	}

	/**
	 * Method that inserts movieRackNr into database
	 * 
	 * @param String libraryName
	 * @param String movieRackNr
	 */
	public void insertMovieRack(String libraryName, String movieRackNr) {
		String query = "INSERT INTO filmrek (BibliotheekNaam, RekNummer) VALUES (" + libraryName + ", " + movieRackNr
				+ ")";
		insert(query);
	}

	// Methods that delete data from
	// database///////////////////////////////////////////////////////////////////

	public int deleteBoek(String isbn) {
		String query = "DELETE FROM boek WHERE isbn = '" + isbn + "';";
		System.out.println(query);
		return (update(query));
	}

	public int deleteBibliotheek(String name) {
		String query = "DELETE FROM bibliotheek WHERE naam = '" + name + "';";
		System.out.println(query);
		return (update(query));
	}

	public int deleteAuteur(String name) {
		String query = "DELETE FROM auteur WHERE naam = '" + name + "';";
		System.out.println(query);
		return (update(query));
	}

	public int deleteActeur(String name) {
		String query = "DELETE FROM acteur WHERE naam = '" + name + "';";
		System.out.println(query);
		return (update(query));
	}

	// Methods that select all books from a
	// table////////////////////////////////////////////////////////////////////

	/**
	 * Method that gets all books from a given library
	 * 
	 * @param String name
	 * @return ArrayList<BoekModel>
	 */
	public ArrayList<BoekModel> getAllBooksFromLibary(String name) {
		String query = "SELECT * \r\n" + "FROM Boek b\r\n"
				+ "INNER JOIN boekenkastheeftboek bkhb ON b.ISBN=bkhb.ISBN\r\n"
				+ "INNER JOIN boekenkast bk ON bkhb.KastNummer=bk.KastNummer\r\n"
				+ "INNER JOIN bibliotheek bieb ON bk.BibliotheekNaam=bieb.Naam\r\n" + "WHERE bieb.Naam \r\n" + "= '"
				+ name + "'";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}
		return rowToGetAllBooks(resultSet);
	}

	/**
	 * Method that returns an arraylist with all existing boekModels
	 * 
	 * @return Arraylist<BoekModel>
	 */
	public ArrayList<BoekModel> getAllBooks() {
		String query = "SELECT * FROM Boek";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllBooks(resultSet);
	}

	/**
	 * Method that fills arraylist with BoekModels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<BoekModel>
	 */
	private ArrayList<BoekModel> rowToGetAllBooks(ResultSet rowSet) {
		ArrayList<BoekModel> allBooks = new ArrayList<>();

		try {
			while (rowSet.next()) {
				BoekModel boekmodel = new BoekModel();

				boekmodel.setISBN(rowSet.getString("ISBN"));
				boekmodel.setTitle(rowSet.getString("Titel"));
				boekmodel.setLanguage(rowSet.getString("Taal"));
				boekmodel.setReleaseDate(rowSet.getString("DatumUitgave"));
				boekmodel.setIntTitle(rowSet.getString("InternationaleTitel"));
				boekmodel.setGenre(rowSet.getString("GenreNaam"));
				boekmodel.setImage(rowSet.getString("Image"));
				boekmodel.setDescription(rowSet.getString("Beschrijving"));
				allBooks.add(boekmodel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allBooks;
	}

	// Methods that select a specific model from the
	// database//////////////////////////////////////////////////////////////////////

	public BoekenkastModel getBookcaseFromISBN(String iSBN, String libraryName) {
		BoekenkastModel bookcase = new BoekenkastModel();
		String query = "SELECT * FROM boekenkastheeftboek WHERE BibliotheekNaam ='" + libraryName + "'" + " AND ISBN ='"
				+ iSBN + "'";
		ResultSet resultSet = select(query);

		try {
			resultSet.next();

			bookcase.setBookCaseNr(resultSet.getInt("BoekNummer"));
			bookcase.setLibraryName(resultSet.getString("BibliotheekNaam"));

		} catch (SQLException e) {
			rmConnection(resultSet);
			e.printStackTrace();
		}
		return bookcase;
	}

	public BoekModel getBookFromISBN(String iSBN) {
		BoekModel book = new BoekModel();
		String query = "SELECT * FROM boek WHERE ISBN = '" + iSBN + "'";
		ResultSet resultSet = select(query);

		try {
			resultSet.next();

			book.setDescription(resultSet.getString("Beschrijving"));
			book.setReleaseDate(resultSet.getString("DatumUitgave"));
			book.setGenre(resultSet.getString("GenreNaam"));
			book.setImage(resultSet.getString("Image"));
			book.setIntTitle(resultSet.getString("InternationaleTitel"));
			book.setISBN(resultSet.getString("ISBN"));
			book.setLanguage(resultSet.getString("Taal"));
			book.setTitle(resultSet.getString("Titel"));

		} catch (SQLException e) {
			rmConnection(resultSet);
			e.printStackTrace();
		}
		return book;
	}

	/**
	 * Method that selects libraryModel from database
	 * 
	 * @param name
	 * @return BibliotheekModel
	 */
	public BibliotheekModel getLibraryFromName(String name) {
		BibliotheekModel library = new BibliotheekModel();
		String query = "SELECT * FROM bibliotheek WHERE Naam= '" + name + "'";
		ResultSet resultSet = select(query);

		try {
			resultSet.next();

			library.setName(resultSet.getString("Naam"));
			library.setLocation(resultSet.getString("Plaats"));
			library.setAdres(resultSet.getString("Adres"));
			library.setCell(resultSet.getString("Telefoon"));

		} catch (SQLException e) {
			rmConnection(resultSet);
			e.printStackTrace();
		}
		rmConnection(resultSet);

		return library;
	}

	/**
	 * Method that selects MovierackModel from database
	 * 
	 * @param libraryName
	 * @return
	 */
	public FilmrekModel getFilmrekFromName(String libraryName) {
		FilmrekModel library = new FilmrekModel();
		String query = "SELECT * FROM filmrek WHERE bibliotheeknaam= '" + libraryName + "'";
		ResultSet resultSet = select(query);

		try {
			resultSet.next();

			library.setLibraryName(resultSet.getString("BibliotheekNaam"));
			library.setRackNr(resultSet.getInt("RekNummer"));

		} catch (SQLException e) {
			rmConnection(resultSet);
			e.printStackTrace();
		}
		rmConnection(resultSet);

		return library;
	}

	/**
	 * Method that gets actorModel from database
	 * 
	 * @param name
	 * @return ActeurModel
	 */
	public ActeurModel getActorFromName(String name) {
		ActeurModel actor = new ActeurModel();
		String query = "SELECT * FROM acteur WHERE Naam= '" + name + "'";
		ResultSet resultSet = select(query);

		try {
			resultSet.next();

			actor.setName(resultSet.getString("Naam"));
			actor.setBirth(resultSet.getString("GeboorteDatum"));
			actor.setDeath(resultSet.getString("OverlijdensDatum"));

		} catch (SQLException e) {
			rmConnection(resultSet);
			e.printStackTrace();
		}
		rmConnection(resultSet);

		return actor;
	}

	/**
	 * Method that gets authormodel from database
	 * 
	 * @param name
	 * @return AuthorModel
	 */
	public AuteurModel getAuthorFromName(String name) {
		AuteurModel author = new AuteurModel();
		String query = "SELECT * FROM auteur WHERE Naam= '" + name + "'";
		ResultSet resultSet = select(query);

		try {
			resultSet.next();

			author.setName(resultSet.getString("Naam"));
			author.setBirth(resultSet.getString("GeboorteDatum"));
			author.setDeath(resultSet.getString("OverlijdensDatum"));

		} catch (SQLException e) {
			rmConnection(resultSet);
			e.printStackTrace();
		}
		rmConnection(resultSet);

		return author;
	}

	/**
	 * Method that gets genremodel from database
	 * 
	 * @param name
	 * @return AuthorModel
	 */
	public GenreModel getGenreFromName(String genreName) {
		GenreModel genre = new GenreModel();
		String query = "SELECT * FROM genre WHERE Naam= '" + genreName + "'";
		ResultSet resultSet = select(query);

		try {
			resultSet.next();

			genre.setGenreName(resultSet.getString("genreNaam"));
			genre.setDescription(resultSet.getString("Omschrijving"));

		} catch (SQLException e) {
			rmConnection(resultSet);
			e.printStackTrace();
		}
		rmConnection(resultSet);

		return genre;
	}

	// Methods that select a list of models from database
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * Method that returns an arraylist with all existing BibliotheekModels
	 * 
	 * @return
	 */
	public ArrayList<BibliotheekModel> getAllLibraries() {
		String query = "SELECT * FROM `bibliotheek`";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllLibraries(resultSet);
	}

	/**
	 * Method that fills arraylist with BibliotheekModels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<BibliotheekModel>
	 */
	private ArrayList<BibliotheekModel> rowToGetAllLibraries(ResultSet rowSet) {
		ArrayList<BibliotheekModel> allLibraries = new ArrayList<>();

		try {
			while (rowSet.next()) {
				BibliotheekModel libraryModel = new BibliotheekModel();

				libraryModel.setName(rowSet.getString("Naam"));
				libraryModel.setAdres(rowSet.getString("Adres"));
				libraryModel.setLocation(rowSet.getString("Plaats"));
				libraryModel.setCell(rowSet.getString("Telefoon"));

				allLibraries.add(libraryModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allLibraries;
	}

	/**
	 * Method that returns an arraylist with Filmmodels
	 * 
	 * @return ArrayList<FilmModel>
	 */
	public ArrayList<FilmModel> getAllMovies() {
		String query = "SELECT * FROM film";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllMovies(resultSet);
	}

	/**
	 * Method that fills arraylist with FilmModels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<FilmModel>
	 */
	private ArrayList<FilmModel> rowToGetAllMovies(ResultSet rowSet) {
		ArrayList<FilmModel> allMovies = new ArrayList<>();

		try {
			while (rowSet.next()) {
				FilmModel movieModel = new FilmModel();

				movieModel.setTitle(rowSet.getString("Titel"));

				allMovies.add(movieModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allMovies;
	}

	/**
	 * Method that returns an arraylist with ActorModels
	 * 
	 * @return
	 */
	public ArrayList<ActeurModel> getAllActors() {
		String query = "SELECT * FROM acteur";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllActors(resultSet);
	}

	/**
	 * Method that returns an arraylist with FilmrekModels
	 * 
	 * @return
	 */
	public ArrayList<FilmrekModel> getAllFilmrekken() {
		String query = "select distinct bibliotheeknaam from filmrek";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllFilmrekken(resultSet);
	}

	/**
	 * Method that fills an arraylist with actormodels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<FilnrekModel>
	 */
	private ArrayList<FilmrekModel> rowToGetAllFilmrekken(ResultSet rowSet) {
		ArrayList<FilmrekModel> allFilmrekken = new ArrayList<>();

		try {
			while (rowSet.next()) {
				FilmrekModel filmrekModel = new FilmrekModel();

				filmrekModel.setLibraryName(rowSet.getString("BibliotheekNaam"));
				// filmrekModel.setRackNr(rowSet.getInt("RekNummer"));

				allFilmrekken.add(filmrekModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allFilmrekken;
	}

	/**
	 * Method that returns an arraylist with FilmrekModels
	 * 
	 * @return
	 */
	public ArrayList<FilmrekModel> getAllFilmrekkenn(String libraryName) {
		String query = "SELECT * FROM `filmrek` WHERE BibliotheekNaam = '" + libraryName + "'";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllFilmrekkenn(resultSet);
	}

	/**
	 * Method that fills an arraylist with actormodels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<FilnrekModel>
	 */
	private ArrayList<FilmrekModel> rowToGetAllFilmrekkenn(ResultSet rowSet) {
		ArrayList<FilmrekModel> allFilmrekkenn = new ArrayList<>();

		try {
			while (rowSet.next()) {
				FilmrekModel filmrekModel = new FilmrekModel();

				filmrekModel.setLibraryName(rowSet.getString("BibliotheekNaam"));
				filmrekModel.setRackNr(rowSet.getInt("RekNummer"));

				allFilmrekkenn.add(filmrekModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allFilmrekkenn;
	}

	/**
	 * Method that fills an arraylist with actormodels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<ActeurModel>
	 */
	private ArrayList<ActeurModel> rowToGetAllActors(ResultSet rowSet) {
		ArrayList<ActeurModel> allActors = new ArrayList<>();

		try {
			while (rowSet.next()) {
				ActeurModel actorModel = new ActeurModel();

				actorModel.setName(rowSet.getString("Naam"));
				actorModel.setBirth(rowSet.getString("GeboorteDatum"));
				actorModel.setDeath(rowSet.getString("OverlijdensDatum"));

				allActors.add(actorModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allActors;
	}

	/**
	 * Method that returns an arraylist with AuthorModels
	 * 
	 * @return
	 */
	public ArrayList<AuteurModel> getAllAuthors() {
		String query = "SELECT * FROM auteur";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllAuthors(resultSet);
	}

	/**
	 * Method that returns an arraylist with AuthorModels based on given booktitle
	 * 
	 * @return
	 */
	public ArrayList<AuteurModel> getAllAuthorsFromBook(String iSBN) {
		String query = "SELECT * FROM auteur a INNER JOIN boekheeftauteur bha ON a.Naam=bha.AuteurNaam WHERE bha.ISBN="
				+ "'" + iSBN + "'";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllAuthors(resultSet);
	}

	/**
	 * Method that fills an arraylist with actormodels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<ActeurModel>
	 */
	private ArrayList<AuteurModel> rowToGetAllAuthors(ResultSet rowSet) {
		ArrayList<AuteurModel> allAuthors = new ArrayList<>();

		try {
			while (rowSet.next()) {
				AuteurModel authorModel = new AuteurModel();

				authorModel.setName(rowSet.getString("Naam"));
				authorModel.setBirth(rowSet.getString("GeboorteDatum"));
				authorModel.setDeath(rowSet.getString("OverlijdensDatum"));

				allAuthors.add(authorModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allAuthors;
	}

	/**
	 * Method that returns all bookcases from a library
	 * 
	 * @param name
	 * @return ArrayList<BoekenkastModel>
	 */

	public ArrayList<BoekenkastModel> getAllBookCasesFromLibrary(String name) {
		String query = "SELECT * FROM boekenkast WHERE BibliotheekNaam = '" + name + "'";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllBookCases(resultSet);
	}

	/**
	 * Method that fills an arraylist with bookcasemodels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<BoekenkastModel>
	 */

	private ArrayList<BoekenkastModel> rowToGetAllBookCases(ResultSet rowSet) {
		ArrayList<BoekenkastModel> allBookCases = new ArrayList<>();

		try {
			while (rowSet.next()) {
				BoekenkastModel bookCase = new BoekenkastModel();

				bookCase.setBookCaseNr(rowSet.getInt("KastNummer"));
				bookCase.setLibraryName(rowSet.getString("BibliotheekNaam"));

				allBookCases.add(bookCase);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allBookCases;
	}

	/**
	 * Method that fills an arraylist with actormodels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<ActeurModel>
	 */
	private ArrayList<GenreModel> rowToGetAllGenres(ResultSet rowSet) {
		ArrayList<GenreModel> allGenre = new ArrayList<>();

		try {
			while (rowSet.next()) {
				GenreModel genreModel = new GenreModel();

				genreModel.setGenreName(rowSet.getString("Genrenaam"));
				genreModel.setDescription(rowSet.getString("omschrijving"));

				allGenre.add(genreModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allGenre;
	}

	/**
	 * Method that returns an arraylist with AuthorModels
	 * 
	 * @return
	 */
	public ArrayList<GenreModel> getAllGenres() {
		String query = "SELECT * FROM genre";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllGenres(resultSet);
	}

	// Methods that update models in the
	// database///////////////////////////////////////////////////////////////////////
	/**
	 * Method that updates the bibliotheek entity in database
	 * 
	 * @param BibliotheekModel library, String oldname
	 */
	public int updateLibrary(BibliotheekModel library, String oldName) {
		String name = library.getName();
		String location = library.getLocation();
		String adres = library.getAdres();
		String cell = library.getCell();

		String query = "UPDATE bibliotheek SET Naam='" + name + "', Plaats='" + location + "', Adres='" + adres
				+ "', Telefoon='" + cell + "' WHERE Naam='" + oldName + "'";
		return (update(query));
	}

	/**
	 * Method that updates the acteur entity in database
	 * 
	 * @param ActeurModel actor, String oldName
	 */
	public int updateActor(ActeurModel actor, String oldName) {
		String name = actor.getName();
		String dob = actor.getBirth();
		String dod = actor.getDeath();

		String query = "UPDATE acteur SET Naam='" + name + "', GeboorteDatum='" + dob + "', OverlijdensDatum='" + dod
				+ "' WHERE Naam='" + oldName + "'";
		return (update(query));
	}

	/**
	 * Method that updates the auteur entity in database
	 * 
	 * @param ActeurModel author, String oldName
	 */
	public int updateAuthor(AuteurModel author, String oldName) {
		String name = author.getName();
		String dob = author.getBirth();
		String dod = author.getDeath();

		String query = "UPDATE auteur SET Naam='" + name + "', GeboorteDatum='" + dob + "', OverlijdensDatum='" + dod
				+ "' WHERE Naam='" + oldName + "'";
		return (update(query));
	}
}