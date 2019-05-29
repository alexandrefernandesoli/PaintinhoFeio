package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.StrokeLineCap;

public class DesenhaCaneta implements Ferramentas {
    @Override
    public void clickDoMouse(GraphicsContext areaDePintura, Paint cor, MouseEvent event) {
        areaDePintura.beginPath();
        areaDePintura.moveTo(event.getX(), event.getY());
        areaDePintura.setLineCap(StrokeLineCap.ROUND);
        areaDePintura.stroke();
    }

    @Override
    public void arrastoDoMouse(GraphicsContext areaDePintura, Paint cor, MouseEvent event) {
        areaDePintura.lineTo(event.getX(), event.getY());
        areaDePintura.stroke();
        areaDePintura.closePath();
        areaDePintura.beginPath();
        areaDePintura.moveTo(event.getX(), event.getY());
    }

    @Override
    public void soltarClickMouse(GraphicsContext areaDePintura, Paint cor, MouseEvent event) {
        areaDePintura.lineTo(event.getX(), event.getY());
        areaDePintura.stroke();
        areaDePintura.closePath();
    }
}
