package mainView.petinsuView;

import java.io.IOException;

import javafx.scene.Parent;

public interface petInsu {
	public void setRoot(Parent root);
	public void OpenPetInsuView() throws IOException;
	void insurance(String url);
}

