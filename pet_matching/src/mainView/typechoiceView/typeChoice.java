package mainView.typechoiceView;

import java.io.IOException;

import javafx.scene.Parent;

public interface typeChoice {
	public void setRoot(Parent root);
	public void OpenTypeChoiceView() throws IOException;//�̻��� ������ â ����
	public void changePetLeft() throws IOException;//���� ����
	public void changePetRight() throws IOException;//���� ����
	public void start();//������ ����
	public void OpenWinView() throws IOException;//����� ȭ�� ����
}
