package com.sparks.corewar.mars94;

import com.sparks.corewar.ThreadQueue;
import com.sparks.corewar.ProcessThread;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadQueueImpl implements ThreadQueue {
    private final String playerName;
    private final Queue<ProcessThread> processThreadQueue = new LinkedList<>();
    private int threadCount = 0;

    public ThreadQueueImpl(String playerName, int initialOffset) {
        this.playerName = playerName;
        newProcessThread(initialOffset);
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public ProcessThread getProcessThread() {
        ProcessThread thread = processThreadQueue.poll();
        if (thread != null) {
            processThreadQueue.add(thread);
        }

        return thread;
    }

    @Override
    public void newProcessThread(int initialOffset) {
        processThreadQueue.add(new ProcessThreadImpl(playerName + "-" + threadCount, initialOffset));
        threadCount++;
    }

    @Override
    public boolean isEmpty() {
        return processThreadQueue.isEmpty();
    }

    @Override
    public void removeProcessThread(ProcessThread thread) {
        processThreadQueue.remove(thread);
    }

    @Override
    public String toString() {
        return "ThreadQueueImpl{" +
                "playerName='" + playerName + '\'' +
                ", processThreadQueue=" + processThreadQueue +
                ", threadCount=" + threadCount +
                '}';
    }
}

