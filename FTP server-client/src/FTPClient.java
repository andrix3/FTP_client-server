import java.io.*;
import java.net.Socket;

public class FTPClient {
    public final static int port = 13267;
    public final static String server = "127.0.0.1";
    public final static String FILE_TO_RECEIVE = "source-download.txt";
    public final static int FILE_SIZE = 6022386;

    public static void main(String[] args) throws IOException {
        int bytesRead;
        int current;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket socket = null;

        try {
            socket = new Socket(server, port);
            System.out.println("mi sto connettendo...");

            byte []byteArray = new byte[FILE_SIZE];
            InputStream is = socket.getInputStream();

            fos = new FileOutputStream(FILE_TO_RECEIVE);
            bos = new BufferedOutputStream(fos);

            bytesRead = is.read(byteArray,0,byteArray.length);
            current = bytesRead;

            do {
                bytesRead = is.read(byteArray, current, (byteArray.length - current));

                if(bytesRead >= 0)
                    current += bytesRead;
            } while(bytesRead > -1);

            bos.write(byteArray,0,current);
            bos.flush();

            System.out.println("File " + FILE_TO_RECEIVE + " scaricato (" + current + "byte)");
        } finally {
            if(fos != null)
                fos.close();
            if(bos != null)
                bos.close();
            if(socket != null)
                socket.close();
        }
    }
}
