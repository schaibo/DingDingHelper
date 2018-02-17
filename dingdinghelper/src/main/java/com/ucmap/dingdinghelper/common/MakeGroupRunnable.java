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

package com.ucmap.dingdinghelper.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.text.TextUtils;

import com.ucmap.dingdinghelper.utils.ShellUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MakeGroupRunnable implements Runnable {
    private Context mContext;

    private Callback c;

    public MakeGroupRunnable(Context context, Callback c) {
        this.mContext = context.getApplicationContext();
        this.c = c;
    }

    @Override
    public void run() {
        makeSystemApp();
    }

    private void makeSystemApp() {

        List<ApplicationInfo> mInfos = mContext.getPackageManager().getInstalledApplications(0);
        String appPath = findAppPathByApplicationInfo(mContext.getPackageName(), mInfos);
        if (TextUtils.isEmpty(appPath)) {
            if (c != null)
                c.call(false);
            return;
        }
        boolean tag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tag = doSystemAppBeyongLollopop(appPath);
        } else {
            tag = doSystemAppCommon(appPath);
        }

        if (c != null)
            c.call(tag);
    }

    private boolean doSystemAppCommon(String appPath) {
        List<String> mList = Collections.emptyList();
        //
        mList.add("busybox mount -o remount,rw /system");
        mList.add("busybox  cp -r " + appPath + " /system/app/" + (mContext.getPackageName() + ".apk"));
        mList.add("busybox chmod 777 -v /system/app/" + (mContext.getPackageName() + ".apk"));
        mList.add("busybox mount -o remount,ro /system");//只读
//        mList.add("busybox rm -f " + appPath);
        ShellUtils.CommandResult t = ShellUtils.execCmd(mList, true);
        if (t.result == -1) {
            return false;
        }
        return true;
    }

    private boolean doSystemAppBeyongLollopop(String path) {
        File mFile = new File(path);
        String parent = mFile.getParent();
        String parentName = mFile.getParentFile().getName();

        List<String> mList = new ArrayList<>();
        //
        mList.add("busybox mount -o remount,rw /system");
        mList.add("busybox  cp -r " + parent + " /system/app/" + mContext.getPackageName());
        mList.add("busybox chmod 777 -v /system/app/" + (mContext.getPackageName()));
        mList.add("busybox chmod 777  /system/app/" + (mContext.getPackageName()) + "/base.apk");
        mList.add("busybox mount -o remount,ro /system");//只读
//        mList.add("busybox rm -rf /data/app/" + parentName);
        ShellUtils.CommandResult t = ShellUtils.execCmd(mList, true);
        if (t.result == -1) {
            return false;
        }
        return true;
    }

    private String findAppPathByApplicationInfo(String packageName, List<ApplicationInfo> infos) {
        for (ApplicationInfo mInfo : infos) {
            if (mInfo.sourceDir.contains(packageName))
                return mInfo.sourceDir;
        }
        return null;
    }

    public interface Callback {
        void call(boolean isSuccess);
    }
}
