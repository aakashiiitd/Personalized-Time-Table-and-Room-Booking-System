package application;

import java.io.*;
import java.util.ArrayList;

public class Student implements Serializable{
	public String firstname;
	public String lastname;
	public String email;
	public String password;
	public String type;
	public String group;
	public ArrayList<String> courses=new ArrayList<String>();
	
	public Student(String firstname,String lastname,String email,String pass,String type,ArrayList<String> courses) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.email = email;
		this.password = pass;
		this.type = type;
	}
}