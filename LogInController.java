package application.log;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import application.Main;
import application.Student;
import application.Admin;
import application.Faculty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
public class LogInController {
	private Main main;
	
	ObservableList<String> typelist=(ObservableList<String>) FXCollections.observableArrayList("Admin","Faculty","Student");
	@FXML
	private TextField email;
	@FXML
	private PasswordField password;
	@FXML
	private ChoiceBox<String> type;
	@FXML
	private CheckBox logged;
	@FXML
	private void initialize(){
		type.setValue("Student");
		type.setItems(typelist);
		
	}
	// This function calls the function from the Main.java file. This is used to go to SignUp Page.
	//@author Aakash Tanwar and Rahul Patwardhan
	// @exception throws IOException
	@FXML
	private void goSignUp() throws IOException{
		Main.showSignUp();
	}
	/*
	 * This function is for Logging In. It check from the list of users from the one of the 3 .txt files depending upon the the type.
	 * Then it saves the email and password into current.txt 
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @param String email, String password
	 * @exception throws IOException, ClassNotFoundException
	 */
	@FXML
	private void handleActionPerformed() throws IOException, ClassNotFoundException{
		String mail=email.getText();
		String pass=password.getText();
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
				if(Main.stu.get(i).email.equalsIgnoreCase(mail) && Main.stu.get(i).password.equals(pass)){
					jk=1;
					FileOutputStream f=new FileOutputStream("current.txt");  
					ObjectOutputStream o=new ObjectOutputStream(f);  
					o.writeObject(Main.stu.get(i));    
				    o.close();
				    f.close();
				    if(logged.isSelected()){
				    	ArrayList<String[]> hello=new ArrayList<String[]>();
				    	String[] request = new String[3];
				    	BufferedReader br = new BufferedReader(new FileReader("logged.csv"));
						String line="";
						while ((line = br.readLine()) != null) {
							String[] s = line.split(",");
							hello.add(s);
						}
						request[0]=Main.stu.get(i).email;
						request[1]="1";
						request[2]="Student";
						if(!hello.contains(request)){	
							hello.add(request);
						}
						FileWriter writer = new FileWriter("logged.csv");
						for(int gg=0;gg<hello.size();gg++){
							for (int yolo = 0; yolo < hello.get(gg).length; yolo++) {
							    if(!hello.get(gg)[0].equalsIgnoreCase(Main.stu.get(i).email)){
									hello.get(gg)[1]="0";
							    	writer.append(hello.get(gg)[yolo]);
								    writer.append(",");
							    }
							    else{
							    	writer.append(hello.get(gg)[yolo]);
								    writer.append(",");
							    }
							}
							writer.append("\n");
						}
						writer.close();
				    }
				    Main.showStudent();
				}
			}
		}
		else if(type.getValue().equals("Faculty")){
			for(int i=0;i<Main.fac.size();i++){
				if(Main.fac.get(i).email.equalsIgnoreCase(mail) && Main.fac.get(i).password.equals(pass)){
					jk=1;
					FileOutputStream f=new FileOutputStream("current.txt");  
					ObjectOutputStream o=new ObjectOutputStream(f);  
					o.writeObject(Main.fac.get(i));    
				    o.close();
				    f.close();
				    if(logged.isSelected()){
				    	ArrayList<String[]> hello=new ArrayList<String[]>();
				    	String[] request = new String[3];
				    	BufferedReader br = new BufferedReader(new FileReader("logged.csv"));
						String line="";
						while ((line = br.readLine()) != null) {
							String[] s = line.split(",");
							hello.add(s);
						}
						request[0]=Main.fac.get(i).email;
						request[1]="1";
						request[2]="Faculty";
						if(!hello.contains(request)){	
							hello.add(request);
						}
						FileWriter writer = new FileWriter("logged.csv");
						for(int gg=0;gg<hello.size();gg++){
							for (int yolo = 0; yolo < hello.get(gg).length; yolo++) {
							    if(!hello.get(gg)[0].equalsIgnoreCase(Main.fac.get(i).email)){
									hello.get(gg)[1]="0";
							    	writer.append(hello.get(gg)[yolo]);
								    writer.append(",");
							    }
							    else{
							    	writer.append(hello.get(gg)[yolo]);
								    writer.append(",");
							    }
							}
							writer.append("\n");
						}
						writer.close();
				    }
				    Main.showFaculty();
				}
			}
		}
		else if(type.getValue().equals("Admin")){
			for(int i=0;i<Main.ad.size();i++){
				if(Main.ad.get(i).email.equalsIgnoreCase(mail) && Main.ad.get(i).password.equals(pass)){
					jk=1;
					FileOutputStream f=new FileOutputStream("current.txt");  
					ObjectOutputStream o=new ObjectOutputStream(f);  
					o.writeObject(Main.ad.get(i));    
				    o.close();
				    f.close();
				    if(logged.isSelected()){
				    	ArrayList<String[]> hello=new ArrayList<String[]>();
				    	String[] request = new String[3];
				    	BufferedReader br = new BufferedReader(new FileReader("logged.csv"));
						String line="";
						while ((line = br.readLine()) != null) {
							String[] s = line.split(",");
							hello.add(s);
						}
						request[0]=Main.ad.get(i).email;
						request[1]="1";
						request[2]="Admin";
						if(!hello.contains(request)){	
							hello.add(request);
						}
						FileWriter writer = new FileWriter("logged.csv");
						for(int gg=0;gg<hello.size();gg++){
							for (int yolo = 0; yolo < hello.get(gg).length; yolo++) {
							    if(!hello.get(gg)[0].equalsIgnoreCase(Main.ad.get(i).email)){
									hello.get(gg)[1]="0";
							    	writer.append(hello.get(gg)[yolo]);
								    writer.append(",");
							    }
							    else{
							    	writer.append(hello.get(gg)[yolo]);
								    writer.append(",");
							    }
							}
							writer.append("\n");
						}
						writer.close();
				    }
				    Main.showAdmin();
				}
			}
		}
		if(jk==0){
			JOptionPane.showMessageDialog(null, "Email or Password is wrong");
		}
	}
}
