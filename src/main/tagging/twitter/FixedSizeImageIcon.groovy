package tagging.twitter;
import javax.swing.*;
import java.net.URL;
public class FixedSizeImageIcon extends ImageIcon {
    final int w;
    final int h;

    public FixedSizeImageIcon(int h, int w, URL url) {
        super(url);
        this.w = w;
        this.h = h;
    }

    @Override
    public int getIconWidth() {
        return w;
    }

    @Override
    public int getIconHeight() {
        return h;
    }
}