package Client.View;

import java.awt.event.*;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.*;
import Client.ClientController.*;
import Client.ViewController.*;

public class Gui extends JFrame {
    private JPanel picture;
    private JPanel frame;
    private JPanel buttons;
    private JButton inventoryButton;
    private JButton customerButton;
    private JLabel header;

    public Gui() {
        picture = new JPanel();
        frame = new JPanel();
        inventoryButton = new JButton("Inventory Management System");
        customerButton = new JButton("Client Management System");
        header = new JLabel("LaXu Automated Tool Shop Management Solution");
        // picture is content, frame is menu buttons and header
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setPreferredSize(ViewConstants.FRAME_DIMENSION);

        picture.setPreferredSize(ViewConstants.PICTURE_DIMENSION);
        picture.setLayout(new CardLayout());

        header.setFont(ViewConstants.HEADER_FONT);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        frame.add(inventoryButton, c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        frame.add(customerButton, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        frame.add(picture, c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, ViewConstants.X_DIMENSION / 4 - 10, 0, 0); // top, right, bottom, left
        frame.add(header, c);
    }

    public void display() {
        this.add(frame);
        this.pack();
        this.setVisible(true);
    }

    public void addCard(JPanel card, String cardName) {
        this.picture.add(card, cardName);
    }

    public JPanel getPicture() {
        return this.picture;
    }

    public void registerInventoryButton(ActionListener listener) {
        registerListener(listener, inventoryButton);
    }

    public void registerCustomerButton(ActionListener listener) {
        registerListener(listener, customerButton);
    }

    public void registerExitButton(WindowAdapter listener) {
        this.addWindowStateListener(listener);
    }

    private void registerListener(ActionListener listener, JButton comp) {
        comp.addActionListener(listener);
    }

    public void setPanel(String panelName) {
        CardLayout pictureLayout = (CardLayout) picture.getLayout();
        pictureLayout.show(picture, panelName);
    }

    public JButton getButton(String key) {
        if (key.equals("inventoryPanelButton")) {
            return inventoryButton;
        } else {
            return customerButton;
        }
    }
}