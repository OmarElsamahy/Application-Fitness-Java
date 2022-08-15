package sample;

import java.io.Serializable;
//class food to hold all data of food as its calories name and in which program should we put it
class Food implements Serializable//implements serializable as we send all data through stream
{
    private String name;
    private double calories;
    private int food_ID;
    private int program_ID;
    Food(String name,double calories,int food_ID,int program_ID)
    {
        this.name=name;
        this.calories=calories;
        this.food_ID=food_ID;
        this.program_ID=program_ID;
    }
    String getFoodName()
    {
        return name;
    }
    int getFood_ID()
    {
        return food_ID;
    }
    double getCalories()
    {
        return calories;
    }
    int getProgram_ID()
    {
        return program_ID;
    }
}
