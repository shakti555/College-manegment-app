package aurai.polytechnic.government.GPA;

public class pdfdata {

    public  pdfdata()
    {

    }

  private   String Pdftxt,pdfurl,Uniqueid,Date;

    public String getPdftxt() {
        return Pdftxt;
    }

    public void setPdftxt(String pdftxt) {
        Pdftxt = pdftxt;
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }

    public String getUniqueid() {
        return Uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        Uniqueid = uniqueid;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public pdfdata(String pdftxt, String pdfurl, String uniqueid, String date) {
        Pdftxt = pdftxt;
        this.pdfurl = pdfurl;
        Uniqueid = uniqueid;
        Date = date;
    }
}
