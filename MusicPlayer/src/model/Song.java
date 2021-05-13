
package model;

public class Song {
 
    private String songName = null;
    
    public Song(String songName) {
        this.songName = songName.substring(0,songName.length() - 4).replaceAll("[0-9\\_]", " ");
    }
    public Song() {}

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
