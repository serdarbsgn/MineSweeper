Main class is the nearly the same class of all my java games;
reads the screen resolution,
sets the title of frame,
and implements the main game with all the game logic and drawing.

Game class implements ActionListener,MousemMotionListener and Mouselistener,
Firstly it initialized a 2d-array of integers that is all set to zero, then a timer on delay continuosly calls
actionPerformed function. As soon as the player left-clicks on one of the squares, game sets the minefield with mines randomly
but always leaves the selected square free of mine. Then a function is called to inform players about the mines near the field.
Well, everyone knows how minesweeper is played right?

Left click is for selecting a square
Right click is for flagging a square
Middle mouse click is for restarting the game.

