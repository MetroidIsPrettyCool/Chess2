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
import javafx.scene.control.TextField;
import java.io.*;
import java.net.*;

public class JoinController {

    @FXML
    private Button startButton;

    @FXML
    private Pane background;

    @FXML
    private Button backButton;

    @FXML
    private TextField ipInput;

    @FXML
    void ipEnter(ActionEvent event) {
	try  {
	    LAN.setIp(ipInput.getText());
	}
	catch (Exception e)  {
	    System.out.println(e);
	    return;
	}
	if (LAN.getConnected())  {
	    try  {
		Parent game = FXMLLoader.load(getClass().getResource("fxml/board.fxml"));
		startButton.getScene().setRoot(game);
	    }
	    catch (Exception e)  {
		System.out.println(e + " Loading game board");
	    }
	    return;
	}
	startButton.setText("Connecting...");
	startButton.setDisable(true);
	LAN.connect();
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
	    LAN.setIp(ipInput.getText());
	}
	catch (Exception e)  {
	    System.out.println(e);
	    return;
	}
	if (LAN.getConnected())  {
	    try  {
		Parent game = FXMLLoader.load(getClass().getResource("fxml/board.fxml"));
		startButton.getScene().setRoot(game);
	    }
	    catch (Exception e)  {
		System.out.println(e + " Loading game board");
	    }
	    return;
	}
	startButton.setText("Connecting...");
	startButton.setDisable(true);
	LAN.connect();
    }

    @FXML
    public void initialize() {
        background.setStyle("-fx-background-image:url('file:textures/menu.png')");
	new AnimationTimer() {
            @Override
            public void handle(long now) {
		if (LAN.getConnected())  {
		    startButton.setDisable(false);
		    startButton.setText("Start!");
		    this.stop();
		}
		if (!LAN.getConnecting() && startButton.isDisabled())  {
		    startButton.setText("Connect!");
		    startButton.setDisable(false);
		}
	    }
        }.start();
    }
    
}
