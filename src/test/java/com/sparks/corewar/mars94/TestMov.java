package com.sparks.corewar.mars94;

import com.sparks.corewar.Machine;
import org.junit.jupiter.api.Test;

import static com.sparks.corewar.mars94.TestHelpers.*;

class TestMov {
    @Test
    void testMovDirectI() {
        Machine machine = initMov("I", 1, "$", 2, "$");
        addDat(machine, 2, 3,4);
        machine.step();
        validateNumberNonempty(machine, 3);
        validateEquals(machine, "DAT", "I", 3, "#", 4, "#", 2, 3);
    }

    @Test
    void testMovDirectF() {
        Machine machine = initMov("F", 1, "$", 2, "$");
        addDat(machine, 2, 3,4);
        machine.step();
        validateNumberNonempty(machine, 3);

        validateEquals(machine, "DAT", "I", 3, "#", 4, "#", 2);
        validateEquals(machine, "DAT", "F", 3, "#", 4, "#", 3);
    }

    @Test
    void testMovDirectX() {
        Machine machine = initMov("X", 1, "$", 2, "$");
        addDat(machine, 2, 3,4);
        machine.step();
        validateNumberNonempty(machine, 3);

        validateEquals(machine, "DAT", "I", 3, "#", 4, "#", 2);
        validateEquals(machine, "DAT", "F", 4, "#", 3, "#", 3);
    }

    @Test
    void testMovPredecIndirect() {
        Machine machine = initMov("F", 1, "<", 2, "<");
        addDat(machine, 2, 3,4);
        addDat(machine, 3, 5,6);
        addDat(machine, 5, 7,8);

        machine.step();
        validateNumberNonempty(machine, 5);

        validateEquals(machine, "DAT", "I", 3, "#", 3, "#", 2);
        validateEquals(machine, "DAT", "I", 5, "#", 5, "#", 3);
        validateEquals(machine, "DAT", "I", 7, "#", 8, "#", 5);
        validateEquals(machine, "DAT", "F", 7, "#", 8, "#", 8);
    }

    @Test
    void testMovPostincIndirect() {
        Machine machine = initMov("F", 1, ">", 2, ">");
        addDat(machine, 2, 3,4);
        addDat(machine, 3, 5,6);
        addDat(machine, 6, 7,8);

        machine.step();
        validateNumberNonempty(machine, 5);

        validateEquals(machine, "DAT", "I", 3, "#", 5, "#", 2);
        validateEquals(machine, "DAT", "I", 5, "#", 7, "#", 3);
        validateEquals(machine, "DAT", "I", 7, "#", 8, "#", 6);
        validateEquals(machine, "DAT", "F", 7, "#", 8, "#", 9);
    }

    private Machine initMov(String mod, int data1, String add1, int data2, String add2) {
        return initWithInst("MOV", mod, data1, add1, data2, add2);
    }
}
