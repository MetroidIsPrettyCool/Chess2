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
	try  {
	    GameSettings.ip = InetAddress.getLocalHost();
	    
	}
	catch (Exception e)  {
	    System.out.println(e + " Setting up connection");
	}
	
	ipDisp.setText("IP Address: " + GameSettings.ip.getHostAddress());
	System.out.println(ipDisp.getText());

	if (GameSettings.twoComputers == true)  {
	    Thread initServer = new Thread(new HostController ().new StartServer ()); 
	    initServer.start();
	}

	if (GameSettings.out != null || GameSettings.twoComputers == false)  {
	    try  {
		Parent game = FXMLLoader.load(getClass().getResource("./board.fxml"));
		startButton.getScene().setRoot(game);
	    }
	    catch (Exception e)  {
	    }
	}
    }
    
    private class StartServer implements Runnable  { 
	
        public void run ()  { 
	    try  {
		GameSettings.server = new ServerSocket(GameSettings.port); 
		System.out.println("Server started"); 
	    
		System.out.println("Waiting for a client ..."); 
	    
		GameSettings.socket = GameSettings.server.accept(); 
		System.out.println("Client accepted");

		GameSettings.out = new ObjectOutputStream(GameSettings.socket.getOutputStream());
		GameSettings.in = new ObjectInputStream(GameSettings.socket.getInputStream());
	    }
	    catch (Exception e)  {
		System.out.println(e);
		return;
	    }
        } 
    } 

    @FXML
    public void initialize() {
        background.setStyle("-fx-background-image:url('file:./textures/menu.png')");
    }
    
}
