package com.example.vishalsingh.admin;

/**
 * Created by vishalsingh on 12/11/17.
 */

class Data {

    private String drivername;
    private String busno;
    private String location;
    private String studentcount;
    private String busstatus;
    private int imgId;


   public Data(String drivername,String busno,String loaction,String studentcount,String busstatus,int imgId){
       this.drivername = drivername;
       this.busno = busno;
       this.location = loaction;
       this.studentcount = studentcount;
       this.busstatus = busstatus;
       this.imgId = imgId;
   }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStudentcount() {
        return studentcount;
    }

    public void setStudentcount(String studentcount) {
        this.studentcount = studentcount;
    }

    public String getBusstatus() {
        return busstatus;
    }

    public void setBusstatus(String busstatus) {
        this.busstatus = busstatus;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
