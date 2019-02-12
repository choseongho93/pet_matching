package mainView.findpetView;

import java.io.IOException;
import java.util.ArrayList;

import DAO.DatabaseService;
import DAO.DatabaseServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mainView.Controller;
import member.Owner;
import member.Pet;

public class Findpetimpl implements Findpet{
	@FXML static ListView<String> listView;
	static public ListView<String> getListView() {return listView;}
	public void setListView(ListView<String> listView) {this.listView=listView;}
	private Parent root;
	private DatabaseService ds= new DatabaseServiceImpl();
	@Override
	public void setRoot(Parent root) {this.root=root;}
	@Override
	public void getUser(ArrayList<Pet> petlist) {
		Pet pt=new Pet();
		listView = (ListView<String>) root.lookup("#listView");
		listView.getItems().clear();
		for (int i = 0; i < petlist.size(); i++) {
			pt = petlist.get(i);
			listView.getItems().add("유저Id : "+pt.getId()+" (강아지 이름 : " + pt.getName() + ")");
		}
		setListView(listView);
	}
	@Override
	public void OpenFindpetView() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../chatting.fxml"));
		Parent root = loader.load();
		Controller ctr = loader.getController();
		ctr.setRoot(root);
		setRoot(root);
		Stage sg = new Stage();
		Scene sc = new Scene(root);
		sg.setScene(sc);
		sg.show();
	}

	@Override
	public void OpenProfile(String id,Image img) throws IOException {//�봽濡쒗븘 �삤�뵂
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../profile.fxml"));
		Parent root = loader.load();
		Controller ctr = loader.getController();
		ctr.setRoot1(root);
		setRoot(root);
		//�뱾�뼱�삩 id媛� �뾾�쓬 .�븘吏�.
		System.out.println("id:"+id);
		
		String genderst="";
		System.out.println("1번 ==========================");
		//�뜲�씠�꽣 �뼸湲�.
		Owner own = ds.getUProfile(id);
		Pet pt = ds.getDProfile(id);
		// �씠誘몄� �뀑�똿
		ImageView selectImg = (ImageView)root.lookup("#selectImg");
		selectImg.setImage(img);
		Label name = (Label)root.lookup("#name");
		name.setText(pt.getName());
		Label age = (Label)root.lookup("#age");
		age.setText(pt.getAge());
		Label area = (Label)root.lookup("#area");
		area.setText(pt.getArea());
		Label gender = (Label)root.lookup("#gender");
		System.out.println(pt.getGender());
		if(pt.getGender()==true) {
			System.out.println("수컷==================");
			genderst="수컷";}
		else{genderst="암컷";}
		gender.setText(genderst);
		Label species = (Label)root.lookup("#species");
		species.setText(pt.getSpecies());
		Label lineage = (Label)root.lookup("#lineage");
		lineage.setText(pt.getLineage());
		Label ph = (Label)root.lookup("#ph");
		ph.setText(own.getPh());
		Label inocul = (Label)root.lookup("#inocul");
		if(pt.getInoculrecord()==1) {
			inocul.setText("있음");
		}else if(pt.getInoculrecord()==0) {
			inocul.setText("없음");
		}
		
		
		Stage sg = new Stage();
		Scene sc = new Scene(root);
		sg.setScene(sc);
		sg.show();
	}

}
