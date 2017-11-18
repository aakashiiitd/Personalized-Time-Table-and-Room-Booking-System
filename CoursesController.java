package application.courses;

import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import application.Main;
import application.Student;
import javafx.scene.control.TextField;

public class CoursesController {
	
	@FXML
	private TextArea name;
	@FXML
	private TextArea course_code;
	@FXML
	private TextArea instructor;
	@FXML
	private TextArea credits;
	@FXML
	private TextArea pre_req;
	@FXML
	private TextArea post_req;
	/*
	 * This function directs to the Student Page
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	@FXML
	private void goStudent() throws IOException{
		Main.showStudent();
	}
	/*
	 * This function autofills after entering the course name
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	@FXML
	private void submitName() throws IOException{
		ArrayList<String[]> details = new ArrayList<String[]>();
		String line="";
		BufferedReader br = new BufferedReader(new FileReader("courses.csv"));
		while ((line = br.readLine()) != null) {
			String[] s = line.split(",");
			details.add(s);
		}
		
		for(int i=0;i<details.size();i++){
			if(details.get(i)[1].equalsIgnoreCase(name.getText())||details.get(i)[5].equalsIgnoreCase(name.getText())){
				course_code.setText(details.get(i)[2]);
				instructor.setText(details.get(i)[3]);
				credits.setText(details.get(i)[4]);
				pre_req.setText(details.get(i)[13]);
				post_req.setText(details.get(i)[14]);
			}
		}
	}
	/*
	 * This function autofills after entering course code
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	@FXML
	private void submitCode() throws IOException{
		ArrayList<String[]> details = new ArrayList<String[]>();
		String line="";
		BufferedReader br = new BufferedReader(new FileReader("courses.csv"));
		while ((line = br.readLine()) != null) {
			String[] s = line.split(",");
			details.add(s);
		}
		
		for(int i=0;i<details.size();i++){
			if(details.get(i)[2].equalsIgnoreCase(course_code.getText())){
				name.setText(details.get(i)[1]);
				instructor.setText(details.get(i)[3]);
				credits.setText(details.get(i)[4]);
				pre_req.setText(details.get(i)[13]);
				post_req.setText(details.get(i)[14]);
			}
		}
	}
	/*
	 * This function adds the particular course to the student's courses
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, ClassNotFoundException
	 */
	@FXML
	private void addCourse() throws IOException, ClassNotFoundException{
		if(name.equals("") || course_code.equals("") || instructor.equals("") || credits.equals("") || post_req.equals("")){
			JOptionPane.showMessageDialog(null, "One of the field is blank");
		}
		else{
		String code = course_code.getText();
		
		ObjectInputStream infac=new ObjectInputStream(new FileInputStream("current.txt"));  
		Student s=(Student)infac.readObject();
		infac.close();
		
		ObjectInputStream dk=new ObjectInputStream(new FileInputStream("students.txt"));  
		ArrayList<Student> stu=(ArrayList<Student>)dk.readObject();
		dk.close();
		
		int yo=0;
		for(int i=0;i<stu.size();i++){
			for(int j=0;j<stu.get(i).courses.size();j++){
				if(stu.get(i).email.equalsIgnoreCase(s.email)&&stu.get(i).courses.get(j).equalsIgnoreCase(code)){
					yo=1;
					JOptionPane.showMessageDialog(null, "Course Already Added");
				}
			}
		}
		for(int i=0;i<stu.size();i++){
			if(stu.get(i).email.equalsIgnoreCase(s.email)&&yo==0){
					stu.get(i).courses.add(code);
					FileOutputStream fout=new FileOutputStream("students.txt");  
					ObjectOutputStream out=new ObjectOutputStream(fout);  
					out.writeObject(stu);    
					out.close();
					fout.close();
			}
		}
		if(yo==0){
			JOptionPane.showMessageDialog(null, "Course Added");
		}
	}
	}
	
}