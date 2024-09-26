(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)
(*comp : (’a -> ’b) -> (’c -> ’a) -> (’c -> ’b)*)

let comp f1 f2= function a-> (f1(f2 a));;

let f = let square x = x * x in comp square ((+) 1);;

(*Esta funcion lo que hace es primero suma 1 al numero que pasamos
y luego multiplica ese resultado por si mismo *)

f 1;;
(*int =4*)
f 2;;
(*int =9*)
f 3;;
(*int =16*)