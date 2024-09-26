#load "talf.cma";;
open Conj;;
open Auto;;
open Ergo;;
open Graf;;
let escaner_af cadena (Af (_, _, inicial, _, finales) as a) =

  let rec aux = function

       (Conjunto [], _) ->
          false

     | (actuales, []) ->
          not (es_vacio (interseccion actuales finales))

     | (actuales, simbolo :: t) ->
          aux ((epsilon_cierre (avanza simbolo actuales a) a), t)

  in
     aux ((epsilon_cierre (Conjunto [inicial]) a), cadena)
  ;;

let escaner_afn cadena (Af (_, _, inicial, _, finales) as a) =
  let rec aux = function
    (Conjunto [], _) ->false
    | (actuales, []) ->not (es_vacio (interseccion actuales finales))
    | (actuales, simbolo :: t) ->aux ((avanza simbolo actuales a), t)
  in aux ((Conjunto [inicial]), cadena);;

  
let escaner_afd cadena (Af (_, _, inicial, _, finales) as a) =
  let avanza_afd simbolo estado (Af (_, _, _, Conjunto arcos, _)) =
    let rec aux = function
      [] -> raise (Failure "notAFD")
      | Arco_af (origen, destino, s) :: t ->
        if (s = simbolo) && (origen = estado) then
          destino
        else
          aux t
    in aux arcos in
  let rec aux = function
    | (actual, []) -> (pertenece actual finales)
    | (actual, simbolo :: t) -> try aux ((avanza_afd simbolo actual a), t) with _ -> false
  in aux (inicial, cadena);;

  let afne = af_of_string "0 1 2 3 4; a b; 0; 2; 0 3 a; 0 1 epsilon; 3 4 b; 4 1 epsilon; 4 0 b; 1 2 epsilon; 1 2 a;";;
  let afd =af_of_string  "0 23 124 2 012; a b; 0; 0 23 124 2 012; 0 23 a; 23 124 b; 124 2 a; 124 012 b; 012 23 a;";;
  let afn = af_of_string "0 1 2 3 4; a b; 0; 0 2 1 4; 0 3 a; 0 2 a; 3 4 b; 4 1 b; 4 0 b; 4 2 b; 1 2 a; 4 2 b; 3 1 b; 3 2 b;";;
  
  escaner_af [Terminal "a"] afne;;
  escaner_afn [Terminal "a"] afn;;
  escaner_afd [Terminal "a"] afd;;
  escaner_afn [Terminal "a"] afne;;
  escaner_afd [Terminal "a"] afn;;
