package aurai.polytechnic.government.GPA;


public class Post {


     public Post()
     {

     }


      private  String  Time,Date,Discription,Postimg;

    public String getPostimg() {
        return Postimg;
    }

    public void setPostimg(String postimg) {
        Postimg = postimg;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public Post(String mImageResourse, String time, String date, String discription) {
        this.Postimg = mImageResourse;
        Time = time;
        Date = date;

        Discription = discription;
    }
}

