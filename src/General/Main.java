package General;

import java.io.*;
import java.util.*;

import ADS.AdsController;
import GUI.Tabs.TabManager;

public class Main {
    public static List<String> names = new ArrayList<>();
    static List<String> ids;

    public static void main(String[] args) {

        AdsController controller = new AdsController();

        javax.swing.SwingUtilities.invokeLater(TabManager::new);
    }
}
