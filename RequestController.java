package application.requestroom;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import application.Main;
import application.Student;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RequestController {
	@FXML
	private TextField purpose;
	@FXML
	private TextField stime;
	@FXML
	private TextField etime;
	@FXML
	private TextField capacity;
	@FXML
	private TextField day;
	@FXML
	private DatePicker date;
	@FXML
	private TextField course;
	
	/*
	 * This function directs to the LogIn Page
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	@FXML
	private void goLogIn() throws IOException{
		Main.showStudent();
	}
	/*
	 * This function makes request for the room at a particular time and date
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, ClassNotFoundException
	 */
	@FXML
	private void RequestRoom() throws IOException, ClassNotFoundException{
		ArrayList<String[]> hello = new ArrayList<String[]>();
		String[] request = new String[9];
		
		String status = "processing";
		
		BufferedReader br = new BufferedReader(new FileReader("request.csv"));
		String line="";
		while ((line = br.readLine()) != null) {
			String[] s = line.split(",");
			hello.add(s);
		}
		
		ObjectInputStream infac=new ObjectInputStream(new FileInputStream("current.txt"));  
		Student f=(Student)infac.readObject();
		infac.close();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, 5); // Adding 5 days
		String output = sdf.format(c.getTime());
		
		request[0] = status; 
		request[1] = f.email;
		request[2] = stime.getText();
		request[3] = etime.getText();
		request[4] = capacity.getText();
		request[5] = date.getValue().toString();
		request[6] = day.getText();
		request[7] = purpose.getText();
		request[8] = output;
		
		hello.add(request);
		
		
		FileWriter writer = new FileWriter("request.csv");
		for(int gg=0;gg<hello.size();gg++){
			for (int yolo = 0; yolo < hello.get(gg).length; yolo++) {
			    writer.append(hello.get(gg)[yolo]);
			    writer.append(",");
			}
			writer.append("\n");
		}
		writer.close();
		
		JOptionPane.showMessageDialog(null, "Request Sent Successfully!");
		
		Main.showStudent();
	}
	
	
}