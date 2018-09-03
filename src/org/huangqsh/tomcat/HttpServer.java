package org.huangqsh.tomcat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	
	public static String WEB_ROOT = System.getProperty("user.dir") + File.separator + "WEB_ROOT";
	
	private boolean isShutDown = false;
	
	public static void main(String[] args) {
		System.out.println("Tomcat start");
		HttpServer httpServer = new HttpServer();
		httpServer.await();
	}

	private void await() {
		//创建socket
		int port = 8080;
		ServerSocket serverSocket = null;
		InetAddress hostAddress = null;
		try {
			hostAddress = InetAddress.getByName("127.0.0.1");
			serverSocket = new ServerSocket(port, 1, hostAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//只要不关闭就死循环，接收socket
		while (!isShutDown) {
			Socket socket = null;
			InputStream in;
			OutputStream out;
			try {
				socket = serverSocket.accept();
				in = socket.getInputStream();
				out = socket.getOutputStream();
				
				Request request = new Request(in);
				request.parse();
				
				Response response = new Response(out,request);
				response.sendResponse();
				
				if("/wq.html".equals(request.getUri())) {
					isShutDown = true;
				}
				socket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
