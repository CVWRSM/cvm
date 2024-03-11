
/*
 * generated by Xtext
 */
lexer grammar InternalWreslEditorLexer;


@header {
package gov.ca.dwr.wresl.xtext.editor.parser.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}




Daysintimestep : ('D'|'d')('A'|'a')('Y'|'y')('S'|'s')('I'|'i')('N'|'n')('T'|'t')('I'|'i')('M'|'m')('E'|'e')('S'|'s')('T'|'t')('E'|'e')('P'|'p');

Daysinmonth : ('D'|'d')('A'|'a')('Y'|'y')('S'|'s')('I'|'i')('N'|'n')('M'|'m')('O'|'o')('N'|'n')('T'|'t')('H'|'h');

Timeseries : ('T'|'t')('I'|'i')('M'|'m')('E'|'e')('S'|'s')('E'|'e')('R'|'r')('I'|'i')('E'|'e')('S'|'s');

Condition : ('C'|'c')('O'|'o')('N'|'n')('D'|'d')('I'|'i')('T'|'t')('I'|'i')('O'|'o')('N'|'n');

Constrain : ('C'|'c')('O'|'o')('N'|'n')('S'|'s')('T'|'t')('R'|'r')('A'|'a')('I'|'i')('N'|'n');

Objective : ('O'|'o')('B'|'b')('J'|'j')('E'|'e')('C'|'c')('T'|'t')('I'|'i')('V'|'v')('E'|'e');

Unbounded : ('U'|'u')('N'|'n')('B'|'b')('O'|'o')('U'|'u')('N'|'n')('D'|'d')('E'|'e')('D'|'d');

Wateryear : ('W'|'w')('A'|'a')('T'|'t')('E'|'e')('R'|'r')('Y'|'y')('E'|'e')('A'|'a')('R'|'r');

External : ('E'|'e')('X'|'x')('T'|'t')('E'|'e')('R'|'r')('N'|'n')('A'|'a')('L'|'l');

Sequence : ('S'|'s')('E'|'e')('Q'|'q')('U'|'u')('E'|'e')('N'|'n')('C'|'c')('E'|'e');

Timestep : ('T'|'t')('I'|'i')('M'|'m')('E'|'e')('S'|'s')('T'|'t')('E'|'e')('P'|'p');

Declare : ('D'|'d')('E'|'e')('C'|'c')('L'|'l')('A'|'a')('R'|'r')('E'|'e');

Cfs_taf : ('C'|'c')('F'|'f')('S'|'s')'_'('T'|'t')('A'|'a')('F'|'f');

Convert : ('C'|'c')('O'|'o')('N'|'n')('V'|'v')('E'|'e')('R'|'r')('T'|'t');

Include : ('I'|'i')('N'|'n')('C'|'c')('L'|'l')('U'|'u')('D'|'d')('E'|'e');

Initial_1 : ('I'|'i')('N'|'n')('I'|'i')('T'|'t')('I'|'i')('A'|'a')('L'|'l');

Integer : ('I'|'i')('N'|'n')('T'|'t')('E'|'e')('G'|'g')('E'|'e')('R'|'r');

Penalty : ('P'|'p')('E'|'e')('N'|'n')('A'|'a')('L'|'l')('T'|'t')('Y'|'y');

Prevapr : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('A'|'a')('P'|'p')('R'|'r');

Prevaug : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('A'|'a')('U'|'u')('G'|'g');

Prevdec : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('D'|'d')('E'|'e')('C'|'c');

Prevfeb : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('F'|'f')('E'|'e')('B'|'b');

Prevjan : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('J'|'j')('A'|'a')('N'|'n');

Prevjul : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('J'|'j')('U'|'u')('L'|'l');

Prevjun : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('J'|'j')('U'|'u')('N'|'n');

Prevmar : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('M'|'m')('A'|'a')('R'|'r');

Prevmay : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('M'|'m')('A'|'a')('Y'|'y');

Prevnov : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('N'|'n')('O'|'o')('V'|'v');

Prevoct : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('O'|'o')('C'|'c')('T'|'t');

Prevsep : ('P'|'p')('R'|'r')('E'|'e')('V'|'v')('S'|'s')('E'|'e')('P'|'p');

Taf_cfs : ('T'|'t')('A'|'a')('F'|'f')'_'('C'|'c')('F'|'f')('S'|'s');

Af_cfs : ('A'|'a')('F'|'f')'_'('C'|'c')('F'|'f')('S'|'s');

Cfs_af : ('C'|'c')('F'|'f')('S'|'s')'_'('A'|'a')('F'|'f');

Daysin : ('D'|'d')('A'|'a')('Y'|'y')('S'|'s')('I'|'i')('N'|'n');

Define : ('D'|'d')('E'|'e')('F'|'f')('I'|'i')('N'|'n')('E'|'e');

Select : ('S'|'s')('E'|'e')('L'|'l')('E'|'e')('C'|'c')('T'|'t');

Alias : ('A'|'a')('L'|'l')('I'|'i')('A'|'a')('S'|'s');

Const_1 : ('C'|'c')('O'|'o')('N'|'n')('S'|'s')('T'|'t');

Given : ('G'|'g')('I'|'i')('V'|'v')('E'|'e')('N'|'n');

