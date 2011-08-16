package com.maize.bluetoothNotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {
	
	public static final String TAG = "BluetoothBroadcastReceiver"; 
	
	@Override
	public void onReceive(Context context, Intent intent) {
		BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		String action = intent.getAction();
		Log.d(TAG, action);
		
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification();
		notification.icon = R.drawable.icon;
		notification.when = System.currentTimeMillis();
    	notification.defaults = Notification.DEFAULT_VIBRATE;
		Intent notificationIntent = new Intent();
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);		
		
        if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
        	int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
        	Log.d(TAG, "Bond state changed to " + state);
        	
        	if (state == BluetoothDevice.BOND_BONDED) {
    			notification.tickerText = "Connected to " + device.getName();
        	}
        	else if (state == BluetoothDevice.BOND_BONDING) {
    			notification.tickerText = "Connecting to " + device.getName() + "...";	
        	} 
        	else if (state == BluetoothDevice.BOND_NONE) {
    			notification.tickerText = "Disconnected from " + device.getName();
        	}         	

			notification.setLatestEventInfo(context, notification.tickerText, "Address: " + device.getAddress(), contentIntent);
			manager.notify(1, notification);
        }		
	}
}
