import java.util.*;
import java.io.*; 

public class Board implements Cloneable, Serializable  {
    public Piece [] [] board;
    public String boardImage;
    public Board (String img)  {
	this.board = new Piece [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	for (int i = 0; i < GameSettings.BOARDSIZE; i++)  {
	    for (int j = 0; j < GameSettings.BOARDSIZE; j++)  {
		this.board [i] [j] = new BlankPiece(i, j);
	    }
	}
	this.board [1] [5] = new King (1, 5, 1);
	this.board [1] [4] = new Fortress (1, 4, 1);
	this.board [8] [4] = new King (8, 4, 0);
	this.board [8] [5] = new Fortress (8, 5, 0);
	boardImage = img;
    }
    public void flip ()  {
	Piece [] [] temp = new Piece [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
	    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
		temp [i] [j] = this.board [GameSettings.BOARDSIZE - i - 1] [GameSettings.BOARDSIZE - j - 1];
	    }
	}
	for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
	    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
		try  {
		    temp [i] [j].setModified(false);
		    if (!temp [i] [j].equals(this.board [i] [j]))  temp [i] [j].setModified(true);
		    this.board [i] [j] = (Piece)temp [i] [j].clone();
		    this.board [i] [j].setX(i);
		    this.board [i] [j].setY(j);
		}
		catch (Exception e)  {
		    System.out.println(e + " Flipping Board");
		}
	    }
	}
    }
    public Object clone () throws CloneNotSupportedException  { 
        return super.clone(); 
    } 
}
