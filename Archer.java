import java.util.Arrays;
public class Archer extends Piece  {
    public Archer ()  {
	super(0, 0, 9, 1, "archer", "Archer", "Compounds are for pussies", "Can move horizontally like a castle", "But captures up to 3 spaces vertically", 0);
    }
    public Archer (int px, int py, int pcolor)  {
	super(px, py, 9, 1, "archer", "Archer", "Compounds are for pussies", "Can move horizontally like a castle", "But captures up to 3 spaces vertically", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
        int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	for (int i = 1; this.x - i >= 0 && i != 4; i++)  {
	    if (board.board [this.x - i] [this.y].getId() != 0)  {
		moves [this.x - i] [this.y] = 1;
		break;
	    }
	}
	for (int i = 1; this.y - i >= 0; i++)  {
	    moves [this.x] [this.y - i] = 1;
	    if (board.board [this.x] [this.y - i].getId() != 0)  break;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.y + i; i++)  {
	    moves [this.x] [this.y + i] = 1;
	    if (board.board [this.x] [this.y + i].getId() != 0)  break;
	}
	return moves;
    }
}
