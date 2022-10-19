import time
import sys
import serial
from PyQt5.QtWidgets import QApplication,QWidget,QLabel,QComboBox, QPushButton
from PyQt5.QtGui import QIcon,QPixmap
from PyQt5.QtCore import pyqtSlot

canch_list = ["N/A", "CAN1", "CAN2", "CAN3", "CAN4", "CAN5", "CAN6", "CAN7", "CAN8"]
vnch_list = ["Ch1", "Ch2", "Ch3", "Ch4"]
vncan_mapping = ["N/A", "N/A", "N/A", "N/A"]

class Raspberry():
    timeout = 1000;
    def __init__(self):
        try:
            self.serial = serial.Serial('/dev/ttyACM0', 9600, timeout=self.timeout)
        except serial.SerialException as e:
            print("The communication between host and target could not be established")
            sys.exit(1)
        self.readChannelStatus()

    def send(self, text: str):
        line = '%s\r\f' % text
        print(line)
        self.serial.write(line.encode('utf-8'))

    def receive(self) -> str:
        line = self.serial.readline()
        return line.decode('UTF8').strip()

    def close(self):
        self.serial.close()

    def readChannelStatus(self):
        global vncan_mapping
        self.send("read_all")
        self.receive()
        for i in range(0, len(vnch_list)):
            vncan_mapping[i] = self.receive()

    def transmitData(self, channel: str, can: str):
        self.send("Chn")
        self.send(channel)
        self.send(can)

class App(QWidget):
    def __init__(self):
        super().__init__()
        self.title='VN 2 ECU Mapper'
        self.left=10
        self.top=10
        self.width=480
        self.height=320
        self.firmware = Raspberry()
        self.initUI()

    def getChn1Data(self) -> str:
        return self.chn1.currentText()

    def getChn2Data(self) -> str:
        return self.chn2.currentText()

    def getChn3Data(self) -> str:
        return self.chn3.currentText()

    def getChn4Data(self) -> str:
        return self.chn4.currentText()
    
    def triggerTransmission(self):
        chn1_data = self.getChn1Data()
        chn2_data = self.getChn2Data()
        chn3_data = self.getChn3Data()
        chn4_data = self.getChn4Data()
        self.firmware.transmitData("0", chn1_data)
        self.firmware.transmitData("1", chn2_data)
        self.firmware.transmitData("2", chn3_data)
        self.firmware.transmitData("3", chn4_data)

    def initUI(self):
        self.chn1 = QComboBox(self)
        self.chn2 = QComboBox(self)
        self.chn3 = QComboBox(self)
        self.chn4 = QComboBox(self)
        self.btn = QPushButton('Configure', self)
        self.btn.clicked.connect(self.triggerTransmission)
        self.chn1.setGeometry(50, 50, 120, 30)
        self.chn2.setGeometry(50, 100, 120, 30)
        self.chn3.setGeometry(50, 150, 120, 30)
        self.chn4.setGeometry(50, 200, 120, 30)
        self.btn.setGeometry(50, 250, 120, 30)
        self.chn1.addItems(canch_list)
        self.chn2.addItems(canch_list)
        self.chn3.addItems(canch_list)
        self.chn4.addItems(canch_list)
        self.chn1.setEditable(True)
        self.chn2.setEditable(True)
        self.chn3.setEditable(True)
        self.chn4.setEditable(True)

        self.chn1_label = QLabel("Channel 1", self)
        self.chn2_label = QLabel("Channel 2", self)
        self.chn3_label = QLabel("Channel 3", self)
        self.chn4_label = QLabel("Channel 4", self)
        self.chn1_label.setGeometry(200, 50, 120, 30)
        self.chn2_label.setGeometry(200, 100, 120, 30)
        self.chn3_label.setGeometry(200, 150, 120, 30)
        self.chn4_label.setGeometry(200, 200, 120, 30)
        self.setWindowTitle(self.title)
        self.setGeometry(self.left,self.top,self.width,self.height)
        print(vncan_mapping[0])
        print(vncan_mapping[1])
        print(vncan_mapping[2])
        print(vncan_mapping[3])
        self.chn1.setCurrentIndex(canch_list.index(vncan_mapping[0]))
        self.chn2.setCurrentIndex(canch_list.index(vncan_mapping[1]))
        self.chn3.setCurrentIndex(canch_list.index(vncan_mapping[2]))
        self.chn4.setCurrentIndex(canch_list.index(vncan_mapping[3]))
        self.show()

if __name__=='__main__':
    app=QApplication(sys.argv)
    ex=App()
    sys.exit(app.exec())
