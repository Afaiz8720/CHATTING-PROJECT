package message;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
public class Server {
	
	ServerSocket server ;
	Socket socket ;
	
	BufferedReader br ;
	PrintWriter out ;
	
	public Server(){
		try {
			server = new ServerSocket(1510);
			System.out.println("Server is ready to accept connection");
			System.out.println("Waiting...");
			socket = server.accept();
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			startReading();
			startWriting();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void startReading() {
		Runnable r1 = () ->{
			System.out.println("Reader started.....");
			
			try {
			while(true)
			{
				
					String msg = br.readLine();
					if(msg.equals("exit"))
					{
						System.out.println("Server terminated for msg");
						break ;
					}
					
					System.out.println("Client message is : " + msg);
					
				
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		
		new Thread(r1).start();
	}
	
    public void startWriting() {
         Runnable r2 = () ->{
        		System.out.println("Writer started.....");
    			
        		  try {
    			  while(true && !socket.isClosed())
    			  {
    				  	BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
    					String content = br1.readLine();
    					
    					out.println(content);
    					
    					if(content.equals("exit")) {
    						socket.close();
    						break ;
    					}
    					
    					
    					out.flush();
    					
    				
    			  }
        		} catch (IOException e) {
					e.printStackTrace();
			}
		};
		
		new Thread(r2).start();
	}

	public static void main(String[] args) {
		System.out.println("this is a server..going to start server");
		new Server();

	}

}
