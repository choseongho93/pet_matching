package mainView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientEx.ClientExample;
import DAO.DatabaseService;
import DAO.DatabaseServiceImpl;
import Login.LoginWin.LoginServiceCrt;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mainView.common.CommonService;
import mainView.common.CommonServiceImpl;
import mainView.contact.Contact;
import mainView.contact.ContactImpl;
import mainView.findpetView.Findpet;
import mainView.findpetView.Findpetimpl;
import mainView.mediaservice.MediaServImpl;
import mainView.mediaservice.MediaServInter;
import mainView.petinsuView.petInsu;
import mainView.petinsuView.petInsuImpl;
import mainView.typechoiceView.typeChoice;
import mainView.typechoiceView.typeChoiceImpl;
import member.Owner;
import member.Pet;

public class Controller implements Initializable {
	public static Parent root;
	Label newUser;
	static private String selectId;
	static private Image selectedImg;
	private Parent root1;
	private Findpet findpet;
	private petInsu petinsu;
	private typeChoice typechoice;
	private CommonService comserv;
	private Login.commonService.CommonService comserv1;
	private Contact contact;
	private DatabaseService db;
	private ArrayList<Pet> petlist;
	private ArrayList<Owner> ownerlist;
	private Pet pt;
	private Owner owner;
	private Socket sock;
	Button btnStartStop;
	public ClientExample c;
	private MediaServInter mserv;

	public void setRoot(Parent root) {
		this.root = root;
	}

	public void setRoot1(Parent root1) {
		this.root1 = root1;
		mserv.setMedia(root1, "/BGM/Bensound - Cute.mp3");// 노래 경로
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		findpet = new Findpetimpl();
		petinsu = new petInsuImpl();
		typechoice = new typeChoiceImpl();
		comserv = new CommonServiceImpl();
		contact = new ContactImpl();
		mserv = new MediaServImpl();
		db = new DatabaseServiceImpl();
		petlist = new ArrayList<>();
		ownerlist = new ArrayList<>();
		petlist = db.selectPet();
		ownerlist = db.selectOwner();
		btnStartStop = new Button("start");
		c = new ClientExample(root);
		comserv1 = new Login.commonService.CommonServiceImpl();
	}

	public void sendMsg() {
		TextField textField = (TextField) root.lookup("#textField");
		String data = LoginServiceCrt.getLogid() + " " + textField.getText();
		textField.clear();
		c.send(data);
	}

	public void KeyPressed(ActionEvent e) {
		TextField textField = (TextField) root.lookup("#textField");
		String data = LoginServiceCrt.getLogid() + " " + textField.getText();
		textField.clear();
		c.send(data);
	}

	public void ClientStartStop() {
		if (btnStartStop.getText().equals("start")) {
			c.startClient();
			btnStartStop.setText("stop");
			Button btn = (Button) root.lookup("#btnStartStop");
			btn.setText("stop");
		} else if (btnStartStop.getText().equals("stop")) {
			c.stopClient();
			btnStartStop.setText("start");
			System.out.println(btnStartStop.getText());
			Button btn = (Button) root.lookup("#btnStartStop");
			btn.setText("start");
		}
	}

	public void findPet() throws IOException {// 좋은 짝 찾기(채팅기능 포함) 화면 실행
		findpet.OpenFindpetView();
		findpet.getUser(petlist);
		if (Findpetimpl.getListView() != null) {
			Findpetimpl.getListView().getSelectionModel().selectedIndexProperty()
					.addListener((obserable, oldValue, newValue) -> {
						selectedId(petlist.get((int) newValue).getId());
					});
		}
	}

	public void petInsurance() throws IOException {// 애완견 보험 화면 실행
		petinsu.OpenPetInsuView();
	}

	public void petInsu1() {// 보험 링크
		petinsu.insurance("https://www.meritzdirect.com/pet/product.do#!/");
	}

