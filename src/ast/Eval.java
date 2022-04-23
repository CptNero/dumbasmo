package ast;

public class Eval {

    public EvalType evalType;
    private double value = 0;
    private int memoryId = 0;

    private String opcode = "";
    private Eval op1;
    private Eval op2;

    Eval(double value) {
        this.value = value;
        this.evalType = EvalType.VALUE;
    }

    Eval(int memoryId, double value) {
        this.memoryId = memoryId;
        this.value = value;
        this.evalType = EvalType.MEMORY;
    }

    Eval(String opcode, Eval op1, Eval op2) {
        this.opcode = opcode;
        this.op1 = op1;
        this.op2 = op2;

        if (this.op2.evalType == EvalType.MEMORY && !opcode.equals("/"))
        {
            Eval temp = this.op1;
            this.op1 = this.op2;
            this.op2 = temp;
        }

        this.evalType = EvalType.CODE;
    }

    public double getValue() {
        if (this.evalType != EvalType.VALUE) {
            System.out.println("Eval object wasn't value.");
        }

        return value;
    }

    public int getMemory() {
        if (this.evalType != EvalType.MEMORY) {
            System.out.println("Eval object wasn't a memory.");
        }

        return memoryId;
    }

    public Eval getOperandMemory() {
        if (op1.evalType == EvalType.MEMORY) {
            return op1;
        }
        if (op2.evalType == EvalType.MEMORY) {
            return op2;
        }

        return new Eval(-1);
    }

    public Eval getOp1() {
        return this.op1;
    }

    public Eval getOp2() {
        return this.op2;
    }

    public String toString() {
        switch (this.evalType) {
            case VALUE -> {
                return Double.toString(this.value);
            }
            case MEMORY -> {
                return String.format("m[%d]", this.memoryId);
            }
            case CODE -> {
                return String.format("%s %s %s", this.opcode, this.op1.toString(), this.op2.toString());
            }
        }

        return "";
    }

    public String getCode() {
        if (this.evalType != EvalType.CODE) {
            System.out.println("Eval object wasn't CODE.");
        }

        return String.format("%s %s %s", this.opcode, this.op1.toString(), this.op2.toString());
    }
}