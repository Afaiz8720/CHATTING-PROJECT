package message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	Socket socket ;
	
	BufferedReader br ;
	PrintWriter out ;
	
	public Client() {
		try {
			System.out.println("Sending request to server");
			socket = new Socket("127.0.0.1",1500);
			System.out.println("Connection Done");
			
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
						socket.close();
						break ;
					}
					
					System.out.println("Server message is : " + msg);
					
			   }
			
		   } catch (IOException e) {
			//e.printStackTrace();
			   System.out.println("Connection Closed");
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
   					out.flush();
   					
   					if(content.equals("exit")) {
						socket.close();
						break ;
					}
   					
   			}
   			
   			System.out.println("Connection Closed");
   			
       		} catch (IOException e) {
					//e.printStackTrace();
       			
				}
		};
		
		new Thread(r2).start();
	}

	
	public static void main(String[] args) {
		System.out.println("This is Client......");
		
		new Client();

	}

}
