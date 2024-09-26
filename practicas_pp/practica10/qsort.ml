let rec qsort1 ord = function
[] -> []
| h::t -> let after, before = List.partition (ord h) t in
qsort1 ord before @ h :: qsort1 ord after;;

let rec qsort2 ord =
  let append1 l1 l2 = List.rev_append (List.rev l1) l2 in
  function
  [] -> []
  | h::t -> let after, before = List.partition (ord h) t in
  append1 (qsort2 ord before) (h :: qsort2 ord after);;


  let rec randomlist' n x l1=
  if(x<n) then (randomlist' n (x+1) ((Random.int 10000)::l1)) else l1;;
  let randomlist n= randomlist' n 0 [];;

  let rec desord1 n l1=
  if(n>0) then (desord1 (n-1) (n::l1)) else l1;;
  let desord n= desord1 n [];;

  let rec orde1 n x l1=
  if(x<n) then (orde1 n (x+1) (x::l1)) else l1;;
  let orde n= orde1 n 0 [];;



  (*qsort2 solo tiene una ventaja sobre qsort1 y es que permite ordenadar listas
  de mayor tamaño que qsort1 por el tema de ser recursiva terminal, llega un punto en el que con qsort1
   da stack overflow, por ejemplo la lista RANDOM con un millon de elementos qsort2 es capaz de ordenarla mientras
  que qsort1 no es capaz de ordenarlo, la siguiente lista l1, qsort2 es capaz de ordenarlo(aunque tarda unos segundos) y qsort1 no es capaz:*)

  let l1= randomlist 1000000;;

  (*La desventaja de qsort2 sobre qsort1 es que qsort2 es mas lento que qsort1 por
intentar mantener la recursivad terminal, la razón es simplemente por la unión de las listas,
mientras que qsort1 usa el @ que no es recursivo terminal pero si que es bastante eficiente, mientras
que qsort2 si que es recursiva terminal, pero es mas lenta que usar directamente el @ porque usamos
List.rev_append (List.rev l1) l2 ,por ejemplo con una lista random de 600000 elementos
vamos *)
(*Para qsort1*)
let f n= function x->x>n;;
let t1=Sys.time();;
let l2=qsort1 f (randomlist 600000);;
let t2=Sys.time();;
let tiempo1=t2-.t1;;
(*Para qsort2 *)
let f n= function x->x>n;;
let t1=Sys.time();;
let l2=qsort2 f (randomlist 600000);;
let t2=Sys.time();;
let tiempo2=t2-.t1;;
(*# val tiempo1 : float = 1.0140699999999998 *)
(*# val tiempo2 : float = 2.63514*)
(*Como obsevamos en este caso quicksort 2 fue mas de doble de lento(concretamente un 259,86 %)*)



