(*DIEGO SUAREZ RAMOS - diego.suarez.ramos@udc.es*)
let rec pow x y=
	if y=0 then 1 else x*((pow x) (y-1));;
let power x y=
	if(x<0) then raise (Failure "power") else (pow x y);;


(*ahora definimos la funcion de manera eficiente, y es mas eficiente porque hacemos la mitad de llamadas a la pila, lo que significa que la pila aguantará más sin llenarse, porque al ser recursividad no terminal podemos tener stack overflow, y con la segunda llamada, podemos tener el doble de valores que en la primera, aunque de poco sirve aunque al estar operando en int y y no Z, llegara un punto en el que todos los power dean 0 o se reseten los valores al pasar de int_max al estar trabajando es un espacio modular así que tampoco tiene mucho sentido*)
let rec par x z=if z=0 then 1 else (x*x)*((par x) (z-1));;
let rec impar x z= if z=0 then 1 else x*(x*x)*((impar x) (z-1));;

let pow' x y= 
	let n=y/2 in
	if (y mod 2)= 0 then ((par x) n) else ((impar x) n);;

let power' x y=
	if(x<0) then raise(Failure "power'") else (pow' x y);;

	

let rec parf x z=if z=0 then 1. else (x*.x)*.((parf x) (z-1));;
let rec imparf x z= if z=0 then 1. else x*.(x*.x)*.((imparf x) (z-1));;

let powf x y= 
	let n=y/2 in
	if (y mod 2)= 0 then ((parf x) n) else ((imparf x) n);;

let powerf x y=
	if(x<0.) then raise (Failure "powerf") else (powf x y);;