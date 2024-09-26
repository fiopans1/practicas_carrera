import numpy as np
import matplotlib.pyplot as plt
import math
from skimage import io, color
import cv2 as cv2


#$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ NORMALIZACION Y FUNCIONES DE LECTURA $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
def normalizacion_y_importar(inImage): #no se normaliza
    img = io.imread(inImage)
    if(img.ndim!=2):
        if(img.ndim==3):
            if(img.shape[2]==4):
                img= color.rgba2rgb(img)
                
        img = color.rgb2gray(img)  # esta funcion ya normaliza entre 0 y 1
    else:
        img= img/255.0
    return img
def normalizacion_min_max(inImage):
    max= np.max(inImage)
    min= np.min(inImage)
    if(min>=0 and max<=1):
        outImage=inImage
    else:
        outImage = (inImage-min) / (max-min)
    return outImage
def mostrar_y_guardar(inImage,nameSave,title="resultImage"):
    cv2.imshow(title, inImage)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    img_modificada = (inImage*255.0).astype(np.float32)
    cv2.imwrite(nameSave, img_modificada)
def mostrar(inImage,originalImage=None,title="resultImage"):
    if originalImage!=None:
        cv2.imshow('OriginalImage', originalImage)
    cv2.imshow(title, inImage)
    cv2.waitKey(0)
    cv2.destroyAllWindows()



#$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ EJERCICIO 1 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

def adjustIntensity(inImage, inRange, outRange=[0, 1]): #no se normaliza
    if inRange == []:
        inRange = [np.min(inImage), np.max(inImage)]
    gMax = inRange[0]
    gMin = inRange[1]
    gMaxNorm = outRange[0]
    gMinNorm = outRange[1]
    outImage = (gMinNorm + (((gMaxNorm - gMinNorm)
                * (inImage - gMin)) / (gMax - gMin)))
    return outImage


def equalizeIntensity(inImage, nBins=256):  # esta funcion hay que cambiarla
    histograma, _ = np.histogram(inImage * (nBins-1), bins=nBins, range=[0,nBins-1])
    op = histograma.cumsum() / histograma.sum()
    return normalizacion_min_max(op[(inImage*255).astype(int)])


def plot_images_and_histograms(original_image, modified_image, nBins=256):
    # Crear una figura con cuatro subgráficos en dos filas y dos columnas
    fig, axs = plt.subplots(3, 2, figsize=(12, 8))

    # Subgráfico 1: Mostrar la imagen original
    axs[0, 0].imshow(original_image, cmap='gray')
    axs[0, 0].set_title('Imagen Original')

    # Subgráfico 2: Mostrar la imagen modificada
    axs[0, 1].imshow(modified_image, cmap='gray')
    axs[0, 1].set_title('Imagen Modificada')

    # Subgráfico 3: Mostrar el histograma de la imagen original
    hist_original, bins_original = np.histogram(
        original_image, nBins)
    axs[1, 0].plot(hist_original, color='black')
    axs[1, 0].set_title('Histograma Original')
    axs[1, 0].set_ylim(0, 5000)

    # Subgráfico 3: Mostrar el histograma de la imagen modificada
    hist_mod, bins_mod = np.histogram(
        modified_image, nBins)
    axs[1, 1].plot(hist_mod, color='black')
    axs[1, 1].set_title('Histograma Modificada')
    axs[1, 1].set_ylim(0, 5000)

    # Subgráfico 4: Mostrar el histograma acumulado
    cdf1 = hist_original.cumsum()
    cdf_normalized1 = cdf1 / cdf1[-1]
    axs[2, 0].plot(cdf_normalized1, color='black')
    axs[2, 0].set_title('Histograma Acumulado Original')

    # Subgráfico 4: Mostrar el histograma acumulado
    cdf2 = hist_mod.cumsum()
    cdf_normalized2 = cdf2 / cdf2[-1]
    axs[2, 1].plot(cdf_normalized2, color='black')
    axs[2, 1].set_title('Histograma Acumulado Modificado')

    # Ajustar el espacio entre subgráficos
    plt.tight_layout()

    # Mostrar la figura
    plt.show()
    
    
