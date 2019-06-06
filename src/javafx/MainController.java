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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController implements SeguraElementos {
    @FXML
    private Canvas tela;
    private GraphicsContext areaDePintura;
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
        Map<String, File> teste = new HashMap<>();
        teste.put("asd", new File("teste.txt"));

        try{
            Arquivo.criaArquivosRecentes(teste);
        }catch (IOException excecao){
            Excecoes.mensagemErro(excecao);
        }
        SelecionaFerramenta selecionaFerramenta = new SelecionaFerramenta();
        arquivosRecentes.getItems().add(new MenuItem("Arquivo exemplo"));
        configuraFerramentas();
        estadosDesfazer(0);
        adicionaListeners();

        tela.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent evento) -> {
            txtTexto.setVisible(false);
            estadosDesfazer(1);
            desfazerRefazer.copia(tela);
            Arquivo.setArquivoSalvo(false);
            selecionaFerramenta.clickDoMouse(areaDePintura, this, evento);
        });

        tela.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent evento) ->
                selecionaFerramenta.arrastoDoMouse(areaDePintura, this, evento));

        tela.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent evento) ->
                selecionaFerramenta.soltarClickMouse(areaDePintura, this, evento));
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


    private void estadosDesfazer(int estado) {
        switch (estado) {
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

        slider.valueProperty().addListener((ActionEvent) ->
                tamanhoLabel.setText(String.format("%.0f", slider.getValue())));

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

        Arquivo.setArquivoSalvo(true);
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

    @FXML
    public void onSave() {
        try {
            Arquivo.salvarArquivo(tela);
        } catch (IOException excecao) {
            Excecoes.mensagemErro(excecao);
        }
    }

    @FXML
    public void onOpen() {
        try {
            Arquivo.abrirArquivo(areaDePintura);
        } catch (IOException excecao) {
            Excecoes.mensagemErro(excecao);
        }
    }

    @FXML
    public void onRedimensionar() {
        ChoiceDialog dialogoRedimensionamento = new ChoiceDialog("768x432", "896x504", "1024x768", "1280x720", "1920x1080");
        dialogoRedimensionamento.setTitle("Redimensionar area de pintura");
        dialogoRedimensionamento.setHeaderText("Escolha uma das opções de tamanho!");
        dialogoRedimensionamento.setContentText("Tamanhos:");
        dialogoRedimensionamento.showAndWait().ifPresent(opcao -> {
            String[] tamanho = ((String) opcao).split("x");
            tela.setWidth(Double.parseDouble(tamanho[0]));
            tela.setHeight(Double.parseDouble(tamanho[1]));
        });
    }

    @FXML
    public void onExit() {
        Platform.exit();
    }
}
