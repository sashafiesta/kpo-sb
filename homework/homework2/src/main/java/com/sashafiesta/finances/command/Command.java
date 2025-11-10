package com.sashafiesta.finances.command;

public interface Command {
    boolean execute();
    String getName();
}
