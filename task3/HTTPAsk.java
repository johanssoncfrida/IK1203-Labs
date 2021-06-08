import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import tcpclient.TCPClient;

public class HTTPAsk{

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
        //Headers
        String HTTP404 = "HTTP/1.1 404 Not Found\r\n\r\n"; 
        String HTTP200 = "HTTP/1.1 200 OK\r\n\r\n";
        String HTTP400 = "HTTP/1.1 400 Bad Request\r\n\r\n";

        //Parameters to TCPClient
        String host = null;
        String port = null;
        String toServer = null;

        //Helpers to read inputstream
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
        //From Arraylist to byte array
        byte [] fromClientBuffer = new byte[buffer.size()];
        int i = 0;
        for(Byte b : buffer){
            fromClientBuffer[i++] = b;
        }
        //Splits including special characters and new line
        String s = new String(fromClientBuffer, StandardCharsets.UTF_8);
        String [] string = s.split("[\\r?\\n?=&/ ]");
        
        //the ask part comes at second index in string array
        if(string[2].equals("ask")){
            for(i = 0; i<string.length; i++){
                if(string[i].equals("hostname"))
                    host = string[++i];
                else if(string[i].equals("port"))
                    port = string[++i];
                else if(string[i].equals("string"))
                    toServer = string[++i];
            }
            StringBuilder out = new StringBuilder();
            
            if(host != null && port != null && string[0].equals("GET")){
                
                String answer = TCPClient.askServer(host,Integer.parseInt(port), toServer);
                out.append(HTTP200).append(answer);
            }else{
                out.append(HTTP404);
            }  
            byte [] outToSocket = new byte [out.toString().length()];
            outToSocket = out.toString().getBytes();
            connectionSocket.getOutputStream().write(outToSocket);
            }else{
                byte [] outToSocket = new byte[HTTP400.length()];
                outToSocket = HTTP400.getBytes();
                connectionSocket.getOutputStream().write(outToSocket);
        }
        connectionSocket.close();
        
    }
    

}

