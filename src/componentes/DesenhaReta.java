package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class DesenhaReta implements Ferramentas {
    private double x;
    private double y;

    @Override
    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        x = event.getX();
        y = event.getY();
    }

    @Override
    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {

    }

    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        gc.strokeLine(x, y, event.getX(), event.getY());
    }

}
