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
 *
 * @author Felipe Hiroshi
 */
public class UndoRedo {
    
    
     public void copia(Canvas tela, Canvas telaUndo){
        telaUndo.setVisible(true);
        SnapshotParameters params = new SnapshotParameters();
        WritableImage image = tela.snapshot(params, null);
        telaUndo.getGraphicsContext2D().drawImage(image, 0, 0);
        telaUndo.setVisible(false);
    }
     
    public void clickUndo(Canvas tela, Canvas telaUndo){
        telaUndo.setVisible(true);
        SnapshotParameters params = new SnapshotParameters();
        WritableImage image = telaUndo.snapshot(params, null);
        tela.getGraphicsContext2D().drawImage(image, 0, 0);
        telaUndo.setVisible(false);
    }
}
