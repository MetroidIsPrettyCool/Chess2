import java.util.Arrays;
public class Fortress extends Piece  {
    public Fortress (int px, int py, int pcolor)  {
	super(px, py, 7, "fortress", "Fortress", "Allows you to keep playing without a king", "Cannot move", "Can be captured", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
        return null;
    }
    @Override
    public void upkeep ()  {
	if (this.getColor() == 0)  {
	    BoardController.white.setKingInPlay(true);
	}
	else  {
	    BoardController.black.setKingInPlay(true);
	}
    }
}
