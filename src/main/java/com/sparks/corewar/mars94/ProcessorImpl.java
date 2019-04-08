package com.sparks.corewar.mars94;

import com.sparks.corewar.*;
import com.sparks.corewar.mars94.ringmemory.RingMemCell;

import java.util.List;
import java.util.function.BinaryOperator;

public class ProcessorImpl implements Processor {
    private ProgramQueue programQueue = new ProgramQueue();
    private final int minimumPrograms;

    public ProcessorImpl(int minimumPrograms) {
        this.minimumPrograms = minimumPrograms;
    }

    public void step(Bus bus) {
        ThreadQueue threadQueue = programQueue.getNextQueue();
        if (threadQueue == null) {
            // There are no active programs
            bus.reportTermination();
            // TODO: report reason
        } else {
            ProcessThread thread = threadQueue.getProcessThread();
            ProcessorRegisters registers = new ProcessorRegisters(thread.currentTaskPointer(), bus);

            switch (registers.instructionRegister.getOpcode().type()) {
                case "DAT":
                    killThread(bus, threadQueue, thread);
                    // TODO: report reason
                case "MOV":
                    handleMov(thread, registers, bus);
                    break;
                case "ADD":
                    handleAdd(thread, registers, bus);
                    break;
                case "SUB":
                    handleSub(thread, registers, bus);
                    break;
                case "MUL":
                    handleMul(thread, registers, bus);
                    break;
                case "DIV":
                    if (registers.aInstruction.getAOperand().number() == 0) {
                        threadQueue.removeProcessThread(thread);
                        if (!threadQueue.isEmpty()) {
                            //TODO report division by 0;
                        } else {
                            killThread(bus, threadQueue, thread);
                            // TODO: report reason
                        }
                    } else {
                        handleDiv(thread, registers, bus);
                    }
                    break;
                case "MOD":
                    if (registers.aInstruction.getAOperand().number() == 0) {
                        threadQueue.removeProcessThread(thread);
                        if (!threadQueue.isEmpty()) {
                            //TODO report division by 0;
                        } else {
                            killThread(bus, threadQueue, thread);
                            // TODO: report reason
                        }
                    } else {
                        handleMod(thread, registers, bus);
                    }
                    break;
                case "JMP":
                    handleJmp(thread, registers);
                    break;
                case "JMZ":
                    handleJmz(thread, registers);
                    break;
                case "JMN":
                    handleJmn(thread, registers);
                    break;
                case "DJN":
                    handleDjn(thread, registers, bus);
                    break;
                case "CMP":
                    handleCmp(thread, registers);
                    break;
                case "SLT":
                    handleSlt(thread, registers);
                    break;
                case "SPL":
                    handleSpl(thread, threadQueue, registers);
                    break;
                default:
                    killThread(bus, threadQueue, thread);
                    // TODO: report reason
            }
        }
    }

    @Override
    public void addPlayerTaskQueue(ThreadQueue threadQueue) {
        programQueue.addPlayerTaskQueue(threadQueue);
    }

    private void handleAdd(ProcessThread thread, ProcessorRegisters registers, Bus bus) {
        handleArithmetic(thread, registers, bus, (a, b) -> (a + b));
    }

    private void handleSub(ProcessThread thread, ProcessorRegisters registers, Bus bus) {
        handleArithmetic(thread, registers, bus, (a, b) -> (b - a));
    }

    private void handleMul(ProcessThread thread, ProcessorRegisters registers, Bus bus) {
        handleArithmetic(thread, registers, bus, (a, b) -> (a * b));
    }

    private void handleDiv(ProcessThread thread, ProcessorRegisters registers, Bus bus) {
        handleArithmetic(thread, registers, bus, (a, b) -> (b / a));
    }

    private void handleMod(ProcessThread thread, ProcessorRegisters registers, Bus bus) {
        handleArithmetic(thread, registers, bus, (a, b) -> (b % a));
    }

    private void handleMov(ProcessThread thread, ProcessorRegisters registers, Bus bus) {
        handleArithmetic(thread, registers, bus, (a, b) -> (a));
    }

    private void handleArithmetic(ProcessThread thread, ProcessorRegisters registers, Bus bus,
                                  BinaryOperator<Integer> arithmeticOp) {
        ABValue result = registers.aValue.combine(registers.bValue, arithmeticOp);
        MemoryCell newCell = result.apply(registers.bInstruction);
        bus.writeToMemory(registers.bPointer + registers.programCounter, newCell);
        thread.updateTaskPointer(1);
    }

    private void handleJmp(ProcessThread thread, ProcessorRegisters registers) {
        thread.updateTaskPointer(registers.aPointer);
    }

    private void handleJmz(ProcessThread thread, ProcessorRegisters registers) {
        if (registers.bValue.isZero()) {
            handleJmp(thread, registers);
        } else {
            thread.updateTaskPointer(1);
        }
    }

    private void handleJmn(ProcessThread thread, ProcessorRegisters registers) {
        if (registers.bValue.isNonZero()) {
            handleJmp(thread, registers);
        } else {
            thread.updateTaskPointer(1);
        }
    }

    private void handleDjn(ProcessThread thread, ProcessorRegisters registers, Bus bus) {
        ABValue decrementedBVal = registers.bValue.decrement();
        MemoryCell decrementedBTarget;
        if (List.of("A", "BA").contains(registers.instructionRegister.getOpcode().modifier())) {
            decrementedBTarget = new RingMemCell(registers.bInstruction,
                    registers.bInstruction.getAOperand().number() - 1,
                    registers.bInstruction.getBOperand().number());
        } else if (List.of("B", "AB").contains(registers.instructionRegister.getOpcode().modifier())) {
            decrementedBTarget = new RingMemCell(registers.bInstruction, registers.bInstruction.getAOperand().number(),
                    registers.bInstruction.getBOperand().number() - 1);
        } else {
            decrementedBTarget = new RingMemCell(registers.bInstruction,
                    registers.bInstruction.getAOperand().number() - 1,
                    registers.bInstruction.getBOperand().number() - 1);
        }
        bus.writeToMemory(registers.programCounter + registers.bPointer, decrementedBTarget);

        if (decrementedBVal.isNonZero()) {
            thread.updateTaskPointer(registers.aPointer);
        } else {
            thread.updateTaskPointer(1);
        }
    }

    private void handleCmp(ProcessThread thread, ProcessorRegisters registers) {
        if (registers.aValue.isEqual(registers.bValue)) {
            thread.updateTaskPointer(2);
        } else {
            thread.updateTaskPointer(1);
        }
    }

    private void handleSlt(ProcessThread thread, ProcessorRegisters registers) {
        if (registers.aValue.isLessThan(registers.bValue)) {
            thread.updateTaskPointer(2);
        } else {
            thread.updateTaskPointer(1);
        }
    }

    private void handleSpl(ProcessThread thread, ThreadQueue threadQueue, ProcessorRegisters registers) {
        thread.updateTaskPointer(1);
        threadQueue.newProcessThread(registers.programCounter + registers.aPointer);
    }

    private void killThread(Bus bus, ThreadQueue threadQueue, ProcessThread thread) {
        threadQueue.removeProcessThread(thread);
        if (threadQueue.isEmpty()) {
            programQueue.removeQueue(threadQueue);
        }
        if (programQueue.getProgramCount() < minimumPrograms) {
            bus.reportTermination();
        }
    }
}