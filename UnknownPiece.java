import java.util.Arrays; 
public class UnknownPiece extends Piece  {
    public UnknownPiece ()  {
	super(0, 0, 1, 3, "unknown", "Unknown", "", "", "", 0);
    }
    public UnknownPiece (int px, int py, int pcolor)  {
	super(px, py, 1, 3, "unknown", "Unknown", " - Unknown Piece", " - Kinda lame", " - V A1", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	for (int i = this.x - 1; i <= this.x + 1; i++)  {
	    for (int j = this.y - 1; j <= this.y + 1; j++)  {
		if (i >= 0 && GameSettings.BOARDSIZE > i && j >= 0 && GameSettings.BOARDSIZE > j)
		    if (this.canMakeMove(board.board [i] [j]))  moves [i] [j] = 1;
	    }
	}
	moves [this.x] [this.y] = 0;
	return moves;
    }
    @Override
    public boolean canBeCaptured (Piece p)  {
	return true;
    }
}
