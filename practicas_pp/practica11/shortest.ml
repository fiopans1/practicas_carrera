(*DIEGO SUAREZ RAMOS- diego.suarez.ramos@udc.es*)
let nofuevisitado l e=
  not(List.mem e l);;

let limit m n (x,y)=
  (x>=1 && x<=m && y>=1 && y<=n);;

let todoslosmovimientos (x,y) = [x+1,y+2; x+2,y+1; x-1,y+2; x-2,y+1;
  x+1,y-2; x+2,y-1; x-1,y-2; x-2,y-2];;

let movimientolegal m n (x,y) visits = List.filter (nofuevisitado visits) (List.filter (limit m n) (todoslosmovimientos (x,y)));;

let rec aux m n (x,y) l= function
[]->raise(Not_found)
| h::t-> if(h=(x,y))
  then List.rev(h::l)
  else try aux m n (x,y )(h::l) (movimientolegal m n h (h::l))
with Not_found->aux m n (x,y) l t;;

let rec shortest_tour m n (i,j) (x,y)=
  if(not(limit m n (i,j)) || not(limit m n (x,y))) then
      raise(Invalid_argument "tour") else
    if (limit m n (i,j)) then (aux m n (x,y) [] [i,j])
    else raise(Not_found);;