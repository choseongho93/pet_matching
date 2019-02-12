package serverEx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Controller implements Initializable {
	public static Parent root;
	Button btnStartStop;
	private ServerExample se;
	
	public void setRoot(Parent root) throws IOException {
		this.root = root;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnStartStop = new Button("start");
		se = new ServerExample(root);
	}

	public void serverOk() throws IOException {
		if(btnStartStop.getText().equals("start")) {
			se.startServer();
			btnStartStop.setText("stop");
			Button btn = (Button) root.lookup("#btnStartStop");
			btn.setText("stop");
		} else if(btnStartStop.getText().equals("stop")){
			se.stopServer();
			btnStartStop.setText("start");
			Button btn = (Button) root.lookup("#btnStartStop");
			btn.setText("start");
		}
	}
	
	public void serverExit() {
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}
}




class ServerView{
	public void displayText(String text) {
		TextArea textArea = (TextArea) Controller.root.lookup("#textArea");
		textArea.appendText(text+"\n");
	}
}
