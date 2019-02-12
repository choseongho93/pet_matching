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
			co.ShowAlertErr("���̵� �Ǵ� ��й�ȣ�� �����ϴ�!!");

		}else if (txtID.getText().isEmpty()==false&&passPW.getText().isEmpty()==false){
			Owner loginown = new Owner();
			loginown.setId(txtID.getText());
			loginown.setPw(passPW.getText());
			Owner Confirm = new Owner();
			Confirm = ds.Select(loginown);
			
			System.out.println("Ȯ�κ�й�ȣ:"+Confirm.getPw());
			System.out.println("�Էº�й�ȣ:"+passPW.getText());
			if(Confirm.getPw()==null) {				
				co.ShowAlertErr("�ش���̵� ã�����Ͽ����ϴ�.");
			}else if(Confirm.getPw().equals(passPW.getText())) {//���̵� ��� ��ġ
				if(checks) {//�߰�
					System.out.println("�α��ο� �����ϼ̽��ϴ�.");
					ds.imageLocalSave();
					txtID.setText(txtID.getText());//�߰�
					setLogid(txtID.getText());	
					passPW.clear();//
				}
				else{//�߰�
					System.out.println("�α��ο� �����ϼ̽��ϴ�.");
					ds.imageLocalSave();
					setLogid(txtID.getText());	
					txtID.clear();
					passPW.clear();//
				}
				try {
					//mainview.setRoot(root);
					mainview.OpenMainView();//���� ȭ�� ȣ��
					} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				co.ShowAlertErr("��й�ȣ ����ġ");
				System.out.println("�α��� ����");
				if(checks) {//�߰�
					txtID.setText(txtID.getText());//�߰�
					passPW.clear();//�߰�
					passPW.requestFocus();//�߰�
				}else {
					txtID.clear();//
					passPW.clear();//
					txtID.requestFocus();//
				}
				
			}
		}else if(txtID.getText().isEmpty()||passPW.getText().isEmpty()) {
			co.ShowAlertErr("���̵� �Ǵ� ��й�ȣ�� �Է����ּ���.");
		}
		
	}
	
	//����â�Ϸ�.
	@Override
	public void Joinbtn() {
		System.out.println("ȸ������ â Ŭ���ϼ̽��ϴ�. ������ �Է����ּ���");
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
	//�ݱ��
	public void Cancle(Parent root) {
		TextField txtID = (TextField)root.lookup("#fxID");
		PasswordField passPW = (PasswordField)root.lookup("#fxPASS");
		txtID.clear();  passPW.clear();  txtID.requestFocus();
		System.out.println("���α׷��� �����մϴ�.");
		((Stage)root.getScene().getWindow()).close();
		
	}
	
	
}
