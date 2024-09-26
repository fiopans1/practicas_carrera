#include <GL/glut.h>
#include <stdio.h>

bool altPresionada = false;
int previousX, previousY;
float rotationX = 0.0;
float rotationY = 0.0;
float sensitivity = 0.5; // Ajusta la sensibilidad según tus necesidades

void keyboard(unsigned char key, int x, int y)
{
    if (key == 27)
    { // 27 es el código ASCII para la tecla "Esc"
        exit(0);
    }
}

void special(int key, int x, int y)
{
    if (key == GLUT_KEY_ALT_L || key == GLUT_KEY_ALT_R)
    {
        altPresionada = true;
    }
}

void specialUp(int key, int x, int y)
{
    if (key == GLUT_KEY_ALT_L || key == GLUT_KEY_ALT_R)
    {
        altPresionada = false;
    }
}

void motion(int x, int y)
{
    int deltaX = x - previousX;
    int deltaY = y - previousY;

    if (altPresionada)
    {
        // Rotación en el eje X cuando Alt está presionada
        rotationX += deltaY * sensitivity;
    }
    else
    {
        // Rotación en el eje Y cuando Alt no está presionada
        rotationY += deltaX * sensitivity;
    }

    previousX = x;
    previousY = y;
    glutPostRedisplay();
}

void display()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();

    glRotatef(rotationX, 1.0, 0.0, 0.0); // Rotación en el eje X
    glRotatef(rotationY, 0.0, 1.0, 0.0); // Rotación en el eje Y

    // Tu código de dibujo aquí

    glutSwapBuffers();
}

int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutInitWindowSize(800, 600);
    glutCreateWindow("Rotación de Eje X/Y con Tecla Alt");

    glutKeyboardFunc(keyboard);
    glutSpecialFunc(special);
    glutSpecialUpFunc(specialUp);
    glutMotionFunc(motion);
    glutDisplayFunc(display);

    glutMainLoop();

    return 0;
}
