package ssd;

import static org.lwjgl.opengl.GL11.*;

public class Drawing {

    private static int quality = 100;

    private static float convertX(float x) {
        return Controller.WINDOW_LENGTH;
    }

    public static void drawQuad(float x, float y, float width, float height) {
        glBegin(GL_QUADS);

        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);

        glEnd();
    }

    public static void drawQuad(float x, float y, float width, float height, float cornerRadius) {
        if (cornerRadius == 0) {
            glBegin(GL_QUADS);

            glVertex2f(x, y);
            glVertex2f(x + width, y);
            glVertex2f(x + width, y + height);
            glVertex2f(x, y + height);

            glEnd();
        } else {
            float doubleRadius = cornerRadius * 2;
            if(doubleRadius > width) {
                doubleRadius = width;
                cornerRadius = doubleRadius / 2;
            }
            if(doubleRadius > height) {
                doubleRadius = height;
                cornerRadius = doubleRadius / 2;
            }

            glBegin(GL_TRIANGLE_FAN);

            drawCurve(cornerRadius, x + cornerRadius, y + cornerRadius, 180, 270);
            drawCurve(cornerRadius, x + width - cornerRadius, y + cornerRadius, 270, 360);
            drawCurve(cornerRadius, x + width - cornerRadius, y + height - cornerRadius, 0, 90);
            drawCurve(cornerRadius, x + cornerRadius, y + height - cornerRadius, 90, 180);

            glEnd();
        }
    }

    public static void drawCurve(float radius, float cx, float cy, float start, float end) {

        int step = 360 / quality;
        for (float a = start; a <= end + step; a += step) {
            float angle = a;
            if (angle > end) {
                angle = end;
            }
            float x = (float) (cx + (Math.cos(Math.toRadians(angle)) * radius));
            float y = (float) (cy + (Math.sin(Math.toRadians(angle)) * radius));

            glVertex2f(x, y);
        }
    }
}
