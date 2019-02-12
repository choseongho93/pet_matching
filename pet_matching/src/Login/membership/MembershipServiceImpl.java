package Login.membership;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import DAO.DatabaseService;
import DAO.DatabaseServiceImpl;
import Login.commonService.CommonService;
import Login.commonService.CommonServiceImpl;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import member.Owner;
import member.Pet;

public class MembershipServiceImpl implements MembershipService{
	private Parent root;
	private FileInputStream fis;
	private File file;

	public static Owner own = new Owner();
	public static Pet pt = new Pet();
	CommonService co = new CommonServiceImpl();
	DatabaseService ds = new DatabaseServiceImpl();
	@Override
	public void setRoot(Parent root) {this.root = root;}
	@Override
	public void membershipProc()throws IOException{
		// TODO Auto-generated method stub
		//설정된 값 가져오기
		TextField uName = (TextField)root.lookup("#uName"); //이름
		TextField uId = (TextField)root.lookup("#uId"); //아디
		TextField uPh = (TextField)root.lookup("#uPh"); //전화번호
		PasswordField uPw = (PasswordField)root.lookup("#uPw"); //비밀번호
		PasswordField uPwc = (PasswordField)root.lookup("#uPwc"); //확인
		Boolean gender = getGender(); //사람 (성별 참 ==1남자 //거짓==0 여자)
									//강아지(성별 참 ==1수컷 //거짓==0 암컷)
		TextField dName = (TextField)root.lookup("#dName"); //강아지이름
		TextField dSpecies = (TextField)root.lookup("#dSpecies"); //강아지 종
		TextField dAge = (TextField)root.lookup("#dAge"); //강아지 나이
		TextField dLineage = (TextField)root.lookup("#dLineage"); //강아지 혈통
		Boolean dGender = getdGender(); //강아지 암수

		TextField dArea = (TextField)root.lookup("#dArea"); //강아지 지역
		TextField dMating = (TextField)root.lookup("#dMating"); //강아지 교배횟수
		TextArea spnote = (TextArea)root.lookup("#spNote");//특이사항
		Boolean incYN = getInc(); // 접종기록
		int inc;
		if(incYN) {inc = 1;
		}else {inc =0;}

		System.out.println("이름:"+uName.getText().toString());
		System.out.println("ID:"+uId.getText().toString());
		System.out.println("PW:"+uPw.getText().toString());
		System.out.println("성별:"+gender);
		System.out.println("전화번호:"+uPh.getText().toString());

		if(uName.getText().isEmpty()) {
			co.Alert(1,"이름을 입력하세요.");
		}
		if(uId.getText().isEmpty()) {
			co.Alert(1,"아이디를 입력하세요.");
		}
		if(uPw.getText().isEmpty()) {
			co.Alert(1,"비밀번호를 입력해주세요.");
		}
		if(uPwc.getText().isEmpty()) {
			co.Alert(1,"비밀번호확인을 입력해주세요.");
		}
		if(uPh.getText().isEmpty()) {
			co.Alert(1,"전화번호를 입력해주세요.");
		}
		//위에 빈것이없다면
		if(uName.getText().isEmpty()==false && uId.getText().isEmpty()==false &&uPw.getText().isEmpty()==false&&uPh.getText().isEmpty()==false) {
			System.out.println("유저 빈칸없음");

			if(dName.getText().isEmpty()) {
				co.Alert(1,"애견이름을 입력하세요.");
			}
			if(dAge.getText().isEmpty()) {
				co.Alert(1,"나이를 입력하세요.");
			}
			if(dSpecies.getText().isEmpty()) {
				co.Alert(1,"견종을 입력해주세요.");
			}
			if(dArea.getText().isEmpty()) {
				co.Alert(1,"지역을 입력해주세요.");
			}
			if(dName.getText().isEmpty()==false && dSpecies.getText().isEmpty()==false && dAge.getText().isEmpty()==false && dArea.getText().isEmpty()==false) {
				if(uPw.getText().toString().equals(uPwc.getText().toString())) {
					System.out.println("test");
					own.setName(uName.getText());
					own.setId(uId.getText());
					own.setPw(uPw.getText());
					own.setGender(gender);
					own.setPh(uPh.getText());
					//주인설정

					//강아지설정
					pt.setArea(dArea.getText());
					pt.setAge(dAge.getText());
					pt.setGender(dGender);
					pt.setId(uId.getText());
					pt.setInoculrecord(inc);
					pt.setLineage(dLineage.getText());
					pt.setName(dName.getText());
					pt.setNumofmating(dMating.getText());
					pt.setSpecies(dSpecies.getText());
					pt.setSpnote(spnote.getText());

					System.out.println(own.getId()+"\n"+pt.getId()+"\n");
					boolean bool = ds.Insert(own,pt,fis,file);
					if(bool) {co.Alert(0, "성공적으로 저장 되었습니다.");
						co.WindowClose();}
					else {co.Alert(1,"저장실패.");}
			
				}else{
					System.out.println("비밀번호를 확인해주세요.");
				}
			}

		}				
	}
	private Boolean getGender(){ 
		RadioButton uMale = (RadioButton)root.lookup("#uMale");
		if(uMale.isSelected())	return true;//MAN
		return false;//
	}
	private Boolean getInc(){
		RadioButton incN = (RadioButton)root.lookup("#incN");
		if(incN.isSelected())	return false;//
		return true;//
	}
	private Boolean getdGender(){
		RadioButton dMale = (RadioButton)root.lookup("#dMale");
		if(dMale.isSelected())	return true;//female
		return false;//Man
	}
	@Override
	public void FileOpen() {
		Stage stage = new Stage();
		FileChooser filechooser = new FileChooser();
		filechooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*"));
		this.file = filechooser.showOpenDialog(stage);
		if(file !=null) {
			//파일이 존재하면 파일길이만큼 버퍼 생성 후 파일 읽어들임
			try {
				this.fis = new FileInputStream(file);
				Image image1 = new Image(file.toURI().toString());
				String filename = file.getPath();
				int lastIndex = file.getPath().lastIndexOf(File.separator); 
				String filestr=filename.substring(lastIndex+1);
				//경로에서 파일이름만 나눔
								
				pt.setImagename(filestr);
				ImageView image = (ImageView)root.lookup("#image");
				image.setImage(image1);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("취소함");
		}
	}
	@Override
	public void overlap() {
		TextField uId = (TextField)root.lookup("#uId");
	      if(ds.overlapTest(uId.getText())) {
	         co.Alert(1, "아이디가 존재합니다.");
	      }else {
	         co.Alert(0, "사용 할 수 있는 아이디입니다.");
	      }
	}
}
