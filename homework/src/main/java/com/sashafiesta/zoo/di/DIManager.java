package com.sashafiesta.zoo.di;
import com.sashafiesta.zoo.service.IVeterinaryClinic;
import com.sashafiesta.zoo.service.IZoo;
import com.sashafiesta.zoo.service.impl.VeterinaryClinicImpl;
import com.sashafiesta.zoo.service.impl.ZooImpl;

public class DIManager {
    private DIContainer container;

    public DIManager() {
        container = new DIContainer();
    }
    public void init() {
        container.register(IVeterinaryClinic.class, VeterinaryClinicImpl.class);
        container.register(IZoo.class, ZooImpl.class);
    }
    public <T> T getInstance(Class<T> type) {
        return container.getInstance(type);
    }
}
