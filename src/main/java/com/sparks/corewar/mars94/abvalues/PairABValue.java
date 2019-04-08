package com.sparks.corewar.mars94.abvalues;

import com.sparks.corewar.MemoryCell;
import com.sparks.corewar.mars94.ABValue;
import com.sparks.corewar.mars94.ringmemory.RingMemCell;

import java.util.function.BinaryOperator;

public class PairABValue implements ABValue {
    private final int valOne;
    private final int valTwo;
    private final String opcode;

    public PairABValue(int valOne, int valTwo, String opcode) {
        this.valOne = valOne;
        this.valTwo = valTwo;
        this.opcode = opcode;
    }

    @Override
    public boolean isZero() {
        return valOne == 0 && valTwo == 0;
    }

    @Override
    public boolean isNonZero() {
        return valOne != 0 && valTwo != 0;
    }

    @Override
    public boolean isLessThan(ABValue bValue) {
        assert bValue instanceof PairABValue;
        return this.valOne < ((PairABValue) bValue).valOne && this.valTwo < ((PairABValue) bValue).valTwo;
    }

    @Override
    public boolean isEqual(ABValue bValue) {
        assert bValue instanceof PairABValue;
        return this.valOne == ((PairABValue) bValue).valOne && this.valTwo == ((PairABValue) bValue).valTwo;
    }

    @Override
    public ABValue combine(ABValue bValue, BinaryOperator<Integer> combiner) {
        assert bValue instanceof PairABValue;
        PairABValue newVal = new PairABValue(combiner.apply(this.valOne, ((PairABValue) bValue).valOne),
                combiner.apply(this.valTwo, ((PairABValue) bValue).valTwo), opcode);

        return newVal;
    }

    @Override
    public ABValue decrement() {
        return new PairABValue(valOne - 1, valTwo - 1, opcode);
    }

    @Override
    public MemoryCell apply(MemoryCell target) {
        if (opcode.equals("X")) {
            return new RingMemCell(target, valTwo, valOne);
        } else {
            return new RingMemCell(target, valOne, valTwo);
        }
    }

    @Override
    public String toString() {
        return "PairABValue{" +
                "valOne=" + valOne +
                ", valTwo=" + valTwo +
                '}';
    }
}
