package com.sparks.corewar.mars94.ringmemory;

import com.sparks.corewar.Memory;
import com.sparks.corewar.MemoryCell;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RingMemory implements Memory {
    private final MemoryCell[] contents;
    private final int size;

    public RingMemory(int size) {
        this.size = size;
        this.contents = new MemoryCell[size];
    }

    public MemoryCell get(int offset) {
        MemoryCell cell = contents[Math.floorMod(offset, size)];//offset % size];
        if (cell == null) {
            cell = new RingMemCell();
        }
        return cell;
    }

    public void set(int offset, MemoryCell cell) {
        contents[Math.floorMod(offset, size)] = cell;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return Stream.of(contents).map(e -> (e == null ? new RingMemCell().toString() : e.toString())).collect(Collectors.joining("\n"));
    }
}
