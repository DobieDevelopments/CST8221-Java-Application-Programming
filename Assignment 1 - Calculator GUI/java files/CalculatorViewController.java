package calculator;

/*
 * File Header File name: [CalculatorViewController.java ] 
 * Author: [ John Dobie, 040 659 609]
 * Course: CST8221 _ Java Application Programming 
 * Lab Section: [302] 
 * Assignment: [2] 
 * Date: [November 6th, 2018] 
 * Professor: [Daniel Cormier] 
 * Purpose: [To display and hold the functionality for a GUI driven calculator application] 
 * Class list: [CalculatorViewController, Controller]
 */

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import calculator.CalculatorModel.OperationalMode;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 * This class carries the creation of the UI and implementation of user interaction for the
 * calculator app.
 * 
 * @author John Dobie
 * @version 2.0
 * @see calculator
 * @since 1.8.0_121
 */
public class CalculatorViewController extends JPanel {


  /**
   * serialVersionUID = {@value}
   */
  private static final long serialVersionUID = 1191234254050258610L;

  /**
   * 
   */
  private JTextField display1;

  /**
   * 0.0
   */
  private JTextField display2;

  /**
   * F
   */
  private JLabel error;

  /**
   * .
   */
  private JButton dotButton;

  /**
   * {@value}
   */
  private final String backspace = "\u21da";

  /**
   * {@value}
   */
  private final String plusminus = "\u00B1";

  /**
   * <pre>
   *  { "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", \u00B1, "+" }
   * </pre>
   */
  private final String[] NUMS =
      {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", plusminus, "+"};

  /**
   * 
   */
  private JPanel errorPanel;

  /**
   * The current operating state of the calculator.
   */
  enum OperatorState {
    /**
     * Calculator state where nothing has been pressed yet.
     */
    EMPTY,
    /**
     * Calculator state for accepting operand 1
     */
    NUM1,
    /**
     * Calculator state for accepting the operation
     */
    OPERATION,
    /**
     * Calculator state for accepting operand 2
     */
    NUM2, 
    /**
     * Calculator state for accepting the next operation
     */
    NEXTOPERATION
  }

  /**
   * The constructor for our CalculatorViewController class, which sets up our UI with default
   * values and functionality.
   */
  public CalculatorViewController() {
    // INITIAL SETUP
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

    int width = getBounds().width; // the width of our calculator's window
    int height = getBounds().height; // the height of our calculator's window
    int x = (screen.width - width) / 2; // the x coordinate for our calculator's window
    int y = (screen.height - height) / 2; // the y coordinate for our calculator's window
    setBounds(x, y, width, height);

    short defaultInset = 5; // the base value for all insets or border thicknesses
    setBorder(BorderFactory.createMatteBorder(defaultInset, defaultInset, defaultInset,
        defaultInset, Color.BLACK));
    setLayout(new BorderLayout());
    // END INITIAL SETUP

    // PANEL DECLARATIONS
    JPanel topPanel = new JPanel(); // the top panel for holding our error panel, backspace button,
                                    // and text display fields
    topPanel.setLayout(new BorderLayout());
    topPanel.setBackground(Color.YELLOW);

    errorPanel = new JPanel(new BorderLayout());
    errorPanel.setBackground(Color.YELLOW);
    errorPanel.setPreferredSize(new Dimension(44, 55));
    errorPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, defaultInset, Color.BLACK));

    JPanel displayPanel = new JPanel(); // the panel that holds the two text fields for displaying
                                        // our calculations
    displayPanel.setLayout(new BorderLayout());
    displayPanel.setBackground(Color.WHITE);

    JPanel modePanel = new JPanel(); // the panel that holds our mode selection buttons (check box /
                                     // radio buttons)
    modePanel.setLayout(new BorderLayout());
    modePanel.setBackground(Color.BLACK);
    modePanel.setBorder(
        BorderFactory.createMatteBorder(defaultInset * 2, 0, defaultInset * 2, 0, Color.BLACK));

    JPanel radioPanel = new JPanel(); // the panel that holds only the three radio mode selection
                                      // buttons
    radioPanel.setLayout(new GridLayout(1, 3));
    radioPanel.setBackground(Color.BLACK);
    radioPanel.setBackground(Color.YELLOW);

