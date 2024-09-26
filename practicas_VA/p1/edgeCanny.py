import cv2
import numpy as np
import math
import p1 as p1


# Cargar una imagen de ejemplo
#inImage = cv2.imread("imgs/morph.png", cv2.IMREAD_GRAYSCALE)/255



objSE =  np.array([[1, 0, 0], 
                    [0, 1, 0],
                    [0, 0, 0]], dtype=np.uint8)

bgSE = np.array([[0, 1, 0],
                    [0, 0, 1],
                    [0, 0, 0]], dtype=np.uint8)

inImage = p1.normalizacion_y_importar("imgs/circles.png")


#img1=p1.edgeCanny(inImage,0.3,0.01,0.3)
#img1=p1.edgeCanny(inImage,0.3,0.1,0.9)
img1=p1.edgeCanny(inImage,0.7,0,0.9)
#ero1 = p1.erode(inImage,bgSE)
#p1.mostrar_y_guardar(ero1,"resultado/ero.png")
#img1 = p1.hit_or_miss(((inImage-np.min(inImage))/np.max(inImage)),objSE,bgSE)

p1.mostrar_y_guardar(img1,"resultado/canny4.png")
#p1.plot_images_and_histograms(inImage,img1)
#calcula mal la perpendicular