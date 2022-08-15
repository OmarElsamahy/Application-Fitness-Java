package sample;

import com.mysql.cj.protocol.Resultset;

import java.io.StringBufferInputStream;
import java.sql.*;
import java.util.ArrayList;
//dbhandler class that is the only class that handles database and has a connection with it and can do all operations in it
public class DBHandler {
    static Statement stmt;
    static ResultSet rset;
    static  Connection connection;
    static void  initializeDB() {//initializes database and performs connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
           connection = DriverManager.getConnection("jdbc:mysql://localhost/fitnessapp", "root", "omar");//username and password of mysql
            System.out.println("Database connected");
            stmt = connection.createStatement();
        }
        catch (Exception ex) {
            System.out.println("Error in Connection");
            System.exit(-1);
        }
    }
    public static int getNumberOfUsers()//function that searches for highest user id in database and is used to add a new user to give him a new userid
    {
        String query="select max(User_ID) from users;";//the sql statmenet added
        int maxUser=0;
            try {
                rset=stmt.executeQuery(query);
                if(rset.next()) {
                    maxUser = rset.getInt(1);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        return maxUser;//gets max user and returns it
    }
    public static int addUser(String username,String password,String email,double weight,double height,int age,int goal)
    {//the add user fn where it gets all data of user and adds it to the database with the incremented user id to prevent errors
        int UserID=getNumberOfUsers()+1;
        String query="insert into users values('"+username+"','"+password+"','"+email+"',"+weight+","+height+","+age+","+UserID+","+goal+");";
        try {
            stmt.executeUpdate(query);
            System.out.println("User Added");
            App.Registration(username,password,email,weight,height,UserID,age,goal);//calls the registration function is App to add a new User in the class
        } catch (SQLException throwables) {
            System.out.println("Error in user addition");
        }
        return UserID;//returns user id to be sent by handle client to client
    }
    static void addMetabolism(double weight, double height, int age, int UserID,Metabolism m)
    {//add metablism fn used for every new user to give him his metabolic details
        Metabolism metabolism=m;//has a new metabolism object that adds the data to it
        // and uses it to calculate the details of the user as type and calorie burn
        double caloric_Burn=metabolism.getCalorieBurn();
        String metabolism_type=metabolism.getMetabolismType(caloric_Burn);
        String metabolismEntryQuery="insert into Metabolism values('"+metabolism_type+"',"+caloric_Burn+","+UserID+");";
        try{
            stmt.executeUpdate(metabolismEntryQuery);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public static int searchForUser(String Username,String Password)
    {//method used to sign in where it searches database for the user if found return id if not return 0
        int userID=0;
        String query="Select * from users where Username='"+Username+"' and Password = '"+Password+"';";
        try {
            rset=stmt.executeQuery(query);
            if(rset.next())
            {
                userID=rset.getInt(7);
                System.out.println("ID is"+userID);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return userID;
    }
    public static Program getProgramForUser(int UserID) {
        Program program = null;//get program for user is made to find program with the goal that user set for himeself
        //then when we find program we search through database for the specific food,workouts and coaches appropriate
        //for him to be added and brought back to the client
        int goal = 0, progType = 0;
        try {
            rset = stmt.executeQuery("Select Goal from users where User_ID = " +UserID+ ";");
            if(rset.next()) {
                goal = rset.getInt(1);
            }
            rset = stmt.executeQuery("Select Type_ID from Program where Type_ID=" +goal+ ";");
            if(rset.next()) {
                progType = rset.getInt(1);
            }
            program=new Program(progType,progType);
            rset=stmt.executeQuery("Select * from Food where Program_ID= "+progType+";");
            while(rset.next())
            {
                    String Food_Name= rset.getString(1);
                    double calories=rset.getDouble(2);
                    int foodID = rset.getInt(3);
                    int program_ID=rset.getInt(4);
                Food f=new Food(Food_Name,calories,foodID,program_ID);
                program.foods.add(f);

            }
            rset=stmt.executeQuery("Select * from Strength_workout where Program_ID= "+progType+";");
            while(rset.next())
            {
                   String workoutname=rset.getString(1);
                   int calorieBurn= rset.getInt(2);
                   int workout_ID=rset.getInt(3);
                   int programID=rset.getInt(4);
                   String targetedMuscle= rset.getString(5);
                   Workouts sw = new Strength_Workout(workoutname,calorieBurn,workout_ID,programID,targetedMuscle);// polymorphism to set that strength workouts is a tye of workouts
                   program.Sworkout.add((Strength_Workout) sw);
                   System.out.println("Workout " + sw.workoutName + " Added");
            }
            rset=stmt.executeQuery("Select * from cardio_workout where Program_ID= "+progType+";");
            while(rset.next())
            {
                    String workoutname=rset.getString(1);
                    int calorieBurn= rset.getInt(2);
                    int workout_ID=rset.getInt(3);
                    int duration= rset.getInt(4);
                    int programID=rset.getInt(5);
                Workouts cw=new Cardiac_Workout(workoutname,calorieBurn,workout_ID,duration,programID);// polymorphism to set that cardiac workouts is a tye of workouts
                program.Cworkout.add((Cardiac_Workout) cw);
            }
            rset=stmt.executeQuery("Select * from Live_Coach where Coach_ID= "+progType+";");
            if(rset.next())
            {
                String name=rset.getString(1);
                String num=rset.getString(2);
                int id=rset.getInt(3);
                Live_Coach coach = new Live_Coach(name,num,id);
                program.coach=coach;
            }
           } catch (SQLException throwables) {
               System.out.println("Error in getting program");
           }
        return program;
    }
    public static int getMetabolsim(int User_ID)
    {//returns metabolism calorie burn in metabolism of this user
        int calorie_burn=0;
        String query="select Caloric_Burn from Metabolism where User_ID= "+User_ID+";";
        try {
            rset=stmt.executeQuery(query);
            if(rset.next()){
            calorie_burn=rset.getInt(1);
            }
        } catch (SQLException throwables) {
            System.out.println("Error in getMetabolism");
        }
        return calorie_burn;
    }
    public static String getMetabolsimType(int User_ID)
    {//returns metabolism type of user
        String type = null;
        String query="select type from Metabolism where User_ID= "+User_ID+";";
        try {
            rset=stmt.executeQuery(query);
            if(rset.next()){
            type=rset.getString(1);
            }
        } catch (SQLException throwables) {
            System.out.println("Error in getMetabolism");
        }
        return type;
    }
    public static User getUser(int userID)
    {//gets all user data from database
        User u=new User();
        String query="Select * from users where User_ID= "+userID+";";
        try {
            rset=stmt.executeQuery(query);
            if(rset.next())
            {
                u.setUsername(rset.getString(1));
                u.setPassword(rset.getString(2));
                u.setEmail(rset.getString(3));
                u.setWeight(rset.getDouble(4));
                u.setHeight(rset.getDouble(5));
                u.setAge(rset.getInt(6));
                u.setUserID(rset.getInt(7));
                u.setGoal(rset.getInt(8));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return u;
    }
}
