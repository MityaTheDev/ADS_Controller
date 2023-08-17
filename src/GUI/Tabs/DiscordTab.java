package GUI.Tabs;

import GUI.ButtonClickListener;
import General.Utils;
import General.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class DiscordTab {
    private Utils utils = new Utils();

    public JTextArea accountsTextArea = new JTextArea();

    private String accounts;
    private String text;
    private List<String> list;

    private TabManager tabManager;
    private JCheckBox raffleCheckBox;
    private JCheckBox giveawayCheckBox;
    private ActionListener actionListener;


    public JTextField urlTextField;
    public JTextField selectorTextField;

    public DiscordTab(TabManager tabManager) {
        this.tabManager = tabManager;
    }

    public void draw(JPanel contentPanel) {
        Data.currentScreen = Tabs.DISCORD;

        GroupLayout layout = (GroupLayout) contentPanel.getLayout();

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel discordLabel = new JLabel("Discord url:");
        urlTextField = new JTextField();
        raffleCheckBox = new JCheckBox("Raffle");
        giveawayCheckBox = new JCheckBox("Giveaway");
        setActionListener();
        raffleCheckBox.addActionListener(actionListener);
        giveawayCheckBox.addActionListener(actionListener);
        giveawayCheckBox.setSelected(true);
        JLabel elementLabel = new JLabel("Element");
        selectorTextField = new JTextField();
        selectorTextField.setMaximumSize(new Dimension(selectorTextField.getMaximumSize().width, elementLabel.getHeight()));
        JLabel accountsLabel = new JLabel("Accounts");
        accountsTextArea = new JTextArea();
        JScrollPane accountsScrollPane = new JScrollPane(accountsTextArea);
        accountsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JButton activeSessionsButton = new JButton("Get active sessions");
        JButton shuffleButton = new JButton("Shuffle");
        JButton toOrderButton = new JButton("To Order");
        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.GREEN);
        JButton stopButton = new JButton("Stop");
        stopButton.setBackground(Color.RED);
        JButton continueButton = new JButton("Continue");
        JButton pauseButton = new JButton("Pause");
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 0,0,0);
        constraints.gridx = 0;
        constraints.gridy = 0;
        controlPanel.add(startButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        controlPanel.add(stopButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        controlPanel.add(continueButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        controlPanel.add(pauseButton, constraints);

        GroupLayout.ParallelGroup discordLinkPanelVertical = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        GroupLayout.ParallelGroup elementPanelVertical = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        GroupLayout.ParallelGroup accountsPanelVertical = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        GroupLayout.ParallelGroup controlPanelVertical = layout.createParallelGroup(GroupLayout.Alignment.CENTER);

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(discordLinkPanelVertical
                                .addComponent(discordLabel)
                                .addComponent(urlTextField))
                        .addGroup(elementPanelVertical
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(giveawayCheckBox)
                                        .addComponent(raffleCheckBox))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(elementLabel)
                                        .addComponent(selectorTextField)))
                        .addGroup(accountsPanelVertical
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(accountsLabel)
                                        .addComponent(accountsScrollPane))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(activeSessionsButton)
                                        .addComponent(shuffleButton)
                                        .addComponent(toOrderButton))
                        .addGroup(controlPanelVertical
                                        .addComponent(controlPanel)))
        );

        GroupLayout.SequentialGroup discordLinkPanelHorizontal = layout.createSequentialGroup();
        GroupLayout.SequentialGroup elementPanelHorizontal = layout.createSequentialGroup();
        GroupLayout.SequentialGroup accountsPanelHorizontal = layout.createSequentialGroup();
        GroupLayout.SequentialGroup controlPanelHorizontal = layout.createSequentialGroup();

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(discordLinkPanelHorizontal
                                .addComponent(discordLabel)
                                .addComponent(urlTextField))
                        .addGroup(elementPanelHorizontal
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(giveawayCheckBox)
                                        .addComponent(raffleCheckBox))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(elementLabel)
                                        .addComponent(selectorTextField)))
                        .addGroup(accountsPanelHorizontal
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(accountsLabel)
                                        .addComponent(accountsScrollPane))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(activeSessionsButton)
                                        .addComponent(shuffleButton)
                                        .addComponent(toOrderButton))
                        .addGroup(controlPanelHorizontal
                                        .addComponent(controlPanel)))
        );

        controlPanel.setVisible(true);

        accountsScrollPane.setMaximumSize(new Dimension(contentPanel.getWidth() / 2, (int)(contentPanel.getHeight() / 2.5)));

        contentPanel.setVisible(true);

        ButtonClickListener buttonClickListener = tabManager.getButtonListener();

        activeSessionsButton.addActionListener(buttonClickListener);
        shuffleButton.addActionListener(buttonClickListener);
        toOrderButton.addActionListener(buttonClickListener);
        startButton.addActionListener(buttonClickListener);
        stopButton.addActionListener(buttonClickListener);
        continueButton.addActionListener(buttonClickListener);
        pauseButton.addActionListener(buttonClickListener);
    }

    private void setActionListener() {
        actionListener = e -> {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            String name = checkBox.getText();

            if (name.equals("Raffle") && raffleCheckBox.isSelected() && giveawayCheckBox.isSelected()) {
                giveawayCheckBox.setSelected(false);
            }

            if (name.equals("Giveaway") && raffleCheckBox.isSelected() && giveawayCheckBox.isSelected()) {
                raffleCheckBox.setSelected(false);
            }
        };
    }

    public void shuffle() {
        accounts = accountsTextArea.getText();
        utils.clear(accountsTextArea);
        text = "";
        list = new ArrayList<>(Arrays.asList(accounts.split("\n")));
        list.removeIf(""::equals);
        Collections.shuffle(list);
        for (String acc : list) {
            text += acc + "\n";
        }
        text = text.substring(0, text.length() - 1);
        utils.print(accountsTextArea, text);
    }

    public void toOrder() {
        accounts = accountsTextArea.getText();
        utils.clear(accountsTextArea);
        text = "";
        list = new ArrayList<>(Arrays.asList(accounts.split("\n")));
        list.removeIf(""::equals);
        Collections.sort(list);
        for (String acc : list) {
            text += acc + "\n";
        }
        text = text.substring(0, text.length() - 1);
        utils.print(accountsTextArea, text);
    }
}
