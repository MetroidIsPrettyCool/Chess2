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
    private Button optionsButton;

    @FXML
    private Pane background;
    
    @FXML
    void hostGame(ActionEvent event)  {
	try  {
	    Parent game = FXMLLoader.load(getClass().getResource("fxml/host.fxml"));
	    hostButton.getScene().setRoot(game);
	}
	catch (Exception e)  {
	    System.out.println(e);
	}
    }

    @FXML
    void joinGame(ActionEvent event) {
	try  {
	    Parent game = FXMLLoader.load(getClass().getResource("fxml/join.fxml"));
	    joinButton.getScene().setRoot(game);
	}
	catch (Exception e)  {
	    System.out.println(e);
	}

    }

    @FXML
    void options (ActionEvent event)  {
	try  {
	    Parent game = FXMLLoader.load(getClass().getResource("fxml/options.fxml"));
	    optionsButton.getScene().setRoot(game);
	}
	catch (Exception e)  {
	    System.out.println(e);
	}
    }

    @FXML
    void exitGame(ActionEvent event) {
	Platform.exit();
    }

    @FXML
    public void initialize() {
        background.setStyle("-fx-background-image:url('file:textures/menu.png')");
    }

}
