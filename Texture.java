import java.util.*;
import java.io.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Texture  {
    private Image whiteDefaultIcon = null, whiteCapturableIcon = null, whiteSelectedIcon = null, whiteBigIcon = null;
    private String whiteIcon = null;
    private Image blackDefaultIcon = null, blackCapturableIcon = null, blackSelectedIcon = null, blackBigIcon = null;
    private String blackIcon = null;
    public Texture (String picon)  {
	setIcon(picon);
    }
    public void setIcon (String picon)  {
	if (picon != null)  {
	    this.whiteIcon = "textures/" + GameSettings.pieceTexture + "/" + picon + "_white" + ".png";;
	    this.whiteDefaultIcon = new Image(new File(this.whiteIcon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.whiteCapturableIcon = new Image(new File (this.whiteIcon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.whiteSelectedIcon = new Image(new File (this.whiteIcon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.whiteBigIcon = new Image(new File (this.whiteIcon).toURI().toString());
	    this.whiteDefaultIcon = reColor(this.whiteDefaultIcon, Color.MAGENTA, Color.BLACK);
	    this.whiteBigIcon = reColor(this.whiteBigIcon, Color.MAGENTA, Color.BLACK);
	    this.whiteSelectedIcon = reColor(this.whiteSelectedIcon, Color.MAGENTA, Color.BLUE);
	    this.whiteCapturableIcon = reColor(this.whiteCapturableIcon, Color.MAGENTA, Color.LIME);

	    this.blackIcon = "textures/" + GameSettings.pieceTexture + "/" + picon + "_black" + ".png";;
	    this.blackDefaultIcon = new Image(new File(this.blackIcon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.blackCapturableIcon = new Image(new File (this.blackIcon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.blackSelectedIcon = new Image(new File (this.blackIcon).toURI().toString(), GameSettings.IMAGESIZE, GameSettings.IMAGESIZE, false, false);
	    this.blackBigIcon = new Image(new File (this.blackIcon).toURI().toString());
	    this.blackDefaultIcon = reColor(this.blackDefaultIcon, Color.MAGENTA, Color.WHITE);
	    this.blackBigIcon = reColor(this.blackBigIcon, Color.MAGENTA, Color.WHITE);
	    this.blackSelectedIcon = reColor(this.blackSelectedIcon, Color.MAGENTA, Color.BLUE);
	    this.blackCapturableIcon = reColor(this.blackCapturableIcon, Color.MAGENTA, Color.LIME);
	}
    }
    public Image getIcon (boolean capturable, boolean selected, boolean white, boolean big)  {
	if (big)  return (white) ? this.whiteBigIcon : this.blackBigIcon;
	if (selected)  return (white) ? this.whiteSelectedIcon : this.blackSelectedIcon;
	if (capturable)  return (white) ? this.whiteCapturableIcon : this.blackCapturableIcon;
	return (white) ? this.whiteDefaultIcon : this.blackDefaultIcon;
    }
    public static int getInManifest (String str)  {
	int i;
	for (i = 1; i != GameSettings.pieceManifest.length && !GameSettings.pieceManifest [i].equals(str); i++);
	System.out.println(i);
	return i;
    }
    // Copied from Stack Overflow
    // If it works, it works
    private Image reColor(Image inputImage, Color oldColor, Color newColor) {
	try  {
	    int height = (int) inputImage.getHeight();
	    int width = (int) inputImage.getWidth();
	    WritableImage outputImage = new WritableImage(width, height);
	    PixelReader reader = inputImage.getPixelReader();
	    PixelWriter writer = outputImage.getPixelWriter();
	    int oldRed = (int)(oldColor.getRed() * 255);
	    int oldGreen = (int)(oldColor.getGreen() * 255);
	    int oldBlue = (int)(oldColor.getBlue() * 255);
	    int newRed = (int)(newColor.getRed() * 255);
	    int newGreen = (int)(newColor.getGreen() * 255);
	    int newBlue = (int)(newColor.getBlue() * 255);
	    for (int i = 0; i < height; i++) {
		for (int j = 0; j < width; j++) {
		    int argb = reader.getArgb(i, j);
		    int alpha = (argb >> 24) & 0xFF;
		    int red = (argb >> 16) & 0xFF;
		    int green = (argb >> 8) & 0xFF;
		    int blue =  argb & 0xFF;
		    if (red == oldRed && green == oldGreen && blue == oldBlue) {
			red = newRed;
			green = newGreen;
			blue = newBlue;
		    }
		    argb = (alpha << 24) | (red << 16) | (green << 8) | blue;
		    writer.setArgb(i, j, argb);
		}
	    }
	    return outputImage;
	}
	catch (Exception e)  {
	    System.out.println(e + " ReColoring image");
	}
	return (Image)inputImage;
    }
}
