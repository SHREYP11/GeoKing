import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

/**
 * A class for playing sound files using Java Sound API.
 *
 * @author Michael Zhao
 * @version 1.0
 */
public class SoundPlayer {

    /**
     * Plays the specified audio file.
     *
     * @param filename the path of the audio file to be played.
     */
    public void play(String filename) throws UnsupportedAudioFileException {
        try {

            // create new file object
            File file = new File(filename);

            // create new AudioInputStream object
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

            Clip clip = AudioSystem.getClip(); // create new Clip object

            clip.open(audioInputStream);
            // press button in settings GUI and start the clip
            clip.start();

        } catch (Exception exception) {
            throw new UnsupportedAudioFileException(
                    "Operation failed because file did not contain valid data of a recognized file type and format");
        }

    }

    /**
     * Stops the specified audio clip.
     *
     * @param clip the audio clip to be stopped.
     * @throws UnsupportedAudioFileException if the audio file is not supported.
     * @throws IOException                   if an I/O error occurs.
     * @throws LineUnavailableException      if a line cannot be opened because it
     *                                       is unavailable.
     */
    public void stop(Clip clip) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // press button in settings GUI and stop the clip
        clip.stop();
        clip.close();

    }

}
