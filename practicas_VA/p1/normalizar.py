import cv2
import numpy as np


def normalize_grayscale_image(image):
    if image.dtype != np.float32:
        # La imagen no está normalizada, normalízala
        normalized_image = image.astype(np.float32) / 255.0
        return normalized_image
    else:
        # La imagen ya está normalizada, no es necesario hacer nada
        return image


# Carga una imagen en niveles de grises
image = cv2.imread('tu_imagen.jpg', cv2.IMREAD_GRAYSCALE)

# Normaliza la imagen
normalized_image = normalize_grayscale_image(image)

# Ahora, 'normalized_image' contiene la imagen normalizada en el rango [0, 1]


def normalize_grayscale_image(image, min_value=0, max_value=255):
    if image.dtype != np.uint8:
        # La imagen no está normalizada, normalízala
        min_pixel_value = np.min(image)
        max_pixel_value = np.max(image)

        # Evitar la división por cero
        if max_pixel_value == min_pixel_value:
            normalized_image = np.zeros_like(image)
        else:
            normalized_image = ((image - min_pixel_value) / (max_pixel_value -
                                min_pixel_value)) * (max_value - min_value) + min_value
            normalized_image = normalized_image.astype(np.uint8)

        return normalized_image
    else:
        # La imagen ya está normalizada, no es necesario hacer nada
        return image


# Carga una imagen en niveles de grises
image = cv2.imread('tu_imagen.jpg', cv2.IMREAD_GRAYSCALE)

# Normaliza la imagen
normalized_image = normalize_grayscale_image(image, min_value=0, max_value=255)

# Ahora, 'normalized_image' contiene la imagen normalizada
