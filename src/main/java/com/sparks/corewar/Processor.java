package com.sparks.corewar;

public interface Processor {
    void step(Bus bus);
    void addPlayerTaskQueue(ThreadQueue threadQueue);
}
