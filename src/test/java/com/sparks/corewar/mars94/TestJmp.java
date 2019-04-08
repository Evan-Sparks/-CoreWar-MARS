package com.sparks.corewar.mars94;

import com.sparks.corewar.Machine;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.sparks.corewar.mars94.TestHelpers.*;

class TestJmp {
    @Test
    void testJmp() {
        for (String mod: List.of("A", "B", "AB", "BA", "F", "X", "I")) {
            Machine machine = initWithInst("JMP", mod, 1, "@", 2, "#");
            addInstruction(machine, 2, "DAT", "I", "#", 3, "#", 4);
            addInstruction(machine, 6, "ADD", "F", "#", 5, "#", 6);

            machine.step();
            validateNumberNonempty(machine, 3);
            validateEquals(machine, "ADD", "F", 5, "#", 6, "#", 6);

            machine.step();
            validateNumberNonempty(machine, 3);
            validateEquals(machine, "ADD", "F", 10, "#", 12, "#", 6);
        }
    }

    @Test
    void testJmz() {
        //Nonzero
        noJmpHelper("JMZ", "F", -1);
        noJmpHelper("JMZ", "F", 0);
        noJmpHelper("JMZ", "F", 3);
        noJmpHelper("JMZ", "F", 4);
        noJmpHelper("JMZ", "I", 0);
        noJmpHelper("JMZ", "I", 3);
        noJmpHelper("JMZ", "I", 4);
        noJmpHelper("JMZ", "X", 0);
        noJmpHelper("JMZ", "X", 3);
        noJmpHelper("JMZ", "X", 4);
        noJmpHelper("JMZ", "B", 3);
        noJmpHelper("JMZ", "A", 4);
        noJmpHelper("JMZ", "AB", 3);
        noJmpHelper("JMZ", "BA", 4);

        //Zero
        condJumpHelper("JMZ", "F", 2);
        condJumpHelper("JMZ", "I", 2);
        condJumpHelper("JMZ", "A", 3);
        condJumpHelper("JMZ", "B", 4);
        condJumpHelper("JMZ", "BA", 3);
        condJumpHelper("JMZ", "AB", 4);
    }

    @Test
    void testJmn() {
        //Nonzero
        condJumpHelper("JMN", "F", -1);
        condJumpHelper("JMN", "B", 3);
        condJumpHelper("JMN", "A", 4);
        condJumpHelper("JMN", "AB", 3);
        condJumpHelper("JMN", "BA", 4);
        condJumpHelper("JMN", "X", -1);
        condJumpHelper("JMN", "I", -1);

        //not nonzero
        noJmpHelper("JMN", "F", 2);
        noJmpHelper("JMN", "I", 2);
        noJmpHelper("JMN", "A", 3);
        noJmpHelper("JMN", "B", 4);
        noJmpHelper("JMN", "BA", 3);
        noJmpHelper("JMN", "AB", 4);
        noJmpHelper("JMN", "F", 0);
        noJmpHelper("JMN", "F", 3);
        noJmpHelper("JMN", "F", 4);
        noJmpHelper("JMN", "I", 0);
        noJmpHelper("JMN", "I", 3);
        noJmpHelper("JMN", "I", 4);
        noJmpHelper("JMN", "X", 0);
        noJmpHelper("JMN", "X", 3);
        noJmpHelper("JMN", "X", 4);
    }

