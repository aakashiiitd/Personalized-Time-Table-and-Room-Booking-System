package application.faculty;

import application.Admin;
import application.Faculty;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FacultyController {
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
	private TextField course;
	/*
	 * This function directs to the LogIn page
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	@FXML
	private void goLogIn() throws IOException, ClassNotFoundException{
		ObjectInputStream infac=new ObjectInputStream(new FileInputStream("current.txt"));  
		Faculty f=(Faculty)infac.readObject();
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
	/*
	 * This function clears all the fields mentioned.
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
	 * This function checks the availability of a room by mentioning a particular room, day and time.
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
	
	@FXML
	public void initialize() throws IOException, ClassNotFoundException, FileNotFoundException{
		ObjectInputStream infac=new ObjectInputStream(new FileInputStream("current.txt"));  
		Faculty f=(Faculty)infac.readObject();
		infac.close();
		
		welcome.setText("Welcome "+f.firstname+" "+f.lastname);
	}
	/*
	 * This function books the room, the details of which are entered on the GUI.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, FileNotFoundException
	 */
	@FXML
	private void BookRoom() throws IOException, FileNotFoundException{
		String roomnum = room.getText();
		String dayname = day.getText();
		String ftime = time_from.getText();
		String ttime = time_to.getText();
		String co=course.getText();
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
			ArrayList<String[]> hello=new ArrayList<String[]>();
			
			int yo=0;
			int j;
			int k;
			String line="";
			String[] list = new String[23];
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
	            						for(int l=j;l<=k;l++){
		            						list[l]="1111";
		            					}
	            						JOptionPane.showMessageDialog(null, "Room booked");
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
	            						for(int l=j;l<=k;l++){
		            						list[l]="1111";
		            					}
	            						JOptionPane.showMessageDialog(null, "Room booked");
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
	            						for(int l=j;l<=k;l++){
		            						list[l]="1111";
		            					}
	            						JOptionPane.showMessageDialog(null, "Room booked");
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
	            						for(int l=j;l<=k;l++){
		            						list[l]="1111";
		            					}
	            						JOptionPane.showMessageDialog(null, "Room booked");
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
	            
	           hello.add(list);

			}
			br.close();
			FileWriter writer = new FileWriter("room.csv");
			for(int gg=0;gg<hello.size();gg++){
				for (int yolo = 0; yolo < hello.get(gg).length; yolo++) {
				    writer.append(hello.get(gg)[yolo]);
				    writer.append(",");
				}
				writer.append("\n");
			}
			writer.close();
			int joke=0;
			if(dayname.equalsIgnoreCase("Monday")){
				joke=6;
			}
			else if(dayname.equalsIgnoreCase("Tuesday")){
				joke=7;
			}
			else if(dayname.equalsIgnoreCase("Wednesday")){
				joke=8;
			}
			else if(dayname.equalsIgnoreCase("Thursday")){
				joke=9;
			}
			else if(dayname.equalsIgnoreCase("Friday")){
				joke=10;
			}
			ArrayList<String[]> reqo = new ArrayList<String[]>();
			String lineo="";
			BufferedReader bro = new BufferedReader(new FileReader("courses.csv"));
			while ((lineo = bro.readLine()) != null) {
				String[] s = lineo.split(",");
				reqo.add(s);
			}
			bro.close();
			for(int io=0;io<reqo.size();io++){
				if(reqo.get(io)[2].equalsIgnoreCase(co)){
					reqo.get(io)[joke]=ftime+"-"+ttime+"$"+roomnum;
				}
			}
			FileWriter writeri= new FileWriter("courses.csv");
			for(int gg=0;gg<reqo.size();gg++){
				for (int yolo = 0; yolo < reqo.get(gg).length; yolo++) {
				    writeri.append(reqo.get(gg)[yolo]);
				    writeri.append(",");
				}
				writeri.append("\n");
			}
			writeri.close();
		}
	}
	/*
	 * This function cancels the booking of the room made by the faculty.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, FileNotFoundException
	 */
	@FXML
	private void CancelRoom() throws IOException, FileNotFoundException{
		String roomnum = room.getText();
		String dayname = day.getText();
		String ftime = time_from.getText();
		String ttime = time_to.getText();
		String co=course.getText();
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
			ArrayList<String[]> hello=new ArrayList<String[]>();
			
			int yo=0;
			int j;
			int k;
			String line="";
			String[] list = new String[23];
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
	            			if(list[j].equals("1111")){
	            				if(ttime.indexOf("30")==-1){
	            					if(Integer.parseInt(trim_ttime)>=8){
	            						k=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
	            					}
	            					else{
	            						k=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
	            					}
	            					
	            					for(int l=j;l<=k;l++){
	            						if(list[l].equals("0")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						for(int l=j;l<=k;l++){
		            						list[l]="0";
		            					}
	            						JOptionPane.showMessageDialog(null, "Room Cancelled");
	            					}
	            					else{
	            						JOptionPane.showMessageDialog(null, "Room already available");
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
	            						if(list[l].equals("0")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						for(int l=j;l<=k;l++){
		            						list[l]="0";
		            					}
	            						JOptionPane.showMessageDialog(null, "Room Cancelled");
	            					}
	            					else{
	            						JOptionPane.showMessageDialog(null, "Room already available");
	            						yo=1;
	            					}
	            				}
	            			}
	            			else{
	            				JOptionPane.showMessageDialog(null, "Room already available");
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
	            			if(list[j].equals("1111")){
	            				if(ttime.indexOf("30")==-1){
	            					if(Integer.parseInt(trim_ttime)>=8){
	            						k=Integer.parseInt(trim_ttime)-6+(Integer.parseInt(trim_ttime)-9);
	            					}
	            					else{
	            						k=Integer.parseInt(trim_ttime)+12-6+(Integer.parseInt(trim_ttime)+12-9);
	            					}
	            					
	            					for(int l=j;l<=k;l++){
	            						if(list[l].equals("0")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						for(int l=j;l<=k;l++){
		            						list[l]="0";
		            					}
	            						JOptionPane.showMessageDialog(null, "Room Cancelled");
	            					}
	            					else{
	            						JOptionPane.showMessageDialog(null, "Room already available");
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
	            						if(list[l].equals("0")){
	            							jk=1;
	            						}
	            					}
	            					if(jk==0){
	            						for(int l=j;l<=k;l++){
		            						list[l]="0";
		            					}
	            						JOptionPane.showMessageDialog(null, "Room Cancelled");
	            					}
	            					else{
	            						JOptionPane.showMessageDialog(null, "Room already available");
	            						yo=1;
	            					}
	            				}
	            			}
	            			else{
	            				JOptionPane.showMessageDialog(null, "Room already available");
	            				yo=1;
	            			}
	            		}
	            	}
	            }
	            
	           hello.add(list);

			}
			br.close();
			FileWriter writer = new FileWriter("room.csv");
			for(int gg=0;gg<hello.size();gg++){
				for (int yolo = 0; yolo < hello.get(gg).length; yolo++) {
				    writer.append(hello.get(gg)[yolo]);
				    writer.append(",");
				}
				writer.append("\n");
			}
			writer.close();
			int joke=0;
			if(dayname.equalsIgnoreCase("Monday")){
				joke=6;
			}
			else if(dayname.equalsIgnoreCase("Tuesday")){
				joke=7;
			}
			else if(dayname.equalsIgnoreCase("Wednesday")){
				joke=8;
			}
			else if(dayname.equalsIgnoreCase("Thursday")){
				joke=9;
			}
			else if(dayname.equalsIgnoreCase("Friday")){
				joke=10;
			}
			ArrayList<String[]> reqo = new ArrayList<String[]>();
			String lineo="";
			BufferedReader bro = new BufferedReader(new FileReader("courses.csv"));
			while ((lineo = bro.readLine()) != null) {
				String[] s = lineo.split(",");
				reqo.add(s);
			}
			bro.close();
			for(int io=0;io<reqo.size();io++){
				if(reqo.get(io)[2].equalsIgnoreCase(co)){
					reqo.get(io)[joke]="0";
				}
			}
			FileWriter writeri= new FileWriter("courses.csv");
			for(int gg=0;gg<reqo.size();gg++){
				for (int yolo = 0; yolo < reqo.get(gg).length; yolo++) {
				    writeri.append(reqo.get(gg)[yolo]);
				    writeri.append(",");
				}
				writeri.append("\n");
			}
			writeri.close();
		}
	}
	/*
	 * This function displays all the rooms available for a particular day and time
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
	 * This function shows the courses taught by the faculty.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException, ClassNotFoundException, FileNotFoundException
	 */
	@FXML
	private void ViewCourses() throws IOException, FileNotFoundException, ClassNotFoundException{
		show.setText("");
		String line="";
		ArrayList<String> list;
		int yo=0;
		ObjectInputStream infac=new ObjectInputStream(new FileInputStream("current.txt"));  
		Faculty f=(Faculty)infac.readObject();
		infac.close();
		BufferedReader br = new BufferedReader(new FileReader("faccourse.csv"));
		while ((line = br.readLine()) != null) {
            list = new ArrayList<String>(Arrays.asList(line.split(",")));
            if(list.get(0).toLowerCase().contains(f.firstname.toLowerCase()) && list.get(0).toLowerCase().contains(f.lastname.toLowerCase())){
            	for(int i=1;i<list.size();i++){
            		show.appendText(list.get(i));
            		show.appendText("\n");
            		yo=1;
            	}
            }
		}
		if(yo==0){
			JOptionPane.showMessageDialog(null, "No Courses");
		}
		br.close();
	}
}