	public void petInsu2() {
		petinsu.insurance("http://www.hi-pet.co.kr/main/main.html");
	}

	public void petInsu3() {
		petinsu.insurance("http://www.fdog.co.kr/about/about_01.html");
	}

	public void typeChoice() throws IOException {// 이상형 월드컵 화면 실행
		typechoice.OpenTypeChoiceView();
	}

	public void CancleProc1() {
		comserv1.setRoot(root1);
		comserv1.WindowClose();
	}

	public void CancleProc2() {
		comserv1.setRoot(root);
		comserv1.WindowClose();
		c.stopClient();
	}

	public void CancleProc(ActionEvent e) {
		comserv.WindowClose(e);
		mserv.Stop();// 노래 정지
	}

	public void changePetLeft() {// 이상형 월드컵 화면에서 사진 클릭시 다른 사진으로 변경
		typechoice.setRoot(root1);
		try {
			typechoice.changePetLeft();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 사진 이미지 변경
	}

	public void changePetRight() {// 이상형 월드컵 화면에서 사진 클릭시 다른 사진으로 변경
		typechoice.setRoot(root1);
		try {
			typechoice.changePetRight();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 사진 이미지 변경
	}

	public void start() {// 이상형 월드컵 시작 버튼
		typechoice.setRoot(root1);
		typechoice.start();
		mserv.Start();// 노래 시작
	}

	public void contactUs() throws IOException {// Contact us 화면 실행
		contact.OpenContactView();
	}

	public void showProfile() throws IOException {// 프로필 창 오픈
//		findpet.setRoot(root);
		if (selectedImg == null) {
			File imgfile = new File("C:\\userdata\\imageData\\" + selectId + ".jpg");
			FileInputStream fis = new FileInputStream(imgfile);
			selectedImg = new Image(fis);
			System.out.println("이미지값없었음");
			System.out.println("ID:" + selectId);
			findpet.OpenProfile(this.selectId, this.selectedImg);
		} else {
			findpet.OpenProfile(this.selectId, this.selectedImg);
		}
		selectedImg = null;
	}

	public void selectedImg(Image selectImg) {
		// TODO Auto-generated method stub
		this.selectedImg = selectImg;
	}

	public void selectedId(String selectId) {
		// TODO Auto-generated method stub
		this.selectId = selectId;
	}

	public void mouseEntered01() {
		ImageView im01 = (ImageView) root1.lookup("#im01");
		Button Btn01 = (Button) root1.lookup("#Btn01");
		mouseEntered(Btn01, im01);
	}

	public void mouseExited01() {
		ImageView im01 = (ImageView) root1.lookup("#im01");
		Button Btn01 = (Button) root1.lookup("#Btn01");
		mouseExited(Btn01, im01);
	}

	public void mouseEntered02() {
		ImageView im02 = (ImageView) root1.lookup("#im02");
		Button Btn02 = (Button) root1.lookup("#Btn02");
		mouseEntered(Btn02, im02);
	}

	public void mouseExited02() {
		ImageView im02 = (ImageView) root1.lookup("#im02");
		Button Btn02 = (Button) root1.lookup("#Btn02");
		mouseExited(Btn02, im02);
	}

	public void mouseEntered03() {
		ImageView im03 = (ImageView) root1.lookup("#im03");
		Button Btn03 = (Button) root1.lookup("#Btn03");
		mouseEntered(Btn03, im03);
	}

	public void mouseExited03() {
		ImageView im03 = (ImageView) root1.lookup("#im03");
		Button Btn03 = (Button) root1.lookup("#Btn03");
		mouseExited(Btn03, im03);
	}

	public void mouseEntered04() {
		ImageView im04 = (ImageView) root1.lookup("#im04");
		Button Btn04 = (Button) root1.lookup("#Btn04");
		mouseEntered(Btn04, im04);
	}

	public void mouseExited04() {
		ImageView im04 = (ImageView) root1.lookup("#im04");
		Button Btn04 = (Button) root1.lookup("#Btn04");
		mouseExited(Btn04, im04);
	}

	public void mouseEntered(Button btn, ImageView im) {// 확대
		Button bt = btn;
		ImageView img = im;
		bt.setScaleX(1.1);
		bt.setScaleY(1.1);
		img.setScaleX(1.1);
		img.setScaleY(1.1);
	}

	public void mouseExited(Button btn, ImageView im) {// 축소
		Button bt = btn;
		ImageView img = im;
		bt.setScaleX(1);
		bt.setScaleY(1);
		img.setScaleX(1);
		img.setScaleY(1);
	}

	public void mouseEntered1() {
		ImageView im01 = (ImageView) root1.lookup("#im1");
		im01.setScaleX(1.1);
		im01.setScaleY(1.1);
	}

	public void mouseExited1() {
		ImageView im01 = (ImageView) root1.lookup("#im1");
		im01.setScaleX(1);
		im01.setScaleY(1);
	}

	public void mouseEntered2() {
		ImageView im02 = (ImageView) root1.lookup("#im2");
		im02.setScaleX(1.1);
		im02.setScaleY(1.1);
	}

	public void mouseExited2() {
		ImageView im02 = (ImageView) root1.lookup("#im2");
		im02.setScaleX(1);
		im02.setScaleY(1);
	}

	public void mouseEntered3() {
		ImageView im03 = (ImageView) root1.lookup("#im3");
		im03.setScaleX(1.1);
		im03.setScaleY(1.1);
	}

	public void mouseExited3() {
		ImageView im03 = (ImageView) root1.lookup("#im3");
		im03.setScaleX(1);
		im03.setScaleY(1);
	}

	public void mouseEntered4() {
		ImageView im04 = (ImageView) root1.lookup("#im4");
		im04.setScaleX(1.1);
		im04.setScaleY(1.1);
	}

	public void mouseExited4() {
		ImageView im04 = (ImageView) root1.lookup("#im4");
		im04.setScaleX(1);
		im04.setScaleY(1);
	}

	public void mouseEntered1_1() {
		ImageView im01 = (ImageView) root.lookup("#im1");
		im01.setScaleX(1.1);
		im01.setScaleY(1.1);
	}

	public void mouseExited1_1() {
		ImageView im01 = (ImageView) root.lookup("#im1");
		im01.setScaleX(1);
		im01.setScaleY(1);
	}

	public void mouseEntered2_2() {
		ImageView im02 = (ImageView) root.lookup("#im2");
		im02.setScaleX(1.1);
		im02.setScaleY(1.1);
	}

	public void mouseExited2_2() {
		ImageView im02 = (ImageView) root.lookup("#im2");
		im02.setScaleX(1);
		im02.setScaleY(1);
	}

	public void Play() {// 노래 재생
		mserv.Start();
	}

	public void Pause() {// 노래 일시정지
		mserv.Pause();
	}

	public void Stop() {// 노래 종료
		mserv.Stop();
	}

	public void volumnControl() {
		mserv.volumnControl();
	}

	public void mouseEnterPlay() {
		mserv.mouseEntered1();
	}

	public void mouseExitedPlay() {
		mserv.mouseExited1();
	}

	public void mouseEnterPause() {
		mserv.mouseEntered2();
	}

	public void mouseExitedPause() {
		mserv.mouseExited2();
	}

	public void mouseEnterStop() {
		mserv.mouseEntered3();
	}

	public void mouseExitedStop() {
		mserv.mouseExited3();
	}

	public void userBtn() {
		c.send(LoginServiceCrt.LoginId + "#Refresh");
		System.out.println("Refresh요청");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String a = c.getSize();
		displayRefresh(a);
	}

	public void displayRefresh(String text) {
		newUser = (Label) root.lookup("#newUser");
		System.out.println(text);
		newUser.setText(text);
		System.out.println("세팅완료.");
	}
}
