
package model;


import java.nio.file.Path;


public class MusicForPlay {
    
    private Song song = null;
    private Path path = null;


    public MusicForPlay(Song song, Path path) {
        this.song = song;
        this.path = path;
    }
    public MusicForPlay() {}

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return song.getSongName();
    }

    
    
    
    
   

   
    
    
}
