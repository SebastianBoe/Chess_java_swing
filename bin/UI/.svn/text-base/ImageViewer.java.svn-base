package UI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageViewer extends JPanel
{
	private Image image;
	private boolean stretched = true;
	private int xCoordinate, yCoordinate;

	public ImageViewer(){}
	
	public ImageViewer(Image image)
	{
		this.image = image;
	}
	
	protected void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		
		if(image != null)
		{
			if(isStretched())
				g.drawImage(image, xCoordinate, yCoordinate,
						getSize().width, getSize().height, this);
			else
				g.drawImage(image, xCoordinate, yCoordinate, this);
		}
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public void setImage(Image image)
	{
		this.image = image;
	}
	
	public boolean isStretched()
	{
		return stretched;
	}
	
	public void setStretched(boolean stretched)
	{
		this.stretched = stretched;
		repaint();
	}
	
	public int getXCoordinate()
	{
		return xCoordinate;
	}

	public void setXCoordinate(int xCoordinate) 
	{
		this.xCoordinate = xCoordinate;
		repaint();
	}

	public int getYCoordinate() {
		return yCoordinate;
	}

	public void setYCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
		repaint();
	}
}