import pygame as pg


class Event:
    def __init__(self, game):
        self.activated = []
        self.game = game
        self.step = 0
        self.END_RUMBLING = pg.USEREVENT + 1

    def musicEvent(self):
        pg.event.peek()

    def run(self, id):

        if not self.activated.__contains__(id):
            self.activated.append(id)
            if id == 0:
                if self.game.open and self.step == 0:
                    pg.mixer.music.fadeout(3000)
                    self.game.dialogueLayer = 1
                    self.game.dialogueBool = True
                    self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue6.json')
                    self.step = 1
                    self.activated.remove(id)

                elif not self.game.dialogueBool and self.step == 1:
                    self.game.screenshake = max(180, self.game.screenshake)
                    pg.mixer.music.load('Sfx/rumbling.ogg')
                    pg.mixer.music.play()
                    self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue9.json')
                    self.game.dialogueBool = True
                    pg.mixer.music.set_endevent(self.END_RUMBLING)
                    for tile in self.game.tilemap.offgrid_tiles:
                        if tile['type'] == "Maquna_tiempo":
                            tile['variant'] = 2
                    self.step = 2
                else:
                    self.activated.remove(id)
            elif id == 1.5:
                pg.mixer.music.load("Musica/Laboratorio2.ogg")
                pg.mixer.music.play(-1, fade_ms=5000)
                self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue8.json')
                self.game.dialogueBool = True
            elif id == 1:
                print("Evento 2")
                self.game.dialogueLayer = 1
                self.game.dialogueBool = True
                self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue10.json')
            elif id == 2:
                print("Evento 3")
                self.game.dialogueLayer = 2
                self.game.dialogueBool = True
                self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue11.json')
                self.game.img = self.game.dialogue.load_image("Imagenes/Event/Explicación.png", (600, 600))
                self.game.tilemap.extract([('Reloj', 0)])
                self.game.tp_dir = 1
                self.game.checkpoint_dir = 1
                self.activated.append(1)
            elif id == 3:
                print("Evento 4")
                self.game.dialogueLayer = 1
                self.game.dialogueBool = True
                self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue2.json')
            elif id == 4:
                print("Evento 5")
                self.game.dialogueLayer = 1
                self.game.dialogueBool = True
                self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue12.json')
            elif id == 5:
                print("Evento 6")
                self.game.dialogueLayer = 1
                self.game.dialogueBool = True
                self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue3.json')
            elif id == 6:
                print("Evento 7")
                self.game.dialogueLayer = 1
                self.game.dialogueBool = True
                self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue4.json')
            elif id == 7:
                print("Evento 8")
                self.game.dialogueLayer = 1
                self.game.dialogueBool = True
                self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue13.json')
            elif id == 8:
                print("Evento 9")
                self.game.dialogueLayer = 1
                self.game.dialogueBool = True
                self.game.messages = self.game.dialogue.load_dialogue('Dialogos/dialogue14.json')
            elif id == 9:
                print("Evento 10")
                self.game.final()




