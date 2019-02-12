package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import member.Owner;
import member.Pet;

public class DatabaseServiceImpl implements DatabaseService {
	//����̹� ���ڿ� ======== oracle ȸ��� , jdbc api  , driver , oracledriver???
	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	//��� jdbc api  //����Ŭ //thin:@�ּ�:��Ʈ:xe; == URL;
	final String URL = "jdbc:oracle:thin:@192.168.0.5:1521:xe";//vm�����ǰ� ��� ���ϹǷ� ��������� ��
	//SQL��.
	final String INSERTSQLO =
			"INSERT INTO owner(id,pw,name,ph,gender) VALUES(?,?,?,?,?)";
	final String INSERTSQLP = 
			"INSERT INTO pet(id,name,species,age,lineage,gender,area,inoculrecord,numofmating,imagename,imagebin,spnote) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	//Ŀ��ƮŬ������ ����.
	private static Connection conn;
	//������� ����
	private PreparedStatement pstm;
	//��������� ResultSet 
	private ResultSet rs;

	String user="java",pw="1234";
	static {
		try {
			//����̹� ���� ?  ?
			Class.forName(DRIVER);	
		}catch(Exception e){
			e.printStackTrace();			
		}
	}
	// ���� Ŭ���� ������ ���ÿ� �����.
	//	public DatabaseServiceImpl() { user = "java"; pw = "1234";	}

	@Override
	//ȸ������ �޼ҵ�
	public boolean Insert(Owner own,Pet pt,FileInputStream fis,File f) {
		//�μ�Ʈ �޼ҵ� ����� ��ȿ���� ��/���� ��ȯ
		// TODO Auto-generated method stub
		try {
			conn = DriverManager.getConnection(URL, user, pw);
			//���ο� ���� ���� ���� DB��������
			pstm = conn.prepareStatement(INSERTSQLO);
			pstm.setString(1, own.getId());
			pstm.setString(2, own.getPw()); 
			pstm.setString(3, own.getName());
			pstm.setString(4, own.getPh()); 
			int ogender;
			if(own.getGender()) {ogender = 1;}else{ogender=0;}
			pstm.setInt(5, ogender); 
			System.out.println("ù��° ��������");
			int i = pstm.executeUpdate();
			System.out.println("i : "+i);
			System.out.println("����Ϸ�");
			//
			//id,name,species,age,lineage,gender,area,inoculrecord,numofmating,imagename,imagebin,spnote
			//��� (���� �� ==1���� //����==0 ����)
			//������(���� �� ==1���� //����==0 ����)
			pstm = conn.prepareStatement(INSERTSQLP);
			pstm.setString(1, pt.getId()); pstm.setString(2, pt.getName());
			pstm.setString(3, pt.getSpecies()); pstm.setString(4,pt.getAge());
			pstm.setString(5,pt.getLineage());
			int pgender;
			if(pt.getGender()) {pgender = 1;}else{pgender=0;}
			pstm.setInt(6,pgender ); pstm.setString(7, pt.getArea());
			pstm.setInt(8, pt.getInoculrecord());pstm.setString(9, pt.getNumofmating());
			pstm.setString(10, pt.getImagename());pstm.setBinaryStream(11, fis,(int)f.length());
			pstm.setString(12, pt.getSpnote());
			System.out.println("�ι�° ���� ����");
			pstm.executeQuery();
			System.out.println("����Ϸ�.");

		}catch(SQLException e) {
			e.printStackTrace(); return false;
		}
		finally {
			try {
				if(pstm != null) pstm.close(); if(conn != null) conn.close();
			}catch(SQLException e) { e.printStackTrace();	}
		}
		return true;
	}

	//�α��ν� Ȯ���� ���� �޼ҵ�
	public Owner Select(Owner own) {
		// TODO Auto-generated method stub
		String sql = "select id,pw from owner where id=?";
		Owner reown = new Owner();
		try {
			conn=DriverManager.getConnection(URL, user, pw);
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, own.getId());
			rs = pstm.executeQuery();
			if(rs.next()) {
				reown.setId(rs.getString("id")); 
				reown.setPw(rs.getString("pw"));
			}
			return reown;
		} catch(Exception e) { e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close(); if(pstm != null) pstm.close(); if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}



