import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) throws IOException {
        
        int port = Integer.parseInt(args[0]);
        ServerSocket welcomeSocket = new ServerSocket(port);
        System.out.println("Started server at " + welcomeSocket.getLocalPort());
        
        while(true) {
            try{
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Connected to port: " + connectionSocket.getLocalPort());

            handleClients(connectionSocket);
            }catch(IOException ex){
                ex.printStackTrace();
                welcomeSocket.close();
            }
            
        }
    }
    public static void handleClients(Socket connectionSocket) throws IOException{
        String resp = "HTTP/1.1 200 OK \r\n\r\n";
        byte [] header = new byte[resp.length()];
        header = resp.getBytes(StandardCharsets.UTF_8);
          
        byte [] response = getResponse(connectionSocket);

        connectionSocket.getOutputStream().write(header);
        connectionSocket.getOutputStream().write(response);
        connectionSocket.close();
    }

    private static byte[] getResponse(Socket connectionSocket) throws IOException{
        
        List<Byte> buffer = new ArrayList<Byte>();
        int count = 0;
        int step = 0;
        int flag = 0;
        
        InputStream io = connectionSocket.getInputStream();
        
        int currByte = io.read();
        while(currByte != 0 && currByte != -1){
            buffer.add(count, (byte) currByte);
            
            if(step == 0){
                if(currByte ==  13)
                    step = 1;
                else
                    flag = 0;
            }else if(step == 1){
                if(currByte ==  10){
                    step = 0;
                    flag++;
                    if(flag == 2)
                        break;
                }else
                    flag = 0;
            }
            currByte = io.read();
            count ++;
            
        }
        byte [] fromClientBuffer = new byte[buffer.size()];
        int i = 0;
        for(Byte b : buffer){
            fromClientBuffer[i++] = b;
        }
        return fromClientBuffer;
    }
    
}


