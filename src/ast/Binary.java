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
                System.out.println(b.toString() + " " + text + ":" + b.toString().equals(text));
                if (b.text.equals(text)) {
                    return b;
                }
            }
            System.out.println("Couldnt find " + text);
            return null;
        }
    }

    private BinaryOperator op = null;
    private Expression lhsNode = null;
    private Expression rhsNode = null;
    public Binary(String op, Expression lhs, Expression rhs) {
        System.out.println(op);
        this.op = BinaryOperator.getBinaryOperator(op);
        this.lhsNode = lhs;
        this.rhsNode = rhs;
    }
    public Eval evaluate(Program p) {
        Eval lhs = this.lhsNode.evaluate(p);
        Eval rhs = this.rhsNode.evaluate(p);

        if (lhs.isMemory || rhs.isMemory)
        {
            String operands = lhs.getValue() + " " + rhs.getValue();
            switch (this.op) {
                case ADD: return new Eval(true, "ADD " + operands);
                case SUB: return new Eval(true, "SUB " + operands);
                case MUL: return new Eval(true, "MUL " + operands);
                case DIV: return new Eval(true, "DIV " + operands);
            }
        }

        double value = 0;

        System.out.println(lhs.value);
        System.out.println(rhs.value);

        switch (this.op) {
            case ADD: value = lhs.value + rhs.value; break;
            case SUB: value = lhs.value - rhs.value; break;
            case MUL: value = lhs.value * rhs.value; break;
            case DIV: value = lhs.value / rhs.value; break;
        }
        return (value == 0) ? new Eval(false, 0) : new Eval(false, value);
    }
}
