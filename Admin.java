package application;
import java.io.*;
import java.util.ArrayList;

public class Admin implements Serializable {
	public String firstname;
	public String lastname;
	public String email;
	public String password;
	public String type;
	
	public Admin(String firstname,String lastname,String email,String pass,String type) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.email = email;
		this.password = pass;
		this.type = type;
		
	}
}