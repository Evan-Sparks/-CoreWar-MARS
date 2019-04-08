package com.sparks.corewar.mars94.ringmemory;

import com.sparks.corewar.Operand;

import java.util.Objects;

public class RingMemOperand implements Operand {
    private final int data;
    private final String mod;

    public RingMemOperand(int data, String mod) {
        this.data = data;
        this.mod = mod;
    }

    @Override
    public int number() {
        return data;
    }

    @Override
    public String addressMode() {
        return mod;
    }

    @Override
    public String toString() {
        return (mod == null ? "" : mod) + data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RingMemOperand that = (RingMemOperand) o;
        return data == that.data &&
                Objects.equals(mod, that.mod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, mod);
    }
}
