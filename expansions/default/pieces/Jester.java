public class Jester extends Piece  {
    private boolean moveMode;
    public Jester (int px, int py, int pcolor)  {
	super(px, py, 8, "jester", "Jester", "Very special piece, alternates between:", "moving to any open square on the board", "To moving only one space in any direction", pcolor);
	this.moveMode = true;
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	if (this.moveMode)  {
	    for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
		for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
		    if (board.board [i] [j].getName().equals(""))  moves [i] [j] = 1;
		}
	    }
	}
	else  {
	    for (int i = this.getX() - 1; i <= this.getX() + 1; i++)  {
		for (int j = this.getY() - 1; j <= this.getY() + 1; j++)  {
		    if (i >= 0 && GameSettings.BOARDSIZE > i && j >= 0 && GameSettings.BOARDSIZE > j)
			if (this.canMakeMove(board.board [i] [j]))  moves [i] [j] = 1;
		}
	    }
	    moves [this.getX()] [this.getY()] = 0;
	}
	return moves;
    }
    @Override
    public void afterMove (Board board, Piece p)  {
	this.moveMode = !this.moveMode;
    }
}
