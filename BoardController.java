import java.net.URI;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.collections.*;

public class BoardController {

    @FXML
    private Label infoDesc0;

    @FXML
    private Label infoDesc1;

    @FXML
    private Label infoDesc2;

    @FXML
    private Label infoDesc3;

    @FXML
    private ImageView infoIcon;

    @FXML
    private Label pointsDisplay;

    @FXML
    private ImageView poolOption1;

    @FXML
    private ImageView poolOption2;

    @FXML
    private ImageView poolOption3;

    @FXML
    private ImageView poolOption4;

    @FXML
    private ImageView poolOption5;

    @FXML
    private GridPane pane;

    @FXML
    private Pane boardBackground;

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    @FXML
    private Label option1Cost;

    @FXML
    private Label option2Cost;

    @FXML
    private Label option3Cost;

    @FXML
    private Label option4Cost;

    @FXML
    private Label option5Cost;

    public int optionOffset = 0;
    
    @FXML
    void poolOption1Selected (Event event) {
	placeNewPiece(0);
    }

    @FXML
    void poolOption2Clicked (Event event) {
	placeNewPiece(1);
    }

    @FXML
    void poolOption3Selected (Event event) {
        placeNewPiece(2);
    }

    @FXML
    void poolOption4Selected (Event event) {
        placeNewPiece(3);
    }

    @FXML
    void poolOption5Selected (Event event) {
	placeNewPiece(4);
    }

    void placeNewPiece (int off)  {
	if (currPlayer.getIsLocal() && currPlayer.allPieces [optionOffset + off].getCost() <= currPlayer.getScore())  {
	    for (int i = 0; i != currPlayer.getBoard().BOARDSIZE; i++)  {
		for (int j = currPlayer.getBoard().BOARDSIZE - 1; j >= 0; j--)  {
		    currPlayer.getBoard().board [i] [j].setCapturable(false);
		}
	    }
	    for (int i = currPlayer.kingRank; currPlayer.getBoard().BOARDSIZE > i && i >= 0; i += (currPlayer.getIsWhite() ? -1 : 1))  {
		for (int j = 0; j != currPlayer.getBoard().BOARDSIZE; j++)  {
		    if (currPlayer.getBoard().board [i] [j].getId() == 0)  currPlayer.getBoard().board [i] [j].setCapturable(true);
		}
	    }
	    selectedPiece = currPlayer.allPieces [optionOffset + off];
	    placing = true;
	    selecting = false;
	    drawBoard();
	}
    }

    @FXML
    void rotateLeft (ActionEvent event) {
	if (optionOffset  > 0)  optionOffset--;
	updateOptions();
    }

    @FXML
    void rotateRight (ActionEvent event) {
	if (currPlayer.allPieces.length > optionOffset + 5) optionOffset++;
	updateOptions();
    }

    public ImageView [] [] images;

    public static int xPos = 0, yPos = 0;

    public EventHandler <MouseEvent> squareClicked;

    public static Player currPlayer = null, white, black;

    public boolean selecting = true;
    public boolean placing = false;

    public Piece selectedPiece;

    public static boolean switchSides = false;
    
