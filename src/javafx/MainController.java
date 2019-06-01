package javafx;

import componentes.Arquivo;
import componentes.DesenhaCaneta;
import componentes.DesenhaCirculo;
import componentes.DesenhaRetangulo;
import javafx.application.Platform;
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
    @FXML
    private ToggleButton tbCaneta;
    @FXML
    private ToggleButton tbBorracha;
    @FXML
    private ToggleButton tbRetangulo;
    @FXML
    private ToggleButton tbCirculo;
    @FXML 
    private ToggleButton tbTexto;
    @FXML
    private TextField txtTexto;
            
    DesenhaCaneta caneta = new DesenhaCaneta();
    DesenhaRetangulo retangulo = new DesenhaRetangulo();
    DesenhaCirculo circulo = new DesenhaCirculo();
    
    
    public void initialize() {
        areaDePintura = tela.getGraphicsContext2D();
        selecionaCor.setValue(Color.BLACK);
        slider.setShowTickMarks(true);
        tbCaneta.setSelected(true);
        txtTexto.setVisible(false);
        
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

//        selecionaFerramenta.setOnAction((ActionEvent) -> {
//            mensagens.setText(selecionaFerramenta.getValue().toString());
//            if(selecionaFerramenta.getValue().equals("Borracha")){
//                selecionaCor.setDisable(true);
//            }else{
//                selecionaCor.setDisable(false);
//            }
//        });

        selecionaCor.setOnAction((ActionEvent) -> {
            areaDePintura.setStroke(selecionaCor.getValue());
            areaDePintura.setFill(selecionaCor.getValue());
        });

        slider.valueProperty().addListener((ActionEvent) -> {
            tamanhoLabel.setText(String.format("%.0f", slider.getValue()));
            areaDePintura.setLineWidth(slider.getValue());
        });
        
        tela.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            if (tbCaneta.isSelected()) {
                caneta.clickDoMouse(areaDePintura, event);
            } else if (tbRetangulo.isSelected()) {
                retangulo.clickDoMouse(areaDePintura, event);
            } else if (tbCirculo.isSelected()) {
                circulo.clickDoMouse(areaDePintura, event);
            } else if(tbTexto.isSelected()){
                String texto;
                txtTexto.setVisible(true);
                txtTexto.setTranslateX(event.getX());
                txtTexto.setTranslateY(event.getY());
                texto = txtTexto.getText();
                areaDePintura.strokeText(texto, event.getX(), event.getY());
            }
        });

        tela.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
            if (tbCaneta.isSelected()) {
                caneta.arrastoDoMouse(areaDePintura, event);
            }
            if (tbBorracha.isSelected()) {
                double size = slider.getValue();
                areaDePintura.clearRect(event.getX() - size / 2, event.getY() - size / 2, size, size);
            } else if (tbRetangulo.isSelected()) {
                retangulo.arrastoDoMouse(areaDePintura, event);
            } else if (tbCirculo.isSelected()) {
                circulo.arrastoDoMouse(areaDePintura, event);
            }
        });

        tela.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> {
            if (tbCaneta.isSelected()) {
                caneta.soltarClickMouse(areaDePintura, event);
            } else if (tbRetangulo.isSelected()) {
                retangulo.soltarClickMouse(areaDePintura, event);
            } else if (tbCirculo.isSelected()) {
                circulo.soltarClickMouse(areaDePintura, event);
            } else if(tbTexto.isSelected()){
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
