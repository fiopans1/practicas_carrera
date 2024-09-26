import pygame as pg


class Entity:
    def __init__(self, game, e_type, pos, size):
        self.game = game
        self.type = e_type
        self.pos = list(pos)
        self.size = size
        self.velocity = [0, 0]
        self.collisions = {'up': False, 'down': False, 'right': False, 'left': False}
        self.pushing = False

        self.action = ''
        self.anim_offset = (0, 0)
        self.flip = False
        self.set_action('idle')


    def rect(self):
        return pg.Rect(self.pos[0], self.pos[1], self.size[0], self.size[1])

    def set_action(self, action):
        if action != self.action:
            self.action = action
            self.animation = self.game.assets[self.type + '/' + self.action].copy()

    def update(self, tilemap, deltaT, movement=(0, 0)):
        self.collisions = {'up': False, 'down': False, 'right': False, 'left': False}

        frame_movement = (self.velocity[0] * deltaT,  + self.velocity[1] * deltaT)

        self.pos[0] += frame_movement[0]
        entity_rect = self.rect()

        #calculo de colisiones con paredes suelos y plataformas, eje x
        for type, rect in tilemap.physics_rects_around(self.pos):
            if entity_rect.colliderect(rect):
                if frame_movement[0] > 0:
                    entity_rect.right = rect.left
                    self.collisions['right'] = True
                if frame_movement[0] < 0:
                    entity_rect.left = rect.right
                    self.collisions['left'] = True
                self.pos[0] = entity_rect.x

        #calculo de colisiones con tiles de accion
        for tile in tilemap.tiles_around(self.pos):
            auxrect = pg.Rect(tile['pos'][0] * tilemap.tile_size, tile['pos'][1] * tilemap.tile_size, tilemap.tile_size,
                              tilemap.tile_size)
            #el jugador toca el tp
            if tile['type'] == "Tp":
                if entity_rect.colliderect(auxrect) and self.game.enterPulsado:
                    self.change_teleport(tile)
            #el jugador toca un tile que lo mata
            if tile['type'] == "hazards":
                if entity_rect.colliderect(auxrect):
                    self.death()
                    break
            #el jugador toca un boton 
            if tile['type'] == "Botones":
                if tile['variant'] == 0 or tile['variant'] == 2 or tile['variant'] == 4:
                    if entity_rect.colliderect(auxrect) and self.game.enterPulsado:
                        self.press_button(tile)
            #el jugador toca una puerta
            if tile['type'] == "Puerta":
                #si esta cerrada colisiona con ella
                if not self.game.open:
                    auxrect = pg.Rect(tile['pos'][0] * tilemap.tile_size, tile['pos'][1] * tilemap.tile_size, tilemap.tile_size*0.25, tilemap.tile_size*3)
                    if entity_rect.colliderect(auxrect):
                            if frame_movement[0] > 0:
                                entity_rect.right =auxrect.left
                                self.collisions['right'] = True
                            if frame_movement[0] < 0:
                                entity_rect.left = auxrect.right
                                self.collisions['left'] = True
                            self.pos[0] = entity_rect.x
                #si esta abierta permite atravesarla 
                else:
                    self.door(tile)
            #cambia el punto de aparicion del personaje al del checkpoint
            if tile['type'] == "CheckPoint":
                if tile['variant'] == 0:
                    if entity_rect.colliderect(auxrect):
                        self.game.checkpoint_dir = self.game.tp_dir
                        self.checkpoint(auxrect, tile)
            #El jugador activa un evento
            if tile['type'] == "Evento":
                if entity_rect.colliderect(auxrect):
                    self.activate_event(tile['variant'])

            # El jugador toca un portal
            if tile['type'] == "AN":
                    self.tocar_portal()


        self.pushing=False
        #calcula las colisiones de las cajas, eje x
        for caja in self.game.cajas:
            if self != caja:
                caja_rect = caja.rect()
                if entity_rect.colliderect(caja_rect):
                    if frame_movement[0] > 0:
                        entity_rect.right = caja_rect.left
                        self.collisions['right'] = True
                        self.pushing=True
                    if frame_movement[0] < 0:
                        entity_rect.left = caja_rect.right
                        self.collisions['left'] = True
                        self.pushing = True
                    caja.velocity[0] += self.velocity[0] * self.size[0]/caja.size[0] * 0.1
                    self.pos[0] = entity_rect.x

        self.pos[1] += frame_movement[1]
        entity_rect = self.rect()

        #calculo de colisiones con paredes suelos y plataformas, eje y
        for type, rect in tilemap.physics_rects_around(self.pos):
            if entity_rect.colliderect(rect):
                if frame_movement[1] > 0:
                    entity_rect.bottom = rect.top
                    self.collisions['down'] = True
                if frame_movement[1] < 0:
                    entity_rect.top = rect.bottom
                    self.collisions['up'] = True
                self.pos[1] = entity_rect.y
        #calcula las colisiones de las cajas, eje y
        for caja in self.game.cajas:
            if self != caja:
                caja_rect = caja.rect()
                if entity_rect.colliderect(caja_rect):
                    if frame_movement[1] > 0:
                        entity_rect.bottom = caja_rect.top
                        self.collisions['down'] = True
                        self.pos[0] += caja.velocity[0] *deltaT
                    self.pos[1] = entity_rect.y


        if movement[0] > 0:
            self.flip = False
        if movement[0] < 0:
            self.flip = True

        self.velocity[1] = min(7, self.velocity[1] + 0.2) #Gravedad

        if self.collisions['down'] or self.collisions['up']:
            self.velocity[1] = 0


        self.animation.update()

    def change_teleport(self, tile):
        ()

    def death(self):
        ()

    def press_button(self, tile):
        ()

    def door(self, tile):
        ()

    def checkpoint(self, auxrect, tile):
        ()

    def activate_event(self,id):
        ()

    def tocar_portal(self):
        ()


    def render(self, surf, offset=(0, 0)):
        surf.blit(pg.transform.flip(self.animation.img(), self.flip, False), (self.pos[0] - offset[0] + self.anim_offset[0], self.pos[1] - offset[1] + self.anim_offset[1]))


