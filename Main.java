package application;
	
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private Stage primaryStage;
	private static BorderPane mainLayout;
	
	public static ArrayList<Student> stu=new ArrayList<Student>();
	public static ArrayList<Faculty> fac=new ArrayList<Faculty>();
	public static ArrayList<Admin> ad=new ArrayList<Admin>();
	
	/* 
	 * This sets the stage for starting the GUI.
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Timetable");
			FXMLLoader loader= new FXMLLoader();
			loader.setLocation(Main.class.getResource("log/log.fxml"));
			mainLayout = loader.load();
			Scene scene=new Scene(mainLayout);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			ArrayList<String[]> hello=new ArrayList<String[]>();
	    	BufferedReader br = new BufferedReader(new FileReader("logged.csv"));
			String line="";
			while ((line = br.readLine()) != null) {
				String[] s = line.split(",");
				hello.add(s);
			}
			br.close();
			int yo=0;
			for(int jk=0;jk<hello.size();jk++){
				if(hello.get(jk)[1].equals("1") && hello.get(jk)[2].equals("Student")){
					showStudent();
					yo=1;
				}
				else if(hello.get(jk)[1].equals("1") && hello.get(jk)[2].equals("Faculty")){
					showFaculty();
					yo=1;
				}
				else if(hello.get(jk)[1].equals("1") && hello.get(jk)[2].equals("Admin")){
					showAdmin();
					yo=1;
				}
				
			}
			//int yo=0;
			if(yo==0){
				showLogin();
			}
	}
	/* 
	 * This function starts a new FXML loader for loading the fxml file mentioned.
	 * It also creates a scene for the BorderPane.
	 * It then displays the GUI
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	private void showMainView() throws IOException{
		FXMLLoader loader= new FXMLLoader();
		loader.setLocation(Main.class.getResource("log/log.fxml"));
		mainLayout = loader.load();
		Scene scene=new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/* 
	 * This function starts a new FXML loader for loading the fxml file mentioned.
	 * It also creates a scene for the BorderPane.
	 * It then displays the GUI
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	public static void showSignUp() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("sign/Sign.fxml"));
		BorderPane sp = loader.load();
		mainLayout.setCenter(sp);
	}
	/* 
	 * This function starts a new FXML loader for loading the fxml file mentioned.
	 * It also creates a scene for the BorderPane.
	 * It then displays the GUI
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	public static void showLogin() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("log/log.fxml"));
		BorderPane sp = loader.load();
		mainLayout.setCenter(sp);
	}
	/* 
	 * This function starts a new FXML loader for loading the fxml file mentioned.
	 * It also creates a scene for the BorderPane.
	 * It then displays the GUI
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	public static void showStudent() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("student/Student.fxml"));
		BorderPane sp = loader.load();
		mainLayout.setCenter(sp);
	}
	/* 
	 * This function starts a new FXML loader for loading the fxml file mentioned.
	 * It also creates a scene for the BorderPane.
	 * It then displays the GUI
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	public static void showFaculty() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("faculty/Faculty.fxml"));
		BorderPane sp = loader.load();
		mainLayout.setCenter(sp);
	}
	/* 
	 * This function starts a new FXML loader for loading the fxml file mentioned.
	 * It also creates a scene for the BorderPane.
	 * It then displays the GUI
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	public static void showAdmin() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("admin/Admin.fxml"));
		BorderPane sp = loader.load();
		mainLayout.setCenter(sp);
	}
	/* 
	 * This function starts a new FXML loader for loading the fxml file mentioned.
	 * It also creates a scene for the BorderPane.
	 * It then displays the GUI
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	public static void showRequestRoom() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("requestroom/RequestRoom.fxml"));
		BorderPane sp = loader.load();
		mainLayout.setCenter(sp);
	}
	/* 
	 * This function starts a new FXML loader for loading the fxml file mentioned.
	 * It also creates a scene for the BorderPane.
	 * It then displays the GUI
	 * 
	 * @author Aakash Tanwar and Rahul Patwardhan
	 * @exception throws IOException
	 */
	public static void showCourses() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("courses/courses.fxml"));
		BorderPane sp = loader.load();
		mainLayout.setCenter(sp);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
