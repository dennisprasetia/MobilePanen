package com.wonokoyo.budidaya.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "weigh")
public class Weigh implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Real.class, parentColumns = "no_do", childColumns = "nodo_real")
    private String nodo_real;

    @SerializedName("urut")
    @Expose
    private int urut;

    @SerializedName("berat")
    @Expose
    private double berat;

    @SerializedName("ekor")
    @Expose
    private int ekor;

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

    public int getUrut() {
        return urut;
    }

    public void setUrut(int urut) {
        this.urut = urut;
    }

    public double getBerat() {
        return berat;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public int getEkor() {
        return ekor;
    }

    public void setEkor(int ekor) {
        this.ekor = ekor;
    }
}
