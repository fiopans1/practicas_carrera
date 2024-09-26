import numpy as np
import cv2 as cv2
import matplotlib.pyplot as plt
import math
import matplotlib.cm as cm


# function to calculate gaussian
def gaussian(sigma,x):
    a= 1/(np.sqrt(2*np.pi)*sigma)
    b=math.exp(-(x**2)/(2*(sigma**2)))

    c = a*b
    return a*b

# kernel [-1,0,1]
def gaussian_kernel(sigma):
    a=gaussian(sigma, -1)
    b=gaussian(sigma, 0)
    c=gaussian(sigma, 1)
    sum=a+b+c
    if sum!=0:
        a=a/sum
        b=b/sum
        c=c/sum
    return np.reshape(np.asarray([a,b,c]), (1,3))

#susam mask of 37 pixels
def susan_mask():
    mask=np.ones((7,7))
    mask[0,0]=0
    mask[0,1]=0
    mask[0,5]=0
    mask[0,6]=0
    mask[1,0]=0
    mask[1,6]=0
    mask[5,0]=0
    mask[5,6]=0
    mask[6,0]=0
    mask[6,1]=0
    mask[6,5]=0
    mask[6,6]=0
    return mask

def create10by10Mask():
    arr= np.array([[255,0,0,0,0,0,0,0,0,0],[255,255,0,0,0,0,0,0,0,0],[255,255,255,0,0,0,0,0,0,0],[255,255,255,255,0,0,0,0,0,0],[255,255,255,255,255,0,0,0,0,0],[255,255,255,255,255,0,0,0,0,0],[255,255,255,255,0,0,0,0,0,0],[255,255,255,0,0,0,0,0,0,0],[255,255,0,0,0,0,0,0,0,0],[255,0,0,0,0,0,0,0,0,0]])
    return arr
def denoising_img(image):
    output =image.copy()
    for i in range(1,image.shape[0]-1):
        for j in range(1,image.shape[1]-1):
            output[i,j]=np.median([output[i-1][j],output[i+1][j],output[i][j-1],output[i][j+1],output[i-1][j-1],output[i+1][j+1],output[i+1][j-1],output[i-1][j+1]])

    return output

def smoothing(image):
    G=gaussian_kernel(0.5)
    I=cv2.filter2D(image,-1,G+np.transpose(G))

    return I

def normalization(image):
    output=image.copy()
    output=output*(np.max(output)-np.min(output))/255
    return output

def plot_image(image,title):
	plt.figure()

	plt.title(title)
	plt.imshow(image,cmap = 'gray')

	plt.show()
def plot_multipleImage(img1,title1,img2,title2,img3,title3,img4,title4):
    plt.subplot(221)
    plt.imshow(img1, cmap=cm.gray)
    plt.title(title1)

    plt.subplot(222)
    plt.imshow(img2, cmap=cm.gray)
    plt.title(title2)

    plt.subplot(223)
    plt.imshow(img3, cmap=cm.gray)
    plt.title(title3)

    plt.subplot(224)
    plt.imshow(img4, cmap=cm.gray)
    plt.title(title4)
    plt.show()




def susan_corner_detection(img):
    img = img.astype(np.float64)
    g=37/2
    circularMask=susan_mask()
    #print circularMask
    # img=create10by10Mask()
    # print(img)
    output=np.zeros(img.shape)
    #val=np.ones((7,7))

    for i in range(3,img.shape[0]-3):
        for j in range(3,img.shape[1]-3):
            ir=np.array(img[i-3:i+4, j-3:j+4])
            ir =  ir[circularMask==1]
            ir0 = img[i,j]
            a=np.sum(np.exp(-((ir-ir0)/10)**6))
            if a<=g:
                a=g-a
            else:
                a=0
            output[i,j]=a
    return output

image = cv2.imread('fotos_manu/susan.jpg', cv2.IMREAD_GRAYSCALE)

# Especifica el radio y umbral  
radio_mascara = 10
umbral_diferencia = 10

# Aplica el detector de esquinas SUSAN

area_usan = susan_corner_detection(image)

# Muestra las imágenes de salida
# cv2.imshow('Original', image)
#cv2.imshow('Esquinas', esquinas)
cv2.imshow('Area USAN', area_usan)
cv2.waitKey(0)
cv2.destroyAllWindows()