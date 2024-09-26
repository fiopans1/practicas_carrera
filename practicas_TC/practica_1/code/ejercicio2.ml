#load "talf.cma";;
open Conj;;
open Auto;;
open Ergo;;
open Graf;;


let equivalentes (Af(estados1,alfabeto1,iniciales1,arcos1, finales1) as arbol1) (Af(estados2,alfabeto2,iniciales2,arcos2, finales2) as arbol2) =
  let conjuntos_iguales (Conjunto con1) conj2=
    let rec aux= function
      []->true
      | h::t-> if (pertenece h conj2) then aux t else false (*cola y estados visitados serán una lista de conjuntos]*)
    in aux con1 in
  let pertenece_cierre estado (Af(estados,alfabeto,iniciales,arcos, finales) as arbol)=
      let rec aux= function
      []-> false
      | h::t -> if (pertenece h finales) then true else aux t
    in aux (list_of_conjunto (epsilon_cierre (Conjunto [estado]) arbol)) in
  let pertenece_avance (Conjunto estados) arbol=
    let rec aux = function
      []->false
      | h::t -> if (pertenece_cierre h arbol) then true else aux t
    in aux estados in
  let avanza_con_epsilon simbolo estados (Af (_, _, _, Conjunto arcos, _) as arbol) =
    let rec aux estados_origen destinos = function
      [] -> destinos
      | Arco_af (origen, destino, s) :: t ->
        if (s = simbolo) && (pertenece origen estados_origen) then
          aux estados_origen (agregar destino destinos) t
        else
          aux estados_origen destinos t
    in
     aux (epsilon_cierre estados arbol) conjunto_vacio arcos in
  let nuevos_estados arbol1 arbol2 estado_actual1 estado_actual2 cola (Conjunto alfabeto)=
    let rec aux col = function
      []-> col (*crear nueva funcion para substituir a avanza_con_epsilon que trabaje con grupos de estados*)
      | h::t -> aux (col @ [((avanza_con_epsilon h estado_actual1 arbol1),(avanza_con_epsilon h estado_actual2 arbol2))]) t
    in aux cola alfabeto in

  let rec aux estados_visitados = function
    []->true
    | (estado_actual1,estado_actual2)::t ->
      if (not (pertenece (estado_actual1, estado_actual2) estados_visitados)) then
        let pertact1 = (pertenece_avance estado_actual1 arbol1) in
        let pertact2 = (pertenece_avance estado_actual2 arbol2) in
        if (((pertact1) && not (pertact2)) || (not (pertact1) && (pertact2))) then
          false
        else
          aux (agregar (estado_actual1,estado_actual2) estados_visitados) (nuevos_estados arbol1 arbol2 estado_actual1 estado_actual2 t alfabeto1)
      else aux estados_visitados t
    in 
      if (conjuntos_iguales alfabeto1 alfabeto2) then
        aux conjunto_vacio [(Conjunto [iniciales1],Conjunto [iniciales2])]
      else false;;


      
      

      let afd = Af(Conjunto [Estado "q0"; Estado "q1q2" ; Estado "q3";
        Estado "q2"], Conjunto [Terminal "a"; Terminal "b"],
        Estado "q0", Conjunto [Arco_af(Estado "q0", Estado "q1q2", Terminal "a");
        Arco_af(Estado "q1q2", Estado "q3", Terminal "b");
        Arco_af(Estado "q3", Estado "q2", Terminal "a");
        Arco_af(Estado "q2", Estado "q3", Terminal "b")],
        Conjunto [Estado "q1"; Estado "q3"]);;

        let afn = Af(Conjunto [Estado "q0"; Estado "q1" ; Estado "q2";
        Estado "q3"], Conjunto [Terminal "a"; Terminal "b"],
        Estado "q0", Conjunto [Arco_af(Estado "q0", Estado "q1", Terminal "a");
        Arco_af(Estado "q0", Estado "q2", Terminal "a");
        Arco_af(Estado "q2", Estado "q3", Terminal "b");
        Arco_af(Estado "q3", Estado "q2", Terminal "a")],
        Conjunto [Estado "q1q2"; Estado "q3"]);;


  let arbol11 = af_of_string "0 1 2 3 4; a b; 0; 2; 0 3 a; 0 1 epsilon; 3 4 b; 4 1 epsilon; 4 0 b; 1 2 epsilon; 1 2 a;";;
  let arbol12 =af_of_string  "0 23 124 2 012; a b; 0; 0 23 124 2 012; 0 23 a; 23 124 b; 124 2 a; 124 012 b; 012 23 a;";;
  let arbol13 = af_of_string "0 1 2 3 4; a b; 0; 0 2 1 4; 0 3 a; 0 2 a; 3 4 b; 4 1 b; 4 0 b; 4 2 b; 1 2 a; 4 2 b; 3 1 b; 3 2 b;";;

  let arbol21= af_of_string "0 012 12 2; a b c; 0; 0 012 12 2; 0 012 a; 0 12 b; 0 2 c; 012 012 a; 012 2 c; 012 12 b; 12 12 b; 12 2 c; 2 2 c;";;
  let arbol22= af_of_string "0 1 2; a b c; 0; 0 1 2; 0 0 a; 0 1 a; 0 1 b; 0 2 a; 0 2 b; 0 2 c; 1 1 b; 1 2 b; 1 2 c; 2 2 c;";;
  let arbol23 = af_of_string "0 1 2; a b c; 0; 2; 0 1 epsilon; 1 2 epsilon; 0 0 a; 1 1 b; 2 2 c;";;


