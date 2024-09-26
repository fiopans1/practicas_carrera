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
GLfloat mouseX = 0.0f;
GLfloat mouseY = 0.0f;
int extremidad = 0;
int movi = 0;
GLfloat extremidades[4][2][3] = {{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}, {{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}, {{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}, {{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}};
bool solid = true;
bool locuraPiernas = false;
int velocidad = 2500;
int mousseButtonState = 0;
void init()
{
    glClearColor(0.0, 0.0, 0.0, 0.0);
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

    switch (mousseButtonState)
    {
    case GLUT_LEFT_BUTTON:
        extremidades[2][0][1] += deltaX;
        extremidades[2][0][2] -= deltaY;
    }
}
void timerRotation(int value)
{

    if (locuraPiernas)
    {
        extremidades[2][0][2] += 3.0f;
        extremidades[3][0][2] -= 3.0f;
    }
    glutTimerFunc(velocidad, timerRotation, value);
}
void cuerpo()
{
    glColor3f(0.5f, 0.5f, 1.0f);
    glScalef(3.0f, 5.5f, 2.0f);
    glutSolidCube(1.0);
}
void brazopierna(int ext)
{
    if (solid)
    {
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL); // Sólidos
    }
    else
    {
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // Alambre
    }
    // aplicamos la primera rotacion
    glRotatef(extremidades[ext][0][0], 1.0f, 0.0f, 0.0f);
    glRotatef(extremidades[ext][0][1], 0.0f, 1.0f, 0.0f);
    glRotatef(extremidades[ext][0][2], 0.0f, 0.0f, 1.0f);
    // dibujamos la primera esfera
    glColor3f(1.0f, 0.0f, 0.0f);
    glutSolidSphere(0.5, 40, 40);

    // dibujamos el primer cubo
    glColor3f(0.0f, 0.0f, 1.0f);
    glTranslatef(0.0f, -1.5f, 0.0f);
    glPushMatrix();
    glScalef(1.0f, 2.0f, 1.0f);
    glutSolidCube(1.0);
    glPopMatrix();
    //  dibujamos la segunda esfera
    glColor3f(1.0f, 0.0f, 0.0f);
    glTranslatef(0.0f, -1.0f, 0.0f);
    glutSolidSphere(0.5, 40, 40);
    //  aplicamos la segunda rotacion
    glRotatef(extremidades[ext][1][0], 1.0f, 0.0f, 0.0f);
    glRotatef(extremidades[ext][1][1], 0.0f, 1.0f, 0.0f);
    glRotatef(extremidades[ext][1][2], 0.0f, 0.0f, 1.0f);
    //  dibujamos el segundo cubo
    glColor3f(0.0f, 0.0f, 1.0f);
    glTranslatef(0.0f, -1.5f, 0.0f);
    glPushMatrix();
    glScalef(1.0f, 2.0f, 1.0f);
    glutSolidCube(1.0);
    glPopMatrix();
}
void submenu_1(int id)
{
    switch (id)
    {
    case 0:
        solid = true;
        break;
    case 1:
        solid = false;
        break;
    }
}
void submenu_2(int id)
{
    switch (id)
    {
    case 0:
        velocidad = 2000;
        break;
    case 1:
        velocidad = 1000;
        break;
    case 2:
        velocidad = 500;
        break;
    case 3:
        velocidad = 100;
        break;
    }
}
void menu_1(int id)
{
    switch (id)
    {
    case 0:
        locuraPiernas = !locuraPiernas;
        break;
    }
}
void menu()
{
    // submenu cambio de visualizacion
    int submenu1 = glutCreateMenu(submenu_1);
    glutAddMenuEntry("Solido", 0);
    glutAddMenuEntry("Alambre", 1);
    // submenu cambio de visualizacion
    int submenu2 = glutCreateMenu(submenu_2);
    glutAddMenuEntry("Velocidad 1", 0);
    glutAddMenuEntry("Velocidad 2", 1);
    glutAddMenuEntry("Velocidad 3", 2);
    glutAddMenuEntry("Velocidad 4", 3);
    // menu principal
    glutCreateMenu(menu_1);
    glutAddSubMenu("Cambiar visualizacion", submenu1);
    glutAddMenuEntry("Girar Piernas", 0);
    glutAddSubMenu("Velocidades de giro", submenu2);
    glutAttachMenu(GLUT_RIGHT_BUTTON);
}
void display()
{
    glClear(GL_COLOR_BUFFER_BIT);
    glLoadIdentity();
    cuerpo();
    glLoadIdentity();
    glTranslatef(2.0f, 2.25f, 0.0f);
    brazopierna(0);
    glLoadIdentity();
    glTranslatef(-2.0f, 2.25f, 0.0f);
    brazopierna(1);
    glLoadIdentity();
    glTranslatef(1.0f, -3.25f, 0.0f);
    brazopierna(2);
    glLoadIdentity();
    glTranslatef(-1.0f, -3.25f, 0.0f);
    brazopierna(3);

    glutSwapBuffers();
}

void keyboard(unsigned char key, int x, int y)
{
    switch (key)
    {
    case 'w':
    case 'W':

        extremidades[extremidad][movi][0] += 1.0f;
        break;

    case 's':
    case 'S':

        extremidades[extremidad][movi][0] -= 1.0f;
        break;
    case 'q':
    case 'Q':

        extremidades[extremidad][movi][1] += 1.0f;
        break;

    case 'a':
    case 'A':

        extremidades[extremidad][movi][1] -= 1.0f;
        break;
    case 'e':
    case 'E':

        extremidades[extremidad][movi][2] += 1.0f;
        break;

    case 'd':
    case 'D':

        extremidades[extremidad][movi][2] -= 1.0f;
        break;
    // segundo grupo
    case 'r':
    case 'R':
        if (movi == 0)
        {
            movi = 1;
        }
        else
        {
            movi = 0;
        }
    case '1':
        extremidad = 0;
        break;
    case '2':
        extremidad = 1;
        break;
    case '3':
        extremidad = 2;
        break;
    case '4':
        extremidad = 3;
        break;
    default: // escape
        // exit(0);
        break;
    }
}
void reshape(int w, int h)
{
    glViewport(0, 0, (GLsizei)w, (GLsizei)h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-10.0, 10.0, -10.0, 10.0, -10.0, 10.0);
    glMatrixMode(GL_MODELVIEW);
}
void idle()
{

    display();
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