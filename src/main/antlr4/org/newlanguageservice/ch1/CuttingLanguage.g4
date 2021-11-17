grammar CuttingLanguage;

actions
:
	(
		action
		| variable_definition
	)+
;

variable_definition
:
	VAR variableName = ID COLON type = PRIMITIVE_TYPE
	((
		'=' expr = expression
	)?)
;


variable_equal:variable_ref EQUAL expr=expression;


expression
:
	mul_div_expression
;

mul_div_expression
:
	add_or_sub
	((
		(op = MUL
		| DIV) expression
	)*)
;

add_or_sub
:
	sub_element
	((
		(op = PLUS
		| MINUS) expression
	)*)
;

sub_element
:
	brackets_expression
	| variable_ref|INT
;

brackets_expression
:
	L_BRACKET expr = expression R_BRACKET
;

variable_ref
:
	var_name_ref = ID
;

action
:
	moveTo
	| lineTo
;

moveTo
:
	moveToName = 'MoveTo' '(' x = INT ',' y = INT ')'
;

lineTo
:
	lineToName = 'LineTo' '(' x = INT ',' y = INT ')'
;

EQUAL:'=';

DIV
:
	'/'
;

MUL
:
	'*'
;

PLUS
:
	'+'
;

MINUS
:
	'-'
;

COLON
:
	':'
;

VAR
:
	'var'
;

L_BRACKET
:
	'('
;

R_BRACKET
:
	')'
;

PRIMITIVE_TYPE
:
	INT_TYPE
	| STRING_TYPE
;
ID
:
	[_a-zA-Z]
	(
		[_0-9a-zA-Z]*
	)
;


INT
:
	'-'? DIGIT+
;

fragment
INT_TYPE
:
	'int'
;

fragment
STRING_TYPE
:
	'string'
;

fragment
DIGIT
:
	[0-9]
;

WS
:
	[ \n\r\t] -> skip
; 
