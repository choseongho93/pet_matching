package serverEx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Server  extends Application{
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerEvent.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Controller ctr = loader.getController();
		ctr.setRoot(root);
		primaryStage.setTitle("server");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
