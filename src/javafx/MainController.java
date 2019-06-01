package javafx;

import componentes.Arquivo;
import componentes.DesenhaCaneta;
import componentes.DesenhaCirculo;
import componentes.DesenhaRetangulo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class MainController implements SeguraElementos{
    @FXML
    private Canvas tela;
    private GraphicsContext areaDePintura;
    @FXML
    private AnchorPane fundo;
    @FXML
    private ColorPicker selecionaCor;
    @FXML
    private ChoiceBox selecionaFerramenta;
    @FXML
    private Slider slider;
    @FXML
    private Label tamanhoLabel;
    @FXML
    private Label mensagens;
    @FXML
    private ToggleGroup ferramentas;
    private ToggleButton btnCaneta;
    private ToggleButton btnBorracha;
    private ToggleButton btnRetangulo;
    
    
    DesenhaCaneta caneta = new DesenhaCaneta();
    DesenhaRetangulo retangulo = new DesenhaRetangulo();
    DesenhaCirculo circulo = new DesenhaCirculo();
    
    
    public void initialize() {
        areaDePintura = tela.getGraphicsContext2D();
        selecionaFerramenta.getItems().addAll("Caneta", "Borracha", "Balde", "Retangulo", "Circulo");
        selecionaFerramenta.getSelectionModel().selectFirst();
        selecionaCor.setValue(Color.BLACK);
        slider.setShowTickMarks(true);

        fundo.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (fundo.getWidth() > tela.getWidth()) {
                tela.setWidth(fundo.getWidth());
            }
        });
        fundo.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (fundo.getHeight() > tela.getHeight()) {
                tela.setHeight(fundo.getHeight());
            }
        });

        areaDePintura.setLineWidth(2);

        selecionaFerramenta.setOnAction((ActionEvent) -> {
            mensagens.setText(selecionaFerramenta.getValue().toString());
            if(selecionaFerramenta.getValue().equals("Borracha")){
                selecionaCor.setDisable(true);
            }else{
                selecionaCor.setDisable(false);
            }
        });

        selecionaCor.setOnAction((ActionEvent) -> {
            areaDePintura.setStroke(selecionaCor.getValue());
            areaDePintura.setFill(selecionaCor.getValue());
        });

        slider.valueProperty().addListener((ActionEvent) -> {
            tamanhoLabel.setText(String.format("%.0f", slider.getValue()));
            areaDePintura.setLineWidth(slider.getValue());
        });

        tela.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if (selecionaFerramenta.getValue().equals("Caneta")) {
                caneta.clickDoMouse(areaDePintura, event);
            } else if (selecionaFerramenta.getValue().equals("Retangulo")) {
                retangulo.clickDoMouse(areaDePintura, event);
            } else if (selecionaFerramenta.getValue().equals("Circulo")) {
                circulo.clickDoMouse(areaDePintura, event);
            }
        });

        tela.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
            if (selecionaFerramenta.getValue().equals("Caneta")) {
                caneta.arrastoDoMouse(areaDePintura, event);
            }
            if (selecionaFerramenta.getValue().equals("Borracha")) {
                double size = slider.getValue();
                areaDePintura.clearRect(event.getX() - size / 2, event.getY() - size / 2, size, size);
            } else if (selecionaFerramenta.getValue().equals("Retangulo")) {
                retangulo.arrastoDoMouse(areaDePintura, event);
            } else if (selecionaFerramenta.getValue().equals("Circulo")) {
                circulo.arrastoDoMouse(areaDePintura, event);
            }
        });

        tela.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> {
            if (selecionaFerramenta.getValue().equals("Caneta")) {
                caneta.soltarClickMouse(areaDePintura, event);
            } else if (selecionaFerramenta.getValue().equals("Retangulo")) {
                retangulo.soltarClickMouse(areaDePintura, event);
            } else if (selecionaFerramenta.getValue().equals("Circulo")) {
                circulo.soltarClickMouse(areaDePintura, event);
            }
        });
    }

    @Override
    public Canvas getTela(){
        return tela;
    }

    @Override
    public ToggleButton getSelecionado(){
        return (ToggleButton) ferramentas.getSelectedToggle();
    }
    
    @FXML
    private void clickCaneta(ActionEvent event){
        tela.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> caneta.clickDoMouse(areaDePintura, event1));
        tela.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event1) -> caneta.arrastoDoMouse(areaDePintura, event1));
        tela.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event1) -> caneta.soltarClickMouse(areaDePintura, event1));
    }
    
    @FXML
    private void clickBorracha(ActionEvent event){
        double size = slider.getValue();
        tela.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event1) -> areaDePintura.clearRect(event1.getX() - size / 2, event1.getY() - size / 2, size, size));
    }
    
    @FXML
    private void clickRetangulo(ActionEvent event){
        tela.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> retangulo.clickDoMouse(areaDePintura, event1));
        tela.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event1) -> retangulo.arrastoDoMouse(areaDePintura, event1));
        tela.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event1) -> retangulo.soltarClickMouse(areaDePintura, event1));
    }

    public void onSave() {
        Arquivo.salvarArquivo(tela, mensagens);
    }

    public void onOpen() {
        Arquivo.abrirArquivo(areaDePintura, mensagens);
    }

    public void onExit() {
        Platform.exit();
    }
}
