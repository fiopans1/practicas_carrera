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


  let a = gic_of_string "S A B; a b c; S; S -> A B; A -> a | b; B -> S A;";;
  let b = gic_of_string "S A B; a b c; S; S -> a A; A -> a b c A | b B; B -> b c B | epsilon;";;
