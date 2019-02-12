package mainView.typechoiceView;

import java.io.IOException;

import javafx.scene.Parent;

public interface typeChoice {
	public void setRoot(Parent root);
	public void OpenTypeChoiceView() throws IOException;//이상형 월드컵 창 실행
	public void changePetLeft() throws IOException;//사진 변경
	public void changePetRight() throws IOException;//사진 변경
	public void start();//월드컵 시작
	public void OpenWinView() throws IOException;//우승자 화면 실행
}
