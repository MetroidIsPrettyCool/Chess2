public class BlankPiece extends Piece  {
    public BlankPiece ()  {
	super(0, 0, 0, 0, "blank", "", "", "", "", 2);
    }
    public BlankPiece (int px, int py)  {
	super(px, py, 0, 0, "blank", "", "", "", "", 2);
    }
    @Override
    public boolean canBeCaptured (Piece piece)  {
	return true;
    }
}
