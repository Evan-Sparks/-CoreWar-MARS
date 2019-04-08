package com.sparks.corewar.mars94.abvalues;

import com.sparks.corewar.MemoryCell;
import com.sparks.corewar.mars94.ABValue;
import com.sparks.corewar.mars94.ringmemory.RingMemCell;

import java.util.function.BinaryOperator;

public class InstructionABValue implements ABValue {
    private final MemoryCell memoryCell;

    public InstructionABValue(MemoryCell memoryCell) {
        this.memoryCell = memoryCell;
    }

    @Override
    public boolean isZero() {
        return memoryCell.getAOperand().number() == 0 && memoryCell.getBOperand().number() == 0;
    }

    @Override
    public boolean isNonZero() {
        return memoryCell.getAOperand().number() != 0 && memoryCell.getBOperand().number() != 0;
    }

    @Override
    public boolean isLessThan(ABValue bValue) {
        assert bValue instanceof InstructionABValue;
        return memoryCell.getAOperand().number() < ((InstructionABValue) bValue).memoryCell.getAOperand().number()
                && memoryCell.getBOperand().number() < ((InstructionABValue) bValue).memoryCell.getBOperand().number();
    }

    @Override
    public boolean isEqual(ABValue bValue) {
        assert bValue instanceof InstructionABValue;
        return memoryCell.getAOperand().number() < ((InstructionABValue) bValue).memoryCell.getAOperand().number()
                && memoryCell.getBOperand().number() < ((InstructionABValue) bValue).memoryCell.getBOperand().number();
    }

    @Override
    public ABValue combine(ABValue bValue, BinaryOperator<Integer> combiner) {
        assert bValue instanceof InstructionABValue;
        return new InstructionABValue(new RingMemCell(memoryCell,
                combiner.apply(memoryCell.getAOperand().number(), ((InstructionABValue) bValue).memoryCell.getAOperand().number()),
                combiner.apply(memoryCell.getBOperand().number(), ((InstructionABValue) bValue).memoryCell.getBOperand().number())));
    }

    @Override
    public ABValue decrement() {
        return new InstructionABValue(new RingMemCell(memoryCell, memoryCell.getAOperand().number() - 1,
                memoryCell.getBOperand().number() - 1));
    }

    @Override
    public MemoryCell apply(MemoryCell target) {
        return memoryCell;
    }
}
