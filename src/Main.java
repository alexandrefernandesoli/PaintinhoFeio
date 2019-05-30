import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("javafx/MainFXML.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Paintinho Feio");
        stage.setMinHeight(700);
        stage.setMinWidth(700);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
