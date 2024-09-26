
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

void drawCircle(float radius, int segments)
{
    glBegin(GL_POLYGON);
    for (int i = 0; i < segments; i++)
    {
        float theta = 2.0 * M_PI * i / segments;
        float x = radius * cos(theta);
        float y = radius * sin(theta);
        glVertex2f(x, y);
    }
    glEnd();
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-5, 5, -5, 5, -10, 10); // Configura la proyección ortográfica
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

    // Posiciona la cámara en la ubicación deseada sin gluLookAt
    glRotatef(45, 1, 0, 0); // Gira la vista
    glTranslatef(0, 0, -5); // Traslada la vista

    glColor3f(0.3, 0.6, 0.9);

    GLUquadric *quad = gluNewQuadric();
    gluQuadricNormals(quad, GLU_SMOOTH);

    // Dibuja el cilindro con relleno
    glPushMatrix();
    glTranslatef(0.0, 0.0, 0.0);              // Ajusta la posición del cilindro
    gluCylinder(quad, 1.0, 1.0, 2.0, 20, 20); // Dibuja un cilindro
    glPopMatrix();

    // Remarca los bordes del cilindro
    glLineWidth(2.0);                          // Grosor de las líneas
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // Cambia a modo de líneas
    glColor3f(0.0, 0.0, 0.0);                  // Color para los bordes

    glPushMatrix();
    glTranslatef(0.0, 0.0, 0.0);              // Ajusta la posición del cilindro
    gluCylinder(quad, 1.0, 1.0, 2.0, 20, 20); // Dibuja el cilindro con líneas para remarcar los bordes
    glPopMatrix();

    // Dibuja círculos en las bases para remarcarlas
    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL); // Restaura el modo de representación a "relleno"
    glColor3f(0.0, 0.0, 0.0);                  // Color para los círculos
    glPushMatrix();
    glTranslatef(0.0, 0.0, 0.0); // Ajusta la posición del círculo de la base
    drawCircle(1.0, 20);         // Dibuja el círculo en la base inferior
    glPopMatrix();

    glPushMatrix();
    glTranslatef(0.0, 0.0, 2.0); // Ajusta la posición del círculo de la base
    drawCircle(1.0, 20);         // Dibuja el círculo en la base superior
    glPopMatrix();

    // Restaura el grosor de las líneas
    glLineWidth(1.0);

    gluDeleteQuadric(quad);

    glutSwapBuffers();
}

void reshape(int width, int height)
{
    glViewport(0, 0, width, height);
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(800, 600);
    glutCreateWindow("Cilindro con Círculos en las Bases");

    glutDisplayFunc(display);
    glutReshapeFunc(reshape);

    glEnable(GL_DEPTH_TEST);

    glutMainLoop();

    return 0;
}
