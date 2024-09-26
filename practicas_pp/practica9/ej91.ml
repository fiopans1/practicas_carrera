(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)

(*let rec to0from n =
  if n < 0 then []
    else n :: to0from (n-1);;*)

let rec to0fr n l1=
    if n<0 then List.rev(l1)
    else to0fr (n-1) (n::l1);; 
let to0from n= to0fr n [];;
(**************************************************** *)
(*let rec fromto m n =
  if m > n then []
  else m :: fromto (m+1) n;;*)
let rec fromt m n l1=
  if m>n then List.rev(l1)
  else fromt (m+1) n (m::l1);;
let fromto m n= fromt m n [];;
(**************************************************** *)
(*let rec from1to n =
  if n < 1 then []
  else from1to (n-1) @ [n];;*)
let rec from1 n l1=
  if n<1 then l1
  else from1 (n-1) (List.rev_append(List.rev l1) [n]);;
let from1to n= from1 n [];;
(**************************************************** *)
(*let map =
  List.map;; *)
let rec ma f l l1= match l with
  []->List.rev(l1)
  | h::t -> ma f t ((f h)::l1);;
let map f l= ma f l [];;
(**************************************************** *)
(*let power x y =
  let rec innerpower x y =
  if y = 0 then 1
  else x * innerpower x (y-1)
  in
  if y >= 0 then innerpower x y
  else invalid_arg "power";;*)
let power x y =
  let rec innerpower x y z=
  if y = 0 then z
  else innerpower x (y-1) (x*z)
  in
  if y >= 0 then innerpower x y 1
  else invalid_arg "power";;
  (**************************************************** *)
  (*let incseg l =
   List.fold_right (fun x t -> x::List.map ((+) x) t) l [];;*)

    let incseg l = 
      let rec aux l acc l2 = match l with
        [] -> []
        | [h] -> List.rev ((h + acc)::l2)
        | h::t -> aux t (h + acc) ((h + acc)::l2)
      in aux l 0 [];;
  (**************************************************** *)
(*let rec remove x = function
[] -> []
| h::t -> if x = h then t
else h :: remove x t;;*)
let rec remo x l l1= match l with
  []->List.rev(l1)
  | h::t->if x=h then List.rev_append l1 t
  else remo x t (h::l1);; 
  let remove x l= remo x l [];;
(**************************************************** *) 
(*let rec divide = function
  h1::h2::t -> let  l1, l2 = divide t
    in h1::l1,h2::l2
  | l -> l, [];;*)
let rec divid l (l1,l2)= match l with
    h1::h2::t-> divid t ((h1::l1),(h2::l2))
    | l-> (List.rev (List.rev_append (List.rev l) l1)), List.rev(l2)
let divide l= divid l ([],[]);;
(**************************************************** *) 
(*let rec compress = function
| h1::h2::t -> if h1 = h2 then compress (h2::t)
    else h1 :: compress (h2::t)
| l -> l;;*)
let rec compr l l1=match l with
    h1::h2::t-> if(h1=h2) then compr (h2::t) l1
    else compr (h2::t) (h1::l1)
  | l-> (List.rev (List.rev_append (List.rev l) l1));;
let compress l= compr l [];;

