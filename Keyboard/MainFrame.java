import java.awt.BorderLayout;

import javax.swing.JFrame;

/* The frame class that contains the keyboard buttons and the text area */
public class MainFrame extends JFrame {

	private static final int FRAME_WIDTH = 900;
	private static final int FRAME_HEIGHT = 500;

	private TextPanel _textPanel; // the textArea panel
	private KeyboardPanel _buttonsPanel; // the keys panel
	
	public MainFrame() {
		super("Keyboard Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setResizable(false);
		
		setLayout(new BorderLayout());
		_textPanel = new TextPanel();
		_buttonsPanel = new KeyboardPanel(this);
		add(_textPanel, BorderLayout.CENTER);
		add(_buttonsPanel, BorderLayout.SOUTH);
	}

	// A function for getting the text from the textPanel area
	public String getText() {
		return _textPanel.getText();
	}

	// A function for editing the text in the textPanel area
	public void setText(String text) {
		_textPanel.setText(text);
	}

}