    @Test
    void testDjn() {
        // is nonzero, jump to nondecremented
        Machine machine = initDjn("F", 5, "$", -1, "$");
        validateEquals(machine, "ADD", "F", -2, "#",-3, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 2);
        validateEquals(machine, "ADD", "F", 5, "#",6, "#", 6);
        validateEquals(machine, "ADD", "F", 7, "#",8, "#", 10);
        machine.step();
        validateEquals(machine, "ADD", "F", -2, "#",-3, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 2);
        validateEquals(machine, "ADD", "F", 10, "#",12, "#", 6);
        validateEquals(machine, "ADD", "F", 7, "#",8, "#", 10);

        // nonzero indirect through decremented
        machine = initDjn("F", -1, "@", -1, "$");
        validateEquals(machine, "ADD", "F", -2, "#",-3, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 2);
        validateEquals(machine, "ADD", "F", 5, "#",6, "#", 6);
        validateEquals(machine, "ADD", "F", 7, "#",8, "#", 10);
        machine.step();
        validateEquals(machine, "ADD", "F", -2, "#",-3, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 2);
        validateEquals(machine, "ADD", "F", 5, "#",6, "#", 6);
        validateEquals(machine, "ADD", "F", 14, "#",16, "#", 10);

        // Zero
        machine = initDjn("B", 5, "$", 3, "$");
        validateEquals(machine, "ADD", "F", -1, "#",-2, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",0, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 2);
        validateEquals(machine, "ADD", "F", 5, "#",6, "#", 6);
        validateEquals(machine, "ADD", "F", 7, "#",8, "#", 10);
        machine.step();
        validateEquals(machine, "ADD", "F", -1, "#",-2, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",0, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 6, "#",8, "#", 2);
        validateEquals(machine, "ADD", "F", 5, "#",6, "#", 6);
        validateEquals(machine, "ADD", "F", 7, "#",8, "#", 10);

        //zero
        machine = initDjn("A", 5, "$", 4, "$");
        validateEquals(machine, "ADD", "F", -1, "#",-2, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 0, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 2);
        validateEquals(machine, "ADD", "F", 5, "#",6, "#", 6);
        validateEquals(machine, "ADD", "F", 7, "#",8, "#", 10);
        machine.step();
        validateEquals(machine, "ADD", "F", -1, "#",-2, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 0, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 6, "#",8, "#", 2);
        validateEquals(machine, "ADD", "F", 5, "#",6, "#", 6);
        validateEquals(machine, "ADD", "F", 7, "#",8, "#", 10);

        // predecrement
        machine = initDjn("B", 5, "<", 6, "<");
        validateEquals(machine, "ADD", "F", -1, "#",-2, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 2);
        validateEquals(machine, "ADD", "F", 5, "#",5, "#", 6);
        validateEquals(machine, "DAT", "I", 2, "#",1, "#", 7);
        validateEquals(machine, "DAT", "F", 0, "#",-1, "#", 8);
        validateEquals(machine, "ADD", "F", 7, "#",8, "#", 10);
        validateEquals(machine, "ADD", "F", 9, "#",10, "#", 11);
        machine.step();
        validateEquals(machine, "ADD", "F", -1, "#",-2, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 2);
        validateEquals(machine, "ADD", "F", 5, "#",5, "#", 6);
        validateEquals(machine, "DAT", "I", 2, "#",1, "#", 7);
        validateEquals(machine, "DAT", "F", 0, "#",-1, "#", 8);
        validateEquals(machine, "ADD", "F", 7, "#",8, "#", 10);
        validateEquals(machine, "ADD", "F", 18, "#",20, "#", 11);
    }

    /*
    addInstruction(machine, 2, "ADD", "F", "#", 1, "#", 2);
        addInstruction(machine, 3, "ADD", "F", "#", 3, "#", 4);

        addDat(machine, 4, 0, 1);
        addDat(machine, 5, 1, 0);
        addDat(machine, 6, 3, 4);
        addDat(machine, 7, 3, 4);
     */
    @Test
    void testCmp() {
        compareSat("CMP", "F", 5, "$", 6, "$");
        compareUnsat("CMP", "F", 3, "$", 4, "$");
    }

    @Test
    void testSlt() {
        compareSat("SLT", "A", 3, "$", 4, "$");
        compareUnsat("SLT", "A", 4, "$", 3, "$");
    }

    private void noJmpHelper(String opcode, String mod, int bNum) {
        Machine machine = initHelper(opcode, mod, bNum);
        validateNumberNonempty(machine, 6);
        validateEquals(machine, "DAT", "I", -1, "#",-2, "#", 0);
        validateEquals(machine, "ADD", "F", 6, "#",8, "#", 2);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 5, "#",6, "#", 6);
    }


    private void condJumpHelper(String opcode, String mod, int bNum) {
        Machine machine = initHelper(opcode, mod, bNum);
        validateNumberNonempty(machine, 6);
        validateEquals(machine, "DAT", "I", -1, "#",-2, "#", 0);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 2);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 10, "#",12, "#", 6);
    }

    private Machine initHelper(String opcode, String mod, int bNum) {
        Machine machine = initWithInst(opcode, mod, 5, "$", bNum, "$");
        addDat(machine, 0, -1, -2);
        addDat(machine, 4, 0, 1);
        addDat(machine, 5, 1, 0);

        addInstruction(machine, 2, "ADD", "F", "#", 3, "#", 4);
        addInstruction(machine, 6, "ADD", "F", "#", 5, "#", 6);
        machine.step();
        validateNumberNonempty(machine, 6);

        validateEquals(machine, "DAT", "I", -1, "#",-2, "#", 0);
        validateEquals(machine, "DAT", "I", 0, "#",1, "#", 4);
        validateEquals(machine, "DAT", "I", 1, "#",0, "#", 5);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 2);
        validateEquals(machine, "ADD", "F", 5, "#",6, "#", 6);
        machine.step();
        return machine;
    }

    private Machine initDjn(String mod, int aNum, String aAdd, int bNum, String bMod) {
        Machine machine = initWithInst("DJN", mod, aNum, aAdd, bNum, bMod);
        addInstruction(machine, 0, "ADD", "F", "#", -1, "#", -2);
        addDat(machine, 4, 0, 1);
        addDat(machine, 5, 1, 0);
        addDat(machine, 7, 2, 2);

        addInstruction(machine, 2, "ADD", "F", "#", 3, "#", 4);
        addInstruction(machine, 6, "ADD", "F", "#", 5, "#", 6);
        addInstruction(machine, 10, "ADD", "F", "#", 7, "#", 8);
        addInstruction(machine, 11, "ADD", "F", "#", 9, "#", 10);

        machine.step();
        return machine;
    }

    private Machine initComparison(String opcode, String mod, int aNum, String aAdd, int bNum, String bMod) {
        Machine machine = initWithInst(opcode, mod, aNum, aAdd, bNum, bMod);
        addInstruction(machine, 2, "ADD", "F", "#", 1, "#", 2);
        addInstruction(machine, 3, "ADD", "F", "#", 3, "#", 4);

        addDat(machine, 4, 0, 1);
        addDat(machine, 5, 1, 0);
        addDat(machine, 6, 3, 4);
        addDat(machine, 7, 3, 4);
        return machine;
    }

    private void compareSat(String opcode, String mod, int aNum, String aAdd, int bNum, String bMod) {
        Machine machine = initComparison(opcode, mod, aNum, aAdd, bNum, bMod);
        machine.step();
        validateEquals(machine, "ADD", "F", 1, "#",2, "#", 2);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 3);
        machine.step();
        validateEquals(machine, "ADD", "F", 1, "#",2, "#", 2);
        validateEquals(machine, "ADD", "F", 6, "#",8, "#", 3);
    }

    private void compareUnsat(String opcode, String mod, int aNum, String aAdd, int bNum, String bMod) {
        Machine machine = initComparison(opcode, mod, aNum, aAdd, bNum, bMod);
        machine.step();
        validateEquals(machine, "ADD", "F", 1, "#",2, "#", 2);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 3);
        machine.step();
        validateEquals(machine, "ADD", "F", 2, "#",4, "#", 2);
        validateEquals(machine, "ADD", "F", 3, "#",4, "#", 3);
    }
}
