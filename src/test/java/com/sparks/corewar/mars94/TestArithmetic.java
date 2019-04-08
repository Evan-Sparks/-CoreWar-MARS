package com.sparks.corewar.mars94;

import com.sparks.corewar.Machine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

import static com.sparks.corewar.mars94.TestHelpers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestArithmetic {
    @Test
    void testArithmetic() {
        List<Operator> operators = new ArrayList<>();
        operators.add(new Operator("ADD", (a, b) -> (b + a)));
        operators.add(new Operator("SUB", (a, b) -> (b - a)));
        operators.add(new Operator("MUL", (a, b) -> (b * a)));
        operators.add(new Operator("DIV", (a, b) -> (b / a)));
        operators.add(new Operator("MOD", (a, b) -> (b % a)));
        for (Operator operator: operators) {
            Machine machine = init(operator.opcode, "F");
            machine.step();
            validateEquals(machine, "DAT", "I", operator.operator.apply(3, 5), "#",
                    operator.operator.apply(4, 6), "#",3);
        }

        for (Operator operator: operators) {
            Machine machine = init(operator.opcode, "A");
            machine.step();
            validateEquals(machine, "DAT", "I", operator.operator.apply(3, 5), "#",
                    6, "#",3);
        }
    }

    @Test
    void testDivZero() {
        Machine machine = initWithInst("DIV", "F", 0, "$", 0, "$");
        machine.step();
        assertTrue(machine.isTerminated());

        machine = initWithInst("MOD", "F", 0, "$", 0, "$");
        machine.step();
        assertTrue(machine.isTerminated());
    }

    private Machine init(String opcode, String mod) {
        Machine machine = initWithInst(opcode, mod, 1, "$", 2, "$");
        addDat(machine, 2, 3, 4);
        addDat(machine, 3, 5, 6);
        return machine;
    }

    private static class Operator {
        public final String opcode;
        public final BinaryOperator<Integer> operator;

        private Operator(String opcode, BinaryOperator<Integer> operator) {
            this.opcode = opcode;
            this.operator = operator;
        }
    }
}
