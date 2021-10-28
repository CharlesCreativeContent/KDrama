package com.kdramabeans.game;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class BGM extends Thread {

    public Clip clip;
    public String song; // .wav files
    public boolean isMusic;
    public static boolean muted = false;


    public BGM(String song,boolean isMusic) {
        this.isMusic = isMusic;
        this.song = song;
    }

    public void run() {
        try {
            clip = AudioSystem.getClip();
            clip.open(createAudioStream(song));
            if(isMusic){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    public void playSong() {
        clip.start();
    }

    public void pauseSong() {
        clip.stop();
        this.interrupt();
    }

    public boolean isPlaying() {
        return clip.isRunning();
    }

    //Helper Methods
    private AudioInputStream createAudioStream(String song) throws IOException, UnsupportedAudioFileException {
        URL url = BGM.class.getResource("/" + song);
        return AudioSystem.getAudioInputStream(url);
    }

    public static void mute(){
        muted = !muted;
    }

    public static boolean isMuted(){
        return muted;
    }

}