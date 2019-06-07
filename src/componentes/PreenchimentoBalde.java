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

        private final WritableImage canvasSnapshot;
        private final Point ponto;
        private final Color corNova;

        private FloodFill(WritableImage capturaDoCanvas, Color corNova, Point ponto) {
            this.canvasSnapshot = capturaDoCanvas;
            this.corNova = corNova;
            this.ponto = ponto;
        }

        @Override
        protected Void call(){
            PixelReader pixelReader = canvasSnapshot.getPixelReader();
            PixelWriter pixelWriter = canvasSnapshot.getPixelWriter();
            Color corAntiga = pixelReader.getColor((int) ponto.getX(), (int) ponto.getY());

            if (corAntiga.equals(corNova) /*|| !corNova.equals(corDoPixel)*/)
                return null;

            Queue<Point> fila = new LinkedList<>();
            fila.add(ponto);

            while (!fila.isEmpty()) {

                Point pixel = fila.poll();
                if (!(pixel.getX() < 0.0f || pixel.getX() >= canvasSnapshot.getWidth() ||
                        pixel.getY() < 0.0f || pixel.getY() >= canvasSnapshot.getHeight())) {

                    final int[] deslocamentos = {-1, 1};
                    for (int i : deslocamentos) {
                        for (int j : deslocamentos) {

                            Point vizinho = new Point((int) pixel.getX() + i, (int) pixel.getY() + j);
                            Color corDoVizinho = pixelReader.getColor((int) vizinho.getX(), (int) vizinho.getY());

                            if (corDoVizinho.equals(corAntiga)) {
                                pixelWriter.setColor((int) vizinho.getX(), (int) vizinho.getY(), corNova);
                                fila.add(vizinho);
                            }
                        }
                    }
                }
            }

            return null;
        }
    }
}