	@Override
	//�̹��� ���ÿ� �����ϴ� �޼ҵ�
	public void imageLocalSave() {
		// TODO Auto-generated method stub
		//������ ���� �� ���
		String rowsql = "select count(id) from pet";
		int rowcnt = 0;
		try {
			conn=DriverManager.getConnection(URL, user, pw);
			pstm = conn.prepareStatement(rowsql);
			ResultSet rs2 = null;
			rs2 = pstm.executeQuery();
			rs2.next();
			rowcnt=rs2.getInt("count(id)");
			if(rs2 != null) rs2.close(); if(pstm != null) pstm.close();
			
		}catch(Exception e ) {
			e.printStackTrace();
		}
		
		//id�� �̹��� blob��� ���Ϸ� ��� ,  ���� ID String�� ������Ʈ������ ���
		String sql = "select id,imagebin from pet";
		SavedId svid = new SavedId();
		svid.savedId = new String[rowcnt];
		try {
			int idnum=0;
			conn=DriverManager.getConnection(URL, user, pw);
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			File dir0 = new File("C:\\userdata");
			dir0.mkdir();
			File dir1 = new File(dir0.getPath()+File.separator+"imgUserid");
			dir1.mkdir();
			File dir2 = new File(dir0.getPath()+File.separator+"imageData");
			dir2.mkdir();
			while(rs.next()) {
				svid.savedId[idnum] = rs.getString("id");
				Blob bfile = rs.getBlob("imagebin");
				System.out.print("Blob���:");
				System.out.println(bfile);

				byte[] outdata = new byte[10000];
				outdata = bfile.getBytes(1,(int)bfile.length());
				//�̹����� ����� ���� ���丮

				File f = new File(dir2.getPath()+File.separator+svid.savedId[idnum]+".jpg");

				System.out.print("byte��������� : ");
				System.out.println(outdata);
				//���Ͼ���
				System.out.println("�̹��� ���� ������");
				OutputStream out;
				FileOutputStream fos = new FileOutputStream(f);
				fos.write(outdata);
				fos.close();

				idnum++;
			}

			File i = new File(dir1.getPath()+File.separator+"SaveId.txt");
			FileOutputStream fos = new FileOutputStream(i);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(svid);
			oos.close();
			fos.close();
			System.out.println("�̹��� ���� �� �̹��������� ������ID ��ϿϷ�.");
			idnum=0;

			//�����б�
			//			FileInputStream fin = new FileInputStream(f);
			//			Image image =new Image(f.toURI().toString());


		} catch(Exception e) { e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close(); if(pstm != null) pstm.close(); if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub


	}

	@Override
	public Boolean overlapTest(String id) {
		// TODO Auto-generated method stub
		String sql = "select id from owner where id=?";
		try {
			conn=DriverManager.getConnection(URL, user, pw);
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			rs = pstm.executeQuery();
			System.out.println(rs);
			if(rs.next()) {
				System.out.println("����");
				return true;
			}else {
				System.out.println("������������");
				return false;
			}
		} catch(Exception e) { e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close(); if(pstm != null) pstm.close(); if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	@Override
	public Owner getUProfile(String id) {
		String sql = "select ph from owner where id=?";
		
		Owner own = new Owner();
		try {
			conn = DriverManager.getConnection(URL, user, pw);
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			rs = pstm.executeQuery();
			if(rs.next()) {
				own.setPh(rs.getString("ph"));
			}
			return own;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs != null) rs.close(); if(pstm != null) pstm.close(); if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public Pet getDProfile(String id) {
		String sql = "select name,age,species,area,inoculrecord,numofmating,lineage,gender from pet where id = ?";
		Pet pt = new Pet();
		try {
			conn = DriverManager.getConnection(URL, user, pw);
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			rs = pstm.executeQuery();
			if(rs.next()) {
				pt.setName(rs.getString("name"));
				pt.setAge(rs.getString("age"));
				pt.setSpecies(rs.getString("species"));
				pt.setArea(rs.getString("area"));
				pt.setInoculrecord(rs.getInt("inoculrecord"));
				pt.setNumofmating(rs.getString("numofmating"));
				pt.setLineage(rs.getString("lineage"));
				boolean gender;
				if(rs.getInt("gender")==1) {gender=true;}
				else {gender=false;}
				pt.setGender(gender);
			}
			return pt;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs != null) rs.close(); if(pstm != null) pstm.close(); if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public ArrayList<Pet> selectPet() {
		String sql = "select * from pet";
		ArrayList<Pet> petlist = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(URL, user, pw);
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				Pet pt = new Pet();
				pt.setId(rs.getString("id"));
				pt.setName(rs.getString("name"));
				pt.setSpecies(rs.getString("species"));
				pt.setAge(rs.getString("age"));
				pt.setLineage(rs.getString("lineage"));
				boolean gender;
				if(rs.getInt("gender")==1) {gender=true;}
				else {gender=false;}
				pt.setGender(gender);
				pt.setArea(rs.getString("area"));
				pt.setInoculrecord(rs.getInt("inoculrecord"));
				pt.setNumofmating(rs.getString("numofmating"));
				pt.setImagename(rs.getString("imagename"));
				pt.setSpnote(rs.getString("spnote"));
				petlist.add(pt);
			}
		} catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null ) rs.close();
				if(pstm != null ) pstm.close();
				if(conn != null ) conn.close();
			} catch (SQLException e) { e.printStackTrace(); }
		}
		return petlist;
	}
	
	@Override
	public ArrayList<Owner> selectOwner() {
		String sql = "select * from owner";
		ArrayList<Owner> ownerlist = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(URL, user, pw);
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while(rs.next()) {
				Owner owner = new Owner();
				owner.setId(rs.getString("id"));
				owner.setPw(rs.getString("pw"));
				owner.setName(rs.getString("name"));
				boolean gender;
				if(rs.getInt("gender")==1) {gender=true;}
				else {gender=false;}
				owner.setGender(gender);
				owner.setPh(rs.getString("ph"));
				ownerlist.add(owner);
			}
		} catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null ) rs.close();
				if(pstm != null ) pstm.close();
				if(conn != null ) conn.close();
			} catch (SQLException e) { e.printStackTrace(); }
		}
		return ownerlist;
	}
}


