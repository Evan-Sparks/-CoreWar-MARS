package com.sparks.corewar.mars94;

import com.sparks.corewar.Machine;
import com.sparks.corewar.Memory;
import com.sparks.corewar.MemoryCell;
import com.sparks.corewar.Processor;
import com.sparks.corewar.mars94.ringmemory.RingMemCell;
import com.sparks.corewar.mars94.ringmemory.RingMemOpcode;
import com.sparks.corewar.mars94.ringmemory.RingMemOperand;
import com.sparks.corewar.mars94.ringmemory.RingMemory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestHelpers {
    static void validateNumberNonempty(Machine machine, int expected) {
        MemoryCell empty = new RingMemCell();
        Memory memory = machine.getMemory();

        int count = 0;
        for (int i = 0; i < memory.getSize(); i++) {
            MemoryCell cell = memory.get(i);
            if (!empty.equals(cell)) {
                count++;
            }
        }
        assertEquals(expected, count, "Wrong number of modified cells");
    }

    static void addInstruction(Machine machine, int offset, String opcode, String mod, String ad1, int val1,
                               String ad2, int val2) {
        MemoryCell cell = new RingMemCell(new RingMemOpcode(opcode, mod),
                new RingMemOperand(val1, ad1), new RingMemOperand(val2, ad2));
        machine.getMemory().set(offset, cell);
    }

    static void addDat(Machine machine, int offset, int val1, int val2) {
        addInstruction(machine, offset, "DAT", "I", "#", val1, "#", val2);
    }

    static Machine initWithInst(String opcode, String mod, int data1, String add1, int data2, String add2) {
        Memory memory = new RingMemory(12);
        Processor processor = new ProcessorImpl(1);
        Machine machine = new MachineImpl(memory, processor);

        List<MemoryCell> cells = new ArrayList<>();
        MemoryCell cell = new RingMemCell(new RingMemOpcode(opcode, mod), new RingMemOperand(data1, add1),
                new RingMemOperand(data2, add2));
        cells.add(cell);

        machine.loadWarrior("testName", cells, 1, 0);
        return machine;
    }

    static void validateEquals(Machine machine, String opcode, String mod, int val1, String ad1, int val2, String ad2,
                               int... offsets) {
        MemoryCell cell = new RingMemCell(opcode, mod, val1, ad1, val2, ad2);
        for (int i: offsets) {
            assertEquals(cell, machine.getMemory().get(i), "Incorrect instruction at offset " + i);
        }
    }
}
