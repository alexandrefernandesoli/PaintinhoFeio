/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

/**
 * @author Felipe Hiroshi
 */
public class DesfazerRefazer {
    private WritableImage imageRedo;
    private WritableImage image;

    public void copia(Canvas tela) {
        SnapshotParameters params = new SnapshotParameters();
        image = tela.snapshot(params, null);
    }

    public void clickUndo(Canvas tela) {
        SnapshotParameters params = new SnapshotParameters();
        imageRedo = tela.snapshot(params, null);
        tela.getGraphicsContext2D().drawImage(image, 0, 0);
    }

    public void redo(Canvas tela) {
        tela.getGraphicsContext2D().drawImage(imageRedo, 0, 0);
    }
}
