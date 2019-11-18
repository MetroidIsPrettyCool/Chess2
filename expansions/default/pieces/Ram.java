public class Ram extends Piece  {
    public Ram (int px, int py, int pcolor)  {
	super(px, py, 3, "ram", "Battering Ram", "Moves only forwards and backwards", "Can take two pieces when moving forward", "Smash", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
        for (int i = 1; this.getX() - i >= 0; i++)  {
	    moves [this.getX() - i] [this.getY()] = 1;
	    if (!board.board [this.getX() - i] [this.getY()].getName().equals(""))  {
		if (board.board [this.getX() - i] [this.getY()].canBeCaptured(this))  {
		    i++;
		    for (; this.getX() - i >= 0; i++)  {
			moves [this.getX() - i] [this.getY()] = 1;
			if (!board.board [this.getX() - i] [this.getY()].getName().equals(""))  break;
		    }
		}
		break;
	    }
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.getX() + i; i++)  {
	    moves [this.getX() + i] [this.getY()] = 1;
	    if (!board.board [this.getX() + i] [this.getY()].getName().equals(""))  break;
	}
	return moves;
    }
    public Board makeMove (Board board, Piece d)  {
	Board newBoard;
	try  {
	    newBoard = (Board)board.clone();
	    newBoard.board [d.getX()] [d.getY()] = (Piece)this.clone();
	    newBoard.board [d.getX()] [d.getY()].setX(d.getX());
	    newBoard.board [d.getX()] [d.getY()].setY(d.getY());
	    newBoard.board [d.getX()] [d.getY()].setModified(true);
	    if (d.getX() < this.getX())
		for (int i = 0; i * -1 != this.getX() - d.getX(); i--)
		    newBoard.board [this.getX() + i] [this.getY()] = new BlankPiece (this.getX() + i, this.getY());
	    else  newBoard.board [this.getX()] [this.getY()] = new BlankPiece (this.getX(), this.getY());
	}
	catch (Exception e)  {
	    System.out.println(e + " Moving piece");
	    return null;
	}
	return newBoard;
    }
}
