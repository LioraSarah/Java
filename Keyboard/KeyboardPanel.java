import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/* Implements the keyboard panel (the letters buttons) */
public class KeyboardPanel extends JPanel{

	private static final Color LAVENDER = new Color(230,230,250); // the background color of the keyboard
	
	private String _buttonLables[][] = {{"`/~", "1/!", "2/@", "3/#", "4/$", "5/%", "6/^", "7/&", "8/*", "9/(", "0/)", "-/_", "=/+", "Backspace"},
								  {"Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[/{", "]/}", "\\/|"},
								  {"Caps" , "A", "S", "D", "F", "G", "H", "J", "K", "L", ";/:", "'/\"", "Enter"},
								  {"Shift", "Z", "X", "C", "V", "B", "N", "M", ",/<", "./>", "//?", "Shift"}, 
								  {" "}};
	
	private boolean _shiftFlag = false;
	private boolean _capsFlag = false;
	private MainFrame _parent;
	
	public KeyboardPanel(MainFrame parent) {
		
		setLayout(new GridLayout(5, 1, 1, 1));
		_parent = parent;
		
		/* The keyboard is made out of 5 different panels, each panel implements a row of key buttons */
		/* We go through the key buttons rows, creating each row with the specific keys from the letters array,
		 * and adding each row to the keyboard panel */
		for (String[] row: _buttonLables){
			ButtonsRowPanel rPanel = new ButtonsRowPanel(row, this); // creating the row with specific keys from letters array
			rPanel.setBackground(LAVENDER);
			add(rPanel);
		}
		setBackground(LAVENDER);
	}

	/* Set internal "Shift" status to b */
	public void setShift(boolean b) {
		_shiftFlag = b;
	}
	
	/* Return internal "Shift" status */
	public boolean isShift() {
		return _shiftFlag;
	}
	
	/* Set internal "Caps" status to b */
	public void setCaps(boolean b) {
		_capsFlag = b;
	}
	
	/* Return internal "Caps" status */
	public boolean isCaps() {
		return _capsFlag;
	}
	
	/* Return current text */
	public String getText() {
		return _parent.getText();
	}
	
	/* Set current text */
	public void setText(String text) {
		_parent.setText(text);
	}
	
	
}