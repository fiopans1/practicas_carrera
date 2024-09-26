### N-queens problem

See definition of the problem [here](https://en.wikipedia.org/wiki/Eight_queens_puzzle)

**Features used:**
- Clingo integration
- Neato
- Absolute Position

`clingo encoding.lp --outf=2 | clingraph --view --dir='out/sudoku' --format=png --out=render  --engine=neato --viz-encoding=viz.lp`

#`clingo encoding.lp --outf=2 | clingraph --viz-encoding=viz.lp --out=render  --engine=neato`

![](default.png)
