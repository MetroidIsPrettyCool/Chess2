import java.util.Arrays;
public class Pawn extends Piece  {
    private boolean hasMoved;
    public Pawn (int px, int py, int pcolor)  {
	super(px, py, 0, "pawn", "Pawn", "Pawn Piece", "Performs same as normal chess", "Can't promote (yet)", pcolor);
	this.hasMoved = false;
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	if (this.getX() - 1 >= 0)  {
	    if (board.board [this.getX() - 1] [this.getY()].getName().equals(""))  {
		moves [this.getX() - 1] [this.getY()] = 1;
		if (this.getX() - 2 >= 0 && !this.hasMoved && board.board [this.getX() - 2] [this.getY()].getName().equals(""))  moves [this.getX() - 2] [this.getY()] = 1;
	    }
	    if ((this.getY() - 1) >= 0 && !board.board [this.getX() - 1] [this.getY() - 1].getName().equals(""))  moves [this.getX() - 1] [this.getY() - 1] = 1;
	    if ((this.getY() + 1) < GameSettings.BOARDSIZE && !board.board [this.getX() - 1] [this.getY() + 1].getName().equals(""))  moves [this.getX() - 1] [this.getY() + 1] = 1;
	}
	return moves;
    }
    @Override
    public void afterMove (Board board, Piece p)  {
	this.hasMoved = true;
    }
}