    JPanel midpanel = new JPanel(); // the panel that holds the clear buttons and then number pad
                                    // panel
    midpanel.setLayout(new BorderLayout());
    midpanel.setBackground(Color.BLACK);
    midpanel
        .setBorder(BorderFactory.createMatteBorder(0, defaultInset, 0, defaultInset, Color.BLACK));
    // END PANEL DECLARATIONS

    /* Labels */
    // DISPLAY / ERROR LABEL
    error = new JLabel(); // the label for notifying the user if there is an error with the
                          // calculator
    error.setPreferredSize(new Dimension(44, 55));
    error.setText("F");
    error.setHorizontalAlignment(JLabel.CENTER);
    error.setVerticalAlignment(JLabel.CENTER);
    error.setFont(new Font(getFont().getFontName(), Font.BOLD, 20));

    /* Buttons */
    ActionListener buttonActionListener =
        new Controller(); /*
                           * our action listener that all buttons of the calculator will use.
                           */
    // BACKSPACE BUTTON
    JButton backspace_btn = /*
                             * the backspace button for our calculator, that works with the mouse
                             * and the (alt+B) keyboard shortcut, placed on the topPanel
                             */
        createButton("Backspace", "Backspace", Color.BLACK, Color.YELLOW, buttonActionListener);
    backspace_btn.setOpaque(false);
    backspace_btn.setContentAreaFilled(false);
    backspace_btn.setBorder(BorderFactory.createMatteBorder(0, defaultInset, 0, 1, Color.BLACK));
    backspace_btn.setText(backspace);
    backspace_btn.setPreferredSize(new Dimension(44, 55));
    backspace_btn.setToolTipText("Backspace (Alt+B)");
    backspace_btn.setMnemonic('B');

    JButton topclear_btn = // the upper clear button of our calculator, placed on the midPanel
        createButton("clear", "Clear", Color.BLACK, Color.RED, buttonActionListener);
    topclear_btn.setText("C");

    JButton botclear_btn = // the lower clear button of our calculator, placed on the midPanel
        createButton("clear", "Clear", Color.BLACK, Color.RED, buttonActionListener);
    botclear_btn.setText("C");

    JButton leftequals_btn = /*
                              * the leftmost equals button of our calculator, placed on the
                              * calculator frame itself
                              */
        createButton("equals", "Equals", Color.BLACK, Color.YELLOW, buttonActionListener);
    leftequals_btn.setPreferredSize(new Dimension(40, 390));
    leftequals_btn.setMargin(new Insets(defaultInset, defaultInset, defaultInset, defaultInset));
    leftequals_btn.setText("=");

    JButton rightequals_btn = /*
                               * the rightmost equals button of our calculator, placed on the
                               * calculator frame itself
                               */
        createButton("equals", "Equals", Color.BLACK, Color.YELLOW, buttonActionListener);
    rightequals_btn.setPreferredSize(new Dimension(40, 390));
    rightequals_btn.setMargin(new Insets(defaultInset, defaultInset, defaultInset,
        defaultInset)); /*
                         * modify the text box inset to be able to hold the text without displaying
                         * "..."
                         */
    rightequals_btn.setText("=");

    JPanel btnPanel =
        new JPanel(new GridLayout(4, 4)); /*
                                           * the panel that the numberpad buttons are placed on
                                           */
    btnPanel.setBorder(BorderFactory.createEmptyBorder());
    /*
     * loop through the array of String values [NUMS]to use for the numberpad buttons and create
     * appropriate buttons for each of them.
     */
    for (int i = 0; i < NUMS.length; i++) {
      JButton btn = null;
      if ((i + 1) % 4 == 0) {
        btnPanel.add(NUMS[i], btn = createButton(NUMS[i], NUMS[i], Color.BLACK, Color.CYAN,
            buttonActionListener)); /* the 4 operation buttons, + - * / */
      } else if (NUMS[i].equals(plusminus)) {
        btnPanel.add(NUMS[i], btn = createButton(NUMS[i], NUMS[i], Color.BLACK, Color.PINK,
            buttonActionListener)); /*
                                     * the plus minus button
                                     */
      } else if (NUMS[i].equals(".")) {
        dotButton = btn = createButton(NUMS[i], NUMS[i], Color.BLACK, Color.BLUE,
            buttonActionListener); /*
                                    * the dot button
                                    */
        btnPanel.add(NUMS[i], dotButton);
      } else {
        btnPanel.add(NUMS[i], btn = createButton(NUMS[i], NUMS[i], Color.BLACK, Color.BLUE,
            buttonActionListener)); /*
                                     * the number buttons [0-9]
                                     */
      }
      btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }

