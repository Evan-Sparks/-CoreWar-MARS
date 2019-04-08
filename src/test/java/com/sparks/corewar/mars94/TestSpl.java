package com.sparks.corewar.mars94;

import com.sparks.corewar.Machine;
import com.sparks.corewar.MemoryCell;
import com.sparks.corewar.ProcessThread;
import com.sparks.corewar.ThreadQueue;
import com.sparks.corewar.mars94.ringmemory.RingMemCell;
import com.sparks.corewar.mars94.ringmemory.RingMemOpcode;
import com.sparks.corewar.mars94.ringmemory.RingMemOperand;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static com.sparks.corewar.mars94.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.*;

class TestSpl {
    @Test
    void TestTerminate() {
        Machine machine = initWithInst("DAT", "I", 1, "#", 2, "#");
        machine.step();
        assertTrue(machine.isTerminated());
    }

    @Test
    void testMultiplePrograms() throws NoSuchFieldException, IllegalAccessException {
        Machine machine = initMultiple("ADD", 1, "#");

        validateNumberNonempty(machine, 2);
        validateEquals(machine, "ADD", "I", 1, "#", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 3, "#", 4, "#", 5);
        assertEquals("testName", nextProgramId(machine));
        machine.step();
        validateNumberNonempty(machine, 2);
        validateEquals(machine, "ADD", "I", 2, "#", 4, "#", 1);
        validateEquals(machine, "ADD", "I", 3, "#", 4, "#", 5);
        assertEquals("testName2", nextProgramId(machine));
        machine.step();
        validateNumberNonempty(machine, 2);
        validateEquals(machine, "ADD", "I", 2, "#", 4, "#", 1);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        assertEquals("testName", nextProgramId(machine));
        machine.step();
        validateEquals(machine, "ADD", "I", 2, "#", 4, "#", 1);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        assertEquals("testName2", nextProgramId(machine));
        machine.step();
        validateEquals(machine, "ADD", "I", 2, "#", 4, "#", 1);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        assertThrows(MachineTerminatedException.class, machine::step);
    }

    @Test
    void testSpl() throws NoSuchFieldException, IllegalAccessException {
        Machine machine = initMultiple("SPL", 2, "$");
        addToMachine(machine, "ADD", "I", 5, "#", 6, "#", 2);
        addToMachine(machine, "ADD", "I", 7, "#", 8, "#", 3);
        addToMachine(machine, "ADD", "I", 9, "#", 10, "#", 6);


        validateNumberNonempty(machine, 5);
        validateEquals(machine, "SPL", "I", 2, "$", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 5, "#", 6, "#", 2);
        validateEquals(machine, "ADD", "I", 7, "#", 8, "#", 3);
        validateEquals(machine, "ADD", "I", 3, "#", 4, "#", 5);
        validateEquals(machine, "ADD", "I", 9, "#", 10, "#", 6);

        assertEquals("testName", nextProgramId(machine));
        assertEquals("testName-0", nextThreadId(machine));
        machine.step(); // 1
        validateNumberNonempty(machine, 5);
        validateEquals(machine, "SPL", "I", 2, "$", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 5, "#", 6, "#", 2);
        validateEquals(machine, "ADD", "I", 7, "#", 8, "#", 3);
        validateEquals(machine, "ADD", "I", 3, "#", 4, "#", 5);
        validateEquals(machine, "ADD", "I", 9, "#", 10, "#", 6);

        assertEquals("testName2", nextProgramId(machine));
        assertEquals("testName2-0", nextThreadId(machine));
        machine.step(); // 2
        validateNumberNonempty(machine, 5);
        validateEquals(machine, "SPL", "I", 2, "$", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 5, "#", 6, "#", 2);
        validateEquals(machine, "ADD", "I", 7, "#", 8, "#", 3);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        validateEquals(machine, "ADD", "I", 9, "#", 10, "#", 6);


        assertEquals("testName", nextProgramId(machine));
        assertEquals("testName-0", nextThreadId(machine));
        machine.step(); // 3
        validateNumberNonempty(machine, 5);
        validateEquals(machine, "SPL", "I", 2, "$", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 10, "#", 12, "#", 2);
        validateEquals(machine, "ADD", "I", 7, "#", 8, "#", 3);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        validateEquals(machine, "ADD", "I", 9, "#", 10, "#", 6);

        assertEquals("testName2", nextProgramId(machine));
        assertEquals("testName2-0", nextThreadId(machine));
        machine.step(); // 4
        validateNumberNonempty(machine, 5);
        validateEquals(machine, "SPL", "I", 2, "$", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 10, "#", 12, "#", 2);
        validateEquals(machine, "ADD", "I", 7, "#", 8, "#", 3);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        validateEquals(machine, "ADD", "I", 18, "#", 20, "#", 6);

        assertEquals("testName", nextProgramId(machine));
        assertEquals("testName-1", nextThreadId(machine));
        machine.step(); // 5
        validateNumberNonempty(machine, 5);
        validateEquals(machine, "SPL", "I", 2, "$", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 10, "#", 12, "#", 2);
        validateEquals(machine, "ADD", "I", 14, "#", 16, "#", 3);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        validateEquals(machine, "ADD", "I", 18, "#", 20, "#", 6);

        assertEquals("testName2", nextProgramId(machine));
        assertEquals("testName2-0", nextThreadId(machine));
        machine.step(); // 6
        validateNumberNonempty(machine, 5);
        validateEquals(machine, "SPL", "I", 2, "$", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 10, "#", 12, "#", 2);
        validateEquals(machine, "ADD", "I", 14, "#", 16, "#", 3);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        validateEquals(machine, "ADD", "I", 18, "#", 20, "#", 6);

        assertEquals("testName", nextProgramId(machine));
        assertEquals("testName-0", nextThreadId(machine));
        machine.step(); // 7
        validateNumberNonempty(machine, 5);
        validateEquals(machine, "SPL", "I", 2, "$", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 10, "#", 12, "#", 2);
        validateEquals(machine, "ADD", "I", 28, "#", 32, "#", 3);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        validateEquals(machine, "ADD", "I", 18, "#", 20, "#", 6);

        assertEquals("testName", nextProgramId(machine));
        assertEquals("testName-1", nextThreadId(machine));
        machine.step(); //8
        validateNumberNonempty(machine, 5);
        validateEquals(machine, "SPL", "I", 2, "$", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 10, "#", 12, "#", 2);
        validateEquals(machine, "ADD", "I", 28, "#", 32, "#", 3);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        validateEquals(machine, "ADD", "I", 18, "#", 20, "#", 6);

        machine.step(); //9
        validateNumberNonempty(machine, 5);
        validateEquals(machine, "SPL", "I", 2, "$", 2, "#", 1);
        validateEquals(machine, "ADD", "I", 10, "#", 12, "#", 2);
        validateEquals(machine, "ADD", "I", 28, "#", 32, "#", 3);
        validateEquals(machine, "ADD", "I", 6, "#", 8, "#", 5);
        validateEquals(machine, "ADD", "I", 18, "#", 20, "#", 6);

        assertThrows(MachineTerminatedException.class, machine::step);
    }

