/*
 * Created by wingjay on 11/16/16 3:32 PM
 * Copyright (c) 2016.  All rights reserved.
 *
 * Last modified 11/10/16 11:05 AM
 *
 * Reach me: https://github.com/wingjay
 * Email: yinjiesh@126.com
 */

package com.example.xdcao.diandiapp.UI.songwenqiang.prefs;

import android.content.Context;
import android.support.annotation.NonNull;


public class UserPrefs extends BasePrefs {

  public final static String PREFS_NAME = "userPrefs";

  public UserPrefs( Context context) {
    super(context, PREFS_NAME);
  }


  public final static String KEY_USER_AUTH_TOKEN = "user_auth_token";

  public String getAuthToken() {
    return getString(KEY_USER_AUTH_TOKEN, null);
  }

  public void setAuthToken(@NonNull String authToken) {
    setString(KEY_USER_AUTH_TOKEN, authToken);
  }

  public void clearAuthToken() {
    setString(KEY_USER_AUTH_TOKEN, null);
  }







}
