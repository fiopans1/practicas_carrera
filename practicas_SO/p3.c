/*
 * TITLE: P0 SISTEMAS OPERATIVOS
 * SUBTITLE: Practica 0
 * AUTHOR 1: Diego Suárez Ramos LOGIN 1: diego.suarez.ramos@udc.es
 * AUTHOR 2: Ander Varela Martin LOGIN 2: ander.varela@udc.es
 * GROUP: 2.3
 * DATE: 21/09/2021
 */


//LIBRERIAS
#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/utsname.h>
#include <sys/stat.h>
#include <time.h>
#include "dynamic_list.c"
#include "dynamic_list.h"
#include <ctype.h> 
#include <errno.h>
#include <sys/file.h>
#include <dirent.h>
#include <pwd.h>
#include <grp.h>
#include <dirent.h>
#include "memoria.h"
#include "procesos.h"



//
//TIPOS DE DATOS Y VARIABLES
#define MAX_PARAM 5
#define RED "\x1b[31m"
#define GREEN "\x1b[32m"
#define BLUE "\x1b[34m"
#define COLOR_RESET "\x1b[0m"
typedef char *cadena;
#define MAX_PALABRAS 200
#define MAX_RUTA 256
#define LEERCOMPLETO ((ssize_t)-1)

//CABECERAS P0
void imprimirPrompt();
void leerEntrada(cadena N);
void procesarEntrada(cadena N,tList *L,tListM *M, char *env[], tListP *P);
void readtask(char *env[]);
void crearfichero();
void autores(cadena param1, int n);
void pid(cadena param1, int n);
void carpeta(cadena param1, int n);
void fecha(cadena param1, int n);
void infosis();
void hist(cadena param1, tList *L, int n);
void comandoN(cadena param1,tList L, int n,tListM *M, char *env[],  tListP *P);
void ayuda(cadena param1, int n);
int TrocearCadena(char * cadena, char * trozos[]);
//CABECERAS P1
void crear(cadena trozos[], int n);
void listfich(cadena trozos[],int n);
void listdir(cadena trozos[],int n);
void borrar(cadena trozos[],int n);
void borrar_rec(cadena trozos[],int n);



//CABECERAS FUNCIONES AUXILIARES
bool isNumber(cadena param1);
char LetraTF (mode_t m);
char * ConvierteModo2 (mode_t m);
void listar_long(bool acc1, bool link1,bool long1, cadena name);
void directorio();
void recura_directorios(bool long1,bool hid1,bool acc1, bool link1,bool reca1,bool recb1, tList *directorios);
void borrar1_directorios(cadena directorio);


