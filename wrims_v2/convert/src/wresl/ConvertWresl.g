grammar ConvertWresl;

options {
  language = Java;
}

@header {
  package wresl;
  import java.util.Map;
  import java.util.HashMap;
  import java.util.Arrays;
  import evaluators.Struct;
  import evaluators.Model;
  import evaluators.Tools;
}

@lexer::header {
  package wresl;
}

@members {

	public Struct F = new Struct();	
	public ArrayList<Model> modelList = new ArrayList<Model>();
	
	/// temp variables 
 	private ArrayList<String> list;

	/// error message
	public String currentFilePath;
	public ArrayList<String> outputErrorMessage = new ArrayList<String>();
	public String getErrorMessage(RecognitionException e, String[] tokenNames) {
    		String msg = super.getErrorMessage(e, tokenNames);
			msg = msg+" in file \""+currentFilePath+"\"";
			if (msg!=null){outputErrorMessage.add(msg);}
			return msg;
			}
}


evaluator 
	:	pattern* EOF  ;

pattern
	:   includeFile | sequence | goal_simple | define ;

includeFile
	:	INCLUDE
	;

sequence
	:   SEQUENCE IDENT '{' MODEL m=IDENT ORDER i=INTEGER'}'{
				F.sequenceOrder($i.text, $m.text );
		}
	;

define 
	:	DEFINE 
	(	svar_expression
	|	dvar_std   | dvar_nonstd | dvar_alias
	|	svar_table | svar_dss    | svar_cases | svar_sum )  
	;

goal_simple
	:	GOAL i=IDENT  '{' v=constraintStatement '}'  {
			F.goalSimple($i.text, $v.text);		
		}
	;

goal_lhs
	: 'lhs' IDENT
	;

svar_expression 
	:	i=IDENT '{' v=valueStatement '}' { 
			F.svarExpression($i.text, $v.str);
		}
	;

svar_sum 
	:	i=IDENT '{' t=sumStatement '}' { 
				
				if (F.var_all.containsKey($i.text)){
				//System.out.println("error... variable redefined: " + $i.text);
				F.error_var_redefined.put($i.text, "svar_sum");
				}
				else {
				F.svar_sum.put($i.text, $t.list);
				F.var_all.put($i.text, "svar_sum");
				}
		}
	;

svar_table
	:	i=IDENT '{' t=sqlStatement '}' { 
				
				if (F.var_all.containsKey($i.text)){
				//System.out.println("error... variable redefined: " + $i.text);
				F.error_var_redefined.put($i.text, "svar_table");
				}
				else {
				F.svar_table.put($i.text, $t.list);
				F.var_all.put($i.text, "svar_table");
				}
		}
	;

svar_cases
	//@init { $list = new ArrayList<String>(); }
	:  i=IDENT '{' c=caseStatements '}' { 

				if (F.var_all.containsKey($i.text)){
				//System.out.println("error... variable redefined: " + $i.text);
				F.error_var_redefined.put($i.text, "svar_cases");
				}
				else {
				//list = new ArrayList<String>();
				//list.add($c.text);
				F.svar_cases.put($i.text, $c.caseNames);
				F.svar_conditions.put($i.text, $c.conditions);
				F.svar_map_case_content.put($i.text, $c.caseContent);
				F.var_all.put($i.text, "svar_cases");
				}
		} 	
	;
	
caseStatements returns[ArrayList<String> caseNames, 
					   ArrayList<String> conditions, 
					   Map<String, ArrayList<String>> caseContent ]
					   
@init { $caseNames = new ArrayList<String>(); 
        $conditions = new ArrayList<String>();
        $caseContent = new HashMap<String, ArrayList<String>>(); }
        
	:  ( c=caseStatement  {
			$caseNames.add($c.caseNameStr);
			$conditions.add($c.conditionStr);			
			$caseContent.put($c.caseNameStr, $c.contentList);			         
			
			} )+
	;	

caseStatement returns[String caseNameStr, String conditionStr, ArrayList<String> contentList]
@init { $contentList = new ArrayList<String>();} 
	:  'case' i=IDENT '{' c=conditionStatement 
	( s=sqlStatement | v=valueStatement | u=sumStatement) '}'  {
			
			$caseNameStr = $i.text;
			$conditionStr = $c.str;
			if ($v.str !=null && $v.str!="")      {$contentList.add("value");$contentList.add($v.str);}
			else if ($s.list !=null && ! $s.list.isEmpty() ) {$contentList.add("sql");$contentList.addAll($s.list);}
			else if ($u.list !=null && ! $u.list.isEmpty() ) {$contentList.add("sum");$contentList.addAll($u.list);}
			else { System.out.println("error in caseStatement"); }
			}
	;	


