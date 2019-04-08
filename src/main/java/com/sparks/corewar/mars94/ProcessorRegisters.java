package com.sparks.corewar.mars94;

import com.sparks.corewar.Bus;
import com.sparks.corewar.MemoryCell;
import com.sparks.corewar.mars94.abvalues.InstructionABValue;
import com.sparks.corewar.mars94.abvalues.PairABValue;
import com.sparks.corewar.mars94.abvalues.SingleABValue;
import com.sparks.corewar.mars94.ringmemory.RingMemCell;

public class ProcessorRegisters {
    public final int programCounter;
    public final MemoryCell instructionRegister;

    public final int aPointer;
    public final MemoryCell aInstruction;
    public final ABValue aValue;

    public final int bPointer;
    public final MemoryCell bInstruction;
    public final ABValue bValue;

    public ProcessorRegisters(int counter, Bus bus) {
        instructionRegister = bus.getFromMemory(counter);
        programCounter = counter;
        int primaryOffset = instructionRegister.getAOperand().number();
        int secondaryOffset;

        switch (instructionRegister.getAOperand().addressMode()) {
            case "#":
                // Immediate
                aPointer = 0;
                aInstruction = instructionRegister;
                break;
            case "$":
                // Direct
                aPointer = primaryOffset;
                aInstruction = bus.getFromMemory(programCounter + aPointer);
                break;
            case "@":
                // Indirect
                secondaryOffset = bus.getFromMemory(programCounter + primaryOffset).getBOperand().number();
                aPointer = primaryOffset + secondaryOffset;
                aInstruction = bus.getFromMemory(programCounter + aPointer);
                break;
            case "<":
                // Predecrement Indirect
                decrementBNum(bus, primaryOffset);
                secondaryOffset = bus.getFromMemory(programCounter + primaryOffset).getBOperand().number();
                aPointer = primaryOffset + secondaryOffset;
                aInstruction = bus.getFromMemory(programCounter + aPointer);
                break;
            case ">":
                // Preincrement Indirect
                secondaryOffset = bus.getFromMemory(programCounter + primaryOffset).getBOperand().number();
                aPointer = primaryOffset + secondaryOffset;
                aInstruction = bus.getFromMemory(programCounter + aPointer);
                incrementBNum(bus, primaryOffset);
                break;
            default:
                throw new RuntimeException("Invalid opcode modifier " + instructionRegister.getAOperand().addressMode());
        }

        primaryOffset = instructionRegister.getBOperand().number();
        switch (instructionRegister.getBOperand().addressMode()) {
            case "#":
                // Immediate
                bPointer = 0;
                bInstruction = instructionRegister;
                break;
            case "$":
                // Direct
                bPointer = primaryOffset;
                bInstruction = bus.getFromMemory(programCounter + bPointer);
                break;
            case "@":
                // Indirect
                secondaryOffset = bus.getFromMemory(programCounter + primaryOffset).getBOperand().number();
                bPointer = primaryOffset + secondaryOffset;
                bInstruction = bus.getFromMemory(programCounter + bPointer);
                break;
            case "<":
                // Predecrement Indirect
                decrementBNum(bus, primaryOffset);
                secondaryOffset = bus.getFromMemory(programCounter + primaryOffset).getBOperand().number();
                bPointer = primaryOffset + secondaryOffset;
                bInstruction = bus.getFromMemory(programCounter + bPointer);
                break;
            case ">":
                // Preincrement Indirect
                secondaryOffset = bus.getFromMemory(programCounter + primaryOffset).getBOperand().number();
                bPointer = primaryOffset + secondaryOffset;
                bInstruction = bus.getFromMemory(programCounter + bPointer);
                incrementBNum(bus, primaryOffset);
                break;
            default:
                throw new RuntimeException("Invalid adressing mode " + instructionRegister.getBOperand().addressMode());
        }

        switch (instructionRegister.getOpcode().modifier()) {
            case "A":
                aValue = new SingleABValue(aInstruction.getAOperand().number(), "A");
                bValue = new SingleABValue(bInstruction.getAOperand().number(), "A");
                break;
            case "B":
                aValue = new SingleABValue(aInstruction.getBOperand().number(), "B");
                bValue = new SingleABValue(bInstruction.getBOperand().number(), "B");
                break;
            case "AB":
                aValue = new SingleABValue(aInstruction.getAOperand().number(), "AB");
                bValue = new SingleABValue(bInstruction.getBOperand().number(), "AB");
                break;
            case "BA":
                aValue = new SingleABValue(aInstruction.getBOperand().number(), "BA");
                bValue = new SingleABValue(bInstruction.getAOperand().number(), "BA");
                break;
            case "F":
                aValue = new PairABValue(aInstruction.getAOperand().number(), aInstruction.getBOperand().number(), "F");
                bValue = new PairABValue(bInstruction.getAOperand().number(), bInstruction.getBOperand().number(), "F");
                break;
            case "X":
                aValue = new PairABValue(aInstruction.getAOperand().number(), aInstruction.getBOperand().number(), "X");
                bValue = new PairABValue(bInstruction.getBOperand().number(), bInstruction.getAOperand().number(), "X");
                break;
            case "I":
                aValue = new InstructionABValue(aInstruction);
                bValue = new InstructionABValue(bInstruction);
                break;
            default:
                throw new RuntimeException("Invalid opcode modifier " + instructionRegister.getOpcode().modifier());
        }
    }

    private void decrementBNum(Bus bus, int primaryOffset) {
        MemoryCell cell = bus.getFromMemory(programCounter + primaryOffset);
        MemoryCell decremented =new RingMemCell(cell, cell.getAOperand().number(), cell.getBOperand().number() - 1);
        bus.writeToMemory(programCounter + primaryOffset, decremented);
    }

    private void incrementBNum(Bus bus, int primaryOffset) {
        MemoryCell cell = bus.getFromMemory(programCounter + primaryOffset);
        MemoryCell decremented =new RingMemCell(cell, cell.getAOperand().number(), cell.getBOperand().number() + 1);
        bus.writeToMemory(programCounter + primaryOffset, decremented);
    }

    @Override
    public String toString() {
        return "ProcessorRegisters{" +
                "programCounter=" + programCounter +
                ", instructionRegister=" + instructionRegister +
                ", aPointer=" + aPointer +
                ", aInstruction=" + aInstruction +
                ", aValue=" + aValue +
                ", bPointer=" + bPointer +
                ", bInstruction=" + bInstruction +
                ", bValue=" + bValue +
                '}';
    }
}
