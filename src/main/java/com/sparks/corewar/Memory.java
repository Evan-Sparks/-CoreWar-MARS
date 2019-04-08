package com.sparks.corewar;

public interface Memory {
    MemoryCell get(int offset);
    void set(int offset, MemoryCell cell);
    int getSize();
}
