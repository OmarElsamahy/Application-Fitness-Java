package sample;

import java.io.Serializable;
//class holds all data s users metabolism where he heas type of metabolism,amount of calories burnt and which user does it belong to
class Metabolism implements MetaCalculator , Serializable // implements serializable to be sent and metacaluclator which holds the function of calculating calories
{
    private double calorieBurn;
    private String type;
    private int userID;
    private double weight,height;
    private int age;
    Metabolism(double weight,double height,int age,int userID)
    {
        this.weight=weight;
        this.height=height;
        this.age=age;
        this.userID=userID;
        MetabolismCalculator();
        FindType(calorieBurn);
    }
    @Override
    public double MetabolismCalculator()
    {
        calorieBurn=66.5+(13.75*weight) + ( 5.003 * height)-( 6.755 * age);
        return calorieBurn;
    }
    void FindType(double calorieBurn)
    {
        if(calorieBurn>1700)
        {
            type="mesomorph";
        }
        else if(calorieBurn<1300)
        {
            type="endomorph";
        }
        else
        {
            type="ectomorph";
        }
    }
    String getMetabolismType(double calorieBurn)
    {
        return  type;
    }
    double getCalorieBurn()
    {
        return calorieBurn;
    }
}
