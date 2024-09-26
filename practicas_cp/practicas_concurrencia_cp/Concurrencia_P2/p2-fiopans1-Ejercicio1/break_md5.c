#include <sys/types.h>
#include <openssl/md5.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>

#define PASS_LEN 6
#define E_BARRA 50

struct args{//argumentos compartidos
    pthread_mutex_t mutex;//mutex genera
    int blk; //bloquea la barra
    long estimacion; //para sincronizar barra y break_pass
    pthread_t id; //id del thread de la barra
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

void long_to_pass(long n, unsigned char *str) {  // str should have size PASS_SIZE+1
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
    for(int i=0; i < MD5_DIGEST_LENGTH; i++)
        hex[i] = (hex_value(str[i*2]) << 4) + hex_value(str[i*2 + 1]);
}

char *break_pass(unsigned char *md5, struct args *args) {
    unsigned char res[MD5_DIGEST_LENGTH];
    unsigned char *pass = malloc((PASS_LEN + 1) * sizeof(char));
    long bound = ipow(26, PASS_LEN); // we have passwords of PASS_LEN
                                     // lowercase chars =>
                                    //     26 ^ PASS_LEN  different cases

    for(long i=0; i < bound; i++) {
        long_to_pass(i, pass);

        MD5(pass, PASS_LEN, res); 
        pthread_mutex_lock(&args->mutex);
        args->estimacion++;
        if(0 == memcmp(res, md5, MD5_DIGEST_LENGTH)){
            args->blk=0;
            pthread_mutex_unlock(&args->mutex);
            break;
        } // Found it!
        pthread_mutex_unlock(&args->mutex);

    }

    return (char *) pass;
}


 
void *progressBar(void* ptr){//muestra la barra de progreso
    struct args *args =  ptr;
        int i = 0;
        char bar[E_BARRA+1];
        memset(bar, 0, sizeof(bar));
        long limit=ipow(26,6);
        float perc=0;

        const char *lable = "|/-\\";
        printf("[%-50s][%.02f%%][%c]\r", bar, perc, lable[i %4 ]);
        while(1){
            pthread_mutex_lock(&args->mutex);
            if(!args->blk){
                pthread_mutex_unlock(&args->mutex);
                return NULL;
            }
            perc=((float)(args->estimacion*100)/(float)limit);
            pthread_mutex_unlock(&args->mutex);
            fflush(stdout);
            i=perc/2;
            if(i<E_BARRA){
                for(int j=0;j<i;j++){
                    bar[j] = '#';
                }
            }
            printf("[%-50s][%.02f%%][%c]\r", bar, perc, lable[i %4 ]);
            usleep(1000000);
        }
        
        return NULL;
}

struct args  *lanzar_barra(){
    struct args *args= malloc(sizeof(struct args));
    args->estimacion=0;
    args->blk=1;
    pthread_mutex_init(&args->mutex,NULL);
    pthread_create(&args->id,NULL,progressBar,args);
    return args;

}

void destruir(struct args *args, char *pass, char *argv[]){

    pthread_join(args->id,NULL);
    pthread_mutex_destroy(&args->mutex);
    printf("%s: %s                                                          \n", argv[1], pass);
    free(pass);
    free(args);


}

int main(int argc, char *argv[]) {
    if(argc < 2) {
        printf("Use: %s string\n", argv[0]);
        exit(0);
    }
    struct args *args;

    args=lanzar_barra();

    unsigned char md5_num[MD5_DIGEST_LENGTH];
    hex_to_num(argv[1], md5_num);

    char *pass = break_pass(md5_num,args);
    destruir(args,pass,argv);//destruye args
    return 0;
}
