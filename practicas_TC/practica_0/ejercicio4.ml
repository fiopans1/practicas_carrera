   (*funciones auxiliares*)
   let concat l1 l2= List.rev_append (List.rev l1) l2;;
   
   type 'a conjunto = Conjunto of 'a list;;

   let conjunto_vacio = Conjunto [];;

   (*pertenece : 'a -> 'a conjunto -> bool *)

   let rec pertenece a= function
   Conjunto [] -> false
   | Conjunto (h::t) -> if a=h then true else (pertenece a (Conjunto t));;


   (*agregar : 'a -> 'a conjunto -> 'a conjunto*)

   let agregar a= function Conjunto b->
      if (pertenece a (Conjunto b)) then (Conjunto b) else (Conjunto (a::b));;

   (*conjunto_of_list : 'a list -> 'a conjunto*)

   let conjunto_of_list a=
      let rec nonrepetitive_list c= function 
      []->c 
      | h::t -> if (pertenece h (Conjunto c)) then (nonrepetitive_list c t) else (nonrepetitive_list (h::c) t) 
      in Conjunto (nonrepetitive_list [] a);;

   (* suprimir : 'a -> 'a conjunto -> 'a conjunto*)

   let rec suprimir a = function
   Conjunto []->Conjunto []
   | Conjunto b-> let rec supr c r=function
                        []->r
                        | h::t-> if(h=c) then (concat r t) else (supr c (h::r) t) in
      Conjunto (supr a [] b);;

   (*cardinal : 'a conjunto -> int*)

   let cardinal (Conjunto b)= List.length b;;

   (*union : 'a conjunto -> 'a conjunto -> 'a conjunto*)

   let union (Conjunto a) (Conjunto b)= 
   let  rec unionrec res = function
   []-> res
   | h::t -> if (pertenece h (Conjunto res)) then (unionrec res t) else (unionrec (h::res) t)
   in unionrec a b;;

   (* interseccion : 'a conjunto -> 'a conjunto -> 'a conjunto*)

   let interseccion (Conjunto a) (Conjunto b)= match a,b with
   [],_ | _,[] -> conjunto_vacio
   | a,b -> let rec inter i b= function
   []-> i
   | h::t -> if (pertenece h (Conjunto b)) then  (inter (h::i) b t) else (inter i b t) in
   Conjunto (inter [] b a);;


   (* diferencia : 'a conjunto -> 'a conjunto -> 'a conjunto*)
   let diferencia (Conjunto a) (Conjunto b)= match a,b with
   a,[]-> Conjunto a | [],_ -> conjunto_vacio
   | a,b -> let rec dif i b= function
   []-> i
   | h::t -> if (pertenece h (Conjunto b)) then  (dif i b t) else (dif (h::i) b t) in
   Conjunto (dif [] b a);;

   (* incluido : 'a conjunto -> 'a conjunto -> bool*) 
   
   let incluido (Conjunto a) b= let rec incl z= function
   [] -> true
   | h::t -> if (pertenece h z) then (incl z t) else false in
   (incl b a);;

   (*igual : 'a conjunto -> 'a conjunto -> bool*)

   let igual (Conjunto l1) (Conjunto l2) = let rec equals = function 
   [],[]->true
   | [],_ | _,[] -> false
   | h1::t1,h2::t2 -> if(h1=h2) then (equals (t1,t2)) else false in
   (equals (l1,l2));;

   (* producto_cartesiano : 'a conjunto -> 'b conjunto -> ('a * 'b) conjunto*)
   let producto_cartesiano (Conjunto l1) (Conjunto l2)= let rec prc lis guard=
    let rec pr1 a res= function
    []-> res
    | h::t -> (pr1 a ((a,h)::res) t) in function
    []-> guard
    | h::t -> prc lis (concat (pr1 h [] lis) guard) t in
    (Conjunto (prc l2 [] l1));;
   
   (* list_of_conjunto : 'a conjunto -> 'a list*)
   
   let list_of_conjunto (Conjunto l1) = l1;;

   (*
   let a = Conjunto [1;2;3;4;5;6;7;8;9;10];;
   let b = Conjunto [11;12;13;14;15;16;8;9;17];; 
   incluido (Conjunto [1;2;3;4;5]) a;; ->true
   incluido (Conjunto [1;2;3;4;20]) a;; ->false
   *)