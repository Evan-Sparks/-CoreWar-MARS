package com.sparks.corewar.mars94;

import com.sparks.corewar.MemoryCell;

import java.util.function.BinaryOperator;

public interface ABValue {
    boolean isZero();
    boolean isNonZero();
    boolean isLessThan(ABValue bValue);
    boolean isEqual(ABValue bValue);
    ABValue combine(ABValue bValue, BinaryOperator<Integer> combiner);
    ABValue decrement();
    MemoryCell apply(MemoryCell target);
}
