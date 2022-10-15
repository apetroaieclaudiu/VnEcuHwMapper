package gui_CanCase;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainClass {
	private static int numberOfChannels = 4;
	private static boolean duplicateElement = false;
	public static List<String> list_channels = Arrays.asList("N/A", "CAN1", "CAN2", "CAN3", "CAN4", "CAN5", "CAN6", "CAN7", "CAN8");
	public static List<String> checked_channels = new ArrayList<>();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
					
					for(int i=0;i<numberOfChannels;i++)
					checked_channels.add(i,"N/A");
					
					for(int i=0;i<list_channels.size();i++)
					{
						window.cBox_Ch1.addItem(list_channels.get(i));
						window.cBox_Ch2.addItem(list_channels.get(i));
						window.cBox_Ch3.addItem(list_channels.get(i));
						window.cBox_Ch4.addItem(list_channels.get(i));
					}
					
					window.cBox_Ch1.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{checked_channels.remove(0);
							checked_channels.add(0,window.cBox_Ch1.getSelectedItem().toString());
							
							
						}
					});	
					window.cBox_Ch2.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{checked_channels.remove(1);
							checked_channels.add(1,window.cBox_Ch2.getSelectedItem().toString());
						
							
						}
					});
					window.cBox_Ch3.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{
							checked_channels.remove(2);
							checked_channels.add(2,window.cBox_Ch3.getSelectedItem().toString());
						
							
						}
					});
					window.cBox_Ch4.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{  	
							checked_channels.remove(3);
							checked_channels.add(3,window.cBox_Ch4.getSelectedItem().toString());
							
							
						}
					});
					
					window.button_SetValues.addActionListener(new ActionListener() 
					{
						public void actionPerformed(ActionEvent e) 
						{  
							Set<String> setToCheck = new HashSet<String>();
							
							
							 for (String stringOfData : checked_channels)
							 {
						            if (!setToCheck.add(stringOfData)) 
						            { 	 window.button_SetValues.setBackground(Color.red);
						            	 duplicateElement = true;
						            	 window.infoBox("Duplicate CAN channel was found", "Select another CAN channel");
						            	
						            }
						        
							 }
							
							
						}
					});
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
