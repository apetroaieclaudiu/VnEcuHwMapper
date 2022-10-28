package gui_CanCase;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fazecast.jSerialComm.SerialPort;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainClass {
	static SerialPort chosenPort;
	private static int numberOfChannels = 4;
	private static boolean duplicateElement = false;
	private static boolean goodToSend = true;
	public static List<String> list_channels = Arrays.asList("N/A", "CAN1", "CAN2", "CAN3", "CAN4", "CAN5", "CAN6",
			"CAN7", "CAN8");
	public static List<String> checked_channels = new ArrayList<>();
	public static String dataToSend = new String("");

	public static void main(String[] args) {
		SerialPort[] portNames = SerialPort.getCommPorts();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
					for (int i = 0; i < portNames.length; i++)
						window.portList.addItem(portNames[i].getSystemPortName());

					for (int i = 0; i < numberOfChannels; i++)
						checked_channels.add(i, "N/A");

					for (int i = 0; i < list_channels.size(); i++) {
						window.cBox_Ch1.addItem(list_channels.get(i));
						window.cBox_Ch2.addItem(list_channels.get(i));
						window.cBox_Ch3.addItem(list_channels.get(i));
						window.cBox_Ch4.addItem(list_channels.get(i));
					}

					window.cBox_Ch1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							checked_channels.remove(0);
							checked_channels.add(0, window.cBox_Ch1.getSelectedItem().toString());

						}
					});
					window.cBox_Ch2.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							checked_channels.remove(1);
							checked_channels.add(1, window.cBox_Ch2.getSelectedItem().toString());

						}
					});
					window.cBox_Ch3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							checked_channels.remove(2);
							checked_channels.add(2, window.cBox_Ch3.getSelectedItem().toString());

						}
					});
					window.cBox_Ch4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							checked_channels.remove(3);
							checked_channels.add(3, window.cBox_Ch4.getSelectedItem().toString());

						}
					});

					window.button_SetValues.addActionListener(new ActionListener() {
						@SuppressWarnings("deprecation")
						public void actionPerformed(ActionEvent e) {
							goodToSend = true;
							Set<String> setToCheck = new HashSet<String>();

							for (String stringOfData : checked_channels) {
								if (!setToCheck.add(stringOfData)) {
									duplicateElement = true;
									window.infoBox("Duplicate CAN channel was found", "Select another CAN channel");
									goodToSend = false;
								}

								dataToSend = dataToSend.concat("#" + stringOfData);
							}

							if (goodToSend == true) {
								startThread(dataToSend);
								dataToSend = "";
							} else
								dataToSend = "";
						}
					});
					window.button_Lauterbach.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							startThread("R");
						}
					});

					window.connectButton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if (window.connectButton.getText().equals("Connect")) {
								chosenPort = SerialPort.getCommPort(window.portList.getSelectedItem().toString());
								chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
								if (chosenPort.openPort()) {
									window.connectButton.setText("Disconnect");
									window.portList.setEnabled(false);

								}
							} else {
								chosenPort.closePort();
								window.portList.setEnabled(true);
								window.connectButton.setText("Connect");
							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void startThread(String s) {
		Thread thread = new Thread() {
			@Override
			public void run() {

				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}

				PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
				while (true) {
					output.print(s + " \n");
					output.flush();
					try {
						Thread.sleep(100);

					} catch (Exception e) {
					}
				break;
				}

			}
		};
		thread.start();
		System.out.println(s);

	}

}