class Player(Entity):
    def __init__(self, game, pos, size):
        super().__init__(game, 'player', pos, size)
        self.jumping = False
        self.airtime = 0
        self.idle = 'idle'
        self.teleported = False
        self.tel_distance = 100

    def update(self, tilemap, deltaT, movement=(0, 0)):
        super().update(tilemap,deltaT, movement=movement)

        if movement[0] == 0:
            self.velocity[0] -= self.velocity[0] * 0.2 #Deceleración
        else:
            self.velocity[0] = max(-3, min(3, (abs(self.velocity[0]) + 0.12) * movement[0])) #Aceleración

        self.airtime += 1

        if not self.jumping:
            if self.pushing:
                self.set_action('push')
                self.game.velocidad_pasos = 40
            else:
                self.game.velocidad_pasos = 20
                if movement[0] or movement[1]:
                    self.set_action('run')
                    self.idle = 'idle'
                else:
                    self.set_action(self.idle)




        if self.collisions['down']:
            self.jumping = False
            self.airtime = 0

        if self.airtime > 10:  # Margen de Coyote Time
            self.jumping = True
            if self.velocity[1] > -1:
                self.set_action('fall')

    def mew(self):
        self.idle = 'mew'


    def teleport(self, dir, tilemap):
        auxPos = self.pos
        if dir == 1:
            self.pos = [self.pos[0]+self.tel_distance, self.pos[1]]
        elif dir == 2:
            self.pos = [self.pos[0]-self.tel_distance, self.pos[1]]
        elif dir == 3:
            self.pos = [self.pos[0], self.pos[1]-self.tel_distance]
        elif dir == 4:
            self.pos = [self.pos[0], self.pos[1]+self.tel_distance]
        rectas = []
        for _, rect in tilemap.physics_rects_around(self.pos):
            rectas.append(rect)
        if self.rect().collidelist(rectas) > -1:
            self.pos = auxPos
            self.game.sfx['Tp_Wrong'].play()
        elif dir>0:
            self.game.sfx['Tp'].play()
        else:
            self.game.sfx['Tp_Wrong'].play()

    def change_teleport(self, tile):
        self.game.tp_dir = tile['variant']
        self.game.sfx['Tp_Change' + str(tile['variant'])].play()

    def death(self):
        self.game.death()

    def press_button(self,tile):
        tile['variant'] = tile['variant'] + 1
        self.game.presed()

    def door(self, tile):
        tile['variant'] = 2

    def checkpoint(self, auxrect, tile):
        self.game.checkpoint = [auxrect.centerx, auxrect.centery - 10]
        tile['variant'] = tile['variant'] + 1
        self.game.sfx['Checkpoint'].play()

    def activate_event(self,id):
        self.game.events.run(id)

    def tocar_portal(self):
        self.game.transition = 1

class Caja(Entity):
    def __init__(self, game, pos, size):
        super().__init__(game, 'caja', pos, size)

    def update(self, tilemap, deltaT, movement=(0, 0)):
        super().update(tilemap, deltaT, movement=movement)

        self.velocity[0] -= self.velocity[0] * 0.2  # Deceleración

    def render(self, surf, offset=(0, 0)):
        surf.blit(pg.transform.scale(pg.transform.flip(self.animation.img(), self.flip, False), self.size), (self.pos[0] - offset[0] + self.anim_offset[0], self.pos[1] - offset[1] + self.anim_offset[1]))









