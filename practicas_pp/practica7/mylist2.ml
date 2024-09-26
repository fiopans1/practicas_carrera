(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)
let hd l= match l with
  []->raise(Failure "hd")
  | h::_->h;;

(************************************************************)
let tl l= match l with
  []->raise(Failure "tl")
  | _::t->t;;
(************************************************************)
let rec len l n= match l with
    []->n
    | _::t-> len t (n+1);;

let length l=
        len l 0;;
(************************************************************)
let rec compare_lengths l1 l2=match l1,l2 with
  [],h::t->(-1)
  | h::t,[]->1
  | [],[]->0
  | _::t,_::t2-> compare_lengths t t2;;
(************************************************************)
let rec nthi l n p= 
  match l with
      []->raise(Failure "nth")
      | h::[]-> h
      | h::t -> if( p=n) then h else (nthi t n (p+1));;
let nth l n= 
    if(n<length l) then (nthi l n 0) else raise(Failure "nth");;
(**************************************************************)
let rec append l1 l2= match l1 with
  []->l2
  | h::t -> h::(append t l2);;
(**************************************************************)
let rec find f l= match l with
    []->raise(Not_found)
    | h::t-> if(f h) then h else find f t;;
(**************************************************************)
let rec for_alli f l= match l with
    []->true
    | h::t-> if(f h) then (for_alli f t) else false;;
let for_all f l= match l with
    []->raise Not_found
    | h::t-> (for_alli f l);;
(**************************************************************)
let rec exists f l= match l with
    []->false
    | h::t-> if(f h) then true else (exists f t);;
(**************************************************[*]***********)
let rec mem n l= match l with
  []->false
  | h::t->if(h=n) then true else (mem n t);;
(**************************************************************)
let rec re l l1= match l with
  []->l1
  | h::t->re t (append [h] l1);;
let rev l= re l [];;
(**************************************************************)
let rec fil f l l1= match l with
  []->rev(l1)
  | h::t->if(f h) then (fil f t (h::l1)) else (fil f t l1);;
let filter f l= (fil f l []);;
(**************************************************************)
let find_all f l= filter f l;;
(**************************************************************)
let rec parti f l l1 l2= match l with
  []->(rev(l1),rev(l2))
  | h::t-> if(f h) then (parti f t (h::l1) l2) else (parti f t l1 (h::l2));;
let partition f l= match l with
  []->raise(Failure "partition")
  | h::t-> parti f l [] [];;
(**************************************************************)
let rec spl l l1 l2= match l with
  []->(rev(l1),rev(l2))
  | h::t-> spl t (fst(h)::l1) (snd(h)::l2);;
let split l= spl l [] [];;
(**************************************************************)
let rec combi l1 l2 l3= match l1,l2 with
  [],[]->rev(l3)
  | h::t,h1::t1-> combi t t1 ((h,h1)::l3)
  | _->raise(Failure "caso imposible");;
let combine l1 l2= 
  if((length l1)=(length l2)) then (combi l1 l2 []) else raise(Invalid_argument "combine");;
(**************************************************************)
let rec ini f n p l1=
  if(p<n) then ini f n (p+1) ((f p)::l1) else  rev(l1);;  
let init n f= if(n<0) then raise(Invalid_argument "init") else (ini f n 0 []);; 

(**************************************************************)
let rec rev_append l1 l2= match l1 with
  []->l2
  | h::t->rev_append t (h::l2);;
(**************************************************************)
let rec conca l1 l2= match l1 with
  []->l2
  | h::t->conca t (rev_append (rev l2) h);;

let concat l= conca l [];;
(**************************************************************)
let flatten l= concat l;;
(**************************************************************)
let rec ma f l l1= match l with
  []->rev(l1)
  | h::t -> ma f t ((f h)::l1);;
let map f l= ma f l [];;
(**************************************************************)
let rev_map f l= map f (rev l);;
(**************************************************************)
let rec ma1 f l1 l2 l3= match l1,l2 with
  [],[]->rev(l3)
  | h::t,h1::t1-> ma1 f t t1 ((f h h1)::l3)
  | _->raise(Failure "caso imposible");;
let map2 f l1 l2=
  if((length l1)= (length l2)) then (ma1 f l1 l2 []) else  raise(Failure "map2");;
(**************************************************************)

  let rec fold_left f a l = match l with
  [] -> a
  | h::t -> fold_left f (f a h) t;;
(**************************************************************)
let rec fold_right f l b = match l with
  [] -> b
  | h::t -> f h (fold_right f t b);;