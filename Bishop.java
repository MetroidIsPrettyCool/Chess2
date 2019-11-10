public class Bishop extends Piece  {
    public Bishop ()  {
	super(0, 0, 3, 2, "bishop", "Bishop", "Bishop Piece", "It's just a bishop", "I'm too tired to add anything else", 0);
    }
    public Bishop (int px, int py, int pcolor)  {
	super(px, py, 3, 2, "bishop", "Bishop", "Bishop Piece", "It's just a bishop", "I'm too tired to add anything else", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
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
