public class Trebuchet extends Piece  {
    public Trebuchet ()  {
	super(0, 0, 15, 4, "trebuchet", "Trebuchet", "Best siege engine", "Can take any piece 2 squares away", "But doesn't move when it does so", 0);
    }
    public Trebuchet (int px, int py, int pcolor)  {
	super(px, py, 15, 4, "trebuchet", "Trebuchet", "Best siege engine", "Can take any piece 2 squares away", "But doesn't move when it does so", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	for (int i = this.x - 2; i <= this.x + 2; i++)  {
	    for (int j = this.y - 2; j <= this.y + 2; j++)  {
		if (i >= 0 && GameSettings.BOARDSIZE > i && j >= 0 && GameSettings.BOARDSIZE > j)
		    if (board.board [i] [j].getId() != 0)  moves [i] [j] = 1;
	    }
	}
	moves [this.x] [this.y] = 0;
	return moves;
    }
    @Override
    public Board makeMove (Board board, Piece d)  {
	Board newBoard;
	try  {
	    newBoard = (Board)board.clone();
	    newBoard.board [d.getX()] [d.getY()] = new BlankPiece (d.getX(), d.getY());;
	}
	catch (Exception e)  {
	    System.out.println(e + " Moving piece");
	    return null;
	}
	return newBoard;
    }
}
