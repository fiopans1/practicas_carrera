// Diego Suarez Ramos : diego.suarez.ramos@udc.es

#include <stdio.h>
#include <math.h>
#include <stdbool.h>
#ifdef __APPLE__
#include <OpenGL/gl.h>
#include <OpenGL/glu.h>
#include <GLUT/glut.h>
#else
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#endif
#define WIDTH 400
#define HEIGTH 400
#define ORIGIN_X 100
#define ORIGIN_Y 100

const int sides = 100;    // numero de lados del circulo
const float radius = 3.0; // Radio del círculo.
const float depth = 1.0;  // Altura del círculo en el eje Y.
GLfloat angulosFigura[2] = {0.0, 0.0};
GLfloat movimientoBrazo[2] = {0.0f, 0.0f};
int velocidad = 100;
GLfloat aumento = 2.0f;
GLfloat giro45 = 2.0f;
float colorR = 1.0;
float colorG = 0.84;
float colorB = 0.0;
GLfloat mouseX = 0.0f;
GLfloat mouseY = 0.0f;
int mousseButtonState = 0;
float sensitivity = 0.1; // Ajusta esta sensibilidad según tus necesidades
bool hazPerspectiva = false;
bool stop = false;
GLfloat giroPlataforma = 0.0f;
bool flechaArribaPresionada = false;

void init()
{
    glClearColor(0.0, 0.0, 0.0, 0.0);
}
void special(int key, int x, int y)
{
    if (key == GLUT_ACTIVE_ALT)
    {
        flechaArribaPresionada = true;
        printf("Tecla de flecha hacia arriba presionada\n");
    }
}

void specialUp(int key, int x, int y)
{
    if (key == GLUT_ACTIVE_ALT)
    {
        flechaArribaPresionada = false;
        printf("Tecla de flecha hacia arriba soltada\n");
    }
}
void mouseMovement(int button, int state, int x, int y)
{
    if (state = GLUT_DOWN)
    {
        mousseButtonState = button;
        mouseX = x;
        mouseY = y;
    }
    else if (state = GLUT_UP)
    {
        mousseButtonState = 0;
    }
}
void motion(int x, int y)
{
    int deltaX = x - mouseX;
    int deltaY = y - mouseY;
    deltaX *= sensitivity; // porque sino va demasiado loco
    deltaY *= sensitivity;

    switch (mousseButtonState)
    {
    case GLUT_LEFT_BUTTON:
        if (flechaArribaPresionada)
        {
            angulosFigura[0] += deltaX;
        }
        else
        {

            giroPlataforma += deltaY;
        }
    }
}

void drawBase()
{
    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

    glRotatef(90, 1, 0, 0); // Gira la vista

    glColor3f(1, 0.63, 0.48);

    GLUquadric *quad = gluNewQuadric();
    gluQuadricNormals(quad, GLU_SMOOTH);
    glPushMatrix();
    glTranslatef(0.0, 0.0, 0.0);              // Ajusta la posición del cilindro
    gluCylinder(quad, 4.0, 4.0, 1.0, 20, 20); // Dibuja un cilindro
    glPopMatrix();

    glPushMatrix();
    glTranslatef(0.0, 0.0, 0.0);
    gluDisk(quad, 0.0, 4.0, 20, 20); // Dibuja las bases del cilindro
    glPopMatrix();

    gluDeleteQuadric(quad); // Color del círculo (blanco en este caso).
}
void rotate()
{
    glRotatef(angulosFigura[0], 1.0f, 0.0f, 0.0f);
    glRotatef(angulosFigura[1], 0.0f, 1.0f, 0.0f);
}
void drawCone()
{
    glPushMatrix();
    glColor3f(0.0, 1.0, 1.0);    // Color rojo
    glTranslatef(0.0, 0.0, 0.0); // Translación para centrar el cono
    glRotatef(-90, 1.0, 0.0, 0.0);
    glutSolidCone(3.5, 3.0, 20, 20);
    glPopMatrix();
}

