package com.example.khet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    Paint paintFill, paintStroke;
    float x, xMid, yMid, xOrigin, yOrigin;
    int turn = 1;
    boolean hasSelectedPiece = false;
    int pieceSelectedI = 0, pieceSelectedJ = 0;
    boolean e, ne, n, nw, w, sw, s, se;

    public int[][] motherBoard = {
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0}
    };


    public MyView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.boardColor));
        paintFill.setColor(getResources().getColor(R.color.boardColor));
        paintStroke.setStrokeWidth(2);
        paintFill.setStyle(Paint.Style.FILL);
        canvas.drawRect((xOrigin-x/2), (yOrigin-x/2), (float)(xOrigin+x*9.5), (float)(yOrigin+x*7.5),paintFill);
        paintStroke.setColor(Color.BLACK);
        paintStroke.setStrokeWidth(4);
        paintStroke.setStyle(Paint.Style.STROKE);
        canvas.drawRect((xOrigin-x/2), (yOrigin-x/2), (float)(xOrigin+x*9.5), (float)(yOrigin+x*7.5),paintStroke);
        printBoxes(canvas);
        paintFill.reset();
        paintFill.setColor(getResources().getColor(R.color.playerRed));
        canvas.drawCircle((int)xOrigin, (int)(yOrigin-x), x/5, paintFill);
        canvas.drawCircle((int)(xOrigin+9*x), (int)(yOrigin+8*x), x/5, paintFill);
        printPieces(canvas);
        paintFill.setColor(Color.BLACK);
        paintFill.setTextSize((float) (x/1.5));
        if(!hasSelectedPiece){
            if(turn == 1) {
                paintFill.setColor(Color.RED);
                canvas.drawText("Player 1 turn", xOrigin + 11 * x, yOrigin, paintFill);
            }if(turn == 2) {
                paintFill.setColor(Color.GRAY);

                canvas.drawText("Player 2 turn", xOrigin + 11 * x, yOrigin, paintFill);
            }
        }
        else{
            canvas.drawText("Select Action", xOrigin + 11 * x, yOrigin, paintFill);
            canvas.drawText("Rotate", xOrigin + 12 * x, (float)(yOrigin+0.7*x), paintFill);
            printActions(canvas);
        }

    }

    private void printActions(Canvas canvas) {
        canvas.drawRect(xOrigin+11*x, yOrigin+x, xOrigin+13*x, yOrigin+2*x, paintStroke);
        canvas.drawRect(xOrigin+13*x, yOrigin+x, xOrigin+15*x, yOrigin+2*x, paintStroke);
        paintFill.setTextSize((float) (x/2.7));

        e = true;
        ne = true;
        n = true;
        nw = true;
        w = true;
        sw = true;
        s = true;
        se = true;
        if(pieceSelectedI == 0){
            n = false;
            ne = false;
            nw = false;


        }
        if (pieceSelectedI == 7){
            s = false;
            sw = false;
            se = false;

        }
        if (pieceSelectedJ == 0){
            w=false;
            nw = false;
            sw = false;

        }
        if(pieceSelectedJ == 9){
            e = false;
            ne = false;
            se = false;

        }
        if(getPiece(motherBoard[pieceSelectedI][pieceSelectedJ])!=4) {
            if (n) {

                if (motherBoard[pieceSelectedI - 1][pieceSelectedJ] != 0)
                    n = false;
            }
            if (s) {

                if (motherBoard[pieceSelectedI + 1][pieceSelectedJ] != 0)
                    s = false;
            }
            if (e) {

                if (motherBoard[pieceSelectedI][pieceSelectedJ+1] != 0)
                    e = false;
            }
            if (w) {

                if (motherBoard[pieceSelectedI][pieceSelectedJ-1] != 0)
                    w = false;
            }
            if (ne) {

                if (motherBoard[pieceSelectedI - 1][pieceSelectedJ+1] != 0)
                    ne = false;
            }
            if (sw) {

                if (motherBoard[pieceSelectedI + 1][pieceSelectedJ-1] != 0)
                    sw = false;
            }
            if (se) {

                if (motherBoard[pieceSelectedI+1][pieceSelectedJ+1] != 0)
                    se = false;
            }
            if (nw) {

                if (motherBoard[pieceSelectedI-1][pieceSelectedJ-1] != 0)
                    nw = false;
            }
        }





        canvas.drawText("90deg CW", xOrigin + 11 * x, (float)(yOrigin+1.7*x), paintFill);
        canvas.drawText("90deg ACW", xOrigin + 13 * x, (float)(yOrigin+1.7*x), paintFill);
        if (e) {
            canvas.drawLine(xOrigin + 13 * x, yOrigin + 5 * x, xOrigin + 15 * x, yOrigin + 5 * x, paintStroke);
            canvas.drawLine(xOrigin + 15 * x, yOrigin + 5 * x, xOrigin + 15 * x - x / 2, yOrigin + 5 * x - x / 2, paintStroke);
            canvas.drawLine(xOrigin + 15 * x, yOrigin + 5 * x, xOrigin + 15 * x - x / 2, yOrigin + 5 * x + x / 2, paintStroke);
        }

        if (w){
        canvas.drawLine(xOrigin+13*x,yOrigin+5*x,xOrigin+11*x,yOrigin+5*x,paintStroke);
        canvas.drawLine(xOrigin+11*x,yOrigin+5*x, xOrigin+11*x+x/2, yOrigin+5*x-x/2,paintStroke);
        canvas.drawLine(xOrigin+11*x,yOrigin+5*x, xOrigin+11*x+x/2, yOrigin+5*x+x/2,paintStroke);}
        //Left right over
        if(n) {
            canvas.drawLine(xOrigin + 13 * x, yOrigin + 3 * x, xOrigin + 13 * x, yOrigin + 5 * x, paintStroke);

            canvas.drawLine(xOrigin + 13 * x, yOrigin + 3 * x, xOrigin + 13 * x - x / 2, yOrigin + 3 * x + x / 2, paintStroke);
            canvas.drawLine(xOrigin + 13 * x, yOrigin + 3 * x, xOrigin + 13 * x + x / 2, yOrigin + 3 * x + x / 2, paintStroke);
        }
        if(s) {
            canvas.drawLine(xOrigin + 13 * x, yOrigin + 5 * x, xOrigin + 13 * x, yOrigin + 7 * x, paintStroke);
            canvas.drawLine(xOrigin + 13 * x, yOrigin + 7 * x, xOrigin + 13 * x - x / 2, yOrigin + 7 * x - x / 2, paintStroke);
            canvas.drawLine(xOrigin + 13 * x, yOrigin + 7 * x, xOrigin + 13 * x + x / 2, yOrigin + 7 * x - x / 2, paintStroke);
            //Top down over
        }
        if(sw) {
            canvas.drawLine(xOrigin + 11 * x, yOrigin + 7 * x, xOrigin + 13 * x, yOrigin + 5 * x, paintStroke);
            canvas.drawLine(xOrigin + 11 * x, yOrigin + 7 * x, xOrigin + 11 * x + x / 2, yOrigin + 7 * x, paintStroke);

            canvas.drawLine(xOrigin + 11 * x, yOrigin + 7 * x, xOrigin + 11 * x, yOrigin + 7 * x - x / 2, paintStroke);
        }
        if(ne) {
            canvas.drawLine(xOrigin + 15 * x, yOrigin + 3 * x, xOrigin + 15 * x, yOrigin + 3 * x + x / 2, paintStroke);
            canvas.drawLine(xOrigin + 13 * x, yOrigin + 5 * x, xOrigin + 15 * x, yOrigin + 3 * x, paintStroke);

            canvas.drawLine(xOrigin + 15 * x, yOrigin + 3 * x, xOrigin + 15 * x - x / 2, yOrigin + 3 * x, paintStroke);
        }
        if (nw) {
            canvas.drawLine(xOrigin + 11 * x, yOrigin + 3 * x, xOrigin + 13 * x, yOrigin + 5 * x, paintStroke);
            canvas.drawLine(xOrigin + 11 * x, yOrigin + 3 * x, xOrigin + 11 * x + x / 2, yOrigin + 3 * x, paintStroke);
            canvas.drawLine(xOrigin + 11 * x, yOrigin + 3 * x, xOrigin + 11 * x, yOrigin + 3 * x + x / 2, paintStroke);
        }
        if(se) {
            canvas.drawLine(xOrigin + 13 * x, yOrigin + 5 * x, xOrigin + 15 * x, yOrigin + 7 * x, paintStroke);
            canvas.drawLine(xOrigin + 15 * x, yOrigin + 7 * x, xOrigin + 15 * x - x / 2, yOrigin + 7 * x, paintStroke);
            canvas.drawLine(xOrigin + 15 * x, yOrigin + 7 * x, xOrigin + 15 * x, yOrigin + 7 * x - x / 2, paintStroke);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        x = (float) (getHeight()/10.0);
        xMid = (float)(getWidth()/2);
        yMid = (float)(getHeight()/2);
        paintFill = new Paint();
        paintStroke = new Paint();
        xOrigin = (float)(xMid-(4.5)*(x)-xMid/4);
        yOrigin = (float)(yMid-(3.5)*(x));
        setClassic();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int j = getXBox(event.getX());
            int i = getYBox(event.getY());
            if(!(i<0||j<0||i>7||j>9))
            {
                if(getCol(motherBoard[i][j]) == turn)
                {
                    hasSelectedPiece = true;
                    pieceSelectedI = i;
                    pieceSelectedJ = j;
                }
            }
            else{
                if ((event.getX()<xOrigin+13*x)&&(event.getX()>xOrigin+11*x)&&(event.getY()<yOrigin+2*x)&&(event.getY()>yOrigin+x)){
                    int dir = getDir(motherBoard[pieceSelectedI][pieceSelectedJ]);
                    int col = getCol(motherBoard[pieceSelectedI][pieceSelectedJ]);
                    int piece = getPiece(motherBoard[pieceSelectedI][pieceSelectedJ]);

                    if(piece == 4){
                        if(dir == 1)
                        dir = 2;
                        else
                            dir = 2;

                    }
                    else{

                        if(dir == 1)
                            dir = 4;
                        else
                            dir = dir-1;
                    }
                    motherBoard[pieceSelectedI][pieceSelectedJ] = dir*100+col*10+piece;
                    hasSelectedPiece = false;
                    if (turn == 1)
                        turn = 2;
                    else
                        turn = 1;
                }
                if ((event.getX()<xOrigin+15*x)&&(event.getX()>xOrigin+13*x)&&(event.getY()<yOrigin+2*x)&&(event.getY()>yOrigin+x)){
                    int dir = getDir(motherBoard[pieceSelectedI][pieceSelectedJ]);
                    int col = getCol(motherBoard[pieceSelectedI][pieceSelectedJ]);
                    int piece = getPiece(motherBoard[pieceSelectedI][pieceSelectedJ]);

                    if(piece == 4){
                        if(dir == 1)
                            dir = 2;
                        else
                            dir = 2;

                    }
                    else{

                        if(dir == 4)
                            dir = 1;
                        else
                            dir = dir+1;
                    }
                    motherBoard[pieceSelectedI][pieceSelectedJ] = dir*100+col*10+piece;
                    hasSelectedPiece = false;
                    if (turn == 1)
                        turn = 2;
                    else
                        turn = 1;
                }

                if((event.getX()>xOrigin+11*x)&&(event.getX()<xOrigin+12*x)&&(event.getY()>yOrigin+3*x)&&(event.getY()<yOrigin+4*x)) {

                    int temp;
                    if (nw) {
                        temp = motherBoard[pieceSelectedI - 1][pieceSelectedJ - 1];
                        motherBoard[pieceSelectedI - 1][pieceSelectedJ - 1] = motherBoard[pieceSelectedI][pieceSelectedJ];
                        if (getPiece(motherBoard[pieceSelectedI][pieceSelectedJ]) == 4)
                            motherBoard[pieceSelectedI][pieceSelectedJ] = temp;
                        else
                            motherBoard[pieceSelectedI][pieceSelectedJ] = 0;
                        if (turn == 1)
                            turn = 2;
                        else
                            turn = 1;

                    }
                }
                if((event.getX()>xOrigin+14*x)&&(event.getX()<xOrigin+15*x)&&(event.getY()>yOrigin+3*x)&&(event.getY()<yOrigin+4*x)) {

                    int temp;
                    if (ne) {
                        temp = motherBoard[pieceSelectedI - 1][pieceSelectedJ + 1];
                        motherBoard[pieceSelectedI - 1][pieceSelectedJ + 1] = motherBoard[pieceSelectedI][pieceSelectedJ];
                        if (getPiece(motherBoard[pieceSelectedI][pieceSelectedJ]) == 4)
                            motherBoard[pieceSelectedI][pieceSelectedJ] = temp;
                        else
                            motherBoard[pieceSelectedI][pieceSelectedJ] = 0;
                        if (turn == 1)
                            turn = 2;
                        else
                            turn = 1;
                    }
                }
                if((event.getX()>xOrigin+11*x)&&(event.getX()<xOrigin+12*x)&&(event.getY()>yOrigin+6*x)&&(event.getY()<yOrigin+7*x)) {

                    int temp;
                    if (sw) {
                        temp = motherBoard[pieceSelectedI + 1][pieceSelectedJ - 1];
                        motherBoard[pieceSelectedI + 1][pieceSelectedJ - 1] = motherBoard[pieceSelectedI][pieceSelectedJ];
                        if (getPiece(motherBoard[pieceSelectedI][pieceSelectedJ]) == 4)
                            motherBoard[pieceSelectedI][pieceSelectedJ] = temp;
                        else
                            motherBoard[pieceSelectedI][pieceSelectedJ] = 0;
                        if (turn == 1)
                            turn = 2;
                        else
                            turn = 1;
                    }
                }
                if((event.getX()>xOrigin+14*x)&&(event.getX()<xOrigin+15*x)&&(event.getY()>yOrigin+6*x)&&(event.getY()<yOrigin+7*x)) {

                    int temp;
                    if (se) {
                        temp = motherBoard[pieceSelectedI + 1][pieceSelectedJ + 1];
                        motherBoard[pieceSelectedI + 1][pieceSelectedJ + 1] = motherBoard[pieceSelectedI][pieceSelectedJ];
                        if (getPiece(motherBoard[pieceSelectedI][pieceSelectedJ]) == 4)
                            motherBoard[pieceSelectedI][pieceSelectedJ] = temp;
                        else
                            motherBoard[pieceSelectedI][pieceSelectedJ] = 0;
                        if (turn == 1)
                            turn = 2;
                        else
                            turn = 1;
                    }
                }
                if((event.getX()>xOrigin+12*x)&&(event.getX()<xOrigin+14*x)&&(event.getY()>yOrigin+3*x)&&(event.getY()<yOrigin+4*x)) {

                    int temp;
                    if (n) {
                        temp = motherBoard[pieceSelectedI - 1][pieceSelectedJ];
                        motherBoard[pieceSelectedI - 1][pieceSelectedJ] = motherBoard[pieceSelectedI][pieceSelectedJ];
                        if (getPiece(motherBoard[pieceSelectedI][pieceSelectedJ]) == 4)
                            motherBoard[pieceSelectedI][pieceSelectedJ] = temp;
                        else
                            motherBoard[pieceSelectedI][pieceSelectedJ] = 0;
                        if (turn == 1)
                            turn = 2;
                        else
                            turn = 1;
                    }
                }
                if((event.getX()>xOrigin+12*x)&&(event.getX()<xOrigin+14*x)&&(event.getY()>yOrigin+6*x)&&(event.getY()<yOrigin+7*x)) {

                    int temp;
                    if (s) {
                        temp = motherBoard[pieceSelectedI + 1][pieceSelectedJ];
                        motherBoard[pieceSelectedI + 1][pieceSelectedJ] = motherBoard[pieceSelectedI][pieceSelectedJ];
                        if (getPiece(motherBoard[pieceSelectedI][pieceSelectedJ]) == 4)
                            motherBoard[pieceSelectedI][pieceSelectedJ] = temp;
                        else
                            motherBoard[pieceSelectedI][pieceSelectedJ] = 0;
                        if (turn == 1)
                            turn = 2;
                        else
                            turn = 1;
                    }
                }
                if((event.getX()>xOrigin+14*x)&&(event.getX()<xOrigin+15*x)&&(event.getY()>yOrigin+4*x)&&(event.getY()<yOrigin+6*x)) {

                    int temp;
                    if (e) {
                        temp = motherBoard[pieceSelectedI][pieceSelectedJ + 1];
                        motherBoard[pieceSelectedI][pieceSelectedJ + 1] = motherBoard[pieceSelectedI][pieceSelectedJ];
                        if (getPiece(motherBoard[pieceSelectedI][pieceSelectedJ]) == 4)
                            motherBoard[pieceSelectedI][pieceSelectedJ] = temp;
                        else
                            motherBoard[pieceSelectedI][pieceSelectedJ] = 0;
                        if (turn == 1)
                            turn = 2;
                        else
                            turn = 1;
                    }
                }
                if((event.getX()>xOrigin+11*x)&&(event.getX()<xOrigin+12*x)&&(event.getY()>yOrigin+4*x)&&(event.getY()<yOrigin+6*x)){

                    int temp;
                    if(w){
                        temp = motherBoard[pieceSelectedI][pieceSelectedJ-1];
                        motherBoard[pieceSelectedI][pieceSelectedJ-1] = motherBoard[pieceSelectedI][pieceSelectedJ];
                        if(getPiece(motherBoard[pieceSelectedI][pieceSelectedJ]) == 4)
                            motherBoard[pieceSelectedI][pieceSelectedJ] = temp;
                        else
                            motherBoard[pieceSelectedI][pieceSelectedJ] = 0;
                        if (turn == 1)
                            turn = 2;
                        else
                            turn = 1;
                    }

                }
                hasSelectedPiece = false;

            }


            invalidate();
            return true;
        }


        return true;
    }



    private void setClassic() {
        motherBoard = new int[][]{
                {0, 0, 0, 0, 212, 211, 212, 213, 0, 0},
                {0, 0, 113, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 423, 0, 0, 0, 0, 0, 0},
                {313, 0, 123, 0, 114, 214, 0, 213, 0, 423},
                {213, 0, 423, 0, 224, 124, 0, 313, 0, 123},
                {0, 0, 0, 0, 0, 0, 213, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 323, 0, 0},
                {0, 0, 423, 222, 221, 222, 0, 0, 0, 0}
        };

    }

    private void printBoxes(Canvas canvas){

        for(int i=0;i<8;i++){
            for(int j = 0;j<10;j++){
                drawBoxes(canvas, i, j);


            }

        }

    }
    private void drawBoxes(Canvas canvas, int i, int j){
        paintFill.setStyle(Paint.Style.FILL);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setColor(Color.BLACK);
        paintFill.setColor(getResources().getColor(R.color.boardColor));

        if(j==0||(j==8&&(i==0||i==7))){
            paintFill.setColor(getResources().getColor(R.color.playerRed));
        }
        if(j==9||(j==1&&(i==0||i==7))){
            paintFill.setColor(getResources().getColor(R.color.playerGray));
        }


        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintFill);
        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintStroke);
    }

    private void printPieces(Canvas canvas){

        for(int i=0;i<8;i++){
            for(int j = 0;j<10;j++){
                if(motherBoard[i][j] == 0){
                    continue;

                }
                int temp = motherBoard[i][j];
                int piece = temp%10;
                temp = temp/10;
                int col = temp%10;
                int dir = temp/10;
                if(piece == 1){
                    drawPharoah(canvas, col, dir, i, j);
                }
                else if(piece == 2){
                    drawObelisk(canvas, col, dir, i, j);
                }
                else if(piece == 3){
                    drawPyramid(canvas, col, dir, i, j);
                }
                else if(piece == 4){
                    drawDjed(canvas, col, dir, i, j);
                }

            }

        }

    }

    private void drawPharoah(Canvas canvas, int col, int dir, int i, int j){

        paintStroke.setColor(Color.BLACK);
        if(col == 1)
        paintFill.setColor(getResources().getColor(R.color.playerRed));
        else
            paintFill.setColor(getResources().getColor(R.color.playerGray));

        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintFill);
        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintStroke);

        if(dir == 2||dir == 4)
        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.25*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.25*x)/2)),paintStroke);
        if(dir == 1||dir == 3)
            canvas.drawRect((float)(xOrigin + j*x -((0.25*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.25*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintStroke);
        if(col == 1)
            paintFill.setColor(getResources().getColor(R.color.playerRedDark));
        else
            paintFill.setColor(getResources().getColor(R.color.playerGrayDark));

        canvas.drawCircle(xOrigin+j*x, yOrigin+i*x, x/4, paintFill);
        canvas.drawCircle(xOrigin+j*x, yOrigin+i*x, x/4, paintStroke);

    }
    private void drawObelisk(Canvas canvas, int col, int dir, int i, int j){

        paintStroke.setColor(Color.BLACK);
        if(col == 1)
            paintFill.setColor(getResources().getColor(R.color.playerRed));
        else
            paintFill.setColor(getResources().getColor(R.color.playerGray));

        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintFill);
        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintStroke);


        if(col == 1)
            paintFill.setColor(getResources().getColor(R.color.playerRedDark));
        else
            paintFill.setColor(getResources().getColor(R.color.playerGrayDark));
        canvas.drawRect((float)(xOrigin + j*x -((0.7*x)/2)), (float)(yOrigin+ i*x-((0.7*x)/2)),(float)(xOrigin + j*x +((0.7*x)/2)),(float)(yOrigin+ i*x+((0.7*x)/2)),paintFill);
        canvas.drawRect((float)(xOrigin + j*x -((0.7*x)/2)), (float)(yOrigin+ i*x-((0.7*x)/2)),(float)(xOrigin + j*x +((0.7*x)/2)),(float)(yOrigin+ i*x+((0.7*x)/2)),paintStroke);



        if(dir == 2){
            paintFill.setColor(getResources().getColor(R.color.stackYellow));
            canvas.drawRect((float)(xOrigin+j*x-(0.5*x)/2),(float)(yOrigin+i*x-(0.5*x)/2),(float)(xOrigin+j*x+(0.5*x)/2),(float)(yOrigin+i*x+(0.5*x)/2),paintFill);
            canvas.drawRect((float)(xOrigin+j*x-(0.5*x)/2),(float)(yOrigin+i*x-(0.5*x)/2),(float)(xOrigin+j*x+(0.5*x)/2),(float)(yOrigin+i*x+(0.5*x)/2),paintStroke);
        }

        canvas.drawLine((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin + i*x -((0.9*x)/2)), (float)(xOrigin + j*x +((0.9*x)/2)), (float)(yOrigin + i*x +((0.9*x)/2)), paintStroke);
        canvas.drawLine((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin + i*x +((0.9*x)/2)), (float)(xOrigin + j*x +((0.9*x)/2)), (float)(yOrigin + i*x -((0.9*x)/2)), paintStroke);
    }

    private void drawPyramid(Canvas canvas, int col, int dir, int i, int j){

        paintStroke.setColor(Color.BLACK);
        if(col == 1)
            paintFill.setColor(getResources().getColor(R.color.playerRed));
        else
            paintFill.setColor(getResources().getColor(R.color.playerGray));

        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintFill);
        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintStroke);

        float currXOrigin = xOrigin+j*x, currYOrigin = yOrigin+i*x, l = (float)(x*0.9);

        Path upperTriangle;
        upperTriangle = new Path();
        upperTriangle.setFillType(Path.FillType.EVEN_ODD);
        if(col == 1)
            paintFill.setColor(getResources().getColor(R.color.playerRedDark));
        else
            paintFill.setColor(getResources().getColor(R.color.playerGrayDark));
        if(dir == 1){

            upperTriangle.moveTo(currXOrigin-l/2, currYOrigin-l/2);
            upperTriangle.lineTo(currXOrigin+l/2, currYOrigin-l/2);
            upperTriangle.lineTo(currXOrigin+l/2, currYOrigin+l/2);
            upperTriangle.lineTo(currXOrigin-l/2, currYOrigin-l/2);
            upperTriangle.close();
            canvas.drawPath(upperTriangle, paintFill);
            canvas.drawLine(currXOrigin-l/2, currYOrigin-l/2, currXOrigin+l/2, currYOrigin+l/2, paintStroke);
            canvas.drawLine(currXOrigin, currYOrigin, currXOrigin+l/2, currYOrigin-l/2, paintStroke);
        }
        else if(dir == 2){

            upperTriangle.moveTo(currXOrigin+l/2, currYOrigin-l/2);
            upperTriangle.lineTo(currXOrigin-l/2, currYOrigin-l/2);
            upperTriangle.lineTo(currXOrigin-l/2, currYOrigin+l/2);
            upperTriangle.lineTo(currXOrigin+l/2, currYOrigin-l/2);
            upperTriangle.close();
            canvas.drawPath(upperTriangle, paintFill);
            canvas.drawLine(currXOrigin+l/2, currYOrigin-l/2, currXOrigin-l/2, currYOrigin+l/2, paintStroke);
            canvas.drawLine(currXOrigin, currYOrigin, currXOrigin-l/2, currYOrigin-l/2, paintStroke);
        }
        else if(dir == 3){

            upperTriangle.moveTo(currXOrigin-l/2, currYOrigin-l/2);
            upperTriangle.lineTo(currXOrigin-l/2, currYOrigin+l/2);
            upperTriangle.lineTo(currXOrigin+l/2, currYOrigin+l/2);
            upperTriangle.lineTo(currXOrigin-l/2, currYOrigin-l/2);
            upperTriangle.close();
            canvas.drawPath(upperTriangle, paintFill);
            canvas.drawLine(currXOrigin+l/2, currYOrigin+l/2, currXOrigin-l/2, currYOrigin-l/2, paintStroke);
            canvas.drawLine(currXOrigin, currYOrigin, currXOrigin-l/2, currYOrigin+l/2, paintStroke);
        }
        else{

            upperTriangle.moveTo(currXOrigin+l/2, currYOrigin-l/2);
            upperTriangle.lineTo(currXOrigin+l/2, currYOrigin+l/2);
            upperTriangle.lineTo(currXOrigin-l/2, currYOrigin+l/2);
            upperTriangle.lineTo(currXOrigin+l/2, currYOrigin-l/2);
            upperTriangle.close();
            canvas.drawPath(upperTriangle, paintFill);
            canvas.drawLine(currXOrigin+l/2, currYOrigin-l/2, currXOrigin-l/2, currYOrigin+l/2, paintStroke);
            canvas.drawLine(currXOrigin, currYOrigin, currXOrigin+l/2, currYOrigin+l/2, paintStroke);
        }


    }

    private void drawDjed(Canvas canvas, int col, int dir, int i, int j){

        paintStroke.setColor(Color.BLACK);
        if(col == 1)
            paintFill.setColor(getResources().getColor(R.color.playerRed));
        else
            paintFill.setColor(getResources().getColor(R.color.playerGray));

        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintFill);
        canvas.drawRect((float)(xOrigin + j*x -((0.9*x)/2)), (float)(yOrigin+ i*x-((0.9*x)/2)),(float)(xOrigin + j*x +((0.9*x)/2)),(float)(yOrigin+ i*x+((0.9*x)/2)),paintStroke);

        float currXOrigin = xOrigin+j*x, currYOrigin = yOrigin+i*x, l = (float)(x*0.9);
        float margin = (float)(l*0.25);
        Path mirror;
        mirror = new Path();
        mirror.setFillType(Path.FillType.EVEN_ODD);
        if(col == 1)
            paintFill.setColor(getResources().getColor(R.color.playerRedDark));
        else
            paintFill.setColor(getResources().getColor(R.color.playerGrayDark));
        if(dir == 1){

            mirror.moveTo(currXOrigin-l/2, currYOrigin-l/2);
            mirror.lineTo(currXOrigin-l/2+ margin, currYOrigin-l/2);
            mirror.lineTo(currXOrigin+l/2, currYOrigin+l/2-margin);
            mirror.lineTo(currXOrigin+l/2, currYOrigin+l/2);
            mirror.lineTo(currXOrigin+l/2-margin, currYOrigin+l/2);
            mirror.lineTo(currXOrigin-l/2, currYOrigin-l/2+ margin);
            mirror.lineTo(currXOrigin-l/2, currYOrigin-l/2);
            mirror.close();
            canvas.drawPath(mirror, paintFill);
            canvas.drawPath(mirror, paintStroke);

        }
        else if(dir == 2){

            mirror.moveTo(currXOrigin+l/2, currYOrigin-l/2);
            mirror.lineTo(currXOrigin+l/2- margin, currYOrigin-l/2);
            mirror.lineTo(currXOrigin-l/2, currYOrigin+l/2-margin);
            mirror.lineTo(currXOrigin-l/2, currYOrigin+l/2);
            mirror.lineTo(currXOrigin-l/2+margin, currYOrigin+l/2);
            mirror.lineTo(currXOrigin+l/2, currYOrigin-l/2+ margin);
            mirror.lineTo(currXOrigin+l/2, currYOrigin-l/2);
            mirror.close();
            canvas.drawPath(mirror, paintFill);
            canvas.drawPath(mirror, paintStroke);

        }


    }

    private int getDir(int n){
        return n/100;

    }

    private int getCol(int n){
        int temp = n;
        temp = temp/10;
        return temp%10;
    }
    private int getPiece(int n){
        return n%10;
    }

    private int getXBox(float X){
        return (int)((X-xOrigin+x/2)/(x));

    }

    private int getYBox(float y) {
        return (int)((y-yOrigin+x/2)/(x));

    }



}
