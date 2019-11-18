import java.net.*;
import java.io.*;

public class LAN  {
    private static final int port = 42069;
    private static String ipStr;
    private static InetAddress ip;
    private static ServerSocket server = null;
    private static Socket socket = null;
    private static ObjectOutputStream out = null;
    private static ObjectInputStream in = null;
    private static boolean connected = false;
    private static boolean connecting = false;
    private static boolean isServer;

    public static boolean getIsServer ()  {
	return isServer;
    }
    
    public static void setIp (String ip)  {
	ipStr = ip;
    }

    public static String getIp ()  {
	return ipStr;
    }

    public static void setConnected (boolean connected)  {
	connected = connected;
    }

    public static boolean getConnected ()  {
	return connected;
    }

    public static boolean getConnecting ()  {
	return connecting;
    }

    public static void host ()  {
	try  {
	    isServer = true;
	    connecting = true;
	    ip = InetAddress.getLocalHost();
	    Thread initServer = new Thread(new StartServer ());
	    initServer.setDaemon(true);
	    initServer.start();
	}
	catch (Exception e)  {
	    System.out.println(e + " Hosting");
	    connecting = false;
	    close();
	}
    }

    public static void connect ()  {
	try  {
	    isServer = false;
	    connecting = true;
	    ip = InetAddress.getByName(ipStr);
	    Thread initClient = new Thread(new StartClient());
	    initClient.setDaemon(true);
	    initClient.start();
	}
	catch (Exception e)  {
	    System.out.println(e + " Connecting");
	    connecting = false;
	    close();
	}
    }

    public static Object read ()  {
	if (!connected)  {
	    close();
	}
	try  {
	    return in.readObject();
	}
	catch (Exception e)  {
	    System.out.println(e + " Reading board");
	}
	close();
	return null;
    }

    public static void write (Object obj)  {
	if (!connected)  {
	    close();
	    return;
	}
	try  {
	    out.writeObject(obj);
	}
	catch (Exception e)  {
	    System.out.println(e + " Writing board");
	}
    }

    public static void close ()  {
	try  {
	    if (connected)  {
		out.close();
		in.close();
		socket.close();
		socket = null;
		if (isServer)  {
		    server.close();
		    server = null;
		}
	    }
	}
	catch (Exception ex)  {
	    System.out.println(ex + " Closing down server");
	}
	connected = false;
    }

    private static class StartServer implements Runnable  { 
	public void run ()  { 
	    try  {
		server = new ServerSocket(port);
	    
		socket = server.accept();
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

		// TODO: ADD VERSION / EXPANSION HANDSHAKE

		connected = true;
	    }
	    catch (Exception e)  {
		System.out.println(e + " Starting Server");
	    }
	    connecting = false;
        } 
    }
    
    private static class StartClient implements Runnable  { 
	public void run ()  {
	    try  {
	        socket = new Socket(ip, port); 

		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());

		// TODO: ADD VERSION / EXPANSION HANDSHAKE

		connected = true;
	    }
	    catch (Exception e)  {
		System.out.println(e + " Starting Client");
	    }
	    connecting = false;
        } 
    }
}
