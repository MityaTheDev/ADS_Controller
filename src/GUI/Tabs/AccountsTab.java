package GUI.Tabs;

import ADS.AdsController;
import GUI.ButtonClickListener;
import General.Utils;
import General.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

public class AccountsTab {
    public static final String TO_START = "SESSIONS_TO_START";
    public static final String ACTIVE = "ACTIVE_SESSIONS";

    private final AdsController adsController = new AdsController();

    private final List<String> sessionsToStart = new ArrayList<>();
    private final List<String> activeSessions = new ArrayList<>();

    private Utils utils = new Utils();
    private TabManager tabManager;

    private JLabel sessionsLabel = new JLabel("Sessions");
    private JTextArea sessionsToStartTextArea;
    public JCheckBox headlessCheckBox;
    private JButton startSessionsButton;
    private JButton stopSessionsButton;
    private JButton checkActiveSessionsButton;
    private JComboBox<String> optionsComboBox;
    private JLabel activeSessionsLabel = new JLabel("Active Sessions");
    private JTextArea activeSessionsTextArea;
    private JButton copySessionsButton;
    private JButton copyActiveSessionButton;

    public AccountsTab(TabManager tabManager) {
        this.tabManager = tabManager;
    }

    public void draw(JPanel contentPanel) {
        Data.currentScreen = Tabs.ACCOUNTS;

        GroupLayout layout = (GroupLayout) contentPanel.getLayout();

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        sessionsToStartTextArea = new JTextArea();
        sessionsToStartTextArea.setFont(new Font(new JLabel().getFont().getName(), Font.PLAIN, 16));
        Data.populateSessionsFromTxt();
        utils.print(sessionsToStartTextArea, Data.sessionsToStart);
        JScrollPane sessionsScrollPane = new JScrollPane(sessionsToStartTextArea);
        sessionsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        headlessCheckBox = new JCheckBox("Headless");
        headlessCheckBox.setSelected(true);

        startSessionsButton = new JButton("Start sessions");
        stopSessionsButton = new JButton("Stop sessions");
        checkActiveSessionsButton = new JButton("Check status");

        String[] options = {"active sessions", "all sessions"};
        optionsComboBox = new JComboBox<>(options);

        copySessionsButton = new JButton("Copy sessions");
        copyActiveSessionButton = new JButton("Copy active sessions");

        activeSessionsTextArea = new JTextArea();
        activeSessionsTextArea.setEditable(false);
        utils.print(activeSessionsTextArea, Data.activeSessions);
        JScrollPane activeSessionsScrollPane = new JScrollPane(activeSessionsTextArea);
        activeSessionsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(sessionsLabel)
                                .addComponent(sessionsScrollPane)
                                .addComponent(copySessionsButton))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(headlessCheckBox)
                                        .addComponent(startSessionsButton)
                                        .addComponent(stopSessionsButton)
                                        .addComponent(checkActiveSessionsButton))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER, false)
                                        .addComponent(optionsComboBox)))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(activeSessionsLabel)
                                .addComponent(activeSessionsScrollPane)
                                .addComponent(copyActiveSessionButton))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(sessionsLabel)
                                .addComponent(sessionsScrollPane)
                                .addComponent(copySessionsButton))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(headlessCheckBox)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 5, 10)
                                        .addComponent(startSessionsButton)
                                        .addComponent(stopSessionsButton)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, 15)
                                        .addComponent(checkActiveSessionsButton))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(optionsComboBox, 0, 20, 30)))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(activeSessionsLabel)
                                .addComponent(activeSessionsScrollPane)
                                .addComponent(copyActiveSessionButton))
        );

        contentPanel.setVisible(true);

        ButtonClickListener buttonClickListener = tabManager.getButtonListener();

        startSessionsButton.addActionListener(buttonClickListener);
        stopSessionsButton.addActionListener(buttonClickListener);
        checkActiveSessionsButton.addActionListener(buttonClickListener);
        copySessionsButton.addActionListener(buttonClickListener);
        copyActiveSessionButton.addActionListener(buttonClickListener);
    }

    public void copyToClipboard(String sessions) {

        String text = "";

        switch (sessions) {
            case TO_START:
                text = sessionsToStartTextArea.getText();
                break;
            case ACTIVE:
                text = activeSessionsTextArea.getText();
                break;
        }

        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public JTextArea getSessionsToStartTextArea() {
        return sessionsToStartTextArea;
    }

    public JTextArea getActiveSessionsTextArea() {
        return activeSessionsTextArea;
    }

    public void saveJTextAreaText() {
        Data.sessionsToStart = utils.saveAsSet(sessionsToStartTextArea);
        Data.activeSessions = utils.saveAsSet(activeSessionsTextArea);
    }

    public String getComboBoxOption() {
        return optionsComboBox.getItemAt(optionsComboBox.getSelectedIndex());
    }
}
