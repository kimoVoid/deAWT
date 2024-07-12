package me.kimovoid.deawt;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;
import net.minecraft.client.crash.CrashPanel;
import net.minecraft.client.crash.CrashSummary;
import net.minecraft.client.render.Window;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Most of this stuff was ported from <a href="https://modrinth.com/mod/gambac">Gambac</a>
 * Credits to DanyGames2014
 */
public class NoAWTMinecraft extends Minecraft {

    private final int previousWidth;
    private final int previousHeight;
    private final Frame awtFrame;

    public NoAWTMinecraft(Component component, Canvas canvas, MinecraftApplet minecraftApplet, int w, int h, boolean fullscreen) {
        super(null, null, null, w, h, fullscreen);
        this.previousWidth = w;
        this.previousHeight = h;
        this.awtFrame = new Frame("Minecraft");
    }

    @Override
    public void printCrashReport(CrashSummary crashSummary) {
        this.awtFrame.removeAll();
        this.awtFrame.add(new CrashPanel(crashSummary), "Center");
        this.awtFrame.validate();
        this.awtFrame.setSize(this.width, this.height);
        this.awtFrame.setLocationRelativeTo(null);
        this.awtFrame.setAutoRequestFocus(true);
        this.awtFrame.addWindowListener(new WindowAdapter() {
                                           public void windowClosing(WindowEvent we) {
                                               awtFrame.dispose();
                                               System.exit(1);
                                           }
                                       }
        );
        this.awtFrame.setVisible(true);
    }

    @Override
    public void tick() {
        if (GL11.glGetString(GL11.GL_RENDERER).contains("Apple M")) {
            GL11.glEnable(GL30.GL_FRAMEBUFFER_SRGB);
        }
        if (Display.getWidth() != this.width || Display.getHeight() != this.height) {
            this.onResolutionChanged(Display.getWidth(), Display.getHeight());
        }

        super.tick();
    }

    @Override
    public void toggleFullscreen() {
        final Object fullscreen_b;
        boolean isFullscreen;
        try {
            fullscreen_b = this.getClass().getDeclaredField("fullscreen").get(this);
            isFullscreen = (boolean) fullscreen_b;
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
            isFullscreen = Display.isFullscreen();
        }
        try {
            isFullscreen = !isFullscreen;
            if (isFullscreen) {
                this.width = Display.getWidth();
                this.height = Display.getHeight();

                Display.setDisplayMode(Display.getDesktopDisplayMode());
                this.width = Display.getDisplayMode().getWidth();
                this.height = Display.getDisplayMode().getHeight();
            } else {
                this.width = this.previousWidth;
                this.height = this.previousHeight;
                Display.setDisplayMode(new DisplayMode(this.width, this.height));
            }
            if (this.width <= 0) {
                this.width = 1;
            }
            if (this.height <= 0) {
                this.height = 1;
            }

            if (this.screen != null) {
                this.onResolutionChanged(this.width, this.height);
            }

            Display.setFullscreen(isFullscreen);
            Display.update();
        } catch (Exception ignored) {}

        try {
            this.getClass().getDeclaredField("fullscreen").set(this, isFullscreen);
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
        }
    }

    private void onResolutionChanged(int w, int h) {
        if (w <= 0) {
            w = 1;
        }
        if (h <= 0) {
            h = 1;
        }
        this.width = w;
        this.height = h;
        if (this.screen != null) {
            Window scaled = new Window(this.options, w, h);
            int scaledWidth = scaled.getWidth();
            int scaledHeight = scaled.getHeight();
            this.screen.init(this, scaledWidth, scaledHeight);
        }
    }
}
