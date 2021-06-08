import java.io.IOException;
import java.net.*;

public class ConcHTTPAsk {
    public static void main(String [] args) throws IOException{
        try{
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            
            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connected to port: " + clientSocket.getLocalPort());
                MyRunnable runnable = new MyRunnable(clientSocket);
                new Thread(runnable).start();
                
            }
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
