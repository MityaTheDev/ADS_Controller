package GUI;

import ADS.AdsController;
import GUI.Tabs.*;
import General.Utils;
import org.json.JSONObject;

import static General.Data.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ButtonClickListener implements ActionListener {
    private final AdsController adsController = new AdsController();
    private Thread buttonThread = new Thread();

    private JPanel panel;
    private TabManager tabManager;
    private AccountsTab accountsTab;
    private DiscordTab discordTab;
    private TwitterTab twitterTab;
    private Utils utils;

    public ButtonClickListener(TabManager tabManager, AccountsTab accountsTab,
                               DiscordTab discordTab, TwitterTab twitterTab, JPanel panel) {
        this.panel = panel;
        this.tabManager = tabManager;
        this.accountsTab = accountsTab;
        this.discordTab = discordTab;
        this.twitterTab = twitterTab;
        utils = new Utils();
    }

    public ButtonClickListener(DiscordTab discordTab, JPanel panel) {
        this.panel = panel;
        this.discordTab = discordTab;
        utils = new Utils();
    }

    public ButtonClickListener(TabManager tabManager, JPanel panel) {
        this.panel = panel;
        this.tabManager = tabManager;
        utils = new Utils();
    }

    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        //Navigation buttons are non blocking
        List<String> navigationButtons = new ArrayList<>();
        Arrays.asList(Tabs.values()).forEach(screen -> navigationButtons.add(screen.name()));

        if (navigationButtons.contains(command) || !buttonThread.isAlive()) {

            buttonThread = new Thread(() -> {

                //navigation buttons
                switch (command) {
                    case "ACCOUNTS":
                        if (!currentScreen.equals(Tabs.ACCOUNTS)) {
//                                discordScreen.saveJTextAreaText();
                        }
                        panel.removeAll();
                        panel.updateUI();
                        accountsTab.draw(panel);
                        break;
                    case "DISCORD":
                        if (!currentScreen.equals(Tabs.DISCORD)) {
                            accountsTab.saveJTextAreaText();
                            //twitterScreen
                        }
                        panel.removeAll();
                        panel.updateUI();
                        discordTab.draw(panel);
                        break;
                    case "TWITTER":
                        if (!currentScreen.equals(Tabs.TWITTER)) {
                            accountsTab.saveJTextAreaText();
                            //twitterScreen
                        }
                        utils.println(tabManager.consoleTextArea,
                                "Twitter functionality is in development");
                        break;
                }

                Iterator<String> iterator;
                //command buttons
                switch (command) {
                    case "Start sessions":
                        sessionsToStart = new HashSet<>(utils.saveAsSet(accountsTab.getSessionsToStartTextArea()));
                        if (sessionsToStart.isEmpty()) return;

                        consolePrintln("Starting sessions...");

                        iterator = sessionsToStart.iterator();
                        while (iterator.hasNext()) {
                            String session = iterator.next();
                            consolePrint(session + ": ");

                            String sessionID = sessionsID.get(session);
                            boolean headless = accountsTab.headlessCheckBox.isSelected();

                            String response = adsController.startSession(sessionID, headless);

                            consolePrintln(response);

                            if (response.equals("success")) {
                                activeSessions.add(session);
                                iterator.remove();
                                saveSessionsToTxt(sessionsToStart);
                            }

                            Utils.sleep(1000);

                            utils.clear(accountsTab.getActiveSessionsTextArea());
                            utils.println(accountsTab.getActiveSessionsTextArea(), activeSessions);
                            utils.clear(accountsTab.getSessionsToStartTextArea());
                            utils.println(accountsTab.getSessionsToStartTextArea(), sessionsToStart);
                        }

                        consolePrintln("");
                        break;
                    case "Stop sessions":
                        activeSessions = new HashSet<>(utils.saveAsSet(accountsTab.getActiveSessionsTextArea()));
                        if (activeSessions.isEmpty()) return;

                        consolePrintln("Stopping sessions...");

                        sessionsToStart = new HashSet<>(utils.saveAsSet(accountsTab.getSessionsToStartTextArea()));
                        boolean noSessionsToStart = sessionsToStart.isEmpty();
                        Set<String> sessionsSet;

                        sessionsSet = noSessionsToStart ?
                                new HashSet<>(activeSessions) :
                                new HashSet<>(sessionsToStart);

                        iterator = sessionsSet.iterator();
                        while (iterator.hasNext()) {
                            String session = iterator.next();
                            String sessionID = sessionsID.get(session);
                            consolePrint(session + ": ");

                            String response = adsController.stopSession(sessionID);

                            consolePrintln(response);

                            if (response.equals("Success")) {
                                iterator.remove();
                                if (noSessionsToStart) {
                                    activeSessions = new HashSet<>(sessionsSet);
                                } else {
                                    sessionsToStart = new HashSet<>(sessionsSet);
                                    saveSessionsToTxt(sessionsToStart);
                                    activeSessions.remove(session);
                                }

                                saveSessionsToTxt(activeSessions);
                            }

                            if (!noSessionsToStart) {
                                utils.clear(accountsTab.getSessionsToStartTextArea());
                                utils.println(accountsTab.getSessionsToStartTextArea(), sessionsToStart);
                            }
                            utils.clear(accountsTab.getActiveSessionsTextArea());
                            utils.println(accountsTab.getActiveSessionsTextArea(), activeSessions);

                            Utils.sleep(1000);
                        }

                        consolePrintln("");
                        break;
                    case "Check status":
                        switch (accountsTab.getComboBoxOption()) {
                            case "active sessions":
                                consolePrintln("Checking sessions status (active): ");

                                if (activeSessions.isEmpty()) {
                                    consolePrintln("no active sessions detected");
                                    consolePrintln("");
                                    return;
                                } else {
                                    iterator = activeSessions.iterator();
                                    while (iterator.hasNext()) {
                                        String session = iterator.next();
                                        String sessionID = sessionsID.get(session);
                                        String status = adsController.checkSessionStatus(sessionID)
                                                .getJSONObject("data").get("status").toString();

                                        consolePrintln(session + ": " + status);

                                        if (!status.equals("Active")) {
                                            iterator.remove();
                                        }

                                        utils.clear(accountsTab.getActiveSessionsTextArea());
                                        utils.println(accountsTab.getActiveSessionsTextArea(), activeSessions);
                                    }

                                    consolePrintln("");
                                }
                                break;
                            case "all sessions":
                                consolePrintln("Checking sessions status (all): ");

                                final int[] i = {0};
                                final int sessionCount = sessionsID.keySet().size();

                                final String[] oldText = {"Checked: " + i[0] + "/" + sessionCount + ", Successful: 0"};
                                consolePrint(oldText[0]);

                                activeSessions.clear();
                                saveSessionsToTxt(activeSessions);
                                utils.clear(accountsTab.getActiveSessionsTextArea());

                                sessionsID.keySet().forEach(key -> {
                                    String sessionID = sessionsID.get(key);
                                    JSONObject response = adsController.checkSessionStatus(sessionID);
                                    String status = response
                                            .getJSONObject("data").get("status").toString();

                                    i[0]++;

                                    String newText = "Checked: " + i[0] + "/" + sessionCount + ", Successful: " + activeSessions.size();

                                    if (status.equals("Active")) {
                                        activeSessions.add(key);
                                        newText = "Checked: " + i[0] + "/" + sessionCount + ", Successful: " + activeSessions.size();

                                        utils.clear(accountsTab.getActiveSessionsTextArea());
                                        utils.println(accountsTab.getActiveSessionsTextArea(), activeSessions);
                                    }

                                    utils.reprint(tabManager.consoleTextArea, oldText[0], newText);
                                    oldText[0] = newText;
                                });

                                consolePrintln("\nActive sessions count: " + activeSessions.size() + "\n"
                                        + (activeSessions.size() > 0 ? activeSessions + "\n" : ""));
                                break;
                        }
                        break;
                    case "Copy sessions":
                        accountsTab.copyToClipboard(AccountsTab.TO_START);
                        break;
                    case "Copy active sessions":
                        accountsTab.copyToClipboard(AccountsTab.ACTIVE);
                        break;
                    case "Get active sessions":
                        utils.clear(discordTab.accountsTextArea);
                        utils.println(discordTab.accountsTextArea, activeSessions);
                        break;
                    case "Shuffle":
                        discordTab.shuffle();
                        break;
                    case "To Order":
                        discordTab.toOrder();
                        break;
                    case "Start":
                        utils.println(tabManager.consoleTextArea,
                                "Congratulations! You've just pressed the \"Start\" button");
                        break;
                    case "Stop":
                        utils.println(tabManager.consoleTextArea,
                                "Congratulations! You've just pressed the \"Stop\" button");
                        break;
                    case "Continue":
                        utils.println(tabManager.consoleTextArea,
                                "Congratulations! You've just pressed the \"Continue\" button");
                        break;
                    case "Pause":
                        utils.println(tabManager.consoleTextArea,
                                "Congratulations! You've just pressed the \"Pause\" button");
                        break;
                    case "Active Sessions":
                        utils.println(tabManager.consoleTextArea, "Imagine active session being printed");
                        break;
                }
            });

            buttonThread.start();
        }
    }

    private void consolePrint(String text) {
        utils.print(tabManager.consoleTextArea, text);
    }

    private void consolePrintln(String text) {
        utils.println(tabManager.consoleTextArea, text);
    }
}
