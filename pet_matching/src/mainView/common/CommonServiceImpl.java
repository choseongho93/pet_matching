package mainView.common;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CommonServiceImpl implements CommonService{
	private Parent root;
	@Override
	public void setRoot(Parent root) {this.root=root;}

	@Override
	public void WindowClose(ActionEvent e) {
		Parent root=(Parent)e.getSource();
		Stage stage=(Stage)root.getScene().getWindow();
		stage.close();
	}

	@Override
	public void ShowAlertErr(String msg) {
		Alert at = new Alert(AlertType.ERROR);
		at.setContentText(msg);
		at.show();
	}

	@Override
	public void ShowAlertOk(String msg) {
		Alert at = new Alert(AlertType.CONFIRMATION);
		at.setContentText(msg);
		at.show();
	}

}
