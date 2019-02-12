package Login.LoginWin;

import java.io.IOException;
import java.net.Socket;

import DAO.DatabaseService;
import DAO.DatabaseServiceImpl;
import Login.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainView.mainViewImpl;
import mainView.mainViewInter;
import mainView.common.CommonService;
import mainView.common.CommonServiceImpl;
import member.Owner;

public class LoginServiceCrt implements LoginService{
	DatabaseService ds = new DatabaseServiceImpl();
	private mainViewInter mainview = new mainViewImpl();
	public static String LoginId;
	public void setLogid(String LoginId) {this.LoginId = LoginId;}
	public static String getLogid() {return LoginId;}
	Socket sock;
	
	CommonService co = new CommonServiceImpl();
	@Override
	public void Loginbtn(Parent root) {
		TextField txtID = (TextField)root.lookup("#fxID");
		PasswordField passPW = (PasswordField)root.lookup("#fxPASS");
		CheckBox check = (CheckBox)root.lookup("#check");///
		boolean checks = true; checks=check.isSelected();///
		if(txtID.getText().isEmpty()||passPW.getText().isEmpty()) {
			co.ShowAlertErr("아이디 또는 비밀번호가 없습니다!!");

		}else if (txtID.getText().isEmpty()==false&&passPW.getText().isEmpty()==false){
			Owner loginown = new Owner();
			loginown.setId(txtID.getText());
			loginown.setPw(passPW.getText());
			Owner Confirm = new Owner();
			Confirm = ds.Select(loginown);
			
			System.out.println("확인비밀번호:"+Confirm.getPw());
			System.out.println("입력비밀번호:"+passPW.getText());
			if(Confirm.getPw()==null) {				
				co.ShowAlertErr("해당아이디를 찾지못하였습니다.");
			}else if(Confirm.getPw().equals(passPW.getText())) {//아이디 비번 일치
				if(checks) {//추가
					System.out.println("로그인에 성공하셨습니다.");
					ds.imageLocalSave();
					txtID.setText(txtID.getText());//추가
					setLogid(txtID.getText());	
					passPW.clear();//
				}
				else{//추가
					System.out.println("로그인에 성공하셨습니다.");
					ds.imageLocalSave();
					setLogid(txtID.getText());	
					txtID.clear();
					passPW.clear();//
				}
				try {
					//mainview.setRoot(root);
					mainview.OpenMainView();//메인 화면 호출
					} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				co.ShowAlertErr("비밀번호 불일치");
				System.out.println("로그인 실패");
				if(checks) {//추가
					txtID.setText(txtID.getText());//추가
					passPW.clear();//추가
					passPW.requestFocus();//추가
				}else {
					txtID.clear();//
					passPW.clear();//
					txtID.requestFocus();//
				}
				
			}
		}else if(txtID.getText().isEmpty()||passPW.getText().isEmpty()) {
			co.ShowAlertErr("아이디 또는 비밀번호를 입력해주세요.");
		}
		
	}
	
	//가입창완료.
	@Override
	public void Joinbtn() {
		System.out.println("회원가입 창 클릭하셨습니다. 정보를 입력해주세요");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/membership/useradd.fxml"));
		Parent root=null;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			Stage JoinStage = new Stage();
			Controller ctl = loader.getController();
			ctl.setRoot(root);
			ctl.memset();
			JoinStage.setTitle("Join Membership");
			JoinStage.setScene(scene);
			JoinStage.show();
			} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	//닫기됨
	public void Cancle(Parent root) {
		TextField txtID = (TextField)root.lookup("#fxID");
		PasswordField passPW = (PasswordField)root.lookup("#fxPASS");
		txtID.clear();  passPW.clear();  txtID.requestFocus();
		System.out.println("프로그램을 종료합니다.");
		((Stage)root.getScene().getWindow()).close();
		
	}
	
	
}
