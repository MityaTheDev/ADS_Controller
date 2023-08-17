package General;

import javax.swing.*;
import java.util.*;

public class Utils {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void reprint(JTextArea textArea, String oldText, String newText) {
        String text = textArea.getText();

        text = text.replaceAll(oldText, newText);
        textArea.setText(text);
    }

    public void print(JTextArea textArea, String text) {
        textArea.setText(textArea.getText() + text);
    }

    public void print(JTextArea textArea, Collection<?> text) {
        Iterator<?> iterator = text.iterator();
        while(iterator.hasNext()) {
            String line = iterator.next().toString();
            textArea.append(line);
            if (iterator.hasNext()) textArea.append("\n");
        }
    }

    public void println(JTextArea textArea, String text) {
        textArea.append(text + "\n");
    }

    public void println(JTextArea textArea, Collection<?> text) {
        text.forEach(line -> {
            textArea.append(line.toString());
            textArea.append("\n");
        });
    }

    public void clear(JTextArea textArea) {
        textArea.setText("");
    }

    public Set<String> saveAsSet(JTextArea textArea) {
        String text = textArea.getText();
        Set<String> set = new HashSet<>(Arrays.asList(text.split("\n")));
        set.removeIf(""::equals);
        return set;
    }

    public List<String> saveAsList(JTextArea textArea) {
        String text = textArea.getText();
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(text.split("\n")));
        arrayList.removeIf(""::equals);
        return arrayList;
    }
}
