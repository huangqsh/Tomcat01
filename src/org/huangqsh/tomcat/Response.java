package org.huangqsh.tomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
	private OutputStream outputStream;
	private Request request;
	
	public Response(OutputStream outputStream,Request request) {
		this.outputStream = outputStream;
		this.request = request;
	}
	
	/**
	 * 请求响应
	 * @throws IOException
	 */
	public void sendResponse() throws IOException {
		byte[] buffer = new byte[2048];
		String uri = request.getUri();
		FileInputStream fis = null;
		try {
			//根据请求路径查询请求文件
			File file = new File(HttpServer.WEB_ROOT + uri);
			System.out.println("文件地址："+HttpServer.WEB_ROOT + uri);
			if (!file.exists()) {
				String head = "HTTP/1.1 404 File Not Found\r\n"+
							 "Content-Type: text/html\r\n"+
							 "Content-Length: 23\r\n"+
							 "\r\n"+
							 "<h1>File Not Found 404</h1>";
				
				outputStream.write(head.getBytes());
			}else {
				String head = "HTTP/1.1 200 OK\r\n"+
						 "Content-Type: text/html\r\n"+
						 "\r\n";
				
				outputStream.write(head.getBytes());
				fis = new FileInputStream(file);
				int read = fis.read(buffer, 0, 2048);
				while (read != -1) {
					outputStream.write(buffer,0,buffer.length);
					read = fis.read(buffer, 0, 2048);
				}
			}
		} catch (Exception e) {
			if(fis != null) {
				fis.close();
			}
		}
		
	}
}
