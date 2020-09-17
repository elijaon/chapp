package nz.ac.vuw.ecs.swen225.gp20.render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class Pane {

    public int[][] board = new int[100][100];
    public Tile[][] tileMap = new Tile[100][100];
    int xPos, yPos;
    int frameWidth, frameHeight;

    // 0 - North
    // 1 - East
    // 2 - South
    // 3 - West
    int direction = 2;

    Image north, south, east, west;

    public Pane() {

        // Initialize character facing GIFS
        north = new ImageIcon(getClass().getResource("resource/backFacing.gif")).getImage();
        south = new ImageIcon(getClass().getResource("resource/frontFacing.gif")).getImage();
        east = new ImageIcon(getClass().getResource("resource/rightFacing.gif")).getImage();
        west = new ImageIcon(getClass().getResource("resource/leftFacing.gif")).getImage();

        int num;
        // Initialize 2D Array with random SHIT.
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                num = ThreadLocalRandom.current().nextInt(0, 3 + 1);
                board[i][j] = num;
            }
        }

        // Convert int 2D array into tileMap 2D Array
        int tileType;
        Tile tile = null;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                tileType = board[i][j];
                switch (tileType) {
                    case 0:
                        tile = new Blue(i, j);
                        break;
                    case 1:
                        tile = new Red(i, j);
                        break;
                    case 2:
                        tile = new Grey(i, j);
                        break;
                    case 3:
                        tile = new Yellow(i, j);
                        break;
                }
                tileMap[i][j] = tile;
            }
        }

        JFrame frame = new JFrame("Tile Map Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frameHeight = (frame.getHeight() /2) - 50;
        frameWidth = (frame.getWidth() /2) - 50;

        xPos = 25;
        yPos = 25;

       JComponent drawBoard = new JComponent() {
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
                renderTiles(g2d);
                drawPlayer(g2d, this);
                // if greater than 0-24 or 0-25 don't accept.
            }
        };

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

                // KEY NOTE:
                // Each of these should instead just be public call-able methods accessible
                // to application key listener

                if(keyEvent.getKeyCode()== KeyEvent.VK_RIGHT) {
                    if (xPos > 40) System.out.println("Boundary Reached");
                    else {
                        System.out.println("Right");
                        xPos++;
                        System.out.println(xPos + " " + yPos);
                        direction = 1;
                    }
                }
                else if(keyEvent.getKeyCode()== KeyEvent.VK_LEFT) {
                    if (xPos < 10) System.out.println("Boundary Reached");
                    else {
                        System.out.println("Left");
                        xPos--;
                        System.out.println(xPos + " " + yPos);
                        direction = 3;
                    }
                }
                else if(keyEvent.getKeyCode()== KeyEvent.VK_DOWN) {
                    if (yPos > 40) System.out.println("Boundary Reached");
                    else {
                        System.out.println("Down");
                        yPos++;
                        System.out.println(xPos + " " + yPos);
                        direction = 2;
                    }
                }
                else if(keyEvent.getKeyCode()== KeyEvent.VK_UP) {
                    if (yPos < 10) System.out.println("Boundary Reached");
                    else {
                        System.out.println("Up");
                        yPos--;
                        System.out.println(xPos + " " + yPos);
                        direction = 0;
                    }
                }

                drawBoard.repaint();
            }
        });

       frame.add(drawBoard);
       frame.setVisible(true);

    }

    public void renderTiles(Graphics2D g) {
        for (int i = 0; i < 5; i++) {
            int invert = i*-1;
            drawRow(g, i);
            drawRow(g, invert);
        }
    }

    public void drawRow(Graphics2D g, int y) {
        // Centre Tile

        tileMap[y + yPos][xPos].drawTile(g, frameWidth, frameHeight + (y * 100));

        for (int i = 1; i < 5; i++) {
            tileMap[y + yPos][xPos - i].drawTile(g, frameWidth - (i * 100), frameHeight + (y * 100));
            tileMap[y + yPos][xPos + i].drawTile(g, frameWidth + (i * 100), frameHeight + (y * 100));
        }
    }

    public void drawPlayer(Graphics2D g, JComponent display) {
        switch (direction) {
            case 0: // North
                g.drawImage(north, frameWidth, frameHeight, display);
                break;
            case 1: //East
                g.drawImage(east, frameWidth, frameHeight, display);
                break;
            case 2: // South
                g.drawImage(south, frameWidth, frameHeight, display);
                break;
            case 3: // West
                g.drawImage(west, frameWidth, frameHeight, display);
                break;
        }
    }



    public static void main(String... args) {
        new Pane();
    }
}
