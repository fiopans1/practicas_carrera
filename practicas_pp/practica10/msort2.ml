(*ORIGINAL:*)
let rec divide l = match l with
h1::h2::t -> let t1, t2 = divide t in (h1::t1, h2::t2)
| _ -> l, [];;

(*let rec merge = function
[], l | l, [] -> l
| h1::t1, h2::t2 -> if h1 <= h2 then h1 :: merge (t1, h2::t2)
else h2 :: merge (h1::t1, t2);;

let rec msort1 l = match l with
[] | _::[] -> l
| _ -> let l1, l2 = divide l in
merge (msort1 l1, msort1 l2);;*)
(*CON CUALQUIER ORDEN:*)

let rec merge ord = function
  [], l | l, [] -> l
  | h1::t1, h2::t2 -> if (ord h1) h2 then h1 :: merge ord (t1, h2::t2)
                      else h2 :: merge ord (h1::t1, t2);;

let rec msort1 ord l = match l with
  [] | _::[] -> l
  | _ -> let l1, l2 = divide l in
        merge ord (msort1 ord l1, msort1 ord l2);;

(*La función m_sort no hace falta que sea terminal porque va a tardar mucho end ar stach overflow.
ESO SI, tenemos que tener divide y merge recursivos terminales porque básicamente a m_sort se hacen
pocas llamadas recursivas, pero a divide y a merge por cada llamada de m_sort se hacen muchísimas llamadas
a estas, por ejemplo divide con una lista de 300000 elementos ya da stack overflow*)
(*CON DIVIDE Y MERGE TERMINALE RECURSIVOS:*)
let rec divid l (l1,l2)= match l with
    h1::h2::t-> divid t ((h1::l1),(h2::l2))
    | l-> (List.rev (List.rev_append (List.rev l) l1)), List.rev(l2)
let divide' l= divid l ([],[]);;

let merge' ord (l1, l2) =
  let rec aux (a1, a2) mer = match a1, a2 with
    [], l | l, [] -> List.rev_append mer l
    | h1::t1, h2::t2 -> if ord h1 h2 then aux (t1, h2::t2) (h1::mer)
    else aux (h1::t1, t2) (h2::mer)
  in aux (l1, l2) [];;

let rec msort2 ord l = match l with
  [] | _::[] -> l
  | _ -> let l1, l2 = divide' l
in merge' ord (msort2 ord l1, msort2 ord l2);;

let rec randomlist' n x l1=
  if(x<n) then (randomlist' n (x+1) ((Random.int 10000)::l1)) else l1;;
  let randomlist n= randomlist' n 0 [];;

  let rec qsort2 ord =
    let append1 l1 l2 = List.rev_append (List.rev l1) l2 in
    function
    [] -> []
    | h::t -> let after, before = List.partition (ord h) t in
    append1 (qsort2 ord before) (h :: qsort2 ord after);;

(*Para qsort2 *)
let f n= function x->x>n;;
let t1=Sys.time();;
let l2=qsort2 f (randomlist 10000);;
let t2=Sys.time();;
let tiempo1=t2-.t1;;
(*Para msort1 *)
let f n= function x->x>n;;
let t1=Sys.time();;
let l2=msort1 f (randomlist 10000);;
let t2=Sys.time();;
let tiempo2=t2-.t1;;
(*Para msort2 *)
let f n= function x->x>n;;
let t1=Sys.time();;
let l2=qsort2 f (randomlist 10000);;
let t2=Sys.time();;
let tiempo3=t2-.t1;;

(*val tiempo1 : float = 0.0239830000000011978
val tiempo2 : float = 0.0167049999999999699
val tiempo3 : float = 0.0177009999999988565*)


(*La lista l2 es:*)

let fromto m n =
  let rec auxl m n l =
    if n < m
      then l
      else auxl m (n - 1) (n::l)
  in auxl m n [];;

let l2 = fromto 1 256000;;
let fromto m n =
  let rec auxl m n l =
    if n < m
      then l
      else auxl m (n - 1) (n::l)
  in auxl m n [];;

let l2 = fromto 1 256000;;
