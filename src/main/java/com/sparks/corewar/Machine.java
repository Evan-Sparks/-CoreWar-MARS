package com.sparks.corewar;

import java.util.List;

public interface Machine {
    void step();
    Memory getMemory();
    Processor getProcessor();
    boolean isTerminated();
    void terminate();
    void loadWarrior(String playerName, List<MemoryCell> instructions, int loadOffset, int threadStartIndex);
}
