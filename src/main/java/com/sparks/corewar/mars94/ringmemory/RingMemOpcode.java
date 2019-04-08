package com.sparks.corewar.mars94.ringmemory;

import com.sparks.corewar.Opcode;

import java.util.Objects;

public class RingMemOpcode implements Opcode {
    private final String opcode;
    private final String modifier;

    public RingMemOpcode(String opcode, String modifier) {
        this.opcode = opcode;
        this.modifier = modifier;
    }

    @Override
    public String type() {
        return opcode;
    }

    @Override
    public String modifier() {
        return modifier;
    }

    @Override
    public String toString() {
        return opcode + "." + modifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RingMemOpcode that = (RingMemOpcode) o;
        return Objects.equals(opcode, that.opcode) &&
                Objects.equals(modifier, that.modifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(opcode, modifier);
    }
}
