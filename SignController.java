package application.sign;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.swing.JOptionPane;

import application.Admin;
import application.Faculty;
import application.Main;
import application.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignController {
	private Main main;
	
	ObservableList<String> typelist=(ObservableList<String>) FXCollections.observableArrayList("Admin","Faculty","Student");
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private DatePicker date;
	@FXML
	private ChoiceBox<String> type;
	@FXML
	private CheckBox robot;
	@FXML
	private CheckBox one;
	@FXML
	private CheckBox two;
	@FXML
	private CheckBox three;
	@FXML
	private CheckBox four;
	@FXML
	private void initialize(){
		type.setValue("Student");
		type.setItems(typelist);
	}
	/*
	 * This disables the the option for choosing group number if the type is Admin or Faculty.
	 * @author Aakash Tanwar and Rahul Patwardhan
	 */
	@FXML
	private void handletype(){
		if(type.getValue().equals("Admin") || type.getValue().equals("Faculty")){
			one.setSelected(false);
			two.setSelected(false);
			three.setSelected(false);
			four.setSelected(false);
		}
	}
	/*
	 * This ensures that if any other checkbox is selected then this one would be set to false.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 */
	@FXML
	private void handleone(){
		if(type.getValue().equals("Admin") || type.getValue().equals("Faculty") || two.isSelected() || three.isSelected() || four.isSelected()){
			one.setSelected(false);
		}
	}
	/*
	 * This ensures that if any other checkbox is selected then this one would be set to false.
	 * @author Aakash Tanwar and Rahul Patwardhan
	 */
	@FXML
	private void handletwo(){
		if(type.getValue().equals("Admin") || type.getValue().equals("Faculty") || one.isSelected() || three.isSelected() || four.isSelected()){
			two.setSelected(false);
		}
	}
	/*
	 * This ensures that if any other checkbox is selected then this one would be set to false.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 */
	@FXML
	private void handlethree(){
		if(type.getValue().equals("Admin") || type.getValue().equals("Faculty") || two.isSelected() || one.isSelected() || four.isSelected()){
			three.setSelected(false);
		}
	}
	/*
	 * This ensures that if any other checkbox is selected then this one would be set to false.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 */
	@FXML
	private void handlefour(){
		if(type.getValue().equals("Admin") || type.getValue().equals("Faculty") || two.isSelected() || three.isSelected() || one.isSelected()){
			four.setSelected(false);
		}
	}
	/* 
	 * At first it checks whether the user has entered all fields required and all of them in a correct manner for signing up.
	 * Then it checks whether the email is already taken or not.
	 * It takes email and password as parameters and stores them in a list of student/faculty/admin depending upon the type selected.
	 * It then stores the email and password in the current.txt file
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @param String email, String password
	 * @exception throws IOException, ClassNotFoundException
	 */
	@FXML
	private void handleActionPerformed() throws IOException, ClassNotFoundException{
		if(email.getText()==null|| date.getValue()==null||type.getValue()==null||robot.isSelected()==false||password.getText()==null){
			JOptionPane.showMessageDialog(null, "One of the fields is empty.");
		}
		else if(type.getValue().equals("Student") && one.isSelected()==false && two.isSelected()==false && three.isSelected()==false && four.isSelected()==false){
			JOptionPane.showMessageDialog(null, "One of the fields is empty.");
		}
		else if((type.getValue().equals("Admin") || type.getValue().equals("Faculty")) && (two.isSelected() || three.isSelected() || one.isSelected() || four.isSelected())){
			JOptionPane.showMessageDialog(null, "You have selected a group");
		}
		else if(password.getText().length()<8){
			JOptionPane.showMessageDialog(null, "Password is too short.");
		}
		else if(email.getText().indexOf("@iiitd.ac.in")==-1){
			JOptionPane.showMessageDialog(null, "Email is not valid.");
		}
		
		else{
			String mail = email.getText();
			String pwd = new String(password.getText());
			//Check if mail, and pwd are already in database
			ObjectInputStream instu=new ObjectInputStream(new FileInputStream("students.txt"));  
			Main.stu=(ArrayList<Student>)instu.readObject();
			instu.close();
			ObjectInputStream infac=new ObjectInputStream(new FileInputStream("faculties.txt"));  
			Main.fac=(ArrayList<Faculty>)infac.readObject();
			infac.close();
			ObjectInputStream inad=new ObjectInputStream(new FileInputStream("admins.txt"));  
			Main.ad=(ArrayList<Admin>)inad.readObject();
			inad.close();
			int jk=0;
			if(type.getValue().equals("Student")){
				for(int i=0;i<Main.stu.size();i++){
					if(Main.stu.get(i).email.equalsIgnoreCase(mail)){
						jk=1;
						JOptionPane.showMessageDialog(null, "Email already exist");
					}
				}
			}
			else if(type.getValue().equals("Faculty")){
				for(int i=0;i<Main.fac.size();i++){
					if(Main.fac.get(i).email.equalsIgnoreCase(mail)){
						jk=1;
						JOptionPane.showMessageDialog(null, "Email already exist");
					}
				}
			}
			else{
				for(int i=0;i<Main.ad.size();i++){
					if(Main.ad.get(i).email.equalsIgnoreCase(mail)){
						jk=1;
						JOptionPane.showMessageDialog(null, "Email already exist");
					}
				}
			}
			if(jk==0){
				if (type.getValue().equals("Student"))
				{
						Student s = new Student(firstname.getText(),lastname.getText(),mail,pwd,type.toString(),new ArrayList<String>());
						Main.stu.add(s);
						FileOutputStream fout=new FileOutputStream("students.txt");  
						ObjectOutputStream out=new ObjectOutputStream(fout);  
						out.writeObject(Main.stu);    
				        out.close();
				        fout.close();
				        FileOutputStream f=new FileOutputStream("current.txt");  
						ObjectOutputStream o=new ObjectOutputStream(f);  
						o.writeObject(s);    
				        o.close();
				        f.close();
						Main.showStudent();
				}
				
				else if (type.getValue().equals("Faculty"))
				{
						Faculty f = new Faculty(firstname.getText(),lastname.getText(),mail,pwd,type.toString());
						Main.fac.add(f);
						FileOutputStream fout=new FileOutputStream("faculties.txt");  
						ObjectOutputStream out=new ObjectOutputStream(fout);  
						out.writeObject(Main.fac);    
				        out.close();
				        fout.close();
				        FileOutputStream fi=new FileOutputStream("current.txt");  
						ObjectOutputStream o=new ObjectOutputStream(fi);  
						o.writeObject(f);    
				        o.close();
				        fi.close();
						Main.showFaculty();
				}
				
				else if(type.getValue().equals("Admin"))
				{
						Admin a = new Admin(firstname.getText(),lastname.getText(),mail,pwd,type.toString());
						Main.ad.add(a);
						FileOutputStream fout=new FileOutputStream("admins.txt");  
						ObjectOutputStream out=new ObjectOutputStream(fout);  
						out.writeObject(Main.ad);    
				        out.close();
				        fout.close();
				        FileOutputStream f=new FileOutputStream("current.txt");  
						ObjectOutputStream o=new ObjectOutputStream(f);  
						o.writeObject(a);    
				        o.close();
				        f.close();
						Main.showAdmin();
				}
			}
		}
		
	}
	// This function calls the function from the Main.java file. This is used to go to Login Page.
	// @author Aakash Tanwar and Rahul Patwardhan
	// @exception throws IOException
	@FXML
	private void goLogIn() throws IOException{
		Main.showLogin();
	}
	
}