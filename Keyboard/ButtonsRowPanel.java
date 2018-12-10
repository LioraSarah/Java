import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

/* Panel of one row of buttons */
public class ButtonsRowPanel extends JPanel{

	private static final Color DARK_PURPLE = new Color(110,110,255);
	private static final Color LIGHT_PURPLE= new Color(174,174,253);

	private KeyboardPanel _parent;
	private ArrayList<JButton> _shiftKeys = new ArrayList<JButton>();
	
	/* letters[] is a list of strings that are the labels of the keyboard buttons.
	 * Each such string can have the form "X/Y" 
	 * where X is the letter to be used when the Shift button is not pressed 
	 * and Y is the letter to be used when the Shift button is pressed.
	 * The string can also be a name of a functional button - Shift, Caps, Enter, Backspace
	 */
    public ButtonsRowPanel(String letters[], KeyboardPanel parent) {
    	_parent = parent;
    	KeyListener listener = new KeyListener();
    	
    	for (String let: letters) {
    		JButton btn = new JButton(let);
            btn.setBackground(LIGHT_PURPLE);
            if (let.equals("Shift")) {
            	_shiftKeys.add(btn);
            }
            if (let.equals(" ")) {
            	btn.setPreferredSize(new Dimension(450, 25));;
            }
            btn.addActionListener(listener);
    		add(btn);
    	}
    	
    }

    /* Perform different actions depending on the button pressed */
    private class KeyListener implements ActionListener {

		String s, old_text, new_text;
		JButton sourceBtn;

		@Override
		public void actionPerformed(ActionEvent e) {
			sourceBtn = (JButton)e.getSource();
			s = e.getActionCommand();
			old_text = _parent.getText();
			new_text = "";

			if (handleBackspace(s))
				return;

			if (handleShift(s))
				return;

			if (handleCaps(s))
				return;
			
			if (s.equals("Tab")) {
				s = "\t";
			}
			
			if (s.equals("Enter")) {
				s = "\n";
			}
			
			if (s.length() > 1 && s.charAt(1) == '/'){
				
				if (_parent.isShift()){
					// If Shift is pressed, use the second character in the button name
					s = "" + s.charAt(2);
				} else {
					s = "" + s.charAt(0);
				}
			}

			// If Shift or Caps are pressed, use the uppercase letter, otherwise lowercase
			if (Character.isLetter(s.charAt(0)) && !(_parent.isShift()) && !(_parent.isCaps())) {
				new_text = old_text + s.toLowerCase();
			} else {
				new_text = old_text + s;
			}
			
			_parent.setText(new_text);
		}

		/* If button name is "Backspace", delete last lette from text */
		private boolean handleBackspace(String s) {
			if (s.equals("Backspace")) {
				if (old_text != null){
					int len = old_text.length();
					if (len > 0){
						StringBuilder sb = new StringBuilder(old_text);
						new_text = sb.deleteCharAt(len-1).toString();
						_parent.setText(new_text);
					}
				}
				return true;
			}
			return false;
		}
		
		/* If button name is "Shift", toggle inner state and also Shift buttons color */
		private boolean handleShift(String s) {
			if (s.equals("Shift")) {
				if(_parent.isShift()) {
					_parent.setShift(false);
					for (JButton k : _shiftKeys){
						k.setBackground(LIGHT_PURPLE);
					}
				} else {
					_parent.setShift(true);
					for (JButton k : _shiftKeys){
						k.setBackground(DARK_PURPLE);
					}
				}
				return true;
			}
			return false;
		}
		
		/* If button name is "Shift", toggle inner state and also button color */
		private boolean handleCaps(String s) {
			if (s.equals("Caps")) {
				if(_parent.isCaps()){
					_parent.setCaps(false);
					sourceBtn.setBackground(LIGHT_PURPLE);
				}
				else {
					_parent.setCaps(true);
					sourceBtn.setBackground(DARK_PURPLE);
				}
				return true;
			}
			return false;			
		}
    }
}
