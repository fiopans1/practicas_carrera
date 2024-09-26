#include <sys/types.h>
#include <openssl/md5.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>
#include <math.h>

#define PASS_LEN 6
#define E_BARRA 50
#define T_INIT 4

struct args{//argumentos compartidos
    pthread_mutex_t mutex;//mutex general
    int blk;//para que los distintos threads esten bloqueados hasta encontrar la solucion
    long estimacion;//variable para calcular las iteraciones por segundo
    pthread_t id;//id del thread de la barra
};

struct abreak{
    struct args *args; //puntero a args
    long cnt;//contador compartido para romper contraseña
    unsigned char *pass;//para guardar solucion
    struct md5_info *md5;//acceso al struct md5_info
    pthread_mutex_t mutex;//mutex para este struct


};
struct thread_info{
    pthread_t id;//id de cada thread que rommpe contraseña
    struct abreak *args;//puntero a abreak
};
struct md5_info{
    unsigned char **md5;//array de claves a descodificar
    int md5_len;//numero de claves a descodificar
    int i_resuelto;//posicion del array de la clave que descodificamos
};


long ipow(long base, int exp)
{
    long res = 1;
    for (;;)
    {
        if (exp & 1)
            res *= base;
        exp >>= 1;
        if (!exp)
            break;
        base *= base;
    }

    return res;
}

long pass_to_long(char *str) {
    long res = 0;

    for(int i=0; i < PASS_LEN; i++)
        res = res * 26 + str[i]-'a';

    return res;
};

void long_to_pass(long n, unsigned char *str) {
    for(int i=PASS_LEN-1; i >= 0; i--) {
        str[i] = n % 26 + 'a';
        n /= 26;
    }
    str[PASS_LEN] = '\0';
}

int hex_value(char c) {
    if (c>='0' && c <='9')
        return c - '0';
    else if (c>= 'A' && c <='F')
        return c-'A'+10;
    else if (c>= 'a' && c <='f')
        return c-'a'+10;
    else return 0;
}

void hex_to_num(char *str, unsigned char *hex) {
    for(int i=0; i < MD5_DIGEST_LENGTH; i++){
        hex[i] = (hex_value(str[i*2]) << 4) + hex_value(str[i*2 + 1]);
    }
}

long min(long a, long b){
    if(a<b){
        return a;
    }else{
        return b;
    }
}

void *break_pass(void *ptr){
    struct abreak *abreak= ptr;
    unsigned char res[MD5_DIGEST_LENGTH];
    unsigned char *pass = malloc((PASS_LEN + 1) * sizeof(char));
    int v;
    int z;
    long bound = ipow(26, PASS_LEN), casos_locales, casos_locales_limite;
    while(1){
        pthread_mutex_lock(&abreak->mutex);
        if(abreak->cnt>=bound-1){
            pthread_mutex_unlock(&abreak->mutex);
            free(pass);
            return NULL;
        }
        casos_locales=abreak->cnt;
        abreak->cnt+=min(bound - (abreak->cnt),1000);
        casos_locales_limite=  abreak->cnt;
        pthread_mutex_unlock(&abreak->mutex);

        for(long i=casos_locales;i<casos_locales_limite && i<bound;i++){
            long_to_pass(i, pass);
            MD5(pass, PASS_LEN, res);
            for(int j=0;j<abreak->md5->md5_len;j++){//este array es de solo lectura asi que no hace falta mutex
                v=memcmp(res, abreak->md5->md5[j], MD5_DIGEST_LENGTH);
                if(v==0){
                    z=j;
                    break;
                }
            }
            pthread_mutex_lock(&abreak->args->mutex);
            if(abreak->args->blk==0){
                pthread_mutex_unlock(&abreak->args->mutex);
                free(pass);
                return NULL;

            }
            abreak->args->estimacion++;
            if(0 == v){
                abreak->args->blk=0;
                pthread_mutex_unlock(&abreak->args->mutex);
                pthread_mutex_lock(&abreak->mutex);
                abreak->pass=pass;
                abreak->md5->i_resuelto=z;
                pthread_mutex_unlock(&abreak->mutex);
                return NULL;
            } // Found it!
            pthread_mutex_unlock(&abreak->args->mutex);
        }


    }

}


 
void *progressBar(void* ptr){//imprime la barra
    struct args *args =  ptr;
        int i = 0;
        char bar[E_BARRA+1];
        memset(bar, 0, sizeof(bar));
        long estimacion1=0,estimacion2=0;
        long limit=ipow(26,6);
        float perc=0;

        const char *lable = "|/-\\";
        printf("[%-50s][%.02f%%][%c][iters/sec->%ld]\r", bar, perc, lable[i %4 ],(estimacion2-estimacion1));
        while(1){
            pthread_mutex_lock(&args->mutex);
            if(args->blk==0){
                pthread_mutex_unlock(&args->mutex);
                return NULL;
            }
            estimacion1=args->estimacion;
            pthread_mutex_unlock(&args->mutex);
            usleep(1000000);
            pthread_mutex_lock(&args->mutex);
            estimacion2=args->estimacion;
            perc=(((float)(args->estimacion*100))/(float)limit);
            pthread_mutex_unlock(&args->mutex);
            fflush(stdout);
            i=perc/2;
            if(i<E_BARRA){
                for(int j=0;j<i;j++){
                    bar[j] = '#';
                }
            }
            printf("[%-50s][%.02f%%][%c][iters/sec->%ld]\r", bar, perc, lable[i %4 ],(estimacion2-estimacion1));

        }
        
        return NULL;
}

