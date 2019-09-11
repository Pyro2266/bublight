package com.github.pyro2266.lightit.drivers;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BMP-180 I2C
 * 3.3V Bosch temperature and barometric pressure sensor
 */
public class BMP180 {

    private static final Logger LOG = LoggerFactory.getLogger(BMP180.class);

    private final static int BIG_ENDIAN = 1;
    private final static int BMP180_ENDIANNESS = BIG_ENDIAN;

    /*
    Prompt> sudo i2cdetect -y 1
         0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f
    00:          -- -- -- -- -- -- -- -- -- -- -- -- --
    10: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
    20: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
    30: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
    40: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
    50: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
    60: -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
    70: -- -- -- -- -- -- -- 77
     */

    // This next addresses is returned by "sudo i2cdetect -y 1", see above.
    private final static int BMP180_ADDRESS = 0x77;

    // Operating Modes
    private final int BMP180_ULTRALOWPOWER = 0;
    private final int BMP180_STANDARD = 1;
    private final int BMP180_HIGHRES = 2;
    private final int BMP180_ULTRAHIGHRES = 3;

    // BMP180 Registers
    private final int BMP180_CAL_AC1 = 0xAA;  // R   Calibration data (16 bits)
    private final int BMP180_CAL_AC2 = 0xAC;  // R   Calibration data (16 bits)
    private final int BMP180_CAL_AC3 = 0xAE;  // R   Calibration data (16 bits)
    private final int BMP180_CAL_AC4 = 0xB0;  // R   Calibration data (16 bits)
    private final int BMP180_CAL_AC5 = 0xB2;  // R   Calibration data (16 bits)
    private final int BMP180_CAL_AC6 = 0xB4;  // R   Calibration data (16 bits)
    private final int BMP180_CAL_B1 = 0xB6;  // R   Calibration data (16 bits)
    private final int BMP180_CAL_B2 = 0xB8;  // R   Calibration data (16 bits)
    private final int BMP180_CAL_MB = 0xBA;  // R   Calibration data (16 bits)
    private final int BMP180_CAL_MC = 0xBC;  // R   Calibration data (16 bits)
    private final int BMP180_CAL_MD = 0xBE;  // R   Calibration data (16 bits)

    private final int BMP180_CONTROL = 0xF4;
    private final int BMP180_TEMPDATA = 0xF6;
    private final int BMP180_PRESSUREDATA = 0xF6;
    private final int BMP180_READTEMPCMD = 0x2E;
    private final int BMP180_READPRESSURECMD = 0x34;

    private int cal_AC1 = 0;
    private int cal_AC2 = 0;
    private int cal_AC3 = 0;
    private int cal_AC4 = 0;
    private int cal_AC5 = 0;
    private int cal_AC6 = 0;
    private int cal_B1 = 0;
    private int cal_B2 = 0;
    private int cal_MB = 0;
    private int cal_MC = 0;
    private int cal_MD = 0;

    private I2CDevice bmp180;
    private int mode = BMP180_STANDARD;
    private int standardSeaLevelPressure = 101325;

    public BMP180() {
        this(BMP180_ADDRESS);
    }

