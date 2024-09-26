import cv2
import numpy as np
from matplotlib import pyplot as plt


def borrar_ruido(x):
    _, x2 = cv2.threshold(x, 50, 255, cv2.THRESH_BINARY)

    cont, _ = cv2.findContours(x2, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_NONE)
    
    mask = cv2.drawContours(x2.copy(), [cnt for cnt in cont if cv2.contourArea(cnt) < 1000], -1, 0, thickness=cv2.FILLED)
    
    return mask

def recortar(imagen, umbral1=100, umbral2=60, umbral3=80, tam_morph=(5,5)):
    #trabajamos con el espacio rojo
    imagen_roja = imagen[:,:,0]
    umbral = umbral1  # Ajusta este valor según tus necesidades
    _, imagen_umbralizada = cv2.threshold(imagen_roja, umbral, 255, cv2.THRESH_BINARY)
    imagen_umbralizada=borrar_ruido(imagen_umbralizada)
    imagenrec = cv2.bitwise_and(imagen, imagen, mask=imagen_umbralizada)
    imagenrec = imagen-imagenrec
    
    
    #trabajamos con el verde
    imagen_green= imagenrec[:,:,1]
    umbral = umbral2  # Ajusta este valor según tus necesidades
    _, imagen_umbralizada = cv2.threshold(imagen_green, umbral, 255, cv2.THRESH_BINARY)
    imagen_umbralizada=borrar_ruido(imagen_umbralizada)
    imagenrec = cv2.bitwise_and(imagen, imagen, mask=imagen_umbralizada)
    imagen2= imagenrec
    
    #trabajamos con el rojo
    imagen_roja = imagenrec[:,:,0]
    umbral = umbral3  # Ajusta este valor según tus necesidades
    _, imagen_umbralizada = cv2.threshold(imagen_roja, umbral, 255, cv2.THRESH_BINARY)
    imagenrec = cv2.bitwise_and(imagen, imagen, mask=imagen_umbralizada)
    
    
    
    imagen2 = imagen2-imagenrec
    imagen_bn =cv2.cvtColor(imagen2, cv2.COLOR_BGR2GRAY)
    imagen_umbralizada=borrar_ruido(imagen_bn)
    imagenrec = cv2.bitwise_and(imagen, imagen, mask=imagen_umbralizada)
    kernel = np.ones(tam_morph, np.uint8)
    imagenrec2 = cv2.morphologyEx(imagenrec, cv2.MORPH_OPEN, kernel)
    imagenrec2 = cv2.morphologyEx(imagenrec2, cv2.MORPH_CLOSE, kernel)
    
    imagen_bn =cv2.cvtColor(imagenrec2, cv2.COLOR_BGR2GRAY)
    imagen_bn =borrar_ruido(imagen_bn)
    imagenrec = cv2.bitwise_and(imagen, imagen, mask=imagen_bn)
    
    
    #pasamos al espacio hsv
    imagen_hsv = cv2.cvtColor(imagenrec, cv2.COLOR_BGR2HSV)
    imagen_x= imagen_hsv[:,:,1]
    imagenrec = cv2.bitwise_and(imagen, imagen, mask=borrar_ruido(imagen_x))
    cv2.imshow('sinruido2', imagenrec)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    return imagenrec

def separar(image):
    #SEPARAMOS LA IMAGEN EN TROZOS
    recimage = image[50:1870,150:2430,:] #eliminamos bordes innecesarios
    borderx= recimage.shape[0]//4
    bordery = recimage.shape[1]//5
    z=0
    for i in range(4):
        for j in range(5):
            img = recimage[i*borderx:borderx*(i+1),j*bordery:bordery*(j+1),:]
            # cv2.imshow('sinruido2', img)
            # cv2.waitKey(0)
            # cv2.destroyAllWindows()
            cv2.imwrite(f'res/planta_{z + 1}.jpg', img)
            z=z+1  
#RECORTAMOS LA IMAGEN   
imagen = cv2.imread('/Users/fiopans1/git/class-repositories/practicas_VA/p2/imgs/PSI_Tray031_2016-01-13--19-48-28_top.png')
imagenrec=recortar(imagen)
cv2.imwrite('imagenrec.jpg', imagenrec)
separar(imagenrec)
#cv2.imwrite('imagenrec.jpg', imagenrec)
