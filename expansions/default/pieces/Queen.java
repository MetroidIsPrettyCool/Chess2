public class Queen extends Piece  {
    public Queen (int px, int py, int pcolor)  {
	super(px, py, 9, "queen", "Queen", "Queen-like", "Mayonaise", "words words words words words", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	for (int i = 1; this.getX() - i >= 0; i++)  {
	    moves [this.getX() - i] [this.getY()] = 1;
	    if (!board.board [this.getX() - i] [this.getY()].getName().equals(""))  break;
	}
	for (int i = 1; this.getY() - i >= 0; i++)  {
	    moves [this.getX()] [this.getY() - i] = 1;
	    if (!board.board [this.getX()] [this.getY() - i].getName().equals(""))  break;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.getY() + i; i++)  {
	    moves [this.getX()] [this.getY() + i] = 1;
	    if (!board.board [this.getX()] [this.getY() + i].getName().equals(""))  break;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.getX() + i; i++)  {
	    moves [this.getX() + i] [this.getY()] = 1;
	    if (!board.board [this.getX() + i] [this.getY()].getName().equals(""))  break;
	}
	for (int i = 1; this.getX() - i >= 0 && this.getY() - i >= 0; i++)  {
	    moves [this.getX() - i] [this.getY() - i] = 1;
	    if (!board.board [this.getX() - i] [this.getY() - i].getName().equals(""))  break;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.getX() + i && this.getY() - i >= 0; i++)  {
	    moves [this.getX() + i] [this.getY() - i] = 1;
	    if (!board.board [this.getX() + i] [this.getY() - i].getName().equals(""))  break;
	}
	for (int i = 1; this.getX() - i >= 0 && GameSettings.BOARDSIZE > this.getY() + i; i++)  {
	    moves [this.getX() - i] [this.getY() + i] = 1;
	    if (!board.board [this.getX() - i] [this.getY() + i].getName().equals(""))  break;
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.getX() + i && GameSettings.BOARDSIZE > this.getY() + i; i++)  {
	    moves [this.getX() + i] [this.getY() + i] = 1;
	    if (!board.board [this.getX() + i] [this.getY() + i].getName().equals(""))  break;
	}
	return moves;
    }
}
