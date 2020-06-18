package me.hydrokel.client.Utils;

public class MouseUtil {

    public static boolean mouseWithinBounds(int mouseX, int mouseY, int x, int y, int x1, int y1) {
        return (mouseX >= x && mouseX <= x1) && (mouseY >= y && mouseY <= y1);
    }

}