    public BMP180(int address) {
        try {
            // Get i2c bus
            I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1); // Depends on the RasPI version
            LOG.debug("Connected to bus. OK.");

            // Get device itself
            bmp180 = bus.getDevice(address);
            LOG.debug("Connected to device. OK.");

            this.readCalibrationData();
        } catch (IOException | I2CFactory.UnsupportedBusNumberException e) {
            LOG.error("Exception thrown!", e);
        }
    }

    private int readU8(int reg) {
        // "Read an unsigned byte from the I2C device"
        int result = 0;
        try {
            result = this.bmp180.read(reg);
            LOG.trace("I2C: Device " + BMP180_ADDRESS + " returned " + result + " from reg " + reg);
        } catch (IOException e) {
            LOG.error("Exception thrown!", e);
        }
        return result;
    }

    private int readS8(int reg) {
        // "Reads a signed byte from the I2C device"
        int result = 0;
        try {
            result = this.bmp180.read(reg);
            if (result > 127)
                result -= 256;
            LOG.trace("I2C: Device " + BMP180_ADDRESS + " returned " + result + " from reg " + reg);
        } catch (IOException e) {
            LOG.error("Exception thrown!", e);
        }
        return result;
    }

    private int readU16(int register) {
        int hi = this.readU8(register);
        int lo = this.readU8(register + 1);
        return (BMP180_ENDIANNESS == BIG_ENDIAN) ? (hi << 8) + lo : (lo << 8) + hi; // Big Endian
    }

    private int readS16(int register) {
        int hi = 0, lo = 0;
        if (BMP180_ENDIANNESS == BIG_ENDIAN) {
            hi = this.readS8(register);
            lo = this.readU8(register + 1);
        } else {
            lo = this.readS8(register);
            hi = this.readU8(register + 1);
        }
        return (hi << 8) + lo;
    }

    public void readCalibrationData() {
        // Reads the calibration data from the IC
        cal_AC1 = readS16(BMP180_CAL_AC1);   // INT16
        cal_AC2 = readS16(BMP180_CAL_AC2);   // INT16
        cal_AC3 = readS16(BMP180_CAL_AC3);   // INT16
        cal_AC4 = readU16(BMP180_CAL_AC4);   // UINT16
        cal_AC5 = readU16(BMP180_CAL_AC5);   // UINT16
        cal_AC6 = readU16(BMP180_CAL_AC6);   // UINT16
        cal_B1 = readS16(BMP180_CAL_B1);    // INT16
        cal_B2 = readS16(BMP180_CAL_B2);    // INT16
        cal_MB = readS16(BMP180_CAL_MB);    // INT16
        cal_MC = readS16(BMP180_CAL_MC);    // INT16
        cal_MD = readS16(BMP180_CAL_MD);    // INT16

        showCalibrationData();
    }

    private void showCalibrationData() {
        // Displays the calibration values for debugging purposes
        LOG.debug("Calibration data:");
        LOG.debug("AC1 = {}", cal_AC1);
        LOG.debug("AC2 = {}", cal_AC2);
        LOG.debug("AC3 = {}", cal_AC3);
        LOG.debug("AC4 = {}", cal_AC4);
        LOG.debug("AC5 = {}", cal_AC5);
        LOG.debug("AC6 = {}", cal_AC6);
        LOG.debug("B1  = {}", cal_B1);
        LOG.debug("B2  = {}", cal_B2);
        LOG.debug("MB  = {}", cal_MB);
        LOG.debug("MC  = {}", cal_MC);
        LOG.debug("MD  = {}", cal_MD);
    }

    private int readRawTemp() throws IOException, InterruptedException {
        // Reads the raw (uncompensated) temperature from the sensor
        bmp180.write(BMP180_CONTROL, (byte) BMP180_READTEMPCMD);
        Thread.sleep(5);  // Wait 5ms
        int raw = readU16(BMP180_TEMPDATA);
        LOG.trace("Raw Temp: " + (raw & 0xFFFF) + ", " + raw);
        return raw;
    }

    private int readRawPressure() throws IOException, InterruptedException {
        // Reads the raw (uncompensated) pressure level from the sensor
        bmp180.write(BMP180_CONTROL, (byte) (BMP180_READPRESSURECMD + (this.mode << 6)));
        if (this.mode == BMP180_ULTRALOWPOWER) {
            Thread.sleep(5);
        } else if (this.mode == BMP180_HIGHRES) {
            Thread.sleep(14);
        } else if (this.mode == BMP180_ULTRAHIGHRES) {
            Thread.sleep(26);
        } else {
            Thread.sleep(8);
        }
        int msb = bmp180.read(BMP180_PRESSUREDATA);
        int lsb = bmp180.read(BMP180_PRESSUREDATA + 1);
        int xlsb = bmp180.read(BMP180_PRESSUREDATA + 2);
        int raw = ((msb << 16) + (lsb << 8) + xlsb) >> (8 - this.mode);
        LOG.trace("Raw Pressure: " + (raw & 0xFFFF) + ", " + raw);
        return raw;
    }

    public float readTemperature() throws IOException, InterruptedException {
        // Gets the compensated temperature in degrees celsius
        int UT = 0;
        int X1 = 0;
        int X2 = 0;
        int B5 = 0;
        float temp = 0.0f;

        // Read raw temp before aligning it with the calibration values
        UT = this.readRawTemp();
        X1 = ((UT - this.cal_AC6) * this.cal_AC5) >> 15;
        X2 = (this.cal_MC << 11) / (X1 + this.cal_MD);
        B5 = X1 + X2;
        temp = ((B5 + 8) >> 4) / 10.0f;
        LOG.debug("Calibrated temperature = " + temp + " C");
        return temp;
    }

    public float readPressure() throws IOException, InterruptedException {
        // Gets the compensated pressure in pascal
        int UT = 0;
        int UP = 0;
        int B3 = 0;
        int B5 = 0;
        int B6 = 0;
        int X1 = 0;
        int X2 = 0;
        int X3 = 0;
        int p = 0;
        int B4 = 0;
        int B7 = 0;

        UT = this.readRawTemp();
        UP = this.readRawPressure();
        
        // True Temperature Calculations
        X1 = (int) ((UT - this.cal_AC6) * this.cal_AC5) >> 15;
        X2 = (this.cal_MC << 11) / (X1 + this.cal_MD);
        B5 = X1 + X2;
        LOG.trace("X1 = " + X1);
        LOG.trace("X2 = " + X2);
        LOG.trace("B5 = " + B5);
        LOG.trace("True Temperature = " + (((B5 + 8) >> 4) / 10.0) + " C");
        
        // Pressure Calculations
        B6 = B5 - 4000;
        X1 = (this.cal_B2 * (B6 * B6) >> 12) >> 11;
        X2 = (this.cal_AC2 * B6) >> 11;
        X3 = X1 + X2;
        B3 = (((this.cal_AC1 * 4 + X3) << this.mode) + 2) / 4;
        LOG.trace("B6 = " + B6);
        LOG.trace("X1 = " + X1);
        LOG.trace("X2 = " + X2);
        LOG.trace("X3 = " + X3);
        LOG.trace("B3 = " + B3);
        
        X1 = (this.cal_AC3 * B6) >> 13;
        X2 = (this.cal_B1 * ((B6 * B6) >> 12)) >> 16;
        X3 = ((X1 + X2) + 2) >> 2;
        B4 = (this.cal_AC4 * (X3 + 32768)) >> 15;
        B7 = (UP - B3) * (50000 >> this.mode);
        LOG.trace("X1 = " + X1);
        LOG.trace("X2 = " + X2);
        LOG.trace("X3 = " + X3);
        LOG.trace("B4 = " + B4);
        LOG.trace("B7 = " + B7);
            
        if (B7 < 0x80000000)
            p = (B7 * 2) / B4;
        else
            p = (B7 / B4) * 2;

        LOG.trace("X1 = " + X1);

        X1 = (p >> 8) * (p >> 8);
        X1 = (X1 * 3038) >> 16;
        X2 = (-7357 * p) >> 16;
        LOG.trace("p  = " + p);
        LOG.trace("X1 = " + X1);
        LOG.trace("X2 = " + X2);
        
        p = p + ((X1 + X2 + 3791) >> 4);
        LOG.debug("Pressure = " + p + " Pa");

        return p;
    }
    
    public void setStandardSeaLevelPressure(int standardSeaLevelPressure) {
        this.standardSeaLevelPressure = standardSeaLevelPressure;
    }

    public double readAltitude() throws IOException, InterruptedException {
        // "Calculates the altitude in meters"
        double altitude = 0.0;
        float pressure = readPressure();
        altitude = 44330.0 * (1.0 - Math.pow(pressure / standardSeaLevelPressure, 0.1903));
        LOG.debug("Altitude = " + altitude);
        return altitude;
    }

}
