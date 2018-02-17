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

import com.ucmap.dingdinghelper.utils.ShellUtils;

import java.util.List;


public class OrderThread extends Thread {


    private List<String> mList = null;

    public OrderThread(List<String> o) {
        this.mList = o;
    }

    @Override
    public void run() {
        if (mList != null && !mList.isEmpty()) {
            ShellUtils.CommandResult mCommandResult = ShellUtils.execCmd(mList, true);
        }
    }
}
