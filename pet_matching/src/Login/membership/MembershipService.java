package Login.membership;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Parent;

public interface MembershipService {
	public void setRoot(Parent root);
	public void membershipProc() throws IOException;
	public void FileOpen();
	public void overlap();
}
