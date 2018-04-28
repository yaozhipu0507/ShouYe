package example.com.shouye.iview;

import android.content.Context;

import java.util.List;

import example.com.shouye.iview.OnNetListener;
import example.com.shouye.moudle.MyRvMiddleBean;
import example.com.shouye.moudle.ShouYeBean;

/**
 * Created by lenovo on 2018/4/27.
 */

public interface ShouYeJieKou {
    interface iView {
        //void shouBanner(List<ShouYeBean.DataBean> list);
        void shouBanner(ShouYeBean shouYeBean);
        void middleView(List<MyRvMiddleBean.DataBean> list);
        void shouYeRV(ShouYeBean shouYeBean);
    }

    interface IShouYeModel {
        void getBannerUrl(Context context, OnNetListener<ShouYeBean> onNetListener);
        void getMiddleViewUrl(Context context, OnNetListener<MyRvMiddleBean> onNetListener);
    }
}
