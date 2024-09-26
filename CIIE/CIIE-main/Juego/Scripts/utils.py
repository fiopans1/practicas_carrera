import pygame as pg
import os
import json
import sys


IMG_PATH = 'Imagenes/'


def load_image(path, size):
    img = pg.image.load(IMG_PATH + path).convert()
    img.set_colorkey((0, 0, 0))

    img = pg.transform.scale(img, size)
    return img


def load_images(path, size):
    images = []
    for img_name in sorted(os.listdir(IMG_PATH + path)):
        images.append(load_image(path + '/' + img_name, size))

    return images


class Animation:
    def __init__(self, images, img_dur=5, loop=True):
        self.images = images
        self.loop = loop
        self.img_duration = img_dur
        self.done = False
        self.frame = 0

    def copy(self):
        return Animation(self.images, self.img_duration, self.loop)

    def update(self):
        if self.loop:
            self.frame = (self.frame + 1) % (self.img_duration * len(self.images))
        else:
            self.frame = min(self.frame + 1, self.img_duration * len(self.images) - 1)
            if self.frame >= self.img_duration * len(self.images) - 1:
                self.done = True

    def img(self):
        return self.images[int(self.frame / self.img_duration)]


class Button:
    def __init__(self, image, x_pos, y_pos,screen) :
        self.screen = screen
        self.image = image
        self.x_pos = x_pos
        self.y_pos = y_pos
        self.rect = self.image.get_rect(center = (self.x_pos,self.y_pos))
    
    def update(self):
        self.screen.blit(self.image,self.rect)


    def checkForInput(self, position ):
        if self.rect.collidepoint(position):
            return True

class Volume:
    def __init__(self, x_pos, y_pos, size_x, screen, volume):
        self.screen = screen
        self.x_pos = x_pos
        self.y_pos = y_pos
        self.size_x = size_x
        self.volume = volume
        self.line = pg.Rect(x_pos, y_pos, size_x, 30)
        self.button = pg.Rect(x_pos+(volume*size_x)-10, y_pos-10, 20, 50)
        pg.draw.rect(self.screen, (100, 100, 255), self.line)

    def update(self):
        self.button = pg.Rect(self.x_pos+(self.volume*self.size_x)-10, self.y_pos-10, 20, 50)
        pg.draw.rect(self.screen, (50, 50, 255), self.button)
        line2 = pg.Rect(self.line.x, self.line.y, self.size_x*self.volume, 30)
        pg.draw.rect(self.screen, (50, 50, 255), line2)

    def checkForInput(self, position, flag):
        if self.line.collidepoint(position) or flag:
            if position[0] > self.x_pos + self.size_x:
                xpos = self.x_pos + self.size_x
            elif position[0] < self.x_pos:
                xpos = self.x_pos
            else:
                xpos = position[0]
            self.volume = (xpos - self.x_pos)/self.size_x
        return self.volume



    



class Dialogue:
    def __init__(self,screen,typeofFont='freesansbold.ttf',sizeFont=24):
        self.screen =screen
        self.font = pg.font.Font(typeofFont,sizeFont)
        
        
    def load_dialogue(self,path):  
        messages = []
        with open(path) as file:
            contents = json.load(file)
            for content in contents:
                messages.append(content.get('frase'))
        return messages

    def load_image(self,path, size):
        img = pg.image.load(path).convert()
        img.set_colorkey((0, 0, 0))

        img = pg.transform.scale(img, size)
        return img                        

    def second_dialogue_layer(self,enterPulsado,game,sizeScreen=(1280,720),speed=3):
        done=False
        finish=False
        ancho=(sizeScreen[0]//5)*3
        posx= (sizeScreen[0]//5)
        posy=0
        alto=(sizeScreen[1]//5)
        # ancho = 1280
        # posx = 0
        # posy = 0
        alto=(sizeScreen[1]//5)
        message = game.messages[game.actual_message]
                
                
        if game.counter < speed * len(message):
            game.counter +=1
        elif game.counter >= speed * len(message):
            done=True       


        if enterPulsado and done and game.actual_message < len(game.messages): #parte que tenemos que sacar
            game.actual_message+=1
            if(game.actual_message<len(game.messages)):
                done = False
                game.counter = 0
                message = game.messages[game.actual_message] 
        if enterPulsado and done and game.actual_message >=len(game.messages):
            finish=True            
        rect = pg.Rect(posx,posy,ancho,alto)
        pg.draw.rect(self.screen,'white',rect) 
        if enterPulsado and not done and game.counter!=0:
            text = message
            game.counter = speed * len(message)
        else:
            text= message[0:game.counter//speed]          
        lines = text.splitlines()
        i = 0
        for line in lines:        
            snip = self.font.render(line, True, 'black')
            self.screen.blit(snip,(posx+10,(10+(30*i))))
            i+=1    
        return finish        
    
    def first_dialogue_layer(self,enterPulsado,game,imgCoordinates=(100,100),speed=3):
        done=False
        finish=False
        message = game.messages[game.actual_message]
        if game.counter < speed * len(message):
            game.counter +=1
        elif game.counter >= speed * len(message):
            done=True      
        if enterPulsado and done and game.actual_message < len(game.messages):
            game.actual_message+=1
            if(game.actual_message<len(game.messages)):
                done = False
                game.counter = 0
                message = game.messages[game.actual_message] #tenemos que hacer que cuando llegue al limite de la pantalla que se dibuje abajo
        if enterPulsado and done and game.actual_message >=len(game.messages):
            finish=True
        self.screen.fill('white')
        if enterPulsado and not done and game.counter!=0:
            text = message
            game.counter = speed * len(message)
        else:
            text= message[0:game.counter//speed]         
        lines = text.splitlines()
        i = 0
        for line in lines:        
            snip = self.font.render(line, True, 'black')
            self.screen.blit(snip,(660,(10+(30*i))))
            i+=1
        self.screen.blit(game.img,imgCoordinates)     
        return finish    

    def third_dialogue_layer(self,game,enterPulsado,sizeScreen=(1280,720),speed=3):
        done=False
        finish=False
        ancho=(sizeScreen[0]//8)*6
        posx= (sizeScreen[0]//8)
        posy=0
        alto=(sizeScreen[1]//5)
        # ancho = 1280
        # posx = 0
        # posy = 0
        alto=(sizeScreen[1]//5)
        message = game.messages[game.actual_message]
                
                
        if game.counter < speed * len(message):
            game.counter +=1
        elif game.counter >= speed * len(message):
            done=True       


        if enterPulsado and done and game.actual_message < len(game.messages): #parte que tenemos que sacar
            game.actual_message+=1
            if(game.actual_message<len(game.messages)):
                done = False
                game.counter = 0
                message = game.messages[game.actual_message] 
        if enterPulsado and done and game.actual_message >=len(game.messages):
            finish=True            
        rect = pg.Rect(posx,posy,ancho,alto)
        pg.draw.rect(self.screen,'white',rect)
        if enterPulsado and not done and game.counter!=0:
            text = message
            game.counter = speed * len(message)
        else:
            text= message[0:game.counter//speed]         
        lines = text.splitlines()
        i = 0
        for line in lines:        
            snip = self.font.render(line, True, 'black')
            self.screen.blit(snip,(posx+150,(10+(30*i))))
            i+=1
        img2 = pg.transform.scale(game.img,(70,70))
        self.screen.blit(img2,(posx+30,posy+30)) 
        return finish        
#arreglar velocidad
