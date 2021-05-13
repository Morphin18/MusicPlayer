package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jl.decoder.JavaLayerException;
import model.MusicForPlay;
import model.Song;
import view.MainFrame;

public class Controller implements Action {

    private JFrame view = null;
    private Sound sound = null;
    private final DefaultListModel<MusicForPlay> playlist = MainFrame.getModel().getListModel();

    public Controller(JFrame view) {
        this.view = view;
    }
    public Controller() {}
    
    @Override
    public void add() {
        JFileChooser chooser = new JFileChooser("C:\\");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Mp3 Files", "mp3", "mpeg3");
        chooser.addChoosableFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setMultiSelectionEnabled(true);

        if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = chooser.getSelectedFiles();

            if (selectedFiles != null) {
                for (File selectedFile : selectedFiles) {
                    String path = selectedFile.getPath();
                    String songName = selectedFile.getName();
                    MainFrame.updatePlaylist(new MusicForPlay(new Song(songName), Paths.get(path)));
                }
            }

        }
    }

    @Override
    public void del() {
        int[] indexPlayList = MainFrame.getjPlayList().getSelectedIndices();
        if (indexPlayList.length > 0) {
            ArrayList<MusicForPlay> listForRemove = new ArrayList<>();
            for (int i = 0; i < indexPlayList.length; i++) {
                MusicForPlay music = MainFrame.getModel().getListModel().getElementAt(indexPlayList[i]);
                listForRemove.add(music);
            }
            for (MusicForPlay mfp : listForRemove) {
                MainFrame.getModel().getListModel().removeElement(mfp);
            }
        }

    }

    @Override
    public void up() {
        if (!playlist.isEmpty()) {
            int indexSelected = MainFrame.getjPlayList().getSelectedIndex() - 1;
        if (indexSelected <= MainFrame.getModel().getListModel().getSize() - 1) {
            MainFrame.getjPlayList().setSelectedIndex(indexSelected);
        }
        }

    }

    @Override
    public void down() {
        if (!playlist.isEmpty()) {
            int indexSelected = MainFrame.getjPlayList().getSelectedIndex() + 1;
        if (indexSelected <= MainFrame.getModel().getListModel().getSize() - 1) {
            MainFrame.getjPlayList().setSelectedIndex(indexSelected);
        }
        }
    }

    @Override
    public void play() {
        if (sound != null) {
            sound.stop();
        }
        int indexSelected = MainFrame.getjPlayList().getSelectedIndex();
        if (!playlist.isEmpty()) {
            try {

                MainFrame.setFieldDisplay(playlist.get(indexSelected).getSong().getSongName());
                sound = new Sound(new FileInputStream(playlist.get(indexSelected).getPath().toFile()));
                sound.play();
                
            } catch (FileNotFoundException | JavaLayerException e) {
                e.getStackTrace();
            }

        }

    }

    @Override
    public void pause() {
        if (sound != null) {
            sound.pause();
        }
    }

    @Override
    public void stop() {
        if (sound != null) {
            sound.stop();
        }
    }

    @Override
    public void mute() {
       if (sound != null) {
           sound.mute();
       }
    }

    @Override
    public void volume() {
        if (sound != null) {
            sound.volume(MainFrame.getjSlider().getValue());
            MainFrame.getFieldVol().setText(String.valueOf(MainFrame.getjSlider().getValue()));
        }
    }

    @Override
    public void next() {
        if (sound != null) {
            sound.stop();
        }
        down();
        play();

    }

    @Override
    public void back() {
        if (sound != null) {
            sound.stop();
        }
        up();
        play();
    }

    @Override
    public void find() {
        String nameFind = MainFrame.getjTextFieldSearch().getText();
        if (!playlist.isEmpty() && nameFind != null) {
            for (int i = 0; i < playlist.getSize(); i++) {
                if (playlist.get(i).getSong().getSongName().contains(nameFind)) {
                    MainFrame.getjPlayList().setSelectedIndex(i);
                    break;
                }
            }
        }
    }

}
