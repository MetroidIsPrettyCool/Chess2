public class Jester extends Piece  {
    private boolean moveMode;
    public Jester ()  {
	super(0, 0, 14, 8, "jester", "Jester", "Very special piece, alternates between:", "moving to any open square on the board", "To moving only one space in any direction", 0);
	this.moveMode = true;
    }
    public Jester (int px, int py, int pcolor)  {
	super(px, py, 14, 8, "jester", "Jester", "Very special piece, alternates between:", "moving to any open square on the board", "To moving only one space in any direction", pcolor);
	this.moveMode = true;
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	if (this.moveMode)  {
	    for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
		for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
		    if (board.board [i] [j].getId() == 0)  moves [i] [j] = 1;
		}
	    }
	}
	else  {
	    for (int i = this.x - 1; i <= this.x + 1; i++)  {
		for (int j = this.y - 1; j <= this.y + 1; j++)  {
		    if (i >= 0 && GameSettings.BOARDSIZE > i && j >= 0 && GameSettings.BOARDSIZE > j)
			if (this.canMakeMove(board.board [i] [j]))  moves [i] [j] = 1;
		}
	    }
	    moves [this.x] [this.y] = 0;
	}
	return moves;
    }
    @Override
    public Board makeMove (Board board, Piece d)  {
	Board newBoard;
	this.moveMode = !this.moveMode;
	try  {
	    newBoard = (Board)board.clone();
	    newBoard.board [d.getX()] [d.getY()] = (Piece)this.clone();
	    newBoard.board [d.getX()] [d.getY()].setX(d.getX());
	    newBoard.board [d.getX()] [d.getY()].setY(d.getY());
	    newBoard.board [d.getX()] [d.getY()].setModified(true);
	    newBoard.board [this.getX()] [this.getY()] = new BlankPiece (this.getX(), this.getY());
	}
	catch (Exception e)  {
	    System.out.println(e + " Moving piece");
	    return null;
	}
	return newBoard;
    }
}
