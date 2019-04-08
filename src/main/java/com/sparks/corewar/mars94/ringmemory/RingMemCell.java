package com.sparks.corewar.mars94.ringmemory;

import com.sparks.corewar.Operand;
import com.sparks.corewar.MemoryCell;
import com.sparks.corewar.Opcode;

import java.util.Objects;

public class RingMemCell implements MemoryCell {
    private final Opcode opcode;
    private final Operand aOperand;
    private final Operand bOperand;

    public RingMemCell() {
        this.opcode = new RingMemOpcode("DAT", "F");
        this.aOperand = new RingMemOperand(0, "#");
        this.bOperand = new RingMemOperand(0, "#");
    }

    public RingMemCell(RingMemOpcode opcode, RingMemOperand aOperand, RingMemOperand bOperand) {
        this.opcode = opcode;
        this.aOperand = aOperand;
        this.bOperand = bOperand;
    }

    public RingMemCell(String opcode, String mod, int val1, String ad1, int val2, String ad2) {
        this(new RingMemOpcode(opcode, mod), new RingMemOperand(val1, ad1), new RingMemOperand(val2, ad2));
    }

    public RingMemCell(MemoryCell oldCell, int aNum, int bNum) {
        this.opcode = oldCell.getOpcode();
        this.aOperand = new RingMemOperand(aNum, oldCell.getAOperand().addressMode());
        this.bOperand = new RingMemOperand(bNum, oldCell.getBOperand().addressMode());
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public Operand getAOperand() {
        return aOperand;
    }

    public Operand getBOperand() {
        return bOperand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RingMemCell that = (RingMemCell) o;
        return Objects.equals(opcode, that.opcode) &&
                Objects.equals(aOperand, that.aOperand) &&
                Objects.equals(bOperand, that.bOperand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opcode, aOperand, bOperand);
    }

    @Override
    public String toString() {
        return opcode.toString() + " " + aOperand.toString() + " " + bOperand.toString();
    }
}
