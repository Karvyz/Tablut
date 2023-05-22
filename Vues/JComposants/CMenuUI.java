package Vues.JComposants;

import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuUI;
import java.awt.*;

public class CMenuUI extends BasicMenuUI {

    public CMenuUI() {
        super.selectionBackground = new Color(0, 0, 0, 0);
        super.oldBorderPainted = false;
    }

    @Override
    protected Dimension getPreferredMenuItemSize(JComponent c, Icon checkIcon, Icon arrowIcon, int defaultTextIconGap) {
        Dimension preferredSize = super.getPreferredMenuItemSize(c, checkIcon, arrowIcon, defaultTextIconGap);
        Insets insets = c.getInsets();
        preferredSize.width += insets.left + insets.right;
        preferredSize.height += insets.top + insets.bottom;
        return preferredSize;
    }
}
