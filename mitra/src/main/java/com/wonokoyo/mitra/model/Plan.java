package com.wonokoyo.mitra.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "plan")
public class Plan implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("no_do")
    @Expose
    private String no_do;

    @SerializedName("no_sj")
    @Expose
    private String no_sj;

    @SerializedName("noreg")
    @Expose
    private String noreg;

    @SerializedName("rit")
    @Expose
    private String rit;

    @SerializedName("nik_panen")
    @Expose
    private String nik_tim;

    @SerializedName("tim_panen")
    @Expose
    private String nama_tim;

    @SerializedName("tanggal")
    @Expose
    private String tgl_panen;

    @SerializedName("kg")
    @Expose
    private double berat;

    @SerializedName("ekor")
    @Expose
    private int ekor;

    @SerializedName("nopol")
    @Expose
    private String nopol;

    @SerializedName("sopir")
    @Expose
    private String sopir;

    @SerializedName("mitra")
    @Expose
    private String mitra;

    @SerializedName("alamat_farm")
    @Expose
    private String alamat;

    @SerializedName("kandang")
    @Expose
    private String kandang;

    @SerializedName("jam_brngkt")
    @Expose
    private String berangkat;

    @SerializedName("jam_tiba_farm")
    @Expose
    private String tiba_farm;

    @SerializedName("mulai_panen")
    @Expose
    private String mulai_panen;

    @SerializedName("selesai_panen")
    @Expose
    private String selesai_panen;

    @SerializedName("jam_tiba_rpa")
    @Expose
    private String tiba_rpa;

    @SerializedName("jam_siap_potong")
    @Expose
    private String potong;

    @Ignore
    private List<Tara> taras;

    @Ignore
    private List<Weigh> weighs;

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

    public String getNoreg() {
        return noreg;
    }

    public void setNoreg(String noreg) {
        this.noreg = noreg;
    }

    public String getRit() {
        return rit;
    }

    public void setRit(String rit) {
        this.rit = rit;
    }

    public String getNik_tim() {
        return nik_tim;
    }

    public void setNik_tim(String nik_tim) {
        this.nik_tim = nik_tim;
    }

    public String getNama_tim() {
        return nama_tim;
    }

    public void setNama_tim(String nama_tim) {
        this.nama_tim = nama_tim;
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

    public String getTgl_panen() {
        return tgl_panen;
    }

    public void setTgl_panen(String tgl_panen) {
        this.tgl_panen = tgl_panen;
    }

    public String getNopol() {
        return nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public String getSopir() {
        return sopir;
    }

    public void setSopir(String sopir) {
        this.sopir = sopir;
    }

    public String getMitra() {
        return mitra;
    }

    public void setMitra(String mitra) {
        this.mitra = mitra;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKandang() {
        return kandang;
    }

    public void setKandang(String kandang) {
        this.kandang = kandang;
    }

    public String getBerangkat() {
        return berangkat;
    }

    public void setBerangkat(String berangkat) {
        this.berangkat = berangkat;
    }

    public String getTiba_farm() {
        return tiba_farm;
    }

    public void setTiba_farm(String tiba_farm) {
        this.tiba_farm = tiba_farm;
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

    public String getTiba_rpa() {
        return tiba_rpa;
    }

    public void setTiba_rpa(String tiba_rpa) {
        this.tiba_rpa = tiba_rpa;
    }

    public String getPotong() {
        return potong;
    }

    public void setPotong(String potong) {
        this.potong = potong;
    }

    public List<Tara> getTaras() {
        return taras;
    }

    public void setTaras(List<Tara> taras) {
        this.taras = taras;
    }

    public List<Weigh> getWeighs() {
        return weighs;
    }

    public void setWeighs(List<Weigh> weighs) {
        this.weighs = weighs;
    }
}
