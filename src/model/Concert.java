package model;


public class Concert extends Event{

    private String artistName;
    private String musicGenre;

    public Concert(String name, Location location, String artistName, String musicGenre) {
        super(name, location);
        this.artistName = artistName;
        this.musicGenre = musicGenre;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getMusicGenre() {
        return musicGenre;
    }

    public void setMusicGenre(String musicGenre) {
        this.musicGenre = musicGenre;
    }

    @Override
    public String toString() {
        return super.toString() + " / " + artistName + " / " + musicGenre + "\n";
    }
}