//EMPIEZA EL CODIGO
int main(int argc, char * argv[], char *env[]){
readtask(env);
return 0;
}
void readtask(char *env[]){//funcion general
    bool terminado;
    cadena N;
    tList L;
    tListM M;
    tListP P;
    createList(&L);
    createEmptyListM(&M);
    createEmptyListP(&P);
    //reservamos memoria para N
    N=malloc(200*sizeof(char));
    terminado=false;
    if(N!=NULL){
    while(!terminado){
        imprimirPrompt();
        leerEntrada(N);
        procesarEntrada(N, &L, &M, env, &P);
    }
    free(N);
    }else{
        printf(RED "MEMORIA LLENA\n" COLOR_RESET); 
    }
}
void imprimirPrompt(){//imprimimos prompt
    printf(GREEN "<user@udcsystem>" COLOR_RESET);
}
void leerEntrada(cadena N){//leemos el stdin
    fflush(stdin);
    if(fgets(N,200,stdin)==NULL){
        perror( "error" );
    }
    fflush(stdin);
}
void procesarEntrada(cadena N, tList *L,tListM *M,char *env[],  tListP *P){//procesamos la entrada
    //COSAS DE LISTA Y VARIABLES
    tItemL informacion;
    char *trozos[MAX_PALABRAS];//reservamos memoria par 200 palabras, si se introducen mas de 200 el programa peta
    char N2[200];
    int n;


    //VAMOS A TROCEAR COMANDO
    strcpy(N2,N);
    n=TrocearCadena(N,trozos);
   if(n>=1){
        //VAMOS A GUARDAR COMANDO EN HISTORIAL
        if(strcmp(trozos[0],"comando")!=0){
            strcpy(informacion.command,N2);
            if(isEmptyList(*L)){
                informacion.numcode=0;
            }else{
                informacion.numcode=getItem(last(*L),*L).numcode+1;
            }
            if(!(insertElement(informacion,LNULL,L))){
                printf(RED "El comando no se pudo guardar en el historial\n" COLOR_RESET); 
            }
        }
        

        //VAMOS A ELEGIR COMANDO:
        
            if(strcmp(trozos[0],"autores")==0){ //AUTORES
                if(n==2){
                    autores(trozos[1],n);
                }else if(n==1){
                    autores(NULL,n);
                }else{
                   printf(RED "This command doesn't exist\n" COLOR_RESET); 
                }
            }else if(strcmp(trozos[0],"pid")==0){ //PID
                if(n==2){
                    pid(trozos[1],n);
                }else if(n==1){
                    pid(NULL,n);
                }else{
                   printf(RED "This command doesn't exist\n" COLOR_RESET); 
                }
            }else if(strcmp(trozos[0],"carpeta")==0){ //CARPETA
                if(n==2){
                    carpeta(trozos[1],n);
                }else if(n==1){
                    carpeta(NULL,n);
                }else{
                   printf(RED "This command doesn't exist\n" COLOR_RESET); 
                }
            }else if(strcmp(trozos[0],"fecha")==0){ //FECHA
                if(n==2){
                    fecha(trozos[1],n);
                }else if(n==1){
                    fecha(NULL,n);
                }else{
                   printf(RED "This command doesn't exist\n" COLOR_RESET); 
                }
            }else if(strcmp(trozos[0],"hist")==0){ //HIST
                if(n==2){
                    hist(trozos[1],L,n);
                }else if(n==1){
                    hist(NULL,L,n);
                }else{
                   printf(RED "This command doesn't exist\n" COLOR_RESET); 
                }
            }else if(strcmp(trozos[0],"comando")==0){ //COMANDO
                if(n==2){
                    comandoN(trozos[1],*L,n, M, env,P);
                }else if(n==1){
                    comandoN(NULL,*L,n, M, env, P);
                }else{
                   printf(RED "This command doesn't exist\n" COLOR_RESET); 
                }
            }else if(strcmp(trozos[0],"infosis")==0){ //INFOSIS
                if(n==1){
                    infosis();
                }else{
                   printf(RED "This command doesn't exist\n" COLOR_RESET); 
                }
                
            }else if(strcmp(trozos[0],"ayuda")==0){ //AYUDA
                if(n==2){
                    ayuda(trozos[1],n);
                }else if(n==1){
                    ayuda(NULL,n);
                }else{
                   printf(RED "This command doesn't exist\n" COLOR_RESET); 
                }
            }else if(strcmp(trozos[0],"salir")==0 || strcmp(trozos[0],"fin")==0 || strcmp(trozos[0],"bye")==0){ //SALIR

                for(int i=0;i<n;i++){
                    free(trozos[i]);
                }
                deleteList(L);
                deleteListM(M);
                deleteListP(P);
                exit(1);
            }else if(strcmp(trozos[0],"crear")==0){
                crear(trozos,n);

            }else if(strcmp(trozos[0],"borrar")==0){
                borrar(trozos,n);
            }else if(strcmp(trozos[0],"borrarrec")==0){
                borrar_rec(trozos,n);
            }else if(strcmp(trozos[0],"listfich")==0){
                listfich(trozos,n);
            }else if(strcmp(trozos[0],"listdir")==0){
                listdir(trozos,n);
            }else if(strcmp(trozos[0],"malloc")==0){
                malloc1(trozos,n,M);
            }else if(strcmp(trozos[0],"mmap")==0){
                mmap1(trozos,n,M);
            }else if(strcmp(trozos[0],"shared")==0){
                shared1(trozos,n,M);
            }else if(strcmp(trozos[0],"recursiva")==0){
                int n1;
                if(n==2){
                    if(isNumber(trozos[1])){
                        n1=(int) strtol(trozos[1], NULL, 10);
                        doRecursiva(n1);
                    }else{
                        doRecursiva(0);
                    }
                }else if(n>2){
                    if(isNumber(trozos[1])){
                        n1=(int) strtol(trozos[1], NULL, 10);
                        doRecursiva(n1);                       
                    }
                }
            
            }else if(strcmp(trozos[0],"dealloc")==0){
                dealloc(trozos,n,M);
            }else if(strcmp(trozos[0],"memoria")==0){
                memoria(trozos,n,M);
            }else if(strcmp(trozos[0],"volcarmem")==0){
                volcarmem(trozos,n);
            }else if(strcmp(trozos[0],"llenarmem")==0){
                llenarmemoria(trozos,n);
            }else if(strcmp(trozos[0],"e-s")==0){
                esreadwrite(trozos,n);
            }else if(strcmp(trozos[0],"priority")==0){
                priority(trozos,n);
            }else if(strcmp(trozos[0],"rederr")==0){  
                rederr(trozos,n);          
            }else if(strcmp(trozos[0],"entorno")==0){ 
                entorno(trozos,n, env);
            }else if(strcmp(trozos[0],"mostrarvar")==0){ 
                mostrarvar(trozos,n,env);
            }else if(strcmp(trozos[0],"cambiarvar")==0){ 
                cambiarvar(trozos,n,env);
            }else if(strcmp(trozos[0],"uid")==0){   
                uid1(trozos,n); 
            }else if(strcmp(trozos[0],"fork")==0){
                fork1();    
            }else if(strcmp(trozos[0],"ejec")==0){
                ejec(trozos,n);
            }else if(strcmp(trozos[0],"fg")==0){
                fg(trozos,n);
            }else if(strcmp(trozos[0],"ejecpri")==0){
                ejecprio(trozos,n);
            }else if(strcmp(trozos[0],"fgpri")==0){
                fgprio(trozos,n);
            }else if(strcmp(trozos[0],"back")==0){
                back(trozos,n,P,N2);
            }else if(strcmp(trozos[0],"backpri")==0){
                backprio(trozos,n,P,N2);
            }else if(strcmp(trozos[0],"listjobs")==0){
                listjobs(P);
            }else if(strcmp(trozos[0],"ejecas")==0){
                ejecas(trozos,n);
            }else if(strcmp(trozos[0],"fgas")==0){
                fgas(trozos,n);
            }else if(strcmp(trozos[0],"bgas")==0){
                backas(trozos,n,P,N2);
            }else if(strcmp(trozos[0],"job")==0){
                job(trozos,n,P);
            }else if(strcmp(trozos[0],"borrarjobs")==0){
                borrarjobs(trozos,n,P);
            }else if(strcmp(trozos[n-1],"&")==0){
                back1(trozos,n,P,N2);
            }else{
                fg1(trozos,n);
            }
    }
}
int TrocearCadena(char * cadena, char * trozos[]){ //troceamos la cadena en palabras
    int i=1;
    if ((trozos[0]=strtok(cadena," \n\t"))==NULL){
        return 0;
    }
    while ((trozos[i]=strtok(NULL," \n\t"))!=NULL){
        i++;
        
    }
        
    return i;
}

