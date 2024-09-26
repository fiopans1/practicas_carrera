-module(break_md5).
-define(PASS_LEN, 6).
-define(UPDATE_BAR_GAP, 1000).
-define(BAR_SIZE, 40).
-define(PROCESOS, 7).

-export([pass_to_num/1,
         num_to_pass/1,
         num_to_hex_string/1,
         hex_string_to_num/1,
        break_md5s/1]).

-export([progress_loop/4,
            break_md5s/6]).

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

progress_loop(N, Bound,T1,Pid) ->
    receive
        stop ->
        io:fwrite("\n"),
        Pid!stop,
        ok;
        {progress_report, Checked} ->
            T2=erlang:monotonic_time(microsecond),
            N2 = N + Checked,
            Full_N = N2 * ?BAR_SIZE div Bound,
            Full = lists:duplicate(Full_N, $=),
            Empty = lists:duplicate(?BAR_SIZE - Full_N, $-),
            Iters=((1000000000) div (T2-T1)),
            io:format("\r[~s~s] ~.2f% ~w   ", [Full, Empty, N2/Bound*100, Iters]),
            progress_loop(N2, Bound,T2,Pid)

    end.


comparar(NumH,TargetH)->
    V=[X || X<-TargetH , X==NumH],
    if(V==[])->
        TargetH;
    true->
        NewTargetH=[X || X<-TargetH, X/=NumH],
        {ok,NewTargetH}
    end.


break_md5s(_, N, N, _,Pid,_) -> Pid ! acabe;  % Checked every possible password
break_md5s(Target_Hash, N, Bound, Progress_Pid,Pid,Concat) ->
    receive
        {NewTargetH,C}->
            break_md5s(NewTargetH,N,Bound,Progress_Pid,Pid,C);
        stop->
            Pid!acabe,
            ok
    after 0->
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
            []->
                Pid!{acabamos,Concat},
                Pid!acabe,
                ok;
            
            {ok,NewTargetH}->
                Pid! {actualiza,NewTargetH,[Pass | Concat]},
                io:format("\e[2K\r~.16B: ~s~n", [Num_Hash, Pass]),
                break_md5s(NewTargetH, N+1, Bound, Progress_Pid,Pid,[Pass | Concat]);
            H->
                break_md5s(H, N+1, Bound, Progress_Pid, Pid,Concat)
        end
    end.


init_procesos(0,D,_,_,_,_,_)->D;
init_procesos(N,D,Progress_Pid, Num_Hash_lista,Bound,A_cada_proceso,Init)->

        Fin= Init+A_cada_proceso,
        Pid=spawn(?MODULE,break_md5s,[Num_Hash_lista,Init,Fin, Progress_Pid,self(),[]]),
        init_procesos(N-1,[Pid | D],Progress_Pid, Num_Hash_lista,Bound,A_cada_proceso,Fin).
%% Break a hash
parar([])->ok;
parar([H |T])->
        H!stop,
        parar(T). 
mandar_mensaje([],_)->ok;
mandar_mensaje([H|T],Mensaje)-> H!Mensaje,mandar_mensaje(T,Mensaje).

servidor(Procesos,Progress_Pid,Acabados)->
    receive
        stop -> ok;
        {actualiza,NewTargetH,C}->
            mandar_mensaje(Procesos,{NewTargetH,C}),
        servidor(Procesos,Progress_Pid,Acabados+1);
        {acabamos,Concat}->
            if(Acabados==?PROCESOS)->
                parar(Procesos),Progress_Pid!stop,servidor(Procesos,Progress_Pid,Acabados);
            true->
                servidor(Procesos,Progress_Pid,Acabados)
            end,
            Concat;
        acabe->
            if(Acabados==?PROCESOS)-> 
                Progress_Pid ! stop,
                servidor(Procesos,Progress_Pid,Acabados+1);
            true->
                servidor(Procesos,Progress_Pid,Acabados+1)
            end
    end.

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
    T=erlang:monotonic_time(microsecond),
    Progress_Pid = spawn(?MODULE, progress_loop, [0, Bound,T,self()]),
    Num_Hash_lista = array_hex_to_num(Hash),
    A_cada_proceso=Bound div ?PROCESOS,
    Procesos=init_procesos(?PROCESOS,[],Progress_Pid, Num_Hash_lista,Bound,A_cada_proceso,0),
    Res = servidor(Procesos,Progress_Pid,0),%break_md5s(Num_Hash_lista, 0, Bound, Progress_Pid,[]),
    Res.
%llamamos a break_md5 que va a definir las variables y luego llamar al otro break_md5
%que cuando llega al limite devolvera not_found, y sino rompe la contraseña