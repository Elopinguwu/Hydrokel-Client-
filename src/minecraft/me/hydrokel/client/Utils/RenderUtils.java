package me.hydrokel.client.Utils;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderUtils {
    ///////////////////////////2D//////////////////////////////////
    public static void enableGL2D() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    public static void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static void setupFor2D(EntityLivingBase entity, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslated((float)entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - RenderManager.renderPosX, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - RenderManager.renderPosY, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - RenderManager.renderPosZ);
        GL11.glRotated(-((float)Minecraft.getMinecraft().thePlayer.rotationYaw), 0.0F, 1.0F, 0.0F);
        GL11.glRotated(((float)Minecraft.getMinecraft().thePlayer.rotationPitch), 1.0F, 0.0F, 0.0F);
        GL11.glScaled(-0.018F, -0.018F, 0.018F);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
    }

    public static void disableFor2D() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    public static void drawRect(Rectangle rectangle, int color) {
        drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, color);
    }

    public static void drawRect2(double x, double y, double width, double height, int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
    }

    public static void drawRect(float x2, float y2, float x1, float y1, int color) {
        enableGL2D();
        glColor(color);
        drawRect(x2, y2, x1, y1);
        disableGL2D();
    }

    public static void drawBorderedRect(float x2, float y2, float x1, float y1, float width, int internalColor, int borderColor) {
        enableGL2D();
        glColor(internalColor);
        drawRect(x2 + width, y2 + width, x1 - width, y1 - width);
        glColor(borderColor);
        drawRect(x2 + width, y2, x1 - width, y2 + width);
        drawRect(x2, y2, x2 + width, y1);
        drawRect(x1 - width, y2, x1, y1);
        drawRect(x2 + width, y1 - width, x1 - width, y1);
        disableGL2D();
    }

    public static void drawBorderedRect(float x2, float y2, float x1, float y1, int insideC, int borderC) {
        enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        drawVLine(x2 *= 2.0f, y2 *= 2.0f, y1 *= 2.0f, borderC);
        drawVLine((x1 *= 2.0f) - 1.0f, y2, y1, borderC);
        drawHLine(x2, x1 - 1.0f, y2, borderC);
        drawHLine(x2, x1 - 2.0f, y1 - 1.0f, borderC);
        drawRect(x2 + 1.0f, y2 + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        disableGL2D();
    }

    public static void drawBorderedRectReliant(float x2, float y2, float x1, float y1, float lineWidth, int inside, int border) {
        enableGL2D();
        drawRect(x2, y2, x1, y1, inside);
        glColor(border);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBegin((int)3);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glVertex2f((float)x2, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y2);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        disableGL2D();
    }

    public static void drawGradientBorderedRectReliant(float x2, float y2, float x1, float y1, float lineWidth, int border, int bottom, int top) {
        enableGL2D();
        drawGradientRect(x2, y2, x1, y1, top, bottom);
        glColor(border);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBegin((int)3);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glVertex2f((float)x2, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y2);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        disableGL2D();
    }

    public static void drawRoundedRect(float x2, float y2, float x1, float y1, int borderC, int insideC) {
        enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        drawVLine(x2 *= 2.0f, (y2 *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
        drawVLine((x1 *= 2.0f) - 1.0f, y2 + 1.0f, y1 - 2.0f, borderC);
        drawHLine(x2 + 2.0f, x1 - 3.0f, y2, borderC);
        drawHLine(x2 + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
        drawHLine(x2 + 1.0f, x2 + 1.0f, y2 + 1.0f, borderC);
        drawHLine(x1 - 2.0f, x1 - 2.0f, y2 + 1.0f, borderC);
        drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
        drawHLine(x2 + 1.0f, x2 + 1.0f, y1 - 2.0f, borderC);
        drawRect(x2 + 1.0f, y2 + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        disableGL2D();
    }

    public static void drawBorderedRect(Rectangle rectangle, float width, int internalColor, int borderColor) {
        float x2 = rectangle.x;
        float y2 = rectangle.y;
        float x22 = rectangle.x + rectangle.width;
        float y22 = rectangle.y + rectangle.height;
        enableGL2D();
        glColor(internalColor);
        drawRect(x2 + width, y2 + width, x22 - width, y22 - width);
        glColor(borderColor);
        drawRect(x2 + 1.0f, y2, x22 - 1.0f, y2 + width);
        drawRect(x2, y2, x2 + width, y22);
        drawRect(x22 - width, y2, x22, y22);
        drawRect(x2 + 1.0f, y22 - width, x22 - 1.0f, y22);
        disableGL2D();
    }

    public static void drawGradientRect(float x2, float y2, float x1, float y1, int topColor, int bottomColor) {
        enableGL2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        glColor(topColor);
        GL11.glVertex2f((float)x2, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        glColor(bottomColor);
        GL11.glVertex2f((float)x1, (float)y2);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        disableGL2D();
    }

    public static void drawGradientHRect(float x2, float y2, float x1, float y1, int topColor, int bottomColor) {
        enableGL2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        glColor(topColor);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glVertex2f((float)x2, (float)y1);
        glColor(bottomColor);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y2);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        disableGL2D();
    }

    public static void drawGradientRect(double x2, double y2, double x22, double y22, int col1, int col2) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GL11.glBegin((int)7);
        glColor(col1);
        GL11.glVertex2d((double)x22, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        glColor(col2);
        GL11.glVertex2d((double)x2, (double)y22);
        GL11.glVertex2d((double)x22, (double)y22);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
    }

    public static void drawGradientBorderedRect(double x2, double y2, double x22, double y22, float l1, int col1, int col2, int col3) {
        enableGL2D();
        GL11.glPushMatrix();
        glColor(col1);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y22);
        GL11.glVertex2d((double)x22, (double)y22);
        GL11.glVertex2d((double)x22, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x22, (double)y2);
        GL11.glVertex2d((double)x2, (double)y22);
        GL11.glVertex2d((double)x22, (double)y22);
        GL11.glEnd();
        GL11.glPopMatrix();
        drawGradientRect(x2, y2, x22, y22, col2, col3);
        disableGL2D();
    }

    public static void glColor(Color color) {
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 255) / 255.0f;
        float red = (float)(hex >> 16 & 255) / 255.0f;
        float green = (float)(hex >> 8 & 255) / 255.0f;
        float blue = (float)(hex & 255) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
        float red = 0.003921569f * (float)redRGB;
        float green = 0.003921569f * (float)greenRGB;
        float blue = 0.003921569f * (float)blueRGB;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void drawStrip(int x2, int y2, float width, double angle, float points, float radius, int color) {
        float a2;
        int i2;
        float yc2;
        float xc2;
        float f1 = (float)(color >> 24 & 255) / 255.0f;
        float f2 = (float)(color >> 16 & 255) / 255.0f;
        float f3 = (float)(color >> 8 & 255) / 255.0f;
        float f4 = (float)(color & 255) / 255.0f;
        GL11.glPushMatrix();
        GL11.glTranslated((double)x2, (double)y2, (double)0.0);
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f1);
        GL11.glLineWidth((float)width);
        if (angle > 0.0) {
            GL11.glBegin((int)3);
            i2 = 0;
            while ((double)i2 < angle) {
                a2 = (float)((double)i2 * (angle * 3.141592653589793 / (double)points));
                xc2 = (float)(Math.cos(a2) * (double)radius);
                yc2 = (float)(Math.sin(a2) * (double)radius);
                GL11.glVertex2f((float)xc2, (float)yc2);
                ++i2;
            }
            GL11.glEnd();
        }
        if (angle < 0.0) {
            GL11.glBegin((int)3);
            i2 = 0;
            while ((double)i2 > angle) {
                a2 = (float)((double)i2 * (angle * 3.141592653589793 / (double)points));
                xc2 = (float)(Math.cos(a2) * (double)(- radius));
                yc2 = (float)(Math.sin(a2) * (double)(- radius));
                GL11.glVertex2f((float)xc2, (float)yc2);
                --i2;
            }
            GL11.glEnd();
        }
        disableGL2D();
        GL11.glDisable((int)3479);
        GL11.glPopMatrix();
    }

    public static void drawHLine(float x2, float y2, float x1, int y1) {
        if (y2 < x2) {
            float var5 = x2;
            x2 = y2;
            y2 = var5;
        }
        drawRect(x2, x1, y2 + 1.0f, x1 + 1.0f, y1);
    }

    public static void drawVLine(float x2, float y2, float x1, int y1) {
        if (x1 < y2) {
            float var5 = y2;
            y2 = x1;
            x1 = var5;
        }
        drawRect(x2, y2 + 1.0f, x2 + 1.0f, x1, y1);
    }

    public static void drawHLine(float x2, float y2, float x1, int y1, int y22) {
        if (y2 < x2) {
            float var5 = x2;
            x2 = y2;
            y2 = var5;
        }
        drawGradientRect(x2, x1, y2 + 1.0f, x1 + 1.0f, y1, y22);
    }

    public static void drawRect(float x2, float y2, float x1, float y1, float r2, float g2, float b2, float a2) {
        enableGL2D();
        GL11.glColor4f((float)r2, (float)g2, (float)b2, (float)a2);
        drawRect(x2, y2, x1, y1);
        disableGL2D();
    }

    public static void drawRect(float x2, float y2, float x1, float y1) {
        GL11.glBegin((int)7);
        GL11.glVertex2f((float)x2, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y2);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glEnd();
    }

    public static void drawCircle(float cx, float cy2, float r2, int num_segments, int c2) {
        cx *= 2.0f;
        cy2 *= 2.0f;
        float f2 = (float)(c2 >> 24 & 255) / 255.0f;
        float f22 = (float)(c2 >> 16 & 255) / 255.0f;
        float f3 = (float)(c2 >> 8 & 255) / 255.0f;
        float f4 = (float)(c2 & 255) / 255.0f;
        float theta = (float)(6.2831852 / (double)num_segments);
        float p2 = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x2 = r2 *= 2.0f;
        float y2 = 0.0f;
        enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GL11.glColor4f((float)f22, (float)f3, (float)f4, (float)f2);
        GL11.glBegin((int)2);
        int ii2 = 0;
        while (ii2 < num_segments) {
            GL11.glVertex2f((float)(x2 + cx), (float)(y2 + cy2));
            float t = x2;
            x2 = p2 * x2 - s * y2;
            y2 = s * t + p2 * y2;
            ++ii2;
        }
        GL11.glEnd();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        disableGL2D();
    }

    public static void drawFullCircle(int cx, int cy2, double r2, int c2) {
        r2 *= 2.0;
        cx *= 2;
        cy2 *= 2;
        float f2 = (float)(c2 >> 24 & 255) / 255.0f;
        float f22 = (float)(c2 >> 16 & 255) / 255.0f;
        float f3 = (float)(c2 >> 8 & 255) / 255.0f;
        float f4 = (float)(c2 & 255) / 255.0f;
        enableGL2D();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        GL11.glColor4f((float)f22, (float)f3, (float)f4, (float)f2);
        GL11.glBegin((int)6);
        int i2 = 0;
        while (i2 <= 360) {
            double x2 = Math.sin((double)i2 * 3.141592653589793 / 180.0) * r2;
            double y2 = Math.cos((double)i2 * 3.141592653589793 / 180.0) * r2;
            GL11.glVertex2d((double)((double)cx + x2), (double)((double)cy2 + y2));
            ++i2;
        }
        GL11.glEnd();
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        disableGL2D();
    }

    public static void drawSmallString(String s, int x2, int y2, int color) {
        GL11.glPushMatrix();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawString(s, x2 * 2, y2 * 2, color);
        GL11.glPopMatrix();
    }

    public static void drawLargeString(String text, int x2, int y2, int color) {
        GL11.glPushMatrix();
        GL11.glScalef((float)1.5f, (float)1.5f, (float)1.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x2 *= 2, y2, color);
        GL11.glPopMatrix();
    }
    ///////////////////////////3D//////////////////////////////////
    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ);
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ);
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
    }

    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ);
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ);
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ);
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ);
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ);
        tessellator.draw();
    }

    public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)lineWidth);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        RenderUtils.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glLineWidth((float)lineWidth);
        GL11.glColor4f((float)lineRed, (float)lineGreen, (float)lineBlue, (float)lineAlpha);
        RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        RenderUtils.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawSolidEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        RenderUtils.drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        RenderUtils.drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glLineWidth((float)lineWdith);
        GL11.glColor4f((float)lineRed, (float)lineGreen, (float)lineBlue, (float)lineAlpha);
        RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)lineWdith);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glBegin((int)2);
        GL11.glVertex3d((double)0.0, (double)(0.0 + (double)Minecraft.getMinecraft().thePlayer.getEyeHeight()), (double)0.0);
        GL11.glVertex3d((double)x, (double)y, (double)z);
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawCircle(int x, int y, double r, int c) {
        float f = (float)(c >> 24 & 255) / 255.0f;
        float f1 = (float)(c >> 16 & 255) / 255.0f;
        float f2 = (float)(c >> 8 & 255) / 255.0f;
        float f3 = (float)(c & 255) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glBegin((int)2);
        int i = 0;
        while (i <= 360) {
            double x2 = Math.sin((double)i * 3.141592653589793 / 180.0) * r;
            double y2 = Math.cos((double)i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d((double)((double)x + x2), (double)((double)y + y2));
            ++i;
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void drawFilledCircle(int x, int y, double r, int c) {
        float f = (float)(c >> 24 & 255) / 255.0f;
        float f1 = (float)(c >> 16 & 255) / 255.0f;
        float f2 = (float)(c >> 8 & 255) / 255.0f;
        float f3 = (float)(c & 255) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glBegin((int)6);
        int i = 0;
        while (i <= 360) {
            double x2 = Math.sin((double)i * 3.141592653589793 / 180.0) * r;
            double y2 = Math.cos((double)i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d((double)((double)x + x2), (double)((double)y + y2));
            ++i;
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void dr(double i, double j, double k, double l, int i1) {
        if (i < k) {
            double j1 = i;
            i = k;
            k = j1;
        }
        if (j < l) {
            double k1 = j;
            j = l;
            l = k1;
        }
        float f = (float)(i1 >> 24 & 255) / 255.0f;
        float f1 = (float)(i1 >> 16 & 255) / 255.0f;
        float f2 = (float)(i1 >> 8 & 255) / 255.0f;
        float f3 = (float)(i1 & 255) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(i, l, 0.0);
        worldRenderer.pos(k, l, 0.0);
        worldRenderer.pos(k, j, 0.0);
        worldRenderer.pos(i, j, 0.0);
        tessellator.draw();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }
    public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        tessellator.draw();// Ends X.
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        tessellator.draw();// Ends Y.
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
                .color(red, green, blue, alpha).endVertex();
        tessellator.draw();
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ)
                .endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ)
                .endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ)
                .endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ)
                .endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ)
                .endVertex();
        tessellator.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ)
                .endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ)
                .endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ)
                .endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ)
                .endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ)
                .endVertex();
        tessellator.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION);
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ)
                .endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ)
                .endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ)
                .endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ)
                .endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ)
                .endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ)
                .endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ)
                .endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ)
                .endVertex();
        tessellator.draw();
    }

    public static void blockESPOutline(BlockPos blockPos)
    {
        double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4d(0, 0.0, 0.0, 0F);
        drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 0F, 1F, 0F, 0.15F);
        GL11.glColor4d(1, 0.0, 0.0, 1F);
        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void blockESPSolid(BlockPos blockPos)
    {
        double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4d(1, 0.0, 0.0, 1F);
        drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 0F, 1F, 0F, 0.15F);
        GL11.glColor4d(0, 0.0, 0.0, 0F);
        drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
    }

    public static void drawSkinHead(String username) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        try {
            AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(username), username).loadTexture(Minecraft.getMinecraft().getResourceManager());
            Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(username));
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            double x = 0;
            double y = 0;
            double h = 32;
            double w = 32;
            double fw = 20;
            double fh = 20;
            double u = 32;
            double v = 32;
            GlStateManager.translate(5, 5, 0);
            GlStateManager.scale(32 / fw, 32 / fw, 0);
            Gui.drawTexturedModalRect(x, y, u, v, w, h);
            u = 160;
            v = 32;
            Gui.drawTexturedModalRect(x, y, u, v, w, h);
            GL11.glPopMatrix();
            GL11.glDisable(GL11.GL_BLEND);
        } catch (IOException e) {
        }
    }

    public static float[] getRGBAs(int rgb) {
        return new float[] { ((rgb >> 16) & 255) / 255F, ((rgb >> 8) & 255) / 255F, (rgb & 255) / 255F,
                ((rgb >> 24) & 255) / 255F };
    }

    public static int getRainbow(int speed, int offset) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.getHSBColor(hue, 0.75f, 1f).getRGB();

    }

    public static Vec3 getRenderPos(double x, double y, double z) {

        x = x - Minecraft.getMinecraft().getRenderManager().renderPosX;
        y = y - Minecraft.getMinecraft().getRenderManager().renderPosY;
        z = z - Minecraft.getMinecraft().getRenderManager().renderPosZ;

        return new Vec3(x, y, z);
    }

    public static void putVertex3d(Vec3 vec) {
        GL11.glVertex3d(vec.xCoord, vec.yCoord, vec.zCoord);
    }

}

