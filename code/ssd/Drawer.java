package ssd;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

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

    private SSD ssd = new CathodeSSD();

    public void draw(long window) {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            ssd.set(3);

            drawBackground();
            drawSSD(ssd);

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void drawBackground() {
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    private void drawSSD(SSD ssd) {
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
                    drawQuad(1 - xborder - thickness, 1 - yborder - gap, thickness, -ysize, cornerRadius);
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
                    drawQuad(-1 + xborder, 1 - yborder - gap, thickness, -ysize, cornerRadius);
                    break;
                case 6:
                    drawQuad(-1 + xborder + gap, 0.5f * inset - 0.5f * thickness, xsize, thickness, cornerRadius);
                    break;
            }
        }
    }
}
