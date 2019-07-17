package com.crgt.router;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * util of auto binding bundle data
 *
 * @author android
 * @date 2019/5/28
 * @mail android@crgecent.com
 */

public final class RouterBindUtil {
    private static final String TAG = RouterBindUtil.class.getSimpleName();

    /**
     * auto parse bundle params, and bind data to fields
     *
     * @param object
     */
    public static void inject(@NonNull Object object) {
        if (!verify(object)) {
            return;
        }
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return;
        }
        Bundle extras = null;
        if (object instanceof Activity) {
            extras = ((Activity) object).getIntent().getExtras();
        } else if (object instanceof Fragment) {
            extras = ((Fragment) object).getArguments();
        } else if (object instanceof android.app.Fragment) {
            extras = ((android.app.Fragment) object).getArguments();
        }
        if (extras == null) {
            return;
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(RouterParam.class)) {
                try {
                    bindData(object, extras, field);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * auto parse uri params, and put data into the bundle
     *
     * @param clazz activity or fragment class
     * @param uri uri
     * @param bundle bundle
     */
    public static void autoPutParam(Class<?> clazz, @NonNull Uri uri, @NonNull Bundle bundle) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return;
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(RouterParam.class)) {
                try {
                    parseParam(uri, bundle, field);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void bindData(Object object, @NonNull Bundle extras, @NonNull Field field)
            throws IllegalAccessException {
        String key = field.getAnnotation(RouterParam.class).key();
        if (TextUtils.isEmpty(key)) {
            key = field.getName();
        }
        field.setAccessible(true);
        Class<?> type = field.getType();

        Object value = null;
        if (String.class.isAssignableFrom(type)) {
            // String type
            value = extras.getString(key, (String) field.get(object));
        } else if (String[].class.isAssignableFrom(type)) {
            // String[] type
            value = extras.getStringArray(key);
        } else if (int.class.isAssignableFrom(type)) {
            // int type
            value = extras.getInt(key, (Integer) field.get(object));
        } else if (int[].class.isAssignableFrom(type)) {
            // int[] type
            value = extras.getIntArray(key);
        } else if (ArrayList.class.isAssignableFrom(type)) {
            // ArrayList type
            ParameterizedType listType = (ParameterizedType) field.getGenericType();

            Class<?> elementType = (Class<?>) listType.getActualTypeArguments()[0];
            if (Integer.class.isAssignableFrom(elementType)) {
                // ArrayList<Integer> type
                value = extras.getIntegerArrayList(key);
            } else if (String.class.isAssignableFrom(elementType)) {
                // ArrayList<String> type
                value = extras.getStringArrayList(key);
            } else if (Parcelable.class.isAssignableFrom(elementType)) {
                // ArrayList<Parcelable> type
                value = extras.getSerializable(key);
            }
        } else if (boolean.class.isAssignableFrom(type)) {
            // boolean type
            value = extras.getBoolean(key, (Boolean) field.get(object));
        } else if (long.class.isAssignableFrom(type)) {
            // long type
            value = extras.getLong(key, (Long) field.get(object));
        } else if (float.class.isAssignableFrom(type)) {
            // float type
            value = extras.getFloat(key, (Float) field.get(object));
        } else if (double.class.isAssignableFrom(type)) {
            // double type
            value = extras.getDouble(key, (Double) field.get(object));
        } else if (Parcelable.class.isAssignableFrom(type)) {
            // Parcelable type
            value = extras.getParcelable(key);
        } else if (Serializable.class.isAssignableFrom(type)) {
            // Serializable type
            value = extras.getSerializable(key);
        } else if (Bundle.class.isAssignableFrom(type)) {
            value = extras.getBundle(key);
        }
        if (value != null) {
            field.set(object, value);
        }
    }

    private static void parseParam(@NonNull Uri uri, @NonNull Bundle bundle, @NonNull Field field) {
        String key = field.getAnnotation(RouterParam.class).key();
        if (TextUtils.isEmpty(key)) {
            key = field.getName();
        }
        field.setAccessible(true);
        Class<?> type = field.getType();

        List<String> values = uri.getQueryParameters(key);
        String value = null;
        if (values != null && values.size() > 0) {
            value = values.get(0);
        }
        if (TextUtils.isEmpty(value)) {
            return;
        }

        if (String.class.isAssignableFrom(type)) {
            // String type
            bundle.putString(key, value);
        } else if (int.class.isAssignableFrom(type)) {
            // int type
            bundle.putInt(key, Integer.parseInt(value));
        } else if (boolean.class.isAssignableFrom(type)) {
            // boolean type
            bundle.putBoolean(key, Boolean.parseBoolean(value));
        } else if (long.class.isAssignableFrom(type)) {
            // long type
            bundle.putLong(key, Long.parseLong(value));
        } else if (float.class.isAssignableFrom(type)) {
            // float type
            bundle.putFloat(key, Float.parseFloat(value));
        } else if (double.class.isAssignableFrom(type)) {
            // double type
            bundle.putDouble(key, Double.parseDouble(value));
        } else {
            bundle.putString(key, value);
        }
    }

    private static boolean verify(Object o) {
        return o instanceof Activity || o instanceof Fragment || o instanceof android.app.Fragment;
    }
}