    /* Text Fields */
    // DISPLAY 1&2 TEXTFIELDS
    display1 = new JTextField(
        16); /*
              * the top text display field, placed on the displayPanel on the topPanel
              */
    display1.setPreferredSize(new Dimension(display1.getPreferredSize().width, 30));
    display1.setText("");
    display1.setBackground(Color.WHITE);
    display1.setEditable(false);
    display1.setHorizontalAlignment(SwingUtilities.RIGHT);
    // display1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    display1.setBorder(BorderFactory.createEmptyBorder());

    display2 = new JTextField(
        16); /*
              * the bottom text display field, placed on the displayPanel on the topPanel
              */
    display2.setPreferredSize(new Dimension(display2.getPreferredSize().width, 30));
    display2.setBackground(Color.WHITE);
    display2.setEditable(false);
    display2.setHorizontalAlignment(SwingUtilities.RIGHT);
    display2.setText("0.0");
    display2.setBorder(BorderFactory.createEmptyBorder());

    // CHECKBOXES
    ButtonGroup modebtnGroup =
        new ButtonGroup(); /*
                            * the button group that holds all of the mode selection buttons
                            * (checkbox / radio buttons) so that we can only select one at a time.
                            */
    JCheckBox modeCheckBox = new JCheckBox("Int", false); // the mode checkbox for the [Int] mode
    modeCheckBox.setBackground(Color.GREEN);
    modeCheckBox.setActionCommand("Int");
    modeCheckBox.addActionListener(buttonActionListener);

    // RADIO BUTTONS
    JRadioButton radiobtnInt =
        new JRadioButton(".0", false); /*
                                        * the mode radio button for [0.0] precision, placed on the
                                        * radio panel on the mode panel
                                        */
    radiobtnInt.setBackground(Color.YELLOW);
    radiobtnInt.setActionCommand("Integer");
    radiobtnInt.addActionListener(buttonActionListener);

    JRadioButton radiobtnFloat =
        new JRadioButton(".00", true); /*
                                        * the mode radio button for [0.00] precision, selected by
                                        * default, placed on the radio panel on the mode panel
                                        */
    radiobtnFloat.setBackground(Color.YELLOW);
    radiobtnFloat.setActionCommand("Float");
    radiobtnFloat.addActionListener(buttonActionListener);

    JRadioButton radiobtnSci =
        new JRadioButton("Sci", false); /*
                                         * the mode radio button for [scientific] precision, placed
                                         * on the radio panel on the mode panel
                                         */
    radiobtnSci.setBackground(Color.YELLOW);
    radiobtnSci.setActionCommand("Scientific");
    radiobtnSci.addActionListener(buttonActionListener);

    modebtnGroup.add(modeCheckBox);
    modebtnGroup.add(radiobtnFloat);
    modebtnGroup.add(radiobtnInt);
    modebtnGroup.add(radiobtnSci);

    // ADD PANELS
    displayPanel.add(display1, BorderLayout.NORTH);
    displayPanel.add(display2, BorderLayout.SOUTH);

    errorPanel.add(error);

    topPanel.add(errorPanel, BorderLayout.WEST);
    topPanel.add(displayPanel, BorderLayout.CENTER);
    topPanel.add(backspace_btn, BorderLayout.EAST);
    topPanel.add(modePanel, BorderLayout.SOUTH);

    modePanel.add(modeCheckBox, BorderLayout.WEST);
    radioPanel.add(radiobtnInt);
    radioPanel.add(radiobtnFloat);
    radioPanel.add(radiobtnSci);
    modePanel.add(radioPanel, BorderLayout.EAST);

    midpanel.add(topclear_btn, BorderLayout.NORTH);
    midpanel.add(botclear_btn, BorderLayout.SOUTH);
    midpanel.add(btnPanel, BorderLayout.CENTER);

