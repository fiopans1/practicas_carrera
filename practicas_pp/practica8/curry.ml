(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)
(*curry : ((’a * ’b) -> ’c) -> (’a -> (’b -> ’c))*)
let curry f=fun a b-> (f (a,b));;

let uncurry f= fun (a,b)->(f a) b;;

uncurry (+);;
(*int * int -> int = <fun>*)
let sum = (uncurry (+));;
(*val sum : int * int -> int = <fun>*)
(*sum 1;;*)
sum (1,0);;
(*Va a saltar error porque espera un tipo int*int y esto es un tipo int*)
sum (2,1);;
(*int = 3*)
let g = curry (function p -> 2 * fst p + 3 * snd p);;
(*val g : int -> int -> int = <fun>*)
(*g (2,5);;*)
g 2 5;;
(*Va a saltar error porque espera un int->int y le metemos un int*int*)
let h = g 2;;
(*val h : int-> int <fun>*)
h 1, h 2, h 3;;
(*int * int * int = (7,10,13)*)