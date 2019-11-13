public class Cannon extends Piece  {
    public Cannon (int px, int py, int pcolor)  {
	super(px, py, 5, "cannon", "Cannon", "Moves like a standard castle, but", "When capturing it must jump over a piece", "Fancy!", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
        for (int i = 1; this.getX() - i >= 0; i++)  {
	    moves [this.getX() - i] [this.getY()] = 1;
	    if (!board.board [this.getX() - i] [this.getY()].getName().equals(""))  {
		moves [this.getX() - i] [this.getY()] = 0;
		i++;
		for (; this.getX() - i >= 0; i++)  {
		    moves [this.getX() - i] [this.getY()] = 1;
		    if (!board.board [this.getX() - i] [this.getY()].getName().equals(""))  break;
		}
		break;
	    }
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.getX() + i; i++)  {
	    moves [this.getX() + i] [this.getY()] = 1;
	    if (!board.board [this.getX() + i] [this.getY()].getName().equals(""))  {
		moves [this.getX() + i] [this.getY()] = 0;
		i++;
		for (; GameSettings.BOARDSIZE > this.getX() + i; i++)  {
		    moves [this.getX() + i] [this.getY()] = 1;
		    if (!board.board [this.getX() + i] [this.getY()].getName().equals(""))  break;
		}
		break;
	    }
	}
	for (int i = 1; this.getY() - i >= 0; i++)  {
	    moves [this.getX()] [this.getY() - i] = 1;
	    if (!board.board [this.getX()] [this.getY() - i].getName().equals(""))  {
		moves [this.getX()] [this.getY() - i] = 0;
		i++;
		for (; this.getY() - i >= 0; i++)  {
		    moves [this.getX()] [this.getY() - i] = 1;
		    if (!board.board [this.getX()] [this.getY() - i].getName().equals(""))  break;
		}
		break;
	    }
	}
	for (int i = 1; GameSettings.BOARDSIZE > this.getY() + i; i++)  {
	    moves [this.getX()] [this.getY() + i] = 1;
	    if (!board.board [this.getX()] [this.getY() + i].getName().equals(""))  {
		moves [this.getX()] [this.getY() + i] = 0;
		i++;
		for (; GameSettings.BOARDSIZE > this.getY() + i; i++)  {
		    moves [this.getX()] [this.getY() + i] = 1;
		    if (!board.board [this.getX()] [this.getY() + i].getName().equals(""))  break;
		}
		break;
	    }
	}
	return moves;
    }
}
