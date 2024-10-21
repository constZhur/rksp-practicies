package ru.mirea.pr3.task1;

public class Main {
    public static void main(String[] args) {
        TemperatureSensor temperatureSensor = new TemperatureSensor();
        CO2Sensor co2Sensor = new CO2Sensor();

        Alarm alarm = new Alarm();

        temperatureSensor.subscribe(alarm);
        co2Sensor.subscribe(alarm);

        temperatureSensor.start();
        co2Sensor.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
