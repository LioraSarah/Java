import java.awt.*;

import javax.swing.*;

/* Panel with text area and vertical scroll bar */
public class TextPanel extends JPanel {

	private static final Font TEXT_FONT = new Font(Font.SANS_SERIF, 0, 20); // The font of the text
	private final JTextArea _textArea;
	private JScrollPane _scroll;
	
	public TextPanel() {
		_textArea = new JTextArea();
		_textArea.setEditable(false);
		_textArea.setFont(TEXT_FONT);
		_textArea.setLineWrap(true);
		
		_scroll = new JScrollPane(_textArea);
		_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		// We don't need an horizontal scroll cuz we wrapped the lines
		_scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		setLayout(new GridLayout());
		add(_scroll);
	}
	
	public void setText(String text) {
		_textArea.setText(text);
	}

	public String getText() {
		return _textArea.getText();
	}

}
