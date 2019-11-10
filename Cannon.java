public class Cannon extends Piece  {
    public Cannon ()  {
	super(0, 0, 10, 4, "cannon", "Cannon", "Moves like a standard castle, but", "When capturing it must jump over a piece", "Fancy!", 0);
    }
    public Cannon (int px, int py, int pcolor)  {
	super(px, py, 10, 4, "cannon", "Cannon", "Moves like a standard castle, but", "When capturing it must jump over a piece", "Fancy!", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
        for (int i = 1; this.x - i >= 0; i++)  {
	    moves [this.x - i] [this.y] = 1;
	    if (board.board [this.x - i] [this.y].getId() != 0)  {
		moves [this.x - i] [this.y] = 0;
		i++;
		for (; this.x - i >= 0; i++)  {
		    moves [this.x - i] [this.y] = 1;
		    if (board.board [this.x - i] [this.y].getId() != 0)  break;
		}
		break;
	    }
	}
	for (int i = 1; this.y - i >= 0; i++)  {
	    if (board.board [this.x] [this.y - i].getId() != 0)  break;
	    moves [this.x] [this.y - i] = 1;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.y + i; i++)  {
	    if (board.board [this.x] [this.y + i].getId() != 0)  break;
	    moves [this.x] [this.y + i] = 1;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.x + i; i++)  {
	    if (board.board [this.x + i] [this.y].getId() != 0)  break;
	    moves [this.x + i] [this.y] = 1;
	}
	return moves;
    }
}
