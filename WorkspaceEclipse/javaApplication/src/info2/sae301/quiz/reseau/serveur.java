import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class serveur {
    public static void main(String[] args) {
        int port = 65432;
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is running and listening on port " + port);
            boolean aTester = true;
            while (aTester == true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                
                // Create input and output streams for the client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                // Handle client messages
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received from client: " + message);
                    
                    // You can process the message here or send a response
                    out.println("Server received: " + message);
                }
                
                // Close the client socket
                aTester = false;
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
