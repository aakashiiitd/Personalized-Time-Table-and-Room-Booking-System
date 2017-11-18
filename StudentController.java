package application.student;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import application.Admin;
import application.Main;
import application.Student;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class StudentController {
	
	private Main main;
	@FXML
	private TextField room;
	@FXML
	private TextField day;
	@FXML
	private TextField time_from;
	@FXML
	private TextField time_to;
	@FXML
	private TextArea welcome;
	@FXML
	private TextArea show;
	@FXML
	private TextField search;
	
	@FXML
	private void goLogIn() throws IOException, ClassNotFoundException{
		ObjectInputStream infac=new ObjectInputStream(new FileInputStream("current.txt"));  
		Student f=(Student)infac.readObject();
		infac.close();
		ArrayList<String[]> hello=new ArrayList<String[]>();
    	
    	BufferedReader br = new BufferedReader(new FileReader("logged.csv"));
		String line="";
		while ((line = br.readLine()) != null) {
			String[] s = line.split(",");
			hello.add(s);
		}
		br.close();
		FileWriter writer = new FileWriter("logged.csv");
		for(int gg=0;gg<hello.size();gg++){
			for (int yolo = 0; yolo < hello.get(gg).length; yolo++) {
			    if(hello.get(gg)[0].equalsIgnoreCase(f.email)){
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
		Main.showLogin();
	}
	
	@FXML
	private void goRequest() throws IOException{
		Main.showRequestRoom();
	}
	
	@FXML
	private void goCourses() throws IOException, ClassNotFoundException{
		Main.showCourses();
	}
	/*
	 * This function dictates what programs to carry out when the GUI is just opened.
	 * This function first gives a welcome message to the user at the top by reading his/her name from current.txt
	 * It also cancels any request which has taken more than 5 days to get approved.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, ClassNotFoundException, FileNotFoundException, ParseException
	 */
	@FXML
	public void initialize() throws IOException, ClassNotFoundException, FileNotFoundException, ParseException{
		ObjectInputStream infac=new ObjectInputStream(new FileInputStream("current.txt"));  
		Student f=(Student)infac.readObject();
		infac.close();
		
		welcome.setText("Welcome "+f.firstname+" "+f.lastname);
		ArrayList<String[]> req = new ArrayList<String[]>();
		String line="";
		BufferedReader br = new BufferedReader(new FileReader("request.csv"));
		while ((line = br.readLine()) != null) {
			String[] s = line.split(",");
			req.add(s);
		}
		for(int i=0;i<req.size();i++){
				Date dateobj = new Date();
				String sDate1=req.get(i)[8];
				Date date1=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(sDate1);
				if(dateobj.after(date1) && req.get(i)[0].equalsIgnoreCase("processing")){
					req.get(i)[0]="Cancelled";
				}
		}
		
		FileWriter writer = new FileWriter("request.csv");
		for(int gg=0;gg<req.size();gg++){
			for (int yolo = 0; yolo < req.get(gg).length; yolo++) {
			    writer.append(req.get(gg)[yolo]);
			    writer.append(",");
			}
			writer.append("\n");
		}
		writer.close();
	}
	/*
	 * This function clears all the fields.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	@FXML
	private void clearFields() throws IOException{
		room.setText("");
		day.setText("");
		time_from.setText("");
		time_to.setText("");
	}
	/*
	 * It checks whether the room is available at the particular day and time.
	 * This function first stores all the data from the fields in variables.
	 * It then reads the room.csv file and checks the time and day from the file.
	 * Then a message dialog box appears saying whether the room is avaialble or not.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, FileNotFoundException
	 */
	@FXML
	private void checkAvailability() throws IOException, FileNotFoundException{
		String roomnum = room.getText();
		String dayname = day.getText();
		String ftime = time_from.getText();
		String ttime = time_to.getText();
		String trim_ftime="";
		String trim_ttime="";
		for(int i=0;i<ftime.length();i++){
			
			if(ftime.charAt(i)==':'){
				break;
			}
			trim_ftime=trim_ftime+ftime.charAt(i);
		}
		for(int i=0;i<ttime.length();i++){
			
			if(ttime.charAt(i)==':'){
				break;
			}
			trim_ttime=trim_ttime+ttime.charAt(i);
		}
		
		if(dayname.equals("") || roomnum.equals("") || ftime.equals("") || ttime.equals("")){
			JOptionPane.showMessageDialog(null, "One of the field is blank");
		}
		else{
			String line="";
			String[] list = new String[23];
			
			
			int yo=0;
			int j;
			int k;
			BufferedReader br = new BufferedReader(new FileReader("room.csv"));
			while ((line = br.readLine()) != null) {
				int jk=0;
	            list = line.split(",");
	            if(list[0].equalsIgnoreCase(roomnum)){
	            	if(list[1].equalsIgnoreCase(dayname)){
	            		
	            		if(ftime.indexOf("30")==-1){
	            			if(Integer.parseInt(trim_ftime)>=8){
	            				j=Integer.parseInt(trim_ftime)-6+(Integer.parseInt(trim_ftime)-8);
	            			}
	            			else{
	            				j=Integer.parseInt(trim_ftime)+12-6+(Integer.parseInt(trim_ftime)+12-8);
	            			}
	            			if(list[j].equals("0")){
	            				if(ttime.indexOf("30")==-1){
	            					if(Integer.parseInt(trim_ttime)>=8){
	            						k=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
	            					}
	            					else{
	            						k=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
	            					}
	            					
	            					for(int l=j;l<=k;l++){
	            						if(list[l].equals("1111")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						JOptionPane.showMessageDialog(null, "Room is available");
	            						yo=1;
	            					}
	            					else{
	            						JOptionPane.showMessageDialog(null, "Room not available");
	            						yo=1;
	            					}
	            				}
	            				else{
	            					if(Integer.parseInt(trim_ttime)>=8){
	            						k=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9)+1;
	            					}
	            					else{
	            						k=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9)+1;
	            					}
	            					for(int l=j;l<=k;l++){
	            						if(list[l].equals("1111")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						JOptionPane.showMessageDialog(null, "Room is available");
	            						yo=1;
	            					}
	            					else{
	            						JOptionPane.showMessageDialog(null, "Room not available");
	            						yo=1;
	            					}
	            				}
	            			}
	            			else{
	            				JOptionPane.showMessageDialog(null, "Room not available");
	            				yo=1;
	            			}
	            		}
	            		else{
	            			if(Integer.parseInt(trim_ftime)>=8){
	            				j=Integer.parseInt(trim_ftime)-6+(Integer.parseInt(trim_ftime)-8)+1;
	            			}
	            			else{
	            				j=Integer.parseInt(trim_ftime)+12-6+(Integer.parseInt(trim_ftime)+12-8)+1;
	            			}
	            			if(list[j].equals("0")){
	            				if(ttime.indexOf("30")==-1){
	            					if(Integer.parseInt(trim_ttime)>=8){
	            						k=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
	            					}
	            					else{
	            						k=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
	            					}
	            					
	            					for(int l=j;l<=k;l++){
	            						if(list[l].equals("1111")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						JOptionPane.showMessageDialog(null, "Room is available");
	            						yo=1;
	            					}
	            					else{
	            						JOptionPane.showMessageDialog(null, "Room not available");
	            						yo=1;
	            					}
	            				}
	            				else{
	            					if(Integer.parseInt(trim_ttime)>=8){
	            						k=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9)+1;
	            					}
	            					else{
	            						k=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9)+1;
	            					}
	            					for(int l=j;l<=k;l++){
	            						if(list[l].equals("1111")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						JOptionPane.showMessageDialog(null, "Room is available");
	            						yo=1;
	            					}
	            					else{
	            						JOptionPane.showMessageDialog(null, "Room not available");
	            						yo=1;
	            					}
	            				}
	            			}
	            			else{
	            				JOptionPane.showMessageDialog(null, "Room not available");
	            				yo=1;
	            			}
	            		}
	            	}
	            }
	            
			}
			br.close();
			if(yo==0){
				JOptionPane.showMessageDialog(null, "Room not available");
			}
		}
	}
	/*
	 * It shows the rooms available for a particular day and time. It also gives the capacity of the room.
	 * This function first stores all the data from the fields in variables.
	 * It then reads the room.csv file and checks the time and day from the file.
	 * Then it displays the rooms available in the textarea.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, FileNotFoundException
	 */
	@FXML
	private void ViewRooms() throws IOException, FileNotFoundException{
		show.setText("");
		String dayname = day.getText();
		String ftime = time_from.getText();
		String ttime = time_to.getText();
		String trim_ftime="";
		String trim_ttime="";
		for(int i=0;i<ftime.length();i++){
			
			if(ftime.charAt(i)==':'){
				break;
			}
			trim_ftime=trim_ftime+ftime.charAt(i);
		}
		for(int i=0;i<ttime.length();i++){
			
			if(ttime.charAt(i)==':'){
				break;
			}
			trim_ttime=trim_ttime+ttime.charAt(i);
		}
		
		if(dayname.equals("") || ftime.equals("") || ttime.equals("")){
			JOptionPane.showMessageDialog(null, "One of the field is blank");
		}
		else{
			String line="";
			String[] list = new String[23];
			
			
			int yo=0;
			int j;
			int k;
			BufferedReader br = new BufferedReader(new FileReader("room.csv"));
			while ((line = br.readLine()) != null) {
				int jk=0;
	            list = line.split(",");

	            	if(list[1].equalsIgnoreCase(dayname)){
	            		
	            		if(ftime.indexOf("30")==-1){
	            			if(Integer.parseInt(trim_ftime)>7){
	            				j=Integer.parseInt(trim_ftime)-6+(Integer.parseInt(trim_ftime)-8);
	            			}
	            			else{
	            				j=Integer.parseInt(trim_ftime)+12-6+(Integer.parseInt(trim_ftime)+12-8);
	            			}
	            			if(list[j].equals("0")){
	            				if(ttime.indexOf("30")==-1){
	            					if(Integer.parseInt(trim_ttime)>7){
	            						k=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
	            					}
	            					else{
	            						k=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
	            					}
	            					
	            					for(int l=j;l<=k;l++){
	            						if(list[l].equals("1111")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						show.appendText(list[0] + " Capacity" +list[22]);
	            						show.appendText("\n");
	            						yo=1;
	            					}
	            				}
	            				else{
	            					if(Integer.parseInt(trim_ttime)>7){
	            						k=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9)+1;
	            					}
	            					else{
	            						k=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9)+1;
	            					}
	            					for(int l=j;l<=k;l++){
	            						if(list[l].equals("1111")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						show.appendText(list[0] + " Capacity" +list[22]);
	            						show.appendText("\n");
	            						yo=1;
	            					}
	            				}
	            			}
	            		}
	            		else{
	            			if(Integer.parseInt(trim_ftime)>7){
	            				j=Integer.parseInt(trim_ftime)-6+(Integer.parseInt(trim_ftime)-8)+1;
	            			}
	            			else{
	            				j=Integer.parseInt(trim_ftime)+12-6+(Integer.parseInt(trim_ftime)+12-8)+1;
	            			}
	            			if(list[j].equals("0")){
	            				if(ttime.indexOf("30")==-1){
	            					if(Integer.parseInt(trim_ttime)>7){
	            						k=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
	            					}
	            					else{
	            						k=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
	            					}
	            					
	            					for(int l=j;l<=k;l++){
	            						if(list[l].equals("1111")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						show.appendText(list[0] + " Capacity" +list[22]);
	            						show.appendText("\n");
	            						yo=1;
	            					}
	            				}
	            				else{
	            					if(Integer.parseInt(trim_ttime)>7){
	            						k=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9)+1;
	            					}
	            					else{
	            						k=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9)+1;
	            					}
	            					for(int l=j;l<=k;l++){
	            						if(list[l].equals("1111")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						show.appendText(list[0] + " Capacity" +list[22]);
	            						show.appendText("\n");
	            						yo=1;
	            					}
	            				}
	            			}
	            		}
	            	}
			}	
			if(yo==0){
				JOptionPane.showMessageDialog(null, "No Room available");
			}
		}
	}
	/*
	 * It displays all the requests made by the students and also their status on the textarea.
	 * It reads the request.csv file and checks for requests made by the user by using his/her email
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, ClassNotFoundException
	 */
	@FXML
	private void viewRequests() throws IOException, ClassNotFoundException{
		show.clear();
		ArrayList<String[]> req = new ArrayList<String[]>();
		String line="";
		BufferedReader br = new BufferedReader(new FileReader("request.csv"));
		while ((line = br.readLine()) != null) {
			String[] s = line.split(",");
			req.add(s);
		}
		
		ObjectInputStream infac=new ObjectInputStream(new FileInputStream("current.txt"));  
		Student f=(Student)infac.readObject();
		infac.close();
		
		int yo=1;
		for(int i=0; i<req.size();i++){
			for(int j=0;j<req.get(i).length;j++){
				if(req.get(i)[0].equalsIgnoreCase(f.email)){
					show.appendText(req.get(i)[j]+" ");
					yo=0;
				}
			}
			show.appendText("\n");
		}
		if(yo==1){
			JOptionPane.showMessageDialog(null, "NO REQUESTS");
		}
	}
	/*
	 * This function shows the courses selected by the student for himself/herself in textarea.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, ClassNotFoundException
	 */
	@FXML
	private void ShowCourses() throws IOException, ClassNotFoundException{
		show.clear();
		String sea=search.getText();
		ArrayList<String[]> req = new ArrayList<String[]>();
		String line="";
		BufferedReader br = new BufferedReader(new FileReader("courses.csv"));
		int yo=0;
		while ((line = br.readLine()) != null) {
			String[] s = line.split(",");
			req.add(s);
		}
		for(int i=0; i<req.size();i++){
				if(req.get(i)[14].toLowerCase().contains(sea.toLowerCase()) || req.get(i)[2].toLowerCase().contains(sea.toLowerCase()) || req.get(i)[1].toLowerCase().contains(sea.toLowerCase())){
					show.appendText(req.get(i)[1]+" "+req.get(i)[2]+" ");
					show.appendText("\n");
					yo=1;
				}
		}
		if(yo==0){
			show.appendText("NO COURSE");
		}
	}
	/*
	 * This function opens up the timetable which has been made for the student according to his selections.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, ClassNotFoundException
	 */
	@FXML
	private void showtimetable() throws IOException, ClassNotFoundException{
		String[] columnNames = {"DAYS","8:00-8:30", "8:30-9:00","9:00-9:30","9:30-10:00","10:00-10:30","10:30-11:00",
				"11:00-11:30","11:30-12:00","12:00-12:30","12:30-1:00","1:00-1:30","1:30-2:00","2:00-2:30",
				"2:30-3:00","3:00-3:30","3:30-4:00","4:00-4:30","4:30-5:00",
				"5:00-5:30","5:30-6:00"};
		
		ObjectInputStream infac=new ObjectInputStream(new FileInputStream("current.txt"));  
		Student f=(Student)infac.readObject();
		infac.close();
		
		ArrayList<String[]> req = new ArrayList<String[]>();
		String line="";
		BufferedReader br = new BufferedReader(new FileReader("courses.csv"));
		while ((line = br.readLine()) != null) {
			String[] s = line.split(",");
			req.add(s);
		}
		br.close();
		Object[][] data = new Object[5][21];
		data[0][0]="Monday";
		data[1][0]="Tuesday";
		data[2][0]="Wednesday";
		data[3][0]="Thursday";
		data[4][0]="Friday";
		
		int yolo=1;
		char d1;
		char d2;
		for(int j=0;j<req.size();j++){
			if(f.courses.contains(req.get(j)[2].toUpperCase()) || f.courses.contains(req.get(j)[2].toLowerCase())){
			
					if(!req.get(j)[11].equals("0")){
					String s=req.get(j)[11];
					
					d1=s.charAt(1);
					
					String ftime="";
					for(int l=3;l<s.length();l++){
						if(s.charAt(l)=='-'){
							break;
						}
						ftime=ftime+s.charAt(l);
					}
					
					String ttime="";
					int gg=s.indexOf('-');
					for(int l=gg+1;l<s.length();l++){
						if(s.charAt(l)=='@'){
							break;
						}
						ttime=ttime+s.charAt(l);
					}
					
					String ss="";
					for(int klol=0;klol<s.length();klol++){
						if(klol==gg){
							ss=ss+'*';
						}
						else{
							ss=ss+s.charAt(klol);
						}
					}
					s=ss;
					gg=s.indexOf('@');
					ss="";
					for(int klol=0;klol<s.length();klol++){
						if(klol==gg){
							ss=ss+'*';
						}
						else{
							ss=ss+s.charAt(klol);
						}
					}
					s=ss;
					
					String room="";
					int l;
					for(l=gg+1;l<s.length();l++){
						if(s.charAt(l)=='#'){
							yolo=1;
							break;
						}
						room=room+s.charAt(l);
						yolo=0;
					}
					int lol=l;
					String trim_ftime="";
					String trim_ttime="";
					for( l=0;l<ftime.length();l++){
						
						if(ftime.charAt(l)==':'){
							break;
						}
						trim_ftime=trim_ftime+ftime.charAt(l);
					}
					for( l=0;l<ttime.length();l++){
						
						if(ttime.charAt(l)==':'){
							break;
						}
						trim_ttime=trim_ttime+ttime.charAt(l);
					}
					
					int yo=0;
					int yoo=0;
					if(ftime.indexOf("30")==-1){
            			if(Integer.parseInt(trim_ftime)>7){
            				yo=Integer.parseInt(trim_ftime)-6+(Integer.parseInt(trim_ftime)-8)-1;
            			}
            			else{
            				yo=Integer.parseInt(trim_ftime)+12-6+(Integer.parseInt(trim_ftime)+12-8)-1;
            			}
            			
            				if(ttime.indexOf("30")==-1){
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9)-1;
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9)-1;
            					}
            					for(int p=yo;p<=yoo;p++){
        							data[Character.getNumericValue(d1)][p]="TUT "+req.get(j)[5]+ " " + room;
        						}
            				}
            				else{
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
            					}
            					for(int p=yo;p<=yoo;p++){
        							data[Character.getNumericValue(d1)][p]="TUT "+req.get(j)[5]+ " " + room;
        						}
            				}
            			
            		}
            		else{
            			if(Integer.parseInt(trim_ftime)>7){
            				yo=Integer.parseInt(trim_ftime)-6+(Integer.parseInt(trim_ftime)-8);
            			}
            			else{
            				yo=Integer.parseInt(trim_ftime)+12-6+(Integer.parseInt(trim_ftime)+12-8);
            			}
            			
            				if(ttime.indexOf("30")==-1){
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9)-1;
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9)-1;
            					}
            					
            					for(int p=yo;p<=yoo;p++){
        							data[Character.getNumericValue(d1)][p]="TUT "+req.get(j)[5]+ " " + room;
        						}
            				}
            				else{
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
            					}
            					for(int p=yo;p<=yoo;p++){
        							data[Character.getNumericValue(d1)][p]="TUT "+req.get(j)[5]+ " " + room;
        						}
            				}
            			
            		}
					if(yolo==1){
						String dftime="";
						d2=s.charAt(lol+1);
						for(int ll=lol+3;ll<s.length();ll++){
							if(s.charAt(ll)=='-'){
								break;
							}
							dftime=dftime+s.charAt(ll);
						}
						String dttime="";
						int dgg=s.indexOf('-');
						for(int ll=dgg+1;ll<s.length();ll++){
							if(s.charAt(ll)=='@'){
								break;
							}
							dttime=dttime+s.charAt(ll);
						}

						dgg=s.indexOf('@');
						String droom="";
						int ll;
						for(ll=dgg+1;ll<s.length();ll++){
							if(s.charAt(ll)=='#'){
								yolo=1;
								break;
							}
							droom=droom+s.charAt(ll);
							yolo=0;
						}
						String dtrim_ftime="";
						String dtrim_ttime="";
						for( ll=0;ll<dftime.length();ll++){
							
							if(dftime.charAt(ll)==':'){
								break;
							}
							dtrim_ftime=dtrim_ftime+dftime.charAt(ll);
						}
						for( ll=0;ll<dttime.length();ll++){
							
							if(dttime.charAt(ll)==':'){
								break;
							}
							dtrim_ttime=dtrim_ttime+dttime.charAt(ll);
						}
						yo=0;
						yoo=0;
						if(dftime.indexOf("30")==-1){
	            			if(Integer.parseInt(dtrim_ftime)>7){
	            				yo=Integer.parseInt(dtrim_ftime)-6+(Integer.parseInt(dtrim_ftime)-8)-1;
	            			}
	            			else{
	            				yo=Integer.parseInt(dtrim_ftime)+12-6+(Integer.parseInt(dtrim_ftime)+12-8)-1;
	            			}
	            			
	            				if(dttime.indexOf("30")==-1){
	            					if(Integer.parseInt(dtrim_ttime)>7){
	            						yoo=Integer.parseInt(dtrim_ttime)-6+(Integer.parseInt(dtrim_ttime)-9)-1;
	            					}
	            					else{
	            						yoo=Integer.parseInt(dtrim_ttime)+12-6+(Integer.parseInt(dtrim_ttime)+12-9)-1;
	            					}
	            					for(int p=yo;p<=yoo;p++){
	        							data[Character.getNumericValue(d2)][p]="TUT "+req.get(j)[5]+ " " + room;
	        						}
	            				}
	            				else{
	            					if(Integer.parseInt(dtrim_ttime)>7){
	            						yoo=Integer.parseInt(dtrim_ttime)-6+(Integer.parseInt(dtrim_ttime)-9);
	            					}
	            					else{
	            						yoo=Integer.parseInt(dtrim_ttime)+12-6+(Integer.parseInt(dtrim_ttime)+12-9);
	            					}
	            					for(int p=yo;p<=yoo;p++){
	        							data[Character.getNumericValue(d2)][p]="TUT "+req.get(j)[5]+ " " + room;
	        						}
	            				}
	            			
	            		}
	            		else{
	            			if(Integer.parseInt(dtrim_ftime)>7){
	            				yo=Integer.parseInt(dtrim_ftime)-6+(Integer.parseInt(dtrim_ftime)-8);
	            			}
	            			else{
	            				yo=Integer.parseInt(dtrim_ftime)+12-6+(Integer.parseInt(dtrim_ftime)+12-8);
	            			}
	            			
	            				if(dttime.indexOf("30")==-1){
	            					if(Integer.parseInt(dtrim_ttime)>7){
	            						yoo=Integer.parseInt(dtrim_ttime)-6+(Integer.parseInt(dtrim_ttime)-9)-1;
	            					}
	            					else{
	            						yoo=Integer.parseInt(dtrim_ttime)+12-6+(Integer.parseInt(dtrim_ttime)+12-9)-1;
	            					}
	            					
	            					for(int p=yo;p<=yoo;p++){
	        							data[Character.getNumericValue(d2)][p]="TUT "+req.get(j)[5]+ " " + room;
	        						}
	            				}
	            				else{
	            					if(Integer.parseInt(dtrim_ttime)>7){
	            						yoo=Integer.parseInt(dtrim_ttime)-6+(Integer.parseInt(dtrim_ttime)-9);
	            					}
	            					else{
	            						yoo=Integer.parseInt(dtrim_ttime)+12-6+(Integer.parseInt(dtrim_ttime)+12-9);
	            					}
	            					for(int p=yo;p<=yoo;p++){
	        							data[Character.getNumericValue(d2)][p]="TUT "+req.get(j)[5]+ " " + room;
	        						}
	            				}
	            			
	            		}
					}
					
				}
			}
		}
		
		for(int j=0;j<req.size();j++){
			if(f.courses.contains(req.get(j)[2].toUpperCase()) || f.courses.contains(req.get(j)[2].toLowerCase())){
				for(int k=6;k<=10;k++){
					if(!req.get(j)[k].equals("0")){
					String s=req.get(j)[k];
					String ftime="";
					for(int l=0;l<s.length();l++){
						if(s.charAt(l)=='-'){
							break;
						}
						ftime=ftime+s.charAt(l);
					}
					String ttime="";
					int gg=s.indexOf('-');
					for(int l=gg+1;l<s.length();l++){
						if(s.charAt(l)=='$'){
							break;
						}
						ttime=ttime+s.charAt(l);
					}
					gg=s.indexOf('$');
					String room="";
					for(int l=gg+1;l<s.length();l++){
						room=room+s.charAt(l);
					}
					String trim_ftime="";
					String trim_ttime="";
					for(int l=0;l<ftime.length();l++){
						
						if(ftime.charAt(l)==':'){
							break;
						}
						trim_ftime=trim_ftime+ftime.charAt(l);
					}
					for(int l=0;l<ttime.length();l++){
						
						if(ttime.charAt(l)==':'){
							break;
						}
						trim_ttime=trim_ttime+ttime.charAt(l);
					}
					int yo=0;
					int yoo=0;
					if(ftime.indexOf("30")==-1){
            			if(Integer.parseInt(trim_ftime)>7){
            				yo=Integer.parseInt(trim_ftime)-6+(Integer.parseInt(trim_ftime)-8)-1;
            			}
            			else{
            				yo=Integer.parseInt(trim_ftime)+12-6+(Integer.parseInt(trim_ftime)+12-8)-1;
            			}
            			
            				if(ttime.indexOf("30")==-1){
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9)-1;
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9)-1;
            					}
            					for(int p=yo;p<=yoo;p++){
        							data[k-6][p]=req.get(j)[5]+ " " + room;
        						}
            				}
            				else{
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
            					}
            					for(int p=yo;p<=yoo;p++){
        							data[k-6][p]=req.get(j)[5]+ " " + room;
        						}
            				}
            			
            		}
            		else{
            			if(Integer.parseInt(trim_ftime)>7){
            				yo=Integer.parseInt(trim_ftime)-6+(Integer.parseInt(trim_ftime)-8);
            			}
            			else{
            				yo=Integer.parseInt(trim_ftime)+12-6+(Integer.parseInt(trim_ftime)+12-8);
            			}
            			
            				if(ttime.indexOf("30")==-1){
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9)-1;
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9)-1;
            					}
            					
            					for(int p=yo;p<=yoo;p++){
        							data[k-6][p]=req.get(j)[5]+ " " + room;
        						}
            				}
            				else{
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
            					}
            					for(int p=yo;p<=yoo;p++){
        							data[k-6][p]=req.get(j)[5]+ " " + room;
        						}
            				}
            			
            		}
				}
				}
				
			}
		}
		
		yolo=1;
		
		
		for(int j=0;j<req.size();j++){
			if(f.courses.contains(req.get(j)[2].toUpperCase()) || f.courses.contains(req.get(j)[2].toLowerCase())){
			
					if(!req.get(j)[12].equals("0")){
					String s=req.get(j)[12];
					
					d1=s.charAt(1);
					
					String ftime="";
					for(int l=3;l<s.length();l++){
						if(s.charAt(l)=='-'){
							break;
						}
						ftime=ftime+s.charAt(l);
					}
					
					String ttime="";
					int gg=s.indexOf('-');
					for(int l=gg+1;l<s.length();l++){
						if(s.charAt(l)=='@'){
							break;
						}
						ttime=ttime+s.charAt(l);
					}
					
					String ss="";
					for(int klol=0;klol<s.length();klol++){
						if(klol==gg){
							ss=ss+'*';
						}
						else{
							ss=ss+s.charAt(klol);
						}
					}
					s=ss;
					gg=s.indexOf('@');
					ss="";
					for(int klol=0;klol<s.length();klol++){
						if(klol==gg){
							ss=ss+'*';
						}
						else{
							ss=ss+s.charAt(klol);
						}
					}
					s=ss;
					
					String room="";
					int l;
					for(l=gg+1;l<s.length();l++){
						if(s.charAt(l)=='#'){
							yolo=1;
							break;
						}
						room=room+s.charAt(l);
						yolo=0;
					}
					int lol=l;
					String trim_ftime="";
					String trim_ttime="";
					for( l=0;l<ftime.length();l++){
						
						if(ftime.charAt(l)==':'){
							break;
						}
						trim_ftime=trim_ftime+ftime.charAt(l);
					}
					for( l=0;l<ttime.length();l++){
						
						if(ttime.charAt(l)==':'){
							break;
						}
						trim_ttime=trim_ttime+ttime.charAt(l);
					}
					
					int yo=0;
					int yoo=0;
					if(ftime.indexOf("30")==-1){
            			if(Integer.parseInt(trim_ftime)>7){
            				yo=Integer.parseInt(trim_ftime)-6+(Integer.parseInt(trim_ftime)-8)-1;
            			}
            			else{
            				yo=Integer.parseInt(trim_ftime)+12-6+(Integer.parseInt(trim_ftime)+12-8)-1;
            			}
            			
            				if(ttime.indexOf("30")==-1){
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9)-1;
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9)-1;
            					}
            					for(int p=yo;p<=yoo;p++){
        							data[Character.getNumericValue(d1)][p]="LAB "+req.get(j)[5]+ " " + room;
        						}
            				}
            				else{
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
            					}
            					for(int p=yo;p<=yoo;p++){
        							data[Character.getNumericValue(d1)][p]="LAB "+req.get(j)[5]+ " " + room;
        						}
            				}
            			
            		}
            		else{
            			if(Integer.parseInt(trim_ftime)>7){
            				yo=Integer.parseInt(trim_ftime)-6+(Integer.parseInt(trim_ftime)-8);
            			}
            			else{
            				yo=Integer.parseInt(trim_ftime)+12-6+(Integer.parseInt(trim_ftime)+12-8);
            			}
            			
            				if(ttime.indexOf("30")==-1){
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9)-1;
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9)-1;
            					}
            					
            					for(int p=yo;p<=yoo;p++){
        							data[Character.getNumericValue(d1)][p]="LAB "+req.get(j)[5]+ " " + room;
        						}
            				}
            				else{
            					if(Integer.parseInt(trim_ttime)>7){
            						yoo=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
            					}
            					else{
            						yoo=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
            					}
            					for(int p=yo;p<=yoo;p++){
        							data[Character.getNumericValue(d1)][p]="LAB "+req.get(j)[5]+ " " + room;
        						}
            				}
            			
            		}
					if(yolo==1){
						String dftime="";
						d2=s.charAt(lol+1);
						for(int ll=lol+3;ll<s.length();ll++){
							if(s.charAt(ll)=='-'){
								break;
							}
							dftime=dftime+s.charAt(ll);
						}
						String dttime="";
						int dgg=s.indexOf('-');
						for(int ll=dgg+1;ll<s.length();ll++){
							if(s.charAt(ll)=='@'){
								break;
							}
							dttime=dttime+s.charAt(ll);
						}

						dgg=s.indexOf('@');
						String droom="";
						int ll;
						for(ll=dgg+1;ll<s.length();ll++){
							if(s.charAt(ll)=='#'){
								yolo=1;
								break;
							}
							droom=droom+s.charAt(ll);
							yolo=0;
						}
						String dtrim_ftime="";
						String dtrim_ttime="";
						for( ll=0;ll<dftime.length();ll++){
							
							if(dftime.charAt(ll)==':'){
								break;
							}
							dtrim_ftime=dtrim_ftime+dftime.charAt(ll);
						}
						for( ll=0;ll<dttime.length();ll++){
							
							if(dttime.charAt(ll)==':'){
								break;
							}
							dtrim_ttime=dtrim_ttime+dttime.charAt(ll);
						}
						yo=0;
						yoo=0;
						if(dftime.indexOf("30")==-1){
	            			if(Integer.parseInt(dtrim_ftime)>7){
	            				yo=Integer.parseInt(dtrim_ftime)-6+(Integer.parseInt(dtrim_ftime)-8)-1;
	            			}
	            			else{
	            				yo=Integer.parseInt(dtrim_ftime)+12-6+(Integer.parseInt(dtrim_ftime)+12-8)-1;
	            			}
	            			
	            				if(dttime.indexOf("30")==-1){
	            					if(Integer.parseInt(dtrim_ttime)>7){
	            						yoo=Integer.parseInt(dtrim_ttime)-6+(Integer.parseInt(dtrim_ttime)-9)-1;
	            					}
	            					else{
	            						yoo=Integer.parseInt(dtrim_ttime)+12-6+(Integer.parseInt(dtrim_ttime)+12-9)-1;
	            					}
	            					for(int p=yo;p<=yoo;p++){
	        							data[Character.getNumericValue(d2)][p]="LAB "+req.get(j)[5]+ " " + room;
	        						}
	            				}
	            				else{
	            					if(Integer.parseInt(dtrim_ttime)>7){
	            						yoo=Integer.parseInt(dtrim_ttime)-6+(Integer.parseInt(dtrim_ttime)-9);
	            					}
	            					else{
	            						yoo=Integer.parseInt(dtrim_ttime)+12-6+(Integer.parseInt(dtrim_ttime)+12-9);
	            					}
	            					for(int p=yo;p<=yoo;p++){
	        							data[Character.getNumericValue(d2)][p]="LAB "+req.get(j)[5]+ " " + room;
	        						}
	            				}
	            			
	            		}
	            		else{
	            			if(Integer.parseInt(dtrim_ftime)>7){
	            				yo=Integer.parseInt(dtrim_ftime)-6+(Integer.parseInt(dtrim_ftime)-8);
	            			}
	            			else{
	            				yo=Integer.parseInt(dtrim_ftime)+12-6+(Integer.parseInt(dtrim_ftime)+12-8);
	            			}
	            			
	            				if(dttime.indexOf("30")==-1){
	            					if(Integer.parseInt(dtrim_ttime)>7){
	            						yoo=Integer.parseInt(dtrim_ttime)-6+(Integer.parseInt(dtrim_ttime)-9)-1;
	            					}
	            					else{
	            						yoo=Integer.parseInt(dtrim_ttime)+12-6+(Integer.parseInt(dtrim_ttime)+12-9)-1;
	            					}
	            					
	            					for(int p=yo;p<=yoo;p++){
	        							data[Character.getNumericValue(d2)][p]="LAB "+req.get(j)[5]+ " " + room;
	        						}
	            				}
	            				else{
	            					if(Integer.parseInt(dtrim_ttime)>7){
	            						yoo=Integer.parseInt(dtrim_ttime)-6+(Integer.parseInt(dtrim_ttime)-9);
	            					}
	            					else{
	            						yoo=Integer.parseInt(dtrim_ttime)+12-6+(Integer.parseInt(dtrim_ttime)+12-9);
	            					}
	            					for(int p=yo;p<=yoo;p++){
	        							data[Character.getNumericValue(d2)][p]="LAB "+req.get(j)[5]+ " " + room;
	        						}
	            				}
	            			
	            		}
					}
					
				}
			}
		}
		
		JTable table = new JTable(data, columnNames);
		JFrame frame = new JFrame("TimeTable");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JScrollPane tableContainer = new JScrollPane(table);
        table.setRowHeight(0, 80);
        table.setRowHeight(1, 80);
        table.setRowHeight(2, 80);
        table.setRowHeight(3, 80);
        table.setRowHeight(4, 80);
        panel.add(tableContainer, BorderLayout.CENTER);
        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
	}
}
