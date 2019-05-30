package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.StrokeLineCap;

public class DesenhaCaneta implements Ferramentas {
    @Override
    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        gc.beginPath();
        gc.moveTo(event.getX(), event.getY());
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.stroke();
    }

    @Override
    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();
        gc.closePath();
        gc.beginPath();
        gc.moveTo(event.getX(), event.getY());
    }

    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        gc.lineTo(event.getX(), event.getY());
        gc.stroke();
        gc.closePath();
    }
}
