package mainView.findpetView;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import member.Pet;

public interface Findpet {
	public void setRoot(Parent root);
	public void OpenFindpetView() throws IOException;
	public void OpenProfile(String id,Image img) throws IOException;
	public void getUser(ArrayList<Pet> petlist);
}
