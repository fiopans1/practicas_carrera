import cv2
import numpy as np
from matplotlib import pyplot as plt



pathGT =  '/Users/fiopans1/git/class-repositories/practicas_VA/p2/imgs/PSI_Tray031_2015-12-27--09-03-57_top_gt.png'
pathPI = '/Users/fiopans1/git/class-repositories/practicas_VA/imagenrec.jpg'
imageGT = cv2.imread(pathGT)
imagePI = cv2.imread(pathPI)
bnGT = cv2.cvtColor(imageGT, cv2.COLOR_BGR2GRAY)
bnPI = cv2.cvtColor(imagePI, cv2.COLOR_BGR2GRAY)
_, imagen_binariaGT = cv2.threshold(bnGT, 1, 255, cv2.THRESH_BINARY)
_, imagen_binariaPI = cv2.threshold(bnPI, 30, 255, cv2.THRESH_BINARY)
andLogico = cv2.bitwise_and(imagen_binariaGT, imagen_binariaPI)
cv2.imshow('andLogico', andLogico)
cv2.imshow('imagen_binariaGT', imagen_binariaGT)
cv2.imshow('imagen_binariaPI', imagen_binariaPI)
cv2.waitKey(0)
cv2.destroyAllWindows()
pixelesEnComun = np.sum(andLogico / 255)
pixelesGT = np.sum(imagen_binariaGT / 255)
pixelesPI = np.sum(imagen_binariaPI / 255)
print('Pixeles en comun(VP):',pixelesEnComun)
print('Falsos positivos:',pixelesPI-pixelesEnComun)
print('Falsos negativos:',pixelesGT-pixelesEnComun)
print('Verdaderos negativos(no cuenta)')