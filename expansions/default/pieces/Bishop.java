public class Bishop extends Piece  {
    public Bishop (int px, int py, int pcolor)  {
	super(px, py, 1, "bishop", "Bishop", "Bishop Piece", "It's just a bishop", "I'm too tired to add anything else", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
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
