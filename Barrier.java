import java.util.Arrays;
public class Barrier extends Piece  {
    public Barrier ()  {
	super(0, 0, 8, 2, "barrier", "Barrier", "Cannot move", "Or be captured", "Kinda boring", 0);
    }
    public Barrier (int px, int py, int pcolor)  {
	super(px, py, 8, 2, "barrier", "Barrier", "Cannot move", "Or be captured", "Kinda boring", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
        return null;
    }
    @Override
    public boolean canBeCaptured (Piece p)  {
	return false;
    }
}