#$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ EJERCICIO 2 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$





def extendImageDuplicate1(inImage, kernel):
    dImage = inImage.shape
    dKernel = kernel.shape
    if (kernel.ndim == 1):
        dKernel = (1, dKernel[0])
    border_x = dKernel[0]//2
    border_y = dKernel[1]//2
    pad_width = ((border_x, border_x), (border_y, border_y))
    outImage = np.pad(inImage, pad_width=pad_width, mode='edge')
    return outImage


def filterImage(inImage, kernel):
    dImage = inImage.shape
    dKernel = kernel.shape
    if (kernel.ndim == 1):
        dKernel = (1, dKernel[0])
    border_x = dKernel[0]//2
    border_y = dKernel[1]//2
    ex_image = extendImageDuplicate1(inImage, kernel)
    dExImage = ex_image.shape
    outImage = np.zeros_like(inImage)
    for x in range(border_x, dExImage[0] - border_x):
        for y in range(border_y, dExImage[1] - border_y):
            roi = ex_image[x - border_x:x +
                           border_x+1, y-border_y:y+border_y+1]
            outImage[x-border_x, y-border_y] = (roi*kernel).sum()
    return normalizacion_min_max(outImage)


def gaussKernel1D(sigma):
    N = (2 * math.ceil(3 * sigma)) + 1
    kernel = np.zeros(N)
    center = math.floor(N/2)#saque el +1
    for x in range(N):
        exponent = -(((x-center)**2) / (2*(sigma**2)))
        kernel[x] = (1 / math.sqrt(2*np.pi * (sigma))
                     ) * np.exp(exponent)
    kernel /= np.sum(kernel)
    return kernel


def gaussianFilter(inImage, sigma):
    kernel = gaussKernel1D(sigma)
    outimage = filterImage(inImage, kernel)
    outimage = filterImage(outimage.T, kernel.T)
    return outimage.T


