package com.sparks.corewar.mars94;

import com.sparks.corewar.ThreadQueue;

import java.util.LinkedList;
import java.util.Queue;

public class ProgramQueue {
    private final Queue<ThreadQueue> queues = new LinkedList<>();

    public void addPlayerTaskQueue(ThreadQueue queue) {
        queues.add(queue);
    }

    public ThreadQueue getNextQueue() {
        ThreadQueue queue = queues.poll();
        queues.add(queue);
        return queue;
    }

    public void removeQueue(ThreadQueue queue) {
        queues.remove(queue);
    }

    public int getProgramCount() {
        return queues.size();
    }

    @Override
    public String toString() {
        return "ProgramQueue{" +
                "queues=" + queues +
                '}';
    }
}
