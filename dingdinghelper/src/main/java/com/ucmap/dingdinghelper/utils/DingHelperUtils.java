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

package com.ucmap.dingdinghelper.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import com.ucmap.dingdinghelper.entity.AccountEntity;
import com.ucmap.dingdinghelper.sphelper.SPUtils;
import com.ucmap.dingdinghelper.timing.TimingManagerUtil;

import java.util.Random;

public class DingHelperUtils {

    private static Random mRandom = new Random();

    public static void setAlarm(AccountEntity accountEntity, Context context) {

        String gTime = (String) SPUtils.getString(Constants.MORNING_CHECK_IN_TIME, "8:45");
        String[] hm = gTime.split(":");
        String nTime = (String) SPUtils.getString(Constants.AFTERNOON_CHECK_IN_TIME, "20:45");
        String[] nm = nTime.split(":");
        //截取手机号码后四位作为通知id
        int id = Integer.parseInt(accountEntity.getAccount().substring(accountEntity.getAccount().length() - 4, accountEntity.getAccount().length()));
        for (int i = 1; i <= 6; i++) {
//            int tm = isAdd() ? Integer.parseInt(hm[1]) + mRandom.nextInt(5) : Integer.parseInt(hm[1]) - mRandom.nextInt(5);
            TimingManagerUtil.setTiming(context, 2, Integer.parseInt(hm[0]), Integer.parseInt(hm[1]), id + i, i);
            Log.i("Infoss", "闹钟ID:" + (id + i) + "   hour:" + Integer.parseInt(hm[0]) + "   min: " + Integer.parseInt(hm[1]) + "   week:" + i);
//            int ta = isAdd() ? Integer.parseInt(nm[1]) + mRandom.nextInt(5) : Integer.parseInt(nm[1]) - mRandom.nextInt(5);
            TimingManagerUtil.setTiming(context, 2, Integer.parseInt(nm[0]), Integer.parseInt(nm[1]), id + i + 10, i);
            Log.i("Infoss", "闹钟ID:" + (id + i + 10) + "   hour:" + Integer.parseInt(nm[0]) + "   min: " + Integer.parseInt(nm[1]) + "   week:" + i);
        }
    }

    private static boolean isAdd() {
        return mRandom.nextBoolean();
    }

    public static boolean isScreenLocked(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        Log.i("Infoss", "isScreenLocked:" + keyguardManager.inKeyguardRestrictedInputMode());
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    public static boolean isScreenLight(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        Log.i("Infoss", "isScreenLight:" + pm.isScreenOn());
        return pm.isScreenOn();
    }
}