    @FXML
    public void initialize () {
	// White and black players. first arg is if they player is white, second is if the player is local
	white = new Player(true, (GameSettings.server != null));
	black = new Player(false, (!GameSettings.twoComputers) ^ (GameSettings.server != null));
	// First playr to play is white
	currPlayer = white;
	// Makes sure the options pool is up to date
	updateOptions();
	// Stores what texture is in each square
	images = new ImageView [currPlayer.getBoard().BOARDSIZE] [currPlayer.getBoard().BOARDSIZE];
	// Sets the background of the board
        boardBackground.setStyle("-fx-background-image:url('file:./textures/"  + GameSettings.boardTexture + "/" + currPlayer.getBoard().boardImage + "')");
	// When a player clicks on a square, this function is invoked
	squareClicked = new EventHandler <MouseEvent> ()  {
		@Override
		public void handle (MouseEvent e) {
		    // Deselects the previously selected square
		    currPlayer.getBoard().board [xPos] [yPos].setSelected(false);
		    // Sets xPos and yPos to the x and y coordinates of the square clicked
		    xPos = pane.getRowIndex((ImageView)e.getSource()) == null ? 0 : pane.getRowIndex((ImageView)e.getSource());
		    yPos = pane.getColumnIndex((ImageView)e.getSource()) == null ? 0 : pane.getColumnIndex((ImageView)e.getSource());
		    // If the current player isn't in selection mode and isn't in placing mode and if the piece selected is set to be capturable and the current player is local
		    if (!selecting && !placing && currPlayer.getBoard().board [xPos] [yPos].getCapturable() && currPlayer.getIsLocal())  {
			currPlayer.setBoard(currPlayer.getBoard().board [xPos] [yPos].makeMove(currPlayer.getBoard(), selectedPiece, xPos, yPos));
			// Increment the current player's score
			currPlayer.setScore(currPlayer.getScore() + 1);
			// Send the board to the other player
			try  {
			    GameSettings.out.writeObject(currPlayer.board);
			}
			catch (Exception ex)  {
			    System.out.println(ex + " Reading board");
			}
		        // Switch players
			switchPlayers();
		    }
		    // If the player is placing and the piece selected is capturable
		    else if (placing && currPlayer.getBoard().board [xPos] [yPos].getCapturable())  {
			try  {
			    Board newBoard = (Board)currPlayer.getBoard().clone();
			    newBoard.board [xPos] [yPos] = (Piece)selectedPiece.clone();
			    newBoard.board [xPos] [yPos].setX(xPos);
			    newBoard.board [xPos] [yPos].setY(yPos);
			    currPlayer.setBoard(newBoard);
			}
			catch (Exception ex)  {
			    System.out.println(ex + " Placing new piece");
			}
			currPlayer.setScore(currPlayer.getScore() - selectedPiece.getCost());
			// Send the board to the other player
			try  {
			    GameSettings.out.writeObject(currPlayer.board);
			}
			catch (Exception ex)  {
			    System.out.println(ex + " Reading board");
			}
		        // Switch players
		        switchPlayers();
		    }
		    // If the player is in selection mode
		    else  {
			// Sets every square to not be capturable
			for (int i = 0; i != currPlayer.getBoard().BOARDSIZE; i++)  {
			    for (int j = currPlayer.getBoard().BOARDSIZE - 1; j >= 0; j--)  {
				currPlayer.getBoard().board [i] [j].setCapturable(false);
			    }
			}
			// Set the spot selected to selected.
			currPlayer.getBoard().board [xPos] [yPos].setSelected(true);
			// Stores the selected piece
			selectedPiece = currPlayer.getBoard().board [xPos] [yPos];
			// If the piece selected has possible moves and is the same color as the current player and the current player is local
			if (currPlayer.getIsLocal() && currPlayer.getBoard().board [xPos] [yPos].getPossibleMoves(currPlayer.getBoard()) != null && (currPlayer.getBoard().board [xPos] [yPos].getColor() == 0 ? true : false) == currPlayer.getIsWhite())  {
			    // CAN PROBALY BE MOVED INTO GETPOSSIBLEMOVES, MIGHT DO LATER TO REDUCE CLUTTER
			    // Loop through every square on the board
			    for (int i = 0; i != currPlayer.getBoard().BOARDSIZE; i++)  {
				for (int j = currPlayer.getBoard().BOARDSIZE - 1; j >= 0; j--)  {
				    // If the square can be moved to by the square and can be captured by it
				    if (currPlayer.getBoard().board [xPos] [yPos].getPossibleMoves(currPlayer.getBoard()) [i] [j] == 1)  {
					// Set the piece to display as capturable
					currPlayer.getBoard().board [i] [j].setCapturable(true);
				    }
				}
			    }
			}
			// If the current player is local, move them out of selection mode (Otherwise the current player is remote and the local player should be able to select whatever)
			if (currPlayer.getIsLocal())  selecting = false;
		    }
		    placing = false;
		    // Redraw the board
		    drawBoard();
		    // Updates the sidebar
		    updateSidebar();
		    // Makes sure the options pool is up to date
		    updateOptions();
		}
	    };
	// Sets up the board (no touching)
	for (int i = 0; i != currPlayer.getBoard().BOARDSIZE; i++)  {
	    for (int j = 0; j != currPlayer.getBoard().BOARDSIZE; j++)  {
		try  {
		    images [i] [j] = new ImageView (new Image (currPlayer.getBoard().board [i] [j].getIcon(true), 40, 40, false, false, true));
		    images [i] [j].setPickOnBounds(true);
		    pane.add(images [i] [j], j, i);
		    getNodeFromGridPane(pane, i, j).addEventFilter(MouseEvent.MOUSE_CLICKED, squareClicked);
		}
		catch (Exception e)  {
		    System.out.println(e + " loading piece icon " + currPlayer.getBoard().board [i] [j].getIcon(true));
		}
	    }
	}
	Thread awaitMove = new Thread(new BoardController ().new AwaitMove ()); 
        awaitMove.start();
    }

