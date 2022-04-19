grammar example;

options {
    language = Java;
}

@header {
    import java.util.ArrayList;
    import java.util.List;
}

@members {
    public static void main(String[] args) throws Exception {
         FunctionsLexer lex = new FunctionsLexer(new ANTLRFileStream(args[0]));
         CommonTokenStream tokens = new CommonTokenStream (lex);
         FunctionsParser parser = new FunctionsParser(tokens);
         parser.start(args.length > 1 && "--generate".equals(args[1]));
    }
}

start [ boolean genSrc ]
    @init{ ast.Program p = new ast.Program(); }
    @after{ if (genSrc) {
                System.out.println(p);
            } else {
                p.evaluate();
            }
    }
    : ((line[p] { p.addLine($line.node); } )? COMMENT? LF)* EOF
    ;

line [ ast.Program p ] returns [ ast.Line node ]
    : expr { $node = new ast.Line($p, $expr.node); }
    | MEM '=' expr { $node = new ast.Line($p, $expr.node, $MEM.text); }
    ;

expr returns [ ast.Expression node ]
    : fstop=addop { $node = $fstop.node; } (OPADD nxtop=addop {
        $node = new ast.Binary($OPADD.text, $node, $nxtop.node);
      })*
    ;

addop returns [ ast.Expression node ]
    : fstop=mulop { $node = $fstop.node; } (OPMUL nxtop=mulop {
        $node = new ast.Binary($OPMUL.text, $node, $nxtop.node);
      })*
    ;

mulop returns [ ast.Expression node ]
    : fstop=fct { $node = $fstop.node; } (OPPWR nxtop=mulop {
        $node = new ast.Binary($OPPWR.text, $node, $nxtop.node);
      })?
    ;

fct returns [ ast.Expression node ]
    : SZAM { $node = new ast.Const($SZAM.text); }
    | LPAR expr { $node = new ast.Parens($expr.node); } RPAR
    | OPADD fct { $node = new ast.Unary($OPADD.text, $fct.node); }
    | OPABS LPAR expr { $node = new ast.Unary($OPABS.text, $expr.node); } RPAR
    | MEM { $node = new ast.Memory($MEM.text); }
    | OPMINMAX LPAR fstop=expr { $node = new ast.MultiArg($OPMINMAX.text, $fstop.node); }
        (OPLST nxtop=expr  { ((ast.MultiArg)$node).addArgument($nxtop.node); }) * RPAR
    ;

LF       : '\n' ;
WS       : [ \t\r]+ ->skip ;
SZAM     : [0-9]+('.' [0-9]+)? ;
OPADD    : '+' | '-' ;
OPMUL    : '*' | '/' ;
OPPWR    : '^' ;
OPABS    : 'abs' ;
OPMINMAX : 'min' | 'max' ;
OPLST    : ',' ;
LPAR     : '(' ;
RPAR     : ')' ;
MEM      : 'M' ;
COMMENT  : '#' (~[\n])* ->skip ;