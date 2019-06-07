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
import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;

public class Arquivo {
    private final static String CAMINHO_ARQUIVOS_RECENTES = System.getProperty("user.home") + "/PaintinhoFeio";
    private static String ultimoDiretorioVisitado = System.getProperty("user.home");
    private static Stage primaryStage;
    private static boolean arquivoSalvo;
    private static File arquivoAtual;
    private static File arquivoSalvaRecentes;
    private static FileOutputStream saida;
    private static Queue<File> arquivosRecentes = new ArrayDeque<>();

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
                atualizaArquivosRecentes();
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
                abrirArquivoRealmente(arquivoAtual, areaDePintura);

                ultimoDiretorioVisitado = arquivoAtual.getParent();
                atualizaArquivosRecentes();
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

    public static void abrirArquivoRealmente(File arquivo, GraphicsContext areaDePintura) throws FileNotFoundException {
        Image imagem = new Image(new FileInputStream(arquivo));

        areaDePintura.getCanvas().setWidth(imagem.getWidth());
        areaDePintura.getCanvas().setHeight(imagem.getHeight());
        areaDePintura.drawImage(imagem, 0, 0);
    }

    public static void criaArquivosRecentes() throws IOException {
        File path = new File(CAMINHO_ARQUIVOS_RECENTES);

        if (!path.exists())
            path.mkdir();

        arquivoSalvaRecentes = new File(CAMINHO_ARQUIVOS_RECENTES + "/recentes.txt");

        if (!arquivoSalvaRecentes.exists()) {
            arquivoSalvaRecentes.createNewFile();
        } else {
            FileReader ler = new FileReader(arquivoSalvaRecentes);
            BufferedReader bfLer = new BufferedReader(ler);
            String linha = bfLer.readLine();
            while (linha != null) {
                arquivosRecentes.add(new File(linha));
                System.out.println(linha);
                linha = bfLer.readLine();
            }
        }
    }

    private static void atualizaArquivosRecentes() {
        if(!arquivosRecentes.contains(arquivoAtual))
            arquivosRecentes.add(arquivoAtual);

        if (arquivosRecentes.size() > 3)
            arquivosRecentes.remove();

        try {
            saida = new FileOutputStream(arquivoSalvaRecentes);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(saida));

            for (File arquivo : arquivosRecentes) {
                bw.write(arquivo.getAbsolutePath());
                bw.newLine();
            }
            bw.close();
        } catch (IOException excexao) {
            Excecoes.mensagemErro(excexao);
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

    public static Queue<File> getArquivosRecentes() {
        return arquivosRecentes;
    }
}
