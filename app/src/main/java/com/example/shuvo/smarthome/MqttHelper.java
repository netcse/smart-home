package com.example.shuvo.smarthome;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MqttHelper {
    private static MqttHelper mqttHelper = null;
    private MqttAndroidClient client = null;

    public static MqttHelper getInstance(Context context) {
        if (mqttHelper == null)
            mqttHelper = new MqttHelper(context);

        return mqttHelper;
    }

    private MqttHelper(Context context) {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(context, "tcp://45.125.222.120:1883",
                clientId);
    }

    public MqttAndroidClient getClient() {
        return client;
    }

    public void setClient(MqttAndroidClient client) {
        this.client = client;
    }

    protected MqttHelper connect() {

        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("resp", "onSuccess");

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("resp", "onFailure");

                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }
        return this;
    }

    protected void publish(String topic, String payload) {
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }
}
