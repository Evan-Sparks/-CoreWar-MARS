package com.sparks.corewar;

public interface Bus {
    MemoryCell getFromMemory(int offset);
    void writeToMemory(int offset, MemoryCell cell);
    void reportTermination();
    boolean isTerminated();
}