//P0
void autores(cadena param1, int n){                //AUTORES
     if(n==1 && param1==NULL){//imprimimos solo autores
        puts("********************");
        puts("NOMBRE Y LOGIN DE LOS AUTORES:");
        puts("-> Diego Suarez Ramos: diego.suarez.ramos@udc.es");
        puts("-> Ander Varela Martin: ander.varela@udc.es");
        puts("********************"); 
     }else  if(strcmp("-l",param1)==0){//imprimimos autores -l
        puts("********************");
        puts("LOGIN DE LOS AUTORES:");
        puts("-> diego.suarez.ramos@udc.es");
        puts("-> ander.varela@udc.es");
        puts("********************");
    }else if(strcmp("-n",param1)==0){//imprimimos autores -l
        puts("********************");
        puts("NOMBRE DE LOS AUTORES");
        puts("-> Diego Suarez Ramos");
        puts("-> Ander Varela Martin");
        puts("********************");
    }else{
        printf(RED "This command doesn't exist\n" COLOR_RESET);
    }
}
void pid(cadena param1,int n){                     //PID
    pid_t id;
    if(n==1 && param1==NULL){//imprimimos pid
        id=getpid();//esta funcion nunca falla
        puts("********************");
        printf("-> El pid del shell es: %d\n", id);
        puts("********************");
    }else if(strcmp(param1,"-p")==0){//imprimimos pid padre
        id=getppid();//esta funcion nunca falla
        puts("********************");
        printf("-> El pid del padre del shell es: %d\n",id);
        puts("********************");
    }else{
        printf(RED "This command doesn't exist\n" COLOR_RESET);
    }
}
void carpeta(cadena param1,int n){                 //CARPETA

    if(n==1 && param1==NULL){//obtenemos carpeta
        directorio();
    }else if(n==2){//cambiamos carpeta
        if(chdir(param1)!=0){
          perror( "error" );   
        }
    }else{
        printf(RED "This command doesn't exist\n" COLOR_RESET);
    }
}
void fecha(cadena param1, int n){                  //FECHA
    time_t tiempo = time(0);
    struct tm *tiempolocal;
    char time[50];
    tiempolocal = localtime(&tiempo);
    if(tiempolocal==NULL){
        perror( "error" );
    }else{
        if(n==1 && param1==NULL){//fecha
            strftime(time,50,"%d/%m/%y %H:%M:%S",tiempolocal);
            puts("********************");
            printf("-> %s\n",time);
            puts("********************");
        }else if(strcmp(param1,"-d")==0){//fecha -d
        
            strftime(time,50,"%d/%m/%y",tiempolocal);
            puts("********************");
            printf("-> %s\n",time);
            puts("********************");
        }else if(strcmp(param1,"-h")==0){//fecha -h
        
            strftime(time,50,"%H:%M:%S",tiempolocal);
            puts("********************");
            printf("-> %s\n",time);
            puts("********************");
        }else{
            printf(RED "This command doesn't exist\n" COLOR_RESET);
        }
    }
}
void hist(cadena param1, tList *L, int z) {        //HIST
    tPosL p;
    tItemL items;
    tNumCode n;

    puts("********************");
    if(z==1 && param1==NULL){//hist
        if (!isEmptyList(*L)) {//miramos si el historial esta vacio o no
            p = first(*L);
            //recorremos la lista monstrando los datos y actualizando contadores
            while (p != LNULL) {
                items = getItem(p, *L);
                printf("%d - %s\n", items.numcode, items.command);
                p = next(p, *L);
            }
        }else{
            puts("Historial vacio");
        }
    }else if (strcmp("-c", param1) == 0) {//hist -cincludePa
        deleteList(L); //borramos historial
        puts("Historial borrado");


    } else if (isNumber(param1)) {//necesitamos ver que sea numero hist N
        tPosL pos;
        n= (tNumCode) strtol(param1, NULL, 10);
        pos=findItem(n,*L);
        if(pos!=LNULL){
        items= getItem(pos,*L);
        printf("%d - %s\n", items.numcode, items.command);
        }else{
            printf(RED "No se encontro el comando en el historial\n" COLOR_RESET);
        }

    }else{
        printf(RED "Error con el historial\n" COLOR_RESET);
    }
    puts("********************");
}
void comandoN(cadena param1,tList L, int z,tListM *M, char *env[],  tListP *P){       //COMANDO N
    if(z==2 && isNumber(param1)){//comando N
    //tPosL p;
    tItemL items;
    tNumCode n;
    tPosL pos;
    n= (tNumCode) strtol(param1, NULL, 10);
    pos=findItem(n,L);
        if(pos!=LNULL){
            items= getItem(pos,L);
            printf("%d - %s\n", items.numcode, items.command);
            procesarEntrada(items.command, &L,M, env, P);
        }else{
            printf(RED "-> No se encontro el comando en el historial\n" COLOR_RESET);
        }
    }else{
        printf(RED "This command doesn't exist\n" COLOR_RESET);
    }

}
void infosis() {               //INFOSIS
        struct utsname uts;

        if (uname(&uts) < 0)//caso error
            perror("uname() error");
        else{//infosis
            puts("********************");
            printf("Sysname:  %s\n", uts.sysname);
            printf("Nodename: %s\n", uts.nodename);
            printf("Release:  %s\n", uts.release);
            printf("Version:  %s\n", uts.version);
            printf("Machine:  %s\n", uts.machine);
            puts("********************");
        }

}
void ayuda(cadena param1, int n){                  //AYUDA

    puts("********************");
    if(n==1 && param1==NULL) {//ayuda general

       printf("->'ayuda [cmd]' donde cmd es uno de los siguientes comandos:\n- fin\t\t- salir\n- bye\t\t- carpeta\n- pid\t\t- autores\n- hist\t\t- comando\n- fecha\t\t- infosis\n");
        printf("- ayuda\t\t- crear\n- borrar\t- borrarrec\n- listfich\t- listdir\n");
       printf("- malloc\t- mmap\n- shared\t- dealloc\n- memoria\t- volcarmem\n- llenarmem\t- recursiva\n- e-s\t\t- fgas\n");
       printf("- priority\t- rederr\n- uid\t\t- entorno\n- mostrarvar\t- cambiarvar\n- fork\t\t- ejec\n- ejecpri\t- job\n");
       printf("- fg\t\t- fgpri\n- back\t\t- backpri\n- fgas\t\t- backas\n- listjobs\t- borrarjobs\n");


    }else if(n==2){//ayuda especifico
        if(strcmp(param1,"autores")==0){
            printf("autores [-n|-l]	Muestra los nombres y logins de los autores\n");

        }else if(strcmp(param1,"pid")==0){
            printf("pid [-p]	Muestra el pid del shell o de su proceso padre\n");
        }else if(strcmp(param1,"carpeta")==0){
            printf("carpeta [dir]	Cambia (o muestra) el directorio actual del shell\n");
        }else if(strcmp(param1,"fecha")==0){
            printf("fecha [-d|-h]	Muestra la fecha y o la hora actual\n");
        }else if(strcmp(param1,"hist")==0){
            printf("hist [-c|-N]	Muestra el historico de comandos, con -c lo borra\n");
        }else if(strcmp(param1,"comando")==0){
            printf("comando [-N]	Repite el comando N (del historico)\n");
        }else if(strcmp(param1,"infosis")==0){
            printf("infosis 	Muestra informacion de la maquina donde corre el shell");
        }else if(strcmp(param1,"ayuda")==0){
            printf("ayuda [cmd]	Muestra ayuda sobre los comandos\n");
        }else if(strcmp(param1,"salir")==0 || strcmp(param1,"fin")==0 || strcmp(param1,"bye")==0){
            printf("bye/salir/fin 	Termina la ejecucion del shell\n");
        }else if(strcmp(param1,"crear")==0){
            printf("crear [-f] [name]	 Crea un fichero o directorio\n");
        }else if(strcmp(param1,"listfich")==0){
            printf("listfich [-long][-link][-acc] n1 n2 ..	lista ficheros\n");
        }else if(strcmp(param1,"listdir")==0){
            printf("listdir [-reca] [-recb] [-hid][-long][-link][-acc] n1 n2 ..	lista ficheros contenidos de directorios\n");
        }else if(strcmp(param1,"borrar")==0){
            printf("borrar [name1 name2 ..]	 Borra ficheros o directorios vacios\n");
        }else if(strcmp(param1,"borrarrec")==0){
            printf("borrarrec [name1 name2 ..]	 Borra ficheros o directorios no vacios\n");
        }else if(strcmp(param1,"malloc")==0){
            printf("malloc [-free] tam	 asigna(o desasigna) memoria en el programa\n");
        }else if(strcmp(param1,"mmap")==0){
            printf("mmap [-free] fich prms	 mapea(o desmapea) ficheros en el espacio de direcciones del proceso\n");
        }else if(strcmp(param1,"shared")==0){
            printf("shared [-free|-create|-delkey] cl tam	 asigna(o desasigna) memoria compartida en el programa\n");
        }else if(strcmp(param1,"dealloc")==0){
            printf("dealloc -malloc|-shared|-mmap....	Desasigna un bloque de memoria asignado con malloc, shared o mmap\n");
        }else if(strcmp(param1,"memoria")==0){
            printf("memoria [-blocks|-funcs|-vars|-all|-pmap] ..	Muestra muestra detalles de la memoria del proceso\n");
        }else if(strcmp(param1,"volcarmem")==0){
            printf("volcarmem addr cont 	Vuelca en pantallas los contenidos (cont bytes) de la posicion de memoria addr\n");
        }else if(strcmp(param1,"llenarmem")==0){
            printf("llenarmem addr cont byte 	 Llena la memoria a partir de addr con byte\n");
        }else if(strcmp(param1,"recursiva")==0){
            printf("recursiva [n] 	Invoca a la funcion recursiva n veces\n");
        }else if(strcmp(param1,"e-s")==0){
            printf("e-s [read|write] [-o] fiche addr cont 	Invoca a la funcion recursiva n veces\n");
        }else if(strcmp(param1,"priority")==0){
            printf("priority [pid] [valor] 	Muestra o cambia la prioridad del proceso pid a valor\n");
        }else if(strcmp(param1,"rederr")==0){
            printf("rederr [-reset] fich 	Redirecciona el error estándar del shell\n");
        }else if(strcmp(param1,"uid")==0){
            printf("uid [-get|-set] [-l] [id] 	 Muestra o cambia (si puede) la credencial del proceso que ejecuta el shell\n");
        }else if(strcmp(param1,"entorno")==0){
            printf("entorno [-environ|-addr] 	 Muestra el entorno del proceso\n");
        }else if(strcmp(param1,"mostrarvar")==0){
            printf("mostrarvar 	 Muestra el valor y las direcciones de una variable de entorno\n");
        }else if(strcmp(param1,"cambiarvar")==0){
            printf("cambiarvar [-a|-e|-p] var valor	Cambia el valor de una variable de entorno\n");
        }else if(strcmp(param1,"fork")==0){
            printf("fork 	Hace una llamada fork para crear un proceso\n");
        }else if(strcmp(param1,"ejec")==0){
            printf("ejec prog args....	Ejecuta, sin crear proceso,prog con argumentos\n");
        }else if(strcmp(param1,"ejecpri")==0){
            printf("ejecpri prio prog args....	Ejecuta, sin crear proceso, prog con argumentos con la prioridad cambiada a prio\n");
        }else if(strcmp(param1,"fg")==0){
            printf("fg prog args...	Crea un proceso que ejecuta en primer plano prog con argumentos\n");
        }else if(strcmp(param1,"fgpri")==0){
            printf("fgpri prio prog args...	Crea un proceso que ejecuta en primer plano prog con argumentos  con la prioridad cambiada a prio\n");
        }else if(strcmp(param1,"back")==0){
            printf("back prog args...	Crea un proceso que ejecuta en segundo plano prog con argumentos\n");
        }else if(strcmp(param1,"backpri")==0){
            printf("backpri prio prog args...	Crea un proceso que ejecuta en segundo plano prog con argumentos con la prioridad cambiada a prio\n");
        }else if(strcmp(param1,"fgas")==0){
            printf("fgas us prog args...	Crea un proceso que ejecuta en primer plano, y como el usuario us, prog con argumentos\n");
        }else if(strcmp(param1,"backas")==0){
            printf("backas us prog args...	Crea un proceso que ejecuta en segundo plano, y como el usuario us, prog con argumentos\n");
        }else if(strcmp(param1,"listjobs")==0){
            printf("listjobs 	Lista los procesos en segundo plano\n");
        }else if(strcmp(param1,"borrarjobs")==0){
            printf("borrarjobs [-term][-sig]	Elimina los propcesos terminados o terminados por senal de la lista de procesos en s.p.\n");
        }else if(strcmp(param1,"job")==0){
            printf("job [-fg] pid	Muestra informacion del proceso pid. -fg lo pasa a primer plano\n");
        }else if(strcmp(param1,"fgas")==0){
            printf("fgas us prog args...	Crea un proceso que ejecuta en primer plano, y como el usuario us, prog con argumentos\n");
        }else{
            printf(RED "This command doesn't exist\n" COLOR_RESET);
        }
    }else{
        printf(RED "This command doesn't exist\n" COLOR_RESET);
    }
    puts("********************");
}


