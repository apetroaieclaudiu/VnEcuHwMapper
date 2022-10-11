import sys
import serial
from PyQt5.QtWidgets import QApplication,QWidget,QLabel,QComboBox
from PyQt5.QtGui import QIcon,QPixmap

canch_list = ["CAN1", "CAN2", "CAN3", "CAN4", "CAN5", "CAN6", "CAN7", "CAN8"]
vnch_list = ["Ch1", "Ch2", "Ch3", "Ch4"]
vncan_mapping = []

class Raspberry():
    def __init__(self):
        self.serial = serial.Serial('/dev/ttyACM0', 115200, timeout=timeout)
        self.readChannelStatus()

    def send(self, text: str):
        line = '%s\r\f' % text
        self.serial.write(line.encode('utf-8'))

    def receive(self) -> str:
        line = self.serial.read_until(self.TERMINATOR)
        return line.decode('UTF8').strip()

    def close(self):
        self.serial.close()

    def readChannelStatus(self):
        for i in range(1, len(vnch_list)):
            global vncan_mapping[i] = self.receive()

class App(QWidget):
    def __init__(self):
        super().__init__()
        self.title='VN 2 ECU Mapper'
        self.left=10
        self.top=10
        self.width=480
        self.height=320
        self.initUI()

    def initUI(self):
        self.chn1 = QComboBox(self)
        self.chn2 = QComboBox(self)
        self.chn3 = QComboBox(self)
        self.chn4 = QComboBox(self)
        self.chn1.setGeometry(50, 50, 120, 30)
        self.chn2.setGeometry(50, 100, 120, 30)
        self.chn3.setGeometry(50, 150, 120, 30)
        self.chn4.setGeometry(50, 200, 120, 30)
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
        self.chn1.setCurrentIndex(canch_list.index(vncan_mapping[0]))
        self.chn2.setCurrentIndex(canch_list.index(vncan_mapping[1]))
        self.chn3.setCurrentIndex(canch_list.index(vncan_mapping[2]))
        self.chn4.setCurrentIndex(canch_list.index(vncan_mapping[3]))
        self.show()

if __name__=='__main__':
    app=QApplication(sys.argv)
    ex=App()
    firmware = Raspberry()
    sys.exit(app.exec())
