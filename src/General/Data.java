package General;

import GUI.Tabs.Tabs;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Data {
    public static Map<String, String> sessionsID = new HashMap<>();

    public static Set<String> sessionsToStart = new HashSet<>();
    public static Set<String> activeSessions = new HashSet<>();

    public static Tabs currentScreen;

    public static void populateMapFromTxt(String path) {
        List<String> lines = new ArrayList<>();

        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        lines.forEach(line -> {
            String[] data = line.split("\t");
            String index = data[0];
            String id = data[1];

            sessionsID.put(index, id);
        });
    }

    public static void populateSessionsFromTxt() {
        List<String> lines;

        try {
            if (Files.exists(Paths.get("Sessions to start.txt"))) {
                lines = Files.readAllLines(Paths.get("Sessions to start.txt"));
                sessionsToStart.clear();
                sessionsToStart.addAll(lines);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (Files.exists(Paths.get("Active sessions.txt"))) {
                lines = Files.readAllLines(Paths.get("Active sessions.txt"));
                activeSessions.clear();
                activeSessions.addAll(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSessionsToTxt(Set<String> set) {

        String fileName = set.equals(sessionsToStart) ? "Sessions to start.txt" : "Active sessions.txt";

        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName))) {

            StringBuilder text = new StringBuilder();
            for (String s : set) {
                text.append(s).append("\n");
            }
            if (text.length() > 1) {
                text = new StringBuilder(text.substring(0, text.length() - 1));
            }
            printWriter.print(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
