package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public interface Ferramentas {
    void clickDoMouse(GraphicsContext gc, Paint cor, MouseEvent event);
    void arrastoDoMouse(GraphicsContext gc, Paint cor, MouseEvent event);
    void soltarClickMouse(GraphicsContext gc, Paint cor, MouseEvent event);
}
