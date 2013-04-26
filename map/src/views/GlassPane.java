package views;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GlassPane extends JPanel{
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private Point location;
	private Composite transparence;

	public GlassPane(){
		setOpaque(false);
		transparence = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
	}  

	public void setLocation(Point location){
		this.location = location;       
	}

	public void setImage(BufferedImage image){
		img = image;
	}

	public void paintComponent(Graphics g){
		if(img == null)
			return;
		Graphics2D g2d = (Graphics2D)g;
		g2d.setComposite(transparence);
		g2d.drawImage(img, (int) (location.getX() - (img.getWidth(this)  / 2)), (int) (location.getY() - (img.getHeight(this) / 2)), null);
	}  
}