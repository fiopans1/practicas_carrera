import cv2
import numpy as np



def cornerSusan (inImage, r, t):
    
    dimsI = inImage.shape
    inImage2= np.zeros_like(inImage)
    inImage3= np.zeros_like(inImage)
    g = 3/4 * (((r*2+1)^2))
    for i in range(r,dimsI[0]-r):
        for j in range(r,dimsI[1]-r):
            matrix = inImage[i-r:i+r+1, j-r: j+r+1]
            c0 = inImage[i,j]
            diff = np.abs(matrix - c0)
            n = np.sum(diff<=t)
            if n<g:
                inImage2[i,j] = 255
            inImage3[i,j] = g-n
    return (inImage2, inImage3)

# Ejemplo de uso
# Lee la imagen de entrada
image = cv2.imread('fotos_manu/susan.jpg', cv2.IMREAD_GRAYSCALE)

# Especifica el radio y umbral  
radio_mascara = 4
umbral_diferencia = 10

# Aplica el detector de esquinas SUSAN
esquinas, area_usan = cornerSusan(image, radio_mascara, umbral_diferencia)

# Muestra las imágenes de salida
# cv2.imshow('Original', image)
cv2.imshow('Esquinas', esquinas)
cv2.imshow('Area USAN', area_usan)
cv2.waitKey(0)
cv2.destroyAllWindows()



#revisar normalizacion entre 0 y 1 y que cuando se importa la imagen se importe entre 0 y 1 normalizada
#crear archivo que guarde y todo
            
    
    
    
                    
                        