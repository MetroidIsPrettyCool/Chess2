import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;

public class HostController {

    @FXML
    private Button gameModeButton;

    @FXML
    private Button startButton;

    @FXML
    private Pane background;

    @FXML
    private Button backButton;

    @FXML
    private RadioButton p2rButton;

    @FXML
    private RadioButton p2lButton;
    
    @FXML
    private Label portNumber;

    @FXML
    void toggleGameMode(ActionEvent event) {
	// TODO: ADD LIST OF GAMEMODES
    }

    @FXML
    void playerTwoIsRemote(ActionEvent event) {
	p2lButton.setSelected(false);
	GameSettings.twoComputers = true;
    }

    @FXML
    void playerTwoIsLocal(ActionEvent event) {
	p2rButton.setSelected(false);
	GameSettings.twoComputers = false;
    }
    
    @FXML
    void goBack(ActionEvent event) {
	try  {
	    Parent mainMenu = FXMLLoader.load(getClass().getResource("./mainMenu.fxml"));
	    backButton.getScene().setRoot(mainMenu);
	}
	catch (Exception e)  {
	    System.out.println(e);
	}
    }

    @FXML
    void startGame(ActionEvent event) {
	GameSettings.port = 0;
	portNumber.setText("Port: " + GameSettings.port);
	
	// Conection code goes here
	
	try  {
	    Parent game = FXMLLoader.load(getClass().getResource("./board.fxml"));
	    startButton.getScene().setRoot(game);
	}
	catch (Exception e)  {
	    System.out.println(e);
	}
    }

    @FXML
    public void initialize() {
        background.setStyle("-fx-background-image:url('file:./textures/menu.png')");
    }
    
}
