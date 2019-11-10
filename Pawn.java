import java.util.Arrays;
public class Pawn extends Piece  {
    public Pawn ()  {
	super(0, 0, 1, 0, "pawn", "Pawn", "Pawn Piece", "Performs same as normal chess", "Can't promote (yet)", 0);
    }
    public Pawn (int px, int py, int pcolor)  {
	super(px, py, 1, 0, "pawn", "Pawn", "Pawn Piece", "Performs same as normal chess", "Can't promote (yet)", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	if (this.x - 1 >= 0)  {
	    if (board.board [this.x - 1] [this.y].getId() == 0)  moves [this.x - 1] [this.y] = 1;
	    if ((this.y - 1) >= 0 && board.board [this.x - 1] [this.y - 1].getId() != 0)  moves [this.x - 1] [this.y - 1] = 1;
	    if ((this.y + 1) < GameSettings.BOARDSIZE && board.board [this.x - 1] [this.y + 1].getId() != 0)  moves [this.x - 1] [this.y + 1] = 1;
	}
	return moves;
    }
}
