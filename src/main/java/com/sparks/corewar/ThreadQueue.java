package com.sparks.corewar;

public interface ThreadQueue {
    String getPlayerName();
    ProcessThread getProcessThread();
    void newProcessThread(int initialOffset);
    boolean isEmpty();
    void removeProcessThread(ProcessThread thread);
}
