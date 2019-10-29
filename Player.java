public class Player  {
    Boolean isWhite;
    Boolean isLocal;
    int score = 0;
    String boardTexture = "board";
    String pieceTexture = "piece";
    Board board = new Board ("board.png");
    public Player (Boolean isWhite, Boolean isLocal)  {
	this.isWhite = isWhite;
	this.isLocal = isLocal;
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
    public void setBoardTexture (String boardTexture)  {
	this.boardTexture = boardTexture; 
    }
    public String getBoardTexture ()  {
	return this.boardTexture;
    }
    public void setPieceTexture (String pieceTexture)  {
	this.pieceTexture = pieceTexture;
    }
    public String getPieceTexture ()  {
	return this.pieceTexture;
    }
    public void setBoard (Board newBoard)  {
        this.board = newBoard;
    }
    public Board getBoard ()  {
	return this.board;
    }
}
