public class Rook extends Piece  {
    public Rook (int px, int py, int pcolor)  {
	super(px, py, 4, "rook", "Rook", "AKA Castle", "Can't castle", "words words words", pcolor);
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
	return moves;
    }
}
