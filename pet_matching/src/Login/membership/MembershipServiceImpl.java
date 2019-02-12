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
		//������ �� ��������
		TextField uName = (TextField)root.lookup("#uName"); //�̸�
		TextField uId = (TextField)root.lookup("#uId"); //�Ƶ�
		TextField uPh = (TextField)root.lookup("#uPh"); //��ȭ��ȣ
		PasswordField uPw = (PasswordField)root.lookup("#uPw"); //��й�ȣ
		PasswordField uPwc = (PasswordField)root.lookup("#uPwc"); //Ȯ��
		Boolean gender = getGender(); //��� (���� �� ==1���� //����==0 ����)
									//������(���� �� ==1���� //����==0 ����)
		TextField dName = (TextField)root.lookup("#dName"); //�������̸�
		TextField dSpecies = (TextField)root.lookup("#dSpecies"); //������ ��
		TextField dAge = (TextField)root.lookup("#dAge"); //������ ����
		TextField dLineage = (TextField)root.lookup("#dLineage"); //������ ����
		Boolean dGender = getdGender(); //������ �ϼ�

		TextField dArea = (TextField)root.lookup("#dArea"); //������ ����
		TextField dMating = (TextField)root.lookup("#dMating"); //������ ����Ƚ��
		TextArea spnote = (TextArea)root.lookup("#spNote");//Ư�̻���
		Boolean incYN = getInc(); // �������
		int inc;
		if(incYN) {inc = 1;
		}else {inc =0;}

		System.out.println("�̸�:"+uName.getText().toString());
		System.out.println("ID:"+uId.getText().toString());
		System.out.println("PW:"+uPw.getText().toString());
		System.out.println("����:"+gender);
		System.out.println("��ȭ��ȣ:"+uPh.getText().toString());

		if(uName.getText().isEmpty()) {
			co.Alert(1,"�̸��� �Է��ϼ���.");
		}
		if(uId.getText().isEmpty()) {
			co.Alert(1,"���̵� �Է��ϼ���.");
		}
		if(uPw.getText().isEmpty()) {
			co.Alert(1,"��й�ȣ�� �Է����ּ���.");
		}
		if(uPwc.getText().isEmpty()) {
			co.Alert(1,"��й�ȣȮ���� �Է����ּ���.");
		}
		if(uPh.getText().isEmpty()) {
			co.Alert(1,"��ȭ��ȣ�� �Է����ּ���.");
		}
		//���� ����̾��ٸ�
		if(uName.getText().isEmpty()==false && uId.getText().isEmpty()==false &&uPw.getText().isEmpty()==false&&uPh.getText().isEmpty()==false) {
			System.out.println("���� ��ĭ����");

			if(dName.getText().isEmpty()) {
				co.Alert(1,"�ְ��̸��� �Է��ϼ���.");
			}
			if(dAge.getText().isEmpty()) {
				co.Alert(1,"���̸� �Է��ϼ���.");
			}
			if(dSpecies.getText().isEmpty()) {
				co.Alert(1,"������ �Է����ּ���.");
			}
			if(dArea.getText().isEmpty()) {
				co.Alert(1,"������ �Է����ּ���.");
			}
			if(dName.getText().isEmpty()==false && dSpecies.getText().isEmpty()==false && dAge.getText().isEmpty()==false && dArea.getText().isEmpty()==false) {
				if(uPw.getText().toString().equals(uPwc.getText().toString())) {
					System.out.println("test");
					own.setName(uName.getText());
					own.setId(uId.getText());
					own.setPw(uPw.getText());
					own.setGender(gender);
					own.setPh(uPh.getText());
					//���μ���

					//����������
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
					if(bool) {co.Alert(0, "���������� ���� �Ǿ����ϴ�.");
						co.WindowClose();}
					else {co.Alert(1,"�������.");}
			
				}else{
					System.out.println("��й�ȣ�� Ȯ�����ּ���.");
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
			//������ �����ϸ� ���ϱ��̸�ŭ ���� ���� �� ���� �о����
			try {
				this.fis = new FileInputStream(file);
				Image image1 = new Image(file.toURI().toString());
				String filename = file.getPath();
				int lastIndex = file.getPath().lastIndexOf(File.separator); 
				String filestr=filename.substring(lastIndex+1);
				//��ο��� �����̸��� ����
								
				pt.setImagename(filestr);
				ImageView image = (ImageView)root.lookup("#image");
				image.setImage(image1);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("�����");
		}
	}
	@Override
	public void overlap() {
		TextField uId = (TextField)root.lookup("#uId");
	      if(ds.overlapTest(uId.getText())) {
	         co.Alert(1, "���̵� �����մϴ�.");
	      }else {
	         co.Alert(0, "��� �� �� �ִ� ���̵��Դϴ�.");
	      }
	}
}
