package com.hitema.MaxAirain.APIHitemaprojectfacerecognisation.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Material {

    private String materialId;
    private String name;
    private int quantityA;
    private int quantityT;

    @Override
    public String toString() {
        return "Material{" +
                "materielId= " + materialId +
                " name='" + name + '\'' +
                " quantityA=" + quantityA +
                " quantityT=" + quantityT +
                '}';
    }

}
