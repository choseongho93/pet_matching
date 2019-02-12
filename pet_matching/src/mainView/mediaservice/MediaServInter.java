package mainView.mediaservice;

import javafx.scene.Parent;

public interface MediaServInter {
	public void Start();
	public void Pause();
	public void Stop();
	public void setMedia(Parent form, String mediaName);
	public void volumnControl();
	public void mouseEntered1();
	public void mouseExited1();
	public void mouseEntered2();
	public void mouseExited2();
	public void mouseEntered3();
	public void mouseExited3();
}
