package student_management;

public class Student {
	
	private String name;
	private int rollNumber;
	private int grade;
	
	public Student(String n, int rn, int g) {
		name = n;
		rollNumber = rn;
		grade = g;
	}
	
	public String getName() {
		return name;
	}
	
	public int getRollNumber() {
		return rollNumber;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setRollNumber(int rn){
		rollNumber = rn;
	}
	
	public void setGrade(int g) {
		grade = g;
	}
	
	public String toString() {
		return name+","+rollNumber+","+grade;
	}
}
