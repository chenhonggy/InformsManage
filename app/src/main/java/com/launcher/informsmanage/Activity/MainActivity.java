package com.launcher.informsmanage.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by chen on 14-7-26.
 */
public class MainActivity extends Activity{
    /**
     *
     * 进入下一个Activity
     *
     * @param extras The Bundle of extras to add to this intent, 可以为null.
     * @param cls 	The name of a class inside of the application package that will be used as the component for this Intent.
     */
    public void pushToNextActivity(Bundle extras, Class<?>cls){
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(this, cls);
        startActivity(intent);
    }

    /**
     *
     * 进入下一个Activity
     *
     *
     * @param cls 	The name of a class inside of the application package that will be used as the component for this Intent.
     */
    public void pushToNextActivity(Class<?>cls){
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }

    /**
     *
     * 进入下一个Activity
     *
     *
     * @param cls 	The name of a class inside of the application package that will be used as the component for this Intent.
     */
    public void pushToNextActivity(Class<?>cls,int requestCode){
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
    }

}
