package mainView.typechoiceView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import DAO.SavedId;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mainView.Controller;
import mainView.common.CommonService;
import mainView.common.CommonServiceImpl;
import member.Pet;

public class typeChoiceImpl implements typeChoice {
	private Parent root;
	private CommonService comserv = new CommonServiceImpl();
	private Pet pet;
	private int roundcnt = 0;
	private Image[] imagearr;
	private Image[] selectimages;
	private Image[] compareArr;
	private int Imagearrnum = 0;
	private int imagenum = 2;//
//	private String path = "../../pet/";
	private int cases = 0;
	private int endcase = 0;
	private String selectId = "";
	private Image selectImg;
	private int lastSelect;

	public Image getSelectImg() {
		return selectImg;
	}

	public String getSelectId() {
		return selectId;
	}

	int index;
	public ArrayList<ImagenUname> iuar = new ArrayList<>();

	@Override
	public void setRoot(Parent root) {
		this.root = root;
	}

	@Override
	public void OpenTypeChoiceView() throws IOException {// �̻��� ������ ȭ��
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../typeChoice.fxml"));
		Parent root = loader.load();
		Controller ctr = loader.getController();
		ctr.setRoot1(root);
		setRoot(root);
		Stage sg = new Stage();
		Scene sc = new Scene(root);
		sg.setScene(sc);
		sg.show();
	}

	public void roundset() throws IOException {// ���� ī��Ʈ
		if (cases == 0) {
			if (roundcnt == 9) {
				comserv.ShowAlertOk("8�� ����!!");
				roundcnt = 1;
				cases++;
				imagearr = selectimages;// ó�� �̹��� �迭�� ���õ� �̹��� �迭 ����
				selectimages = new Image[9];
				Imagearrnum = 0;
				imagenum = 0;
			}
		} else if (cases == 1) {
			if (roundcnt == 5) {
				comserv.ShowAlertOk("�ذ�� ����!!");
				roundcnt = 1;
				cases++;
				imagearr = selectimages;
				selectimages = new Image[5];
				Imagearrnum = 0;
				imagenum = 0;
			}
		} else if (cases == 2) {
			if (roundcnt == 3) {
				comserv.ShowAlertOk("��� ����!!");
				roundcnt = 1;
				cases++;
				imagearr = selectimages;
				selectimages = new Image[3];
				Imagearrnum = 0;
				imagenum = 0;
			}
		} else if (cases == 3) {/// ����� ȭ�� �����ֱ�
			if (roundcnt == 2) {
				endcase = 1;
				cases++;
			}
		}
	}

