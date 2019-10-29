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
    void poolOption1Selected(Event event) {
	
    }

    @FXML
    void poolOption2Clicked(Event event) {

    }

    @FXML
    void poolOption3Selected(Event event) {

    }

    @FXML
    void poolOption4Selected(Event event) {

    }

    @FXML
    void poolOption5Selected(Event event) {

    }

    public ImageView [] [] images;

    public static int xPos = 0, yPos = 0;

    public EventHandler <MouseEvent> squareClicked;

    public Player currPlayer = null, white, black;

    public Boolean selecting = true;

    public Piece selectedPiece;
    
    @FXML
    public void initialize() {
	// White and black players. first arg is if they player is white, second is if the player is local
	white = new Player(true, true);
	black = new Player(false, true);
	// First playr to play is white
	currPlayer = white;
	// Stores what texture is in each square
	images = new ImageView [currPlayer.getBoard().BOARDSIZE] [currPlayer.getBoard().BOARDSIZE];
	// Sets the background of the board
	boardBackground.setStyle("-fx-background-image:url('file:" + currPlayer.getBoard().boardImage + "')");
	// When a player clicks on a square, this function is invoked
	squareClicked = new EventHandler <MouseEvent> ()  {
		@Override
		public void handle(MouseEvent e) {
		    // Deselects the previously selected square
		    currPlayer.getBoard().board [xPos] [yPos].setSelected(false);
		    // Sets xPos and yPos to the x and y coordinates of the square clicked
		    xPos = pane.getRowIndex((ImageView)e.getSource()) == null ? 0 : pane.getRowIndex((ImageView)e.getSource());
		    yPos = pane.getColumnIndex((ImageView)e.getSource()) == null ? 0 : pane.getColumnIndex((ImageView)e.getSource());
		    // If the current player isn't in selection mode and if the piece selected is set to be capturable
		    if (!selecting && currPlayer.getBoard().board [xPos] [yPos].getCapturable())  {
			currPlayer.setBoard(currPlayer.getBoard().board [xPos] [yPos].makeMove(currPlayer.getBoard(), selectedPiece, xPos, yPos));
			// Increment the current player's score
			currPlayer.setScore(currPlayer.getScore() + 1);
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
			// Redraw the board
			drawBoard();
			for (int i = 0; i != currPlayer.getBoard().BOARDSIZE; i++)  {
			    for (int j = currPlayer.getBoard().BOARDSIZE - 1; j >= 0; j--)  {
				currPlayer.getBoard().board [i] [j].setCapturable(false);
				currPlayer.getBoard().board [i] [j].setSelected(false);
			    }
			}
			//Put the current player back into selecting mode
			selecting = true;
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
			// If the piece selected has possible moves and is the same color as the current player
			if (currPlayer.getBoard().board [xPos] [yPos].getPossibleMoves(currPlayer.getBoard()) != null && (currPlayer.getBoard().board [xPos] [yPos].getColor() == 0 ? true : false) == currPlayer.getIsWhite())  {
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
		    // Redraw the board
		    drawBoard();
		    // Updates the sidebar
		    updateSidebar();
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

    private void updateSidebar ()  {
	pointsDisplay.setText(currPlayer.getScore() + " / 20 \u20bf");
	infoIcon.setImage(new Image(currPlayer.getBoard().board [xPos] [yPos].getIcon(false)));
	infoDesc0.setText(currPlayer.getBoard().board [xPos] [yPos].getName());
	infoDesc1.setText(currPlayer.getBoard().board [xPos] [yPos].getDesc1());
	infoDesc2.setText(currPlayer.getBoard().board [xPos] [yPos].getDesc2());
	infoDesc3.setText(currPlayer.getBoard().board [xPos] [yPos].getDesc3());
	boardBackground.setStyle("-fx-background-image:url('file:" + currPlayer.getBoard().boardImage + "')");
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
