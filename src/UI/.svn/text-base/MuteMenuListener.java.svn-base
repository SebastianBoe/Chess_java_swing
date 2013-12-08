package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class MuteMenuListener implements ActionListener 
{
	Audio audio;
	JMenuItem item;
	public MuteMenuListener(Audio audio, JMenuItem item) 
	{
		super();
		this.item = item;
		this.audio = audio;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		audio.setMuted(!audio.isMuted());
		if(audio.isMuted())
		{
			item.setText("Unmute");
		}
		else
		{
			item.setText("Mute");
		}
	}

}
