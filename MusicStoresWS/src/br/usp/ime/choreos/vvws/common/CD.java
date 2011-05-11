package br.usp.ime.choreos.vvws.common;

public class CD {
	String title;
	String artist;
	String genre;
	Integer numberOfTracks;
	
	public CD(String title, String artist, String genre, Integer numberOfTracks) {
	        this.title = title;
	        this.artist = artist;
	        this.genre = genre;
	        this.numberOfTracks = numberOfTracks;
        }

	public CD() {
        }
	
	public String getTitle() {
        	return title;
        }
	public void setTitle(String title) {
        	this.title = title;
        }
	public String getArtist() {
        	return artist;
        }
	public void setArtist(String artist) {
        	this.artist = artist;
        }
	public String getGenre() {
        	return genre;
        }
	public void setGenre(String genre) {
        	this.genre = genre;
        }
	public Integer getNumberOfTracks() {
        	return numberOfTracks;
        }
	public void setNumberOfTracks(Integer numberOfTracks) {
        	this.numberOfTracks = numberOfTracks;
        }

	
}
