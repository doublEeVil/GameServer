package com.game.world.bean;

public class QiPan {
    public static final int SPACE = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;

    public static final int FORBBIDEN = 1; //
    public static final int CONTINUE = 2;
    public static final int GAMEOVER = 3;


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

    public int setPlace(int x, int y, int flag) {
        if (qiziArr[x][y] == SPACE) {
            qiziArr[x][y] = flag;

            // 检查
            boolean isOver = checkGameOver();
            if (isOver) {
                return GAMEOVER;
            }
            return CONTINUE;
        }
        return FORBBIDEN;
    }

    public boolean checkGameOver() {
        boolean flag;

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                // 采用最原始暴力的方法，该方法可以认为性能为0
                // 不想写算法优化下去了，偷懒一下
                flag = checkRight(i, j);
                if (flag) {
                    return flag;
                }
                flag = checkDown(i, j);
                if (flag) {
                    return flag;
                }
                flag = checkLeftDown(i, j);
                if (flag) {
                    return flag;
                }
                flag = checkRightDown(i ,j);
                if (flag) {
                    return flag;
                }
            }
        }
        return false;
    }

    private boolean checkRight(int i, int j) {
        if (j < 11) {
            return qiziArr[i][j] != SPACE &&
                    qiziArr[i][j] == qiziArr[i][j+1] &&
                    qiziArr[i][j] == qiziArr[i][j+2] &&
                    qiziArr[i][j] == qiziArr[i][j+3] &&
                    qiziArr[i][j] == qiziArr[i][j+4];
        }
        return false;
    }

    private boolean checkDown(int i, int j) {
        if (i < 11) {
            return qiziArr[i][j] != SPACE &&
                    qiziArr[i][j] == qiziArr[i+1][j] &&
                    qiziArr[i+2][j] == qiziArr[i][j] &&
                    qiziArr[i+3][j] == qiziArr[i][j] &&
                    qiziArr[i+4][j] == qiziArr[i][j];

        }
        return false;
    }

    private boolean checkLeftDown(int i, int j) {
        if (j > 3 && i < 11) {
            return qiziArr[i][j] != SPACE &&
                    qiziArr[i][j] == qiziArr[i+1][j-1] &&
                    qiziArr[i][j] == qiziArr[i+2][j-2] &&
                    qiziArr[i][j] == qiziArr[i+3][j-3] &&
                    qiziArr[i][j] == qiziArr[i+4][j-4];
        }
        return false;
    }

    private boolean checkRightDown(int i, int j) {
        if (i < 11 && j < 11) {
            return qiziArr[i][j] != SPACE &&
                    qiziArr[i][j] == qiziArr[i+1][j+1] &&
                    qiziArr[i][j] == qiziArr[i+2][j+2] &&
                    qiziArr[i][j] == qiziArr[i+3][j+3] &&
                    qiziArr[i][j] == qiziArr[i+4][j+4];
        }
        return false;
    }
}
