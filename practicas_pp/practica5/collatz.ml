(*DIEGO SUAREZ RAMOS - diego.suarez.ramos@udc.es*)
let f n = if n mod 2 = 0 then n / 2 else 3 * n + 1;;

let rec g n=
	let x = f n in
	if(x=1) then (print_int(n);print_string(",");print_int(1);print_string("\n")) else (print_int(n);print_string(",");g x);;

let orbit n=
if n>0 then g n else print_string("Argumento invalido\n");;

let rec len n q=
	let x=f n in
		if(x=1) then q+1 else ( (len x) (q+1));;
let length n=
	if n>0 then (len n 0)
		else 0;;
		
let rec ti n z=
	let x= f n in
		if (x=1) then  z else( if(x>z) then ((ti x) x) else ((ti x) z)  );;

let top n=
	if n>0 then (ti n 0) else 0;;
	

let rec lyt n z y=
	let x= f n in
		if(x=1) then ((y+1),z) else (if x>z then (lyt x x (y+1)) else (lyt x z (y+1)  ));;
let length'n'top z=
	if(z>0) then (lyt z 0 0) else 0,0;;


let rec mayor n m y=
	let z=length n in
		if(n<=y) then (if(z>length(m)) then (mayor (n+1) n y) else (mayor (n+1) m y)) else m;;
let longest_in x y=
	if(x<y) then (mayor x 0 y) else 0;;
	

let rec higth n m y=
	let z=top n in
		if(n<=y) then (if(z>top(m)) then (higth (n+1) n y) else (higth (n+1) m y)) else m;;

let highest_in x y=
	if(x<y) then (higth x 0 y) else 0;;
