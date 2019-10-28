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

public class MenuController {

    @FXML
    private Button exitButton;

    @FXML
    private Button joinButton;

    @FXML
    private Button hostButton;

    @FXML
    private Pane background;
    
    @FXML
    void hostGame(ActionEvent event)  {
	try  {
	    Parent game = FXMLLoader.load(getClass().getResource("./board.fxml"));
	    hostButton.getScene().setRoot(game);
	}
	catch (Exception e)  {
	    System.out.println(e);
	}
    }

    @FXML
    void joinGame(ActionEvent event) {

    }

    @FXML
    void exitGame(ActionEvent event) {
	Platform.exit();
    }

    @FXML
    public void initialize() {
        background.setStyle("-fx-background-image:url('file:./textures/menu.png')");
    }

}
