public class Elephant extends Piece  {
    public Elephant (int px, int py, int pcolor)  {
	super(px, py, 9, "elephant", "Elephant", "Moves like a castle + a knight", "Not much else to say", "I'll make these descriptions better next release", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	int [] [] knight = {{0, 1, 0, 1, 0}, {1, 0, 0, 0, 1}, {0, 0, 0, 0, 0}, {1, 0, 0, 0, 1}, {0, 1, 0, 1, 0}};
	for (int i = 0; i != 5; i++)  {
	    for (int j = 0; j != 5; j++)  {
		if (this.getX() - i + 2 >= 0 && this.getX() - i + 2 < GameSettings.BOARDSIZE)
		    if (this.getY() - j + 2 >= 0 && this.getY() - j + 2 < GameSettings.BOARDSIZE)
			moves [this.getX() - i + 2] [this.getY() - j + 2] = knight [i] [j];
	    }
	}
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
