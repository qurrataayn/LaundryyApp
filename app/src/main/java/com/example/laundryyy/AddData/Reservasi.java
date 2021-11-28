package com.example.laundryyy.AddData;

public class Reservasi {
    String nama;
    String alamat;
    String nohp;
    int nourut;
    private double latitude, longitude;

    public Reservasi(){

    }

    public int getNourut() {
        return nourut;
    }

    public void setNourut(int nourut) {
        this.nourut = nourut;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString(){
        return ""+nama+"\n"+
                ""+alamat+"\n"+
                ""+nohp+"\n"+
                ""+nourut+"\n"+
                ""+latitude+"\n"+
                ""+longitude;
    }

    public Reservasi (String nm, String al, String nhp, double la, double lo, int nour){
        nama=nm;
        alamat=al;
        nohp=nhp;
        nourut=nour;
        latitude=la;
        longitude=lo;
    }
}
