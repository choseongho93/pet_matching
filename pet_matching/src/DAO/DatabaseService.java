package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.scene.image.Image;
import member.*;

public interface DatabaseService {
	//ȸ����� �޼ҵ�
	public boolean Insert(Owner own,Pet pt,FileInputStream fis,File f);
	//�α��� �޼ҵ�
	public Owner Select(Owner own);
	//�α��ν� ��� �������̹��� ���� ����
	public void imageLocalSave();
	public Boolean overlapTest(String id);
	// ������ â�� ����  ������������ ����
	public Pet getDProfile(String id);
	//  // ph��ȣ ����
	public Owner getUProfile(String id);
	public ArrayList<Pet> selectPet();
	public ArrayList<Owner> selectOwner();
	
}
