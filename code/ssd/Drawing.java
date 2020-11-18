package ssd;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Drawing {

    public static void drawQuad(int x, int y, float width, float height) {
        GL11.glColor3f(0, 255, 255);

        glBegin(GL_QUADS);

        glVertex2f(-x, y);
        glVertex2f(width, y);
        glVertex2f(width, -height);
        glVertex2f(-x, -height);

        glEnd();
    }
}
