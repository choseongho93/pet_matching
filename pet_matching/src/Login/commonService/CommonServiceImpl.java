package Login.commonService;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;;

public class CommonServiceImpl implements CommonService {
	private Parent root;
	private Alert al; 
	@Override
	public void setRoot(Parent root) {
		this.root=root;
	};
	@Override
	public void WindowClose() {
		((Stage)root.getScene().getWindow()).close();
	};
	@Override
	public void Alert(int e ,String msg) {
		if(e==1) al = new Alert(AlertType.ERROR);
		else al = new Alert(AlertType.CONFIRMATION);
		al.setContentText(msg);
		al.show();
	};
}
