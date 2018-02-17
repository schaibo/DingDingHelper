/*
 * Copyright (C)  Justson(https://github.com/Justson/DingDingHelper)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ucmap.dingdinghelper.pixelsdk;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


public class PixelActivityUnion {


    private static final PixelActivityUnion mPixelActivityUnion = new PixelActivityUnion();
    private Class<? extends Activity> activity = null;
    private Bundle mBundle = null;
    private IActivityManager mActivityManager;

    private ScreenStateBroadcast mScreenStateBroadcast = null;
    private Context mContext;

    private static volatile int index = 0;

    private PixelActivityUnion() {
        if (mPixelActivityUnion != null) {
            throw new UnsupportedOperationException("single instance has been created,");
        }
    }

    public static PixelActivityUnion with(Context context) {
        mPixelActivityUnion.mContext = context.getApplicationContext();
        return mPixelActivityUnion;
    }

    public PixelActivityUnion targetActivityClazz(Class<? extends Activity> activity) {
        mPixelActivityUnion.activity = activity;
        return mPixelActivityUnion;
    }

    public PixelActivityUnion args(@Nullable Bundle bundle) {
        mPixelActivityUnion.mBundle = bundle;
        return mPixelActivityUnion;
    }


    public PixelActivityUnion setActiviyManager(IActivityManager manager) {
        this.mActivityManager = manager;
        return mPixelActivityUnion;
    }

    public static void start() {
        mPixelActivityUnion.doRegister();
    }

    public static void quit() {
        mPixelActivityUnion.exit();
    }

    public void exit() {
        if (mScreenStateBroadcast != null) {
            mContext.unregisterReceiver(mScreenStateBroadcast);
        }
        if (mActivityManager != null && activity != null) {
            mActivityManager.removeAcitivtyByClazz(activity);
        }
    }

    private void doRegister() {

        if (mContext == null)
            throw new NullPointerException("context is null");
        if (activity == null)
            throw new NullPointerException("target activity must nonnull");
        Log.i("Infoss", "已经注册广播");
        mScreenStateBroadcast = new ScreenStateBroadcast();
        IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mContext.registerReceiver(mScreenStateBroadcast, mIntentFilter);
    }


    class ScreenStateBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i("Infoss", "action:" + intent.getAction());
            /*开锁*/
            if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                onScreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                onScrenntOff();
            }
        }
    }

    private void onScreenOn() {
        mActivityManager.removeAcitivtyByClazz(activity);
    }

    private void onScrenntOff() {
        Intent mIntent = new Intent(mContext, activity);
        if (mBundle != null)
            mIntent.putExtras(mBundle);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mIntent);
    }

}
