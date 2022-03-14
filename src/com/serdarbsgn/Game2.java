package com.serdarbsgn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game2 extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    int resolutionX, resolutionY;
    int[] mouseCoordinates;
    int mouseClicked;
    int[][] mineField;
    int mineCount = 75;
    boolean firstClick = true;

    private final Timer timer;

    public Game2(int width, int height) {
        resolutionX = width;
        resolutionY = height;
        mouseCoordinates = new int[]{0, 0};
        mineField = new int[26][14];
        setFocusable(true);
        addMouseMotionListener(this);
        addMouseListener(this);
        int delay = 5;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        requestFocus(true);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, resolutionX, resolutionY);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(20, 20, resolutionX - 40, resolutionY - 40);
        g.setColor(Color.GRAY);
        for (int i = 2; i < 27; i++) {
            g.drawLine(resolutionX * i / 27 - 15, resolutionY / 15 - 16, resolutionX * i / 27 - 15, resolutionY - 21);
        }
        for (int j = 2; j < 15; j++) {
            g.drawLine(resolutionX / 27 - 15, resolutionY * j / 15 - 16, resolutionX - 21, resolutionY * j / 15 - 16);
        }
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 14; j++) {
                if (mineField[i][j] != 10 && mineField[i][j] != 9) {
                    if (mineField[i][j] > 50) {
                        g.setColor(Color.RED);
                        g.fillRect(30 + (resolutionX * i / 27), 30 + (resolutionY * j / 15), 20, 20);
                        continue;
                    }
                    if (mineField[i][j] == 0) {
                        g.setColor(Color.DARK_GRAY);
                    }
                    if (mineField[i][j] == 1) {
                        g.setColor(Color.BLUE);
                    }
                    if (mineField[i][j] == 2) {
                        g.setColor(new Color(0, 90, 0));
                    }
                    if (mineField[i][j] == 3) {
                        g.setColor(Color.RED);
                    }
                    if (mineField[i][j] == 4) {
                        g.setColor(Color.MAGENTA);
                    }
                    if (mineField[i][j] == 5) {
                        g.setColor(Color.YELLOW);
                    }
                    if (mineField[i][j] == 6) {
                        g.setColor(Color.PINK);
                    }
                    if (mineField[i][j] == 7) {
                        g.setColor(Color.BLACK);
                    }
                    g.drawString(mineField[i][j] + "", 35 + (resolutionX * i / 27), 40 + (resolutionY * j / 15));
                }
            }
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        System.out.println(mouseCoordinates[0]+" "+mouseCoordinates[1]);
        System.out.println((mouseCoordinates[0] - 20) / 35+" "+(mouseCoordinates[1] - 20) / 35);
        repaint();
    }


    public void mouseMoved(MouseEvent e) {
        mouseCoordinates[0] = e.getX();
        mouseCoordinates[1] = e.getY();
    }

    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            mouseClicked = 1;
            if (firstClick) {
                initializeMineField((mouseCoordinates[0] - 20) / 35, (mouseCoordinates[1] - 20) / 35);
                firstClick = false;
            } else arrangeNumbers((mouseCoordinates[0] - 20) / 35, (mouseCoordinates[1] - 20) / 35);
        }
        if (e.getButton() == 3) {
            mouseClicked = 3;
            flagSquare();
        }
    }

    private void flagSquare() {
        if (mineField[(mouseCoordinates[0] - 20) / 35][(mouseCoordinates[1] - 20) / 35] < 50) {
            mineField[(mouseCoordinates[0] - 20) / 35][(mouseCoordinates[1] - 20) / 35] += 99;
        } else {
            mineField[(mouseCoordinates[0] - 20) / 35][(mouseCoordinates[1] - 20) / 35] -= 99;
        }
    }

    private void initializeMineField(int x, int y) {
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 14; j++) {
                if (i == x && j == y) {
                    if (x >= 1) {
                        if (mineField[x - 1][y] == 9) {
                            mineCount++;
                        }
                        mineField[x - 1][y] = 10;

                        if (y >= 1) {
                            if (mineField[x - 1][y - 1] == 9) {
                                mineCount++;
                            }

                            mineField[x - 1][y - 1] = 10;
                            if (mineField[x][y - 1] == 9) {
                                mineCount++;
                            }

                            mineField[x][y - 1] = 10;
                        }
                        if (y <= 12) {
                            if (mineField[x][y + 1] == 9) {
                                mineCount++;
                            }

                            mineField[x][y + 1] = 10;
                            if (mineField[x - 1][y + 1] == 9) {
                                mineCount++;
                            }

                            mineField[x - 1][y + 1] = 10;
                        }
                    }
                    if (x <= 24) {
                        if (mineField[x + 1][y] == 9) {
                            mineCount++;
                        }

                        mineField[x + 1][y] = 10;
                        if (y >= 1) {
                            if (mineField[x + 1][y - 1] == 9) {
                                mineCount++;
                            }

                            mineField[x + 1][y - 1] = 10;
                        }
                        if (y <= 12) {
                            if (mineField[x + 1][y + 1] == 9) {
                                mineCount++;
                            }

                            mineField[x + 1][y + 1] = 10;
                        }
                    }
                } else if ((i == x && j == y + 1) || (i == x + 1 && (j == y - 1 || j == y || j ==y + 1))) {  //|| (i == x + 1 && j == y) || (i == x + 1 && j == y + 1))
                    continue;
                } else {
                    if (mineCount != 0) {
                        int temp = (int) (Math.random() * 4);
                        if (temp == 3) {
                            mineField[i][j] = 9;
                            mineCount--;
                        } else mineField[i][j] = 10;
                    } else {
                        mineField[i][j] = 10;
                    }
                }
            }
        }
        arrangeNumbers((mouseCoordinates[0] - 20) / 35, (mouseCoordinates[1] - 20) / 35);

    }

    private void arrangeNumbers(int x, int y) {
        if (mineField[x][y] == 0) {
            return;
        }
        int bombCount = -1;
        if (mineField[x][y] == 9) {
            System.out.println("Game Over");
        } else {
            bombCount = 0;
            if (x >= 1) {
                if (mineField[x - 1][y] == 9 || mineField[x - 1][y] == 108) {
                    bombCount++;
                }
                if (y >= 1) {
                    if (mineField[x - 1][y - 1] == 9 || mineField[x - 1][y - 1] == 108) {
                        bombCount++;
                    }
                    if (mineField[x][y - 1] == 9 || mineField[x][y - 1] == 108) {
                        bombCount++;
                    }
                }
                if (y <= 12) {
                    if (mineField[x][y + 1] == 9 || mineField[x][y + 1] == 108) {
                        bombCount++;
                    }
                    if (mineField[x - 1][y + 1] == 9 || mineField[x - 1][y + 1] == 108) {
                        bombCount++;
                    }
                }
            }
            if (x <= 24) {
                if (mineField[x + 1][y] == 9 || mineField[x + 1][y] == 108) {
                    bombCount++;
                }
                if (y >= 1) {
                    if (mineField[x + 1][y - 1] == 9|| mineField[x + 1][y-1] == 108) {
                        bombCount++;
                    }
                }
                if (y <= 12) {
                    if (mineField[x + 1][y + 1] == 9|| mineField[x + 1][y+1] == 108) {
                        bombCount++;
                    }
                }
            }
        }
        if (bombCount == 0) {
            mineField[x][y] = 0;
            if (x >= 1) {
                arrangeNumbers(x - 1, y);

                if (y >= 1) {
                    arrangeNumbers(x - 1, y - 1);
                    arrangeNumbers(x, y - 1);
                }
                if (y <= 12) {
                    arrangeNumbers(x - 1, y + 1);
                    arrangeNumbers(x, y + 1);
                }
            }
            if (x <= 24) {
                arrangeNumbers(x + 1, y);
                if (y >= 1) {
                    arrangeNumbers(x + 1, y - 1);
                }
                if (y <= 12) {
                    arrangeNumbers(x + 1, y + 1);
                }
            }
        } else mineField[x][y] = bombCount;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