//P1
void crear(cadena trozos[], int n){//declararlo de manera 
    struct stat st;
    int fd;//puntero que apunta a la direccion del fichero
    //FILE *fd;
    if(n==1){//obtenemos carpeta
        directorio();
    }else if(n==2 && strcmp(trozos[1],"-f")!=0){
        if(stat(trozos[1], &st)== -1){
            if(mkdir(trozos[1],0700)==-1){//el 0700 es para darle los permisos
                perror( "error" );
            }
        }else{
            printf(RED "Ese directorio ya existe\n" COLOR_RESET);
        }
    }else if(n==3 && strcmp(trozos[1],"-f")==0){
        if(lstat(trozos[2],&st)==-1){
            fd=open(trozos[2],O_CREAT, 0700);
            if(fd==-1){
                perror( "error" );
            }
        }else{
            printf(RED "Ese fichero ya existe\n" COLOR_RESET);
        }
        /*fd=fopen(trozos[2], "w");//nose si será esta función o otro tipo, mejor preguntar
        if(fd==NULL){
            perror( error );
        }
        fclose(fd);  */  
    }else{
        printf(RED "This command doesn't exist\n" COLOR_RESET);
    }
}
void borrar(cadena trozos[],int n){
    if(n==1){
        directorio();
    }else if(n>1){
        struct stat *st=malloc(sizeof(struct stat));

        for(int i=1; i<n && i<MAX_PALABRAS; i++){
            lstat (trozos[i], st);

            if(S_ISDIR (st->st_mode)){//miramos si es directorio
                
                /*if(st.st_size==0){
                    rmdir(trozos[i]);
                    puts("The directory has been deleted");
                }*/ //No funciona
                
                if(rmdir(trozos[i])==0){
                    puts("********************");
                    printf("-> The directory '%s' has been deleted\n", trozos[i]);
                    puts("********************");
                }else{
                    puts("********************");
                    perror( "error" );
                    
                    puts("********************");
                }
            }else{
                if(S_ISREG (st->st_mode) || S_ISLNK (st->st_mode)){
                    if(unlink(trozos[i])==0){
                        puts("********************");
                        printf("-> The file '%s' has been deleted\n", trozos[i]);
                        puts("********************");
                    }else{
                        puts("********************");
                        perror( "error" );
                        
                        puts("********************");
                    }
                }else{
                    puts("********************");
                    printf("-> '%s' is not a file or a directory\n", trozos[i]);
                    puts("********************");
                }
            }

        }
        free(st);
    }else{
        printf(RED "This command doesn't exist\n" COLOR_RESET);

    }
    
}
void listfich(cadena trozos[],int n){
    //VARIABLES
    bool long1=false, link1=false, acc1=false, anombre=false;
    //CODIGO
    if(n==1){
        directorio();
    }else if(n>1){
        //hacer lo de la parte anterior de manera bucle
        for(int i=1;i<n && i<MAX_PALABRAS;i++){//nos aseguramos que nunca se pase del numero maximo de palabras
            if(strcmp(trozos[i],"-long")==0  && anombre==false){
                long1=true;
            }else if(strcmp(trozos[i],"-link")==0  && anombre==false){
                link1=true;
            }else if(strcmp(trozos[i],"-acc")==0  && anombre==false){
                acc1=true;
            }else if(strcmp(trozos[i],"-long")!=0 && strcmp(trozos[i],"-link")!=0 && strcmp(trozos[i],"-acc")!=0){
                anombre=true;
                listar_long(acc1,link1,long1,trozos[i]);
                    
            }
        }
        

    }else{
        printf(RED "Opción no valida\n" COLOR_RESET);
    }
    //free(st);
}
void listdir(cadena trozos[],int n){//hacer que funcione -hid y dejarlo bonito(y poner lo de reca y recb)
    char ruta[MAX_RUTA];
    char ruta_actual[MAX_RUTA];
    struct stat *st=malloc(sizeof(struct stat));//preguntar como es que entra memoria ya reservada para st
    bool long1=false, link1=false, acc1=false, reca1=false, recb1=false, hid1=false, anombre=false;
    tList dirs,ficheros;
    tItemL nombres, items, Ificheros;
    tPosL p,q;
    DIR *dir;
    struct dirent *rdir;
    //CODIGO
    if(n==1){
        directorio();
    }else if(n>1){
        getcwd(ruta,MAX_RUTA);
        createList(&ficheros);
        createList(&dirs);
        //hacer lo de la parte anterior de manera bucle
        for(int i=1;i<n && i<MAX_PALABRAS;i++){//nos aseguramos que nunca se pase del numero maximo de palabras
            if(strcmp(trozos[i],"-long")==0 && anombre==false){
                long1=true;
            }else if(strcmp(trozos[i],"-link")==0  && anombre==false){
                link1=true;
            }else if(strcmp(trozos[i],"-acc")==0  && anombre==false){
                acc1=true;
            }else if(strcmp(trozos[i],"-reca")==0  && anombre==false){
                reca1=true;
            }else if(strcmp(trozos[i],"-recb")==0  && anombre==false){
                recb1=true;
            }else if(strcmp(trozos[i],"-hid")==0  && anombre==false){
                hid1=true;
            }else if(strcmp(trozos[i],"-long")!=0 && strcmp(trozos[i],"-link")!=0 && strcmp(trozos[i],"-acc")!=0 && strcmp(trozos[i],"-reca")!=0 && strcmp(trozos[i],"-recb")!=0 && strcmp(trozos[i],"-hid")!=0){
                anombre=true;
                if(lstat(trozos[i],st)==-1){
                    perror( "error" );
                }else{
                //*********************************************ALMACENADO******************************+
                        strcpy(nombres.command,trozos[i]);
                        if(isEmptyList(ficheros)){
                            nombres.numcode=0;
                        }else{
                            nombres.numcode=getItem(last(ficheros),ficheros).numcode+1;
                        }
                        if(strncmp(trozos[i],".",1)!=0 || (hid1 || strncmp(trozos[i],"./",2)==0)){
                            if(!(insertElement(nombres,LNULL,&ficheros))){
                                printf(RED "MEMORIA LLENA PARA INTRODUCIR EN LISTA DE FICHEROS\n" COLOR_RESET); 
                            } 
                        }
                        
                    if(S_ISDIR (st->st_mode)){//miramos si es directorio y guardamos en lista de directorios
                        strcpy(nombres.command,trozos[i]);
                        if(isEmptyList(dirs)){
                            nombres.numcode=0;
                        }else{
                            nombres.numcode=getItem(last(dirs),dirs).numcode+1;
                        }
                            if(strncmp(trozos[i],".",1)!=0 || (hid1 || strncmp(trozos[i],"./",2)==0)){
                            if(!(insertElement(nombres,LNULL,&dirs))){
                                printf(RED "MEMORIA LLENA PARA INTRODUCIR EN LISTA DE DIRECTORIOS\n" COLOR_RESET); 
                            }
                            }
                        
                     
                    }
                   //*********************************************ALMACENADO******************************+

                    
            }
        }
        }
        if((reca1==true || recb1==true) && reca1!=recb1){
            if(reca1){
            if (!isEmptyList(ficheros)) {//miramos si el historial esta vacio o no
                q = first(ficheros);
                //recorremos la lista monstrando los datos y actualizando contadores
                while (q != LNULL) {
                    Ificheros = getItem(q, ficheros);
                    listar_long(acc1,link1,long1,Ificheros.command);//lista el fichero

                    q = next(q, ficheros);
                }
            } 
            //***********************************
            if(!isEmptyList(dirs)){                                            
                recura_directorios(long1,hid1,acc1,link1,reca1,recb1,&dirs);  
                            
            } 
            }else if(recb1){
                if(!isEmptyList(dirs)){                                            
                recura_directorios(long1,hid1,acc1,link1,reca1,recb1,&dirs);  
                            
                }
                //***********************************
                if (!isEmptyList(ficheros)) {//miramos si el historial esta vacio o no
                    q = first(ficheros);
                    //recorremos la lista monstrando los datos y actualizando contadores
                    while (q != LNULL) {
                        Ificheros = getItem(q, ficheros);
                        listar_long(acc1,link1,long1,Ificheros.command);//lista el fichero

                        q = next(q, ficheros);
                    }
                }   
            }

        }else if(reca1==true && recb1==true){//no usar M y usar ficheros que al final hacen lo mismo
          printf(RED "No puedes activar -reca y -recb a la vez\n" COLOR_RESET);   
        }else{//caso no reca ni recb
            if (!isEmptyList(ficheros)) {//miramos si el historial esta vacio o no
                p = first(ficheros);
                //recorremos la lista monstrando los datos y actualizando contadores
                while (p != LNULL) {
                    items = getItem(p, ficheros); 
                    listar_long(acc1,link1,long1,items.command);
                    if(S_ISDIR (st->st_mode)){
                        dir=opendir(items.command);
                        getcwd(ruta_actual,MAX_RUTA);
                        strcpy(ruta_actual,"/");
                        strcpy(ruta_actual,items.command);
                        if(chdir(ruta_actual)==0){
                            printf("+++++++++++++++++++++++++ABRIENDO %s+++++++++++++++++++++++++++++++++\n", items.command);
                            if(dir==NULL){
                                perror( "error" );
                            }else{

                            while((rdir = readdir(dir)) != NULL){
                                if(strncmp(rdir->d_name,".\0",2)!=0 && strncmp(rdir->d_name,"..\0",3)!=0){
                                    if(strncmp(rdir->d_name,".",1)!=0 || (hid1 || strncmp(rdir->d_name,"./",2)==0)){
                                        listar_long(acc1,link1,long1,rdir->d_name);
                                    }
                                }                            
                            } 
                            closedir(dir);

                            } 
                        printf("+++++++++++++++++++++++++CERRANDO %s+++++++++++++++++++++++++++++++++\n", items.command);  
                        }

                    }

                    p = next(p, ficheros);
                }
            }


        }
        chdir(ruta);
        deleteList(&ficheros);
        deleteList(&dirs);
    }
    free(st);
    
    
}
void borrar_rec(cadena trozos[],int n){
    struct stat *st=malloc(sizeof(struct stat));

    if(n==1){
        directorio();
    }else if(n>1){
        for(int i=1; i<n && i<MAX_PALABRAS;i++){
            if(lstat(trozos[i],st)==-1){
                perror( "error" );
            }else{
                if(S_ISDIR (st->st_mode)){
                    if(strncmp(trozos[i],".\0",2)!=0 && strncmp(trozos[i],"..\0",3)!=0){
                        borrar1_directorios(trozos[i]);
                    }
                }else if(S_ISREG (st->st_mode) || S_ISLNK (st->st_mode)){
                    if(unlink(trozos[i])!=0){
                        perror( "error" );
                    }
                }else{

                }
            }
        }
    }
    free(st);
}


