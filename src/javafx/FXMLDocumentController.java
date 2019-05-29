package javafx;

import componentes.DesenhaCaneta;
import componentes.SeguraTamanhoPincel;
import componentes.Teste;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;

public class FXMLDocumentController implements SeguraTamanhoPincel {

    @FXML
    private Canvas tela;
    private GraphicsContext areaDePintura;
    
    @FXML
    private StackPane fundo;
    
    @FXML
    private ColorPicker selecionaCor;

    @FXML
    private ChoiceBox selecionaFerramenta;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Label tamanhoLabel;

    public void initialize() {
        selecionaFerramenta.getItems().addAll("Caneta", "Borracha", "Balde", "Retangulo", "Reta");
        selecionaFerramenta.getSelectionModel().selectFirst();
        selecionaCor.setValue(Color.BLACK);
        slider.setShowTickMarks(true);
        Rectangle rect = new Rectangle();
        DesenhaCaneta caneta = new DesenhaCaneta();

        tela.widthProperty().bind(fundo.widthProperty());
        tela.heightProperty().bind(fundo.heightProperty());
        
        areaDePintura = tela.getGraphicsContext2D();
        areaDePintura.setLineWidth(2);
        
        selecionaFerramenta.setOnAction((ActionEvent) -> {
            System.out.println(selecionaFerramenta.getValue());
        });
        
        selecionaCor.setOnAction((ActionEvent) ->{
           areaDePintura.setStroke(selecionaCor.getValue());
           areaDePintura.setFill(selecionaCor.getValue());
        });
        
        slider.valueProperty().addListener((ActionEvent) ->{
            tamanhoLabel.setText(String.format("%.0f", slider.getValue()));
            areaDePintura.setLineWidth(slider.getValue());
        });

        tela.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if (selecionaFerramenta.getValue().equals("Caneta")) {
                caneta.clickDoMouse(areaDePintura, selecionaCor.getValue(), event);
            }else if(selecionaFerramenta.getValue().equals("Retangulo")){
                rect.setTranslateX(event.getX());
                rect.setTranslateY(event.getY());
                rect.setX(event.getX());
                rect.setY(event.getY());
            }
        });

        tela.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
            if (selecionaFerramenta.getValue().equals("Caneta")) {
                caneta.arrastoDoMouse(areaDePintura, selecionaCor.getValue(), event);
            }
            if (selecionaFerramenta.getValue().equals("Borracha")) {
                double size = slider.getValue();
                areaDePintura.clearRect(event.getX() - size / 2, event.getY() - size / 2, size, size);
            }else if(selecionaFerramenta.getValue().equals("Retangulo")){
                rect.setWidth(Math.abs(event.getX() - rect.getTranslateX()));
                rect.setHeight(Math.abs(event.getY() - rect.getTranslateY()));
            }
        });

        tela.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> {
            if (selecionaFerramenta.getValue().equals("Caneta")) {
                caneta.soltarClickMouse(areaDePintura, selecionaCor.getValue(), event);
            }else if(selecionaFerramenta.getValue().equals("Retangulo")){
                if(event.getX() - rect.getTranslateX() < 0 || event.getY() - rect.getTranslateY() <0){
                    rect.setX(event.getX());
                    rect.setY(event.getY());
                }    
                areaDePintura.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
        });
    }
    
    public void onSave() {
        FileChooser salvaArquivo = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo JPG (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo PNG (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilter3 = new FileChooser.ExtensionFilter("Arquivo GIF (*.gif)", "*.gif");
        salvaArquivo.getExtensionFilters().addAll(extFilter, extFilter2, extFilter3);
        salvaArquivo.setTitle("Salvar imagem");
        File salvar = salvaArquivo.showSaveDialog(null);
        
        if(salvar != null){
            try {
                Image snapshot = tela.snapshot(null, null);
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", salvar);
            } catch (IOException e) {
                System.out.println("Failed to save image: " + e);
            } 
        }
    }
    
    public void onOpen() throws FileNotFoundException{
        FileChooser escolheArquivo = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo de imagem", "*.png", "*.jpeg", "*.jpg", "*.gif");
        escolheArquivo.getExtensionFilters().add(extFilter);
        escolheArquivo.setTitle("Abrir imagem");
        File escolha = escolheArquivo.showOpenDialog(null);
        
        if(escolha != null){
            Image imagem =  new Image(new FileInputStream(escolha));
            areaDePintura.drawImage(imagem, 0, 0);
        }
    }

    public void onExit() {
        Platform.exit();
    }

    @Override
    public double getTamanhoPincel(){
        return slider.getValue();
    }
}
