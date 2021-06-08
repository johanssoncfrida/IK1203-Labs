package tcpclient;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;


/**
 * TCPClient in TCP server-client connection
 */
public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws IOException {
        if(ToServer == null){ return askServer(hostname, port);}
        byte [] fromUserBuffer = new byte[2048];
        fromUserBuffer = (ToServer + "\r\n").getBytes(StandardCharsets.UTF_8);
        byte [] fromServerBuffer;
        StringBuilder sb = new StringBuilder();
        
        Socket clientSocket = new Socket(hostname, port);
        clientSocket.getOutputStream().write(fromUserBuffer);
        fromServerBuffer = clientSocket.getInputStream().readAllBytes();
        String sentence = new String(fromServerBuffer, StandardCharsets.UTF_8);
        sb.append(sentence);
            
        clientSocket.close();
        return sb.toString();
    }
    

    public static String askServer(String hostname, int port) throws IOException {

        byte [] fromServerBuffer;
        Socket clientSocket = new Socket(hostname, port);
        StringBuilder sb = new StringBuilder();

        fromServerBuffer = clientSocket.getInputStream().readAllBytes();
        String sentence = new String(fromServerBuffer,StandardCharsets.UTF_8);
        sb.append(sentence);
    
        clientSocket.close();
        return sb.toString();
        
    }
}
