package serverEx;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Login.LoginWin.LoginServiceCrt;
import javafx.scene.Parent;

public class ServerExample {
	ExecutorService executorService;
	ServerSocket serverSocket;
	static List<Client> connections = new Vector<Client>();
	Parent root;
	ServerView va;
	String id;

	public ServerExample(Parent root) {
		this.root = root;
		va = new ServerView();
	}

	public ServerExample() {
	}

	public void setId(String id) {
		this.id = id;
	}

	void startServer() {
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("localhost", 12345));
		} catch (Exception e) {
			if (!serverSocket.isClosed()) {
				stopServer();
			}
			return;
		}

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				va.displayText("[서버시작]");
				while (true) {
					try {
						Socket socket = serverSocket.accept();

						InputStream idget = socket.getInputStream();
						DataInputStream idgetd = new DataInputStream(idget);
						id = idgetd.readUTF();
						String message = "[연결 수락: " + socket.getRemoteSocketAddress() + ": "
								+ Thread.currentThread().getName() + "]";
						va.displayText(message);

						System.out.println("클라이언트로 부터 전송받은 id:" + id);
						Client client = new Client(socket, id);
						connections.add(client);
						va.displayText("[연결 개수: " + connections.size() + "]");
					} catch (Exception e) {
						if (!serverSocket.isClosed()) {
							stopServer();
						}
						break;
					}
				}
			}
		};
		executorService.submit(runnable);
	}

	void stopServer() {
		try {
			Iterator<Client> iterator = connections.iterator();
			while (iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			if (executorService != null && !executorService.isShutdown()) {
				executorService.shutdown();
			}
			va.displayText("[서버멈춤]");
		} catch (Exception e) {
		}
	}

	// 데이터 통신코드를 가지고있음
	// 서버 접속하면 클라이언트를 객체로만들어서 하나씩 관리해줌
	class Client {
		Socket socket;
		String id;

		Client(Socket socket, String id) {
			this.socket = socket;
			this.id = id;
			receive();
		}

		String getId() {
			return id;
		}

		void receive() {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						while (true) {

							byte[] byteArr = new byte[2048];
							InputStream inputStream = socket.getInputStream();
							// 클라이언트가 비저상 종료를 했을 경우 IOException 발생
							int readByteCount = inputStream.read(byteArr);
							// 클라이언트가 정상적으로 Socket의 close()를 호출했을 경우
							if (readByteCount == -1) {
								throw new IOException();
							}
							String message = "[요청 처리: " + socket.getRemoteSocketAddress() + ": "
									+ Thread.currentThread().getName() + "]";
							va.displayText(message);
							String data = new String(byteArr, 0, readByteCount, "UTF-8");
							if (data.contains("#Refresh")) {
								String[] c = data.split("#");
								String conn = "#Refresh/" + connections.size();
								for (Client client : connections) {
									if (client.getId().equals(c[0])) {
										client.send(conn);
										System.out.println("새로고침");
									}
								}
							} else {
								System.out.println(data);
								String[] w = data.split(" ");
								if (w[1].charAt(0) == '/') {
									if (w[1].charAt(1) == 'w') {
										String msg = "";
										for (int j = 3; j < w.length; j++) {
											msg += w[j] + " ";
										}
										String senddata = w[0] + "의 귓속말:" + msg;
										for (Client client : connections) {

											if (client.getId().equals(w[2])) {
												System.out.println("귓 전송");
												client.send(senddata);
											}
										}
									}
								} else {
									for (Client client : connections) {
										String msg = "";
										for (int j = 1; j < w.length; j++) {
											msg += w[j] + " ";
										}
										String senddata = w[0] + "의 전체메시지:" + msg;
										System.out.println("전체메시지 전송");

										client.send(senddata);
										msg = "";
									}
								}
							}
						}
					} catch (Exception e) {
						try {
							connections.remove(Client.this);
							String message = "[클라이언트 통신 안됨: " + socket.getRemoteSocketAddress() + ": "
									+ Thread.currentThread().getName() + "]";
							va.displayText(message);
							socket.close();
							va.displayText("[연결 개수: " + connections.size() + "]");
						} catch (IOException e2) {
						}
					}
				}
			};
			executorService.submit(runnable);
		}

		void send(String data) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(data);
						byte[] byteArr = data.getBytes("UTF-8");
						OutputStream outputStream = socket.getOutputStream();
						outputStream.write(byteArr);
						outputStream.flush();
					} catch (Exception e) {
						try {
							String message = "[클라이언트 통신 안됨: " + socket.getRemoteSocketAddress() + ": "
									+ Thread.currentThread().getName() + "]";
							va.displayText(message);
							connections.remove(Client.this);
							socket.close();
							va.displayText("[연결 개수: " + connections.size() + "]");
						} catch (IOException e2) {
						}
					}
				}
			};
			executorService.submit(runnable);
		}
	}

}
