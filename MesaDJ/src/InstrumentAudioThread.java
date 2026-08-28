package src;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class InstrumentAudioThread implements Runnable {
    private final String instrumentName;
    private final String audioFilePath;
    private Clip clip;
    private volatile boolean running = true;
    private volatile boolean playing = false;
    private volatile int bpm = 120;
    
    private final Object lock = new Object();

    public InstrumentAudioThread(String instrumentName, String audioFilePath) {
        this.instrumentName = instrumentName;
        this.audioFilePath = audioFilePath;
        initAudio();
    }
    
    private void initAudio() {
        try {
            File audioFile = new File(audioFilePath);
            if (!audioFile.exists()) {
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            System.out.println("Erro ao carregar áudio do " + instrumentName + ": " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (running) {
            synchronized (lock) {
                if (playing && clip != null && !clip.isRunning()) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } else if (!playing && clip != null && clip.isRunning()) {
                    clip.stop();
                }
            }
            long sleepTime = 60000 / bpm; 
            
            try {
                Thread.sleep(sleepTime); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
    
    public void playInstrument() {
        synchronized (lock) {
            playing = true;
        }
    }
    
    public void pauseInstrument() {
        synchronized (lock) {
            playing = false;
        }
    }
    
    public void stopThread() {
        running = false;
        playing = false;
    }
    
    public void setBpm(int bpm) {
        if (bpm > 0) {
            this.bpm = bpm;
        }
    }
    
    public boolean isPlaying() {
        return playing;
    }
    
    public String getInstrumentName() {
        return instrumentName;
    }
}
