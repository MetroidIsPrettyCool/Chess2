import java.io.*;
import javafx.scene.image.Image;
public class PoolPiece extends Piece  {
    Piece becomes;
    public PoolPiece (Piece p)  {
	super(0, 0, -1, 0, null, "", "", "", "", (BoardController.currPlayer.getIsWhite() ? 0 : 1));
	this.becomes = p;
	this.setName(this.becomes.getName());
	this.setDesc1(this.becomes.getDesc1());
	this.setDesc2(this.becomes.getDesc2());
	this.setDesc3(this.becomes.getDesc3());
	this.setCost(this.becomes.getCost());
    }
    @Override
    public Image getIcon (boolean selectable, boolean big)  {
	// Never is going to be in an instance where a PoolPiece is selected or capturable, that would be MADNESS
	return this.becomes.getIcon(false, big);
    }
    @Override
    public boolean canCapture (Piece p)  {
	return (p.getId() == 0);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	if (BoardController.currPlayer.getKingRank() == -1)  return null;
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
        for (int i = BoardController.currPlayer.getKingRank(); i != GameSettings.BOARDSIZE && i >= 0; i++)  {
	    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
	        if (board.board [i] [j].getId() == 0)  moves [i] [j] = 1;
	    }
	}
	return moves;
    }
    @Override
    public Board makeMove (Board board, Piece d)  {
	Board newBoard;
	try  {
	    newBoard = (Board)board.clone();
	    newBoard.board [d.getX()] [d.getY()] = (Piece)this.becomes.clone();
	    newBoard.board [d.getX()] [d.getY()].setX(d.getX());
	    newBoard.board [d.getX()] [d.getY()].setY(d.getY());
	    newBoard.board [d.getX()] [d.getY()].setModified(true);
	}
	catch (Exception e)  {
	    System.out.println(e + " Moving piece");
	    return null;
	}
	return newBoard;
    }
}
