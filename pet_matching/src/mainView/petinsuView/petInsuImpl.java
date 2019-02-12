package mainView.petinsuView;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mainView.Controller;

public class petInsuImpl implements petInsu{
	private Parent root;
	@Override
	public void setRoot(Parent root) {this.root=root;}

	@Override
	public void OpenPetInsuView() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../petInsu.fxml"));
		Parent root = loader.load();
		Controller ctr = loader.getController();
		ctr.setRoot1(root);
		setRoot(root);
		Stage sg = new Stage();
		Scene sc = new Scene(root);
		sg.setScene(sc);
		sg.show();
	}
	@Override
	public void insurance(String url) {
		try {Desktop.getDesktop().browse(new URI(url));}
		catch (Exception e1) {e1.printStackTrace();}
	}
}
