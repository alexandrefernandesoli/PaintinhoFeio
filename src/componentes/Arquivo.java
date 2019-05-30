package componentes;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Arquivo {
    public static void salvarArquivo(Canvas tela, Label mensagens) {
        FileChooser salvaArquivo = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo JPG (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("Arquivo PNG (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilter3 = new FileChooser.ExtensionFilter("Arquivo GIF (*.gif)", "*.gif");
        salvaArquivo.getExtensionFilters().addAll(extFilter, extFilter2, extFilter3);
        salvaArquivo.setTitle("Salvar imagem");
        File salvar = salvaArquivo.showSaveDialog(null);

        if (salvar != null) {
            try {
                Image snapshot = tela.snapshot(null, null);
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", salvar);
            } catch (IOException e) {
                mensagens.setText("Failed to save image: " + e);
            }
        }
    }

    public static void abrirArquivo(GraphicsContext areaDePintura, Label mensagens) {
        FileChooser escolheArquivo = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivo de imagem", "*.png", "*.jpeg", "*.jpg", "*.gif");
        escolheArquivo.getExtensionFilters().add(extFilter);
        escolheArquivo.setTitle("Abrir imagem");
        File escolha = escolheArquivo.showOpenDialog(null);

        if (escolha != null) {
            try {
                Image imagem = new Image(new FileInputStream(escolha));
                areaDePintura.drawImage(imagem, 0, 0);
            } catch (FileNotFoundException e) {
                mensagens.setText("Failed to save image: " + e);
            }
        }
    }
}
