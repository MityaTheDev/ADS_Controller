package GUI.Tabs;

import GUI.*;
import General.Data;
import General.Utils;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static General.Data.saveSessionsToTxt;
import static General.Data.sessionsToStart;

public class TabManager extends JFrame {
    private final Utils utils = new Utils();

    private JPanel navigationPanel;
    private JPanel contentPanel;
    private JPanel consolePanel;
    private JLabel consoleLabel = new JLabel("Console");
    public JTextArea consoleTextArea;

    private final AccountsTab accountsTab = new AccountsTab(this);
    private final DiscordTab discordTab = new DiscordTab(this);
    private final TwitterTab twitterTab = new TwitterTab(this);

    private ButtonClickListener buttonClickListener;

    public TabManager() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveSessionsToTxt(Data.activeSessions);
                sessionsToStart = utils.saveAsSet(accountsTab.getSessionsToStartTextArea());
                saveSessionsToTxt(Data.sessionsToStart);

                System.exit(0);
            }
        });

        setTitle("ADS Controller");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JButton accountsButton = new JButton(Tabs.ACCOUNTS.name());
        JButton discordButton = new JButton(Tabs.DISCORD.name());
        JButton twitterButton = new JButton(Tabs.TWITTER.name());

        consoleTextArea = new JTextArea();
        consoleTextArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret) consoleTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane consoleScrollPane = new JScrollPane(consoleTextArea);

        navigationPanel = new JPanel();
        navigationPanel.setLayout(new GroupLayout(navigationPanel));

        contentPanel = new JPanel();
        contentPanel.setLayout(new GroupLayout(contentPanel));

        consolePanel = new JPanel();
        consolePanel.setLayout(new GroupLayout(consolePanel));

        //NAVIGATION
        GroupLayout navigationLayout = (GroupLayout) navigationPanel.getLayout();
        navigationLayout.setAutoCreateGaps(true);
        navigationLayout.setAutoCreateContainerGaps(true);

        navigationLayout.setVerticalGroup(
                navigationLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(accountsButton)
                        .addComponent(discordButton)
                        .addComponent(twitterButton));

        navigationLayout.setHorizontalGroup(
                navigationLayout.createSequentialGroup()
                        .addComponent(accountsButton)
                        .addComponent(discordButton)
                        .addComponent(twitterButton));


        //CONSOLE
        GroupLayout consoleLayout = (GroupLayout) consolePanel.getLayout();
        consoleLayout.setAutoCreateGaps(true);
        consoleLayout.setAutoCreateContainerGaps(true);

        consoleLayout.setVerticalGroup(
                consoleLayout.createSequentialGroup()
                        .addComponent(consoleLabel)
                        .addComponent(consoleScrollPane));

        consoleLayout.setHorizontalGroup(
                consoleLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(consoleLabel)
                        .addComponent(consoleScrollPane));

        //GENERAL
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(navigationPanel))
                        .addGroup(layout.createParallelGroup(Alignment.CENTER)
                                .addComponent(contentPanel))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(consolePanel)));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(navigationPanel))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(contentPanel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(consolePanel)));

        pack();
        setSize(new Dimension((int)(Screen.width / 2.5), (int) (Screen.height / 1.45)));

        navigationPanel.setMinimumSize(new Dimension((int) (getWidth() * 0.955), (int) (getHeight() * 0.08)));
        contentPanel.setMinimumSize(new Dimension((int) (getWidth() * 0.955), (int) (getHeight() * 0.33)));
        consolePanel.setMinimumSize(new Dimension((int) (getWidth() * 0.955), (int) (getHeight() * 0.43)));
        consolePanel.setMaximumSize(new Dimension((int) (Screen.width), (int) (Screen.height / 2)));

        buttonClickListener = new ButtonClickListener(this,
                accountsTab, discordTab, twitterTab, contentPanel);

        accountsButton.addActionListener(buttonClickListener);
        discordButton.addActionListener(buttonClickListener);
        twitterButton.addActionListener(buttonClickListener);

        accountsTab.draw(contentPanel);

        setVisible(true);
    }

    ButtonClickListener getButtonListener() {
        return buttonClickListener;
    }
}