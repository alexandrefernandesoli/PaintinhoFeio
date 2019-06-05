package componentes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Arquivo {
    private static Stage primaryStage;

    public static void salvarArquivo(Canvas tela) {
        FileChooser salvaArquivo = new FileChooser();
        String[] extensoes = {"png", "jpg", "gif"};
        for(String extensao: extensoes){
            FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Arquivo "+
                    extensao.toUpperCase()+" (*."+extensao+")","*."+extensao);
            salvaArquivo.getExtensionFilters().add(filtro);
        }
        salvaArquivo.setTitle("Salvar imagem");
        File salvar = salvaArquivo.showSaveDialog(primaryStage);

        if (salvar != null) {
            try {
                Image snapshot = tela.snapshot(null, null);
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", salvar);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public static void abrirArquivo(GraphicsContext areaDePintura) {
        FileChooser escolheArquivo = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo de imagem", "*.png", "*.jpeg", "*.jpg", "*.gif");
        escolheArquivo.getExtensionFilters().add(extFilter);
        escolheArquivo.setTitle("Abrir imagem");
        File escolha = escolheArquivo.showOpenDialog(primaryStage);

        if (escolha != null) {
            try {
                Image imagem = new Image(new FileInputStream(escolha));
                if (imagem.getWidth() > areaDePintura.getCanvas().getWidth() || imagem.getHeight() > areaDePintura.getCanvas().getHeight()) {
                    areaDePintura.getCanvas().setWidth(imagem.getWidth());
                    areaDePintura.getCanvas().setHeight(imagem.getHeight());
                }
                areaDePintura.drawImage(imagem, 0, 0);
                JOptionPane.showMessageDialog(null, "Imagem aberta com sucesso!");
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public static void setStage(Stage stage){
        primaryStage = stage;
    }
}
