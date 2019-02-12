package member;

import java.io.File;
import java.sql.Blob;

public class Pet {
	private String id;
	private String name;
	private String species; //견종
	private String age;
	private String lineage; //혈통
	private Boolean gender;
	private String area;  //지역
	private int	inoculrecord; //접종기록 유무
	private String numofmating; //교미횟수
	private String imagename; //이미지이름
	private String spnote; //특이사항
	
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getSpecies() {return species;}
	public void setSpecies(String species) {this.species = species;}
	public String getAge() {return age;}
	public void setAge(String age) {this.age = age;}
	public String getLineage() {return lineage;}
	public void setLineage(String lineage) {this.lineage = lineage;}
	public Boolean getGender() {return gender;}
	public void setGender(Boolean gender) {this.gender = gender;}
	public String getArea() {return area;}
	public void setArea(String area) {this.area = area;}
	public int getInoculrecord() {return inoculrecord;}
	public void setInoculrecord(int inoculrecord) {this.inoculrecord = inoculrecord;}
	public String getNumofmating() {return numofmating;}
	public void setNumofmating(String numofmating) {this.numofmating = numofmating;}
	public String getImagename() {return imagename;}
	public void setImagename(String imagename) {this.imagename = imagename;}
	public String getSpnote() {return spnote;}
	public void setSpnote(String spnote) {this.spnote = spnote;}
}
