/*
 * Copyright 2016, 2017 IBM Corp.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.cloud.appid.android.sample.appid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ibm.cloud.appid.android.api.AppID;
import com.ibm.cloud.appid.android.api.userprofile.UserProfileException;
import com.ibm.cloud.appid.android.api.userprofile.UserProfileResponseListener;

import org.json.JSONObject;

/**
 * Show the textual presentation of the json user info of the user currently signed in
 */
public class UserInfoActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		((TextView)findViewById(R.id.userInfoData)).setText("Retrieving user info...");
	}

	@Override
	protected void onResume() {
		super.onResume();
		retrieveUserInfo();
	}

	protected void retrieveUserInfo() {
		AppID.getInstance().getUserProfileManager().getUserInfo(new UserProfileResponseListener() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				try {
					String userInfo = jsonObject.toString(2);
					setText(userInfo);
				} catch (Exception e) {
					setText("Invalid JSON Response");
				}
			}

			@Override
			public void onFailure(UserProfileException e) {
				setText("Could not retrieve user info");
			}
		});
	}

	protected void setText(final String s) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				((TextView)findViewById(R.id.userInfoData)).setText(s);
			}
		});
	}
}