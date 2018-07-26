package com.game.wuziqi.client;

public class QiPan {
    public static int SPACE = 0;
    public static int BLACK = 1;
    public static int WHITE = 2;

    private int[][] qiziArr = new int[15][15];

    public QiPan() {

    }

    public void reset() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                qiziArr[i][j] = SPACE;
            }
        }
    }

    public void setPlace(int x, int y, int flag) {
        qiziArr[x][y] = flag;
    }

    public void print() {
        for (int i = 0; i < 15; i++) {
            System.out.print("  " + Integer.toHexString(i));
        }
        System.out.println();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (j == 0) {
                    System.out.print(Integer.toHexString(i) + " ");
                }
                if (qiziArr[i][j] == SPACE) {
                    System.out.print("+  ");
                } else if (qiziArr[i][j] == BLACK) {
                    System.out.print("●  ");
                } else {
                    System.out.print("○  ");
                }
            }
            System.out.println();
        }

    }
}
