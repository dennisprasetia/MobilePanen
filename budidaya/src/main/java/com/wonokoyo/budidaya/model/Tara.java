package com.wonokoyo.budidaya.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "tara")
public class Tara implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Real.class, parentColumns = "no_do", childColumns = "nodo_real")
    private String nodo_real;

    @SerializedName("rit")
    @Expose
    private String rit;

    @SerializedName("urut")
    @Expose
    private String urut;

    @SerializedName("berat")
    @Expose
    private double berat;

    @SerializedName("tgl_tara")
    @Expose
    private String tgl_tara;

    @SerializedName("tipe")
    @Expose
    private String tipe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNodo_real() {
        return nodo_real;
    }

    public void setNodo_real(String nodo_real) {
        this.nodo_real = nodo_real;
    }

    public String getRit() {
        return rit;
    }

    public void setRit(String rit) {
        this.rit = rit;
    }

    public String getUrut() {
        return urut;
    }

    public void setUrut(String urut) {
        this.urut = urut;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public String getTgl_tara() {
        return tgl_tara;
    }

    public void setTgl_tara(String tgl_tara) {
        this.tgl_tara = tgl_tara;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}
