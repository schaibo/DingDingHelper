package com.ucmap.dingdinghelper.ui;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ucmap.dingdinghelper.R;
import com.ucmap.dingdinghelper.entity.PositionEntity;
import com.ucmap.dingdinghelper.sphelper.SPUtils;
import com.ucmap.dingdinghelper.utils.JsonUtils;

/**
 * @author ringle-android
 * @date 19-8-14
 * @since 1.0.0
 */
public class SaveClickPositionActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private PositionEntity mPositionEntity;
    EditText xEdt1;
    EditText yEdt1;
    EditText xEdt2;
    EditText yEdt2;
    EditText xEdt3;
    EditText yEdt3;
    EditText xEdt4;
    EditText yEdt4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_click_position);
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        this.setSupportActionBar(mToolbar);
        xEdt1 = (EditText) this.findViewById(R.id.xEdt1);
        yEdt1 = (EditText) this.findViewById(R.id.yEdt1);
        xEdt2 = (EditText) this.findViewById(R.id.xEdt2);
        yEdt2 = (EditText) this.findViewById(R.id.yEdt2);
        xEdt3 = (EditText) this.findViewById(R.id.xEdt3);
        yEdt3 = (EditText) this.findViewById(R.id.yEdt3);
        xEdt4 = (EditText) this.findViewById(R.id.xEdt4);
        yEdt4 = (EditText) this.findViewById(R.id.yEdt4);
        String p = SPUtils.getString("position_entity", "");
        if (!TextUtils.isEmpty(p)) {
            PositionEntity positionEntity = JsonUtils.parserJson(p, PositionEntity.class);
            bindPosition(positionEntity);
        }
        this.findViewById(R.id.save)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        save();
                        onBackPressed();
                        Toast.makeText(SaveClickPositionActivity.this, "保存坐标成功", Toast.LENGTH_LONG).show();
                        return;
                    }
                });
    }

    private void save() {
        if (TextUtils.isEmpty(xEdt1.getText().toString())) {
            return;
        }
        if (TextUtils.isEmpty(yEdt1.getText().toString())) {
            return;
        }
        PositionEntity positionEntity = new PositionEntity();
        Point point1 = new Point(Integer.parseInt(xEdt1.getText().toString()), Integer.parseInt(yEdt1.getText().toString()));
        positionEntity.setPoint1(point1);


        if (TextUtils.isEmpty(xEdt2.getText().toString())) {
            return;
        }
        if (TextUtils.isEmpty(yEdt2.getText().toString())) {
            return;
        }
        Point point2 = new Point(Integer.parseInt(xEdt2.getText().toString()), Integer.parseInt(yEdt2.getText().toString()));
        positionEntity.setPoint2(point2);

        if (TextUtils.isEmpty(xEdt3.getText().toString())) {
            return;
        }
        if (TextUtils.isEmpty(yEdt3.getText().toString())) {
            return;
        }
        Point point3 = new Point(Integer.parseInt(xEdt3.getText().toString()), Integer.parseInt(yEdt3.getText().toString()));
        positionEntity.setPoint3(point3);

        if (TextUtils.isEmpty(xEdt4.getText().toString())) {
            return;
        }
        if (TextUtils.isEmpty(yEdt4.getText().toString())) {
            return;
        }
        Point point4 = new Point(Integer.parseInt(xEdt4.getText().toString()), Integer.parseInt(yEdt4.getText().toString()));
        positionEntity.setPoint4(point4);
        SPUtils.save("position_entity", new Gson().toJson(positionEntity));
    }

    private void bindPosition(PositionEntity positionEntity) {
        xEdt1.setText(positionEntity.getPoint1().x + "");
        yEdt1.setText(positionEntity.getPoint1().y + "");
        xEdt2.setText(positionEntity.getPoint2().x + "");
        yEdt2.setText(positionEntity.getPoint2().y + "");
        xEdt3.setText(positionEntity.getPoint3().x + "");
        yEdt3.setText(positionEntity.getPoint3().y + "");
        xEdt4.setText(positionEntity.getPoint4().x + "");
        yEdt4.setText(positionEntity.getPoint4().y + "");

    }
}