	@Override
	public void changePetLeft() {// �̹��� Ŭ���� ���� ����
		ImageView petimage01 = (ImageView) root.lookup("#petImage01");
		Label txtRound = (Label) root.lookup("#txtRound");

		// ���õ� �̹��� ����
		selectimages[Imagearrnum] = petimage01.getImage();// ������ �̹����� select�̹��� �迭�� ����
		Imagearrnum++;

		roundcnt++;// Ŭ�� �� ���� ���� �� ����
		// �̹��� ��ü

		// �̹��� ��ȣ�� -1�� �ʱ�ȭ ,����ī��Ʈ��,���ε� �̹�����ȣ �ʱ�ȭ

		try {
			roundset();
			if (endcase == 0) {
				changeImage();
			} else if (endcase == 1) {
				lastSelect = 0;
				Imagearrnum = 0;
				imagenum = lastSelect;

				OpenWinView();// ����� ȭ�� ���
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		txtRound.setText(roundcnt + "");
	}

	@Override
	public void changePetRight() {
		ImageView petimage02 = (ImageView) root.lookup("#petImage02");
		Label txtRound = (Label) root.lookup("#txtRound");

		// ���õ� �̹��� ����
		selectimages[Imagearrnum] = petimage02.getImage();
		Imagearrnum++;

		roundcnt++;// Ŭ�� �� ���� ���� �� ����

		// �̹��� ��ü

		try {
			roundset();
			if (endcase == 0) {
				changeImage();
			} else if (endcase == 1) {
				lastSelect = 1;
				Imagearrnum = 0;
				imagenum = lastSelect;
				OpenWinView();// ����� ȭ�� ���
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		txtRound.setText(roundcnt + "");
	}

	public void changeImage() {
		ImageView petimage01 = (ImageView) root.lookup("#petImage01");
		ImageView petimage02 = (ImageView) root.lookup("#petImage02");
		petimage01.setImage((imagearr[imagenum]));// ó�� ���۽� 1,2�� �̹��� ȣ�� �����Ƿ� 3������ ����
		imagenum++;
		petimage02.setImage((imagearr[imagenum]));
		imagenum++;
	}

	@Override
	public void start() {// ���� ��ư�� ������ ���� �������� ������ ��Ÿ��
		comserv.ShowAlertOk("16�� ����!!");
		endcase = 0;
		ImageView petimage01 = (ImageView) root.lookup("#petImage01");
		ImageView petimage02 = (ImageView) root.lookup("#petImage02");
		Label txtRound = (Label) root.lookup("#txtRound");
		roundcnt = 1;
		txtRound.setText("" + roundcnt);
		compareArr = new Image[17]; // �� �̹����迭
		imagearr = new Image[17];// ó�� ���� �� �̹��� �迭 ����

		selectimages = new Image[9];// 16���� ���� �� ���õ� �̹��� 8�� �����ϴ� �迭 ����

		File imageid = new File("C:\\userdata\\imgUserid\\SaveId.txt");
		try {
			FileInputStream fis = new FileInputStream(imageid);
			ObjectInputStream ois = new ObjectInputStream(fis);
			SavedId svid;
			svid = (SavedId) ois.readObject();

			int ran[] = new int[16];
			int random;

			// 16���� ���� ����(�ְ� �������ڴ� ������ �̹����� ����)
			for (int i = 0; i < 16; i++) {
				ran[i] = (int) (Math.random() * svid.savedId.length);
				for (int j = 0; j < i; j++) {
					if (ran[i] == ran[j]) {
						i--;
					}
				}
			}
			for (int i = 0; i < 16; i++) {
				System.out.print(ran[i] + "\t");
			}

			for (int i = 0; i < 16; i++) {
				// ������ ���̵� Ȯ��
				System.out.println(svid.savedId[ran[i]]);

				ImagenUname iu = new ImagenUname();
				iu.setId(svid.savedId[ran[i]]);

				File imagefile = new File("C:\\userdata\\imageData\\" + svid.savedId[ran[i]] + ".jpg");
				FileInputStream ffis = new FileInputStream(imagefile);

				Image image = new Image(imagefile.toURI().toString());
				iu.setImage(image);
				// ArrayList <iu ���� > iu �� �̹����� �ش��ϴ� id�� ������ִ� class
				iuar.add(iu);
				imagearr[i] = iuar.get(i).getImage(); // ������� imagearr�� �ش� �̹��� ����
				compareArr[i] = iuar.get(i).getImage(); // �Ȱ��� �����ؼ� ���߿� � �̹������� ���ϱ����� �迭.
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		petimage01.setImage((imagearr[0]));// ���۽� �⺻ �̹��� ��
		petimage02.setImage((imagearr[1]));// ���۽� �⺻ �̹��� ��
	}

	public void showWinImage(Parent root) {
		ImageView petimage03 = (ImageView) root.lookup("#petimage03");
		petimage03.setImage((imagearr[lastSelect]));

		for (int i = 0; i < 16; i++) {
			if (imagearr[lastSelect].equals(compareArr[i])) {
				// �������� ������ �̹����� ó���� �ִ� ��ü �̹��� �迭�� ���Ͽ� ���õ� �̹��������� ��ȣ�� �ε����� ���.
				index = i;
			}
		}
		System.out.println(index);
		selectImg = imagearr[lastSelect];
		selectId = iuar.get(index).getId();
		System.out.println(selectId);
	}

//	public void setSelectImage(Image img) {
//		this.selectImg = img;
//	}
	public Image getSelectImage() {
		return this.selectImg;
	}

	@Override
	public void OpenWinView() throws IOException {// ����� ȭ�� ����
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../winView.fxml"));
		Parent root = loader.load();
		showWinImage(root);
		Controller ctr = loader.getController();
		ctr.setRoot1(root);
		ctr.selectedId(this.selectId);
		ctr.selectedImg(this.selectImg);
		setRoot(root);
		Stage sg = new Stage();
		Scene sc = new Scene(root);
		sg.setScene(sc);
		sg.show();
	}

}
