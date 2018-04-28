package example.com.shouye.moudle;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import java.io.IOException;
import example.com.shouye.api.Api;
import example.com.shouye.utils.HttpUtils;
import example.com.shouye.iview.OnNetListener;
import example.com.shouye.iview.ShouYeJieKou;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/4/27.
 */

public class ShouYeModel implements ShouYeJieKou.IShouYeModel{
    protected Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void getBannerUrl(Context context, final OnNetListener<ShouYeBean> onNetListener) {
        HttpUtils.getHttpUtils(context).doGet(Api.ZHUYEURL, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                final ShouYeBean shouYeBean = new Gson().fromJson(str, ShouYeBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(shouYeBean);
                    }
                });
            }
        });
    }

    @Override
    public void getMiddleViewUrl(Context context, final OnNetListener<MyRvMiddleBean> onNetListener) {
        HttpUtils.getHttpUtils(context).doGet(Api.ZHUYEMIDDLEVIEW, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onNetListener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                final MyRvMiddleBean middleBean = new Gson().fromJson(str, MyRvMiddleBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(middleBean);
                    }
                });
            }
        });
    }
}