    private class AwaitMove implements Runnable  { 	
        public void run ()  { 
	    while (true)  {
		// Not great style I know, but whatever
		try  {
		    currPlayer.board = (Board)GameSettings.in.readObject();
		}
		catch (Exception e)  {
		    System.out.println(e + " Reading board");
		}
		for (int i = 0; i != currPlayer.getBoard().BOARDSIZE; i++)  {
		    for (int j = currPlayer.getBoard().BOARDSIZE - 1; j >= 0; j--)  {
			currPlayer.getBoard().board [i] [j].setCapturable(false);
			currPlayer.getBoard().board [i] [j].setSelected(false);
		    }
		}
		switchPlayers();
		
	    }
        } 
    } 
    
    // TODO : Don't set every square, only those that need updating
    // (Re)draws the board
    private void drawBoard ()  {
	for (int i = 0; i != currPlayer.getBoard().BOARDSIZE; i++)  {
	    for (int j = 0; j != currPlayer.getBoard().BOARDSIZE; j++)  {
		images [i] [j].setImage(new Image (currPlayer.getBoard().board [i] [j].getIcon(true), 40, 40, false, false, true));
	    }
	}
    }

    private void switchPlayers ()  {
	if (currPlayer.getIsLocal() && GameSettings.twoComputers)  {
	    // Send the board
	}
	// Switch players
	if (currPlayer.equals(white))  {
	    black.setBoard(currPlayer.getBoard());
	    // Increment the current player's score
	    currPlayer = black;
	}
	else  {
	    white.setBoard(currPlayer.getBoard());
	    currPlayer = white;
	}
	// Reset option offset to 0 for the other player
	optionOffset = 0;
	for (int i = 0; i != currPlayer.getBoard().BOARDSIZE; i++)  {
	    for (int j = currPlayer.getBoard().BOARDSIZE - 1; j >= 0; j--)  {
		currPlayer.getBoard().board [i] [j].setCapturable(false);
		currPlayer.getBoard().board [i] [j].setSelected(false);
	    }
	}
	//Put the current player back into selecting mode
	selecting = true;
	placing = false;
    }

    private void updateSidebar ()  {
	pointsDisplay.setText(currPlayer.getScore() + " / 20 \u20bf");
	infoIcon.setImage(new Image(currPlayer.getBoard().board [xPos] [yPos].getIcon(false)));
	infoDesc0.setText(currPlayer.getBoard().board [xPos] [yPos].getName());
	infoDesc1.setText(currPlayer.getBoard().board [xPos] [yPos].getDesc1());
	infoDesc2.setText(currPlayer.getBoard().board [xPos] [yPos].getDesc2());
	infoDesc3.setText(currPlayer.getBoard().board [xPos] [yPos].getDesc3());
    }

    private void updateOptions ()  {
	poolOption1.setImage(new Image (currPlayer.allPieces [optionOffset].getIcon(false)));
	option1Cost.setText("" + currPlayer.allPieces [optionOffset].getCost());
	poolOption2.setImage(new Image (currPlayer.allPieces [optionOffset + 1].getIcon(false)));
	option2Cost.setText("" + currPlayer.allPieces [optionOffset + 1].getCost());
	poolOption3.setImage(new Image (currPlayer.allPieces [optionOffset + 2].getIcon(false)));
	option3Cost.setText("" + currPlayer.allPieces [optionOffset + 2].getCost());
	poolOption4.setImage(new Image (currPlayer.allPieces [optionOffset + 3].getIcon(false)));
	option4Cost.setText("" + currPlayer.allPieces [optionOffset + 3].getCost());
	poolOption5.setImage(new Image (currPlayer.allPieces [optionOffset + 4].getIcon(false)));
	option5Cost.setText("" + currPlayer.allPieces [optionOffset + 4].getCost());
    }
    
    private Node getNodeFromGridPane (GridPane gridPane, final int row, final int column)  {
	Node result = null;
	ObservableList<Node> children = gridPane.getChildren();
	for (Node node : children) {
	    if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column)  {
		result = node;
		break;
	    }
	}
	return result;
    }
}
