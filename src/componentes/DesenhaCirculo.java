package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class DesenhaCirculo extends Forma implements Ferramentas {
 
    @Override
    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        coordenadas(event);
    }

    @Override
    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {
        tamanho(event);
    }

    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        posicao(event);
        gc.fillOval(coordenadaX, coordenadaY, largura, altura);
        finaliza();
    }
}
