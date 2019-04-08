package com.sparks.corewar.mars94;

import com.sparks.corewar.*;

import java.util.List;

public class MachineImpl implements Machine {
    private final Memory memory;
    private final Processor processor;
    private boolean isTerminated = false;

    public MachineImpl(Memory memory, Processor processor) {
        this.memory = memory;
        this.processor = processor;
    }

    public void step() {
        Bus bus = new BusImpl(memory);
        if (!isTerminated) {
            processor.step(bus);
            if (bus.isTerminated()) {
                terminate();
            }
        } else {
            throw new MachineTerminatedException();
        }
    }

    @Override
    public Memory getMemory() {
        return memory;
    }

    @Override
    public Processor getProcessor() {
        return processor;
    }

    public boolean isTerminated() {
        return this.isTerminated;
    }

    public void terminate() {
        this.isTerminated = true;
    }

    @Override
    public void loadWarrior(String playerName, List<MemoryCell> instructions, int loadOffset, int threadStartIndex) {
        if (loadOffset < 0 || instructions.size() < threadStartIndex) {
            throw new RuntimeException("Invalid Warrior");
        }
        for (int i = 0; i < instructions.size(); i++) {
            memory.set(i + loadOffset, instructions.get(i));
        }
        ThreadQueueImpl queue = new ThreadQueueImpl(playerName, loadOffset + threadStartIndex);
        processor.addPlayerTaskQueue(queue);
    }
}
