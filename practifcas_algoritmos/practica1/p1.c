
/*
AUTORES:
Diego Suarez Ramos : diego.suarez.ramos@udc.es
Ander Varela Martin : ander.varela@udc.es
Brais Rodruiguez Gonzalez : brais.rodriguez.gonzalez@udc.es


*/

#include <stdio.h>
#include <sys/time.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
//FUNCIONES:
int sumaSubMax1(int v[], int n) {
    int i, j;
    int estaSuma, sumaMax = 0;
    for (i = 0; i < n; i++) {
        estaSuma = 0;
        for (j = i; j < n; j++) {
            estaSuma += v[j];
            if (estaSuma > sumaMax) {
                sumaMax = estaSuma;
            }
        }
    }
    return sumaMax;
}

int sumaSubMax2(int v[], int n) {
    int j;
    int estaSuma=0, sumaMax=0;
    for(j=0;j<n;j++){
        estaSuma =estaSuma + v[j];
        if(estaSuma > sumaMax){
            sumaMax = estaSuma;
        }else if(estaSuma < 0){
            estaSuma = 0;
        }
    }
    return sumaMax;
}

//FUNCIONES AUXILIARES
void inicializar_semilla() {
    srand(time(NULL));
/* se establece la semilla de una nueva serie de enteros pseudo-aleatorios */
}

void aleatorio(int v [], int n) {
    int i, m=2*n+1;
    for (i=0; i < n; i++){
        v[i] = (rand() % m) - n;
    }
/* se generan números pseudoaleatorio entre -n y +n */
}

double microsegundos() { /* obtiene la hora del sistema en microsegundos */
    struct timeval t;
    if (gettimeofday(&t, NULL) < 0 ){
        return 0.0;
    }
    return (t.tv_usec + t.tv_sec * 1000000.0);
}

void listar_vector(int v[], int n){
    printf("[ ");
    for(int i=0;i<n;i++){
        printf("%3d ",v[i]);
    }
    printf("]");
}




//TEST:
void test1() {
	int i, a, b;
	int v[6][5]={{ -9,2,-5,-4, 6},  
                { 4,0,9,2,5},  
                { -2,-1,-9,-7,-1},
                { 9,-2,1,-7,-8},
                { 15,-2,-5,-4,16},  
                { 7,-5,6,7,-7}};
	printf("test\n");
	printf("%25s%15s%15s\n", "", "sumaSubMax1", "sumaSubMax2");
	for (i=0; i<6; i++) {
		listar_vector(v[i], 5);
		a = sumaSubMax1(v[i], 5);
		b = sumaSubMax2(v[i], 5);
		printf("%15d%15d\n", a, b);
	}
}

void test2() {
    int i, a, b;
    int v[9];
    printf("test\n");
    printf("%35s%20s%15s\n", "", "sumaSubMax1", "sumaSubMax2");
    for (i=0; i<10; i++) {
        aleatorio(v, 9);
        listar_vector(v, 9);
        a = sumaSubMax1(v, 9);
        b = sumaSubMax2(v, 9);
        printf("%15d%15d\n", a, b);
    }
}
//FUNCIONES PARA TABLA
void printfalgord(int alg, int orden){
	if(alg==1) printf("SumaSubMax 1\n");
	if(alg==2) printf("SumaSubMax 2\n");
}
double calculartiempos(int (*sumasub)(int v[],int n),int z){
    double t1,t2,t;
    int v[z];
    int i;
    aleatorio(v, z);
    t1=microsegundos();
    sumasub(v, z);
    t2=microsegundos();
    t=t2-t1;
    if(t<500){
        t1=microsegundos();
        for(i=0;i<1000;i++){
            sumasub(v,z);
        }
        t2=microsegundos();
        printf("(*)");
        return ((t2-t1)/1000);
    }else{
        printf("   ");
        return t;
    }

}

void tabla (int (*sumasub)(int v[], int n),int initcnt,int nmax,int modo, float p){//para sum1
    double t,contador=initcnt;
    int i;

	//llamar a funcionprintf("SumaSubMax 1\n");
    printf("%15s%15s%15s","n","t(n)","t(n)/n^1.8");
    printf("%15s%15s\n","t(n)/n^2","t(n)/n^2.2");
	if(modo==1){
		for(i=1;i<=nmax;i++){
			t=calculartiempos(sumasub,contador);
			printf("%12.0f%15.3f%15.6f",contador,t,t/pow(contador,p-0.2));
			printf("%15.6f%15.6f\n",t/(contador*log(contador)),t/pow(contador,p+0.2));
			contador=contador*2;
		}
	}else{
		for(i=1;i<=nmax;i++){
			t=calculartiempos(sumasub,contador);
			printf("%12.0f%15.3f%15.6f",contador,t,t/pow(contador,p-0.2));
			printf("%15.6f%15.6f\n",t/pow(contador,p),t/pow(contador,p+0.2));
			contador=contador*2;
		}	
	}
}
/*PARAMETROS:
1-funcion sumaSubMax
2-cantidad elementos inciales
3- COLUMNAS
4-copmlejidad a escoger
5-n elevado a k, la k

*/
int main() {
    inicializar_semilla();
    tabla(sumaSubMax1,500,7,2,2);
    tabla(sumaSubMax1,500,7,2,2);
    tabla(sumaSubMax1,500,7,2,2);
    tabla(sumaSubMax1,500,7,2,2);
    tabla(sumaSubMax2,500,7,2,1);
    tabla(sumaSubMax2,500,7,2,1);
    tabla(sumaSubMax2,500,7,2,1);
    tabla(sumaSubMax2,500,7,2,1);
    return 0;
}