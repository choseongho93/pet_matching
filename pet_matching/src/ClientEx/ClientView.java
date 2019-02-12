package ClientEx;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import mainView.Controller;

public class ClientView {
	
	public void displayText(String text) {
		TextArea textArea = (TextArea) Controller.root.lookup("#textArea");
		textArea.appendText(text+"\n");
	}
	public void displayRefresh(String text) {
		Label newUser = (Label) Controller.root.lookup("#newUser");
		System.out.println(text);
		newUser.setText(text+"명");
		System.out.println("새로고침 완료");
	}
}
