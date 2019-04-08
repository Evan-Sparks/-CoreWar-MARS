package com.sparks.corewar.mars94.abvalues;

import com.sparks.corewar.MemoryCell;
import com.sparks.corewar.mars94.ABValue;
import com.sparks.corewar.mars94.ringmemory.RingMemCell;

import java.util.List;
import java.util.function.BinaryOperator;

public class SingleABValue implements ABValue {
    private final int value;
    private final String modifier;

    public SingleABValue(int value, String modifier) {
        this.value = value;
        this.modifier = modifier;
    }

    @Override
    public boolean isZero() {
        return value == 0;
    }

    @Override
    public boolean isNonZero() {
        return value != 0;
    }

    @Override
    public boolean isLessThan(ABValue bValue) {
        assert bValue instanceof SingleABValue;
        return this.value < ((SingleABValue) bValue).value;
    }

    @Override
    public boolean isEqual(ABValue bValue) {
        assert bValue instanceof SingleABValue;
        return this.value == ((SingleABValue) bValue).value;
    }

    @Override
    public ABValue combine(ABValue bValue, BinaryOperator<Integer> combiner) {
        assert bValue instanceof SingleABValue;
        return new SingleABValue(combiner.apply(this.value, ((SingleABValue) bValue).value), modifier);
    }

    @Override
    public ABValue decrement() {
        return new SingleABValue(value - 1, modifier);
    }

    @Override
    public MemoryCell apply(MemoryCell target) {
        int aVal = target.getAOperand().number();
        int bVal = target.getBOperand().number();
        if (List.of("A", "BA").contains(modifier)) {
            aVal = value;
        } else {
            bVal = value;
        }
        return new RingMemCell(target, aVal, bVal);
    }

    @Override
    public String toString() {
        return "SingleABValue{" +
                "value=" + value +
                ", modifier='" + modifier + '\'' +
                '}';
    }
}
