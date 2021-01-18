package ssd;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import static ssd.Drawing.*;


public class Drawer {

    static float xborder = 0.55f;
    static float yborder = 0.1f;
    static float inset = 0.3f;
    static float gap = 0.075f;
    static float thickness = 0.05f;
    static float xsize = 2 - 2 * xborder - 2 * gap;
    static float ysize = (2 - inset - 2 * yborder - 3 * gap - thickness) / 2;
    static float cornerRadius = 0.03f;

    private Texture backgroundTexture;

    private SSD ssd;

    public Drawer() {
        this.ssd = new CathodeSSD();
        this.ssd.set(0);
    }

    public void draw(long window) {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);

        backgroundTexture = new Texture("./code/ssd/brick.jpg");

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            updateKeys(window);
            drawBackground();
            drawSSD(ssd);
            drawLighting();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void drawBackground() {
//        backgroundTexture.bind();

//        glColor3b((byte) 101, (byte) 56, (byte) 24);
//        glBegin(GL_QUADS);
//            glTexCoord2f(0, 0);
//            glVertex2f(-1, 1);
//
//            glTexCoord2f(1, 0);
//            glVertex2f(1, 1);
//
//            glTexCoord2f(1, 1);
//            glVertex2f(1, -1);
//
//            glTexCoord2f(0, 1);
//            glVertex2f(-1, -1);
//        glEnd();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    private void drawLighting() {
        FloatBuffer ambient = BufferUtils.createFloatBuffer(4);
        ambient.put(new float[] { 0f, 0f, 0f, 0f, });
        ambient.flip();

        FloatBuffer position = BufferUtils.createFloatBuffer(4);
        position.put(new float[] { 0f, 0f, 0.025f, 0.05f, });
        position.flip();

        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glLightModelfv(GL_LIGHT_MODEL_AMBIENT, ambient);
//        glLightfv(GL_LIGHT0, GL_POSITION, position);
    }

    private void drawSSD(SSD ssd) {
        // TODO: try to make it so that you can render multiple SSDs
        boolean[] segments = ssd.getSegments();
        for (int i = 0; i < segments.length; i++) {
            if (segments[i]) {
                GL11.glColor3f(1.0f, 0.35f, 0.22f);
            } else {
                GL11.glColor3f(0.24f, 0.1f, 0.06f);
            }

            switch (i) {
                case 0:
                    drawQuad(-1 + xborder + gap, 1 - yborder - thickness, xsize, thickness, cornerRadius);
                    break;
                case 1:
                    drawQuad(1 - xborder - thickness, inset + yborder + 2 * gap + ysize - 1 + thickness, thickness, ysize, cornerRadius);
                    break;
                case 2:
                    drawQuad(1 - xborder - thickness, inset + yborder + gap - 1, thickness, ysize, cornerRadius);
                    break;
                case 3:
                    drawQuad(-1 + xborder + gap, inset + yborder - 1, xsize, thickness, cornerRadius);
                    break;
                case 4:
                    drawQuad(-1 + xborder, inset + yborder + gap - 1, thickness, ysize, cornerRadius);
                    break;
                case 5:
                    drawQuad(-1 + xborder, inset + yborder + 2 * gap + ysize - 1 + thickness, thickness, ysize, cornerRadius);
                    break;
                case 6:
                    drawQuad(-1 + xborder + gap, 0.5f * inset - 0.5f * thickness, xsize, thickness, cornerRadius);
                    break;
            }
        }
    }

    private void updateKeys(long window) {
        // TODO: find a nicer way to do this!
        if (glfwGetKey(window, GLFW_KEY_0) == 1) ssd.set(0);
        if (glfwGetKey(window, GLFW_KEY_1) == 1) ssd.set(1);
        if (glfwGetKey(window, GLFW_KEY_2) == 1) ssd.set(2);
        if (glfwGetKey(window, GLFW_KEY_3) == 1) ssd.set(3);
        if (glfwGetKey(window, GLFW_KEY_4) == 1) ssd.set(4);
        if (glfwGetKey(window, GLFW_KEY_5) == 1) ssd.set(5);
        if (glfwGetKey(window, GLFW_KEY_6) == 1) ssd.set(6);
        if (glfwGetKey(window, GLFW_KEY_7) == 1) ssd.set(7);
        if (glfwGetKey(window, GLFW_KEY_8) == 1) ssd.set(8);
        if (glfwGetKey(window, GLFW_KEY_9) == 1) ssd.set(9);
        if (glfwGetKey(window, GLFW_KEY_0) == 1) ssd.set(0);
    }
}
