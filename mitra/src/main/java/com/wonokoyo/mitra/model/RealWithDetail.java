package com.wonokoyo.mitra.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RealWithDetail {
    @Embedded
    private Real real;

    @Relation(parentColumn = "no_do", entityColumn = "nodo_real", entity = Tara.class)
    private List<Tara> taras;

    @Relation(parentColumn = "no_do", entityColumn = "nodo_real", entity = Weigh.class)
    private List<Weigh> weighs;

    public Real getReal() {
        return real;
    }

    public void setReal(Real real) {
        this.real = real;
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
