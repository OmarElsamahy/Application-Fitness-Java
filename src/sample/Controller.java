package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Node;

public class Controller implements Initializable
{
    static int flag;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    Socket s;
    ObjectOutputStream out;
    ObjectInputStream in;
    {
        try {                                         //initialize client socket and input and output stream
            s=new Socket("localhost",8000);
            out = new ObjectOutputStream(s.getOutputStream());
            in=new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    TextField newUsername,email,age,weight,height,goal;
    @FXML
    PasswordField newPassword;
    public void Sign_IN(ActionEvent event) throws IOException { // sign in fucntion that gets text from the text fields from user gui window when button pressed and sends it through stream to server
        String x=username.getText();
        String y=password.getText();
        out.writeInt(1);//sends 1 to state that the user wants to sign in
        out.flush();
        System.out.println("Sent 1 to sign in");
        out.writeObject(x);
        out.flush();
        System.out.println("Sent Username");
        out.writeObject(y);
        out.flush();
        System.out.println("Sent Password");
        try {
            flag = in.readInt();
            System.out.println("Flag Received Is "+flag);//receives user id from server and sees is it is found (id != 0) continues to main menu if not show alert
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(flag==0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Sign In");
            alert.setHeaderText("Input Error");
            alert.setContentText("Username Or Password Mismatch");
            alert.showAndWait();
        }
        else{
            mainMenu(event);//means user is found and continue to main menu
        }
    }

    public void SignInNewWindow(ActionEvent event) {//sign in window that is switched to when user chooses to sign in
        Parent view2= null;
        try {
            view2 = FXMLLoader.load(getClass().getResource("SignIn Page.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene signINScene=new Scene(view2);
        Stage SignInWindows=(Stage)((Node)event.getSource()).getScene().getWindow();
        SignInWindows.setScene(signINScene);
        SignInWindows.show();
    }
    public void SignUpNewWindow(ActionEvent event)//sign up window is user chooses to sign up
    {
        Parent view3= null;
        try {
            view3 = FXMLLoader.load(getClass().getResource("SignUpPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene signUpScene=new Scene(view3);
        Stage SignUpWindows=(Stage)((Node)event.getSource()).getScene().getWindow();
        SignUpWindows.setScene(signUpScene);
        SignUpWindows.show();
    }

    public void SignUp(ActionEvent event)//sign up function that is initiated when user chooses to sign up where it gets the text from text fields and sends it to server to add user
    {
        String username=newUsername.getText();
        String password=newPassword.getText();
        String Email=email.getText();
        int Age,Goal;
        double Weight,Height;
        try{
            Age=Integer.parseInt(age.getText());
            Goal=Integer.parseInt(goal.getText());
            Weight=Double.parseDouble(weight.getText());
            Height=Double.parseDouble(height.getText());
            out.writeInt(2);//sends to to state that he wants to sign up
            out.flush();
            out.writeObject(username);
            out.flush();
            out.writeObject(password);
            out.flush();
            out.writeObject(Email);
            out.flush();
            out.writeDouble(Weight);
            out.flush();
            out.writeDouble(Height);
            out.flush();
            out.writeInt(Age);
            out.flush();
            out.writeInt(Goal);
            out.flush();
            System.out.println("User sent is"+username+password+Email+age+weight);
            flag=in.readInt();//receives user id of new user
            System.out.println(flag);
            mainMenu(event);
        }
        catch (Exception e)
        {
            System.out.println("Input Error");
        }
    }
    public void mainMenu(ActionEvent actionEvent)//main menu window to see what function he wants from the user
    {
        Parent view4= null;
        try {
            view4 = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene MainScene=new Scene(view4);
        Stage MainWindow=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        MainWindow.setScene(MainScene);
        MainWindow.show();
    }

    @FXML
    TextArea ta;
    @FXML
    void getProgram(ActionEvent event)//get program function send 3 to server to choose to get a program(object) assigned to this user
    {
        Program program;
        try {
            out.writeInt(3);
            out.flush();
            out.writeInt(flag);
            out.flush();
            program=(Program) in.readObject();
            printProgram(program);
            } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
           System.exit(-1);
        }

    }
    @FXML
    void getMetabolism(ActionEvent event)//get metabolism of user where he sends user id and 4 to get metabolism from user
    {
        try {
            out.writeInt(4);
            out.flush();
            out.writeInt(flag);
            out.flush();
            int metabolism=in.readInt();
            String type= (String) in.readObject();
            ta.appendText("\n"+metabolism+"\n"+type+"\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void getUserDetails(ActionEvent event)//get details of the signed in user
    {
           try{
               out.writeInt(5);
               out.flush();
               out.writeInt(flag);
               out.flush();
               String username= (String) in.readObject();
               String password= (String) in.readObject();
               String Email= (String) in.readObject();
               double Weight=in.readDouble();
               double Height=in.readDouble();
               int Age=in.readInt();
               int Goal=in.readInt();
               ta.appendText("\n"+username+"   "+password+"   "+Email+"  "+Age+"     "+flag+"   "+Goal+"     "+Weight+"     "+Height+"\n");
           }
           catch(IOException | ClassNotFoundException e)
           {

           }
    }

    void printProgram(Program program)//function to print all data in program object when received from server
    {
        for(int i=0;i<program.foods.size();i++)
        {
            ta.appendText(program.foods.get(i).getFoodName()+"\n");
        }

        for(int i=0;i<program.Sworkout.size();i++)
        {
            ta.appendText(program.Sworkout.get(i).getWorkoutName()+"\n");
        }

        for(int i=0;i<program.Cworkout.size();i++)
        {
            ta.appendText(program.Cworkout.get(i).getWorkoutName()+"\n");
        }
        ta.appendText(program.coach.getCoachName()+"\n");

    }

}
