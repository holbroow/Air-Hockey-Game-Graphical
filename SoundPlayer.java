/**
 * A class to play audio files.
 * @author Will Holbrook
 */

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    public SoundPlayer() {}

    public void playAudio(File soundFile) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();

            Thread audioThread = new Thread(() -> {
                try {
                    Thread.sleep(clip.getMicrosecondLength()/1000);
                    clip.close();
                } catch(InterruptedException e) {}
            });
            audioThread.start();
        } catch (Exception e) {}
    }
}
