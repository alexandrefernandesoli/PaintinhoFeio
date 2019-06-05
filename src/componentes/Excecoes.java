package componentes;

import javafx.scene.control.Alert;

import java.io.IOException;

public class Excecoes {
    public static void mensagemErro(IOException erro){
        Alert dialogoErro = new Alert(Alert.AlertType.ERROR);
        dialogoErro.setTitle("Algum erro ocorreu...");
        dialogoErro.setHeaderText("Erro ao abrir/salvar imagem");
        dialogoErro.setContentText(erro.toString());
        dialogoErro.showAndWait();
    }

}