//FUNCIONES AUXILIARES
void recura_directorios(bool long1,bool hid1,bool acc1, bool link1,bool reca1,bool recb1, tList *directorios){//POÑER BEN O MOSTRAR(A RECURSIVIDAD FUNCION BEN)
    //VARIABLES
    //char ruta_actual[MAX_RUTA];
    char ruta[MAX_RUTA];//sino para recorrer recursivamente el directorio dirá que no lo encuentra
    DIR *dir;
    struct dirent *rdir;
    struct stat *st=malloc(sizeof(struct stat));
    tItemL nombres, items, Ificheros;
    tPosL p,q;
    tList ficheros, dirs;
    //CODIGO
    //con esto hacer un bucle y mirar bien como es 
        if(!isEmptyList(*directorios)){
            getcwd(ruta,MAX_RUTA);
            p = first(*directorios);
            while (p != LNULL) {//recorremos la lista de directorios cada directorio
                createList(&ficheros);
                createList(&dirs);
                items = getItem(p, *directorios);
                //listar_long(acc1,link1,long1,items.command);
                printf("+++++++++++++++++++++++++ABRIENDO %s+++++++++++++++++++++++++++++++++\n", items.command);
                dir=opendir(items.command); 
                    
                    if(dir==NULL){
                        perror( "error" );
                    }else{

                        if(chdir(items.command) == 0){
                        while((rdir = readdir(dir)) != NULL){
                        
                            if(strncmp(rdir->d_name,".\0",2)!=0 && strncmp(rdir->d_name,"..\0",3)!=0){

                                if(lstat(rdir->d_name,st)==-1){
                                    perror( "error" );
                                }else{
                                    //*********************************************ALMACENADO******************************+
                                        strcpy(nombres.command,rdir->d_name);
                                        if(isEmptyList(ficheros)){
                                            nombres.numcode=0;
                                        }else{
                                            nombres.numcode=getItem(last(ficheros),ficheros).numcode+1;
                                        }
                                        if(strncmp(rdir->d_name,".",1)!=0 || (hid1 || strncmp(rdir->d_name,"./",2)==0)){
                                            if(!(insertElement(nombres,LNULL,&ficheros))){
                                                printf(RED "MEMORIA LLENA PARA INTRODUCIR EN LISTA DE FICHEROS\n" COLOR_RESET); 
                                            } 
                                        }
                                    if(S_ISDIR (st->st_mode)){//miramos si es directorio y guardamos en lista de directorios
                                        strcpy(nombres.command,rdir->d_name);
                                        if(isEmptyList(dirs)){
                                            nombres.numcode=0;
                                        }else{
                                            nombres.numcode=getItem(last(dirs),dirs).numcode+1;
                                        }
                                        if(strncmp(rdir->d_name,".\0",2)!=0 && strncmp(rdir->d_name,"..\0",3)!=0){
                                            if(strncmp(rdir->d_name,".",1)!=0 || (hid1 || strncmp(rdir->d_name,"./",2)==0)){

                                            if(!(insertElement(nombres,LNULL,&dirs))){
                                                printf(RED "MEMORIA LLENA PARA INTRODUCIR EN LISTA DE DIRECTORIOS\n" COLOR_RESET); 
                                            }
                                            }
                                        }
                                     
                                    
                                    }
                                        //*********************************************ALMACENADO******************************+
                                    
                                }
                            }
                        }
                        }
                        closedir(dir);
                    }   

            //recura
        if(reca1){
            //***********************************
            if (!isEmptyList(ficheros)) {//miramos si el historial esta vacio o no
                q = first(ficheros);
                //recorremos la lista monstrando los datos y actualizando contadores
                while (q != LNULL) {
                    Ificheros = getItem(q, ficheros);
                    listar_long(acc1,link1,long1,Ificheros.command);//lista el fichero

                    q = next(q, ficheros);
                }
            }  
            //***********************************
            if(!isEmptyList(dirs)){                                            
                recura_directorios(long1,hid1,acc1,link1,reca1,recb1,&dirs);  
                            
            }
        }else if(recb1){
            if(!isEmptyList(dirs)){                                            
            recura_directorios(long1,hid1,acc1,link1,reca1,recb1,&dirs);  
                        
            }
            //***********************************
            if (!isEmptyList(ficheros)) {//miramos si el historial esta vacio o no
                q = first(ficheros);
                //recorremos la lista monstrando los datos y actualizando contadores
                while (q != LNULL) {
                    Ificheros = getItem(q, ficheros);
                    listar_long(acc1,link1,long1,Ificheros.command);//lista el fichero

                    q = next(q, ficheros);
                }
            } 
        }
        //recurb
        chdir(ruta);
        deleteList(&ficheros);
        deleteList(&dirs);
        printf("+++++++++++++++++++++++++CERRANDO %s+++++++++++++++++++++++++++++++++\n", items.command);
        p = next(p, *directorios);
        }
        free(st);
    }//falta mirar lo de las rutas a ver como hacer
}
void directorio(){//lista directorio actual
    char dir[200];
    if(getcwd(dir, sizeof(dir))==NULL){
        perror( "error" );  
    }else{
        printf(BLUE "%s\n", dir);
    }    

}
bool isNumber(cadena param1) {//funcion para comprobar si una cadena es un numero
    int cnt=0;
    for(int i=0; i<strlen(param1); i++){
        if (isdigit(param1[i]))
            cnt++;
    }
    if(cnt == strlen(param1)) {
        return true;
    }
    else {
        return false;
    }
}
char LetraTF (mode_t m){
    switch (m&S_IFMT) { /*and bit a bit con los bits de formato,0170000 */
        case S_IFSOCK: return 's'; /*socket */
        case S_IFLNK: return 'l'; /*symbolic link*/
        case S_IFREG: return '-'; /* fichero normal*/
        case S_IFBLK: return 'b'; /*block device*/
        case S_IFDIR: return 'd'; /*directorio */
        case S_IFCHR: return 'c'; /*char device*/
        case S_IFIFO: return 'p'; /*pipe*/
        default: return '?'; /*desconocido, no deberia aparecer*/
    }   
}
char * ConvierteModo2 (mode_t m){
static char permisos[12];
strcpy (permisos,"---------- ");
permisos[0]=LetraTF(m);//mira el tipo de fichero
if (m&S_IRUSR) permisos[1]='r'; /*propietario*/
if (m&S_IWUSR) permisos[2]='w';
if (m&S_IXUSR) permisos[3]='x';
if (m&S_IRGRP) permisos[4]='r'; /*grupo*/
if (m&S_IWGRP) permisos[5]='w';
if (m&S_IXGRP) permisos[6]='x';
if (m&S_IROTH) permisos[7]='r'; /*resto*/
if (m&S_IWOTH) permisos[8]='w';
if (m&S_IXOTH) permisos[9]='x';
if (m&S_ISUID) permisos[3]='s'; /*setuid, setgid y stickybit*/
if (m&S_ISGID) permisos[6]='s';
if (m&S_ISVTX) permisos[9]='t';
return (permisos);
}
void listar_long(bool acc1, bool link1,bool long1, cadena name){//lista datos del directorio
    struct stat *st=malloc(sizeof(struct stat));//preguntar como es que entra memoria ya reservada para st
    struct tm *fecha;
    struct passwd *usr;
    struct group *grp;
    char time[50];
    char link[200];
    char* perm;
    if(long1){
        if(lstat(name,st)==-1){
            perror( "error" );
        }else{
    
            usr=getpwuid(st->st_uid);
            if(usr!=NULL){//comprobamos que se obtiee el user bien
                grp=getgrgid(st->st_gid);
                if(grp!=NULL){//comprobamos que se obtiene el grp bien
                    if(acc1){//convertimos las fechas a un formato accesible con la funcion localtime
                        fecha=localtime(&st->st_atime);//separamos el formato time_t en el struct tm
                        strftime(time,50,"%y/%m/%d-%H:%M:%S",fecha);//elegimos camposd e tm
                        printf("%s ", time);//imprimimos tm
                    }else{
                        fecha=localtime(&st->st_mtime);
                        strftime(time,50,"%y/%m/%d-%H:%M:%S",fecha);
                        printf("%s ", time);
                    } 
                    //listfich -long -acc -link dynamic_list.h p0.c dynamic_list.c prueba pad
                    perm=ConvierteModo2(st->st_mode);                               
                    printf("%ld (%ld) %s %s %s %ld %s", st->st_nlink, st->st_ino, usr->pw_name, grp->gr_name, perm, st->st_size, name);//ConvierteModo2(st.st_mode)
                    if(link1){//tenemos una llamada al sistema operativo que nos dice si un fichero es un link simbolico
                        if(S_ISLNK(st->st_mode)){
                            if(readlink(name,link,200)!=-1){
                                printf("-> %s",link);
                            }else{
                                perror( "error" );
                            }
                        }
                    } 
                }else{
                    perror( "error" );
                }
            }else{
                perror( "error" );
            }  
        printf("\n");
        }
    }else{
        if(lstat(name,st)==-1){
            perror( "error" );
        }else{
            printf("%ld %s\n",st->st_size ,name);
        }
    
    }
    free(st);
}
void borrar1_directorios(cadena directorio){
    //char ruta_actual[MAX_RUTA];
    char ruta[MAX_RUTA];//sino para recorrer recursivamente el directorio dirá que no lo encuentra
    DIR *dir;
    struct dirent *rdir;
    struct stat *st=malloc(sizeof(struct stat));
    getcwd(ruta,MAX_RUTA);
    dir= opendir(directorio);
    if(dir==NULL){
        perror( "error" );
    }else{
        if(chdir(directorio)==0){
            while((rdir=readdir(dir))!=NULL){
                if(strncmp(rdir->d_name,".\0",2)!=0 && strncmp(rdir->d_name,"..\0",3)!=0){
                    if(lstat(rdir->d_name,st)==-1){
                        perror( "error" );
                    }else{
                        if(S_ISDIR (st->st_mode)){
                            if(strncmp(rdir->d_name,".\0",2)!=0 && strncmp(rdir->d_name,"..\0",3)!=0){
                                borrar1_directorios(rdir->d_name);
                            }


                        }else if(S_ISREG (st->st_mode) || S_ISLNK (st->st_mode)){
                            if(unlink(rdir->d_name)!=0){
                                perror( "error" );
                            }
                        }else{

                        }

                    }
                }
            }
            chdir(ruta);
            if(rmdir(directorio)!=0){
                perror( "error" );
            }
            closedir(dir);//revisar si cambiar donde ponerlo
        }else{
            perror( "error" );
        }
    }
    chdir(ruta);
    free(st);
}



