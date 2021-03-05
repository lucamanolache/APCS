package util.drawing;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    private long window;

    public static void main(String[] args) {
        new Window().run();
    }

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop() {
        GL.createCapabilities();
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        GraphicsSorting.mergeSort(this, new ArrayList<>(List.of(10, 0, 1, 3, 19, 2, 3)));
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
        if ( window == NULL ) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            // TODO: add a start if button clicked
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }

    public void drawArray(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        for (Integer integer : list) {
            max = Math.max(max, integer);
        }

        for (int i = 0; i < list.size(); i++) {
            drawValue(i, list.get(i), max, list.size());
        }
    }

    private void drawValue(int i, int size, int max, int length) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        float x = (float) i / length * 2 - 1;
        glBegin(GL_LINES);
        glVertex2d(x, 0);
        glVertex2d(x + 2.0 / length, 0);
        glEnd();

        glBegin(GL_LINES);
        glVertex2d(x, 0);
        glVertex2d(x, (float) size / max * 2 - 1);
        glEnd();

        glBegin(GL_LINES);
        glVertex2d(x, (float) size / max * 2 - 1);
        glVertex2d(x + 2.0 / length, (float) size / max * 2 - 1);
        glEnd();

        glBegin(GL_LINES);
        glVertex2d(x + 2.0 / length, 0);
        glVertex2d(x + 2.0 / length, (float) size / max * 2 - 1);
        glEnd();

        glfwSwapBuffers(window);
        glfwPollEvents();
    }
}
