package model;

public class BoekModel {
	private String ISBN, title, language, releaseDate, intTitle, description, image, genre;
	private int bookNr;
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getIntTitle() {
		return intTitle;
	}
	public void setIntTitle(String intTitle) {
		this.intTitle = intTitle;
	}
	public int getBookNr() {
		return bookNr;
	}
	public void setBookNr(int bookNr) {
		this.bookNr = bookNr;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
         public String getGenre() {
        return genre;
         }

        public void setGenre(String genre) {
        this.genre = genre;
    }
}
