import javax.swing.*;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.awt.*;

/**
 * Class that displays the textbox with a menu bar 
 */
public class textboxGUI extends JFrame {
    
    //Create a textarea for user to write in
    private waifuBackground blankSpace;

    //Initial size for the textbox window
    private final int initial_width = 800;
    private final int initial_height = 600;

    private final String errorMessage = 
                "There is nothing in the clipboard to paste!";

    //Variable to determine if file has been saved or not
    private boolean fileSaved;

    //Create a JFrame
    private JFrame window;

    //Menu Bar 
    private JMenuBar menuBar;

    //Menu options
    private JMenu fileMenu, editMenu, formatMenu, nightMenu;

    //Menu items
    private JMenuItem newItem, openItem, saveAsItem,
                      cutItem, copyItem, pasteItem,
                      fontItem, undoItem,
                      redoItem;

    private JCheckBoxMenuItem textWrap;
    private JCheckBoxMenuItem nightMode;

    //Scroll feature
    private JScrollPane scrollBar;

    //Window Listener 
    WindowListener listener;

    //Variable to hold selected text to be used for copying and pasting
    private String selection;

    //Variables to be used for save and open features
    private String filePath, fileName;

    private UndoManager undoManager = new UndoManager();

    private Clipboard clipboard;

    /**
     * Constructor for the textbox program, makes a blankspace textarea with a menu
     */
    public textboxGUI() {

        //Create the JFrame object
        window = new JFrame();

        //Sets title
        window.setTitle("ノート");
        
        //Sets size
        window.setSize(initial_width, initial_height);

        //Sets text area 
        blankSpace = new waifuBackground();

        String file = new waifuLoader().getImageFile();
        ImageIcon icon = new ImageIcon(file);


        window.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {

                blankSpace.setImageBackground(icon, window.getWidth() - 350, 
                        window.getHeight() - (icon.getIconHeight() + 20), icon.getIconWidth(), icon.getIconHeight());
            }
        });

        blankSpace.getDocument().addUndoableEditListener(undoManager);

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        //Set icon image to the logo
        window.setIconImage(new ImageIcon(ClassLoader.getSystemResource(
            "Update_Notto.png")).getImage());


        //Create and appened scroll bar to the text area
        scrollBar = new JScrollPane(blankSpace);
        scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.
                            VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.
                            HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Build menu bar in the separate method
        buildMenuPanel();
        //Set the window's menu bar
        window.setJMenuBar(menuBar);

        //Add scroll bar with text area to the window
        window.add(scrollBar);

        //Set the window relative to the title screen
        window.setLocationRelativeTo(null);

        //What the exit button does
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

        //Call this method to deal with saving content if user typed in textarea
        checkForContent();

         //Implement the listener to the window
         window.addWindowListener(listener);

        //Allow user to resize the window
        window.setResizable(true);


        //Set the window to be visible
        window.setVisible(true);


        fileSaved = false;
    
    }

    /**
     * Method that checks for text in textarea when user click on the close button
     */
    public void checkForContent() {

        /*When closing the window, check if file has contents, ask user if they would like 
        save the file if contents were found 
        */
        listener = new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                //Debugging to console
                System.out.println("Closing");  
                
                //Create a string to represent an empty document
                String emptyDocument = "";

                //If there is text written, ask user if they would like to save
                if(!fileSaved && !(blankSpace.getText().equals(emptyDocument))) {
                    //Display dialog box 
                    Toolkit.getDefaultToolkit().beep();
                    int choice = JOptionPane.
                            showConfirmDialog(null, 
                            "File not saved. Save Changes?", window.getTitle(), 
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    //Handles the choices selected 
                    if(choice == JOptionPane.YES_OPTION) {
                        System.out.println("Save...");
                        saveDocument();
                    }
                    if (choice == JOptionPane.NO_OPTION) {
                        window.dispose();
                    }
                    if (choice == JOptionPane.CANCEL_OPTION) {
                        System.out.println("Closing process of program interrupted by user...");
                        window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    }
                    if (choice == JOptionPane.CLOSED_OPTION) {
                        window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    }
                }
                else {
                    window.dispose();
                }   
            }
         };
    }

    /**
     * Add the content to the newly created document from the selected open file
     * @param text from the selected file to open
     */
    public void setTextContent(String text) {
        blankSpace.append(text);
        blankSpace.append("\n");
    }

    /**
     * Set the window title of a newly created document (when user uses open feature)
     * @param title from the name of the selected open file
     */
    public void setWindowTitle(String title) {
        window.setTitle(title);
    }
    /**
     * Builds menu panel
     */
    public void buildMenuPanel() {
        
        //Create the menubar object
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(113, 181, 233 ));

        //Build the individual menu dropdownss
        buildFileMenu();
        buildEditMenu();
        buildFormatMenu();
        buildNightMenu();

        //Adding the menu dropdowns to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(nightMenu);

    }

    /**
     * Builds the file dropdown menu 
     */
    public void buildFileMenu() {
        //Construct the file menu dropdown
        fileMenu = new JMenu("File");

        //Create open option
        openItem = new JMenuItem("Open");
        //Set the keybinding to CTRL + O
        openItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openItem.addActionListener(new OpenListener());

        //Create save as option
        saveAsItem = new JMenuItem("Save As");
        //Set the keybinding to CTRL + S
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveAsItem.addActionListener(new SaveAsListener());

        //Create new option
        newItem = new JMenuItem("New");
        //Set the keybinding to CTRL + N
        newItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newItem.addActionListener(new NewListener());
            
        //Add the options to the menu
        fileMenu.add(newItem);
        fileMenu.addSeparator();
        fileMenu.add(openItem);
        fileMenu.add(saveAsItem);
    }

    /**
     * Builds the edit dropdown menu 
     */
    public void buildEditMenu() {
        //Construct the edit menu dropdown
        editMenu = new JMenu("Edit");

        //Create the Cut/Delete menu item...
        cutItem = new JMenuItem("Cut/Delete");
        //Set the keybinding to CTRL + X
        cutItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        //Override preset hotkey
        blankSpace.getInputMap().put(KeyStroke.getKeyStroke("control X"),
        "pressed");
        //Add actionlistener for keybinding
        cutItem.addActionListener(new CutListener());

        //Create copy menu item...
        copyItem = new JMenuItem("Copy");
        //Set the keybinding to CTRL + C
        copyItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        //Override preset hotkey
        blankSpace.getInputMap().put(KeyStroke.getKeyStroke("control C"),
        "pressed");
        //Add actionlistener for keybinding
        copyItem.addActionListener(new CopyListener());

        //Create paste menu item...
        pasteItem = new JMenuItem("Paste");
        //Set the keybinding to CTRL + V
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        //Override preset hotkey
        blankSpace.getInputMap().put(KeyStroke.getKeyStroke("control V"),
        "pressed");
        //Add actionlistener for keybinding
        pasteItem.addActionListener(new PasteListener());

        //Create undo menu item...
        undoItem = new JMenuItem("Undo");
        //Set the keybinding to CTRL + Z
        undoItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_Z, ActionEvent.CTRL_MASK));   
        //Add actionlistener for keybinding
        undoItem.addActionListener(new undoListener());

        //Create redo menu item...
        redoItem = new JMenuItem("Redo");
        //Set the keybinding to CTRL + R
        redoItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_R, ActionEvent.CTRL_MASK));
       //Add actionlistener for keybinding
        redoItem.addActionListener(new redoListener());

        //Add the menu items to the menu dropdown for EDIT
        editMenu.add(cutItem);
        editMenu.addSeparator();
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
    }

    /**
     * Builds the format dropdown menu 
     */
    public void buildFormatMenu() {
        formatMenu = new JMenu("Format");

        textWrap = new JCheckBoxMenuItem("Wrap Text", false);
        textWrap.addActionListener(new TextWrapper());

        fontItem = new JMenuItem("Fonts");
        fontItem.addActionListener(new FontListener());

        formatMenu.add(fontItem);
        formatMenu.add(textWrap);
    }
    private class FontListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            fontGUI fontScreen = new fontGUI(blankSpace); 
        }
    }
    private class TextWrapper implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(textWrap.isSelected()) {
                blankSpace.setLineWrap(true);
            }
            else {
                blankSpace.setLineWrap(false);
            }
        }
    }

    public void buildNightMenu() {
        nightMenu = new JMenu("Night");
        nightMode = new JCheckBoxMenuItem("Dark Mode", false);
        nightMode.addActionListener(new NightMode());

        nightMenu.add(nightMode);

    }

    private class NightMode implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(nightMode.isSelected()) {
                setNightMode();
            }
            else {
                setLightMode();
            }
        }
    }
    private class OpenListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Debugging to console
            System.out.println("Open file...");

            /* Create Open dialog box to allow user 
            *  to load text-file
            */
            JFileChooser openFile = new JFileChooser();
            //Set a filter for ONLY TEXT FILES
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text Files", "text", "txt");
            openFile.setFileFilter(filter);

            //Open the dialog box
            int status = openFile.showOpenDialog(null);

            //When user presses the OPEN button
            if(status == JFileChooser.APPROVE_OPTION) {
                File selectedFile = openFile.getSelectedFile();
                filePath = selectedFile.getPath();
                fileName = selectedFile.getName();
                //Method that handles file opening
                openTextFile(filePath, fileName);
            }
        }

        /**
         * Method that handles filling the new textbox with the content from the selected
         * file
         * @param filename the file name of the selected txt file
         */
        public void openTextFile(String filePath, String fileName) {

            try {
                //Create a file object with the location specified by filePath 
                File myFile = new File(filePath);
                //Need scanner to read contents from the file
                Scanner inputFile = new Scanner(myFile);
                //Scanner will store text in this variable
                String line;

                //Create a new textarea for the opened file
                textboxGUI newFile = new textboxGUI();
                /* Set the file name of the newly created textarea
                of that of the file name choosen by user 
                */
                newFile.setWindowTitle(fileName);
                //While the selected text file has content
                while(inputFile.hasNext()) {
                    line = inputFile.nextLine();
                    newFile.setTextContent(line);
                }
                inputFile.close();
            }
            //If there a wasn't a file found
            catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(null, "Cannot find file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class SaveAsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Debugging to console
            System.out.println("Save as file...");

            //Handle saving via method
            saveDocument();
        }
    }

    public void saveDocument() {
        /* Create Open dialog box to allow user 
        to load text-file
        */
        JFileChooser saveFile = new JFileChooser();
        //Set a filter for ONLY TEXT FILES
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Text Files", "text", "txt");
        saveFile.setFileFilter(filter);

        //Open the dialog box to save
        int status = saveFile.showSaveDialog(null);

        //If user chooses the SAVE Option 
        if(status == JFileChooser.APPROVE_OPTION) {
            File selectedFile = saveFile.getSelectedFile();
            filePath = selectedFile.getPath();
            fileName = selectedFile.getName();

            boolean override = false;
                if(selectedFile.exists()) {
                    UIManager.put("OptionPane.yesButtonText", "Override");
                    UIManager.put("OptionPane.noButtonText", "Append Text");
                    status = JOptionPane.showConfirmDialog(null, 
                        String.format("This file %s already exists. \n Append file? ", 
                        fileName), "File Found!", JOptionPane.YES_NO_CANCEL_OPTION);
                    if(status == JOptionPane.YES_OPTION) {
                        override = false;
                    }
                    if(status == JOptionPane.NO_OPTION) {
                        override = true;
                    }

                }
            /*Adding the contents of the current textarea to that of the 
            newly created file
            */
            try {
                FileWriter fwrite = new FileWriter(filePath, override);
                Scanner inputFile = new Scanner(blankSpace.getText());
                String line;
                PrintWriter outputFile = new PrintWriter(fwrite);
                //Read contents from current text area and add to the new file
                while(inputFile.hasNext()) {
                    line = inputFile.nextLine();
                    outputFile.println(line);
                }

                outputFile.close();
                inputFile.close();
                fileSaved = true;
                //Debuging purposes
                System.out.println("Save complete!");

                //End program after saving...
                System.exit(0);
            }
            //If an error occurred
            catch (IOException execption) {
                JOptionPane.showMessageDialog(null, 
                    "Error saving document", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Inner class that handles what the CTRL + N function does.
     * (Creates a new document)
     */
    private class NewListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Debugging to console
            System.out.println("New file...");

            //Create a new blankspace JFrame 
            new textboxGUI(); 
        }
    }

    /**
     * Inner class that handles what the CTRL + X function does.
     * (Cuts selected text)
     */
    private class CutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Replace the selected text with an empty string
            blankSpace.replaceSelection("");

            //Debugging to console
            System.out.println("Cut selected contents...");
        }
    }

    /**
     * Inner class that handles what the CTRL + C function does.
     * (Copies selected text)
     */
    private class CopyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Debugging to console
            System.out.println("Copy selected contents...");

            //Get the selected text and save it to the selection string
            selection = blankSpace.getSelectedText();

            StringSelection stringSelection = new StringSelection(selection);
            clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            
            //Debugging to console
            System.out.println(selection);
        }
    }

    /**
     * Inner class that handles what the CTRL + V function does.
     * (Pastes selected text)
     */
    private class PasteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Debugging to console
            System.out.println("Paste selected contents...");
            System.out.println(selection);
            try {
                String result = (String) clipboard.getData(DataFlavor.stringFlavor);
                //Add the copied contents from the selection string to blankspace
                blankSpace.append(result);
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null, errorMessage, "Error Pasting", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Inner class that handles what the CTRL + Z function does.
     * (Undo text)
     */
    private class undoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Undoing text...");
            try {
                if(undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
            catch (CannotUndoException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * Inner class that handles what the CTRL + R function does.
     * (Redo text)
     */
    private class redoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Redoing text...");
            try {
                if(undoManager.canRedo()) {
                    undoManager.redo();
                }
            }
            catch (CannotUndoException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
    /**
     * Method that will set the text area to night
     */
    public void setNightMode() {
        scrollBar.getViewport().setBackground(Color.BLACK);
        blankSpace.setForeground(Color.WHITE);
    }
    /**
     * Method that will set the text area back to normal
     */
    public void setLightMode() {
        scrollBar.getViewport().setBackground(Color.WHITE);
        blankSpace.setForeground(Color.BLACK);
    }
}
