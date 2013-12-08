package UI;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.ArrayList;

public class AudioPlaylist
{
	private ArrayList<AudioClip> playlist = new ArrayList<AudioClip>();
	private static String prefix = "ChessAudioFiles/", suffix = ".wav";
	int lastPlayedIndex = 0;

	public void add(String filename)
	{
		playlist.add(Applet.newAudioClip(ClassLoader.getSystemResource(prefix + filename + suffix)));
	}
	
	public void playRandomTrack()
	{
		int randomIndex = (int) Math.floor(Math.random() * playlist.size());
		lastPlayedIndex = randomIndex;
		AudioClip clip = playlist.get(randomIndex);
		clip.play();
	}
	
	public void stop()
	{
		playlist.get(lastPlayedIndex).stop();
	}
	
	public void loopRandomTrack()
	{
		int randomIndex = (int) Math.floor(Math.random() * playlist.size());
		AudioClip clip = playlist.get(randomIndex);
		clip.loop();
	}
	
}
