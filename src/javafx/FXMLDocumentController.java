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
        selecionaFerramenta.getItems().addAll("Caneta", "Borracha", "Balde", "Circulo");
        selecionaFerramenta.getSelectionModel().selectFirst();
        selecionaCor.setValue(Color.BLACK);
        
        
        
        GraphicsContext areaDePintura = tela.getGraphicsContext2D();
        /*
        areaDePintura.setFill(Color.GREEN);
        areaDePintura.setStroke(Color.BLUE);
        areaDePintura.setLineWidth(5);
        areaDePintura.strokeLine(40, 10, 10, 40);
        areaDePintura.fillOval(10, 60, 30, 30);
        areaDePintura.strokeOval(60, 60, 30, 30);
        */
        selecionaFerramenta.setOnMouseClicked(e ->{
            System.out.println(selecionaFerramenta.getValue());
        });
        
        tela.setOnMouseClicked(e ->{
            if(selecionaFerramenta.getValue().equals("Balde")){
                areaDePintura.setFill(selecionaCor.getValue());
                areaDePintura.fillRect(0, 0, tela.getWidth(), tela.getHeight());
            }else if(selecionaFerramenta.getValue().equals("Caneta")){
                double size = Double.parseDouble(tamanhoPincel.getText());
                double x = e.getX() - size / 2;
                double y = e.getY() - size / 2;
                areaDePintura.setFill(selecionaCor.getValue());
                areaDePintura.fillOval(x, y, size, size);
            }
        });
        tela.setOnMouseDragged(e -> {
            double size = Double.parseDouble(tamanhoPincel.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;

            if (selecionaFerramenta.getValue().equals("Borracha")) {
                areaDePintura.clearRect(x, y, size, size);
            } else {
                areaDePintura.setFill(selecionaCor.getValue());
                areaDePintura.fillOval(x, y, size, size);
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