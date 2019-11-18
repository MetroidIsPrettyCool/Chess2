public class Knight extends Piece  {
    public Knight (int px, int py, int pcolor)  {
	super(px, py, 1, "knight", "Knight", "Knight piece", "Nothing fancy here", "You know how this works", pcolor);
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
	return moves;
    }
}
