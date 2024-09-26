(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)
let rec mc x y=
if(x=0 || y=0) then 0 else
 (if (x >= 0 && y >0) then 
 (if (x mod y)=0 then y else ((mc y)(x mod y))) else 
 0);;

let mcd (x,y)=
if(x=0 && y=0) then raise(Failure "mcd") else mc x y;;

