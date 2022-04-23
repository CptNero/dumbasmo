package ast;

public class Binary extends Expression {
    private enum BinaryOperator {
        ADD("+"), SUB("-"), MUL("*"), DIV("/");
        private String text;
        private BinaryOperator(String text) {
            this.text = text;
        }
        public static BinaryOperator getBinaryOperator(String text) {
            for (BinaryOperator b : BinaryOperator.values()) {
                if (b.text.equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    private BinaryOperator op = null;
    private Expression lhsNode = null;
    private Expression rhsNode = null;
    public Binary(String op, Expression lhs, Expression rhs) {
        this.op = BinaryOperator.getBinaryOperator(op);
        this.lhsNode = lhs;
        this.rhsNode = rhs;
    }

    public Eval evaluate(Program p) {
        Eval lhs = this.lhsNode.evaluate(p);
        Eval rhs = this.rhsNode.evaluate(p);

        if (lhs.evalType == EvalType.CODE) {
            p.addCode(lhs.getCode());
        }

        if (rhs.evalType == EvalType.CODE) {
            p.addCode(rhs.getCode());
        }


        // Get the location of the memory the previous command has written to
        if ((lhs.evalType == EvalType.MEMORY && rhs.evalType == EvalType.CODE) ||
            (rhs.evalType == EvalType.MEMORY && lhs.evalType == EvalType.CODE)) {
            Eval memory = ((lhs.evalType == EvalType.CODE) ? lhs : rhs).getOperandMemory();
            Eval op2 = (lhs.evalType != EvalType.CODE) ? lhs : rhs;

            return MakeCode(this.op, memory, op2);
        }

        if (lhs.evalType == EvalType.CODE || rhs.evalType == EvalType.CODE) {
            Eval memory = ((lhs.evalType == EvalType.CODE) ? lhs : rhs).getOperandMemory();
            Eval op2 = (lhs.evalType != EvalType.CODE) ? lhs : rhs;

            return MakeCode(this.op, memory, op2);
        }

        if (lhs.evalType == EvalType.MEMORY || rhs.evalType == EvalType.MEMORY) {
            return MakeCode(this.op, lhs, rhs);
        }

        double value = 0;
        value = switch (this.op) {
            case ADD -> lhs.getValue() + rhs.getValue();
            case SUB -> lhs.getValue() - rhs.getValue();
            case MUL -> lhs.getValue() * rhs.getValue();
            case DIV -> lhs.getValue() / rhs.getValue();
        };

        return new Eval(value);
    }

    private Eval MakeCode(BinaryOperator op, Eval lhs, Eval rhs) {
        return switch (op) {
            case ADD -> new Eval("ADD", lhs, rhs);
            case SUB -> new Eval("SUB", lhs, rhs);
            case MUL -> new Eval("MUL", lhs, rhs);
            case DIV -> new Eval("DIV", lhs, rhs);
        };
    }
}
