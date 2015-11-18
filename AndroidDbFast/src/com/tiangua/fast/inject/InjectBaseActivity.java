package com.tiangua.fast.inject;

import java.lang.reflect.Field;

import android.app.Activity;
import android.view.View;

import com.tiangua.fast.db.util.FasterLog;
import com.tiangua.fast.inject.core.InjectView;

public class InjectBaseActivity extends Activity
{

    @Override
    public void setContentView(int layoutResID)
    {
        // TODO Auto-generated method stub
        super.setContentView(layoutResID);
        injectView(this);
    }

    private void injectView(Activity activity)
    {
        // 得到Activity中的所有定义的字段
        Field[] fields = activity.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0)
        {
            for (Field field : fields)
            {
                // 方法返回true，如果指定类型的注解存在于此元素上
                if (field.isAnnotationPresent(InjectView.class))
                {
                    // 获得该成员的注解
                    InjectView mInjectView = field.getAnnotation(InjectView.class);
                    // 获得该注解的id
                    int viewId = mInjectView.id();
                    // 获得ID为viewID的组件对象
                    View view = activity.findViewById(viewId);

                    try
                    {
                        // 设置类的私有成员变量可以被访问
                        field.setAccessible(true);
                        // field.set(object,value)===object.fieldValue
                        // = value
                        field.set(activity, view);
                    }
                    catch (Exception e)
                    {
                        FasterLog.E(activity.getClass(), e);
                    }
                }
            }
        }
    }
}
