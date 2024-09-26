(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)
let rec remove n l= match l with
  [] -> []
  | h::t -> if (n = h) then t else h::(remove n t);;
(************************************************************)
  let rec remove_all n l= match l with
  [] -> []
  | h::t -> if (n = h) then (remove n t) else h::(remove n t);;
  (************************************************************)
  let rec ldif1 l1 l2 l3= match l1 with
  []->l3
  | h::t->(if (List.mem h l2) then (ldif1 t l2 (remove_all h l3)) else (ldif1 t l2 l3));;
  let ldif l1 l2= ldif1 l1 l2 l1;;
    (************************************************************)
    let rec lpro1 h1 l2 = match l2 with
      [] -> []
      | h::t -> (h1, h)::(lpro1 h1 t);;
    let rec lprod l1 l2 = match l1 with
    [] -> []
    | h::t ->List.append (lpro1 h l2) (lprod t l2);;
   (************************************************************)
   let rec divid l (l1,l2)= match l with
   h1::h2::t-> divid t ((h1::l1),(h2::l2))
   | l-> (List.rev (List.rev_append (List.rev l) l1)), List.rev(l2)
  let divide l= divid l ([],[]);;