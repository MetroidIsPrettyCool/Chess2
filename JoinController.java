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
		
	    GameSettings.ip = InetAddress.getByName(ipInput.getText());
	}
	catch (Exception e)  {
	    System.out.println(e);
	    return;
	}
	Thread initClient = new Thread(new JoinController ().new StartClient ()); 
        initClient.start();
	if (GameSettings.out != null)  {
	    try  {
		Parent game = FXMLLoader.load(getClass().getResource("./board.fxml"));
		startButton.getScene().setRoot(game);
	    }
	    catch (Exception e)  {
		System.out.println(e + " Loading game board");
	    }
	}
    }

    private class StartClient implements Runnable  { 
	
        public void run ()  {
	    try  {
	        GameSettings.socket = new Socket(GameSettings.ip, GameSettings.port); 
		System.out.println("Connected"); 

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
