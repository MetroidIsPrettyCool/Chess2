import java.util.Arrays;
public class King extends Piece  {
    public King (int px, int py, int pcolor)  {
	super(px, py, 20, "king", "King", "Costs 5 to move", "Allows you to place pieces behind and at the same rank", "Furthest back king is used to calculate where pieces can be placed", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	if (BoardController.currPlayer.getScore() < 5)  return null;
	for (int i = this.getX() - 1; i <= this.getX() + 1; i++)  {
	    for (int j = this.getY() - 1; j <= this.getY() + 1; j++)  {
		if (i >= 0 && GameSettings.BOARDSIZE > i && j >= 0 && GameSettings.BOARDSIZE > j)
		    if (this.canMakeMove(board.board [i] [j]))  moves [i] [j] = 1;
	    }
	}
	moves [this.getX()] [this.getY()] = 0;
	return moves;
    }
    @Override
    public void afterMove (Board board, Piece p)  {
	BoardController.currPlayer.setScore(BoardController.currPlayer.getScore() - 5);
    }
    @Override
    public void upkeep ()  {
	if ((BoardController.currPlayer.getIsWhite() ? 0 : 1) == this.getColor())  {
	    BoardController.currPlayer.setKingRank(this.getX());
	}
	if (this.getColor() == 0)  {
	    BoardController.white.setKingInPlay(true);
	}
	else  {
	    BoardController.black.setKingInPlay(true);
	}
    }
}
