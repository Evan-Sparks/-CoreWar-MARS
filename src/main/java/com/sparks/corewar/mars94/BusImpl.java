package com.sparks.corewar.mars94;

import com.sparks.corewar.Bus;
import com.sparks.corewar.Memory;
import com.sparks.corewar.MemoryCell;

public class BusImpl implements Bus {
    private final Memory memory;
    private boolean isTerminated = false;

    public BusImpl(Memory memory) {
        this.memory = memory;
    }

    @Override
    public MemoryCell getFromMemory(int offset) {
        return memory.get(offset);
    }

    @Override
    public void writeToMemory(int offset, MemoryCell cell) {
        memory.set(offset, cell);
    }

    @Override
    public void reportTermination() {
        this.isTerminated = true;
    }

    @Override
    public boolean isTerminated() {
        return this.isTerminated;
    }
}
