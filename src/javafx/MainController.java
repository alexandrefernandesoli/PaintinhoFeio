package javafx;

import componentes.Arquivo;
import componentes.DesenhaCaneta;
import componentes.DesenhaCirculo;
import componentes.DesenhaReta;
import componentes.DesenhaRetangulo;
import componentes.EscreveTexto;
import componentes.UndoRedo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

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


    private DesenhaCaneta caneta = new DesenhaCaneta();
    private DesenhaRetangulo retangulo = new DesenhaRetangulo();
    private DesenhaCirculo circulo = new DesenhaCirculo();
    private EscreveTexto texto = new EscreveTexto();
    private DesenhaReta reta = new DesenhaReta();
    private UndoRedo undoRedo = new UndoRedo();
    private boolean undo = false;

    public void initialize() {
        arquivosRecentes.getItems().add(new MenuItem("Arquivo exemplo"));
        areaDePintura = tela.getGraphicsContext2D();
        selecionaCor.setValue(Color.BLACK);
        slider.setShowTickMarks(true);
        txtTexto.setVisible(false);
        statesUndoRedo(0);

        resize();

        areaDePintura.setLineWidth(2);

        tbBorracha.selectedProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo) {
                selecionaCor.setDisable(true);
            } else {
                selecionaCor.setDisable(false);
            }
        });

        ferramentas.selectedToggleProperty().addListener((obs, valorAntigo, valorNovo) -> {
            if (valorNovo == null)
                valorAntigo.setSelected(true);
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
            txtTexto.setVisible(false);
            statesUndoRedo(1);

            undoRedo.copia(tela);
            if (tbCaneta.isSelected()) {
                caneta.clickDoMouse(areaDePintura, event);
            } else if (tbRetangulo.isSelected()) {
                retangulo.clickDoMouse(areaDePintura, event);
            } else if (tbCirculo.isSelected()) {
                circulo.clickDoMouse(areaDePintura, event);
            } else if (tbTexto.isSelected()) {
                texto.clickDoMouse(txtTexto, areaDePintura, event);
            } else if (tbReta.isSelected()) {
                reta.clickDoMouse(areaDePintura, event);
            } else if (tbBalde.isSelected()) {

            }
            undo = true;
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
            } else if (tbReta.isSelected()) {
                reta.arrastoDoMouse(areaDePintura, event);
            }
        });

        tela.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> {
            if (tbCaneta.isSelected()) {
                caneta.soltarClickMouse(areaDePintura, event);
            } else if (tbRetangulo.isSelected()) {
                retangulo.soltarClickMouse(areaDePintura, event);
            } else if (tbCirculo.isSelected()) {
                circulo.soltarClickMouse(areaDePintura, event);
            } else if (tbTexto.isSelected()) {
                texto.soltarClickMouse(txtTexto, areaDePintura, event);

            } else if (tbReta.isSelected()) {
                reta.soltarClickMouse(areaDePintura, event);
            }
        });
    }

    @Override
    public Canvas getTela() {
        return tela;
    }

    @Override
    public ToggleButton getSelecionado() {
        return (ToggleButton) ferramentas.getSelectedToggle();
    }

    @FXML
    public void clickUndo() {
        undoRedo.clickUndo(tela);
        statesUndoRedo(2);
    }

    @FXML
    public void clickRedo() {
        undoRedo.redo(tela);
        statesUndoRedo(3);
    }

    public void resize() {
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

    public void statesUndoRedo(int i) {
        if (i == 0) {
            btnRedo.setDisable(true);
            btnUndo.setDisable(true);
        }
        if (i == 1) {
            btnRedo.setDisable(true);
            btnUndo.setDisable(false);
        }
        if (i == 2) {
            btnUndo.setDisable(true);
            btnRedo.setDisable(false);
        }
        if (i == 3) {
            btnUndo.setDisable(false);
            btnRedo.setDisable(true);
        }
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
