package UI;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Exceptions.NotInsideBoardException;
import Utilities.Coordinate;
import chess.Board;
import chess.Judge;

@SuppressWarnings("serial")
public class ChessFrame extends JFrame
{
	private final static int imageSize = 44, frameBorderWidth = 8,
	frameBorderHight = 30, JMenuHight = 23;
	private ImageIcon[][] imageIcons =  new ImageIcon[8][8];
	private Image[][] images = new Image[8][8];
	private ImageViewer[][] imageViewers = new ImageViewer[8][8];
	private boolean a = true;
	
	public ChessFrame(Judge judge)
	{
		setLayout(new GridLayout(8, 8, 0, 0));
		buildImages();
		setupFrame();
		addMouseListener(new mouseListenerImpl(this, judge));
//		addMenu(judge.getAudio()); //Funker ikke helt
	}
	
	private void addMenu(Audio audio) 
	{
		JMenuBar jmb = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem jNewMenuItem = new JMenuItem("New game");
		JMenuItem jMuteMenuItem = new JMenuItem("Mute");
//		JMenuItem jAboutMenuItem = new JMenuItem("About");
		
		jMuteMenuItem.addActionListener(new MuteMenuListener(audio, jMuteMenuItem));
		jNewMenuItem.addActionListener(new NewGameMenuListener(this, audio));
		
		fileMenu.add(jNewMenuItem);
		fileMenu.add(jMuteMenuItem);
//		fileMenu.add(jAboutMenuItem);
		
		
		jmb.add(fileMenu);
		setJMenuBar(jmb);
	}

	private void buildImages() {
		for (int row = 0; row < 8; row++)
			for (int column = 0; column < 8; column++)
				buildImage(row, column);
	}
	
	void updateGUI() 
	{
		for (int row = 0; row < 8; row++)
			for (int column = 0; column < 8; column++)
			{
				remove(imageViewers[row][column]);						
				buildImage(row, column);
			}
		resize();
		repaint();
	}

	private void resize() 
	{
		int diff = a ? 1 : -1;
		setSize(getWidth() + diff, getHeight() + diff);
		a = !a;
	}
	
	private void buildImage(int row, int column)
	{
		imageIcons[row][column] = new ImageIcon(generateImageUrlAt(row, column));
		images[row][column] = imageIcons[row][column].getImage();
		imageViewers[row][column] = new ImageViewer(images[row][column]);
		add(imageViewers[row][column]);
	}

	private String generateImageUrlAt(int row, int column)
	{
		String urlPrefix = "resources/ChessPieceIcons/Chess_";
		String urlSuffix = "44.png";
		String iconName = Board.getSquareAt(row, column).getSign();
		return urlPrefix + iconName + urlSuffix;
	}

	Coordinate calculateCoordinate(MouseEvent e) throws NotInsideBoardException 
	{
		if(e.getY() < frameBorderHight + JMenuHight)
			throw new NotInsideBoardException();
		
		double xCoordinateMouse = e.getX() - frameBorderWidth;
		double yCoordinateMouse = e.getY() - frameBorderHight - JMenuHight;
		
		double squareWidth = (getWidth() - 16) / 8;
		double squareHeight = (getHeight() - (frameBorderWidth + frameBorderHight + JMenuHight)) / 8;

		int column = (int)(xCoordinateMouse / squareWidth);
		int row = (int)(yCoordinateMouse / squareHeight);
		return new Coordinate(row, column);
	}
	
	private void setupFrame() {
		setTitle("GrandMasters");
		setSize(imageSize * 8, imageSize * 8 + JMenuHight);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}