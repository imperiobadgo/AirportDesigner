package com.pukekogames.airportdesigner;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = true;
		config.numSamples = 2;
		Main main = new Main();
		Main.IS_STARTED_ON_MOBILE = true;
		initialize(main, config);

	}
}
