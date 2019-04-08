package com.sparks.corewar;

public interface ProcessThread {
    String processThreadId();
    int currentTaskPointer();
    //void setCurrentTaskPointer(int index);
    void updateTaskPointer(int offset);
}
