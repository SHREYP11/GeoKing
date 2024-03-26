import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private Clip clip;

    public void playSound(String filePath, boolean loop) {
        try {
            // Open an audio input stream.
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Get a sound clip resource.
            clip = AudioSystem.getClip();

            // Open audio clip and load samples from the audio input stream.
            clip.open(audioStream);

            // Loop the clip if specified
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            // Start playing
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopSound() {
        if (clip != null) {
            clip.stop();
        }
    }
}
