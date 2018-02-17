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
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ucmap.dingdinghelper.R;


public class PointActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point);
        ActivityManager.getInstance().addActivity(this);
        Window mWindow = this.getWindow();
        mWindow.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
        mLayoutParams.width = 1;
        mLayoutParams.height = 1;

        mLayoutParams.x = 0;
        mLayoutParams.y = 0;

        mWindow.setAttributes(mLayoutParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }
}
