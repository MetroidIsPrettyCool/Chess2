import java.io.*;
import javafx.scene.image.Image;

public class BlankPiece extends Piece  {
    public BlankPiece (int px, int py)  {
	super(px, py, 0, "blank", "", "", "", "", 2);
    }
    @Override
    public Image getIcon (boolean selectable, boolean big)  {
	if (big)  return GameSettings.blankIcons [3];
        if (this.getSelected())  return GameSettings.blankIcons [2];
	if (this.getCapturable())  return GameSettings.blankIcons [1];
	return GameSettings.blankIcons [0];
    }
    @Override
    public boolean canBeCaptured (Piece p)  {
	return true;
    }
}
