#include <stdio.h>
#include <math.h>
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

GLfloat anguloEjeX = 0.0f;
GLfloat anguloEjeY = 0.0f;
GLfloat anguloEjeZ = 0.0f;
void init()
{
    glClearColor(0.0, 0.0, 0.0, 0.0);
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT);
    glLoadIdentity();

    glRotatef(anguloEjeX, 1.0f, 0.0f, 0.0f);
    glRotatef(anguloEjeY, 0.0f, 1.0f, 0.0f);
    glRotatef(anguloEjeZ, 0.0f, 0.0f, 1.0f);

    glColor3f(1.0f, 0.0f, 0.0f);
    glutSolidSphere(0.5, 40, 40);
    glColor3f(0.0f, 0.0f, 1.0f);
    glTranslatef(0.0f, -1.0f, 0.0f);
    glutSolidCube(1.0);

    glutSwapBuffers();
}
void keyboard(unsigned char key, int x, int y)
{
    switch (key)
    {
    case 'w':
    case 'W':
        anguloEjeY += 1.0;
        break;

    case 's':
    case 'S':
        anguloEjeY -= 1.0;
        break;
    case 'q':
    case 'Q':
        anguloEjeX += 1.0;
        break;

    case 'a':
    case 'A':
        anguloEjeX -= 1.0;
        break;
    case 'e':
    case 'E':
        anguloEjeZ += 1.0;
        break;

    case 'd':
    case 'D':
        anguloEjeZ -= 1.0;
        break;
    case 27: // escape
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
    init();
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutIdleFunc(idle);
    glutKeyboardFunc(keyboard);
    glutMainLoop();
    return 0;
}