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
	public void OpenTypeChoiceView() throws IOException {// 이상형 월드컵 화면
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

	public void roundset() throws IOException {// 라운드 카운트
		if (cases == 0) {
			if (roundcnt == 9) {
				comserv.ShowAlertOk("8강 시작!!");
				roundcnt = 1;
				cases++;
				imagearr = selectimages;// 처음 이미지 배열에 선택된 이미지 배열 저장
				selectimages = new Image[9];
				Imagearrnum = 0;
				imagenum = 0;
			}
		} else if (cases == 1) {
			if (roundcnt == 5) {
				comserv.ShowAlertOk("준결승 시작!!");
				roundcnt = 1;
				cases++;
				imagearr = selectimages;
				selectimages = new Image[5];
				Imagearrnum = 0;
				imagenum = 0;
			}
		} else if (cases == 2) {
			if (roundcnt == 3) {
				comserv.ShowAlertOk("결승 시작!!");
				roundcnt = 1;
				cases++;
				imagearr = selectimages;
				selectimages = new Image[3];
				Imagearrnum = 0;
				imagenum = 0;
			}
		} else if (cases == 3) {/// 우승자 화면 보여주기
			if (roundcnt == 2) {
				endcase = 1;
				cases++;
			}
		}
	}

	@Override
	public void changePetLeft() {// 이미지 클릭시 사진 변경
		ImageView petimage01 = (ImageView) root.lookup("#petImage01");
		Label txtRound = (Label) root.lookup("#txtRound");

		// 선택된 이미지 저장
		selectimages[Imagearrnum] = petimage01.getImage();// 선택한 이미지를 select이미지 배열에 저장
		Imagearrnum++;

		roundcnt++;// 클릭 시 마다 라운드 수 증가
		// 이미지 교체

		// 이미지 번호를 -1로 초기화 ,라운드카운트비교,새로들어갈 이미지번호 초기화

		try {
			roundset();
			if (endcase == 0) {
				changeImage();
			} else if (endcase == 1) {
				lastSelect = 0;
				Imagearrnum = 0;
				imagenum = lastSelect;

				OpenWinView();// 우승자 화면 출력
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

		// 선택된 이미지 저장
		selectimages[Imagearrnum] = petimage02.getImage();
		Imagearrnum++;

		roundcnt++;// 클릭 시 마다 라운드 수 증가

		// 이미지 교체

		try {
			roundset();
			if (endcase == 0) {
				changeImage();
			} else if (endcase == 1) {
				lastSelect = 1;
				Imagearrnum = 0;
				imagenum = lastSelect;
				OpenWinView();// 우승자 화면 출력
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
		petimage01.setImage((imagearr[imagenum]));// 처음 시작시 1,2번 이미지 호출 했으므로 3번부터 시작
		imagenum++;
		petimage02.setImage((imagearr[imagenum]));
		imagenum++;
	}

	@Override
	public void start() {// 시작 버튼을 누르는 순간 랜덤으로 사진이 나타남
		comserv.ShowAlertOk("16강 시작!!");
		endcase = 0;
		ImageView petimage01 = (ImageView) root.lookup("#petImage01");
		ImageView petimage02 = (ImageView) root.lookup("#petImage02");
		Label txtRound = (Label) root.lookup("#txtRound");
		roundcnt = 1;
		txtRound.setText("" + roundcnt);
		compareArr = new Image[17]; // 비교 이미지배열
		imagearr = new Image[17];// 처음 시작 시 이미지 배열 생성

		selectimages = new Image[9];// 16강이 끝난 후 선택된 이미지 8장 저장하는 배열 생성

		File imageid = new File("C:\\userdata\\imgUserid\\SaveId.txt");
		try {
			FileInputStream fis = new FileInputStream(imageid);
			ObjectInputStream ois = new ObjectInputStream(fis);
			SavedId svid;
			svid = (SavedId) ois.readObject();

			int ran[] = new int[16];
			int random;

			// 16개의 난수 생성(최고 높은숫자는 가져온 이미지의 갯수)
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
				// 가져온 아이디값 확인
				System.out.println(svid.savedId[ran[i]]);

				ImagenUname iu = new ImagenUname();
				iu.setId(svid.savedId[ran[i]]);

				File imagefile = new File("C:\\userdata\\imageData\\" + svid.savedId[ran[i]] + ".jpg");
				FileInputStream ffis = new FileInputStream(imagefile);

				Image image = new Image(imagefile.toURI().toString());
				iu.setImage(image);
				// ArrayList <iu 형태 > iu 는 이미지에 해당하는 id가 저장되있는 class
				iuar.add(iu);
				imagearr[i] = iuar.get(i).getImage(); // 순서대로 imagearr에 해당 이미지 삽입
				compareArr[i] = iuar.get(i).getImage(); // 똑같이 삽입해서 나중에 어떤 이미지인지 비교하기위한 배열.
			}

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		petimage01.setImage((imagearr[0]));// 시작시 기본 이미지 셋
		petimage02.setImage((imagearr[1]));// 시작시 기본 이미지 셋
	}

	public void showWinImage(Parent root) {
		ImageView petimage03 = (ImageView) root.lookup("#petimage03");
		petimage03.setImage((imagearr[lastSelect]));

		for (int i = 0; i < 16; i++) {
			if (imagearr[lastSelect].equals(compareArr[i])) {
				// 마지막에 선택한 이미지랑 처음에 있던 전체 이미지 배열로 비교하여 선택된 이미지랑같은 번호의 인덱스를 기록.
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
	public void OpenWinView() throws IOException {// 우승자 화면 실행
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
