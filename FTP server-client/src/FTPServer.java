import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
    public final static int port = 13267;
    public final static String FILE_TO_SEND = "file.txt";

    public static void main(String[] args) throws IOException {
        FileInputStream fis;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket ssocket = null;
        Socket socket = null;

        try {
            ssocket = new ServerSocket(port);

            while(true) {
                System.out.println("aspetto...");

                try {
                    socket = ssocket.accept();
                    System.out.println("connessione accettata: " + socket);

                    File myFile = new File(FILE_TO_SEND);
                    byte []byteArray = new byte[(int)myFile.length()];
                    fis = new FileInputStream(myFile);
                    bis = new BufferedInputStream(fis);
                    bis.read(byteArray, 0, byteArray.length);

                    os = socket.getOutputStream();
                    System.out.println("sto inviando " + FILE_TO_SEND + " (" + byteArray.length + " byte)");
                    os.write(byteArray, 0, byteArray.length);
                    os.flush();
                    System.out.println("Fatto.");
                } finally {
                    if(bis != null)
                        bis.close();
                    if(os != null)
                        os.close();
                    if(socket != null)
                        socket.close();
                }
            }
        } finally {
            if(ssocket != null)
                ssocket.close();
        }

    }
}
