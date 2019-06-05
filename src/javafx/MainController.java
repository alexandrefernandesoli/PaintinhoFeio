package javafx;

import componentes.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class MainController implements SeguraElementos {
    @FXML
    private Canvas tela;
    private GraphicsContext areaDePintura;
    @FXML
    private AnchorPane fundo;
    @FXML
    private ColorPicker selecionaCor;
    @FXML
    private Slider slider;
    @FXML
    private Label tamanhoLabel;
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
    private ToggleButton tbReta;
    @FXML
    private ToggleButton tbBalde;
    @FXML
    private TextField txtTexto;
    @FXML
    private Button btnUndo;
    @FXML
    private Button btnRedo;
    @FXML
    private Menu arquivosRecentes;

    private DesfazerRefazer desfazerRefazer = new DesfazerRefazer();

    public void initialize() {
        SelecionaFerramenta selecionaFerramenta = new SelecionaFerramenta();
        arquivosRecentes.getItems().add(new MenuItem("Arquivo exemplo"));

        configuraFerramentas();
        estadosDesfazer(0);
        redimensionaCanvas();
        adicionaListeners();

        tela.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent evento) -> {
            estadosDesfazer(1);
            desfazerRefazer.copia(tela);
            selecionaFerramenta.clickDoMouse(areaDePintura, this, evento);
        });

        tela.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent evento) -> {
            selecionaFerramenta.arrastoDoMouse(areaDePintura, this, evento);
        });

        tela.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent evento) -> {
            selecionaFerramenta.soltarClickMouse(areaDePintura, this, evento);
        });
    }

    @FXML
    public void clickUndo() {
        desfazerRefazer.clickUndo(tela);
        estadosDesfazer(2);
    }

    @FXML
    public void clickRedo() {
        desfazerRefazer.redo(tela);
        estadosDesfazer(3);
    }

    /**
     * Redimensiona o espaço de desenho conforme o tamanho da tela
     */
    private void redimensionaCanvas() {
        fundo.widthProperty().addListener((obs) -> {
            if (fundo.getWidth() > tela.getWidth()) {
                tela.setWidth(fundo.getWidth());
            }
        });
        fundo.heightProperty().addListener((obs) -> {
            if (fundo.getHeight() > tela.getHeight()) {
                tela.setHeight(fundo.getHeight());
            }
        });
    }

    private void estadosDesfazer(int estado) {
        switch (estado){
            case 0:
                btnRedo.setDisable(true);
                btnUndo.setDisable(true);
                break;
            case 1:
                btnRedo.setDisable(true);
                btnUndo.setDisable(false);
                break;
            case 2:
                btnUndo.setDisable(true);
                btnRedo.setDisable(false);
                break;
            case 3:
                btnUndo.setDisable(false);
                btnRedo.setDisable(true);
                break;
            default:
                break;
        }
    }

    /**
     * Atualiza atributos de alguns elementos do programa conforme interação com o usuario
     * - Desabilita seleção de cor quando a ferramenta borracha for selecionada
     * - Garante que sempre pelo menos uma ferramenta está selecionada
     * - Atualiza label quando há uma mudança no slider
     */
    private void adicionaListeners() {
        tbBorracha.selectedProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo) {
                selecionaCor.setDisable(true);
            } else {
                selecionaCor.setDisable(false);
            }
        });

        slider.valueProperty().addListener((ActionEvent) -> {
            tamanhoLabel.setText(String.format("%.0f", slider.getValue()));
        });

        ferramentas.selectedToggleProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo == null)
                valorAntigo.setSelected(true);
        });
    }

    private void configuraFerramentas() {
        tbBorracha.setUserData("borracha");
        tbCaneta.setUserData("caneta");
        tbBalde.setUserData("balde");
        tbCirculo.setUserData("circulo");
        tbReta.setUserData("reta");
        tbRetangulo.setUserData("retangulo");
        tbTexto.setUserData("texto");

        areaDePintura = tela.getGraphicsContext2D();
        selecionaCor.setValue(Color.BLACK);
        slider.setShowTickMarks(true);
        txtTexto.setVisible(false);
        areaDePintura.setLineWidth(2);
    }

    @Override
    public double getTamanho() {
        return slider.getValue();
    }

    @Override
    public Paint getCor() {
        return selecionaCor.getValue();
    }

    @Override
    public ToggleButton getFerramenta() {
        return (ToggleButton) ferramentas.getSelectedToggle();
    }

    @Override
    public TextField getTexto() {
        return txtTexto;
    }

    public void onSave() {
        Arquivo.salvarArquivo(tela);
    }

    public void onOpen() {
        Arquivo.abrirArquivo(areaDePintura);
    }

    public void onExit() {
        Platform.exit();
    }
}
