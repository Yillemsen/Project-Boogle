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
			String query = "SELECT Titel FROM boek WHERE ISBN = '" + ISBN + "'";
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

	public int newBoekenkast(String libraryName, String bookCaseNr) {
		String query = "INSERT INTO boekenkast(`bibliotheeknaam`,`kastnummer`)" + "VALUES ('" + libraryName + "', '"
				+ bookCaseNr + "');";
		System.out.println(query);
		return (insert(query));

	}

	public int newFilmrek(String libraryName, String RackNr) {
		String query = "INSERT INTO filmrek(`bibliotheeknaam`,`reknummer`)" + "VALUES ('" + libraryName + "', '"
				+ RackNr + "');";
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

	public int newBoekheeftauteur(String ISBN, String auteur) {
		String query = "INSERT INTO boekheeftauteur(`isbn`,`Auteurnaam`)" + "VALUES ('" + ISBN + "', '" + auteur
				+ "');";
		System.out.println(query);
		return (insert(query));
	}

	public int newBoekenkastheeftboek(String rackNr, String library, String ISBN, String booknumber) {
		String query = "INSERT INTO boekenkastheeftboek(`kastnummer`,`bibliotheeknaam`,`isbn`,`boeknummer`)"
				+ "VALUES ('" + rackNr + "', '" + library + "','" + ISBN + "','" + booknumber + "');";
		System.out.println(query);
		return (insert(query));
	}

	public int newFilmrekheeftfilm(String rackNr, String library, String title, String movienumber) {
		String query = "INSERT INTO filmrekheeftfilm(`filmreknummer`,`bibliotheeknaam`,`filmtitel`,`filmnummer`)"
				+ "VALUES ('" + rackNr + "', '" + library + "','" + title + "','" + movienumber + "');";
		System.out.println(query);
		return (insert(query));
	}

	public int newFilmheeftacteur(String title, String acteur) {
		String query = "INSERT INTO filmheeftacteur(`FilmTitel`,`Acteurnaam`)" + "VALUES ('" + title + "', '" + acteur
				+ "');";
		System.out.println(query);
		return (insert(query));
	}

	public int newBoek(String ISBN, String title, String language, String releaseDate, String intTitle,
			String description, String genre, String image) {
		String query = "INSERT INTO boek(`ISBN`,`taal`,`titel`,`Datumuitgave`,`InternationaleTitel`,`genreNaam`,`Image`,`beschrijving`)"
				+ "VALUES ('" + ISBN + "', '" + language + "', '" + title + "', '" + releaseDate + "', '" + intTitle
				+ "', '" + genre + "', '" + image + "', '" + description + "');";
		System.out.println(query);
		return (insert(query));
	}

	public int newFilm(String title, String director, String releaseDate, String genreName, String image,
			String description) {
		String query = "INSERT INTO film(`titel`,`regisseur`,`datumPremiere`,`Beschrijving`,`genreNaam`,`Image`)"
				+ "VALUES ('" + title + "', '" + director + "', '" + releaseDate + "', '" + description + "', '"
				+ genreName + "', '" + image + "');";
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

	/**
	 * Method that couples a book and author(s)
	 * 
	 * @param ArrayList<AuteurModel>, String isbn
	 */

	public void insertBookHasAuthor(ArrayList<String> authors, String iSBN) {
		deleteAuthorsFromBook(iSBN);

		for (String authorName : authors) {
			String query = "INSERT INTO boekheeftauteur (AuteurNaam, ISBN) VALUES ('" + authorName + "', '" + iSBN
					+ "')";
			update(query);
		}
	}

	/**
	 * Method that inserts library, bookcase and isbn into linking table
	 * 
	 * @param        int bookCaseNr
	 * @param String libraryName
	 * @param String iSBN
	 */
	public void insertBookcaseHasBook(int bookCaseNr, String libraryName, String iSBN, String bookNr) {
		String query = "INSERT INTO boekenkastheeftboek (KastNummer, BibliotheekNaam, ISBN, BoekNummer) VALUES ('"
				+ bookCaseNr + "', '" + libraryName + "', '" + iSBN + "', '" + bookNr + "')";
		update(query);
	}

	// Methods that delete data from
	// database///////////////////////////////////////////////////////////////////

	public int deleteBoek(String isbn) {
		String query = "DELETE FROM boek WHERE isbn = '" + isbn + "';";
		System.out.println(query);
		return (update(query));
	}

	public int deleteFilm(String title) {
		String query = "DELETE FROM film WHERE titel = '" + title + "';";
		System.out.println(query);
		return (update(query));
	}

	public int deleteBoekenkast(String libraryName, String BookCaseNr) {
		String query = "DELETE FROM boekenkast WHERE kastnummer = '" + BookCaseNr + "' AND bibliotheeknaam  = '"
				+ libraryName + "';";
		System.out.println(query);
		return (update(query));
	}

	public int deleteFilmrek(String libraryName, String RackNr) {
		String query = "DELETE FROM filmrek WHERE reknummer = '" + RackNr + "' AND bibliotheeknaam  = '" + libraryName
				+ "';";
		System.out.println(query);
		return (update(query));
	}

	public int deleteBibliotheek(String name) {
		String query = "DELETE FROM bibliotheek WHERE naam = '" + name + "';";
		System.out.println(query);
		return (update(query));
	}

	public int deleteActeur(String name) {
		String query = "DELETE FROM acteur WHERE naam = '" + name + "';";
		System.out.println(query);
		return (update(query));
	}

	/**
	 * Method that deletes all authors that are linked to a specific bookISBN
	 * 
	 * @param String iSBN
	 * @return int
	 */
	public int deleteAuthorsFromBook(String iSBN) {
		String query = "DELETE FROM boekheeftauteur WHERE ISBN ='" + iSBN + "'";
		return update(query);
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
		String query = "SELECT * \r\n" + "FROM boek b\r\n"
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

	public int deleteAuteur(String name) {
		String query = "DELETE FROM auteur WHERE naam = '" + name + "';";
		System.out.println(query);
		return (update(query));
	}

	/**
	 * Method that returns an arraylist with all existing boekModels
	 * 
	 * @return Arraylist<BoekModel>
	 */
	public ArrayList<BoekModel> getAllBooks() {
		String query = "SELECT * FROM boek";
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

			bookcase.setBookCaseNr(resultSet.getInt("KastNummer"));
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

	public int doesBookExistInLibrary(String iSBN, String library) {
		String query = "SELECT * FROM boekenkastheeftboek WHERE ISBN='" + iSBN + "' AND BibliotheekNaam='" + library
				+ "'";
		ResultSet resultSet = select(query);
		try {
			resultSet.next();

			if (resultSet.getString("KastNummer") != null) {
				return 0;
			}

		} catch (SQLException e) {
			rmConnection(resultSet);
			e.printStackTrace();
		}
		rmConnection(resultSet);

		return 1;
	}

	// Methods that select a list of models from database
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * Method that returns an arraylist with all existing BibliotheekModels
	 * 
	 * @return ArrayList<BibliotheekModel>
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
	////////////////////////////////

	////////////////////////////
	/**
	 * Method that returns an arraylist with FilmrekModels
	 * 
	 * @return
	 */
	public ArrayList<BoekenkastModel> getAllBoekenkasten() {
		String query = "select distinct bibliotheeknaam from boekenkast";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllBoekenkasten(resultSet);
	}

	/**
	 * Method that fills an arraylist with actormodels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<FilnrekModel>
	 */
	private ArrayList<BoekenkastModel> rowToGetAllBoekenkasten(ResultSet rowSet) {
		ArrayList<BoekenkastModel> allBoekenkasten = new ArrayList<>();

		try {
			while (rowSet.next()) {
				BoekenkastModel boekenkastModel = new BoekenkastModel();

				boekenkastModel.setLibraryName(rowSet.getString("BibliotheekNaam"));
				// boekenkastModel.setBookCaseNr(rowSet.getInt("KastNummer"));

				allBoekenkasten.add(boekenkastModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allBoekenkasten;
	}
	////////////////////////////////

	////////////////////////////
	/**
	 * Method that returns an arraylist with FilmrekModels
	 * 
	 * @return
	 */
	public ArrayList<FilmrekModel> getAllFilmrekkenvalue(String libraryName) {
		String query = "SELECT * FROM `filmrek` WHERE BibliotheekNaam = '" + libraryName + "'";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllFilmrekkenvalue(resultSet);
	}

	/**
	 * Method that fills an arraylist with actormodels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<FilnrekModel>
	 */
	private ArrayList<FilmrekModel> rowToGetAllFilmrekkenvalue(ResultSet rowSet) {
		ArrayList<FilmrekModel> allFilmrekkenvalue = new ArrayList<>();

		try {
			while (rowSet.next()) {
				FilmrekModel filmrekModel = new FilmrekModel();

				filmrekModel.setLibraryName(rowSet.getString("BibliotheekNaam"));
				filmrekModel.setRackNr(rowSet.getInt("RekNummer"));

				allFilmrekkenvalue.add(filmrekModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allFilmrekkenvalue;
	}

	////////////////////////////

	////////////////////////////
	/**
	 * Method that returns an arraylist with FilmrekModels
	 * 
	 * @return
	 */
	public ArrayList<FilmModel> getAllFilms() {
		String query = "SELECT * FROM film";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllFilms(resultSet);
	}

	/**
	 * Method that fills an arraylist with actormodels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<FilnrekModel>
	 */
	private ArrayList<FilmModel> rowToGetAllFilms(ResultSet rowSet) {
		ArrayList<FilmModel> allFilms = new ArrayList<>();

		try {
			while (rowSet.next()) {
				FilmModel filmModel = new FilmModel();

				filmModel.setTitle(rowSet.getString("titel"));
				filmModel.setDirector(rowSet.getString("regisseur"));
				filmModel.setReleaseDate(rowSet.getString("datumpremiere"));
				filmModel.setDescription(rowSet.getString("beschrijving"));
				filmModel.setgenreName(rowSet.getString("genrenaam"));
				filmModel.setImage(rowSet.getString("Image"));

				allFilms.add(filmModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allFilms;
	}

	////////////////////////////
	/**
	 * Method that returns an arraylist with FilmrekModels
	 * 
	 * @return
	 */
	public ArrayList<BoekenkastModel> getAllBoekenkastvalue(String libraryName) {
		String query = "SELECT * FROM `boekenkast` WHERE BibliotheekNaam = '" + libraryName + "'";
		ResultSet resultSet = select(query);

		if (goToFirstRow(select(query)) == null) {
			rmConnection(resultSet);
			return null;
		}

		return rowToGetAllBoekenkastvalue(resultSet);
	}

	/**
	 * Method that fills an arraylist with actormodels and returns them
	 * 
	 * @param rowSet
	 * @return ArrayList<FilnrekModel>
	 */
	private ArrayList<BoekenkastModel> rowToGetAllBoekenkastvalue(ResultSet rowSet) {
		ArrayList<BoekenkastModel> allBoekenkastvalue = new ArrayList<>();

		try {
			while (rowSet.next()) {
				BoekenkastModel boekenkastModel = new BoekenkastModel();

				boekenkastModel.setLibraryName(rowSet.getString("BibliotheekNaam"));
				boekenkastModel.setBookCaseNr(rowSet.getInt("KastNummer"));

				allBoekenkastvalue.add(boekenkastModel);
			}
		} catch (SQLException e) {
			rmConnection(rowSet);
			e.printStackTrace();
		}
		rmConnection(rowSet);

		return allBoekenkastvalue;
	}

	////////////////////
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

	public BoekenkastModel getBoekenkastFromName(String libraryName) {
		BoekenkastModel library = new BoekenkastModel();
		String query = "SELECT * FROM boekenkast WHERE bibliotheeknaam= '" + libraryName + "'";
		ResultSet resultSet = select(query);

		try {
			resultSet.next();

			library.setLibraryName(resultSet.getString("BibliotheekNaam"));
			library.setBookCaseNr(resultSet.getInt("KastNummer"));

		} catch (SQLException e) {
			rmConnection(resultSet);
			e.printStackTrace();
		}
		rmConnection(resultSet);

		return library;
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

	public int updateBookFromISBN(BoekModel book) {
		String query;
		String description = book.getDescription();
		String releaseDate = book.getReleaseDate();
		String genre = book.getGenre();
		String image = book.getImage();
		String intTitle = book.getIntTitle();
		String iSBN = book.getISBN();
		String language = book.getLanguage();
		String title = book.getTitle();

		if (image != null) {

			query = "UPDATE boek SET Beschrijving='" + description + "', DatumUitgave='" + releaseDate + "', "
					+ "GenreNaam='" + genre + "', Image='" + image + "', InternationaleTitel='" + intTitle + "', Taal='"
					+ language + "', Titel='" + title + "' WHERE ISBN='" + iSBN + "'";
		} else {
			query = "UPDATE boek SET Beschrijving='" + description + "', DatumUitgave='" + releaseDate + "', Image='"
					+ image + "', InternationaleTitel='" + intTitle + "', Taal='" + language + "', Titel='" + title
					+ "' WHERE ISBN='" + iSBN + "'";
		}
		return (update(query));
	}

	public int updateBookCaseHasBook(int bookCaseNr, String library, String iSBN) {
		String query = "UPDATE boekenkastheeftboek SET KastNummer= '" + bookCaseNr + "' WHERE BibliotheekNaam='"
				+ library + "' AND ISBN='" + iSBN + "'";
		return (update(query));
	}
}