package Login;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.DatabaseService;
import DAO.DatabaseServiceImpl;
import Login.LoginWin.LoginService;
import Login.LoginWin.LoginServiceCrt;
import Login.commonService.CommonService;
import Login.commonService.CommonServiceImpl;
import Login.membership.MembershipService;
import Login.membership.MembershipServiceImpl;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import mainView.mainViewImpl;
import mainView.mainViewInter;

public class Controller implements Initializable{
	private Parent root; //기본창
	private LoginService lov; //로그인 기능
	private MembershipService mem;
	private DatabaseService ds;
	private mainViewInter main;
	private CommonService comserv;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		lov = new LoginServiceCrt();
		mem = new MembershipServiceImpl();
		ds = new DatabaseServiceImpl();
		main = new mainViewImpl();
		comserv = new CommonServiceImpl();
	}
	/*public Boolean DBCon(Owner own,Pet pt,FileInputStream fis,File f) {
		if(ds.Insert(own, pt, fis, f))return true;
		return false;
	}*/
	
	public void setRoot(Parent root) {this.root = root;}	
	public Parent getRoot() {return root;}

	public void memset() {mem.setRoot(getRoot());}
	
	public void Loginbtn() throws IOException {//로그인 버튼 클릭
		lov.Loginbtn(root);//dao select 실행.
	}

	public void Joinbtn() {
		lov.Joinbtn();	//가입창열림
	}

	/*public void Cancle(ActionEvent e) {
		Parent root = (Parent)e.getSource();
		Stage sg = (Stage)root.getScene().getWindow();
		sg.close();
	}*/
		
	public void OpenFile() {
		mem.FileOpen();
	}
	public void useraddT() throws IOException {
		mem.membershipProc();
	}
	public void CacleProc() {
		comserv.setRoot(root);
		comserv.WindowClose();
	}
	public void overlaptest() {
		mem.overlap();
	}
	public void mouseEntered01() {
		ImageView im01 = (ImageView)root.lookup("#im01");
		im01.setScaleX(1.1);
		im01.setScaleY(1.1);
	}
	public void mouseExited01(){
		ImageView im01 = (ImageView)root.lookup("#im01");
		im01.setScaleX(1);
		im01.setScaleY(1);
	}
	public void mouseEntered02() {
		ImageView im02 = (ImageView)root.lookup("#im02");
		im02.setScaleX(1.1);
		im02.setScaleY(1.1);
	}
	public void mouseExited02(){
		ImageView im02 = (ImageView)root.lookup("#im02");
		im02.setScaleX(1);
		im02.setScaleY(1);
	}
	public void mouseEntered03() {
		ImageView im03 = (ImageView)root.lookup("#im03");
		im03.setScaleX(1.2);
		im03.setScaleY(1.2);
	}
	public void mouseExited03(){
		ImageView im03 = (ImageView)root.lookup("#im03");
		im03.setScaleX(1);
		im03.setScaleY(1);
	}
}
