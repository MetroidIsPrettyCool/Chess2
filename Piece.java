import java.util.*;
import java.io.*;
import java.net.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Piece implements Cloneable, Serializable {
    transient Image nicon = null, sicon = null, cicon = null, bicon = null, temp = null;
    boolean promoted = false, selected = false, capturable = false, modified = true;
    int x, y, id, cost, color;
    String icon, name, desc1, desc2, desc3;
    final String [] colors = new String [] {"white", "black", ""};
    public Piece (int px, int py, int pid, int pcost, String picon, String pname, String pdesc1, String pdesc2, String pdesc3, int pcolor)  {
	this.promoted = false;
	this.x = px;
	this.y = py;
	this.id = pid;
	this.cost = pcost;
	this.icon = "textures/" + GameSettings.pieceTexture + "/" + picon + "_" + this.colors[pcolor] + ".png";
	this.name = pname;
	this.desc1 = pdesc1;
	this.desc2 = pdesc2;
	this.desc3 = pdesc3;
	this.color = pcolor;
	if (picon != null)  {
	    this.nicon = new Image(new File(this.icon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.cicon = new Image(new File (this.icon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.sicon = new Image(new File (this.icon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.bicon = new Image(new File (this.icon).toURI().toString());
	    if (this.color == 0)  {
		this.nicon = reColor(this.nicon, Color.MAGENTA, Color.BLACK);
		this.bicon = reColor(this.bicon, Color.MAGENTA, Color.BLACK);
	    }
	    if (this.color == 1)  {
		this.nicon = reColor(this.nicon, Color.MAGENTA, Color.WHITE);
		this.bicon = reColor(this.bicon, Color.MAGENTA, Color.WHITE);
	    }
	    this.sicon = reColor(this.sicon, Color.MAGENTA, Color.BLUE);
	    this.cicon = reColor(this.cicon, Color.MAGENTA, Color.GREEN);
	}
    }
    public int getX ()  {
	return this.x;
    }
    public void setX (int px)  {
	this.x = px;
    }
    public int getY ()  {
	return this.y;
    }
    public void setY (int py)  {
	this.y = py;
    }
    public int getId ()  {
	return this.id;
    }
    public void setId (int pid)  {
	this.id = pid;
    }
    public int getCost ()  {
	return this.cost;
    }
    public void setCost (int pcost)  {
	this.cost = pcost;
    }
    public int getColor ()  {
	return this.color;
    }
    public void setColor (int pcolor)  {
	this.color = pcolor;
    }
    public Image getIcon (boolean selectable, boolean big)  {
	// if this.setIcon(this.icon);
	if (big)  return this.bicon;
        if (this.selected)  return this.sicon;
	if (this.capturable)  return this.cicon;
	return this.nicon;
    }
    public void setIcon (String picon)  {
	if (picon != null)  {
	    this.icon = picon;
	    this.nicon = new Image(new File(this.icon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.cicon = new Image(new File (this.icon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.sicon = new Image(new File (this.icon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.bicon = new Image(new File (this.icon).toURI().toString());
	    if (this.color == 0)  {
		this.nicon = reColor(this.nicon, Color.MAGENTA, Color.BLACK);
		this.bicon = reColor(this.bicon, Color.MAGENTA, Color.BLACK);
	    }
	    if (this.color == 1)  {
		this.nicon = reColor(this.nicon, Color.MAGENTA, Color.WHITE);
		this.bicon = reColor(this.bicon, Color.MAGENTA, Color.WHITE);
	    }
	    this.sicon = reColor(this.sicon, Color.MAGENTA, Color.BLUE);
	    this.cicon = reColor(this.cicon, Color.MAGENTA, Color.LIME);
	}
    }
    public String getName ()  {
	return this.name;
    }
    public void setName (String pname)  {
	this.name = pname;
    }
    public String getDesc1 ()  {
	return this.desc1;
    }
    public void setDesc1 (String pdesc1)  {
	this.desc1 = pdesc1;
    }
    public String getDesc2 ()  {
	return this.desc2;
    }
    public void setDesc2 (String pdesc2)  {
	this.desc2 = pdesc2;
    }
    public String getDesc3 ()  {
	return this.desc3;
    }
    public void setDesc3 (String pdesc3)  {
	this.desc3 = pdesc3;
    }
    public boolean getPromoted ()  {
	return this.promoted;
    }
    public void setPromoted (boolean ppromoted)  {
	this.promoted = ppromoted;
    }
    public boolean getSelected ()  {
	return this.selected;
    }
    public void setSelected (boolean pselected)  {
	if (pselected != this.selected)  this.modified = true;
	this.selected = pselected;
    }
    public boolean getCapturable ()  {
	return this.capturable;
    }
    public void setCapturable (boolean pcapturable)  {
	if (pcapturable != this.capturable)  this.modified = true;
	this.capturable = pcapturable;
    }
    public boolean getModified ()  {
	return this.modified;
    }
    public void setModified (boolean pmodified)  {
	this.modified = pmodified;
    }
    public boolean canBeCaptured (Piece p)  {
	return p.getColor() != this.getColor();
    }
    public boolean canCapture (Piece p)  {	
	return true;
    }
    public boolean canMakeMove (Piece p)  {
	return this.canCapture(p) && p.canBeCaptured(this);
    }
    public int [] [] getPossibleMoves (Board board)  {
        return null;
    }
    public Board makeMove (Board board, Piece d)  {
	Board newBoard;
	try  {
	    newBoard = (Board)board.clone();
	    newBoard.board [d.getX()] [d.getY()] = (Piece)this.clone();
	    newBoard.board [d.getX()] [d.getY()].setX(d.getX());
	    newBoard.board [d.getX()] [d.getY()].setY(d.getY());
	    newBoard.board [d.getX()] [d.getY()].setModified(true);
	    newBoard.board [this.getX()] [this.getY()] = new BlankPiece (this.getX(), this.getY());
	}
	catch (Exception e)  {
	    System.out.println(e + " Moving piece");
	    return null;
	}
	return newBoard;
    }
    public void upkeep ()  {
	// Does nothing by default, needed for a couple special pieces (like the king)
    }
    public Object clone () throws CloneNotSupportedException  {
	if (GameSettings.twoComputers)  this.setIcon(this.icon);
        return super.clone(); 
    }
    // Copied from Stack Overflow
    // If it works, it works
    private Image reColor(Image inputImage, Color oldColor, Color newColor) {
	try  {
	    int height = (int) inputImage.getHeight();
	    int width = (int) inputImage.getWidth();
	    WritableImage outputImage = new WritableImage(width, height);
	    PixelReader reader = inputImage.getPixelReader();
	    PixelWriter writer = outputImage.getPixelWriter();
	    int oldBlue = (int)(oldColor.getBlue() * 255);
	    int oldRed = (int)(oldColor.getRed() * 255);
	    int oldGreen = (int)(oldColor.getGreen() * 255);
	    int newBlue = (int)(newColor.getBlue() * 255);
	    int newRed = (int)(newColor.getRed() * 255);
	    int newGreen = (int)(newColor.getGreen() * 255);
	    for (int i = 0; i < height; i++) {
		for (int j = 0; j < width; j++) {
		    int argb = reader.getArgb(i, j);
		    int alpha = (argb >> 24) & 0xFF;
		    int red = (argb >> 16) & 0xFF;
		    int green = (argb >> 8) & 0xFF;
		    int blue =  argb & 0xFF;
		    if (red == oldRed && green == oldGreen && blue == oldBlue) {
			red = newRed;
			green = newGreen;
			blue = newBlue;
		    }
		    argb = (alpha << 24) | (red << 16) | (green << 8) | blue;
		    writer.setArgb(i, j, argb);
		}
	    }
	    return outputImage;
	}
	catch (Exception e)  {
	    System.out.println(e + " ReColoring image");
	}
	return (Image)inputImage;
    }
}
