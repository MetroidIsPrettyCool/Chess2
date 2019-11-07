import java.util.Arrays;
public class King extends Piece  {
    public King ()  {
	super(0, 0, 6, 10, "king", "King", "King piece", "Same as normal chess", "More or less", 0);
    }
    public King (int px, int py, int pcolor)  {
	super(px, py, 6, 10, "king", "King", "King piece", "Same as normal chess", "More or less", pcolor);
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
    @Override
    public void upkeep ()  {
	if ((BoardController.currPlayer.getIsWhite() ? 0 : 1) == this.getColor())  {
	    BoardController.currPlayer.setKingRank(this.x);
	}
	if (this.getColor() == 0)  {
	    BoardController.white.setKingInPlay(true);
	}
	else  {
	    BoardController.black.setKingInPlay(true);
	}
    }
}
