import java.util.Arrays;
public class King extends Piece  {
    public King ()  {
	super(0, 0, 6, 20, "king", "King", "Costs 5 to move", "Allows you to place pieces behind and at the same rank", "Furthest back king is used to calculate where pieces can be placed", 0);
    }
    public King (int px, int py, int pcolor)  {
	super(px, py, 6, 20, "king", "King", "King piece", "Same as normal chess", "More or less", pcolor);
    }
    @Override
    public int [] [] getPossibleMoves (Board board)  {
	int [] [] moves = new int [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	if (BoardController.currPlayer.getScore() < 5)  return null;
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
    public Board makeMove (Board board, Piece d)  {
	Board newBoard;
	try  {
	    newBoard = (Board)board.clone();
	    newBoard.board [d.getX()] [d.getY()] = (Piece)this.clone();
	    newBoard.board [d.getX()] [d.getY()].setX(d.getX());
	    newBoard.board [d.getX()] [d.getY()].setY(d.getY());
	    newBoard.board [d.getX()] [d.getY()].setModified(true);
	    newBoard.board [this.getX()] [this.getY()] = new BlankPiece (this.getX(), this.getY());
	}
	catch (Exception e)  {
	    System.out.println(e + " Moving piece");
	    return null;
	}
	BoardController.currPlayer.setScore(BoardController.currPlayer.getScore() - 5);
	return newBoard;
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
