package com.sparks.corewar.mars94;

import com.sparks.corewar.ProcessThread;

public class ProcessThreadImpl implements ProcessThread {
    private final String id;
    private int taskPointer;

    public ProcessThreadImpl(String id, int taskPointer) {
        this.id = id;
        this.taskPointer = taskPointer;
    }

    @Override
    public String processThreadId() {
        return id;
    }

    @Override
    public int currentTaskPointer() {
        return taskPointer;
    }

    @Override
    public void updateTaskPointer(int offset) {
        this.taskPointer += offset;
    }

    @Override
    public String toString() {
        return "ProcessThreadImpl{" +
                "id='" + id + '\'' +
                ", taskPointer=" + taskPointer +
                '}';
    }
}
