-module(break_md5).
-define(PASS_LEN, 6).
-define(UPDATE_BAR_GAP, 1000).
-define(BAR_SIZE, 40).

-export([break_md5/1,
         pass_to_num/1,
         num_to_pass/1,
         num_to_hex_string/1,
         hex_string_to_num/1,
        break_md5s/1]).

-export([progress_loop/2]).
%en Erlang los strings son tuplas
% Base ^ Exp

pow_aux(_Base, Pow, 0) ->
    Pow;
pow_aux(Base, Pow, Exp) when Exp rem 2 == 0 ->
    pow_aux(Base*Base, Pow, Exp div 2);
pow_aux(Base, Pow, Exp) ->
    pow_aux(Base, Base * Pow, Exp - 1).

pow(Base, Exp) -> pow_aux(Base, 1, Exp).

%% Number to password and back conversion
%te pasa un numero a una contraseña, para comparar con la que tu tienes cifrada(pasa el i a pass)
num_to_pass_aux(_N, 0, Pass) -> Pass;
num_to_pass_aux(N, Digit, Pass) ->
    num_to_pass_aux(N div 26, Digit - 1, [$a + N rem 26 | Pass]).

num_to_pass(N) -> num_to_pass_aux(N, ?PASS_LEN, []).

%te convierte la contraseña al numero
pass_to_num(Pass) ->
    lists:foldl(fun (C, Num) -> Num * 26 + C - $a end, 0, Pass).

%% Hex string to Number
%pasa un numero a su caracter hexadecimal
hex_char_to_int(N) ->
    if (N >= $0) and (N =< $9) -> N - $0;
       (N >= $a) and (N =< $f) -> N - $a + 10;
       (N >= $A) and (N =< $F) -> N - $A + 10;
       true                    -> throw({not_hex, [N]})
    end.
%pasa un caracter hexadecimal a un int
int_to_hex_char(N) ->
    if (N >= 0)  and (N < 10) -> $0 + N;
       (N >= 10) and (N < 16) -> $A + (N - 10);
       true                   -> throw({out_of_range, N})
    end.
%convierte un numero (que será el args que tu pasas como argumento) a una tupla string con su valor hexadecimal
hex_string_to_num(Hex_Str) ->
    lists:foldl(fun(Hex, Num) -> Num*16 + hex_char_to_int(Hex) end, 0, Hex_Str).


%revierte num_to_hex_string
num_to_hex_string_aux(0, Str) -> Str;
num_to_hex_string_aux(N, Str) ->
    num_to_hex_string_aux(N div 16,
                          [int_to_hex_char(N rem 16) | Str]).

num_to_hex_string(0) -> "0";
num_to_hex_string(N) -> num_to_hex_string_aux(N, []).

%% Progress bar runs in its own process

progress_loop(N, Bound) ->
    receive
        stop -> ok;
        {progress_report, Checked} ->
            N2 = N + Checked,
            Full_N = N2 * ?BAR_SIZE div Bound,
            Full = lists:duplicate(Full_N, $=),
            Empty = lists:duplicate(?BAR_SIZE - Full_N, $-),
            io:format("\r[~s~s] ~.2f%", [Full, Empty, N2/Bound*100]),
            progress_loop(N2, Bound)
    end.

%% break_md5/2 iterates checking the possible passwords

break_md5(Target_Hash, N, N, _) -> {not_found, Target_Hash};  % Checked every possible password
break_md5(Target_Hash, N, Bound, Progress_Pid) ->
    if N rem ?UPDATE_BAR_GAP == 0 ->
            Progress_Pid ! {progress_report, ?UPDATE_BAR_GAP};
       true ->
            ok
    end,
    Pass = num_to_pass(N),
    Hash = crypto:hash(md5, Pass),
    Num_Hash = binary:decode_unsigned(Hash),
    if
        Target_Hash == Num_Hash ->
            io:format("\e[2K\r~.16B: ~s~n", [Num_Hash, Pass]);
        true ->
            break_md5(Target_Hash, N+1, Bound, Progress_Pid)
    end.

%% Break a hash

break_md5(Hash) ->
    Bound = pow(26, ?PASS_LEN),
    Progress_Pid = spawn(?MODULE, progress_loop, [0, Bound]),
    Num_Hash = hex_string_to_num(Hash),
    Res = break_md5(Num_Hash, 0, Bound, Progress_Pid),
    Progress_Pid ! stop,
    Res.
%llamamos a break_md5 que va a definir las variables y luego llamar al otro break_md5
%que cuando llega al limite devolvera not_found, y sino rompe la contraseña

comparar(NumH,TargetH)->
    V=[X || X<-TargetH , X==NumH],
    if(V==[])->
        TargetH;
    true->
        NewTargetH=[X || X<-TargetH, X/=NumH],
        {ok,NewTargetH}
    end.



break_md5s(Target_Hash, N, N, _,Concat) -> {not_found, Target_Hash, Concat};  % Checked every possible password
break_md5s(Target_Hash, N, Bound, Progress_Pid,Concat) ->
    if N rem ?UPDATE_BAR_GAP == 0 ->
            Progress_Pid ! {progress_report, ?UPDATE_BAR_GAP};
       true ->
            ok
    end,
    Pass = num_to_pass(N),%pasamos a numero, encriptamos y pasamos a numero el encriptado
    Hash = crypto:hash(md5, Pass),
    Num_Hash = binary:decode_unsigned(Hash),
    Sol=comparar(Num_Hash,Target_Hash),
    case Sol of
        []->Concat;

        {ok,NewTargetH}->
            io:format("\e[2K\r~.16B: ~s~n", [Num_Hash, Pass]),
            break_md5s(NewTargetH, N+1, Bound, Progress_Pid,[Pass | Concat]);
        H->
            break_md5s(H, N+1, Bound, Progress_Pid, Concat)
        end.

%% Break a hash
array_hex_to_num_aux(Hash, Array)->
    case Hash of
        []->
            Array;
        [H | T]->
             array_hex_to_num_aux(T, [hex_string_to_num(H) | Array])
    end.





array_hex_to_num(Hash)->
    array_hex_to_num_aux(Hash,[]).


break_md5s(Hash) ->
    Bound = pow(26, ?PASS_LEN),
    Progress_Pid = spawn(?MODULE, progress_loop, [0, Bound]),
    Num_Hash_lista = array_hex_to_num(Hash),
    Res = break_md5s(Num_Hash_lista, 0, Bound, Progress_Pid,[]),
    Progress_Pid ! stop,
    Res.
%llamamos a break_md5 que va a definir las variables y luego llamar al otro break_md5
%que cuando llega al limite devolvera not_found, y sino rompe la contraseña
