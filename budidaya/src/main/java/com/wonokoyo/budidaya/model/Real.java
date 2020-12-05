package com.wonokoyo.budidaya.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "real")
public class Real implements Serializable {
    @PrimaryKey
    @SerializedName("do")
    @Expose
    private String no_do;

    @SerializedName("sj")
    @Expose
    private String no_sj;

    @SerializedName("mitra")
    @Expose
    private String mitra;

    @SerializedName("rit")
    @Expose
    private String rit;

    @SerializedName("kandang")
    @Expose
    private String kandang;

    @SerializedName("tgl_panen")
    @Expose
    private String tgl_panen;

    @SerializedName("mulai_panen")
    @Expose
    private String mulai_panen;

    @SerializedName("selesai_panen")
    @Expose
    private String selesai_panen;

    @SerializedName("bruto")
    @Expose
    private double bruto;

    @SerializedName("netto")
    @Expose
    private double netto;

    @SerializedName("total_tandu")
    @Expose
    private double total_tandu;

    @SerializedName("tara_tandu")
    @Expose
    private double tara_tandu;

    @SerializedName("bb")
    @Expose
    private double bb;

    @SerializedName("ekor")
    @Expose
    private int ekor;

    @SerializedName("status")
    @Expose
    private int status;

    public String getNo_do() {
        return no_do;
    }

    public void setNo_do(String no_do) {
        this.no_do = no_do;
    }

    public String getNo_sj() {
        return no_sj;
    }

    public void setNo_sj(String no_sj) {
        this.no_sj = no_sj;
    }

    public String getMitra() {
        return mitra;
    }

    public void setMitra(String mitra) {
        this.mitra = mitra;
    }

    public String getRit() {
        return rit;
    }

    public void setRit(String rit) {
        this.rit = rit;
    }

    public String getKandang() {
        return kandang;
    }

    public void setKandang(String kandang) {
        this.kandang = kandang;
    }

    public String getTgl_panen() {
        return tgl_panen;
    }

    public void setTgl_panen(String tgl_panen) {
        this.tgl_panen = tgl_panen;
    }

    public String getMulai_panen() {
        return mulai_panen;
    }

    public void setMulai_panen(String mulai_panen) {
        this.mulai_panen = mulai_panen;
    }

    public String getSelesai_panen() {
        return selesai_panen;
    }

    public void setSelesai_panen(String selesai_panen) {
        this.selesai_panen = selesai_panen;
    }

    public double getBruto() {
        return bruto;
    }

    public void setBruto(double bruto) {
        this.bruto = bruto;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getTotal_tandu() {
        return total_tandu;
    }

    public void setTotal_tandu(double total_tandu) {
        this.total_tandu = total_tandu;
    }

    public double getTara_tandu() {
        return tara_tandu;
    }

    public void setTara_tandu(double tara_tandu) {
        this.tara_tandu = tara_tandu;
    }

    public double getBb() {
        return bb;
    }

    public void setBb(double bb) {
        this.bb = bb;
    }

    public int getEkor() {
        return ekor;
    }

    public void setEkor(int ekor) {
        this.ekor = ekor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
