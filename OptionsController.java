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
import javafx.scene.control.Label;

public class OptionsController {

    @FXML
    private Button backButton;

    @FXML
    private Pane background;

    @FXML
    void goBack(ActionEvent event) {
	try  {
	    Parent mainMenu = FXMLLoader.load(getClass().getResource("fxml/mainMenu.fxml"));
	    backButton.getScene().setRoot(mainMenu);
	}
	catch (Exception e)  {
	    System.out.println(e);
	}
    }

    @FXML
    public void initialize() {
        background.setStyle("-fx-background-image:url('file:textures/menu.png')");
    }

}
