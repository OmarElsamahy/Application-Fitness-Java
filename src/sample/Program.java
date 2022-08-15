package sample;

import java.io.Serializable;
import java.util.ArrayList;
//class program which holds the program id and all its contents of food,workouts and caoch
class Program implements Serializable//serializable to be sent through server
{
    private int id,type;
    ArrayList<Strength_Workout> Sworkout=new ArrayList<>();
    ArrayList<Cardiac_Workout>Cworkout=new ArrayList<>();
    ArrayList<Food>foods=new ArrayList<>();
    Live_Coach coach;
    Program(int id,int type)
    {
        this.id=id;
        this.type=type;
    }
    Program(int type)
    {
        this.type=type;
    }
    void addSWorkout(ArrayList<Strength_Workout> s)
    {
        Sworkout=s;
    }
    void addCWorkout(ArrayList<Cardiac_Workout> c)
    {
        Cworkout=c;
    }
    void addFood(ArrayList<Food> f)
    {
        foods=f;
    }
    void addCoach(Live_Coach coach) { this.coach=coach; }
    int getId()
    {
        return id;
    }
    int getType()
    {
        return type;
    }
    ArrayList getSWorkout()
    {
        return Sworkout;
    }
    ArrayList getCWorkout()
    {
        return Cworkout;
    }
    ArrayList getFood()
    {
        return foods;
    }
    Live_Coach getCoach()
    {
        return coach;
    }

}