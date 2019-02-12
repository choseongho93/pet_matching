package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.scene.image.Image;
import member.*;

public interface DatabaseService {
	//회원등록 메소드
	public boolean Insert(Owner own,Pet pt,FileInputStream fis,File f);
	//로그인 메소드
	public Owner Select(Owner own);
	//로그인시 모든 강아지이미지 로컬 저장
	public void imageLocalSave();
	public Boolean overlapTest(String id);
	// 프로필 창에 넣을  강아지데이터 겟터
	public Pet getDProfile(String id);
	//  // ph번호 겟터
	public Owner getUProfile(String id);
	public ArrayList<Pet> selectPet();
	public ArrayList<Owner> selectOwner();
	
}
