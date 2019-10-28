import java.util.*;

public class Board implements Cloneable  {
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
	boardImage = img;
    }
    public Object clone () throws CloneNotSupportedException  { 
        return super.clone(); 
    } 
}
