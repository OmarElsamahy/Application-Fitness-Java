package sample;

import java.io.Serializable;
//class live coach is to get the details of coaches and which coach is suitable for which client
class Live_Coach implements Serializable//implements serializable to be sent through server stream
{
    private String coachName,mobileNumber;
    private int coach_ID;
    Live_Coach(String name,String number,int ID)
    {
        coach_ID=ID;
        coachName=name;
        mobileNumber=number;
    }
    int getCoachID()
    {
        return coach_ID;
    }
    String getCoachName()
    {
        return coachName;
    }
}
