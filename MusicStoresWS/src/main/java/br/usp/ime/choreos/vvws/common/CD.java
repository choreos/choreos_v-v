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

	@Override
        public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((artist == null) ? 0 : artist.hashCode());
	        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
	        result = prime
	                                        * result
	                                        + ((numberOfTracks == null) ? 0 : numberOfTracks
	                                                                        .hashCode());
	        result = prime * result + ((title == null) ? 0 : title.hashCode());
	        return result;
        }

	@Override
        public boolean equals(Object obj) {
	        if (this == obj)
		        return true;
	        if (obj == null)
		        return false;
	        if (getClass() != obj.getClass())
		        return false;
	        CD other = (CD) obj;
	        if (artist == null) {
		        if (other.artist != null)
			        return false;
	        } else if (!artist.equals(other.artist))
		        return false;
	        if (genre == null) {
		        if (other.genre != null)
			        return false;
	        } else if (!genre.equals(other.genre))
		        return false;
	        if (numberOfTracks == null) {
		        if (other.numberOfTracks != null)
			        return false;
	        } else if (!numberOfTracks.equals(other.numberOfTracks))
		        return false;
	        if (title == null) {
		        if (other.title != null)
			        return false;
	        } else if (!title.equals(other.title))
		        return false;
	        return true;
        }
	
	

	
}
