import java.util.*;
import java.io.*; 

public class Board implements Cloneable, Serializable  {
    public final int BOARDSIZE = 10;
    public Piece [] [] board;
    public String boardImage;
    public Board (String img)  {
	this.board = new Piece [BOARDSIZE] [BOARDSIZE];
	for (int i = BOARDSIZE - 1; i > BOARDSIZE - 3; i--)  {
	    for (int j = 0; j != BOARDSIZE; j++)  {
		this.board [i] [j] = new UnknownPiece(i, j, 1);
	    }
	}
	for (int i = BOARDSIZE - 3; i > 1; i--)  {
	    for (int j = 0; j != BOARDSIZE; j++)  {
		this.board [i] [j] = new BlankPiece(i, j);
	    }
	}
	for (int i = 1; i >= 0; i--)  {
	    for (int j = 0; j != BOARDSIZE; j++)  {
		this.board [i] [j] = new UnknownPiece(i, j, 0);
	    }
	}
	this.board [0] [5] = new King (0, 5, 0);
	this.board [9] [4] = new King (9, 4, 1);
	boardImage = img;
    }
    public Object clone () throws CloneNotSupportedException  { 
        return super.clone(); 
    } 
}
