package componentes;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class PreenchimentoBalde implements Ferramentas {

    @Override
    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        Canvas canvas = gc.getCanvas();
        WritableImage capturaDoCanvas = canvas.snapshot(null, 
                new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight()));

        Color corNova = (Color) gc.getFill();
        Point ponto = new Point((int) event.getX(), (int) event.getY());

        Task<Void> task = new FloodFill(capturaDoCanvas, corNova, ponto);
        task.setOnSucceeded(successEvent -> gc.drawImage(capturaDoCanvas, 0, 0));
        new Thread(task).start();
    }

    @Override
    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {

    }

    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {

    }

    private class FloodFill extends Task<Void> {

        private static final double EPSILON = 0.125;
        private final WritableImage capturaDoCanvas;
        private final Point ponto;
        private final Color corNova;

        private FloodFill(WritableImage capturaDoCanvas, Color corNova, Point ponto) {
            this.capturaDoCanvas = capturaDoCanvas;
            this.corNova = corNova;
            this.ponto = ponto;
        }

        private boolean coresIguais(Color a, Color b) {
            return (Math.abs(a.getRed() - b.getRed()) < EPSILON &&
                    Math.abs(a.getGreen()- b.getGreen()) < EPSILON &&
                    Math.abs(a.getBlue()- b.getBlue()) < EPSILON);
        }

        @Override
        protected Void call(){
            PixelReader pixelReader = capturaDoCanvas.getPixelReader();
            PixelWriter pixelWriter = capturaDoCanvas.getPixelWriter();
            Color corAntiga = pixelReader.getColor(ponto.x, ponto.y);

            if (coresIguais(corNova, corAntiga))
                return null;

            Queue<Point> filaDePontos = new LinkedList<>();
            filaDePontos.add(ponto);

            while (!filaDePontos.isEmpty()) {
                Point pixel = filaDePontos.remove();

                final int[] deslocamentos = {-1, 0, 1};
                for (int i : deslocamentos) {
                    for (int j : deslocamentos) {

                        Point vizinho = new Point(pixel.x + i, pixel.y + j);
                        Color corDoVizinho = pixelReader.getColor(vizinho.x, vizinho.y);

                        if (0.0 <= vizinho.getX() && vizinho.getX() < capturaDoCanvas.getWidth() &&
                            0.0 <= vizinho.getY() && vizinho.getY() < capturaDoCanvas.getHeight())

                            if (coresIguais(corDoVizinho, corAntiga)) {
                                pixelWriter.setColor(vizinho.x, vizinho.y, corNova);
                                filaDePontos.add(vizinho);
                            }
                    }
                }
            }

            return null;
        }
    }
}
