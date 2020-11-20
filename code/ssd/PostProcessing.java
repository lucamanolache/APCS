package ssd;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class PostProcessing {

    private int frameBuffer;

    public PostProcessing() {
        frameBuffer = GL30.glGenFramebuffers();
        GL30.glBindBuffer(GL30.GL_FRAMEBUFFER, frameBuffer);


    }
}
