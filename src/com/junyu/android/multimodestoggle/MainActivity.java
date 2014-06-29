package com.junyu.android.multimodestoggle;

import com.junyu.android.flyingmodetoggle.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.os.Build;


public class MainActivity extends Activity {
	
	private WifiManager mWifiManager;
	private ConnectivityManager mConnectivityManager;
	private AudioManager mAudioManager;
	private BluetoothAdapter mBluetoothAdapter;
	private boolean wifiConnected;
	private boolean inSilentMode;
	private boolean bluetoothEnabled;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		checkIfPhoneIsConnected();
		checkIfPhoneIsSilent();
		checkIfBluetoothEnabled();
		setButtonClickListener();
		/**
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		*/
	}
	
	private void checkIfPhoneIsConnected() {
		if (mWifiManager.isWifiEnabled() == true) {
			Log.d("wificc", "wifi connected");
			wifiConnected = true;
			toggleWifiUi();
		}else {
			Log.d("wifidd", "wifi disabled");
			wifiConnected = false;
			toggleWifiUi();
		}
	}
	
	private void checkIfPhoneIsSilent() {
		int ringerMode = mAudioManager.getRingerMode();
		if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
			inSilentMode = true;
		}else if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {
			inSilentMode = false;
		}
	}
	
	private void checkIfBluetoothEnabled() {
		if (mBluetoothAdapter.isEnabled()) {
			bluetoothEnabled = true;
		}else {
			bluetoothEnabled = false;
		}
	}
	
	private void setButtonClickListener() {
		
		ImageButton wifiButton = (ImageButton) findViewById(R.id.wifi_mode_button);
		ImageButton normalButton = (ImageButton) findViewById(R.id.back_to_normal_mode);
		ImageButton silentButton = (ImageButton) findViewById(R.id.silent_mode_button);
		ImageButton soundButton = (ImageButton) findViewById(R.id.silent_to_normal_mode);
		ImageButton bluetoothEnableButton = (ImageButton) findViewById(R.id.bluetooth_open);
		ImageButton bluetoothDisableButton = (ImageButton) findViewById(R.id.bluetooth_close);
		
		wifiButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (wifiConnected == true) {
					return;
				}else {
					boolean enabled = mWifiManager.setWifiEnabled(true);
					if (enabled == true) {
						Log.d("wifie", "wifi has been enabled");
						wifiConnected = true;
					}
				}
				toggleWifiUi();
			}
		});
		
		normalButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (wifiConnected == true) {
					boolean disabled = mWifiManager.setWifiEnabled(false);
					if (disabled == true) {
						Log.d("wifid", "wifi has been disabled");
						wifiConnected = false;
					}
				}else {
					return;
				}
				toggleWifiUi();
			}
		});
		
		silentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (inSilentMode == true) {
					return;
				}else {
					mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					inSilentMode = true;
				}
				toggleSilentUi();
			}
		});
		
		soundButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (inSilentMode == true) {
					mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					inSilentMode = false;
				}else {
					return;
				}
				toggleSilentUi();
			}
		});
		
		bluetoothEnableButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bluetoothEnabled == true) {
					return;
				}else {
					boolean done = mBluetoothAdapter.enable();
					if (done == true) {
						bluetoothEnabled = true;
					}
				}
				toggleBluetoothUi();
			}
		});
		
		bluetoothDisableButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (bluetoothEnabled == true) {
					boolean done = mBluetoothAdapter.disable();
					if (done == true) {
						bluetoothEnabled = false;
					}
				}else {
					return;
				}
				toggleBluetoothUi();
			}
		});
	}
	
	private void toggleWifiUi() {
		ImageButton wifiModeButton = (ImageButton) findViewById(R.id.wifi_mode_button);
		ImageButton normalModeButton = (ImageButton) findViewById(R.id.back_to_normal_mode);
		if (wifiConnected == true) {
			wifiModeButton.setBackgroundColor(Color.parseColor("#008080"));
			normalModeButton.setBackgroundColor(Color.parseColor("#000000"));
		}else {
			normalModeButton.setBackgroundColor(Color.parseColor("#008080"));
			wifiModeButton.setBackgroundColor(Color.parseColor("#000000"));
		}
	}
	
	private void toggleSilentUi() {
		ImageButton silentModeButton = (ImageButton) findViewById(R.id.silent_mode_button);
		ImageButton soundModeButton = (ImageButton) findViewById(R.id.silent_to_normal_mode);
		if (inSilentMode == true) {
			silentModeButton.setBackgroundColor(Color.parseColor("#008080"));
			soundModeButton.setBackgroundColor(Color.parseColor("#000000"));
		}else {
			soundModeButton.setBackgroundColor(Color.parseColor("#008080"));
			silentModeButton.setBackgroundColor(Color.parseColor("#000000"));
		}
	}
	
	private void toggleBluetoothUi() {
		ImageButton bluetoothEnableButton = (ImageButton) findViewById(R.id.bluetooth_open);
		ImageButton bluetoothDisableButton = (ImageButton) findViewById(R.id.bluetooth_close);
		if (bluetoothEnabled == true) {
			bluetoothEnableButton.setBackgroundColor(Color.parseColor("#008080"));
			bluetoothDisableButton.setBackgroundColor(Color.parseColor("#000000"));
		}else {
			bluetoothDisableButton.setBackgroundColor(Color.parseColor("#008080"));
			bluetoothEnableButton.setBackgroundColor(Color.parseColor("#000000"));
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		checkIfPhoneIsConnected();
		checkIfPhoneIsSilent();
		checkIfBluetoothEnabled();
		toggleWifiUi();
		toggleSilentUi();
		toggleBluetoothUi();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
