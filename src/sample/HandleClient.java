package sample;

import java.io.*;
import java.net.Socket;

public class HandleClient implements Runnable{
    Socket s;
    ObjectOutputStream out;
    ObjectInputStream  in;
    HandleClient(Socket socket) throws IOException {//gets the accepted socket from the server and begins initiation with client
        s=socket;
        in=new ObjectInputStream(s.getInputStream());//gets input and output objects
        out=new ObjectOutputStream(s.getOutputStream());
    }
    @Override
    public void run() {
        while(true)//while true for a continous interaction
        {
            try {
                int choice=in.readInt();
                if(choice==1)//choice 1 which is sign in takes username and password and searches through data base by calling sign in function explained down
                {
                    String username= (String) in.readObject();
                    String password= (String) in.readObject();
                    int x=signIN(username,password);
                    out.writeInt(x);
                    out.flush();
                //    System.out.println(username+" - "+password+" - "+x);
                }
                else if(choice==2)//choice 2 to sign up new user and send details to database for adding and adding a new metabolism too
                {
                    String username= (String) in.readObject();
                    String password= (String) in.readObject();
                    String Email= (String) in.readObject();
                    double width=in.readDouble();
                    double height=in.readDouble();
                    int age=in.readInt();
                    int goal=in.readInt();
                    int UserID=signUP(username,password,Email,width,height,age,goal);
                    Metabolism metabolism =new Metabolism(width,height,age,UserID);
                    DBHandler.addMetabolism(width,height,age,UserID,metabolism);//the static method in database handler that adds users to it
                    out.writeInt(UserID);
                    out.flush();
                }
                else if(choice==3)
                {//searches for the approptiate program for the user and gets its details fromm database and sends the whole program to the client
                    int userID=in.readInt();
                    Program p=DBHandler.getProgramForUser(userID);//static fn in dbhandler that gets program from database
                    System.out.println("Brought Program from database");
                    out.writeObject(p);
                    out.flush();
                    System.out.println("Program sent to user-----------");
                }
                else if(choice==4)
                {
                    int user_ID=in.readInt();
                    int calCount=DBHandler.getMetabolsim(user_ID);
                    String type=DBHandler.getMetabolsimType(user_ID);
                    out.writeInt(calCount);
                    out.flush();
                    out.writeObject(type);
                    out.flush();
                }
                else if(choice==5)
                {
                    int user_ID=in.readInt();
                    userData(user_ID);
                }
            } catch (Exception e) {
            }
        }
    }
    int signIN(String username,String password)
    {
        int x=DBHandler.searchForUser(username,password);//the sign in method mentioned above which calls static method in dbhandler
        return x;
    }
    int signUP(String username,String password,String email,double weight,double height,int age,int goal)
    {
        int userid=DBHandler.addUser(username,password,email,weight,height,age,goal);
        return userid;
    }
    void userData(int userID)//gets user details and sends it to client
    {
        User u=DBHandler.getUser(userID);
        try {
            out.writeObject(u.getName());
            out.flush();
            out.writeObject(u.getPassword());
            out.flush();
            out.writeObject(u.getEmail());
            out.flush();
            out.writeDouble(u.getWeight());
            out.flush();
            out.writeDouble(u.getHeight());
            out.flush();
            out.writeInt(u.getAge());
            out.flush();
            out.writeInt(u.getGoal());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
