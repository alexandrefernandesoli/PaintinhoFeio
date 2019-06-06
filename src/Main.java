import componentes.Arquivo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("javafx/MainFXML.fxml"));
        Parent root = loader.load();

        Arquivo.setStage(stage);
        Arquivo.criaArquivosRecentes();

        Scene scene = new Scene(root, 896, 504);

        stage.setMinWidth(896);
        stage.setMinHeight(504);
        stage.setScene(scene);
        stage.setTitle("Paintinho Feio");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
