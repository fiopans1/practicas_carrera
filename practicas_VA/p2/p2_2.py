from segment_anything import SamAutomaticMaskGenerator, sam_model_registry, SamPredictor
import cv2
import numpy as np
import supervision as sv
from pathlib import Path
sam = sam_model_registry["vit_h"](checkpoint="model.pth")
# image = cv2.imread("/res/")
def borrar_ruido(x):
    _, x2 = cv2.threshold(x, 50, 255, cv2.THRESH_BINARY)

    cont, _ = cv2.findContours(x2, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_NONE)
    
    mask = cv2.drawContours(x2.copy(), [cnt for cnt in cont if cv2.contourArea(cnt) < 1000], -1, 0, thickness=cv2.FILLED)
    
    return mask
def marcar_hojas(image):
        #mejora contraste
    bn = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    _, imagen_binaria = cv2.threshold(bn, 60, 255, cv2.THRESH_BINARY)
    clahe = cv2.createCLAHE(clipLimit=15, tileGridSize=(15,15))
    image = clahe.apply(cv2.cvtColor(image, cv2.COLOR_BGR2GRAY))
    image = cv2.cvtColor(image, cv2.COLOR_GRAY2RGB)
    #mejora contraste
    mask_generator = SamAutomaticMaskGenerator(sam)
    sam_result = mask_generator.generate(image)
    mask_annotator = sv.MaskAnnotator(color_lookup=sv.ColorLookup.INDEX)
    res = []
    for element in sam_result:
        capa = element['segmentation']
        capa = capa.astype(np.uint8) * 255
        filt = cv2.bitwise_and(imagen_binaria, imagen_binaria, mask=capa)
        if np.sum(borrar_ruido(filt)) >10000:
            res.append(element)
    sam_result = res

    detections = sv.Detections.from_sam(sam_result=sam_result)

    annotated_image = mask_annotator.annotate(scene=image.copy(), detections=detections)
    return annotated_image


ruta = Path('/Users/fiopans1/git/class-repositories/practicas_VA/res')
z=0
for elemento in ruta.iterdir():
    if elemento.is_file() and elemento.suffix == ".jpg":
        image = cv2.imread(ruta.name + "/" + elemento.name )
        finalImage = marcar_hojas(image)
        cv2.imwrite(f'final4/planta_{z + 1}.jpg', finalImage)
        z=z+1
    