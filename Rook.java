public class Rook extends Piece  {
    public Rook ()  {
	super(0, 0, 4, 4, "rook", "Rook", "AKA Castle", "Can't castle", "words words words", 0);
    }
    public Rook (int px, int py, int pcolor)  {
	super(px, py, 4, 4, "rook", "Rook", "AKA Castle", "Can't castle", "words words words", pcolor);
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
	return moves;
    }
}
