package componentes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class Arquivo {
    private static Stage primaryStage;
    private static boolean arquivoSalvo;
    private static File arquivoAtual;
    private static Map<String, File> arquivosRecentes;

    public static void salvarArquivo(Canvas tela) throws IOException {
        if (arquivoAtual == null) {
            FileChooser salvaArquivo = new FileChooser();
            String[] extensoes = {"png", "jpg", "gif"};
            for (String extensao : extensoes) {
                FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter(extensao.toUpperCase() +
                        " (*." + extensao + ")", "*." + extensao);
                salvaArquivo.getExtensionFilters().add(filtro);
            }
            salvaArquivo.setInitialFileName("Sem título");
            salvaArquivo.setTitle("Salvar imagem");
            arquivoAtual = salvaArquivo.showSaveDialog(primaryStage);

            if (arquivoAtual != null) {
                Image snapshot = tela.snapshot(null, null);
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", arquivoAtual);
                arquivoSalvo = true;
            }
        } else {
            Image snapshot = tela.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", arquivoAtual);
            arquivoSalvo = true;
        }

        if (arquivoAtual != null)
            trocaNomeStage();
    }

    public static void abrirArquivo(GraphicsContext areaDePintura) throws IOException {
        if (arquivoSalvo) {
            FileChooser escolheArquivo = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo de imagem", "*.png", "*.jpeg", "*.jpg", "*.gif");
            escolheArquivo.getExtensionFilters().add(extFilter);
            escolheArquivo.setTitle("Abrir imagem");
            arquivoAtual = escolheArquivo.showOpenDialog(primaryStage);

            if (arquivoAtual != null) {
                System.out.println(arquivoAtual.getAbsolutePath());
                Image imagem = new Image(new FileInputStream(arquivoAtual));

                areaDePintura.getCanvas().setWidth(imagem.getWidth());
                areaDePintura.getCanvas().setHeight(imagem.getHeight());
                areaDePintura.drawImage(imagem, 0, 0);
            }
        } else {
            Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
            ButtonType btnSalvar = new ButtonType("Salvar");
            ButtonType btnDescartar = new ButtonType("Descartar");
            ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

            dialogoExe.getButtonTypes().setAll(btnSalvar, btnDescartar, btnCancelar);
            dialogoExe.setTitle("Confirmação");
            dialogoExe.setHeaderText("Você possui alterações não salvas...");
            dialogoExe.setContentText("Deseja descartar as alterações ou salvar?");


            dialogoExe.showAndWait().ifPresent(acao -> {
                if (acao == btnSalvar) {
                    try {
                        salvarArquivo(areaDePintura.getCanvas());
                    } catch (IOException excecao) {
                        Excecoes.mensagemErro(excecao);
                    }
                } else if (acao == btnDescartar) {
                    arquivoSalvo = true;
                    try {
                        abrirArquivo(areaDePintura);
                    } catch (IOException excecao) {
                        Excecoes.mensagemErro(excecao);
                    }
                }
            });
        }
        if (arquivoAtual != null)
            trocaNomeStage();
    }

    public static void criaArquivosRecentes() {
        try {
            File path = new File(System.getProperty("user.home") + "\\PaintinhoFeio");

            if (!path.exists()) {
                path.mkdirs();
            }

            File arquivosRecentesTXT = new File(path.getAbsolutePath() + "\\arquivos_recentes.txt");

            if (!arquivosRecentesTXT.exists()) {
                arquivosRecentesTXT.createNewFile();
            }
        } catch (IOException exececao) {
            Excecoes.mensagemErro(exececao);
        }
    }

    private static void atualizaArquivosRecentes() {
        if(arquivoAtual != null){
            arquivosRecentes.put(arquivoAtual.getAbsolutePath(), arquivoAtual);
        }
    }

    private static void trocaNomeStage() {
        primaryStage.setTitle("Paintinho Feio - " + arquivoAtual.getName());
    }

    public static void setArquivoSalvo(boolean estado) {
        arquivoSalvo = estado;
    }

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }
}