void drawBrazo()
{
    // movemos al punto de rotación
    glTranslatef(0.0f, 3.0f, 0.0);
    // Aplica la rotación
    glRotatef(movimientoBrazo[0], 0.0, 1.0, 0.0); // Rotación en sentido antihorario alrededor del eje z
    glRotatef(movimientoBrazo[1], 0.0, 0.0, 1.0);
    // Vuelve a trasladar al punto de rotación original

    glColor3f(0.66, 0.66, 0.66);
    glTranslatef(2.0f, 0.0f, 0.0f);
    glPushMatrix();
    glScalef(5.0f, 1.0f, 1.5f);
    glutSolidCube(1.0);
    glPopMatrix();
    glTranslatef(2.5f, 1.0f, 0.0f);
    // base
    glColor3f(0.6, 0.98, 0.6);
    glRotatef(-movimientoBrazo[0], 0.0, 1.0, 0.0);
    glRotatef(giroPlataforma, 0.0, 1.0, 0.0);
    glRotatef(-movimientoBrazo[1], 0.0, 0.0, 1.0);
    glPushMatrix();
    glRotatef(90, 1.0, 0.0, 0.0);
    glutSolidCone(1.5, 1.5, 20, 20);
    glPopMatrix();
    //  pasajero1
    glPushMatrix();
    glTranslatef(0.45f, 0.3f, -0.45f);
    glColor3f(colorR, colorG, colorB);
    glutSolidCube(0.75);
    glPopMatrix();
    // pasajero2
    glPushMatrix();
    glTranslatef(-0.45f, 0.3f, -0.45f);
    glutSolidCube(0.75);
    glPopMatrix();
    // pasajero3
    glPushMatrix();
    glTranslatef(0.45f, 0.3f, 0.55f);
    glutSolidCube(0.75);
    glPopMatrix();
    // pasajero4
    glPushMatrix();
    glTranslatef(-0.45f, 0.3f, 0.55f);
    glutSolidCube(0.75);
    glPopMatrix();
}
void display()
{
    glClear(GL_COLOR_BUFFER_BIT);

    glLoadIdentity();
    rotate();
    drawBase();

    glLoadIdentity();
    rotate();
    drawCone();

    glLoadIdentity();
    rotate();
    drawBrazo();

    glutSwapBuffers();
}
void reshape(int w, int h)
{
    glViewport(0, 0, (GLsizei)w, (GLsizei)h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    if (hazPerspectiva)
        gluPerspective(-200.0f, (GLfloat)w / (GLfloat)h, 1.0f, 20.0f); // revisar por que esto no funcona bien
    else
        glOrtho(-10.0, 10.0, -10.0, 10.0, -10.0, 10.0);

    glMatrixMode(GL_MODELVIEW);
}
void keyboard(unsigned char key, int x, int y)
{
    if (glutGetModifiers() & GLUT_ACTIVE_ALT)
    {
        flechaArribaPresionada = true; // hay que pulsar una tecla a parte del alt para activarlo
    }
    else
    {
        flechaArribaPresionada = false; // hay que pulsar una tecla a parte del alt para desactivarlo
    }
    switch (key)
    {
        /*
    case 'q':
    case 'Q':
        angulosFigura[0] += 4.0f;
        break;

    case 'a':
    case 'A':

        angulosFigura[0] -= 4.0f;

        break;
    case 'E':
    case 'e':
        angulosFigura[1] += 4.0f;
        break;

    case 'd':
    case 'D':

        angulosFigura[1] -= 4.0f;

        break;*/
    case 'R':
    case 'r':
        aumento = aumento * -1.0f;
        break;
    case 'o':
    case 'O':

        hazPerspectiva = false;
        reshape(WIDTH, HEIGTH);

        break;
    case 'p':
    case 'P':
        hazPerspectiva = true;
        reshape(WIDTH, HEIGTH);
        break;
    case 's':
    case 'S':

        stop = !stop;

        break;
    default:
        break;
    }
}
void timerRotation(int value)
{
    if (!stop)
    {
        movimientoBrazo[0] += aumento;
        movimientoBrazo[1] += giro45;
    }
    glutTimerFunc(velocidad, timerRotation, value);
}

void idle()
{
    if (movimientoBrazo[1] >= 45 && giro45 > 0)
    {
        giro45 = giro45 * -1.0f;
    }
    else if (movimientoBrazo[1] <= 0 && giro45 < 0)
    {
        giro45 = giro45 * -1.0f;
    }
    display();
}
void submenu_1(int id)
{
    switch (id)
    {
    case 0:
        colorR = 1.0f;
        colorG = 0.84f;
        colorB = 0.0f;
    case 1:
        colorR = 1.0f;
        colorG = 0.0f;
        colorB = 0.0f;
        break;
    case 2:
        colorR = 0.93f;
        colorG = 0.51f;
        colorB = 0.93f;
        break;
    case 3:
        colorR = 0.63f;
        colorG = 0.32f;
        colorB = 0.18f;
        break;
    }
}
void submenu_2(int id)
{
    switch (id)
    {
    case 0:
        velocidad = 200;
        break;
    case 1:
        velocidad = 100;
        break;
    case 2:
        velocidad = 50;
        break;
    }
}
void menu_1(int id)
{
    switch (id)
    {
    case 0:
        // exit(0); lo comento porque al compilar me da error en MACOS

    default:
        break;
    }
}
void menu()
{
    // submenu cambio de visualizacion
    int submenu1 = glutCreateMenu(submenu_1);
    glutAddMenuEntry("Amarillo", 0);
    glutAddMenuEntry("Rojo", 1);
    glutAddMenuEntry("Violeta", 2);
    glutAddMenuEntry("Marrón", 3);
    // submenu cambio de visualizacion
    int submenu2 = glutCreateMenu(submenu_2);
    glutAddMenuEntry("Velocidad Lenta", 0);
    glutAddMenuEntry("Velocidad Normal", 1);
    glutAddMenuEntry("Velocidad Rápida", 2);
    // menu principal
    glutCreateMenu(menu_1);
    glutAddSubMenu("Cambiar velocidad", submenu2);
    glutAddSubMenu("Cambiar color pasajeros", submenu1);
    glutAddMenuEntry("Salir", 0);
    glutAttachMenu(GLUT_RIGHT_BUTTON);
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowSize(WIDTH, HEIGTH);
    glutInitWindowPosition(ORIGIN_X, ORIGIN_Y);
    glutCreateWindow("Hola Mundo");
    menu();
    init();
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutTimerFunc(velocidad, timerRotation, 3.0f);
    glutIdleFunc(idle);
    glutKeyboardFunc(keyboard);
    glutMouseFunc(mouseMovement);
    glutMotionFunc(motion);
    glutMainLoop();
    return 0;
}

// para ejecutar: gcc noria1.c -o noria  -framework Carbon -framework OpenGL -framework GLUT