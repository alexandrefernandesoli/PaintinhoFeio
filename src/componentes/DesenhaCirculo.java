package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class DesenhaCirculo extends Forma {
    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        posicao(event);
        gc.fillOval(coordenadaX, coordenadaY, largura, altura);
        finaliza();
    }
}
