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
import javafx.concurrent.*;
import javafx.application.Platform;
import javafx.animation.*;
import javafx.util.Duration;
import java.time.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;

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
	if (currPlayer.allPieces [optionOffset + off].getCost() <= currPlayer.getScore())  {
	    selectedPiece = new PoolPiece(currPlayer.allPieces [optionOffset + off]);
	    System.out.println(currPlayer.getKingRank());
	    for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
		for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
		    if (selectedPiece.getPossibleMoves(currPlayer.getBoard()) [i] [j] == 1)  {
			currPlayer.getBoard().board [i] [j].setCapturable(true);
		    }
		}
	    }
	}
    }

    @FXML
    void rotateLeft (ActionEvent event) {
	if (optionOffset > 0)  optionOffset--;
    }

    @FXML
    void rotateRight (ActionEvent event) {
	if (currPlayer.allPieces.length > optionOffset + 5) optionOffset++;
    }

    public ImageView [] [] images;

    public static int xPos = 0, yPos = 0;

    public EventHandler <MouseEvent> squareClicked;

    public static Player currPlayer = null, white, black;

    public Piece selectedPiece = null;
    public Piece clickedPiece = null;

    public Board newBoard;
     
    @FXML
    public void initialize () {
	// White and black players. first arg is if they player is white, second is if the player is local
	white = new Player(true, (GameSettings.server != null) || !GameSettings.twoComputers);
	black = new Player(false, !((!GameSettings.twoComputers) ^ (GameSettings.server != null)) || !GameSettings.twoComputers);
	// First playr to play is white
	currPlayer = white;
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
		    // Sets the piece clicked on
		    clickedPiece = currPlayer.getBoard().board [xPos] [yPos];
		    // If the current player is local and the piece is capturable
		    if (currPlayer.getIsLocal() && clickedPiece.getCapturable())  {
			System.out.println(selectedPiece.getId());
			if (selectedPiece.getId() == -1)  {
			    currPlayer.setScore(currPlayer.getScore() - selectedPiece.getCost());
			}
			// Make the move chosen
			currPlayer.setBoard(selectedPiece.makeMove(currPlayer.getBoard(), clickedPiece));
			// Resets the kingInPlay flag pre-emptively
			white.setKingInPlay(false);
			black.setKingInPlay(false);
			// Perform upkeep on all pieces
		        for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
			    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
				currPlayer.getBoard().board [i] [j].upkeep();
				// Set each piece to deselected and uncapturable
				currPlayer.getBoard().board [i] [j].setSelected(false);
				currPlayer.getBoard().board [i] [j].setCapturable(false);
			    }
			}
			// Send the board to the other player
			try  {
			    if (GameSettings.twoComputers)  GameSettings.out.writeObject(currPlayer.getBoard());
			}
			catch (Exception ex)  {
			    System.out.println(ex + " Writing board");
			}
			// TODO: make the way this works change based on the gamemode
			// Update the current player's score
			currPlayer.setScore(currPlayer.getScore() + 2);
			switchPlayers();
			if (!currPlayer.getKingInPlay())  System.out.println((currPlayer.getIsWhite() ? "White " : "Black ") + "Loses");
		    }
		    // Otherwise
		    else  {
			// Set the selected piece to the piece clicked
			selectedPiece = clickedPiece;
			// Set each capturable piece to capturable if it is the same color as the player and the current player is local
			if (selectedPiece.getColor() == (currPlayer.getIsWhite() ? 0 : 1) && currPlayer.getIsLocal())  {
			    for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
				for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
				    currPlayer.getBoard().board [i] [j].setSelected(false);
				    currPlayer.getBoard().board [i] [j].setCapturable(false);
				    if (selectedPiece.getPossibleMoves(currPlayer.getBoard()) != null && selectedPiece.getPossibleMoves(currPlayer.getBoard()) [i] [j] == 1)  {
					currPlayer.getBoard().board [i] [j].setCapturable(true);
				    }
				}
			    }
			}
			// Set the piece to selected
			selectedPiece.setSelected(true);
		    }
		}
	    };
	// Sets up the board (no touching)
        for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
	    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
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

	new AnimationTimer() {
            @Override
            public void handle(long now) {
		if (selectedPiece != null && selectedPiece.getModified())  {
		    infoIcon.setImage(new Image(currPlayer.getBoard().board [xPos] [yPos].getIcon(false)));
		    infoDesc0.setText(currPlayer.getBoard().board [xPos] [yPos].getName());
		    infoDesc1.setText(currPlayer.getBoard().board [xPos] [yPos].getDesc1());
		    infoDesc2.setText(currPlayer.getBoard().board [xPos] [yPos].getDesc2());
		    infoDesc3.setText(currPlayer.getBoard().board [xPos] [yPos].getDesc3());
		}
		for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
		    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
			if (currPlayer.getBoard().board [i] [j].getModified())  {
			    images [i] [j].setImage(new Image (currPlayer.getBoard().board [i] [j].getIcon(true), 40, 40, false, false, true));
			    currPlayer.getBoard().board [i] [j].setModified(false);
			}
		    }
		}
		// TODO: Replace
		// Redraw the options pool
		redrawPool();
	    }
        }.start();
        
	Thread awaitMove = new Thread(new BoardController (). new AwaitMove ()); 
        awaitMove.start();

	redrawPool();
    }

    private class AwaitMove implements Runnable  { 	
        public void run ()  {
	    if (!GameSettings.twoComputers) return;
	    while (true)  {
		// Not great style I know, but whatever
		try  {
		    newBoard = (Board)GameSettings.in.readObject();
		    currPlayer.setBoard((Board)newBoard.clone());
		    if (currPlayer.getIsLocal())  {
			System.out.println("CRITICAL SYNCRONIZATION ERROR");
		    }
		    if (currPlayer.getBoard().board == null || currPlayer.getIsLocal())  {
			try  {
			    GameSettings.out.close();
			    GameSettings.in.close();
			    GameSettings.socket.close();
			    return;
			}
			catch (Exception ex)  {
			    System.out.println(ex + " Returning to menu");
			}
			return;
		    }
			
		}
		catch (Exception e)  {
		    System.out.println(e + " Reading board");
		    try  {
			GameSettings.out.close();
			GameSettings.in.close();
			GameSettings.socket.close();
			return;
		    }
		    catch (Exception ex)  {
			System.out.println(ex + " Returning to menu");
		    }
		}
		for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
		    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
			// If a piece is different
			if (currPlayer.getBoard().board [i] [j].getId() != newBoard.board [i] [j].getId())  currPlayer.getBoard().board [i] [j].setModified(true);
			// Set each piece to deselected and uncapturable
			currPlayer.getBoard().board [i] [j].setSelected(false);
			currPlayer.getBoard().board [i] [j].setCapturable(false);
		    }
		}
		switchPlayers();
	    }
	}
    }
    
    private void switchPlayers ()  {
	// Switch players
	try  {
	    if (currPlayer.equals(white))  {
		black.setBoard((Board)currPlayer.getBoard().clone());
		currPlayer = black;
	    }
	    else  {
		white.setBoard((Board)currPlayer.getBoard().clone());
		currPlayer = white;
	    }
	}
	catch (Exception e)  {
	    System.out.println(e + " Switching players");
	}
	// Reset option offset to 0 for the other player
	optionOffset = 0;
    }

    private void redrawPool ()  {
	if (!GameSettings.twoComputers)  {
	    pointsDisplay.setText(currPlayer.getScore() + " / 20 \u20bf");
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
	else if (white.getIsLocal())  {
	    pointsDisplay.setText(white.getScore() + " / 20 \u20bf");
	    poolOption1.setImage(new Image (white.allPieces [optionOffset].getIcon(false)));
	    option1Cost.setText("" + white.allPieces [optionOffset].getCost());
	    poolOption2.setImage(new Image (white.allPieces [optionOffset + 1].getIcon(false)));
	    option2Cost.setText("" + white.allPieces [optionOffset + 1].getCost());
	    poolOption3.setImage(new Image (white.allPieces [optionOffset + 2].getIcon(false)));
	    option3Cost.setText("" + white.allPieces [optionOffset + 2].getCost());
	    poolOption4.setImage(new Image (white.allPieces [optionOffset + 3].getIcon(false)));
	    option4Cost.setText("" + white.allPieces [optionOffset + 3].getCost());
	    poolOption5.setImage(new Image (white.allPieces [optionOffset + 4].getIcon(false)));
	    option5Cost.setText("" + white.allPieces [optionOffset + 4].getCost());
	}
	else  {
	    pointsDisplay.setText(black.getScore() + " / 20 \u20bf");
	    poolOption1.setImage(new Image (black.allPieces [optionOffset].getIcon(false)));
	    option1Cost.setText("" + black.allPieces [optionOffset].getCost());
	    poolOption2.setImage(new Image (black.allPieces [optionOffset + 1].getIcon(false)));
	    option2Cost.setText("" + black.allPieces [optionOffset + 1].getCost());
	    poolOption3.setImage(new Image (black.allPieces [optionOffset + 2].getIcon(false)));
	    option3Cost.setText("" + black.allPieces [optionOffset + 2].getCost());
	    poolOption4.setImage(new Image (black.allPieces [optionOffset + 3].getIcon(false)));
	    option4Cost.setText("" + black.allPieces [optionOffset + 3].getCost());
	    poolOption5.setImage(new Image (black.allPieces [optionOffset + 4].getIcon(false)));
	    option5Cost.setText("" + black.allPieces [optionOffset + 4].getCost());
	}
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
