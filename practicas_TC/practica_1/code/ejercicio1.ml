#load "talf.cma";;
open Conj;;
open Auto;;
open Ergo;;
open Graf;;
(*coge la lista de estados y simbolos y mira que todos pertenezcan al arbol de manera correcta*)
let pertenecen_estados_simbolos (Af(estados,simbolos,_,Conjunto arcos,_))=
  let rec aux est sim= function
    []-> true
    | (Arco_af (origen, destino, simbolo))::t -> if (pertenece origen est) && (pertenece destino est) && ((simbolo=Terminal "") || (pertenece simbolo sim)) then aux est sim t else false
  in aux estados simbolos arcos;; 

(*****************************************************************************)

(*****************************************************************************)
let es_afne (Af(estados,simbolos,_, Conjunto arcos, _)) =
  let rec aux = function
  [] -> false
  | (Arco_af (a, b, Terminal ""))::t -> if (pertenece a estados) && (pertenece b estados) then true else false
  | (Arco_af (a, b, s))::t ->if (pertenece a estados) && (pertenece b estados) && (pertenece s simbolos) then aux t else false
  in aux arcos;;  
(*****************************************************************************)
let es_afn (Af(estados,simbolos,_, Conjunto arcos, _)) =
  let rec aux1 ori sim des= function
  []-> false
  | (Arco_af (Estado origen,Estado destino, Terminal simbolo))::t -> if (origen=ori && simbolo=sim && destino<>des) then true
    else aux1 ori sim des t
  | _ -> false
  in let rec aux ars= function
  [] -> false
  | (Arco_af (Estado origen,Estado destino, Terminal simbolo))::t -> if(pertenece (Estado origen) estados) && (pertenece (Estado destino) estados) && (pertenece (Terminal simbolo) simbolos) && (simbolo<>"" && aux1 origen simbolo destino ars) then true else aux ars t
  | _ -> false
  in aux arcos arcos;; 
(*****************************************************************************)
let todos_presentes (Af(estados,simbolos,_,Conjunto arcos,_)) =
  let rec aux esta = function
    []-> if esta=conjunto_vacio then true else false
    | (Arco_af (origen,_,simbolo))::t -> if (pertenece (origen,simbolo) esta) then aux (suprimir (origen,simbolo) esta) t else false
  in let tienen_que_estar = cartesiano estados simbolos in aux tienen_que_estar arcos ;;



let es_afd arbol = (pertenecen_estados_simbolos arbol) && (not (es_afn arbol)) && (todos_pre sentes arbol);;
(*****************************************************************************)


(*****************************************************************************)
let afd = Af (Conjunto [Estado "0"; Estado "1"; Estado "2"; Estado "3"],
Conjunto [Terminal "a"; Terminal "b"],
Estado "0",
Conjunto [Arco_af (Estado "0", Estado "1", Terminal "a");
Arco_af (Estado "0", Estado "2", Terminal "b");
Arco_af (Estado "1", Estado "2", Terminal "a");
Arco_af (Estado "1", Estado "0", Terminal "b");
Arco_af (Estado "2", Estado "1", Terminal "a");
Arco_af (Estado "2", Estado "3", Terminal "b");
Arco_af (Estado "3", Estado "1", Terminal "a");
Arco_af (Estado "3", Estado "2", Terminal "b")],
Conjunto [Estado "1"; Estado "3"]);;


let afn = Af (Conjunto [Estado "0"; Estado "1"; Estado "2"],
Conjunto [Terminal "a"; Terminal "b"],
Estado "0",
Conjunto [Arco_af (Estado "0", Estado "1", Terminal "a");
Arco_af (Estado "1", Estado "1", Terminal "b");
Arco_af (Estado "1", Estado "2", Terminal "a");
Arco_af (Estado "2", Estado "0", Terminal "a");
Arco_af (Estado "2", Estado "1", Terminal "a");
Arco_af (Estado "0", Estado "2", Terminal "b")],
Conjunto [Estado "1"; Estado "2"]);;


let afne = Af (Conjunto [Estado "0"; Estado "1"; Estado "2"; Estado "3"],
Conjunto [Terminal "a"; Terminal "b"; Terminal "c"],
Estado "0",
Conjunto [Arco_af (Estado "0", Estado "1", Terminal "a");
Arco_af (Estado "1", Estado "1", Terminal "b");
Arco_af (Estado "1", Estado "2", Terminal "a");
Arco_af (Estado "2", Estado "0", Terminal "");
Arco_af (Estado "2", Estado "3", Terminal "");
Arco_af (Estado "2", Estado "3", Terminal "c")],
Conjunto [Estado "1"; Estado "3"]);;


es