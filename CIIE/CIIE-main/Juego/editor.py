import sys

import pygame

from Scripts.utils import *
from Scripts.tilemap import Tilemap

RENDER_SCALE = 1.0

class Editor:
    def __init__(self):
        pygame.init()

        size = 1280, 720

        pygame.display.set_caption('editor')
        self.screen = pygame.display.set_mode(size)
        self.display = pygame.Surface(size)

        self.clock = pygame.time.Clock()

        tile_size = 40

        self.assets = {
            'laboratorio': load_images('Lab', (tile_size, tile_size)),
            'caja': self.load_cajas(tile_size),
            'hazards': load_images('hazards', (tile_size, tile_size)),


            'Desert': load_images('Desierto/Desert', (tile_size, tile_size)),
            'Desert_Temle': load_images('Desierto/Desert_Temle', (tile_size, tile_size)),
            'Decoracion_Temple': load_images('Desierto/Decoracion_Temple', (tile_size, tile_size)),
            'Desert_Temple_Doors': load_images('Desierto/Desert_Temple_Doors', (tile_size, tile_size)),

            'Maquna_tiempo' : load_images('Maquina_tiempo',(tile_size*3, tile_size*3)),
            'Tp' : load_images('Tp',(tile_size*1.5, tile_size*1.5)),
            'Botones': load_images('Botones', (tile_size, tile_size)),

            'Puerta':load_images('Puerta',(tile_size*0.25, tile_size*3)),
            'AN': load_images('AN_Final', (tile_size*5, tile_size*5)),

            'Jungla_base' : load_images('Jungla/Jl_in',(tile_size, tile_size)),
            'Jungla_dentro' : load_images('Jungla/Jl_out',(tile_size, tile_size)),


            'CheckPoint' : load_images('CheckPoint',(tile_size, tile_size)),

            'Evento': load_images('Event/Event_editor', (tile_size, tile_size)),

            'Reloj': load_images('Event/Reloj', (tile_size, tile_size)),



        }
        
        self.movement = [False, False, False, False]
        
        self.tilemap = Tilemap(self, tile_size=tile_size)
        
        try:
            self.tilemap.load('Mapas/Jungla.json')
        except FileNotFoundError:
            pass
        
        self.scroll = [0, 0]
        
        self.tile_list = list(self.assets)
        self.tile_group = 0
        self.tile_variant = 0
        
        self.clicking = False
        self.right_clicking = False
        self.shift = False
        self.ongrid = True

    def load_cajas(self, tile_size):
        cajas = load_images('caja/idle', (tile_size , tile_size))                       #40x40 id:0
        cajas.extend(load_images('caja/idle', (tile_size * 1.5, tile_size * 1.5)))     #60x60 id:1
        cajas.extend(load_images('caja/idle', (tile_size * 2, tile_size * 2)))         #80x80 id:2

        return cajas

    def run(self):
        while True:
            self.display.fill((255, 255, 255))
            
            self.scroll[0] += (self.movement[1] - self.movement[0]) * 5
            self.scroll[1] += (self.movement[3] - self.movement[2]) * 5
            render_scroll = (int(self.scroll[0]), int(self.scroll[1]))
            
            self.tilemap.render(self.display, offset=render_scroll)
            
            current_tile_img = self.assets[self.tile_list[self.tile_group]][self.tile_variant].copy()
            current_tile_img.set_alpha(100)
            
            mpos = pygame.mouse.get_pos()
            mpos = (mpos[0] / RENDER_SCALE, mpos[1] / RENDER_SCALE)
            tile_pos = (int((mpos[0] + self.scroll[0]) // self.tilemap.tile_size), int((mpos[1] + self.scroll[1]) // self.tilemap.tile_size))
            
            if self.ongrid:
                self.display.blit(current_tile_img, (tile_pos[0] * self.tilemap.tile_size - self.scroll[0], tile_pos[1] * self.tilemap.tile_size - self.scroll[1]))
            else:
                self.display.blit(current_tile_img, mpos)
            
            if self.clicking and self.ongrid:
                self.tilemap.tilemap[str(tile_pos[0]) + ';' + str(tile_pos[1])] = {'type': self.tile_list[self.tile_group], 'variant': self.tile_variant, 'pos': tile_pos}
            if self.right_clicking:
                tile_loc = str(tile_pos[0]) + ';' + str(tile_pos[1])
                if tile_loc in self.tilemap.tilemap:
                    del self.tilemap.tilemap[tile_loc]
                for tile in self.tilemap.offgrid_tiles.copy():
                    tile_img = self.assets[tile['type']][tile['variant']]
                    tile_r = pygame.Rect(tile['pos'][0] - self.scroll[0], tile['pos'][1] - self.scroll[1], tile_img.get_width(), tile_img.get_height())
                    if tile_r.collidepoint(mpos):
                        self.tilemap.offgrid_tiles.remove(tile)
            
            self.display.blit(current_tile_img, (5, 5))
            
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    sys.exit()
                    
                if event.type == pygame.MOUSEBUTTONDOWN:
                    if event.button == 1:
                        self.clicking = True
                        if not self.ongrid:
                            self.tilemap.offgrid_tiles.append({'type': self.tile_list[self.tile_group], 'variant': self.tile_variant, 'pos': (mpos[0] + self.scroll[0], mpos[1] + self.scroll[1])})
                    if event.button == 3:
                        self.right_clicking = True
                    if self.shift:
                        if event.button == 4:
                            self.tile_variant = (self.tile_variant - 1) % len(self.assets[self.tile_list[self.tile_group]])
                        if event.button == 5:
                            self.tile_variant = (self.tile_variant + 1) % len(self.assets[self.tile_list[self.tile_group]])
                    else:
                        if event.button == 4:
                            self.tile_group = (self.tile_group - 1) % len(self.tile_list)
                            self.tile_variant = 0
                        if event.button == 5:
                            self.tile_group = (self.tile_group + 1) % len(self.tile_list)
                            self.tile_variant = 0
                if event.type == pygame.MOUSEBUTTONUP:
                    if event.button == 1:
                        self.clicking = False
                    if event.button == 3:
                        self.right_clicking = False
                        
                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_a:
                        self.movement[0] = True
                    if event.key == pygame.K_d:
                        self.movement[1] = True
                    if event.key == pygame.K_w:
                        self.movement[2] = True
                    if event.key == pygame.K_s:
                        self.movement[3] = True
                    if event.key == pygame.K_g:
                        self.ongrid = not self.ongrid
                    if event.key == pygame.K_t:
                        self.tilemap.autotile()
                    if event.key == pygame.K_o:
                        self.tilemap.save('Mapas/Jungla.json')
                    if event.key == pygame.K_LSHIFT:
                        self.shift = True
                if event.type == pygame.KEYUP:
                    if event.key == pygame.K_a:
                        self.movement[0] = False
                    if event.key == pygame.K_d:
                        self.movement[1] = False
                    if event.key == pygame.K_w:
                        self.movement[2] = False
                    if event.key == pygame.K_s:
                        self.movement[3] = False
                    if event.key == pygame.K_LSHIFT:
                        self.shift = False
            
            self.screen.blit(pygame.transform.scale(self.display, self.screen.get_size()), (0, 0))
            pygame.display.update()
            self.clock.tick(60)

Editor().run()