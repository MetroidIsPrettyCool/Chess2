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

public class Main extends Application  {
    public static void main (String [] args)  {
	Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException  {
       	MusicController musicThread = new MusicController();
	musicThread.start();
        // Create the Pane and all Details
	Parent mainMenu = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
	// Set the Scene to the Stage
        stage.setScene(new Scene (mainMenu));
        // Set the Title to the Stage
        stage.setTitle("\u265F Chess2: Electric Boogaloo \u2654");
	stage.setResizable(false);
	stage.sizeToScene();
        // Display the Stage
        stage.show();
    }
}
