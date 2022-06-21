package ru.vsu.cs.kunakhova_a_y;


import Utils.JTableUtils;
import Utils.LinkedListUtils;
import Utils.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FrameMain extends JFrame {
    private final JFileChooser fileChooserOpen;
    private JTable tableInput;
    private JTable tableOutput;
    private JButton buttonInputFile;
    private JButton buttonTransformList;
    private JPanel PanelMain;

    public FrameMain() {
        this.setTitle("Task 2");
        this.setContentPane(PanelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(700, 200, 300, 300);
        this.pack();

        JTableUtils.initJTableForArray(tableInput, 40, true, true, true, true);
        JTableUtils.initJTableForArray(tableOutput, 40, true, true, true, true);
        tableInput.setRowHeight(25);
        tableOutput.setRowHeight(25);

        fileChooserOpen = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);

        JMenuBar menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        JMenu menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вариант 14");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        this.pack();

        buttonInputFile.addActionListener(actionEvent -> {
            try {
                if (fileChooserOpen.showOpenDialog(PanelMain) == JFileChooser.APPROVE_OPTION) {
                    MyLinkedList<Integer> LList = LinkedListUtils.readLListFromFile(fileChooserOpen.getSelectedFile().getPath());
                    JTableUtils.writeArrayToJTable(tableInput, LinkedListUtils.convertToIntArray(LList));
                }
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
        buttonTransformList.addActionListener(actionEvent -> {
            try {
                MyLinkedList<Integer> linkedList = LinkedListUtils.readLListFromJTable(tableInput);
                linkedList = linkedList.transformList();
                JTableUtils.writeArrayToJTable(tableOutput, LinkedListUtils.convertToIntArray(linkedList));
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
    }
}