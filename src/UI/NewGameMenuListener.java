package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chess.Chess;

public class NewGameMenuListener implements ActionListener 
{
	ChessFrame frame;
	Audio audio;
	
	public NewGameMenuListener(ChessFrame chessFrame, Audio audio) 
	{
		frame = chessFrame;
		this.audio = audio;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		audio.stop();
		frame.setVisible(false);
		frame.dispose();
		String[] muted = {"hei"};
		Chess.main(muted);
	}

}