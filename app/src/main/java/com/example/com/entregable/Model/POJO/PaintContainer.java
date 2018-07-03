package com.example.com.entregable.Model.POJO;

import java.util.List;

/**
 * Created by DH on 3/7/2018.
 */

public class PaintContainer {
    private List<Paint> paints;

    public PaintContainer(List<Paint> paints) {
        this.paints = paints;
    }

    public List<Paint> getPaints() {
        return paints;
    }

    public void setPaints(List<Paint> paints) {
        this.paints = paints;
    }
}
