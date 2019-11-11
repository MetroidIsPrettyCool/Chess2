import java.net.URI;
import java.io.EOFException;
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
	for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
	    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
		currPlayer.getBoard().board [i] [j].upkeep();
		// Set each piece to deselected and uncapturable
		currPlayer.getBoard().board [i] [j].setSelected(false);
		currPlayer.getBoard().board [i] [j].setCapturable(false);
	    }
	}
	if (!GameSettings.twoComputers)  selectedPiece = new PoolPiece(currPlayer.allPieces [optionOffset + off]);
	else if (white.getIsLocal())  selectedPiece = new PoolPiece(white.allPieces [optionOffset + off]);
	else  selectedPiece = new PoolPiece(black.allPieces [optionOffset + off]);
	if (currPlayer.getIsLocal() && currPlayer.allPieces [optionOffset + off].getCost() <= currPlayer.getScore() && selectedPiece.getPossibleMoves(currPlayer.getBoard()) != null)  {
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
    public void initialize ()  {
	// White and black players. first arg is if they player is white, second is if the player is local
	white = new Player(true, (GameSettings.server != null) || !GameSettings.twoComputers);
	black = new Player(false, !((!GameSettings.twoComputers) ^ (GameSettings.server != null)) || !GameSettings.twoComputers);
	// First playr to play is white
	currPlayer = white;
	// Stores what texture is in each square
	images = new ImageView [GameSettings.BOARDSIZE] [GameSettings.BOARDSIZE];
	// Sets the background of the board
        boardBackground.setStyle("-fx-background-image:url('file:textures/"  + GameSettings.boardTexture + "/" + currPlayer.getBoard().boardImage + "')");
	// When a player clicks on a square, this function is invoked
	squareClicked = new EventHandler <MouseEvent> ()  {
		@Override
		public void handle (MouseEvent e) {
		    // Deselects previous square
		    if (selectedPiece != null)  selectedPiece.setSelected(false);
		    // Sets xPos and yPos to the x and y coordinates of the square clicked
		    xPos = pane.getRowIndex((ImageView)e.getSource()) == null ? 0 : pane.getRowIndex((ImageView)e.getSource());
		    yPos = pane.getColumnIndex((ImageView)e.getSource()) == null ? 0 : pane.getColumnIndex((ImageView)e.getSource());
		    // Sets the piece clicked on
		    clickedPiece = currPlayer.getBoard().board [xPos] [yPos];
		    // If the current player is local and the piece is capturable
		    if (currPlayer.getIsLocal() && clickedPiece.getCapturable())  {
			if (selectedPiece.getId() == -1)  {
			    currPlayer.setScore(currPlayer.getScore() - selectedPiece.getCost());
			}
			// Make the move chosen
			currPlayer.setBoard(selectedPiece.makeMove(currPlayer.getBoard(), clickedPiece));
			// Resets the kingInPlay flag pre-emptively
			white.setKingInPlay(false);
			black.setKingInPlay(false);
		        white.setKingRank(-1);
			black.setKingRank(-1);
			// Perform upkeep on all pieces
		        for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
			    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
				currPlayer.getBoard().board [i] [j].upkeep();
			    }
			}
			// TODO: make the way this works change based on the gamemode
			// Update the current player's score
			if (currPlayer.getScore() + (GameSettings.BOARDSIZE - (currPlayer.getKingRank()) / 2) <= 20)
			    currPlayer.setScore(currPlayer.getScore() + (GameSettings.BOARDSIZE - currPlayer.getKingRank()) / 2);
			switchPlayers();
			// Send the board to the other player
			if (GameSettings.twoComputers)  {
			    try  {
				GameSettings.out.writeObject(currPlayer.getBoard());
			    }
			    catch (Exception ex)  {
				System.out.println(ex + " Writing board");
			    }
			}
			for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
			    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
				currPlayer.getBoard().board [i] [j].setSelected(false);
				currPlayer.getBoard().board [i] [j].setCapturable(false);
			    }
			}
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
					if (selectedPiece.canMakeMove(currPlayer.getBoard().board [i] [j]))  currPlayer.getBoard().board [i] [j].setCapturable(true);
				    }
				}
			    }
			}
			else  {
			    for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
				for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
				    currPlayer.getBoard().board [i] [j].setSelected(false);
				    currPlayer.getBoard().board [i] [j].setCapturable(false);
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
		    images [i] [j] = new ImageView (currPlayer.getBoard().board [i] [j].getIcon(true, false));
		    images [i] [j].setPickOnBounds(true);
		    pane.add(images [i] [j], j, i);
		    getNodeFromGridPane(pane, i, j).addEventFilter(MouseEvent.MOUSE_CLICKED, squareClicked);
		}
		catch (Exception e)  {
		    System.out.println(e + " loading piece icon " + currPlayer.getBoard().board [i] [j].getIcon(true, false));
		}
	    }
	}

	new AnimationTimer() {
            @Override
            public void handle(long now) {
		if (!white.getKingInPlay() || !black.getKingInPlay())  {
		    try  {
			Parent menu = FXMLLoader.load(getClass().getResource("fxml/winScreen.fxml"));
			boardBackground.getScene().setRoot(menu);
			this.stop();
			return;
		    }
		    catch (Exception e)  {
			System.out.println(e + " Loading Win Screen");
		    }
		}
		if (selectedPiece != null && selectedPiece.getModified())  {
		    infoIcon.setImage(selectedPiece.getIcon(false, true));
		    infoDesc0.setText(selectedPiece.getName());
		    infoDesc1.setText(selectedPiece.getDesc1());
		    infoDesc2.setText(selectedPiece.getDesc2());
		    infoDesc3.setText(selectedPiece.getDesc3());
		}
		for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
		    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
			if (currPlayer.getBoard().board [i] [j].getModified())  {
			    images [i] [j].setImage(currPlayer.getBoard().board [i] [j].getIcon(true, false));
			    currPlayer.getBoard().board [i] [j].setModified(false);
			}
		    }
		}
		if (GameSettings.socket == null && GameSettings.twoComputers)  {
		    try  {
			Parent menu = FXMLLoader.load(getClass().getResource("./mainMenu.fxml"));
			boardBackground.getScene().setRoot(menu);	
		    }
		    catch (Exception e)  {
			System.out.println(e + " Returning to menu");
		    }
		    this.stop();
		    return;
		}
		// TODO: Replace
		// Redraw the options pool
		redrawPool();
	    }
        }.start();
        
	Thread awaitMove = new Thread(new BoardController (). new AwaitMove ()); 
        awaitMove.start();

	redrawPool();

	if (black.getIsLocal())  {
	    currPlayer.getBoard().flip();
	    black.getBoard().flip();
	}
	if (!GameSettings.twoComputers) white.getBoard().flip();
    }

    private class AwaitMove implements Runnable  { 	
        public void run ()  {
	    if (!GameSettings.twoComputers) return;
	    while (true)  {
		try  {
		    newBoard = (Board)GameSettings.in.readObject();
		    newBoard.flip();
		    for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
			for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
			    newBoard.board [i] [j].setModified(false);
			    if (!newBoard.board [i] [j].getIcon(true, false).equals(currPlayer.getBoard().board [i] [j].getIcon(true, false)))  newBoard.board [i] [j].setModified(true);
			}
		    }
		    currPlayer.setBoard((Board)newBoard.clone());
		}
		catch (Exception e)  {
		    System.out.println(e + " Reading board");
		    try  {
			if (GameSettings.socket != null)  {
			    GameSettings.out.close();
			    GameSettings.in.close();
			    GameSettings.socket.close();
			    GameSettings.socket = null;
			    if (GameSettings.server != null)  {
				GameSettings.server.close();
				GameSettings.server = null;
			    }
			}
		    }
		    catch (Exception ex)  {
			System.out.println(ex + " Closing down server");
		    }
		    return;
		}
		// Resets the kingInPlay flag pre-emptively
		white.setKingInPlay(false);
		black.setKingInPlay(false);
		white.setKingRank(-1);
		black.setKingRank(-1);
		// Perform upkeep on all pieces
		for (int i = 0; i != GameSettings.BOARDSIZE; i++)  {
		    for (int j = 0; j != GameSettings.BOARDSIZE; j++)  {
			currPlayer.getBoard().board [i] [j].upkeep();
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
	    if (!GameSettings.twoComputers)  currPlayer.getBoard().flip();
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
	    poolOption1.setImage(currPlayer.allPieces [optionOffset].getIcon(false, true));
	    option1Cost.setText("" + currPlayer.allPieces [optionOffset].getCost());
	    poolOption2.setImage(currPlayer.allPieces [optionOffset + 1].getIcon(false, true));
	    option2Cost.setText("" + currPlayer.allPieces [optionOffset + 1].getCost());
	    poolOption3.setImage(currPlayer.allPieces [optionOffset + 2].getIcon(false, true));
	    option3Cost.setText("" + currPlayer.allPieces [optionOffset + 2].getCost());
	    poolOption4.setImage(currPlayer.allPieces [optionOffset + 3].getIcon(false, true));
	    option4Cost.setText("" + currPlayer.allPieces [optionOffset + 3].getCost());
	    poolOption5.setImage(currPlayer.allPieces [optionOffset + 4].getIcon(false, true));
	    option5Cost.setText("" + currPlayer.allPieces [optionOffset + 4].getCost());
	}
	else if (white.getIsLocal())  {
	    pointsDisplay.setText(white.getScore() + " / 20 \u20bf");
	    poolOption1.setImage(white.allPieces [optionOffset].getIcon(false, true));
	    option1Cost.setText("" + white.allPieces [optionOffset].getCost());
	    poolOption2.setImage(white.allPieces [optionOffset + 1].getIcon(false, true));
	    option2Cost.setText("" + white.allPieces [optionOffset + 1].getCost());
	    poolOption3.setImage(white.allPieces [optionOffset + 2].getIcon(false, true));
	    option3Cost.setText("" + white.allPieces [optionOffset + 2].getCost());
	    poolOption4.setImage(white.allPieces [optionOffset + 3].getIcon(false, true));
	    option4Cost.setText("" + white.allPieces [optionOffset + 3].getCost());
	    poolOption5.setImage(white.allPieces [optionOffset + 4].getIcon(false, true));
	    option5Cost.setText("" + white.allPieces [optionOffset + 4].getCost());
	}
	else  {
	    pointsDisplay.setText(black.getScore() + " / 20 \u20bf");
	    poolOption1.setImage(black.allPieces [optionOffset].getIcon(false, true));
	    option1Cost.setText("" + black.allPieces [optionOffset].getCost());
	    poolOption2.setImage(black.allPieces [optionOffset + 1].getIcon(false, true));
	    option2Cost.setText("" + black.allPieces [optionOffset + 1].getCost());
	    poolOption3.setImage(black.allPieces [optionOffset + 2].getIcon(false, true));
	    option3Cost.setText("" + black.allPieces [optionOffset + 2].getCost());
	    poolOption4.setImage(black.allPieces [optionOffset + 3].getIcon(false, true));
	    option4Cost.setText("" + black.allPieces [optionOffset + 3].getCost());
	    poolOption5.setImage(black.allPieces [optionOffset + 4].getIcon(false, true));
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