struct thread_info *init_threads(struct args *args,struct md5_info *md5){
    struct thread_info *ar=malloc(sizeof(struct thread_info)*T_INIT);
    struct abreak *ar1=malloc(sizeof(struct abreak));
    ar1->md5=md5;
    ar1->cnt=0;
    pthread_mutex_init(&ar1->mutex,NULL);
    ar1->args=args;

    
    
    for(int i=0;i<T_INIT;i++){
        ar[i].args=ar1;
        if(pthread_create(&ar[i].id,NULL,break_pass,ar[i].args) != 0){
            printf("Could not create thread #%d", i);
            exit(1);
        }
    }
    return ar;



}



struct args  *lanzar_barra(){
    struct args *args= malloc(sizeof(struct args));
    args->blk=1;
    args->estimacion=0;
    pthread_mutex_init(&args->mutex,NULL);
    pthread_create(&args->id,NULL,progressBar,args);
    return args;

}

void destruir(struct thread_info *ar, char **s){
    for(int i=0;i<T_INIT; i++){
        pthread_join(ar[i].id,NULL);
    }
    pthread_join(ar[0].args->args->id,NULL);
    printf("%s: %s                                                 \n", s[ar->args->md5->i_resuelto+1], ar[0].args->pass);
    free(ar[0].args->pass);
    for(int i=0;i<ar[0].args->md5->md5_len;i++){
        free(ar[0].args->md5->md5[i]);
    }
    free(ar[0].args->md5->md5);
    pthread_mutex_destroy(&ar[0].args->args->mutex);
    pthread_mutex_destroy(&ar[0].args->mutex);
    free(ar[0].args->args);
    free(ar[0].args);
    free(ar);


}

int main(int argc, char *argv[]) {
    if(argc < 2) {
        printf("Use: %s string\n", argv[0]);
        exit(0);
    }
    struct args *args;
    struct thread_info *threads;
    struct md5_info md5;
    args=lanzar_barra();

    md5.md5_len=argc-1;
    md5.md5=malloc(md5.md5_len);
    for(int i=0;i<md5.md5_len;i++){
        md5.md5[i]=malloc(sizeof(unsigned char)*MD5_DIGEST_LENGTH);
        hex_to_num(argv[i+1], md5.md5[i]);
    }
    md5.i_resuelto=0;

    threads=init_threads(args,&md5);

    destruir(threads,argv);//destruye todo y hace el printf
    return 0;
}