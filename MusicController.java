import java.util.*;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView;  
import java.io.*;

public class MusicController extends Thread  {
    public static boolean exit = false;
    public void run ()  {
	synchronized (this)  {
	    File musicFolder;
	    try  {
		musicFolder = new File ("music");
		int song;
		int prevSong = -1;
		Random rand = new Random();
		Media songMedia;
		MediaPlayer songPlayer;
		while (true)  {
		    do  {
			song = rand.nextInt(musicFolder.list().length);
		    }  while (song == prevSong);
		    prevSong = song;
		    songMedia = new Media(musicFolder.listFiles() [song].toURI().toString());
		    songPlayer = new MediaPlayer(songMedia);
		    songPlayer.play();
		    wait(1000);
		    // Gives the media object time to laod the duration of the music
		    for (long i = 0; i != (long)songMedia.getDuration().toMillis(); i++)  {
			wait(1);
			if (this.exit)  return;
		    }
		}
	    }
	    catch (Exception e)  {
		System.out.println("Exception Loading Music\n" + e);
		return;
	    }
	}
    }
} 
