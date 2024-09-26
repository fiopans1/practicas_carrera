type 'a bin_tree =
    Empty
  | Node of 'a * 'a bin_tree * 'a bin_tree;;

let rec fold_tree f a = function
    Empty -> a
  | Node (x, l, r) -> f x (fold_tree f a l) (fold_tree f a r);;

(* Implemente sum, prod, size, inorder y mirror usando fold_tree *)

let sum t = fold_tree (fun x y z -> x + y + z ) 0 t ;;

let prod t = fold_tree (fun x y z -> x *. y *. z ) 1.0 t ;;

let size t = fold_tree (fun x y z-> 1 + y + z) 0 t ;;

let inorder t = fold_tree (fun x y z -> y@x::z ) [] t;;

let mirror t = fold_tree (fun x y z -> Node(x,z,y)) Empty t ;;

