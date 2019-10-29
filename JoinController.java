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

public class JoinController {

    @FXML
    private Button startButton;

    @FXML
    private Pane background;

    @FXML
    private Button backButton;

    @FXML
    private TextField portInput;
    
    @FXML
    void startGame(ActionEvent event) {
	try  {
	    GameSettings.port = Integer.parseInt(portInput.getText());
	}
	catch (Exception e)  {
	    return;
	}
	try  {
	    Parent game = FXMLLoader.load(getClass().getResource("./board.fxml"));
	    startButton.getScene().setRoot(game);
	}
	catch (Exception e)  {
	    System.out.println(e);
	}
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
    public void initialize() {
        background.setStyle("-fx-background-image:url('file:./textures/menu.png')");
    }
    
}
