public class Knight extends Piece  {
    public Knight ()  {
	super(0, 0, 2, 1, "knight", "Knight", "Knight piece", "Nothing fancy here", "You know how this works", 0);
    }
    public Knight (int px, int py, int pcolor)  {
	super(px, py, 2, 1, "knight", "Knight", "Knight piece", "Nothing fancy here", "You know how this works", pcolor);
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
	return moves;
    }
}
