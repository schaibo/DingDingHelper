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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class ActivityManager implements IActivityManager {

    private static final List<WeakReference<Activity>> mActivities = new ArrayList<>();


    private static final AtomicReference<ActivityManager> atomic = new AtomicReference<>();

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {

        for (; ; ) {
            ActivityManager mActivityManager = atomic.get();
            if (mActivityManager != null)
                return mActivityManager;
            if (atomic.compareAndSet(null, new ActivityManager())) {
                return atomic.get();
            }

        }
    }

    private boolean findTargetAndRemove(Class<? extends Activity> activityClazz) {
        boolean tag = false;
        //倒序remove
        for (int i = mActivities.size() - 1; i >= 0; i--) {

            WeakReference<Activity> mActivityWeakReference = mActivities.get(i);
            if (mActivityWeakReference.get() == null) {
                mActivities.remove(i);
            }
        }

        for (int i = 0; i < mActivities.size(); i++) {
            WeakReference<Activity> mWeakReference = mActivities.get(i);
            if (mWeakReference.get() != null && mWeakReference.get().getClass() == activityClazz) {
                mWeakReference.get().finish();
                mActivities.remove(i);
                tag = true;
                break;
            }
        }

        return tag;
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            findTargetAndRemove(activity.getClass());
            mActivities.add(new WeakReference<Activity>(activity));
        }
    }

    public void removeActivity(Activity activity) {
        findTargetAndRemove(activity.getClass());
    }


    @Override
    public void removeAcitivtyByClazz(Class<? extends Activity> clazz) {
        findTargetAndRemove(clazz);
    }
}
