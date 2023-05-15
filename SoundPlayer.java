/**
 * A class to play audio files.
 * @author Will Holbrook
 */

 import java.io.File;
 import javax.sound.sampled.*;
 
 public class SoundPlayer {
     public SoundPlayer() {}
 
     public void playAudio(File soundFile) {
         try {
             AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
             AudioFormat audioFormat = audioInputStream.getFormat();
             DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
 
             if (!AudioSystem.isLineSupported(info)) {
                 System.out.println("Line not supported");
                 return;
             }
 
             SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
             line.open(audioFormat);
             line.start();
 
             int bufferSize = (int) audioFormat.getSampleRate() * audioFormat.getFrameSize();
             byte[] buffer = new byte[bufferSize];
             int bytesRead = 0;
 
             while (bytesRead != -1) {
                 bytesRead = audioInputStream.read(buffer, 0, buffer.length);
                 if (bytesRead >= 0) {
                     line.write(buffer, 0, bytesRead);
                 }
             }
 
             line.drain();
             line.close();
             audioInputStream.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 }
 





// import java.io.File;
// import javax.sound.sampled.AudioSystem;
// import javax.sound.sampled.Clip;

// public class SoundPlayer {
//     public SoundPlayer() {}

//     public void playAudio(File soundFile) {
//         try {
//             Clip clip = AudioSystem.getClip();
//             clip.open(AudioSystem.getAudioInputStream(soundFile));
//             clip.start();

//             Thread audioThread = new Thread(() -> {
//                 try {
//                     Thread.sleep(clip.getMicrosecondLength()/1000);
//                     clip.close();
//                 } catch(InterruptedException e) {}
//             });
//             audioThread.start();
//         } catch (Exception e) {}
//     }
// }
