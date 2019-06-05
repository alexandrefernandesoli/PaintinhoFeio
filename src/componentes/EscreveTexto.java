package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * @author Felipe Hiroshi
 */
class EscreveTexto {
    private double coordenadaX;
    private double coordenadaY;

    void clickDoMouse(TextField txtTexto, GraphicsContext areaDePintura, MouseEvent event) {
        txtTexto.setVisible(true);
        txtTexto.requestFocus();
        if (txtTexto.getText().equals("")) {
            coordenadaX = event.getX();
            coordenadaY = event.getY();
        }
        txtTexto.setTranslateX(coordenadaX);
        txtTexto.setTranslateY(coordenadaY);
        if (!txtTexto.getText().equals(""))
            txtTexto.setVisible(false);
        areaDePintura.fillText(txtTexto.getText(), coordenadaX, coordenadaY + 20);
    }

    void soltarClickMouse(TextField txtTexto, GraphicsContext areaDePintura, MouseEvent event) {
        txtTexto.clear();
    }

}
