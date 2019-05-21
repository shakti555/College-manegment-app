package aurai.polytechnic.government.GPA;

public class testdata {
    private String Title,Link,Date,Uniqueid;

    public testdata()
    {

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUniqueid() {
        return Uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        Uniqueid = uniqueid;
    }

    public testdata(String title, String link, String date, String uniqueid) {
        Title = title;
        Link = link;
        Date = date;
        Uniqueid = uniqueid;
    }
}
