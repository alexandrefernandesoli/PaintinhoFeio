package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class DesenhaCirculo implements Ferramentas {
    private double coordenadaX;
    private double coordenadaY;
    private double largura;
    private double altura;

    @Override
    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        coordenadaX = event.getX() - 10;
        coordenadaY = event.getY() - 10;
    }

    @Override
    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {
        largura = Math.abs(event.getX() - coordenadaX) + 15;
        altura = Math.abs(event.getY() - coordenadaY) + 15;
    }

    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        if (event.getX() < coordenadaX || event.getY() < coordenadaY) {
            coordenadaX = event.getX();
            coordenadaY = event.getY();
        }
        gc.fillOval(coordenadaX, coordenadaY, largura, altura);
    }
}
