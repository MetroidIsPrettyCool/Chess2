import java.io.*;
import javafx.scene.image.Image;

public class BlankPiece extends Piece  {
    public BlankPiece ()  {
	super(0, 0, 0, 0, "blank", "", "", "", "", 2);
	this.nicon = new Image(new File("./textures/" + GameSettings.pieceTexture + "/blank_" + colors [color] + ".png").toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	this.cicon = new Image(new File ("./textures/" + GameSettings.pieceTexture + "/blank_c" + colors [color] + ".png").toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	this.sicon = new Image(new File ("./textures/" + GameSettings.pieceTexture + "/blank_s" + colors [color] + ".png").toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	this.bicon = new Image(new File ("./textures/" + GameSettings.pieceTexture + "/blank_" + colors [color] + ".png").toURI().toString());
    }
    public BlankPiece (int px, int py)  {
	super(px, py, 0, 0, "blank", "", "", "", "", 2);
	this.nicon = new Image(new File("./textures/" + GameSettings.pieceTexture + "/blank_" + colors [color] + ".png").toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	this.cicon = new Image(new File ("./textures/" + GameSettings.pieceTexture + "/blank_c" + colors [color] + ".png").toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	this.sicon = new Image(new File ("./textures/" + GameSettings.pieceTexture + "/blank_s" + colors [color] + ".png").toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	this.bicon = new Image(new File ("./textures/" + GameSettings.pieceTexture + "/blank_" + colors [color] + ".png").toURI().toString());
    }
    @Override
    public boolean canBeCaptured (Piece p)  {
	return true;
    }
    @Override
    public void setIcon (String picon)  {
	this.icon = picon;
	this.nicon = new Image(new File("./textures/" + GameSettings.pieceTexture + "/blank_" + colors [color] + ".png").toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	this.cicon = new Image(new File ("./textures/" + GameSettings.pieceTexture + "/blank_c" + colors [color] + ".png").toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	this.sicon = new Image(new File ("./textures/" + GameSettings.pieceTexture + "/blank_s" + colors [color] + ".png").toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	this.bicon = new Image(new File ("./textures/" + GameSettings.pieceTexture + "/blank_" + colors [color] + ".png").toURI().toString());
    }
}
