package componentes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

public class PreenchimentoBalde implements Ferramentas {

    @Override
    public void clickDoMouse(GraphicsContext gc, MouseEvent event) {
        Paint corAntiga = gc.getFill();
//        Paint corNova = event.

//          = event.getX();
//          = event.getY();
    }

    @Override
    public void arrastoDoMouse(GraphicsContext gc, MouseEvent event) {
        throw new UnsupportedOperationException("Não implementado.");
    }

    @Override
    public void soltarClickMouse(GraphicsContext gc, MouseEvent event) {
        throw new UnsupportedOperationException("Não implementado.");
    }
}

/*
Flood-fill (node, target-color, replacement-color) {
	if (target-color is equal to replacement-color)
		return;

	if (color of node is not equal to target-color)
		return;

	node.setColor(replacement-color);
	Set Q empty;
	Q.addEnd(node);

	while (Q is not empty) {
		n = Q.removeFirst();

		if (the color of the node to the west of n is target-color)
			set the color of that node to replacement-color and add that node to the end of Q;

		if (the color of the node to the east of n is target-color)
			set the color of that node to replacement-color and add that node to the end of Q;

		if (the color of the node to the north of n is target-color)
			set the color of that node to replacement-color and add that node to the end of Q;

		if (the color of the node to the south of n is target-color)
			set the color of that node to replacement-color and add that node to the end of Q;
	}
}
*/