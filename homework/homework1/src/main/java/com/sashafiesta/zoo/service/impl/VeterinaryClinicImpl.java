package com.sashafiesta.zoo.service.impl;

import com.sashafiesta.zoo.service.IVeterinaryClinic;
import com.sashafiesta.zoo.animal.Animal;

public class VeterinaryClinicImpl implements IVeterinaryClinic {
    @Override
    public boolean isHealthy(Animal animal) {
        return animal.isHealthy();
    }
}
