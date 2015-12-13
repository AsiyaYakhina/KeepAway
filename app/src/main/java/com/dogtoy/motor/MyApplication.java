package com.dogtoy.motor;

import android.app.Application;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

public class MyApplication extends Application {

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                motorControl.msg(getApplicationContext(), "Entered the range");
                motorControl.turnOnLed(getApplicationContext());

            }

            @Override
            public void onExitedRegion(Region region) {
                motorControl.msg(getApplicationContext(), "Left the range");
                // could add an "exit" notification too if you want (-:
            }
        });

        // The beacon UUID needs to be updated to the one we intend to use.
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 56423, 42651));
            }
        });
    }
}
