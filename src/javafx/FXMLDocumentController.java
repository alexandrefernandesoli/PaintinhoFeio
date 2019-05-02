package javafx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class FXMLDocumentController {

    @FXML
    private Canvas tela;

    @FXML
    private ColorPicker selecionaCor;

    @FXML
    private TextField tamanhoPincel;
    
    @FXML
    private ChoiceBox selecionaFerramenta;

    public void initialize() {
        selecionaFerramenta.getItems().addAll("Caneta", "Borracha", "Quadrado");
        selecionaFerramenta.getSelectionModel().selectFirst();
        selecionaCor.setValue(Color.BLACK);
        
        GraphicsContext areaDePintura = tela.getGraphicsContext2D();
        
        selecionaFerramenta.setOnMouseClicked(e ->{
            System.out.println(selecionaFerramenta.getValue());
        });
        
        tela.setOnMouseDragged(e -> {
            double size = Double.parseDouble(tamanhoPincel.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if (selecionaFerramenta.getValue().equals("Borracha")) {
                areaDePintura.clearRect(x, y, size, size);
            } else {
                areaDePintura.setFill(selecionaCor.getValue());
                areaDePintura.fillRect(x, y, size, size);
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