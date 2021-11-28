package com.example.laundryyy.AddData;

import java.io.Serializable;

public class DataLaundry implements Serializable {

    private String id;
    private String email;
    private String nama;
    private String alamat;
    private String nomer;
    private String informasi;
    private Double latitude, longitude;

    public DataLaundry(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        return ""+nama+"\n"+
                ""+alamat+"\n"+
                ""+nomer+"\n"+
                ""+informasi+"\n"+
                ""+latitude+"\n"+
                ""+longitude;
    }

    public DataLaundry(String idi, String em, String nm, String al,String no, String in, double la, double lo){
        id=idi;
        email=em;
        nama=nm;
        alamat=al;
        nomer=no;
        informasi=in;
        latitude=la;
        longitude=lo;
    }
}
