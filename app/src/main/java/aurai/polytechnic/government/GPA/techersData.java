package aurai.polytechnic.government.GPA;

public class techersData  {

    private String Email,Name,Tpics,Uniqueid;
    public  techersData()
    {

    }

    public String getUniqueid() {
        return Uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        Uniqueid = uniqueid;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTpics() {
        return Tpics;
    }

    public void setTpics(String tpics) {
        Tpics = tpics;
    }

    public techersData(String email, String name, String tpics,String uniqueid) {
        Email = email;
        Name = name;

        Tpics = tpics;
        Uniqueid=uniqueid;
    }




}
