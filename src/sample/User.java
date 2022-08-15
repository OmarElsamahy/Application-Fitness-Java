package sample;

import java.util.ArrayList;

class User
{
    private String username,password,email;
    private double weight,height;
    private int userID,age,goal;
    Program p;
    User(String username,String password,String email,double weight,double height,int userID,int age,int goal)
    {
        this.username=username;
        this.password=password;
        this.email=email;
        this.weight=weight;
        this.height=height;
        this.userID=userID;
        this.age=age;
        this.goal=goal;
    }
    User()
    {

    }
    String getName()
    {
        return username;
    }

    void setProgram(ArrayList<Program> Programs)
    {
        try {
            for (int i = 0; i < Programs.size(); i++) {
                if (Programs.get(i).getId() == goal) {
                    p = Programs.get(i);
                }
            }
        }

        catch(Exception e)
        {
            System.out.println("Exception in set programs");
        }
    }
    Program  getProgram()
    {
        return p;
    }
    void setUsername(String x)
    {
        username=x;
    }
    void setPassword(String x)
    {
        password=x;
    }
    void setEmail(String x)
    {
        email=x;
    }
    void setWeight(double x)
    {
        weight=x;
    }
    void setHeight(double x)
    {
        height=x;
    }
    void setUserID(int x)
    {
        userID=x;
    }
    void setAge(int x)
    {
        age=x;
    }
    void setGoal(int x)
    {
        goal=x;
    }
    String getPassword()
    {
        return password;
    }
    String getEmail()
    {
        return email;
    }
    double getWeight()
    {
        return weight;
    }
    double getHeight()
    {
        return height;
    }
    int getAge()
    {
        return age;
    }
    int getGoal()
    {
        return goal;
    }
}