    this.add(topPanel, BorderLayout.NORTH);
    this.add(midpanel, BorderLayout.CENTER);
    this.add(leftequals_btn, BorderLayout.WEST);
    this.add(rightequals_btn, BorderLayout.EAST);
    setVisible(true);
  }

  /**
   * description
   * 
   * @param text the text that the created button will display
   * @param actionCommand the action command to set for the button we will create
   * @param foreground the text colour of the button
   * @param background the background colour of the button
   * @param handler the event handler that the button will send events to
   * @return the new button created with these parameter values
   */
  private JButton createButton(String text, String actionCommand, Color foreground,
      Color background, ActionListener handler) {
    JButton newButton = new JButton(text); /*
                                            * the new button that we are creating that we will
                                            * return once it is setup appropriately
                                            */

    if (actionCommand != null) // set the button's action command as long as it was specified.
      newButton.setActionCommand(actionCommand);

    newButton
        .setFont(new Font(newButton.getFont().getFontName(), newButton.getFont().getStyle(), 20));
    newButton.setForeground(foreground);
    newButton.setBackground(background);
    newButton.addActionListener(handler);

    return newButton;
  }

  /**
   * the event handler inner class for our calculator
   * 
   * @author John Dobie 040 659 609
   * @version 2.0
   * @see calculator
   * @since 1.8.0_121
   */
  private class Controller implements ActionListener {
    /**
     * OperatorState.EMPTY
     */
    OperatorState state = OperatorState.EMPTY;
    /**
     * OperationalMode.FLOAT_MODE
     */
    CalculatorModel.OperationalMode mode = OperationalMode.FLOAT_MODE;
    /**
     * 
     */
    CalculatorModel model = new CalculatorModel();

    /**
     * false
     */
    boolean scientific = false;

    /**
     * false
     */
    boolean dotClicked = false;

    /**
     * the method called when an event is triggered by a UI element in the outer class
     * 
     * @param e the event triggered by an UI element that we can handle inside this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
      /*
       * switch depending on the event given's action command to differentiate between buttons to
       * perform specific tasks, in this case modifying the display field's text value
       */
      String cmd = e.getActionCommand();
      switch (cmd) {
        case "Backspace": /* Backspace */
          int len = display1.getText().length();
          int len2 = display2.getText().length();
          if (state != OperatorState.EMPTY) {
            if (state == OperatorState.NUM1) { /* Backspace_ Operand 1 */
              if (len2 > 1) {
                display2.setText(display2.getText().substring(0, len2 - 1));
              } else {
                display2.setText("0");
              }
              model.setOperand1(display2.getText());
              if (len2 == 0)
                state = OperatorState.EMPTY;

            } else if (state == OperatorState.OPERATION) { /* Backspace_ Operation */
              display1.setText(display1.getText().substring(0, len - 1));
              state = OperatorState.NUM1;
              model.setOperation(CalculatorModel.Operation.NONE);
            } else if (state == OperatorState.NUM2) { /* Backspace_ Operand 2 */
              if (scientific) {
                float num = Float.parseFloat(display2.getText());
                num = Math.round(num) / 10;
                display2.setText(String.format("%.6e", num));

                if (num == 0)
                  state = OperatorState.EMPTY;
              } else {
                if (len2 > 0) {
                  display2.setText(display2.getText().substring(0, len2 - 1));
                }
                if (len > 0)
                  if ((display1.getText().charAt(len2 - 1) == '+')
                      || (display1.getText().charAt(len2 - 1) == '-')
                      || (display1.getText().charAt(len2 - 1) == '*')
                      || (display1.getText().charAt(len2 - 1) == '/')) {
                    state = OperatorState.OPERATION;
                    display2.setText("0");
                  } else if (len2 > 1) {
                    display2.setText(display2.getText().substring(0, len2 - 1));
                  } else {
                    display2.setText("0");
                  }
              }
            } else if (state == OperatorState.NEXTOPERATION) { /* Backspace_ Operation */
              display1.setText(display1.getText().substring(0, len - 1));
              state = OperatorState.NUM2;
            }
            if (!display2.getText()
                .contains(".")) /* Backspace_ Reset dot button if we removed it */
              dotClicked = false;
          }
          return; // Backspace End
        case "Equals": /* Equals */
          if (model.getErrorState()) /* Equals_ do not perform in error state */
            return;
          display1.setText("");
          if (state == OperatorState.NUM2) { /* Equals_ Both operands are filled */
            model.setOperand2(display2.getText());
            state = OperatorState.EMPTY;
          } else if (state == OperatorState.NUM1
              || state == OperatorState.EMPTY) { /* Equals_ Only one operand is set */
            model.setOperand1(display2.getText());
            state = OperatorState.EMPTY;
          } else if (state == OperatorState.OPERATION
              || state == OperatorState.NEXTOPERATION) { /*
                                                          * Equals_ Operand 2 is not set, but
                                                          * operation is set
                                                          */
            model.setOperand2(display2.getText());
          }
          if (!scientific) {
            display2.setText(model.getResult());
            dotClicked = false;
          } else
            display2.setText(String.format("%6.6e", Float.parseFloat(
                model.getResult()))); /* Equals_ display the result in scientific notation */
          
          if (model.getErrorState()) { /* Error mode set as a result of calculations */
            error.setText("E");
            errorPanel.setBackground(Color.RED);
            if (model.getError() == CalculatorModel.Error.INF)
              display2.setText("Error: Cannot divide by zero.");
            else if (model.getError() == CalculatorModel.Error.NAN)
              display2.setText("Error: Undefined.");
          }
          else if(display2.getText().length() > 35)
          {
            display2.setText("Result too long to display.");
          }
          return; // Equals End
        case "Clear": /* Clear button, reset calculator, and error mode if set */
          state = OperatorState.EMPTY;
          model.setOperand1("0");
          model.setOperand2("0");
          model.setErrorState(false);
          model.setOperation(CalculatorModel.Operation.NONE);
          display1.setText("");
          display2.setText(model.getResult());
          if (mode == OperationalMode.INT_MODE) {
            error.setText("I");
            errorPanel.setBackground(Color.GREEN);
          } else if (mode == OperationalMode.FLOAT_MODE) {
            error.setText("F");
            errorPanel.setBackground(Color.YELLOW);
          }
          dotClicked = false;
          return; // Clear End
        case "Int": /* Int_ integer mode, no decimals */
          if (mode == OperationalMode.INT_MODE) { /*
                                                   * handle if we double click the Int mode
                                                   * checkbox, effectively unchecking the box.
                                                   */
            errorPanel.setBackground(Color.YELLOW);
            error.setText("F");
            scientific = false;
            mode = OperationalMode.FLOAT_MODE;
            model.setMode(mode);
            model.setPrecision(2);
            state = OperatorState.EMPTY;
            model.setOperand1("0.00");
            model.setOperand2("0.00");
            model.setOperation(CalculatorModel.Operation.NONE);
            display1.setText("");
            display2.setText("0.0");
            dotButton.setBackground(Color.BLUE);
            dotButton.setEnabled(true);
            return;
          } /* Int mode */
          errorPanel.setBackground(Color.GREEN);
          error.setText("I");
          scientific = false;
          model.setPrecision(0);
          mode = OperationalMode.INT_MODE;
          model.setMode(mode);
          state = OperatorState.EMPTY;
          model.setOperand1("0");
          model.setOperand2("0");
          model.setOperation(CalculatorModel.Operation.NONE);
          display1.setText("");
          display2.setText("0");
          dotButton.setBackground(new Color(178, 156, 250));
          dotButton.setEnabled(false);
          return; // Int mode End
        case "Integer": /* Floating point mode 1, 0.0 */
          errorPanel.setBackground(Color.YELLOW);
          error.setText("F");
          scientific = false;
          mode = OperationalMode.FLOAT_MODE;
          model.setMode(mode);
          model.setPrecision(1);
          state = OperatorState.EMPTY;
          model.setOperand1("0");
          model.setOperand2("0");
          model.setOperation(CalculatorModel.Operation.NONE);
          display1.setText("");
          display2.setText("0");
          dotButton.setBackground(Color.BLUE);
          dotButton.setEnabled(true); // Single decimal mode End
          return;
        case "Float": /* Floating point mode 2 0.00 */
          errorPanel.setBackground(Color.YELLOW);
          error.setText("F");
          scientific = false;
          mode = OperationalMode.FLOAT_MODE;
          model.setMode(mode);
          model.setPrecision(2);
          state = OperatorState.EMPTY;
          model.setOperand1("0.00");
          model.setOperand2("0.00");
          model.setOperation(CalculatorModel.Operation.NONE);
          display1.setText("");
          display2.setText("0.0");
          dotButton.setBackground(Color.BLUE);
          dotButton.setEnabled(true);
          return; // Double decimal mode End
        case "Scientific": /* Floating point mode 3, scientific notation 0.000000e00 */
          errorPanel.setBackground(Color.YELLOW);
          error.setText("F");
          scientific = true;
          mode = OperationalMode.FLOAT_MODE;
          model.setMode(mode);
          model.setPrecision(3);
          state = OperatorState.EMPTY;
          model.setOperand1("0");
          model.setOperand2("0");
          model.setOperation(CalculatorModel.Operation.NONE);
          display1.setText("");
          display2.setText("0");
          dotButton.setBackground(Color.BLUE);
          dotButton.setEnabled(true);
          return; // Scientific notation mode End
        default:
          break;
      }
      /*
       * loop through each button in the number pad area, and if it is pressed then we modify the
       * display2 text field to reflect that it was pressed.
       */
      for (int i = 0; i < NUMS.length; ++i) {
        if (model.getErrorState()) /* Numberpad_ not operational during an error state */
          return;
        if (e.getActionCommand() == NUMS[i]) {
          if ((i + 1) % 4 == 0) { /* Numberpad_ Operations */
            if (state == OperatorState.NUM1 || state == OperatorState.NUM2) {
              if (state == OperatorState.NUM1 || state == OperatorState.EMPTY) {
                state = OperatorState.OPERATION;
                model.setOperand1(display2.getText());
              } else if (state == OperatorState.NUM2) {
                state = OperatorState.NEXTOPERATION;
                model.setOperand2(display2.getText());
              }
              switch (NUMS[i]) {
                case "+":
                  model.setOperation(CalculatorModel.Operation.ADD);
                  break;
                case "-":
                  model.setOperation(CalculatorModel.Operation.SUBTRACT);
                  break;
                case "*":
                  model.setOperation(CalculatorModel.Operation.MULTIPLY);
                  break;
                case "/":
                  model.setOperation(CalculatorModel.Operation.DIVIDE);
                  break;
                default:
                  model.setOperation(CalculatorModel.Operation.NONE);
                  break;
              }
              if (mode == OperationalMode.INT_MODE) {
                int currentNumber = Integer.parseInt(display2.getText());
                display1.setText(currentNumber + NUMS[i]);
              } else if (mode == OperationalMode.FLOAT_MODE) {
                float currentNumber = Float.parseFloat(display2.getText());
                display1.setText(currentNumber + NUMS[i]);
              }
            }
            break; // Operations end
          } else if (NUMS[i].equals(plusminus)) { /* Numberpad_ PlusMinus button */
            if (state == OperatorState.NUM1 || state == OperatorState.NUM2) {
              if (mode == OperationalMode.FLOAT_MODE) {
                float temp = Float.parseFloat(display2.getText());
                temp *= -1;
                display2.setText(Float.toString(temp));
              } else if (mode == OperationalMode.INT_MODE) {
                int temp = Integer.parseInt(display2.getText());
                temp *= -1;
                display2.setText(Integer.toString(temp));
              }
              if (state == OperatorState.NUM1)
                model.setOperand1(display2.getText());
              else if (state == OperatorState.NUM2)
                model.setOperand2(display2.getText());
            }
            break; // plus minus button end
          } else if (NUMS[i].equals(".")) { /* Numberpad_ decimal dot button */
            if (mode == OperationalMode.FLOAT_MODE) {
              if (!display2.getText().contains(NUMS[i])) {
                display2.setText(display2.getText() + NUMS[i]);
                dotClicked = true;
              }
            }
            break; // dot button end
          } else { /* Numberpad_ number buttons */
            String f = display2.getText();
            if (state == OperatorState.OPERATION) {
              state = OperatorState.NUM2;
              f = NUMS[i];
            } else if (state == OperatorState.NEXTOPERATION) {
              state = OperatorState.NUM2;
              f = NUMS[i];
            } else if (state == OperatorState.EMPTY) {
              f = NUMS[i];
              state = OperatorState.NUM1;
              model.setOperand1(display2.getText());
            } else {
              BigDecimal num = new BigDecimal(display2.getText());
              if (dotClicked) {
                num.stripTrailingZeros();
                StringBuilder sBuilder = new StringBuilder(num.toEngineeringString());
                if (!num.toEngineeringString().contains(".")) {
                  sBuilder.append(".");
                }
                sBuilder.append(NUMS[i]);
                f = sBuilder.toString();
              } else
                f = display2.getText() + NUMS[i];
            }
            if(f.length() >= 35)
            display2.setText(f.substring(0, 35));
            else 
              display2.setText(f);
            break;
          } // number pad end
        }
      }
      requestFocusInWindow();
    }
  }
}
