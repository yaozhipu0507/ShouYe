package example.com.shouye.api;

/**
 * Created by lenovo on 2018/4/27.
 */

public class Api {
    public static boolean isOnline = false;
    public static final String DEV = "https://www.zhaoapi.cn/";
    public static final String WROK = "";
    public static final String HOST = isOnline ? WROK : DEV;
    public static final String PRODUCT_CATAGORY_LIST = HOST + "/product/getProducts";
    public static final String LOGIN = HOST + "/user/login";//登陆
    public static final String REGISTER = HOST + "/user/reg";//注册
    public static final String CLASS = HOST + "/product/getCatagory";//分类
    public static final String PRODUCT_CATAGORY = HOST + "/product/getProductCatagory";//商品子分类接口
    public static final String ZHUYEURL = HOST+"/ad/getAd";
    public static final String ZHUYEMIDDLEVIEW = HOST+"/product/getCatagory";
    public static final String ADD_CARD = HOST + "/product/addCart";
    public static final String PRODUCT_DETAIL = HOST + "/product/getProductDetail?pid=%s&source=android";
    public static final String SELECT_CARD = HOST + "/product/getCarts";
    public static final String DEL_CARD = HOST + "/product/deleteCart";

}
