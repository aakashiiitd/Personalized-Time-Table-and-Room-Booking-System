package application;

import java.util.ArrayList;
import java.io.*;

public class Faculty implements Serializable {
	public String firstname;
	public String lastname;
	public String email;
	public String password;
	public String type;
	public ArrayList<String> courses;
	
	public Faculty(String firstname,String lastname,String email,String pass,String type) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.email = email;
		this.password = pass;
		this.type = type;
	}
}