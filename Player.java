public class Player  {
    Boolean isWhite;
    Boolean isLocal;
    int score = 0;
    public int kingRank;
    Board board = new Board ("board.png");
    Piece [] allPieces = new Piece [6];
    public Player (Boolean isWhite, Boolean isLocal)  {
	this.isWhite = isWhite;
	this.isLocal = isLocal;
	allPieces [0] = new UnknownPiece (0,0, (isWhite ? 0 : 1));
	allPieces [1] = new UnknownPiece (0,0, (isWhite ? 0 : 1));
	allPieces [2] = new UnknownPiece (0,0, (isWhite ? 0 : 1));
	allPieces [3] = new UnknownPiece (0,0, (isWhite ? 0 : 1));
	allPieces [4] = new UnknownPiece (0,0, (isWhite ? 0 : 1));
	allPieces [5] = new UnknownPiece (0,0, (isWhite ? 0 : 1));
	kingRank = (isWhite ? 0 : 9); 
    }
    public void setIsWhite (boolean isWhite)  {
	this.isWhite = isWhite; 
    }
    public boolean getIsWhite ()  {
	return this.isWhite;
    }
    public void setIsLocal (boolean isLocal)  {
	this.isLocal = isLocal; 
    }
    public boolean getIsLocal ()  {
	return this.isLocal;
    }
    public void setScore (int score)  {
	this.score = score;
    }
    public int getScore ()  {
	return this.score;
    }
    public void setBoard (Board newBoard)  {
        this.board = newBoard;
    }
    public Board getBoard ()  {
	return this.board;
    }
}
