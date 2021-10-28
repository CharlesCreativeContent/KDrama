package com.kdramabeans.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.kdramabeans.game.Game.*;
import static com.kdramabeans.game.Game.music;
import static javax.swing.JOptionPane.showMessageDialog;

public class Gui {

    private JFrame window;
    private JPanel titleNamePanel, buttonPanel, mainTextPanel, generalButtonPanel;
    public static JLabel sceneLabel;
    private JLabel titleNameLabel, gifLabel;
    private JButton startButton, nextButton, enterButton, restartButton, quitButton, helpButton, musicButton;
    public static JTextArea mainTextArea, statusArea, inventoryArea, currentScene;
    public static JTextField mainTextField;
    private Container container;
    private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 45);
    private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 15);
    private TextFieldHandler textHandler = new TextFieldHandler();
    public static final Color SKY_ICE = new Color(206, 231, 243);
    public static final Color HOT_ORANGE = new Color(255, 160, 0);

    /*
      ctor that initializes the home page of the game
     */
    public Gui() {
        item = new DataParser();

        // JFrame setup
        setWindow();

        // Panel Title setup
        addTitleNamePanel();

        // Panel GIF setup
        addGif();

        // start button setup - should link to the start of the game
        addStartButtonPanel();


        music = new BGM("goblin.wav",true);

        // calls up all the components and makes the screen visible
        window.setVisible(true);
    }

    public void createGameScreen() {
        // disables the home page panel and will display panel below
        container.remove(gifLabel);
        buttonPanel.setVisible(false);
        titleNamePanel.setVisible(false);


        // sets up the Scene Title
        addSceneTitle();

        // sets up the Scene Image
        addSceneImage();

        // sets up the textArea
        addMainTextArea();

        //sets up inventory area
        addInventoryArea();

        // sets up enter button
        addEnterButton();

        //sets up ui buttons
        generalButtons();

        // sets up textField for userInput
        addMainTextField();

        // sets up the statusArea
        addStatusArea();


        // sets up the panel
        addMainTextPanel();

        buttonPanel.setBounds(550, 700, 150, 75);
        buttonPanel.add(enterButton);
        buttonPanel.setVisible(true);
    }

    public void addTitleNamePanel(){
        //setup scene title
        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 600, 70);
        titleNamePanel.setBackground(SKY_ICE);
        titleNameLabel = new JLabel("You are my Destiny!");
        titleNameLabel.setForeground(HOT_ORANGE);
        titleNameLabel.setFont(titleFont);
        titleNamePanel.add(titleNameLabel);
        container.add(titleNamePanel);
    }

    public void addStartButtonPanel(){
    // start button setup - should link to the start of the game
    buttonPanel = new JPanel();
        buttonPanel.setBounds(300, 500, 200, 100);
        buttonPanel.setBackground(SKY_ICE);

        startButton = new JButton(findPNG("startButton", 200, 75));
        startButton.setBorderPainted(false);
        startButton.addActionListener(textHandler);

        buttonPanel.add(startButton);
        container.add(buttonPanel);
    }

    public void addGif(){
        // Panel GIF setup
        gifLabel = new JLabel(findGIF("koreanair"));
        gifLabel.setBounds(7, 170, 800, 200);
        container.add(gifLabel);
    }

    public void setWindow() {
        //setup window frame
        window = new JFrame();
        window.setSize(800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(SKY_ICE);
        window.setLayout(null);
        window.setResizable(false);
        window.setTitle("KDramaBeans Game");
        container = window.getContentPane();
    }

    public void addSceneTitle(){
        //sets up Scene Title
        currentScene = new JTextArea("HOME");
        currentScene.setBounds(100,0,400,50);
        currentScene.setEditable(false);
        currentScene.setBackground(SKY_ICE);
        currentScene.setFont(titleFont);
        currentScene.setForeground(HOT_ORANGE);
        currentScene.setEditable(false);
        container.add(currentScene);
    }
        public void addSceneImage(){
        //sets up scene image
        sceneLabel = new JLabel(findJPG("home"));
        sceneLabel.setBorder(new LineBorder(Color.black));
        sceneLabel.setBounds(0, 50, 800, 200);
        container.add(sceneLabel, SwingConstants.CENTER);
    }


    public void addMainTextArea(){
        // sets up the textArea
        mainTextArea = new JTextArea(printStatus());
        mainTextArea.setBounds(100, 300, 600, 250);
        mainTextArea.setBackground(Color.white);
        mainTextArea.setForeground(Color.black);
        mainTextArea.setFont(normalFont);
        mainTextArea.setEditable(false);
        mainTextArea.setLineWrap(true);
    }

    public void addInventoryArea(){
        //sets up inventory area
        inventoryArea = new JTextArea(player.printGrabbedItems() + "\n" + player.printEvidence());
        inventoryArea.setBounds(100, 650, 600, 50);
        inventoryArea.setBackground(Color.white);
        inventoryArea.setForeground(Color.black);
        inventoryArea.setEditable(false);
        container.add(inventoryArea);
    }


    public void addEnterButton(){
        // sets up enter button
        enterButton = new JButton(findPNG("enterButton", 100, 50));
        enterButton.setBorderPainted(false);
        enterButton.setBackground(Color.white);
        enterButton.setForeground(Color.black);
        enterButton.setFont(normalFont);
        enterButton.addActionListener(textHandler);
    }

    public void addMainTextPanel(){
        // sets up the MainTextPanel
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 270, 600, 350);
        mainTextPanel.setBackground(Color.white);
        mainTextPanel.add(mainTextArea);
        mainTextPanel.add(statusArea);
        container.add(mainTextPanel);
    }
        public void addMainTextField(){
        // sets up the MainTextField
        mainTextField = new JTextField();
        mainTextField.setText("");
        mainTextField.setBounds(100, 700, 450, 60);
        mainTextField.setBackground(Color.white);
        mainTextField.setForeground(Color.black);
        mainTextField.setFont(normalFont);
        mainTextField.addKeyListener(textHandler);
        container.add(mainTextField);
    }

    public void addStatusArea(){
        // sets up the statusArea
        statusArea = new JTextArea();
        statusArea.setBounds(100, 600, 600, 300);
        statusArea.setBackground(Color.white);
        statusArea.setForeground(Color.black);
        statusArea.setFont(normalFont);
        statusArea.setLineWrap(true);
        statusArea.setEditable(false);
        statusArea.setText("To see commands please click on the [?] - Help button on top right corner!\n");
    }

    public Icon findJPG(String path) {
        Icon sceneJPG = null;
        try {
            sceneJPG = new ImageIcon(getClass().getResource("/"+path+".jpg"));
        } catch (Exception e) {
            System.out.println("Can't find Image: " + path);
            e.printStackTrace();
        }
        return sceneJPG;
    }


    public Icon findGIF(String path) {
        Icon sceneJPG = null;
        try {
            sceneJPG = new ImageIcon(getClass().getResource("/"+path+".gif"));
        } catch (Exception e) {
            System.out.println("Can't find Image: " + path);
            e.printStackTrace();
        }
        return sceneJPG;
    }


    public ImageIcon findPNG(String path, int width, int height) {
        ImageIcon foundImage = null;
        try {
            foundImage = new ImageIcon(ImageIO.read(new File(this.getClass().getResource("/" + path + ".png").toURI())).getScaledInstance(width, height, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            System.out.println("Can't find Image: " + path);
            e.printStackTrace();
        }
        return foundImage;
    }

    public void generalButtons() {
        //sets up ui buttons
        quitButton = new JButton(findPNG("xButton", 50, 50));
        restartButton = new JButton(findPNG("restart", 50, 50));
        helpButton = new JButton(findPNG("infoButton", 50, 50));
        musicButton = new JButton(findPNG("sound", 50, 50));
        setGeneralButtonPanel();
        container.add(generalButtonPanel);
    }

    public void setGeneralButtonPanel() {
        //adds Actions and Border to buttons
        this.generalButtonPanel = new JPanel();
        quitButton.setBorderPainted(false);
        restartButton.setBorderPainted(false);
        helpButton.setBorderPainted(false);
        musicButton.setBorderPainted(false);

        generalButtonPanel.setBounds(0, -10, 1300, 60);
        generalButtonPanel.setBackground(SKY_ICE);
        generalButtonPanel.add(restartButton);
        generalButtonPanel.add(musicButton);
        generalButtonPanel.add(helpButton);
        generalButtonPanel.add(quitButton);
        musicButton.addActionListener(textHandler);
        quitButton.addActionListener(textHandler);
        helpButton.addActionListener(textHandler);
        restartButton.addActionListener(textHandler);
    }

    class TextFieldHandler implements KeyListener, ActionListener {

        // restart, quit, help, enter(click)
        @Override
        public void keyTyped(KeyEvent e) {
            keyPressed(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_ENTER) {
                playGame();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Map<Object, Runnable> allActions = new HashMap<>() {{
                put(enterButton, Game::playGame);
                put(quitButton, () -> System.exit(0));
                put(restartButton, () -> {
                    System.out.println("Restarting...");
                    story.restartGame();
                    player.clearItems();

                    currentScene.setText("HOME");
                    sceneLabel.setIcon(getJPG("home"));
                    mainTextArea.setText(printStatus());
                    inventoryArea.setText("Inventory is Empty!");
                    statusArea.setText("");
                });
                // put(helpButton, () -> statusArea.setText("These are your commands:\n\n" + showHelp()));
                put(helpButton, () -> showMessageDialog(statusArea, showHelp(),"Helpful Information",JOptionPane.WARNING_MESSAGE));
                put(startButton, () -> {
                    buttonPanel.remove(startButton);
                    createGameScreen();
                    if(!BGM.isMuted()){
                        music.start();
                    }
                });
                put(musicButton, () -> {
                    if (music.isPlaying()) {
                        music.pauseSong();
                        musicButton.setIcon(findPNG("xSound",50,50));
                    } else {
                        music.playSong();
                        musicButton.setIcon(findPNG("sound",50,50));
                    }
                });
                put(nextButton, Gui.this::createGameScreen);
            }};
            allActions.getOrDefault(e.getSource(), () -> System.out.println("You have not selected a button.")).run();
        }

        private String showHelp() {
            return "Commands:\n\n[examine,look,see,view] [Item] - to get the item description.\n" +
                    "[grab,get,acquire,attain,snatch] [Item] - to add item to your inventory.\n" +
                    "[drop,remove] [Item] - to drop item from your inventory.\n" +
                    "[use,throw] [Item] - to use item in a scene.\n" +
                    "[choose,select,move] [Option] - to go to next scene.\n";
        }
    }
}