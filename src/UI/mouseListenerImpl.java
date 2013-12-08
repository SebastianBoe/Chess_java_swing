package UI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Utilities.Coordinate;

import Exceptions.NotInsideBoardException;

import chess.Board;
import chess.Judge;

public class mouseListenerImpl implements MouseListener{

	ChessFrame frame;
	Judge judge;
	public mouseListenerImpl(ChessFrame frame, Judge judge) 
	{
		this.judge = judge; 
		this.frame = frame;
	}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}			
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseClicked(MouseEvent e) 
	{
		try
		{
			Coordinate coordinate = frame.calculateCoordinate(e);
			if(judge.SquareClicked(Board.getSquareAt(coordinate)))
			{
				frame.updateGUI();
				ChessBot.make_your_move();
			}
		}
		catch(NotInsideBoardException ex){}
	}

}
