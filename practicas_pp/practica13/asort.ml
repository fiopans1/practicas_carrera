



(* Merge is written according to the pseudocode in "Introduction to Algorithms" 3rd edition p.31. a is the array, p q r are the number of element in the array, therefore, counting starts from 1, not 0.*)
let merge a p q r =
  let n1 = ref (q - p + 1) and n2 = ref (r - q) in
    let la = Array.make (!n1 + 1) max_int and ra = Array.make (!n2 + 1) max_int in
      for i = 0 to !n1 - 1 do
        la.(i) <- a.(p + i - 1)
      done;
      for j = 0 to !n2 - 1 do
        ra.(j) <- a.(q + j)
      done;
        let i = ref 0 and j = ref 0 in
          for k = p - 1 to r - 1 do
            if la.(!i) <= ra.(!j) then
              (a.(k) <- la.(!i) ;
              i := !i + 1)  
            else 
              (a.(k) <- ra.(!j) ; 
              j := !j + 1) 
          done
;;

(* merge_sort is written following the pseudocode on p.34 of the same book, using merge as a subroutine. a is the array, p = 1 (first element) r = length of array. *)
let rec merge_sort a p r =
  let c = ref r in
    while p < r && p < !c do
      merge_sort a p ((p + r)/2);
      merge_sort a ((p + r)/2 + 1) r;
      merge a p ((p + r)/2) r;
      c := (!c + p)/2
    done
;;



  let rec merge ord v izq der cent=
    let i= (ref izq) in let j= (ref (cent+1)) in let k=(ref izq) in let aux=(Array.make (Array.length v) v.(0)) in
    while !i<= cent && !j<= der do (
        if (ord v.((!i)) v.((!j))) then (aux.((!k)) <- v.((!i)) ; i := (!i + 1))
        else aux.((!k)) <- v.((!j)) ; j := (!j + 1) ; k := (!k +1))
    done ;
    while !i <= cent do
      aux.((!k)) <- v.((!i)) ; i := (!i + 1) ; k := (!k +1)
    done ;
    while !i <= der do
      aux.((!k)) <- v.((!j)) ; j := (!j + 1) ; k := (!k +1)
    done ;
    for z = izq to der do
      v.(z) <- aux.(z)
    done ;;

let rec mergesort ord v izq der=
  let cent= ref der in
  while izq<der && izq < !cent do
    mergesort ord v izq ((izq+der)/2);
    mergesort ord v ((izq+der)/2 +1) der;
    merge ord v izq der ((izq+der)/2);
    cent := (!cent + izq)/2
  done;;

let asort ord v=
  mergesort ord v 0 ((Array.length v) -1);;

let v= [| 5;2;3;5;67;3;4;56;7;4;9;3;6;8;1;6|];;
let f =  fun x y -> x<y;;