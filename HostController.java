import javafx.application.Application;
import javafx.application.Platform;
import javafx.animation.*;
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
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import java.io.*;
import java.net.*;

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
    private Label ipDisp;

    @FXML
    void toggleGameMode(ActionEvent event) {
	// TODO: ADD LIST OF GAMEMODES
    }

    private boolean lanGame = false;
    
    @FXML
    void playerTwoIsRemote(ActionEvent event) {
	lanGame = true;
	startButton.setText("Start Server!");
    }

    @FXML
    void playerTwoIsLocal(ActionEvent event) {
	lanGame = false;
	startButton.setText("Start!");
    }
    
    @FXML
    void goBack(ActionEvent event) {
	try  {
	    LAN.close();
	    Parent mainMenu = FXMLLoader.load(getClass().getResource("fxml/mainMenu.fxml"));
	    backButton.getScene().setRoot(mainMenu);
	}
	catch (Exception e)  {
	    System.out.println(e);
	}
    }

    @FXML
    void startGame(ActionEvent event) {
	try  {
	    ipDisp.setText("IP Address: " + InetAddress.getLocalHost().getHostAddress().toString());
	}
	catch (Exception e)  {
	    System.out.println(e + " Getting host Ip? Huh?");
	}
	if (LAN.getConnected() || !lanGame)  {
	    try  {
		startButton.setText("Loading...");
		startButton.setDisable(true);
		Parent game = FXMLLoader.load(getClass().getResource("fxml/board.fxml"));
		startButton.getScene().setRoot(game);
	    }
	    catch (Exception e)  {
		System.out.println(e + " Starting Game");
	    }
	    return;
	}
	startButton.setText("Waiting...");
	startButton.setDisable(true);
	LAN.host();
    }

    @FXML
    public void initialize() {
	lanGame = true;
        background.setStyle("-fx-background-image:url('file:textures/menu.png')");
	final ToggleGroup group = new ToggleGroup();
	p2rButton.setToggleGroup(group);
	p2lButton.setToggleGroup(group);
	new AnimationTimer() {
            @Override
            public void handle(long now) {
	        if (LAN.getConnected())  {
		    startButton.setDisable(false);
		    startButton.setText("Start!");
		    this.stop();
		}
	    }
        }.start();
    }
}
