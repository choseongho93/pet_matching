package mainView;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainViewImpl implements mainViewInter{
//	private Parent root;
//	@Override
//	public void setRoot(Parent root) {this.root = root;}

	@Override
	public void OpenMainView() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainView/main.fxml"));
		Parent root = loader.load();
		Controller ctr = loader.getController();
		ctr.setRoot1(root);
		//setRoot(root);
		Stage sg = new Stage();
		Scene sc = new Scene(root);
		sg.setScene(sc);
		sg.show();
	}

}
