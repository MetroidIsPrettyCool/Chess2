import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.event.*;

public class WinController {

    @FXML
    private Button returnToMenu;

    @FXML
    private Label message;

    @FXML
    private Pane background;

    @FXML
    void goBack(ActionEvent event)  {
	try  {
	    Parent menu = FXMLLoader.load(getClass().getResource("fxml/mainMenu.fxml"));
	    background.getScene().setRoot(menu);
	    if (GameSettings.twoComputers && GameSettings.socket != null)  {
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
	    System.out.println(ex + " Exiting Game");
	}
    }

    @FXML
    public void initialize ()  {
	try  {
	    background.setStyle("-fx-background-image:url('file:textures/menu.png')");
	}
	catch (Exception e)  {
	    System.out.println(e + " Loading board background");
	}
	if (GameSettings.twoComputers)  {
	    if (BoardController.white.getKingInPlay() && BoardController.white.getIsLocal())  message.setText("You Win!");
	    else if (BoardController.black.getKingInPlay() && BoardController.black.getIsLocal())  message.setText("You Win!");
	    else  message.setText("You Lose!");
	}
	else  {
	    if (BoardController.white.getKingInPlay())  message.setText("White Wins!");
	    else  message.setText("Black Wins!");
	}
    }
}