svar_dss
	:	i=IDENT '{' 'timeseries' kind units'}' { 
				
				if (F.var_all.containsKey($i.text)){
				//System.out.println("error... variable redefined: " + $i.text);
				F.error_var_redefined.put($i.text, "svar_dss");
				}
				else {
				list = new ArrayList<String>();
				list.add($kind.str);
				list.add($units.str);
				F.svar_dss.put($i.text, list);
				F.var_all.put($i.text, "svar_dss");
				}
		} 
	;

dvar_std
	:	i=IDENT '{' 'std' kind units'}' { 
				
				if (F.var_all.containsKey($i.text)){
				//System.out.println("error... variable redefined: " + $i.text);
				F.error_var_redefined.put($i.text, "dvar_std");
				}
				else {
				list = new ArrayList<String>();
				list.add($kind.str);
				list.add($units.str);
				F.dvar_std.put($i.text, list);
				F.var_all.put($i.text, "dvar_std");
				}
		}
	;

dvar_alias
	:	i=IDENT '{' alias kind units'}' { 
				
				if (F.var_all.containsKey($i.text)){
				//System.out.println("error... variable redefined: " + $i.text);
				F.error_var_redefined.put($i.text, "dvar_alias");
				}
				else {
				list = new ArrayList<String>();
				list.add($kind.str);
				list.add($units.str);
				list.add($alias.str);
				F.dvar_alias.put($i.text, list);
				F.var_all.put($i.text, "dvar_alias");
				}
		}
	;
	
dvar_nonstd 
	:	i=IDENT '{' c=lower_or_upper kind units '}' { 
				
				if (F.var_all.containsKey($i.text)){
				//System.out.println("error... variable redefined: " + $i.text);
				F.error_var_redefined.put($i.text, "dvar_nonstd");
				}
				else {
				list = new ArrayList<String>();
				list.add($kind.str);
				list.add($units.str);
				list.addAll($c.list);
				F.dvar_nonstd.put($i.text, list);
				F.var_all.put($i.text, "dvar_nonstd");
				}
		} 
	;

lower_or_upper returns[ArrayList<String> list]
	:	lower upper? {       
				$list = new ArrayList<String>();
				$list.add($lower.str);
				if ($upper.str==null) {
				$list.add("unbounded");
				}
				else {
				$list.add($upper.str);
				}		
	    }
	|	upper {       
				$list = new ArrayList<String>();
				$list.add("0");
				$list.add($upper.str);
		}		
	;
 
lower returns[String str]
	: 'lower' e=expression {$str =$e.str; }
	;

upper returns[String str]
	: 'upper' e=expression {$str =$e.str; }
	;

alias returns [String str]
	: 'alias'  e=expression  { $str = $e.text; }
	;
	
kind returns [String str]
	: 'kind'  s=QUOTE_STRING_with_MINUS  { $str =Tools.strip($s.text); } 
	;

units returns [String str]
	: 'units' CFS {$str = "CFS";}
	| 'units' TAF {$str = "TAF";} 
	| 'units' ACRES {$str = "ACRES";}
	| 'units' IN {$str = "IN";}
	| 'units' NONE {$str = "NONE";}
	;

/// sub rules ///




conditionStatement returns[String str]
	: 'condition' 
	( s=logicalRelationStatement {$str = $s.text;}
	| ALWAYS {$str = "always";} )
	;

valueStatement returns[String str]
	: 'value' e=expression {$str = $e.text;}
	;

/// SQL related ///

sqlStatement returns[ArrayList<String> list]
	@init { $list = new ArrayList<String>(); }
	: 'select' i1=all_ident 'from' i2=IDENT 
	  ('given' i3=assignStatement)? ('use' i4=IDENT)? 
	  where_items?	  
	  {       
				$list.add($i1.text);
				$list.add($i2.text);
				$list.add($i3.text);
				$list.add($i4.text);
				if ($where_items.list != null) {$list.addAll($where_items.list);}
		}
	;

where_items returns[ArrayList<String> list]
	@init { $list = new ArrayList<String>(); }

	:	 WHERE  (r1=assignStatement{$list.add($r1.text);} )
	        (',' r=assignStatement {$list.add($r.text);}  )*
	;

