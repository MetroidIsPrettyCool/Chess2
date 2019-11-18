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
	for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
	    this.board [1] [i] = new Pawn (1, i, 1);
	    this.board [0] [i] = new Archer (0, i, 1);
	    this.board [8] [i] = new Pawn (8, i, 0);
	    this.board [9] [i] = new Archer (9, i, 0);
	}
	this.board [1] [3] = new Barrier (1, 3, 1);
	this.board [1] [6] = new Barrier (1, 6, 1);
	this.board [0] [5] = new King (0, 5, 1);
	this.board [0] [4] = new Fortress (0, 4, 1);
	this.board [0] [0] = new Rook (0, 0, 1);
	this.board [0] [9] = new Rook (0, 9, 1);
	this.board [0] [1] = new Bishop (0, 1, 1);
	this.board [0] [8] = new Bishop (0, 8, 1);
	
        this.board [8] [3] = new Barrier (8, 3, 0);
	this.board [8] [6] = new Barrier (8, 6, 0);
	this.board [9] [4] = new King (9, 4, 0);
	this.board [9] [5] = new Fortress (9, 5, 0);
	this.board [9] [0] = new Rook (9, 0, 0);
	this.board [9] [9] = new Rook (9, 9, 0);
	this.board [9] [1] = new Bishop (9, 1, 0);
	this.board [9] [8] = new Bishop (9, 8, 0);
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
