package com.sashafiesta.zoo;

import com.sashafiesta.zoo.cli.CLIRunner;
import com.sashafiesta.zoo.di.DIManager;
import com.sashafiesta.zoo.service.IZoo;

public class Main {
    public static void main(String[] args) {
        DIManager diManager = new DIManager();
        diManager.init();
        IZoo zoo = diManager.getInstance(IZoo.class);
        CLIRunner.run(zoo);
    }
}