///////////////////////////
/// Intrinsic functions ///
///////////////////////////

max_func
	: MAX '(' expression (',' expression)+ ')' ;

min_func
	: MIN '(' expression (',' expression)+ ')' ;

inline_func 
	: IDENT '(' ( ('-' INTEGER) | PREV_MON | 'i' ) ')' ;

sumStatement returns[ArrayList<String> list]
	@init { $list = new ArrayList<String>(); }
	: s=sum_func e=expression
		{$list.add($s.text);$list.add($e.text);}
	;

sum_func
	: SUM 
	s=( '(' 'i=' expression_sum ',' expression_sum (',' INTEGER )? ')' ) 
	;

/////////////////////////////
/// sum rules in sum_func ///
/////////////////////////////
term_sum : reserved_vars | reserved_consts | INTEGER | '(' expression_sum ')' 	;
unary_sum :	('-')? term_sum ;
add_sum  :	unary_sum(('+' | '-') unary_sum)* ;
expression_sum : add_sum ;


///////////////////
/// basic rules ///
///////////////////

term
	:	IDENT | reserved_vars | reserved_consts
	|	'(' expression ')' 
	|	number
	|   inline_func  |   max_func  |  min_func	
	;
	
unary :	('-')? term ;

mult :	unary (('*' | '/' | 'mod') unary)* ;
	
add  :	mult (('+' | '-') mult)* ;

expression returns [String str]
	:	i=add {$str = $i.text; }
	;

relation_group_1  :  LE | GE   ;

relation_group_2  :  '<' | '>'  ;

assignStatement  :   expression '=' expression ;

constraintStatement : expression ('='|relation_group_2) expression ;

relationStatement
	:	expression (EQUALS|relation_group_1|relation_group_2) expression  ;

logicalRelationStatement
	:   relationStatement ((AND|OR) relationStatement)* ;

number : INTEGER | FLOAT ;

reserved_vars : WATERYEAR | MONTH ;

reserved_funcs :  PREV_MON ;

reserved_consts : MON_CONST ;

reserved_keys : GOAL | DEFINE ;

all_ident  : reserved_vars | reserved_consts | IDENT ;

MULTILINE_COMMENT : '/*' .* '*/' {$channel = HIDDEN;} ;

fragment LETTER : ('a'..'z' | 'A'..'Z') ;
fragment DIGIT : '0'..'9';
fragment SYMBOLS : '_';

INTEGER : DIGIT+ ;
FLOAT : INTEGER? '.' INTEGER  | INTEGER '.' ;

/// INLINE_FUNC //
MAX : 'max' ;
MIN : 'min' ;
SUM : 'sum' ;
WHERE : 'where' ;

/// comparison ///
EQUALS : '==';
LE : '<=';
GE : '>=';

/// logical ///
AND : '.and.';
OR  : '.or.';

/// reserved keywords //
GOAL :'goal';
DEFINE :'define';
ALWAYS :'always';
CONDITION : 'condition';
SEQUENCE  : 'sequence' | 'SEQUENCE';
MODEL     : 'model';
ORDER     : 'order';
INCLUDE   : 'include' | 'INCLUDE' | 'Include';
/// reserved vars ///
WATERYEAR : 'wateryear';
MONTH : 'month';

/// reserved constants ///
MON_CONST : 'JAN'|'FEB'|'MAR'|'APR'|'MAY'|'JUN'|'JUL'|'AUG'|'SEP'|'OCT'|'NOV'|'DEC';

/// reserved functions ///
fragment MON_VAR: 'Jan'|'Feb'|'Mar'|'Apr'|'May'|'Jun'|'Jul'|'Aug'|'Sep'|'Oct'|'Nov'|'Dec'; 
PREV_MON : 'prev' ( MON_VAR | MON_CONST ); // need to force single format


///units///
TAF :'\''    ('TAF'  |'taf')     '\'';
CFS :'\''    ('CFS'  |'cfs')     '\'';
ACRES :'\''  ('ACRES'|'acres') '\'';
IN :'\''  ('IN'|'in') '\'';
NONE :'\''  'NONE' '\'';

///basics///
QUOTE_STRING_with_MINUS : '\'' IDENT ( '-' | IDENT )+ '\'';
IDENT : LETTER (LETTER | DIGIT | SYMBOLS )*;

WS : (' ' | '\t' | '\n' | '\r' | '\f')+ {$channel = HIDDEN;};
COMMENT : '!' .* ('\n'|'\r') {$channel = HIDDEN;};
