package sample;

import java.util.ArrayList;

class App
{
    String appName="Nutritionist's Way";
    private static ArrayList<User> users=new ArrayList<>();
    private static ArrayList<Program>Programs=new ArrayList<>();
    static synchronized void Registration(String username,String password,String email,double weight,double height,int userID,int age,int goal)
    {//static method to add all users in app
        User x=new User(username, password, email, weight, height, userID, age, goal);
        users.add(x);
    }

}
