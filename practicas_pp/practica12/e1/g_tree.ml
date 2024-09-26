type 'a g_tree = Gt of 'a * 'a g_tree list;;

let rec size = function 
    Gt (_,[]) -> 1
  | Gt (r,h::t) -> size h + size (Gt (r,t));;

  let rec height =
    function
    | Gt (_, []) -> 1
    | Gt(_,l)-> 1+List.fold_left max 0 (List.map altura l);;
    (*en esta funcion el fold_left lo que hace es sumar todos los valores
    de una lista, y le pasas como variable la lista de alturas(la lista de nodos
    calculandole la altura*)

let rec mirror= function
  Gt(r,[])->Gt(r,[])
  | Gt(r,t)->Gt(r,(List.rev (List.map mirror t)));;

let rec preorder=function
  Gt(r,[])->[r]
  | Gt(r,(Gt(h,t1))::t)->r :: (preorder (Gt (h,(t1@t))));;

let rec postorder=function
  Gt(r,[])->[r]
  | Gt(r,h::t)-> (postorder h)  @ (postorder (Gt (r,t)));;
  
let rec leaves= function
    Gt(r,[])->[r]
   | Gt(r,h::[])->(leaves h)
   | Gt (r,h::t)->(leaves h)@ (leaves (Gt(r,t)));;


