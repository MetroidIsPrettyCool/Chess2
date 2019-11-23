import java.util.Arrays;
public class Archer extends Piece  {
    public Archer (int px, int py, int pcolor)  {
	super(px, py, 2, "archer", "Archer", "Compound bows are for pussies", "Can move horizontally like a castle", "But captures up to 3 spaces vertically", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
        int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	if (this.getX() - 1 >= 0)  moves [this.getX() - 1] [this.getY()] = 1;
	for (int i = 1; this.getX() - i >= 0 && i != 4; i++)  {
	    if (!board.board [this.getX() - i] [this.getY()].getName().equals(""))  moves [this.getX() - i] [this.getY()] = 1;
	}
	for (int i = 1; this.getY() - i >= 0; i++)  {
	    moves [this.getX()] [this.getY() - i] = 1;
	    if (!board.board [this.getX()] [this.getY() - i].getName().equals(""))  break;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.getY() + i; i++)  {
	    moves [this.getX()] [this.getY() + i] = 1;
	    if (!board.board [this.getX()] [this.getY() + i].getName().equals(""))  break;
	}
	return moves;
    }
}
