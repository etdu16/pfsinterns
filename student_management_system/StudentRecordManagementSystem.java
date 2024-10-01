package student_management;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StudentRecordManagementSystem {

	private static final String file_data = "students.txt";
	private static List<Student> studentList = new ArrayList<>();
	private static FileWriter fw;
	private static File file;

	private static void saveStudent(String name, int rollNum, int grade) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
			Student s = new Student(name, rollNum, grade);
			studentList.add(s);
			pw.println(s.toString());
			pw.flush();
		} catch (Exception e) {
			System.out.println("Error saving to file.");
		}
	}

	private static void getStudent(int rn) {
		try {
			int index = -1;
			for (int i = 0; i < studentList.size(); i++) {
				if (studentList.get(i).getRollNumber() == rn) {
					index = i;
					break;
				}
			}
			System.out.println(studentList.get(index).toString());
		} catch (Exception e) {
			System.out.println("Student not found.");
		}
	}

	private static void deleteStudent(int rn) {
		boolean found = false;
		for (int i = 0; i < studentList.size(); i++) {
			if (studentList.get(i).getRollNumber() == rn) {
				studentList.remove(i);
				found = true;
				break;
			}
		}
		if (!found) {
			System.out.println("Student not found.");
		} else {
			String data = "";
			for (int i = 0; i < studentList.size(); i++) {
				data += studentList.get(i).toString() + "\n";
			}
			try {
				fw.close();
			    Path filePath = Paths.get(file_data);
				Files.delete(filePath);
				
				file = new File(file_data);
				fw = new FileWriter(file_data, true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(data);
				pw.close();
			} catch (Exception e) {
				System.out.println("An error occured."+" ");
				e.printStackTrace();
			}
		}

	}

	private static void getCurrentRecords() {
		if (studentList.size() == 0) {
			System.out.println("No students in record.");
		} else {
			for (Student s : studentList) {
				System.out.println(s.toString());
			}
		}
	}

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		loadData();
		try {
			fw = new FileWriter(file_data, true);
		} catch (Exception e) {
			pw.println("Unexpected error.");
		}
		boolean terminate = false;
		pw.println("--------------------------------");

		while (!terminate) {
			pw.println("STUDENT RECORD MANAGEMENT SYSTEM");
			pw.println("--------------------------------");
			pw.println("1 - Add a student [name, roll number, grade]");
			pw.println("2 - Get student info [roll number]");
			pw.println("3 - Delete a student [roll number]");
			pw.println("4 - Retrieve all records");
			pw.println("5 - Terminate application");
			pw.flush();
			try {
				pw.print("Operation: ");
				pw.flush();
				int option = Integer.parseInt(br.readLine());
				pw.println("--------------------------------");
				pw.flush();
				if (option < 1 || option > 5) {
					throw new Exception();
				}
				switch (option) {
				case 1:

					addStudentMenu(pw, br);
					break;
				case 2:
					getStudentMenu(pw, br);
					break;
				case 3:
					deleteStudentMenu(pw, br);
					break;

				case 4:
					currentRecordsMenu(pw, br);
					break;
				default:
					terminate = true;
				}
				if (!terminate) {
					pw.println("--------------------------------");
					pw.flush();
				}
			} catch (Exception e) {
				pw.println("Input format incorrect.");
				pw.flush();
			}
			if (terminate) {
				pw.println("Program terminated.");
				pw.flush();
				pw.close();
			}
		}

	}

	public static void addStudentMenu(PrintWriter pw, BufferedReader br) {
		try {
			pw.print("Name: ");
			pw.flush();
			String name = br.readLine();
			if(name.length()==0) {
				throw new Exception();
			}
			pw.print("Roll Number: ");
			pw.flush();
			int rn = Integer.parseInt(br.readLine());
			pw.print("Grade: ");
			pw.flush();
			int grade = Integer.parseInt(br.readLine());
			saveStudent(name, rn, grade);
			pw.println("Successfully saved.");
			pw.flush();
		} catch (Exception e) {
			pw.println("Invalid input.");
			pw.flush();
		}
	}

	public static void getStudentMenu(PrintWriter pw, BufferedReader br) {
		try {
			pw.print("Roll Number: ");
			pw.flush();
			int rn = Integer.parseInt(br.readLine());
			getStudent(rn);
		} catch (Exception e) {
			// pw.println("An error occured.");
		}
	}

	public static void deleteStudentMenu(PrintWriter pw, BufferedReader br) {
		try {
			pw.print("Roll Number: ");
			pw.flush();
			int rn = Integer.parseInt(br.readLine());
			deleteStudent(rn);
			pw.println("Deleted successfully.");
		} catch (Exception e) {
			// pw.println("An error occured.");
		}
	}

	public static void currentRecordsMenu(PrintWriter pw, BufferedReader br) {
		try {
			getCurrentRecords();
		} catch (Exception e) {
			// pw.println("An error occured.");
		}
	}

	public static void loadData() {
		try (BufferedReader br = new BufferedReader(new FileReader(file_data))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] info = line.split(",");
				studentList.add(new Student(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2])));
			}
			file = new File("students.txt");
		} catch (Exception e) {
			createFile();
		}
	}

	public static void createFile() {
		file = new File("students.txt");
		try {
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
		}
	}

}
