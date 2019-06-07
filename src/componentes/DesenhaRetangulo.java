package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class DesenhaRetangulo extends Forma{
    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        posicao(event);
        gc.fillRect(coordenadaX, coordenadaY, largura, altura);
        finaliza();
    }
}
