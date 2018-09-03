package org.huangqsh.tomcat;

import java.io.IOException;
import java.io.InputStream;

public class Request {
	private InputStream inputStream;
	
	private String uri;
	
	/**
	 * 从inputStream中获得请求数据
	 */
	public void parse() {
		StringBuffer request = new StringBuffer();
		byte[] buffer = null;
		try {
			//将输入流中的数据转到byte数组中，再转成字符串
			//available()方法可以获得流中数据大小；但是在网络中可能存在隐患
			int bufferLen = inputStream.available(); 
			System.out.println(bufferLen);
			buffer = new byte[bufferLen];
			inputStream.read(buffer);
            for(int j=0;j<buffer.length;j++) {
				request.append((char)buffer[j]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		uri = this.parseURI(request.toString());
	}
	
	/**
	 * 从请求中得到URI
	 * @param reqStr
	 * @return
	 */
	private String parseURI(String reqStr) {
		System.out.println("请求信息："+reqStr);
		//取出请求路径
		int index1,index2;
		index1 = reqStr.indexOf(' ');
		if(index1 != -1) {
			index2 = reqStr.indexOf(' ',index1+1);
			if(index2 > index1) {
				uri = reqStr.substring(index1+1, index2);
			}
		}
		System.out.println("请求地址："+uri);
		return uri;
	}
	

	public String getUri() {
		return uri;
	}

	
	public Request(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
