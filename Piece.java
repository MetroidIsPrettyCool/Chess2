import java.util.*;
import java.io.*;

public class Piece implements Cloneable {
    final int BOARDSIZE = 10;
    boolean selected = false;
    boolean capturable = false;
    boolean promoted;
    int x, y, id, cost, color;
    String icon, name, desc1, desc2, desc3;
    final String [] colors = new String []{"white", "black", ""};
    public Piece (int px, int py, int pid, int pcost, String picon, String pname, String pdesc1, String pdesc2, String pdesc3, int pcolor)  {
	this.promoted = false;
	this.x = px;
	this.y = py;
	this.id = pid;
	this.cost = pcost;
	this.icon = picon;
	this.name = pname;
	this.desc1 = pdesc1;
	this.desc2 = pdesc2;
	this.desc3 = pdesc3;
	this.color = pcolor;
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
    public String getIcon (boolean selectable)  {
	if (selectable)  return new File ("textures/" + this.icon + "_" + (this.capturable ? "c" : "") + (this.selected ? "s" : "") + colors [color] + ".png").toURI().toString();
	return new File ("textures/" + this.icon + "_" + colors [color] + ".png").toURI().toString();
    }
    public void setIcon (String picon)  {
	this.icon = picon;
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
	this.selected = pselected;
    }
    public boolean getCapturable ()  {
	return this.capturable;
    }
    public void setCapturable (boolean pcapturable)  {
	this.capturable = pcapturable;
    }
    public boolean canBeCaptured (Piece p)  {
	return true;
    }
    public boolean canCapture (Piece p)  {
	return p.getColor() != this.getColor();
    }
    public boolean canMakeMove (Piece p)  {
	return this.canCapture(p) && p.canBeCaptured(this);
    }
    public int [] [] getPossibleMoves (Board board)  {
        return null;
    }
    public Board makeMove (Board board, Piece p, int x, int y)  {
	Board newBoard;
	try  {
	    newBoard = (Board)board.clone();
	    newBoard.board [x] [y] = (Piece)board.board [p.getX()] [p.getY()].clone();
	    newBoard.board [x] [y].setX(x);
	    newBoard.board [x] [y].setY(y);
	    newBoard.board [p.getX()] [p.getY()] = new BlankPiece ();
	}
	catch (Exception e)  {
	    System.out.println(e + " Moving piece");
	    return null;
	}
	return newBoard;
    }
    public Object clone () throws CloneNotSupportedException  { 
        return super.clone(); 
    } 
}
