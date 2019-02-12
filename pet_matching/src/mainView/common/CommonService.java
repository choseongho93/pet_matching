package mainView.common;

import javafx.event.ActionEvent;
import javafx.scene.Parent;

public interface CommonService {
	public void setRoot(Parent root);
	public void WindowClose(ActionEvent e);
	public void ShowAlertErr(String msg);
	public void ShowAlertOk(String msg);
}
