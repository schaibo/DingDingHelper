package com.ucmap.dingdinghelper.entity;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ringle-android
 * @date 19-8-14
 * @since 1.0.0
 */
public class PositionEntity implements Parcelable {

    private Point mPoint1;
    private Point mPoint2;
    private Point mPoint3;
    private Point mPoint4;


    public PositionEntity(Point point1, Point point2, Point point3, Point point4) {
        mPoint1 = point1;
        mPoint2 = point2;
        mPoint3 = point3;
        mPoint4 = point4;
    }

    public PositionEntity() {
    }


    protected PositionEntity(Parcel in) {
        mPoint1 = in.readParcelable(Point.class.getClassLoader());
        mPoint2 = in.readParcelable(Point.class.getClassLoader());
        mPoint3 = in.readParcelable(Point.class.getClassLoader());
        mPoint4 = in.readParcelable(Point.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mPoint1, flags);
        dest.writeParcelable(mPoint2, flags);
        dest.writeParcelable(mPoint3, flags);
        dest.writeParcelable(mPoint4, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PositionEntity> CREATOR = new Creator<PositionEntity>() {
        @Override
        public PositionEntity createFromParcel(Parcel in) {
            return new PositionEntity(in);
        }

        @Override
        public PositionEntity[] newArray(int size) {
            return new PositionEntity[size];
        }
    };

    public Point getPoint1() {
        return mPoint1;
    }

    public void setPoint1(Point point1) {
        mPoint1 = point1;
    }

    public Point getPoint2() {
        return mPoint2;
    }

    public void setPoint2(Point point2) {
        mPoint2 = point2;
    }

    public Point getPoint3() {
        return mPoint3;
    }

    public void setPoint3(Point point3) {
        mPoint3 = point3;
    }

    public Point getPoint4() {
        return mPoint4;
    }

    public void setPoint4(Point point4) {
        mPoint4 = point4;
    }
}
