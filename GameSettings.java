import java.net.*;
import java.io.*;

public class GameSettings  {
    public static final int BOARDSIZE = 10;
    public static int gameMode;
    public static int port = 42069;
    public static InetAddress ip = null;
    public static ServerSocket server = null;
    public static Socket socket;
    public static ObjectOutputStream out = null;
    public static ObjectInputStream in;
    public static boolean [] expansionsLoaded;
    public static String boardTexture = "default";
    public static String pieceTexture = "default";
    public static boolean twoComputers = true;
}
