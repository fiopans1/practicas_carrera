(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)
false && (2 / 0 > 0);;
if false then (2/0>0) else false;;
(*bool = true*)
(*true && (2 / 0 > 0);;*)
(*if true then (2/0>0) else false;;*)
(*Dará error de ejecución por dividir entre 0*)

true || (2 / 0 > 0);;
if true then true else (2 / 0 > 0);;
(*bool = true*)

(*false || (2 / 0 > 0);;*)
(*if false then true else (2 / 0 > 0);;*)
(*Dará error de ejecución por dividir entre 0*)

let con b1 b2 = b1 && b2;;
let con b1 b2= if b1 then b2 else false;;
(*val con = bool->bool->bool = <fun>*)


let dis b1 b2 = b1 || b2;;
let dis b1 b2 = if b1 then true else b2;;
(*val dis: bool ->bool ->bool= <fun>*)

(*con (1 < 0) (2 / 0 > 0);;*)
(*Dará error de ejecución porque como en la primera dividir entre 0*)


(1 < 0) && (2 / 0 > 0);;
if (1 < 0) then (2 / 0 > 0) else false;;
(*bool = false*)

(*dis (1 > 0) (2 / 0 > 0);;*)
(*Pensé que iba a dar bool = true pero da un error de ejecución por dividir entre 0*)

(1 > 0) || (2 / 0 > 0);;
if (1 > 0) then true else (2 / 0 > 0);;
(*bool = true*)
