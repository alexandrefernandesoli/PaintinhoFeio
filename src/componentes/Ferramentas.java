package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface Ferramentas {
    void clickDoMouse(GraphicsContext gc, MouseEvent event);

    void arrastoDoMouse(GraphicsContext gc, MouseEvent event);

    void soltarClickMouse(GraphicsContext gc, MouseEvent event);
}
