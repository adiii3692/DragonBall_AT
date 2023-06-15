//Import all necessay libraries
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//Class used to play sound effects and the background music
public class Sound {
    
    //The audio is loaded as an instance of the clip object - The clop can be played to start playing the audio
    Clip clip;
    //An array that stores the location of all sound clips and loads them as an instance of the URL class
    URL[] soundLocation = new URL[30];
    
    /**
     * A constructor that loads the necessary audio files into the soundLocation array
     * Precondition: The soundLocation array must have been declared
     * Postcondition: The audio files have been loaded into the soundLocation array
     */
    public Sound()
    {
        soundLocation[0] = getClass().getResource("./sound/bgm.wav");
        soundLocation[1] = getClass().getResource("./sound/link2.wav");
        soundLocation[2] = getClass().getResource("./sound/blue.wav");
        soundLocation[3] = getClass().getResource("./sound/door.wav");
        soundLocation[4] = getClass().getResource("./sound/over.wav");
        soundLocation[5] = getClass().getResource("./sound/win.wav");
    }

    /**
     * A method that loads a particular audio file as an instance of the Clip class so that it can be played
     * Precondition: An integer that is the index of the audio file must be passed to the method
     * Postcondition: The audio file with the index i is loaded as an instance of the Clip class and is ready to be played
     * @param index - The index of the audio file that needs to be loaded
     */
    public void loadAudio(int index)
    {
        //
        try
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundLocation[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * A method that plays the clip that has been loaded 
     * Precondition: The Clip class object must have been instantiated with an audio file
     * Postcondition: The Clip class object that has been instantiated with an audio file begins to play
     */
    public void play()
    {
        clip.start();
    }

    /**
     * A method that loops the selected audio file
     * Precondition: The Clip class object must have been instantiated with an audio file
     * Postcondition: The Clip class object loops continuously - Mainly for the game's title background music  
     */
    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * A method that stops playing the audio file
     * Precondition: The Clip class object must have been instantiated with an audio file
     * Postcondition: The Clip class object stops playing the sound
     */
    public void stop()
    {
        clip.stop();
    }
}
