package Utils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Набор функций для работы с оконными приложениями с ипользованием Swing
 * @author Дмитрий Соломатин (кафедра ПиИТ ФКН ВГУ)
 */
public class SwingUtils {

    /**
     * Показать диалоговое окно с сообщением об ошибке
     * @param message Сообщение
     * @param title Заголовок окна
     * @param ex Иcключение
     */
    public static void showErrorMessageBox(String message, String title, Throwable ex) {
        StringBuilder sb = new StringBuilder(ex.toString());
        if (message != null) {
            sb.append(message);
        }
        if (ex != null) {
            sb.append("\n");
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString(), title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Показать диалоговое окно с сообщением об ошибке
     * @param message Сообщение
     * @param ex Иcключение
     */
    public static void showErrorMessageBox(String message, Throwable ex) {
        showErrorMessageBox(message, "Ошибка", ex);
    }

    /**
     * Показать диалоговое окно с сообщением об ошибке
     * @param ex Иcключение
     */
    public static void showErrorMessageBox(Throwable ex) {
        showErrorMessageBox(null, ex);
    }

    /**
     * Установка темы отображения (LookAndFeel) для элементов управления
     * @param name Название темы
     */
    public static void setLookAndFeelByName(String name) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (name.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    for (Window window : Window.getWindows()) {
                        Dimension size = window.getSize();
                        SwingUtilities.updateComponentTreeUI(window);
                        window.pack();
                        window.setSize(size);
                    }
                    break;
                }
            }
        } catch (Exception ex) {
            Class inner = new Object() {}.getClass();
            Logger.getGlobal().logp(Level.SEVERE, inner.getEnclosingClass().getName(), inner.getEnclosingMethod().getName(), null, ex);
        }
    }

    /**
     * Заполнение меню JMenu элементами для выбора тем отображения (LookAndFeel)
     * @param menu Меню для добавления пунктов меню
     */
    public static void initLookAndFeelMenu(JMenu menu) {
        ActionListener actionListener = (ActionEvent e) -> {
            JMenuItem menuItem = (JMenuItem) e.getSource();
            SwingUtils.setLookAndFeelByName(menuItem.getText());
        };
        ButtonGroup group = new ButtonGroup();
        LookAndFeel currentLookAndFeel = UIManager.getLookAndFeel();
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                continue;
            }
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem();
            menuItem.setText(info.getName());
            menuItem.setSelected(info.getName().equals(currentLookAndFeel.getName()));
            menuItem.addActionListener(actionListener);
            group.add(menuItem);
            menu.add(menuItem);
        }
    }

    /**
     * Установка шрифта по умолчанию для элементов управления
     * @param fontName Название шрифта (null - не менять)
     * @param size Размер шрифта (меньше или равно 0 - не менять)
     */
    public static void setDefaultFont(String fontName, int size) {
        UIManager.getDefaults().entrySet().forEach((entry) -> {
            Object value = UIManager.get(entry.getKey());
            if (value != null && value instanceof FontUIResource) {
                FontUIResource fr = (FontUIResource) value;
                fr = new FontUIResource(
                        (fontName != null) ? fontName : fr.getFontName(),
                        fr.getStyle(),
                        (size > 0) ? size : fr.getSize()
                );
                UIManager.put(entry.getKey(), fr);
            }
        });
    }

}
