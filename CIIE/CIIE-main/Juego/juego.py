import random
from Scripts.utils import *
from Scripts.tilemap import Tilemap
from Scripts.entities import *
from Scripts.events import *

new_game = False

class Game:
    def __init__(self):
        pg.init()

        self.size = 1280, 720
        self.juego_no_cargado = True

        pg.display.set_caption('La gran aventura de Eustaquio')
        self.screen = pg.display.set_mode(self.size)
        self.display = pg.Surface(self.size)

        self.started = False

        self.reloj = pg.time.Clock()

        self.tp_dir = 0

        self.tp_cooldown = 50

        self.events = Event(self)

        self.checkpoint = (50,50)
        self.checkpoint_dir = self.tp_dir

        self.music_volume = 0.8
        self.sfx_volume = 1

        self.movement = [False, False]

        self.player_size = (20, 30)
        tile_size = 40

        self.assets = {
            #tiles mapa 1
            'laboratorio': load_images('Lab', (tile_size, tile_size)), 

            #sprites Jugador
            'player/idle': Animation(load_images('player/idle', self.player_size), img_dur=40),
            'player/mew': Animation(load_images('player/mew', self.player_size), img_dur=40,loop=False),
            'player/jump': Animation(load_images('player/jump', self.player_size), img_dur=10, loop=False),
            'player/fall': Animation(load_images('player/fall', self.player_size), img_dur=10, loop=False),
            
            'player/run': Animation(load_images('player/run', self.player_size), img_dur=10),
            'player/push': Animation(load_images('player/push', self.player_size), img_dur=30),

            #Cajas
            'caja/idle': Animation(load_images('caja/idle', (tile_size, tile_size)), img_dur=1, loop=False),

            #TILES QUE TE MATAN
            'hazards': load_images('hazards', (tile_size, tile_size)), 

            #tiles mapa 2
            'Desert' : load_images('Desierto/Desert',(tile_size, tile_size)),
            'Desert_Temle' : load_images('Desierto/Desert_Temle',(tile_size, tile_size)),
            'Decoracion_Temple' : load_images('Desierto/Decoracion_Temple',(tile_size, tile_size)),
            'Desert_Temple_Doors' : load_images('Desierto/Desert_Temple_Doors',(tile_size, tile_size)),

            #tiles comunes
            'Maquna_tiempo' : load_images('Maquina_tiempo',(tile_size*3, tile_size*3)),
            'Tp' : load_images('Tp',(tile_size, tile_size)),
            'Puerta':load_images('Puerta',(tile_size*0.25, tile_size*3)),
            'Botones': load_images('Botones', (tile_size, tile_size)),
            'AN': load_images('AN_Final', (tile_size*5, tile_size*5)),
            'CheckPoint' : load_images('CheckPoint',(tile_size, tile_size)),

            #tiles jungla
            'Jungla_base' : load_images('Jungla/Jl_in',(tile_size, tile_size)),
            'Jungla_dentro' : load_images('Jungla/Jl_out',(tile_size, tile_size)),


            #Tiles Evento
            'Evento' : load_images('Event/Event_game',(tile_size, tile_size)),

            #Dispositivo de salto en el tiempo
            'Reloj': load_images('Event/Reloj', (tile_size, tile_size)),
        }

        #Sonidos
        self.sfx = {
            'Jump': pg.mixer.Sound('Sfx/jump.ogg'),
            'Tp': pg.mixer.Sound('Sfx/tp/teleport.ogg'),
            'Tp_Change1': pg.mixer.Sound('Sfx/tp/tp_change_direction1.ogg'),
            'Tp_Change2': pg.mixer.Sound('Sfx/tp/tp_change_direction2.ogg'),
            'Tp_Change3': pg.mixer.Sound('Sfx/tp/tp_change_direction3.ogg'),
            'Tp_Change4': pg.mixer.Sound('Sfx/tp/tp_change_direction4.ogg'),
            'Tp_Wrong': pg.mixer.Sound('Sfx/tp/tp_wrong.ogg'),
            'Death': pg.mixer.Sound('Sfx/death.ogg'),
            'Button': pg.mixer.Sound('Sfx/button_press.ogg'),
            'Checkpoint': pg.mixer.Sound('Sfx/checkpoint.ogg'),

        }
        self.sfx['Jump'].set_volume(0.1)
        self.sfx['Tp'].set_volume(1)
        self.sfx['Tp_Wrong'].set_volume(0.4)
        self.sfx['Death'].set_volume(1)
        self.sfx['Button'].set_volume(0.6)

        #volumenes predeterminados para cada sonido
        self.sfx_vol_max = {
            'Jump': 0.1,
            'Tp_Wrong': 0.4,
            'Button': 0.6,
        }
        for key in self.sfx.keys():
            self.sfx_vol_max.setdefault(key, 1)

        self.velocidad_pasos = 20

        self.metal_step = {
            'Step1': pg.mixer.Sound('Sfx/Metal_step/metal_step1.ogg'),
            'Step2': pg.mixer.Sound('Sfx/Metal_step/metal_step2.ogg'),
            'Step3': pg.mixer.Sound('Sfx/Metal_step/metal_step3.ogg'),
            'Step4': pg.mixer.Sound('Sfx/Metal_step/metal_step4.ogg'),
            'Step5': pg.mixer.Sound('Sfx/Metal_step/metal_step5.ogg'),
        }

        self.stone_step = {
            'Step1': pg.mixer.Sound('Sfx/Stone_step/stone_step1.ogg'),
            'Step2': pg.mixer.Sound('Sfx/Stone_step/stone_step2.ogg'),
            'Step3': pg.mixer.Sound('Sfx/Stone_step/stone_step3.ogg'),
            'Step4': pg.mixer.Sound('Sfx/Stone_step/stone_step4.ogg'),
            'Step5': pg.mixer.Sound('Sfx/Stone_step/stone_step5.ogg'),
        }

        self.grass_step = {
            'Step1': pg.mixer.Sound('Sfx/Grass_step/grass_step1.ogg'),
            'Step2': pg.mixer.Sound('Sfx/Grass_step/grass_step2.ogg'),
            'Step3': pg.mixer.Sound('Sfx/Grass_step/grass_step3.ogg'),
            'Step4': pg.mixer.Sound('Sfx/Grass_step/grass_step4.ogg'),
            'Step5': pg.mixer.Sound('Sfx/Grass_step/grass_step5.ogg'),
        }

        for i in range(1, 6):
            self.metal_step['Step' + str(i)].set_volume(1)
            self.stone_step['Step' + str(i)].set_volume(1)
            self.grass_step['Step' + str(i)].set_volume(1)

        
        #dialogue
        self.dialogue= Dialogue(screen=self.screen)
        self.counter=0
        self.actual_message=0
        self.messages=[]
        self.dialogueBool = False
        self.img=None
        #transition
        self.transition = 0
        self.dialogueLayer = 0

        #temblor de pantalla
        self.screenshake = 0

        self.tilemap = Tilemap(self, tile_size=tile_size)
        self.level = 1
        self.load_level('Laboratorio')



    def load_level(self, map_id):
        #mapa
        self.tilemap.load('mapas/' + map_id + '.json')      
        #fondo
        self.background = load_image('fondo/' + map_id + '/fondo.jpg', (1280, 720))
        #Musica
        pg.mixer.music.stop()
        pg.mixer.music.load('Musica/' + map_id + '.ogg')
        pg.mixer.music.set_volume(self.music_volume)
        pg.mixer.music.play(-1, fade_ms=3000)

        #Pasos
        if(map_id == 'Desierto'):
            self.step = self.stone_step
            self.checkpoint = 50, 50
        elif(map_id == 'Jungla'):
            self.step = self.grass_step
            self.checkpoint = 50, 50
        else:
            self.step = self.metal_step
            self.checkpoint = 700, 300

        self.scroll = [0, 0]

        self.player = Player(self, self.checkpoint, self.player_size)
        #lista de cajas de un nivel
        self.cajas = []

        #booleano para comprobar si se ha pulsado o no el boton para abrir las puertas de un nivel
        self.open = False
        #transition
        self.transition = -30
        #transition
        for caja in self.tilemap.extract([('caja', 0), ('caja', 1), ('caja', 2)]):
            if caja['variant'] == 0:
                self.cajas.append(Caja(self, caja['pos'], (40, 40)))
            if caja['variant'] == 1:
                self.cajas.append(Caja(self, caja['pos'], (60, 60)))
            if caja['variant'] == 2:
                self.cajas.append(Caja(self, caja['pos'], (80, 80)))

    def menu(self):
        new_game = False
        pg.mixer.music.stop()

        button_jug_surface = load_image('menu_inicio/bot_ju.png', (200,200*0.33))
        button_continue_surface = load_image('menu_inicio/bot_con.png', (200,200*0.33))
        button_continue_surface_bw = load_image('menu_inicio/bot_con_bw.png', (200,200*0.33))
        button_sal_surface = load_image('menu_inicio/bot_sal.png', (200,200*0.33))
        portada = load_image('menu_inicio/portada.jpg',(1280,720))
        
        button_jugar = Button(button_jug_surface, 640, 340,self.screen)
        if self.juego_no_cargado:
            button_continue = Button(button_continue_surface_bw, 640, 440, self.screen)
        else:
            button_continue = Button(button_continue_surface, 640, 440, self.screen)
        button_salir = Button(button_sal_surface, 640, 540,self.screen)

        running = True
        while running:
            for event in pg.event.get():
                if event.type == pg.QUIT:
                    pg.quit()
                    sys.exit()
                if event.type == pg.MOUSEBUTTONDOWN:
                    if button_jugar.checkForInput(pg.mouse.get_pos()):
                        new_game = True
                        running = False
                    if button_salir.checkForInput(pg.mouse.get_pos()):
                        pg.quit()
                        sys.exit()
                    if button_continue.checkForInput(pg.mouse.get_pos()) and not self.juego_no_cargado:
                        running = False

            self.screen.fill("white")
            self.screen.blit(portada,(0,0))

            button_jugar.update()
            button_continue.update()
            button_salir.update()
            pg.display.update()
        return new_game
    
    def final(self):
        self.juego_no_cargado = True
        portada = Animation(load_images('pantalla_final', (1280,720)), img_dur=100)

        pg.mixer.music.fadeout(5000)
        self.dialogueLayer = 1
        self.dialogueBool = True
        self.messages = self.dialogue.load_dialogue('Dialogos/dialogue7.json')

        running = True
        while running:
            for event in pg.event.get():
                if event.type == pg.QUIT:
                    pg.quit()
                    sys.exit()
                if event.type == pg.KEYDOWN:
                    if event.key == pg.K_RETURN or event.key == pg.K_e:
                        self.enterPulsado = True

            portada.update()
            self.screen.fill("white")
            self.screen.blit(portada.img(),(0,0))
            if (self.dialogueBool):
                self.show_dialogue()
            else:
                running = False
            pg.display.update()
            self.enterPulsado = False
        
        


    def run(self):
        self.juego_no_cargado = False
        count = 0
        tiempo_paso = 0

        pg.mixer.music.play(-1, fade_ms=3000)

        if not self.started:
            self.dialogueLayer = 1
            self.dialogueBool = True
            self.messages = self.dialogue.load_dialogue('Dialogos/dialogue1.json')
            self.img = self.dialogue.load_image("Imagenes/Maquina_tiempo/Maquina_tiempo.png", (400, 400))
            self.started = True

        self.running = True

        while self.running:
            deltaT = self.reloj.tick(60)/ 10
            self.screenshake = max(0,self.screenshake - 1)
            self.enterPulsado =False
            self.scroll[0] += (self.player.rect().centerx - self.display.get_width() / 2 - self.scroll[0]) / 30
            self.scroll[1] += (self.player.rect().centery - self.display.get_height() / 2 - self.scroll[1]) / 30
            render_scroll = (int(self.scroll[0]), int(self.scroll[1]))
            #transition
            if self.transition <0:
                self.transition+=1
            #transition
            for event in pg.event.get():
                if event.type == pg.QUIT:
                    pg.quit()
                    sys.exit()
                if event.type == pg.KEYDOWN:
                    if event.key == pg.K_a:
                        self.movement[0] = True
                    if event.key == pg.K_d:
                        self.movement[1] = True
                    if (event.key == pg.K_w or event.key == pg.K_SPACE)  and not (self.player.jumping or self.dialogueBool):
                        self.player.velocity[1] = -5
                        self.player.jumping = True
                        self.player.set_action('jump')
                        self.sfx['Jump'].play()
                    if event.key == pg.K_m:
                        self.player.mew()
                    if event.key == pg.K_s and not self.player.teleported:
                        if not self.dialogueBool:
                            self.player.teleport(self.tp_dir, self.tilemap)
                            self.player.teleported = True
                    if event.key==pg.K_RETURN or event.key==pg.K_e:
                        self.enterPulsado = True
                    if event.key == pg.K_o: #Herramienta de Testing
                        self.transition += 1
                    if event.key == pg.K_p or event.key == pg.K_ESCAPE:
                        self.running = self.pause(self.reloj)
                        pg.event.clear()
                        break
                    if event.key == pg.K_l:
                        self.running = False
                        break
                if event.type == pg.KEYUP:
                    if event.key == pg.K_a:
                        self.movement[0] = False
                    if event.key == pg.K_d:
                        self.movement[1] = False
                if event.type == self.events.END_RUMBLING:
                    self.events.run(1.5)
            self.display.fill((245, 235, 245))
            #dialogue
            if self.dialogueBool:
                self.movement[0]=False
                self.movement[1]=False
                self.player.velocity[0] = 0
            #dialogue
            

            #Pasos
            tiempo_paso += deltaT
            if(self.movement[0] or self.movement[1]) and (tiempo_paso > self.velocidad_pasos) and (self.player.velocity[1] == 0):
                self.step['Step' + str(random.randint(1, 5))].play()
                tiempo_paso = 0

            self.display.fill((0, 0, 0))   
            self.display.blit(self.background,(0,0))      

            for caja in self.cajas:
                caja.update(self.tilemap, deltaT, (0, 0))
                caja.render(self.display, render_scroll)

            self.player.update(self.tilemap, deltaT, (self.movement[1] - self.movement[0], 0))

            self.tilemap.render_offgrid(self.display, render_scroll)
            self.player.render(self.display, render_scroll)
            self.tilemap.render(self.display, render_scroll)
            screenshake_offset = (random.random() * self.screenshake - self.screenshake/2,random.random() * self.screenshake - self.screenshake/2)
            self.screen.blit(pg.transform.scale(self.display, self.screen.get_size()),screenshake_offset)
            
            #dialogue
            if(self.dialogueBool):
                self.show_dialogue()
            #dialogue
            #transition
            if self.transition:

                self.transition+=1
                transition_surf = pg.Surface(self.display.get_size())
                pg.draw.circle(transition_surf, (255, 255, 255), (self.display.get_width() // 2, self.display.get_height() // 2), (30 - abs(self.transition)) * 8)
                transition_surf.set_colorkey((255, 255, 255))
                self.screen.blit(transition_surf, (0, 0))            
            if self.transition > 30:
                if self.level == 1:
                    self.load_level('Desierto')
                    self.level = 2
                    self.tp_dir = 1
                    self.checkpoint_dir = 1
                    self.dialogueLayer = 1
                    self.dialogueBool = True
                    self.messages = self.dialogue.load_dialogue('Dialogos/dialogue5.json')
                elif self.level == 2:
                    self.load_level('Jungla')
                    self.level = 3
                    self.tp_dir = 3
                    self.checkpoint_dir = 3
                    self.dialogueLayer = 1
                    self.dialogueBool = True
                    self.messages = self.dialogue.load_dialogue('Dialogos/dialogue15.json')
                elif self.level == 3:
                    self.final()
                    self.running = False
                    self.load_level('Laboratorio')
                    self.level = 1
                    self.tp_dir = 1
                    self.checkpoint_dir = 1
            #transition
            pg.display.update()
            if self.player.teleported and count < self.tp_cooldown:
                count += 1
            else:
                self.player.teleported = False
                count = 0
        del self
    #funcion de muerte
    def death(self):
        self.player.velocity[1] = 0
        self.tp_dir = self.checkpoint_dir
        self.player.pos = [self.checkpoint[0],self.checkpoint[1]]
        self.screenshake = max(10, self.screenshake)
        self.sfx['Death'].play()

    def show_dialogue(self):
        if (self.dialogueLayer == 0):
            finish = self.dialogue.first_dialogue_layer(enterPulsado=self.enterPulsado, game=self)
        elif (self.dialogueLayer == 1):
            finish = self.dialogue.second_dialogue_layer(enterPulsado=self.enterPulsado, game=self)
        elif (self.dialogueLayer == 2):
            finish = self.dialogue.third_dialogue_layer(enterPulsado=self.enterPulsado, game=self)
        if finish:
            self.counter = 0
            self.messages = []
            self.actual_message = False
            self.actual_message = 0
            self.dialogueBool = False
    #funcion que se activa al pulsar un boton
    def presed(self):
        self.open = True
        self.sfx['Button'].play()
    #funcion de pausa del juego 
    def pause(self, reloj):
        flag = True
        stopBool = True
        mouse_music = False
        mouse_sfx = False

        font1 = pg.font.SysFont(None, 120)
        font2 = pg.font.SysFont(None, 60)

        button_sal_surface = load_image('menu_inicio/bot_sal.png', (200,200*0.33))
        button_chk_surface = load_image('menu_inicio/bot_ch.png', (200,200*0.33))
        button_con_surface = load_image('menu_inicio/bot_con.png', (200,200*0.33))
        button_salir = Button(button_sal_surface, 1160, 60, self.screen)
        button_respawn = Button(button_chk_surface, 120, 60, self.screen)
        button_continue = Button(button_con_surface, 640, 610, self.screen)

        music_bar = Volume(240, 320, 800, self.screen, self.music_volume)
        sfx_bar = Volume(240, 480, 800, self.screen, self.sfx_volume)

        sf = pg.Surface(self.size)
        sf.set_alpha(120)
        sf.fill((0,0,0))

        pauseImg = font1.render('PAUSA', True, (100,100,255))
        musicImg = font2.render('Musica', True, (100,100,255))
        sfxImg = font2.render('Efectos de sonido', True, (100,100,255))

        self.screen.blit(sf, (0,0))
        self.screen.blit(pauseImg, (640-pauseImg.get_rect().size[0]/2, 50))
        self.screen.blit(musicImg, (640-musicImg.get_rect().size[0]/2, 260))
        self.screen.blit(sfxImg, (640-sfxImg.get_rect().size[0]/2, 420))

        button_salir.update()
        button_respawn.update()
        button_continue.update()

        pg.display.update()
        sf.blit(self.screen,(0,0))
        while stopBool:
            self.reloj.tick(60)/ 10
            self.screen.blit(sf, (0,0))
            for event in pg.event.get():
                if event.type == pg.QUIT:
                    pg.quit()
                    sys.exit()
                if event.type == pg.KEYDOWN and (event.key == pg.K_p or event.key == pg.K_ESCAPE):
                    stopBool = False
                if event.type == pg.KEYUP:
                    if event.key == pg.K_LEFT:
                        self.movement[0] = False
                    if event.key == pg.K_RIGHT:
                        self.movement[1] = False
                if event.type == pg.MOUSEBUTTONDOWN:
                    if button_salir.checkForInput(pg.mouse.get_pos()):
                        stopBool = False
                        flag = False
                    elif button_respawn.checkForInput(pg.mouse.get_pos()):
                        self.death()
                        stopBool = False
                    elif button_continue.checkForInput(pg.mouse.get_pos()):
                        stopBool = False
                    else:
                        aux_music = music_bar.checkForInput(pg.mouse.get_pos(), False)
                        aux_sfx = sfx_bar.checkForInput(pg.mouse.get_pos(), False)
                        if aux_music != self.music_volume:
                            self.music_volume = aux_music
                            pg.mixer.music.set_volume(self.music_volume)
                            mouse_music = True
                        if aux_sfx != self.sfx_volume:
                            self.sfx_volume = aux_sfx
                            mouse_sfx = True
                if event.type == pg.MOUSEMOTION:
                    if mouse_music:
                        self.music_volume = music_bar.checkForInput(pg.mouse.get_pos(), True)
                        pg.mixer.music.set_volume(self.music_volume)
                    if mouse_sfx:
                        self.sfx_volume = sfx_bar.checkForInput(pg.mouse.get_pos(), True)
                if event.type == pg.MOUSEBUTTONUP:
                    mouse_music = False
                    mouse_sfx = False
            music_bar.update()
            sfx_bar.update()
            pg.display.update()
        for sfx in self.sfx.keys():
            self.sfx[sfx].set_volume(self.sfx_volume*self.sfx_vol_max.get(sfx)) #regla de 3
        for i in range(1, 6):
            self.step['Step' + str(i)].set_volume(self.sfx_volume)

        self.movement[0] = False
        self.movement[1] = False
        return flag

if __name__ == '__main__':
    game = Game()
    while 1:
        new_game = game.menu()
        if new_game:
            game = Game()
        game.run()