import java.util.*;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView;  
import java.io.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class Main extends Application  {
    public static void main (String [] args)  {
	Application.launch(args);
    }
    private Scene scene;
    @Override
    public void start(Stage stage) throws IOException  {
       	MusicController musicThread = new MusicController();
	musicThread.setDaemon(true);
	musicThread.start();
        // Create the Pane and all Details
	Parent mainMenu = FXMLLoader.load(getClass().getResource("fxml/mainMenu.fxml"));
        scene = new Scene (mainMenu);
        stage.setScene(scene);
        // Set the Title to the Stage
        stage.setTitle("\u265F Chess2: Electric Boogaloo \u2654");
	stage.setResizable(false);
	stage.sizeToScene();
        // Display the Stage
        stage.show();
	// Escape always brings you back to the main menu
	scene.setOnKeyPressed(e -> {
		if (e.getCode() == KeyCode.ESCAPE) {
		    try  {
			LAN.close();
			Parent menu = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/mainMenu.fxml"));
			scene.setRoot(menu);
		    }
		    catch (Exception ex)  {
			System.out.println(ex + " Exiting Game");
		    }
		}
	    }
	    );
    }
    @Override
    public void stop ()  {
    	MusicController.exit = true;
    	LAN.close();
    }
}