Group : ('G'|'g')('R'|'r')('O'|'o')('U'|'u')('P'|'p');

Local : ('L'|'l')('O'|'o')('C'|'c')('A'|'a')('L'|'l');

Lower : ('L'|'l')('O'|'o')('W'|'w')('E'|'e')('R'|'r');

Model : ('M'|'m')('O'|'o')('D'|'d')('E'|'e')('L'|'l');

Month : ('M'|'m')('O'|'o')('N'|'n')('T'|'t')('H'|'h');

Units : ('U'|'u')('N'|'n')('I'|'i')('T'|'t')('S'|'s');

Upper : ('U'|'u')('P'|'p')('P'|'p')('E'|'e')('R'|'r');

Value : ('V'|'v')('A'|'a')('L'|'l')('U'|'u')('E'|'e');

Where : ('W'|'w')('H'|'h')('E'|'e')('R'|'r')('E'|'e');

Dll : '.'('D'|'d')('L'|'l')('L'|'l');

DAY : '1'('D'|'d')('A'|'a')('Y'|'y');

MON : '1'('M'|'m')('O'|'o')('N'|'n');

Case : ('C'|'c')('A'|'a')('S'|'s')('E'|'e');

Dvar_1 : ('D'|'d')('V'|'v')('A'|'a')('R'|'r');

From : ('F'|'f')('R'|'r')('O'|'o')('M'|'m');

Goal : ('G'|'g')('O'|'o')('A'|'a')('L'|'l');

Kind : ('K'|'k')('I'|'i')('N'|'n')('D'|'d');

Svar_1 : ('S'|'s')('V'|'v')('A'|'a')('R'|'r');

Apr : ('A'|'a')('P'|'p')('R'|'r');

Aug : ('A'|'a')('U'|'u')('G'|'g');

Day : ('D'|'d')('A'|'a')('Y'|'y');

Dec : ('D'|'d')('E'|'e')('C'|'c');

Feb : ('F'|'f')('E'|'e')('B'|'b');

Jan : ('J'|'j')('A'|'a')('N'|'n');

Jul : ('J'|'j')('U'|'u')('L'|'l');

Jun : ('J'|'j')('U'|'u')('N'|'n');

Lhs : ('L'|'l')('H'|'h')('S'|'s');

Mar : ('M'|'m')('A'|'a')('R'|'r');

May : ('M'|'m')('A'|'a')('Y'|'y');

Nov : ('N'|'n')('O'|'o')('V'|'v');

Oct : ('O'|'o')('C'|'c')('T'|'t');

Rhs : ('R'|'r')('H'|'h')('S'|'s');

Sep : ('S'|'s')('E'|'e')('P'|'p');

Std : ('S'|'s')('T'|'t')('D'|'d');

Sum : ('S'|'s')('U'|'u')('M'|'m');

Use : ('U'|'u')('S'|'s')('E'|'e');

M : '$'('M'|'m');

SolidusEqualsSign : '/''=';

LessThanSignEqualsSign : '<''=';

EqualsSignEqualsSign : '=''=';

GreaterThanSignEqualsSign : '>''=';

I_1 : ('I'|'i')'=';

LeftParenthesis : '(';

RightParenthesis : ')';

Asterisk : '*';

PlusSign : '+';

Comma : ',';

HyphenMinus : '-';

Solidus : '/';

Colon : ':';

LessThanSign : '<';

EqualsSign : '=';

GreaterThanSign : '>';

LeftSquareBracket : '[';

RightSquareBracket : ']';

I : ('I'|'i');

LeftCurlyBracket : '{';

RightCurlyBracket : '}';



RULE_IF : ('If'|'IF'|'if');

RULE_ELSEIF : ('Elseif'|'ELSEIF'|'elseif'|'ElseIf');

RULE_ELSE : ('Else'|'ELSE'|'else');

RULE_RANGE : ('range'|'RANGE'|'Range');

RULE_MIN : ('min'|'MIN');

RULE_MAX : ('max'|'MAX');

RULE_MOD : ('mod'|'MOD');

RULE_INTFUNC : ('int'|'INT');

RULE_ABS : ('abs'|'ABS');

RULE_ROUND : ('round'|'ROUND');

RULE_POW : ('pow'|'POW');

RULE_LOG : ('log'|'LOG'|'log10'|'LOG10');

RULE_SIN : ('sin'|'SIN');

RULE_COS : ('cos'|'COS');

RULE_TAN : ('tan'|'TAN');

RULE_COT : ('cot'|'COT');

RULE_ASIN : ('asin'|'ASIN');

RULE_ACOS : ('acos'|'ACOS');

RULE_ATAN : ('atan'|'ATAN');

RULE_ACOT : ('acot'|'ACOT');

RULE_FLOAT : (RULE_INT '.' RULE_INT*|'.' RULE_INT+);

RULE_AND : ('.and.'|'.AND.');

RULE_OR : ('.or.'|'.OR.');

RULE_NOT : ('.not.'|'.NOT.');

RULE_ALWAYS : 'always';

RULE_ORDER : 'order';

RULE_STRING : '\'' ~(('\''|'\n'|'\r'))* '\'';

RULE_SL_COMMENT : '!' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_ID : ('a'..'z'|'A'..'Z') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;


