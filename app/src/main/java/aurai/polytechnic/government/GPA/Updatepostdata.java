package aurai.polytechnic.government.GPA;

public class Updatepostdata {

    public  Updatepostdata()
    {

    }
    private  String Discription,Postimg,Uniqueid;

    public String getUniqueid() {
        return Uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        Uniqueid = uniqueid;
    }

    public Updatepostdata(String discription, String postimg,String uniqueid) {
        Discription = discription;
        Postimg = postimg;
        Uniqueid=uniqueid;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getPostimg() {
        return Postimg;
    }

    public void setPostimg(String postimg) {
        Postimg = postimg;
    }
}
