package com.sashafiesta.finances.command.decorator;

import com.sashafiesta.finances.command.Command;

public class TimerDecorator implements Command {
    private Command command;
    public TimerDecorator(Command command) {
        this.command = command;
    }

    @Override
    public boolean execute() {
        long startTime = System.nanoTime();
        boolean result = command.execute();
        long endTime = System.nanoTime();
        System.out.println("Команда " + command.getName()  + System.lineSeparator() + (result ? "завершилась успешно" : "провалена") + " за " + (endTime - startTime) / 1000000 + " мс.");
        return result;
    }

    @Override
    public String getName() {
        return command.getName();
    }
}
