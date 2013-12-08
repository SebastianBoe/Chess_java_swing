package UI;

import Pieces.Piece;

public class Album 
{
	private AudioPlaylist 
	pawn = new AudioPlaylist(),
	bishop = new AudioPlaylist(),
	knight = new AudioPlaylist(),
	rook = new AudioPlaylist(),
	queen = new AudioPlaylist(),
	king = new AudioPlaylist();
	
	public void addTrack(String filename, Piece piece)
	{
		if(piece.isBishop())
			bishop.add(filename);
		else if(piece.isPawn())
			pawn.add(filename);
		else if(piece.isKing())
			king.add(filename);
		else if(piece.isKnight())
			knight.add(filename);
		else if(piece.isQueen())
			queen.add(filename);
		else if(piece.isRook())
			rook.add(filename);
	}
	
	public void addTrack(String [] strings, Piece piece)
	{
		for (int i = 0; i < strings.length; i++) 
		{
			addTrack(strings[i], piece);
		}
	}
	
	public void stop()
	{
		pawn.stop();
		bishop.stop();
		knight.stop();
		rook.stop();
		queen.stop();
		king.stop();
	}
	
	public void playTrack(Piece piece)
	{
		if(piece.isBishop())
			bishop.playRandomTrack();
		else if(piece.isPawn())
			pawn.playRandomTrack();
		else if(piece.isKing())
			king.playRandomTrack();
		else if(piece.isKnight())
			knight.playRandomTrack();
		else if(piece.isQueen())
			queen.playRandomTrack();
		else if(piece.isRook())
			rook.playRandomTrack();
	}
	
}
