package model;

public class FilmModel {
	private String title, director, releaseDate, description, genreName, image;
	private int movieNr;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMovieNr() {
		return movieNr;
	}
	public void setMovieNr(int movieNr) {
		this.movieNr = movieNr;
	}
	public String getgenreName() {
		return genreName;
	}
	public void setgenreName(String genreName) {
		this.genreName = genreName;
	}
	public String getImage() {
		return image;
	}
	public void setgenreImage(String image) {
		this.image = image;
	}

}
