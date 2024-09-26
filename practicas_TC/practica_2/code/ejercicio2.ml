#load "talf.cma";;
open Conj;;
open Auto;;
open Ergo;;
open Graf;;


let es_fnc (Gic(notTerminal,terminal, Conjunto reglas, _)) =
  let rec aux = function
  []-> true
  | Regla_gic(No_terminal a, [No_terminal b; No_terminal c])::t -> if ((pertenece (No_terminal a) notTerminal) && (pertenece (No_terminal b) notTerminal ) && (pertenece (No_terminal c) notTerminal )) then
     (aux t) else false
  | Regla_gic(No_terminal a, [Terminal b])::t ->if ((pertenece (No_terminal a) notTerminal) && (pertenece (Terminal b) terminal )) then
    (aux t) else false
  | _ -> false
  in aux reglas;;

  let cyk cadena (Gic(_,_, Conjunto reglas, inicial) as gramatica) =
    let obtener_cabeza_regla_simple b reglas_lista=
      let rec aux conj = function
      []-> conj
      | Regla_gic(a, [d])::t-> if (b=d) then (aux (agregar a conj) t) else (aux conj t)
      | _::t -> aux conj t
      in aux conjunto_vacio reglas_lista in
  
    let obtener_cabeza_regla b c reglas_lista= 
      let rec aux conj = function
      []-> conj
      | Regla_gic(a, [d;e])::t-> if (b=d) && (c=e)  then (aux (agregar a conj) t) else (aux conj t)
      | _::t-> aux conj t
      in aux conjunto_vacio reglas_lista in
  
    let anadir_nuevos_noterminales (Conjunto pos1) (Conjunto pos2)= (*no funciona correctamente*)
      let rec aux sim1 conj =function
        [] -> conj
        | h::t -> aux sim1 (union conj (obtener_cabeza_regla sim1 h reglas)) t in
      let rec aux1 conjB conj= function
        []-> conj
        | h::t ->  aux1 conjB (aux h conj conjB ) t in
        aux1 pos2 conjunto_vacio pos1 in
  
    let ejecutar nivel1 j_niveles=
      let m = Array.make_matrix (j_niveles) (j_niveles) conjunto_vacio in
      for i=0 to (j_niveles-1) do
        m.(i).(0) <- List.nth nivel1 i
      done;
  
      for j=1 to (j_niveles-1) do
        for i=0 to ((j_niveles-(j+1)+1)-1) do
          let nij = ref conjunto_vacio in
          for k=0 to (j-1) do
            nij := union (!nij) (anadir_nuevos_noterminales (m.(i).(k)) (m.(i+(k+1)).(j-(k+1))))
          done;
          m.(i).(j) <- !nij
        done
      done;
      (pertenece inicial (m.(0).(j_niveles-1))) in
    let primera_iter = (*devolverá una lista que sera el primer nivel de nuestra estructura*)
      let rec aux acu j_niveles =function
      []-> (ejecutar acu (j_niveles))
      | h::t -> aux (acu @ [(obtener_cabeza_regla_simple h reglas)]) (j_niveles+1) t
      in aux [] 0 cadena in
      if (es_fnc gramatica) then primera_iter else false;;






(*EJEMPLOS*)
let g = gic_of_string "S A B C; a b; S;
S -> A B | B C;
A -> B A | a;
B -> C C | b;
C -> A B | a;";;

let c1 = cadena_of_string "b b a b";;

let c2 = cadena_of_string "b b b b";;

cyk c1 g;;
cyk c2 g;;