package mainView.contact;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContactImpl implements Contact{

	@Override
	public void OpenContactView() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../contact.fxml"));
		Parent root = loader.load();
		Stage sg = new Stage();
		Scene sc = new Scene(root);
		sg.setScene(sc);
		sg.show();
	}

}
