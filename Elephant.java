public class Elephant extends Piece  {
    public Elephant ()  {
	super(0, 0, 11, 9, "elephant", "Elephant", "Moves like a castle + a knight", "Not much else to say", "I'll make these descriptions better next release", 0);
    }
    public Elephant (int px, int py, int pcolor)  {
	super(px, py, 11, 9, "elephant", "Elephant", "Moves like a castle + a knight", "Not much else to say", "I'll make these descriptions better next release", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	int [] [] knight = {{0, 1, 0, 1, 0}, {1, 0, 0, 0, 1}, {0, 0, 0, 0, 0}, {1, 0, 0, 0, 1}, {0, 1, 0, 1, 0}};
	for (int i = 0; i != 5; i++)  {
	    for (int j = 0; j != 5; j++)  {
		if (this.x - i + 2 >= 0 && this.x - i + 2 < GameSettings.BOARDSIZE)
		    if (this.y - j + 2 >= 0 && this.y - j + 2 < GameSettings.BOARDSIZE)
			moves [this.x - i + 2] [this.y - j + 2] = knight [i] [j];
	    }
	}
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
