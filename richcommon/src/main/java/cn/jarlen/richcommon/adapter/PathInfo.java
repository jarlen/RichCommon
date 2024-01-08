package cn.jarlen.richcommon.adapter;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.Random;

/**
 * 路径控件AdapterviewBean
 */
public class PathInfo<T> {
    private int id;
    private String pathName;
    private T data;
    private int type;//1 根目录 2 业务数据路径
    private Object tag;

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public static final int TYPE_ROOT = 1;
    public static final int TYPE_DATA = 2;

    public PathInfo() {
        id = new Random().nextInt();
    }
    public PathInfo(T data) {
        id = new Random().nextInt();
        this.data = data;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PathInfo)) {
            return false;
        }
        PathInfo<?> pathInfo = (PathInfo<?>) o;
        return id == pathInfo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @PathType
    public int getType() {
        return type;
    }

    public void setType(@PathType int type) {
        this.type = type;
    }

    @IntDef({TYPE_ROOT, TYPE_DATA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PathType {
    }

}
