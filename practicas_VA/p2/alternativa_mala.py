import cv2
import numpy as np
from matplotlib import pyplot as plt


imagen_rgb = cv2.imread('/Users/fiopans1/git/class-repositories/practicas_VA/res/planta_4.jpg')

# Convertir la imagen de RGB a BGR
imagen_bgr = cv2.cvtColor(imagen_rgb, cv2.COLOR_RGB2BGR)
filtro_bilateral = cv2.edgePreservingFilter(imagen_bgr, flags=1, sigma_s=40, sigma_r=0.3)
imagen_estilizada = cv2.stylization(filtro_bilateral, sigma_s=60, sigma_r=0.07)
image_gris = (imagen_estilizada[:,:,0]*0.114+imagen_estilizada[:,:,1]*0.587+imagen_estilizada[:,:,2]*0.299)
imagen_suavizada = cv2.GaussianBlur(image_gris, (9, 9), sigmaX=1.5, sigmaY=1.5).astype(np.uint8)
kernel = np.ones((3, 3), np.uint8)
imagen_dilatada = cv2.dilate(imagen_suavizada, kernel, iterations=1)
_, imagen_binariad = cv2.threshold(imagen_dilatada, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
imagen_erosionada = cv2.erode(imagen_suavizada, kernel, iterations=1)
_, imagen_binariae = cv2.threshold(imagen_erosionada, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
bordes = imagen_binariad -imagen_binariae
contornos, _ = cv2.findContours(bordes, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

# Filtrar contornos que se asemejan a una elipse
contornos_elipse = []
for contorno in contornos:
    elipse = cv2.fitEllipse(contorno)
    area_contorno = cv2.contourArea(contorno)
    area_elipse = np.pi * elipse[1][0] * elipse[1][1] / 4
    if 0.1 <= area_contorno / area_elipse <= 1.9:
        contornos_elipse.append(contorno)

# Dibujar los contornos de elipse encontrados en la imagen original
imagen_contornos_elipse = imagen_rgb.copy()
cv2.drawContours(imagen_contornos_elipse, contornos_elipse, -1, (0, 255, 0), 2)

# Mostrar la imagen original y la imagen con contornos de elipse dibujados
cv2.imshow('Imagen Original', imagen_rgb)
cv2.imshow('Contornos de Elipse', imagen_contornos_elipse)
cv2.waitKey(0)
cv2.destroyAllWindows()
