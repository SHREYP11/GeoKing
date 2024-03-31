import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The SoundPlayer class provides methods to play sound files using the Java Sound API.
 */
public class SoundPlayer {

    private Clip clip;

    /**
     * Plays the sound file specified by the file path.
     *
     * @param filePath the path to the sound file to be played
     * @param loop     true if the sound should loop continuously, false otherwise
     */
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

    /**
     * Stops the currently playing sound, if any.
     */
    public void stopSound() {
        if (clip != null) {
            clip.stop();
        }
    }
}
