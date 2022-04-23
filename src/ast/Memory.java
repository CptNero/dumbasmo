package ast;

public class Memory extends Expression {
    private int id;

    public Memory(String id) {
        this.id = Integer.parseInt(id);
    }
    public Eval evaluate(Program p) {
        return new Eval(this.id, this.id);
    }
}
