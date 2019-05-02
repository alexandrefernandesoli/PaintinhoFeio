package javafx;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class FXMLDocumentController {

    @FXML
    private Canvas tela;
    
    @FXML
    private ColorPicker selecionaCor;

    @FXML
    private ChoiceBox selecionaFerramenta;
    
    @FXML
    private Slider slider;

    public void initialize() {
        selecionaFerramenta.getItems().addAll("Caneta", "Borracha", "Balde", "Quadrado");
        selecionaFerramenta.getSelectionModel().selectFirst();
        selecionaCor.setValue(Color.BLACK);
        slider.setShowTickLabels(true);

        GraphicsContext areaDePintura = tela.getGraphicsContext2D();

        selecionaFerramenta.setOnAction((ActionEvent) -> {
            System.out.println(selecionaFerramenta.getValue());
        });
        
        selecionaCor.setOnAction((ActionEvent) ->{
           areaDePintura.setStroke(selecionaCor.getValue());
        });
        
        slider.setOnMouseDragged((ActionEvent) ->{
            System.out.println((int) slider.getValue());
        });

        tela.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selecionaFerramenta.getValue().equals("Caneta")) {
                    double size = slider.getValue();
                    areaDePintura.beginPath();
                    areaDePintura.moveTo(event.getX(), event.getY());
                    areaDePintura.setLineCap(StrokeLineCap.ROUND);
                    areaDePintura.setLineWidth(size);
                    areaDePintura.stroke();
                }
            }
        });

        tela.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selecionaFerramenta.getValue().equals("Caneta")) {
                    areaDePintura.lineTo(event.getX(), event.getY());
                    areaDePintura.stroke();
                    areaDePintura.closePath();
                    areaDePintura.beginPath();
                    areaDePintura.moveTo(event.getX(), event.getY());
                }
                if (selecionaFerramenta.getValue().equals("Borracha")) {
                    double size = slider.getValue();
                    areaDePintura.clearRect(event.getX() - size / 2, event.getY() - size / 2, size, size);
                }
            }
        });

        tela.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selecionaFerramenta.getValue().equals("Caneta")) {
                    areaDePintura.lineTo(event.getX(), event.getY());
                    areaDePintura.stroke();
                    areaDePintura.closePath();
                }
            }
        });
    }

    public void onSave() {
        try {
            Image snapshot = tela.snapshot(null, null);

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e);
        }
    }

    public void onExit() {
        Platform.exit();
    }
}