def medianFilter(inImage, filterSize):
    border = (filterSize // 2)
    eImage = extendImageDuplicate1(inImage, np.zeros((filterSize, filterSize)))
    dExImage = eImage.shape
    outImage = np.zeros_like(inImage)
    for x in range(border, dExImage[0] - border):
        for y in range(border, dExImage[1] - border):
            matrix = eImage[x - border:x +
                            border+1, y-border:y+border+1]
            outImage[x-border, y-border] = np.median(matrix)

    return outImage


# REVISAR AQUI LO DEL CENTRO




#$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ EJERCICIO 3 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$



def extendImageDuplicate(inImage, arriba, abajo, derecha, izquierda):

    # Ancho de relleno para cada lado (arriba, abajo, izquierda, derecha)
    pad_width = ((arriba, abajo), (izquierda, derecha))

    outImage = np.pad(
        inImage, pad_width=pad_width, mode='edge')
    return outImage


def erode(inImage, SE, center=[]):
    dimsI = inImage.shape
    dimsSE = SE.shape
    # codigo comun para todas las funciones
    if (SE.ndim == 1):
        dimsSE = (1, dimsSE[0])
    if center == []:
        x = (dimsSE[0] // 2)
        y = (dimsSE[1] // 2)
    else:
        x = center[0]
        y = center[1]
    imageExt = extendImageDuplicate(
        inImage, x, dimsSE[0] - x - 1, dimsSE[1] - y - 1, y)
    dImageExt = imageExt.shape
    outImage = np.zeros_like(inImage)
    # funciona en 1D
    for i in range(x, (dImageExt[0]-(dimsSE[0]-x)+1)):
        for j in range(y, (dImageExt[1]-(dimsSE[1]-y)+1)):
            ventana = imageExt[i-x: i+(dimsSE[0]-x), j-y: j+(dimsSE[1]-y)]
            if x == 0:
                ventana = ventana.flatten()
            if (ventana[SE==1] == 1).all():
                outImage[i-x, j-y] = 1
    return outImage


def dilate(inImage, SE, center=[]):
    dimsI = inImage.shape
    dimsSE = SE.shape
    # codigo comun para todas las funciones
    if (SE.ndim == 1):
        dimsSE = (1, dimsSE[0])
    if center == []:
        x = (dimsSE[0] // 2)
        y = (dimsSE[1] // 2)
    else:
        x = center[0]
        y = center[1]
    imageExt = extendImageDuplicate(
        inImage, x, dimsSE[0] - x - 1, dimsSE[1] - y - 1, y)
    dImageExt = imageExt.shape
    outImage = np.zeros_like(inImage)
    # funciona en 1D
    for i in range(x, (dImageExt[0]-(dimsSE[0]-x)+1)):
        for j in range(y, (dImageExt[1]-(dimsSE[1]-y)+1)):
            ventana = imageExt[i-x: i+(dimsSE[0]-x), j-y: j+(dimsSE[1]-y)]
            if x == 0:
                ventana = ventana.flatten()
            if (np.any(np.logical_and(ventana, SE))):
                outImage[i-x, j-y] = 1
    return outImage



def opening(inImage, SE, center=[]):
    outImage = erode(inImage, SE, center=center)
    outImage = dilate(outImage, SE, center=center)
    return outImage


def closing(inImage, SE, center=[]):
    outImage = dilate(inImage, SE, center=center)
    outImage = erode(outImage, SE, center=center)
    return outImage


def hit_or_miss (inImage, objSE, bgSE, center=[]):
    if(np.any(np.logical_and(objSE, bgSE))):
        raise Exception("Error: elementos estructurantes incoherentes")
    complementario_inImage = np.abs(1- inImage)
    ero1 = erode(inImage, objSE, center=center)
    ero2 = erode( complementario_inImage, bgSE, center=center)
    result = cv2.bitwise_and(ero1,ero2)
    return result
    
    
def gradientImage (inImage, operator):
    if operator == 'Roberts':
        deltaX= np.array([[0,0,0],[0,-1,0],[0,0,1]]) #adaptado para 3x3
        deltaY= np.array([[0,0,0],[0,0,-1],[0,1,0]]) #adaptado para 3x3
    elif operator == 'CentralDiff':
        deltaX= np.array([-1, 0, 1])
        deltaY= np.array([[-1],[0],[1]])
    elif operator == 'Prewitt':
        deltaX= np.array([[-1,0,1],[-1,0,1],[-1,0,1]])
        deltaY= np.array([[-1,-1,-1],[0,0,0],[1,1,1]])
    elif operator == 'Sobel':
        deltaX= np.array([[-1,0,1],[-2,0,2],[-1,0,1]])
        deltaY= np.array([[-1,-2,-1],[0,0,0],[1,2,1]])
    else:
        raise Exception("Debes introducir un operador correcto")
    Gx = filterImage(inImage, deltaX)
    Gy = filterImage(inImage, deltaY)
    return Gx,Gy



#$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ AUX SIN NORMALIZAR $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
def filterImage2(inImage, kernel):
    dImage = inImage.shape
    dKernel = kernel.shape
    if (kernel.ndim == 1):
        dKernel = (1, dKernel[0])
    border_x = dKernel[0]//2
    border_y = dKernel[1]//2
    ex_image = extendImageDuplicate1(inImage, kernel)
    dExImage = ex_image.shape
    outImage = np.zeros_like(inImage)
    for x in range(border_x, dExImage[0] - border_x):
        for y in range(border_y, dExImage[1] - border_y):
            roi = ex_image[x - border_x:x +
                           border_x+1, y-border_y:y+border_y+1]
            outImage[x-border_x, y-border_y] = (roi*kernel).sum()
    return outImage
def gaussianFilter2(inImage, sigma):
    kernel = gaussKernel1D(sigma)
    outimage = filterImage2(inImage, kernel)
    outimage = filterImage2(outimage.T, kernel.T)
    return outimage.T

def gradientImage2 (inImage, operator):
    if operator == 'Roberts':
        deltaX= np.array([[0,0,0],[0,-1,0],[0,0,1]]) #adaptado para 3x3
        deltaY= np.array([[0,0,0],[0,0,-1],[0,1,0]]) #adaptado para 3x3
    elif operator == 'CentralDiff':
        deltaX= np.array([-1, 0, 1])
        deltaY= np.array([[-1],[0],[1]])
    elif operator == 'Prewitt':
        deltaX= np.array([[-1,0,1],[-1,0,1],[-1,0,1]])
        deltaY= np.array([[-1,-1,-1],[0,0,0],[1,1,1]])
    elif operator == 'Sobel':
        deltaX= np.array([[-1,0,1],[-2,0,2],[-1,0,1]])
        deltaY= np.array([[-1,-2,-1],[0,0,0],[1,2,1]])
    else:
        raise Exception("Debes introducir un operador correcto")
    Gx = filterImage2(inImage, deltaX)
    Gy = filterImage2(inImage, deltaY)
    return Gx,Gy

#$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ CANNY Y LOG $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

def LoG (inImage, sigma):
    inImageG = gaussianFilter2(inImage, sigma)
    kernelL = np.array([[0,1,0],[1,-4,1],[0,1,0]])
    imgFilter = filterImage2(inImageG, kernelL)
    t = 0.01
    outImage = np.zeros(imgFilter.shape)
    dImage = outImage.shape
    for y in range(1, dImage[0] - 1):
        for x in range(1, dImage[1] - 1):
            patch = imgFilter[y-1:y+2, x-1:x+2]
            p = imgFilter[y, x]
            maxP = patch.max()
            if (p < -t) and (maxP> t):
                outImage[y,x] = 1
                
    return normalizacion_min_max(outImage)


def edgeCanny(inImage, sigma, tlow, thigh):
    simage = gaussianFilter2(inImage, sigma)
    [Gx, Gy] = gradientImage2(simage, 'Sobel')
    magnitud = np.sqrt(Gx**2 + Gy**2)
    gradient_direction = np.arctan2(Gy, Gx) * (180 / np.pi)
    gradient_direction[gradient_direction < 0] += 180
    simage = np.zeros_like(magnitud)
    for i in range(1, magnitud.shape[0] - 1):
        for j in range(1, magnitud.shape[1] - 1):
            direction = gradient_direction[i, j]
            if ((direction >= 0 and direction<22.5) or (direction >= 157.5 and direction<=180)) and (magnitud[i, j] >= magnitud[i, j - 1]) and (magnitud[i, j] >= magnitud[i, j + 1]):
                simage[i, j] = magnitud[i, j]
            elif (direction >= 22.5 and direction<67.5) and (magnitud[i, j] >= magnitud[i - 1, j - 1]) and (magnitud[i, j] >= magnitud[i + 1, j + 1]):
                simage[i, j] = magnitud[i, j]
            elif (direction >= 67.5 and direction<112.5) and (magnitud[i, j] >= magnitud[i - 1, j]) and (magnitud[i, j] >= magnitud[i + 1, j]):
                simage[i, j] = magnitud[i, j]
            elif (direction >= 112.5 and direction<157.5) and (magnitud[i, j] >= magnitud[i - 1, j + 1]) and (magnitud[i, j] >= magnitud[i + 1, j - 1]):
                simage[i, j] = magnitud[i, j]
    bordes = np.zeros_like(simage)
    bordesF = (simage >= thigh)
    bordesD = (simage >= tlow) & (simage <= thigh)
    for i in range(1, simage.shape[0] - 1):
        for j in range(1, simage.shape[1] - 1):
            if bordesD[i, j]:
                # Verificar vecinos para conexión a bordes fuertes
                if np.any(bordesF[i - 1:i + 2, j - 1:j + 2]):
                    bordes[i, j] = 1
    bordes[bordesF] = 1
    return normalizacion_min_max(bordes.astype(np.float32))



#$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ SUSAN $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

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
    return (normalizacion_min_max(inImage2), normalizacion_min_max(inImage3))