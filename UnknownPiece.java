import java.util.Arrays; 
public class UnknownPiece extends Piece  {
    public UnknownPiece ()  {
	super(0, 0, 1, 0, "unknown", "Unknown", "", "", "", 0);
    }
    public UnknownPiece (int px, int py, int pcolor)  {
	super(px, py, 1, 0, "unknown", "Unknown", " - Unknown Piece", " - Kinda lame", " - V A1", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves ()  {
	int [] [] moves = new int [this.BOARDSIZE] [this.BOARDSIZE];
	for (int i = this.x - 1; i <= this.x + 1; i++)  {
	    for (int j = this.y - 1; j <= this.y + 1; j++)  {
		if (i >= 0 && this.BOARDSIZE > i && j >= 0 && this.BOARDSIZE > j)  moves [i] [j] = 1;
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
