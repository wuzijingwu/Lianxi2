package text.bwei.com.xiangqing;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 作者：戈鹏
 * on 2017/12/19 09:45
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
