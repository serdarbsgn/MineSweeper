package com.serdarbsgn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    int resolutionX, resolutionY;
    int[] mouseCoordinates;
    int mouseClicked;
    int[][] mineField;
    int mineCount = 75;
    boolean firstClick = true;

    private final Timer timer;

    public Game(int width, int height) {
        resolutionX = width;
        resolutionY = height;
        mouseCoordinates = new int[]{0, 0};
        mineField = new int[28][16];
        setFocusable(true);
        addMouseMotionListener(this);
        addMouseListener(this);
        int delay = 5;
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1) {
            mouseClicked = 1;
        }
        if (e.getButton() == 2) {
            mouseClicked = 2;
        }
        if (e.getButton() == 3) {
            mouseClicked = 3;
        }
    }

    public void mouseMoved(MouseEvent e) {
        mouseCoordinates[0] = e.getX();
        mouseCoordinates[1] = e.getY();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        int x = mouseCoordinates[0] / 35;
        int y = mouseCoordinates[1] / 35;

        if (mouseClicked == 1) {
            if (x == 0 || x == 27 || y == 0 || y == 15) {
            } else {
                if (firstClick) {
                    initializeMineField(x, y);
                    firstClick = false;
                } else arrangeNumbers(x, y);
            }
            mouseClicked = -1;
        }
        if (mouseClicked == 2) {
            mineCount = 75;
            initializeMineField(x, y);
            mouseClicked = -1;
        }
        if (mouseClicked == 3) {
            flagSquare(x, y);
            mouseClicked = -1;
        }
        repaint();
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
        for (int i = 1; i < 27; i++) {
            for (int j = 1; j < 15; j++) {
                if (mineField[i][j] != 10 && mineField[i][j] != 9) {
                    if (mineField[i][j] > 50) {
                        g.setColor(Color.RED);
                        g.fillRect((resolutionX * i / 27) - 10, (resolutionY * j / 15) - 10, 20, 20);
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
                    g.drawString(mineField[i][j] + "", (resolutionX * i / 27), (resolutionY * j / 15));
                }
            }
        }

    }

    private void flagSquare(int x, int y) {
        if (mineField[x][y] < 50) {
            mineField[x][y] += 99;
        } else {
            mineField[x][y] -= 99;
        }
    }

    private void initializeMineField(int x, int y) {
        for (int i = 1; i < 27; i++) {
            for (int j = 1; j < 15; j++) {
                if (i == x && j == y) {
                    mineField[x][y] = 10;

                    if (mineField[x - 1][y] == 9) {
                        mineCount++;
                    }
                    mineField[x - 1][y] = 10;

                    if (mineField[x - 1][y - 1] == 9) {
                        mineCount++;
                    }
                    mineField[x - 1][y - 1] = 10;

                    if (mineField[x][y - 1] == 9) {
                        mineCount++;
                    }
                    mineField[x][y - 1] = 10;

                    if (mineField[x][y + 1] == 9) {
                        mineCount++;
                    }
                    mineField[x][y + 1] = 10;

                    if (mineField[x - 1][y + 1] == 9) {
                        mineCount++;
                    }
                    mineField[x - 1][y + 1] = 10;

                    if (mineField[x + 1][y] == 9) {
                        mineCount++;
                    }
                    mineField[x + 1][y] = 10;

                    if (mineField[x + 1][y - 1] == 9) {
                        mineCount++;
                    }
                    mineField[x + 1][y - 1] = 10;

                    if (mineField[x + 1][y + 1] == 9) {
                        mineCount++;
                    }
                    mineField[x + 1][y + 1] = 10;

                } else if ((i == x && j == y + 1) || (i == x + 1 && (j == y - 1 || j == y || j == y + 1))) {
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
        for (int i = 0; i < 28; i++) {
            if (i < 16) {
                mineField[0][i] = 0;
                mineField[27][i] = 0;
            }
            mineField[i][0] = 0;
            mineField[i][15] = 0;
        }
        System.out.println("Mines That Couldn't Placed : " + mineCount);
        arrangeNumbers(x, y);
    }

    private void arrangeNumbers(int x, int y) {
        if (mineField[x][y] == 0 || mineField[x][y] > 50) {
            return;
        }
        int bombCount = -1;
        if (mineField[x][y] == 9) {
            System.out.println("Game Over");
        } else {
            bombCount = 0;

            if (mineField[x - 1][y] == 9 || mineField[x - 1][y] == 108) {
                bombCount++;
            }

            if (mineField[x - 1][y - 1] == 9 || mineField[x - 1][y - 1] == 108) {
                bombCount++;
            }

            if (mineField[x][y - 1] == 9 || mineField[x][y - 1] == 108) {
                bombCount++;
            }

            if (mineField[x][y + 1] == 9 || mineField[x][y + 1] == 108) {
                bombCount++;
            }

            if (mineField[x - 1][y + 1] == 9 || mineField[x - 1][y + 1] == 108) {
                bombCount++;
            }

            if (mineField[x + 1][y] == 9 || mineField[x + 1][y] == 108) {
                bombCount++;
            }

            if (mineField[x + 1][y - 1] == 9 || mineField[x + 1][y - 1] == 108) {
                bombCount++;
            }

            if (mineField[x + 1][y + 1] == 9 || mineField[x + 1][y + 1] == 108) {
                bombCount++;
            }
        }
        if (bombCount == 0) {
            mineField[x][y] = 0;
            arrangeNumbers(x - 1, y);
            arrangeNumbers(x - 1, y - 1);
            arrangeNumbers(x, y - 1);
            arrangeNumbers(x - 1, y + 1);
            arrangeNumbers(x, y + 1);
            arrangeNumbers(x + 1, y);
            arrangeNumbers(x + 1, y - 1);
            arrangeNumbers(x + 1, y + 1);

        } else mineField[x][y] = bombCount;
    }


    public void mouseDragged(MouseEvent e) {

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
