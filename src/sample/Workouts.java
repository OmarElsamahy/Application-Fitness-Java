package sample;

import java.io.Serializable;
//class workout which is inherited by 2 subchilds strength and cardio which each have their details and override a fn
//the worout is added and then put in programs which is sent to  user
class Workouts implements Serializable//serializable to be sent through servers
{
    protected String workoutName;
    protected double calorie_Burn;
    Workouts(String name,double burn)
    {
        workoutName=name;
        calorie_Burn=burn;
    }
    void calculateCalorieBurn(){};
}
class Strength_Workout extends Workouts implements Serializable
{
    private int workout_ID,Program_ID;
    private String Targeted_Muscle;
    Strength_Workout(String name,double calorie_Burn,int Workout_ID,int Program_ID,String Targeted_Muscle)
    {
        super(name,calorie_Burn);
        workout_ID=Workout_ID;
        this.Program_ID=Program_ID;
        this.Targeted_Muscle=Targeted_Muscle;
    }
    double getCalorieBurn()
    {
        return (calorie_Burn * 3 * 10 -300);
    }
    int getProgram_ID()
    {
        return Program_ID;
    }
    String getWorkoutName()
    {
        return workoutName;
    }
}
class Cardiac_Workout extends Workouts implements Serializable
{
   private int workout_ID,Program_ID;
    private double duration;
    Cardiac_Workout(String name,double calorie_Burn,int Workout_ID,double duration,int Program_ID)
    {
        super(name,calorie_Burn);
        workout_ID=Workout_ID;
        this.Program_ID=Program_ID;
        this.duration=duration;
    }
    double getCalorieBurn()
    {
        return (calorie_Burn * duration -300);
    }
    int getProgram_ID()
    {
        return Program_ID;
    }
    String getWorkoutName()
    {
        return workoutName;
    }
}
