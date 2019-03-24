package vn.edu.hcmut.linexo.presentation.view.play;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import vn.edu.hcmut.linexo.R;
import vn.edu.hcmut.linexo.presentation.model.Board;

public class BoardView extends View {

    /**
     * Width and height of {@link BoardView} in pixel.
     * This size has subtracted the {@link #lineStrokeWidth}.
     */
    private int width;
    private int height;

    private int lineStrokeWidth;

    private Paint line;
    private Paint lineBlur;

    private Drawable signX;
    private Drawable signO;
    private Drawable stone;

    private Board board;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BoardView(Context context) {
        super(context);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init() {

        lineStrokeWidth = getResources().getDimensionPixelSize(R.dimen.dimen_line_stroke);

        line = new Paint();
        line.setAntiAlias(true);
        line.setColor(getContext().getColor(R.color.colorLine));
        line.setStrokeWidth(lineStrokeWidth);
        line.setStrokeCap(Paint.Cap.ROUND);

        lineBlur = new Paint();
        lineBlur.setAntiAlias(true);
        lineBlur.setColor(getContext().getColor(R.color.colorLineBlur));
        lineBlur.setStrokeWidth(lineStrokeWidth);
        lineBlur.setStrokeCap(Paint.Cap.ROUND);

        signX = getContext().getDrawable(R.drawable.ic_x);
        signO = getContext().getDrawable(R.drawable.ic_o);
        stone = new GradientDrawable();
        ((GradientDrawable)stone).setShape(GradientDrawable.RECTANGLE);
        ((GradientDrawable)stone).setColor(getContext().getColor(R.color.colorLine));
        ((GradientDrawable)stone).setCornerRadius(lineStrokeWidth / 2);

        board = new Board(new byte[11][19]);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width   = getMeasuredWidth() - 2 * lineStrokeWidth;
        height  = getMeasuredHeight() - 2 * lineStrokeWidth;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getX() > 0 && event.getX() < getMeasuredWidth()
                    && event.getY() > 0 && event.getY() < getMeasuredHeight()) {
                // get touch coordinates
                float x = event.getX() - lineStrokeWidth;
                float y = event.getY() - lineStrokeWidth;
                // convert to board coordinates
                x = x * (board.getWidth() - 1) / width;
                y = y * (board.getHeight() - 1) / height;
                // change coordinate systems - rotate 45 degrees
                // M = [[.5 -.5],[.5 .5]]
                // b = (M^T)^-1 * a
                float u = x - y;
                float v = x + y;
                // find the line coordinates
                u = (int)Math.abs(u) % 2 == 1
                        ? (int)u
                        : (u > 0) ? (int)u + 1 : (int)u - 1;
                v = (int)Math.abs(v) % 2 == 1
                        ? (int)v
                        : (v > 0) ? (int)v + 1 : (int)v - 1;
                // restore coordinate systems
                // a = (M^T) * b
                x = (u + v) / 2;
                y = (v - u) / 2;
                this.x = (int)x;
                this.y = (int)y;
                invalidate();
            }
        }
        return true;
    }

    int x = 0, y = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int y = 0; y <= 10; ++y) {
            for (int x = 1 - (y % 2); x <= 18; x+=2) {
                drawLine(x, y, canvas, lineBlur);
            }
        }
        drawSign(3, 3, canvas, signX);

        drawSign(5, 5, canvas, signO);
        drawLine(x, y, canvas, line);
        drawStone(5, 7, canvas, stone);
    }

    private void drawLine(int x, int y, Canvas canvas, Paint line) {
        int startX = x;
        int startY = y;
        int stopX  = x;
        int stopY  = y;
        if ((x % 2 == 0) && (y % 2 == 1)) {
            --startY;
            ++stopY;
        }else if ((x % 2 == 1) && (y % 2 == 0)) {
            --startX;
            ++stopX;
        }
        canvas.drawLine(
                (float)(lineStrokeWidth + startX * width / (board.getWidth() - 1)),
                (float)(lineStrokeWidth + startY * height / (board.getHeight() - 1)),
                (float)(lineStrokeWidth + stopX * width / (board.getWidth() - 1)),
                (float)(lineStrokeWidth + stopY * height / (board.getHeight() - 1)),
                line
        );
    }

    private void drawSign(int x, int y, Canvas canvas, Drawable sign) {
        sign.setBounds(
                (int)(lineStrokeWidth + (x - 0.65) * width / (board.getWidth() - 1)),
                (int)(lineStrokeWidth + (y - 0.65) * height / (board.getHeight() - 1)),
                (int)(lineStrokeWidth + (x + 0.65) * width / (board.getWidth() - 1)),
                (int)(lineStrokeWidth + (y + 0.65) * height / (board.getHeight() - 1))
        );
        sign.draw(canvas);
    }

    private void drawStone(int x, int y, Canvas canvas, Drawable stone) {
        stone.setBounds(
                lineStrokeWidth + (x - 1) * width / (board.getWidth() - 1) - lineStrokeWidth / 2,
                lineStrokeWidth + (y - 1) * height / (board.getHeight() - 1) - lineStrokeWidth / 2,
                lineStrokeWidth + (x + 1) * width / (board.getWidth() - 1) + lineStrokeWidth / 2,
                lineStrokeWidth + (y + 1) * height / (board.getHeight() - 1) + lineStrokeWidth / 2
        );
        stone.draw(canvas);
    }
 }
