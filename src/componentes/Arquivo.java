package componentes;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
import java.io.*;
import java.util.Map;
import java.util.Properties;

public class Arquivo {
    public final static String CAMINHO_ARQUIVOS_RECENTES = System.getProperty("user.home")+"/PaintinhoFeio";

    private static String ultimoDiretorioVisitado = System.getProperty("user.home");
    private static Stage primaryStage;
    private static boolean arquivoSalvo;
    private static File arquivoAtual;
    private static File arquivoSalvaRecentes;
    private static FileOutputStream saida;
    private static Properties propriedadesRecentes;
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
            salvaArquivo.setInitialDirectory(new File(ultimoDiretorioVisitado));
            arquivoAtual = salvaArquivo.showSaveDialog(primaryStage);

            if (arquivoAtual != null) {
                Image snapshot = tela.snapshot(null, null);
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", arquivoAtual);
                arquivoSalvo = true;

                ultimoDiretorioVisitado = arquivoAtual.getParent();
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

            escolheArquivo.setInitialDirectory(new File(ultimoDiretorioVisitado));
            arquivoAtual = escolheArquivo.showOpenDialog(primaryStage);

            if (arquivoAtual != null) {
                System.out.println(arquivoAtual.getAbsolutePath());
                Image imagem = new Image(new FileInputStream(arquivoAtual));

                areaDePintura.getCanvas().setWidth(imagem.getWidth());
                areaDePintura.getCanvas().setHeight(imagem.getHeight());
                areaDePintura.drawImage(imagem, 0, 0);

                ultimoDiretorioVisitado = arquivoAtual.getParent();
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

    public static void criaArquivosRecentes(Map<String, File> arquivosParaSalvar) throws IOException{
        File path = new File(CAMINHO_ARQUIVOS_RECENTES);

        if(!path.exists())
            path.mkdir();

        arquivoSalvaRecentes = new File(CAMINHO_ARQUIVOS_RECENTES + "/recentes.txt");

        if(!arquivoSalvaRecentes.exists()){
            arquivoSalvaRecentes.createNewFile();
        }
    }

    private static void atualizaArquivosRecentes() {
        if (arquivoAtual != null) {
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
