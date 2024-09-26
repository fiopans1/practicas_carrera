type token =
  | LAMBDA
  | TRUE
  | FALSE
  | IF
  | THEN
  | ELSE
  | SUCC
  | PRED
  | ISZERO
  | LET
  | LETREC
  | FIX
  | IN
  | CONCAT
  | LENGTH
  | COMPARE
  | BOOL
  | NAT
  | STRING
  | AS
  | CASE
  | OF
  | LIST
  | NIL
  | CONS
  | ISNIL
  | HEAD
  | TAIL
  | LPAREN
  | RPAREN
  | DOT
  | EQ
  | COLON
  | ARROW
  | LBRACE
  | RBRACE
  | EOF
  | COMMA
  | LBRACKET
  | RBRACKET
  | GT
  | LT
  | OR
  | ID of (string)
  | INTV of (int)
  | IDV of (string)
  | IDV2 of (string)
  | STRINGV of (string)

val s :
  (Lexing.lexbuf  -> token) -> Lexing.lexbuf -> Lambda.command
