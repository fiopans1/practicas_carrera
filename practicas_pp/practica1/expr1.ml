https://learntutorials.net/es/ocaml/topic/2730/procesamiento-de-listas

(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)
();;
(*unit = ()*)

2+3*5;;
(*int = 17*)

1.0;;
(*float = 1.0*)

(*1.0*2;;*)
int_of_float(1.0)*2;;
(*da error de sintaxis porque estamos intentando multiplicar un float por un int*)

(*2-2.0*)
2-int_of_float(2.0);;
(*da error de sintaxis porque estamos intentando restar un int y un float*)

(*3.0 + 2.0*)
3.0+.2.0;;
(*da error de sintaxis porque la operacion "+" no es para floats*)

5/3;;
(*int = 1*)

5 mod 3;;
(*int = 2*)

3.0 *. 2.0 *. 3.0;;
(*float = 18.0*)

3.0 = float_of_int 3;;
(*Pensé que iba a dar error al estar igualando un numero a una expresion aunque ese numero
 fuera el resultado, pero como la expresión era cierta al parecer devuelve bool = true*)

(*sqrt 4;;*)
sqrt 4.0;;
(*da error de sintaxis porque es para un tipo float*)


int_of_float 2.1 + int_of_float(-2.9);;
(*int = 0*)

truncate 2.1 + truncate(-2.9);;
(*int = 0*)


(*floor 2.1 + floor(-2.9);;*)
floor 2.1 +. floor(-2.9);;
(*Pensé que iba a dar int=0, pero resulta ser una expresión de floats,
y da error de sintaxis porque faltó poner +. al operador "+"*)

(*ceil 2.1 +. ceil -2.9;;*)
ceil 2.1 +. ceil(-2.9);;
(*da error de sintaxis porque al segundo ceil le faltan los paréntesis al numero negativo*)

2.0 ** 3.0 ** 2.0;;
(*float = 512*)

'B';;
(*char = 'B'*)

int_of_char 'A';;
(*int = 65*)

char_of_int 66;;
(*char = 'B'*)

Char.code 'B';;
(*int = 66*)

Char.chr 67;;
(*char = 'C'*)

'\067';;
(*char = 'C'*)

Char.chr(Char.code 'a' - Char.code 'A' + Char.code 'M');;
(*char = 'm'*)

Char.uppercase_ascii 'm';;
(*avisa que está en desuso*)
(*char = 'M'*)

Char.lowercase_ascii 'O';;
(*avisa que está en desuso*)
(*char = 'o'*)

"this is a string";;
(*string = this is a string*)

String.length "longitud";;
(*int = 8*)

(*"1999" + "1";;*)
int_of_string("1999") + int_of_string("1");;
(*dará error de sintaxis porque + é para ints*)

"1999" ^ "1";;
(*string = 19991*)

int_of_string "1999"+1;;
(*int=2000*)

"\064\065";;
(*string = "@A"*)

string_of_int 010;;
(*string = "10"*)

not true;;
(*bool = false*)

true && false;;
(*bool = false*)

true || false;;
(*bool = true*)

(1<2) = false;;
(*bool = false*)

"1"< "2";;
(*bool = true*)

2<12;;
(*bool = true*)

"2" < "12";;
(*bool = false*)

"uno" < "dos";;
(*bool = false*)

if 3=4 then 0 else 4;;
(*int = 4*)

if 3=4 then "0" else "4";;
(*string = "4"*)

(*if 3=4 then 0 else "4";;*)
if 3=4 then "0" else "4";;
(*da error porque no puedes poner que una de las salidas sea un int y la otra un string, tienen
que ser del mismo tipo*)

(if 4<5 then 8 else 10)+4;;
(*int = 12*)

2.0 *. asin 1.0;;
(*float = 3.14159265....*)

sin (2.0 *. asin 1.0/.2.);;
(*float = 1*)

function x->2*x;;
(*int -> int = <fun>*)

(*(function x-> 2*x) * (2+1);;*)
(*esta expresion no es una funcion, el tipo esperado es un entero, no se como tendria que ser*)

let x=1;;
(*val x : int = 1*)

let y=2;;
(*val y : int = 2*)

x-y;;
(*int = -1*)

let x = y in x-y;;
(*int = 0*)

x-y;;
(*int = -1*)

(*z;;*)
let z="valor"
(*z está sin definir va a dar error de ejecucion*)

let z=x+y;;
(*val z : int = 3;*)

z;;
(*int = 3*)

let x=5;;
(*val x: int = 5*)

z;;
(*int = 3*)

let y = 5 in x+y;;
(*int = 10*)

x+5;;
(*int = 10*)

let x= x+y in let y = x* y in x+y+z;;
(*int = 24*)

x+y+z;;
(*int = 10*)

int_of_float;;
(*float -> int = <fun>*)

float_of_int;;
(*int -> float = <fun>*)

int_of_char;;
(*char -> int = <fun>*)

char_of_int;;
(*int -> char = <fun>*)

abs;;
(*int -> int = <fun>*)

sqrt;;
(*flaot -> float = <fun>**)

truncate;;
(*float -> int= <fun>**)

ceil;;
(*float -> float= <fun>**)

floor;;
(*float -> float= <fun>**)

Char.code;;
(*char -> int= <fun>**)

Char.chr;;
(*int -> char= <fun>**)

Char.uppercase_ascii;;
(*avisa que está en desuso*)
(*char -> char= <fun>**)

Char.lowercase_ascii;;
(*avisa que está en desuso*)
(*char -> char= <fun>**)

int_of_string;;
(*string -> int= <fun>**)

string_of_int;;
(*int -> string= <fun>**)

String.length;;
(*string -> int= <fun>**)

let f = function x-> 2*x;;
(*val f : int -> int = <fun>*)

f(2+1);;
(*int = 6*)

f 2+1;;
(*int 5*)

let n=1;;
(*val n : int = 1*)

let f x = x+n;;
(*val f : int -< int = <fun>*)

(*g 3;;*)
let g=3;;
(*error de ejecucion porque g no tiene valor*)

let n=5;;
(*val n : int = 5*)

(*g 3;;*)
let g=3;;
(*error de ejecucion porque g no tiene valor*)
























































































































