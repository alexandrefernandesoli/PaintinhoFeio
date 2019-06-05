package componentes;

import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class RedimensionarCanvas {
    public static void redimensionaCanvas(Canvas tela, Stage primaryStage) {
        tela.widthProperty().bind(primaryStage.widthProperty());
        tela.heightProperty().bind(primaryStage.heightProperty());
    }
}