    private Machine initMultiple(String opcode, int data1, String mod1) {
        Machine machine = initWithInst(opcode, "I", data1, mod1, 2, "#");

        List<MemoryCell> cells = new ArrayList<>();
        MemoryCell cell = new RingMemCell(new RingMemOpcode("ADD", "I"), new RingMemOperand(3, "#"),
                new RingMemOperand(4, "#"));
        cells.add(cell);

        machine.loadWarrior("testName2", cells, 5, 0);

        return machine;
    }

    private Machine addToMachine(Machine machine, String opcode, String mod, int data1, String add1, int data2, String add2, int offset) {
        MemoryCell cell = new RingMemCell(new RingMemOpcode(opcode, mod), new RingMemOperand(data1, add1),
                new RingMemOperand(data2, add2));
        machine.getMemory().set(offset, cell);
        return machine;
    }

    private String nextProgramId(Machine machine) throws NoSuchFieldException, IllegalAccessException {
        return getThreadQueue(machine).peek().getPlayerName();
    }

    private String nextThreadId(Machine machine) throws NoSuchFieldException, IllegalAccessException {
        ThreadQueueImpl threadQueue = (ThreadQueueImpl) getThreadQueue(machine).peek();

        Field queueField = ThreadQueueImpl.class.getDeclaredField("processThreadQueue");
        queueField.trySetAccessible();
        Queue<ProcessThread> queue = (Queue<ProcessThread>) queueField.get(threadQueue);
        return queue.peek().processThreadId();
    }

    private Queue<ThreadQueue> getThreadQueue(Machine machine) throws NoSuchFieldException, IllegalAccessException {
        ProcessorImpl processor = (ProcessorImpl) machine.getProcessor();
        Field programQueueField;
        programQueueField = ProcessorImpl.class.getDeclaredField("programQueue");
        programQueueField.trySetAccessible();
        ProgramQueue programQueue = (ProgramQueue) programQueueField.get(processor);

        Field threadQueueField = ProgramQueue.class.getDeclaredField("queues");
        threadQueueField.trySetAccessible();
        return (Queue<ThreadQueue>) threadQueueField.get(programQueue);
    }
}
