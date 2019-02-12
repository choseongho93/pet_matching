package ClientEx;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import Login.LoginWin.LoginServiceCrt;
import javafx.scene.Parent;
import mainView.Controller;
import serverEx.ServerExample;

public class ClientExample {
	Socket socket;
	Parent root;
	public ClientView cv =new ClientView();
	Controller ctr;
	String size;
	public ClientExample(Parent root) {
		this.root = root;
	}

	public void startClient() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					ServerExample se = new ServerExample();
					ClientView cv=new ClientView();
					socket = new Socket();
					socket.connect(new InetSocketAddress("192.168.0.53", 12345));
					cv.displayText("연결완료: " + socket.getRemoteSocketAddress() + "]");
					
					OutputStream idout = socket.getOutputStream();
					DataOutputStream iddout = new DataOutputStream(idout);
					System.out.println(LoginServiceCrt.getLogid());
					iddout.writeUTF(LoginServiceCrt.getLogid());
					System.out.println("아이디 받음");
				} catch (Exception e) {
					e.printStackTrace();
					cv.displayText("[서버통신 안됨]");
					if (!socket.isClosed()) {
						stopClient();
					}
					return;
				}
				receive();
			}
		};
		thread.start();
	}

	public void stopClient() {
		try {
			cv.displayText("[연결 종료]");
			if (socket != null && !socket.isClosed()) {
				socket.close();
				
			}
		} catch (IOException e) {
		}
	}

	public void receive() {
		while (true) {
			try {
				byte[] byteArr=new byte[2048];
				InputStream inputStream = socket.getInputStream();
				 
				int readByteCount = inputStream.read(byteArr);
				
				 
				if (readByteCount == -1) {
					throw new IOException();
				}
				String data = new String(byteArr, 0, readByteCount, "UTF-8");
				if(data.contains("#Refresh")) {
					System.out.println("서버로부터 받아온 data:"+data);
					String[] c = data.split("/");
					size = c[1];
//					 
				}else {
					
					cv.displayText(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				cv.displayText("[서버통신 안됨]");
				stopClient();
				break;
			}
		}
	}

	public void send(String data) {
		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					byte[] byteArr = data.getBytes("UTF-8");
					OutputStream outputStream = socket.getOutputStream();
					outputStream.write(byteArr);
					outputStream.flush();
				} catch (Exception e) {
					cv.displayText("[서버통신 안됨]");
					stopClient();
				}
			}
		};
		thread.start();
	}
	
	public String getSize() {return size;}

	
}
