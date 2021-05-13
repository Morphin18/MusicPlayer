package controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import view.MainFrame;

public class Sound {

    private final Controller controller;
    private final Player player;
    private FloatControl volumeControl;
    private Port outline;


    public Sound(FileInputStream fis) throws JavaLayerException {
        this.controller = new Controller();
        this.player = new Player(new BufferedInputStream(fis));
        try {
            this.outline = (Port) AudioSystem.getLine(Port.Info.SPEAKER);
            outline.open();
            this.volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void play() {
        int i = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        player.play();

                    if (player.isComplete()) {
                        controller.next();
                    }
                } catch (JavaLayerException ex) {
                    Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

    public  void stop() {
        player.close();
        MainFrame.setFieldDisplay("");
    }

    public void pause() {
  
    }

    public void volume(int vol) {
        if (AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
            if (vol != 0) {
                volumeControl.setValue((float) vol / 100);
            }
        }
    }

    public void mute() {
        if (AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
            if (volumeControl.getValue() == volumeControl.getMinimum()) {
                volumeControl.setValue(volumeControl.getMaximum());
            } else {
                volumeControl.setValue(volumeControl.getMinimum());
            }
        }
    }
    

}
