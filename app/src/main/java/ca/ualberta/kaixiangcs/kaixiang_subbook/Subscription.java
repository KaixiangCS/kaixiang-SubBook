package ca.ualberta.kaixiangcs.kaixiang_subbook;

/**
 * Created by kaixiangzhang on 2018-02-05.
 *
 * create a new customed object
 * @author Kaixiang Zhang
 * @version 1.0
 * @see Subscripible
 */

public class Subscription implements Subscripible{

    private String name;
    private String date;
    private float charge;
    private String comment;

    /**
     * construct a Subscription object to contain several values in one object
     * @param name
     * @param date
     * @param charge
     * @param comment
     */

    Subscription(String name, String date, float charge,String comment){
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }
    public String getName(){return name;}
    public float getCharge(){return charge;}
    public void setCharge(float charge ){
        this.charge = charge;

        //this.trigger = this.trigger + 2;
    }

    public String getComment(){return comment;}

    public void setComment(String name){
        if (name.length()<30){
            this.name = name;
            System.out.println ("correct!");
        }
        else{
            System.out.println ("Not a correct format");
        }
        //his.trigger = this.trigger + 2;
    }


    public void setName(String name){
        if (name.length()<20){
            this.name = name;
            System.out.println ("correct!");
        }
        else{
            System.out.println ("Not a correct format");
        }
        //this.trigger = this.trigger + 2;
    }
    public String getDate(){return date;}

    public void setDate(String date){
        if (date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")){
            this.date = date;
            //this.trigger = this.trigger + 1;
        }

    }

    /**
     * Use to represent subscription object properly
     * @return
     */
    public String toString() {

        return name + " | " + date + " | " + String.valueOf(charge) + " | " + comment;
    }

}

