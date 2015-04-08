import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.JButton;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	String[] strArray = new String[484];
	Random rand = new Random();
	String answer;
	char[] charArray;
	int lives;
	boolean lastLife = false;


	private JTextField textField_1;
	private JTextField textField_2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Hangman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("New Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { //starts a new game
				textField_1.setText("");
				lives = 10;
				String strLives = Integer.toString(lives);
				textField_2.setText(strLives);

				answer = strArray[rand.nextInt(483)];
				charArray = answer.toCharArray();

				for(int i = 0; i < charArray.length; ++i){
					charArray[i] = '*';
				}

				String a = new String(charArray);
				textField.setText(a);
			}
		});
		btnNewButton.setBounds(319, 34, 105, 37);
		contentPane.add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(111, 34, 187, 37);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				textField_1.setText(""); //clears text on focus
			}
		});
		textField_1.setBounds(111, 138, 187, 37);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		

		JButton btnNewButton_1 = new JButton("Check");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((lives > 0)^(lastLife)){
					try{
						lastLife = false;
						char check = textField_1.getText().charAt(0);
						boolean noLivesLost = false;
						for (int i = 0; i < charArray.length; ++i){
							if(check == answer.charAt(i)){
								charArray[i] = check;
								noLivesLost = true;
							}
						}
						
						if(!noLivesLost) lives--;
						if(lives == 0) lastLife = true;
						String a = new String(charArray);
						textField.setText(a);

						if(textField.getText().equalsIgnoreCase(answer) ){
							textField_1.setText("YOU WIN");
						} else {
							textField_1.setText("");
						}
						String strLives = Integer.toString(lives);
						textField_2.setText(strLives);
					} catch (Exception ex){ //bad practice
						textField_1.setText("Please enter a character");
					}
				} else {
					textField.setText(answer);
					textField_1.setText("GAME OVER");
				} 
			}
		});

		btnNewButton_1.setBounds(319, 138, 105, 37);
		contentPane.add(btnNewButton_1);

		textField_2 = new JTextField();
		textField_2.setBounds(10, 207, 86, 44);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		String strLives = Integer.toString(lives);
		textField_2.setText(strLives);

		/*PopulateArray makeArray = new PopulateArray();
		strArray = makeArray.popArray(strArray);*/

		this.popArray(strArray);
	}

	public String[] popArray(String[] strArray){
		try {
			FileInputStream wordFile = new FileInputStream("words.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(wordFile));


			for (int i = 0; i < strArray.length; i++) {
				strArray[i] = br.readLine();
				//System.out.println(strArray[i]);
			}
			br.close();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strArray;

	}
}

