package gui_CanCase;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import java.awt.Color;

public class GUI {

	public JFrame frame;
	public JTextField txt_Ch1;
	public JTextField txt_Ch2;
	public JTextField txt_Ch3;
	public JTextField txt_Ch4;
	public JComboBox cBox_Ch1;
	public JComboBox cBox_Ch2;
	public JComboBox cBox_Ch3;
	public JComboBox cBox_Ch4;
	public JToggleButton button_SetValues;


	public GUI() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setAutoRequestFocus(false);
		frame.setBounds(100, 100, 555, 259);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txt_Ch1 = new JTextField();
		txt_Ch1.setText("Channel 1");
		txt_Ch1.setBounds(194, 15, 77, 36);
		frame.getContentPane().add(txt_Ch1);
		txt_Ch1.setColumns(10);
		
		txt_Ch2 = new JTextField();
		txt_Ch2.setText("Channel 2");
		txt_Ch2.setColumns(10);
		txt_Ch2.setBounds(281, 15, 74, 36);
		frame.getContentPane().add(txt_Ch2);
		
		txt_Ch3 = new JTextField();
		txt_Ch3.setText("Channel 3");
		txt_Ch3.setColumns(10);
		txt_Ch3.setBounds(365, 15, 77, 36);
		frame.getContentPane().add(txt_Ch3);
		
		txt_Ch4 = new JTextField();
		txt_Ch4.setText("Channel 4");
		txt_Ch4.setColumns(10);
		txt_Ch4.setBounds(452, 15, 76, 36);
		frame.getContentPane().add(txt_Ch4);
		
		cBox_Ch1 = new JComboBox();
		cBox_Ch1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cBox_Ch1.setBounds(194, 62, 75, 25);
		frame.getContentPane().add(cBox_Ch1);
		
		cBox_Ch2 = new JComboBox();
		cBox_Ch2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cBox_Ch2.setBounds(281, 62, 74, 25);
		frame.getContentPane().add(cBox_Ch2);
		
		cBox_Ch3 = new JComboBox();
		cBox_Ch3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cBox_Ch3.setBounds(365, 63, 77, 25);
		frame.getContentPane().add(cBox_Ch3);
		
		cBox_Ch4 = new JComboBox();
		cBox_Ch4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cBox_Ch4.setBounds(452, 63, 76, 25);
		frame.getContentPane().add(cBox_Ch4);
		
		JToggleButton button_Lauterbach = new JToggleButton("Lauterbach");
		button_Lauterbach.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_Lauterbach.setBackground(new Color(60, 179, 113));
		button_Lauterbach.setBounds(10, 133, 154, 65);
		frame.getContentPane().add(button_Lauterbach);
		
		button_SetValues = new JToggleButton("Set Can Channels");
		button_SetValues.setFocusable(false);
		button_SetValues.setSelected(true);
		button_SetValues.setFont(new Font("Tahoma", Font.PLAIN, 15));
		button_SetValues.setBackground(new Color(60, 179, 113));
		button_SetValues.setBounds(10, 22, 154, 65);
		frame.getContentPane().add(button_SetValues);
	}
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
