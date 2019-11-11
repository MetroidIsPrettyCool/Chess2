public class Queen extends Piece  {
    public Queen ()  {
	super(0, 0, 5, 9, "queen", "Queen", "Queen-like", "Mayonaise", "words words words words words", 0);
    }
    public Queen (int px, int py, int pcolor)  {
	super(px, py, 5, 9, "queen", "Queen", "Queen-like", "Mayonaise", "words words words words words", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	for (int i = 1; this.x - i >= 0; i++)  {
	    moves [this.x - i] [this.y] = 1;
	    if (board.board [this.x - i] [this.y].getId() != 0)  break;
	}
	for (int i = 1; this.y - i >= 0; i++)  {
	    moves [this.x] [this.y - i] = 1;
	    if (board.board [this.x] [this.y - i].getId() != 0)  break;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.y + i; i++)  {
	    moves [this.x] [this.y + i] = 1;
	    if (board.board [this.x] [this.y + i].getId() != 0)  break;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.x + i; i++)  {
	    moves [this.x + i] [this.y] = 1;
	    if (board.board [this.x + i] [this.y].getId() != 0)  break;
	}
	for (int i = 1; this.x - i >= 0 && this.y - i >= 0; i++)  {
	    moves [this.x - i] [this.y - i] = 1;
	    if (board.board [this.x - i] [this.y - i].getId() != 0)  break;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.x + i && this.y - i >= 0; i++)  {
	    moves [this.x + i] [this.y - i] = 1;
	    if (board.board [this.x + i] [this.y - i].getId() != 0)  break;
	}
	for (int i = 1; this.x - i >= 0 && GameSettings.BOARDSIZE > this.y + i; i++)  {
	    moves [this.x - i] [this.y + i] = 1;
	    if (board.board [this.x - i] [this.y + i].getId() != 0)  break;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.x + i && GameSettings.BOARDSIZE > this.y + i; i++)  {
	    moves [this.x + i] [this.y + i] = 1;
	    if (board.board [this.x + i] [this.y + i].getId() != 0)  break;
	}
	return moves;
    }
}
