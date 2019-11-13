import java.util.*;
import java.io.*;
import java.net.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Piece implements Cloneable, Serializable {
    // transient Image nicon = null, sicon = null, cicon = null, bicon = null, temp = null;
    private boolean promoted = false, selected = false, capturable = false, modified = true;
    private int x, y, cost, color;
    private String icon, name, desc1, desc2, desc3;
    // final String [] colors = new String [] {"white", "black", ""};
    public Piece (int x, int y, int cost, String icon, String name, String desc1, String desc2, String desc3, int color)  {
	this.promoted = false;
	this.x = x;
	this.y = y;
	// this.id = id;
	this.cost = cost;
	this.icon = icon;
	this.name = name;
	this.desc1 = desc1;
	this.desc2 = desc2;
	this.desc3 = desc3;
	this.color = color;
    }
    public int getX ()  {
	return this.x;
    }
    public void setX (int x)  {
	this.x = x;
    }
    public int getY ()  {
	return this.y;
    }
    public void setY (int y)  {
	this.y = y;
    }
    public int getCost ()  {
	return this.cost;
    }
    public void setCost (int cost)  {
	this.cost = cost;
    }
    public int getColor ()  {
	return this.color;
    }
    public void setColor (int color)  {
	this.color = color;
    }
    public Image getIcon (boolean selectable, boolean big)  {
	if (big)  return GameSettings.icons [Texture.getInManifest(this.getName())].getIcon(false, false, (this.color == 0), true);
	if (this.selected)  return GameSettings.icons [Texture.getInManifest(this.getName())].getIcon(false, true, (this.color == 0), false);
	if (this.capturable)  return GameSettings.icons [Texture.getInManifest(this.getName())].getIcon(true, false, (this.color == 0), false);
	return GameSettings.icons [Texture.getInManifest(this.getName())].getIcon(false, false, (this.color == 0), false);
    }
    public String getTexture ()  {
	return this.icon;
    }
    public String getName ()  {
	return this.name;
    }
    public void setName (String name)  {
	this.name = name;
    }
    public String getDesc1 ()  {
	return this.desc1;
    }
    public void setDesc1 (String desc1)  {
	this.desc1 = desc1;
    }
    public String getDesc2 ()  {
	return this.desc2;
    }
    public void setDesc2 (String desc2)  {
	this.desc2 = desc2;
    }
    public String getDesc3 ()  {
	return this.desc3;
    }
    public void setDesc3 (String desc3)  {
	this.desc3 = desc3;
    }
    public boolean getPromoted ()  {
	return this.promoted;
    }
    public void setPromoted (boolean promoted)  {
	this.promoted = promoted;
    }
    public boolean getSelected ()  {
	return this.selected;
    }
    public void setSelected (boolean selected)  {
	if (selected != this.selected)  this.modified = true;
	this.selected = selected;
    }
    public boolean getCapturable ()  {
	return this.capturable;
    }
    public void setCapturable (boolean capturable)  {
	if (capturable != this.capturable)  this.modified = true;
	this.capturable = capturable;
    }
    public boolean getModified ()  {
	return this.modified;
    }
    public void setModified (boolean modified)  {
	this.modified = modified;
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
    // Should only very rarely be overrrode
    public Board makeMove (Board board, Piece d)  {
	Board newBoard;
	this.afterMove(board, d);
	try  {
	    newBoard = (Board)board.clone();
	    newBoard.board [d.getX()] [d.getY()] = (Piece)this.clone();
	    newBoard.board [d.getX()] [d.getY()].setX(d.getX());
	    newBoard.board [d.getX()] [d.getY()].setY(d.getY());
	    newBoard.board [d.getX()] [d.getY()].setModified(true);
	    newBoard.board [this.getX()] [this.getY()] = new BlankPiece (this.getX(), this.getY());
	}
	catch (Exception e)  {
	    System.out.println(e + " Moving iece");
	    return null;
	}
	return newBoard;
    }
    public void afterMove (Board board, Piece p)  {
	// Only used by a few pieces
    }
    public void upkeep ()  {
	// Does nothing by default, needed for a couple special pieces (like the king)
    }
    public Object clone () throws CloneNotSupportedException  {
        return super.clone(); 
    }
}
