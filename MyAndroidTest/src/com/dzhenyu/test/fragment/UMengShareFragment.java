package com.dzhenyu.test.fragment;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dzhenyu.test.R;
import com.dzhenyu.test.ui.CustomGridView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.yixin.controller.UMYXHandler;


import java.util.List;

/**
 * Created by onlymem on 2015/11/4.
 */
public class UMengShareFragment extends BaseFragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.umeng_share_layout, container, false);
        view.findViewById(R.id.umeng_share).setOnClickListener(this);
        view.findViewById(R.id.system_share).setOnClickListener(this);
        view.findViewById(R.id.system_share_custom_ui).setOnClickListener(this);
        view.findViewById(R.id.umeng_share_custom_ui).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.umeng_share:
                shareByUmeng();
                break;
            case R.id.system_share:
                shareBySystem(null);
                break;
            case R.id.system_share_custom_ui:
                shareBySystemCustomUI();
                break;
            case R.id.umeng_share_custom_ui:
                shareByUmengCustomUI();
                break;
        }
    }

    private void shareBySystem(String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (TextUtils.isEmpty(filePath)) {
            intent.setType("text/plain");
        } else {
            intent.setType("image/jpg");
        }
        //Intent.EXTRA_SUBJECT 主题
        intent.putExtra(Intent.EXTRA_SUBJECT, "system share subject");
        //Intent.EXTRA_TEXT 内容
        intent.putExtra(Intent.EXTRA_TEXT, "sytem share text");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "share"));
    }

    private List<ResolveInfo> appList = null;

    private PackageManager packageManager = null;

    /**
     * 系统分享自定义布局
     */
    private void shareBySystemCustomUI() {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        packageManager = mContext.getPackageManager();
        appList = packageManager
                .queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);

        if (appList == null || appList.size() <= 0) {
            return;
        }

        Dialog dialog = new Dialog(mContext);
        /**
         * dialog的根布局为什么要以五大布局开始，不能直接写成Gridview，直接写成GridView会不显示布局
         */
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                              LinearLayout.LayoutParams.WRAP_CONTENT));

        GridView gridView = new GridView(mContext);
        gridView.setNumColumns(4);
        gridView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                            300));
        gridView.setAdapter(GridViewAdapter);
        gridView.setGravity(Gravity.CENTER);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ResolveInfo info = appList.get(position);
                intent.setComponent(
                        new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
                startActivity(intent);
            }
        });
        gridView.setVisibility(View.VISIBLE);
        linearLayout.addView(gridView);
        dialog.setContentView(linearLayout);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER);
        dialog.show();
    }


    private BaseAdapter GridViewAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return appList.size();
        }

        @Override
        public Object getItem(int position) {
            return appList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHandler viewHandler;
            if(convertView==null){
                convertView=LayoutInflater.from(mContext).inflate(R.layout.share_image,null);
                viewHandler=new ViewHandler();
                viewHandler.imageView= (ImageView) convertView.findViewById(R.id.share_image_icon);
                convertView.setTag(viewHandler);
            }else{
                viewHandler= (ViewHandler) convertView.getTag();
            }
            viewHandler.imageView.setImageDrawable(appList.get(position).loadIcon(packageManager));
            return convertView;
//            ImageView imageView = null;
//            imageView = new ImageView(mContext);
//            /**
//             * 此处不能返回convertView=new view(),convertView.setTag(imageView),否则不会显示？
//             */
//            imageView.setLayoutParams(
//                    new ViewGroup.LayoutParams(100,
//                                               100));
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            imageView.setImageDrawable(appList.get(position).loadIcon(packageManager));
//            return imageView;
        }

        class ViewHandler{
            ImageView imageView;
        }
    };

    private void shareByUmeng() {
        initUmengShare();
        umSocialService.getConfig()
                .setPlatforms(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN,
                              SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA);
        umSocialService.openShare(getActivity(), snsPostListener);
    }

    // 友盟分享结果监听器
    private SocializeListeners.SnsPostListener snsPostListener =
            new SocializeListeners.SnsPostListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i,
                                       SocializeEntity socializeEntity) {
                }
            };

    private UMSocialService umSocialService = null;

    private void initUmengShare() {
        umSocialService =
                UMServiceFactory.getUMSocialService(ShareConstants.SHARE_APP_DESCRIPTOR);
        umSocialService.setShareContent("友盟share test");

        UMQQSsoHandler qq = new UMQQSsoHandler(getActivity(), ShareConstants.QQ_APP_ID,
                                               ShareConstants.QQ_APP_KEY);
        qq.addToSocialSDK();

        QZoneSsoHandler qqzone = new QZoneSsoHandler(getActivity(), ShareConstants.QQ_APP_ID,
                                                     ShareConstants.QQ_APP_KEY);
        qqzone.addToSocialSDK();

        /*       //微信添加方式
                UMWXHandler umwxHandler =
                        new UMWXHandler(getActivity(), ShareConstants.WX_APP_ID, ShareConstants.WX_APP_kEY);
                umwxHandler.addToSocialSDK();


                UMWXHandler umwxCircleHandler =
                        new UMWXHandler(getActivity(), ShareConstants.WX_APP_ID, ShareConstants.WX_APP_kEY);
                umwxCircleHandler.setToCircle(true);
                umwxCircleHandler.addToSocialSDK();
         */

        UMYXHandler umyxHandler = new UMYXHandler(getActivity(), ShareConstants.YX_APP_ID);
        umyxHandler.addToSocialSDK();

        umSocialService.setShareContent("分享百度链接");
        umSocialService.setShareMedia(new UMImage(mContext, R.drawable.level5));

        /*
                 // 添加数据方式
                QQShareContent qqShareContent = new QQShareContent();
                qqShareContent.setTitle("");
                qqShareContent.setShareContent("");
                umSocialService.setShareMedia(qqShareContent);

                QZoneShareContent qZoneShareContent = new QZoneShareContent();
                qZoneShareContent.setShareContent("");
                qZoneShareContent.setTitle("");
                umSocialService.setShareMedia(qZoneShareContent);
          */
    }


    private void shareByUmengCustomUI() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                mContext);
        dialogBuilder.setTitle("自定义UI");
        // 新浪、QQ、QQ空间、易信、来往、豆瓣、人人平台
        final CharSequence[] items = {"新浪微博", "QQ", "QQ空间", "易信", "来往", "人人网"};
        dialogBuilder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // 获取用户点击的平台
                /**
                 * 调用分享，可以自己写布局，然后调用该方法，
                 *  umSocialService.directShare();
                 */
            } // end of onClick
        });

        dialogBuilder.create().show();
    }

    static class ShareConstants {
        public static final String SHARE_APP_DESCRIPTOR = "com.dzhenyu.test";
        public static final String QQ_APP_ID = "100424468";
        public static final String QQ_APP_KEY = "c7394704798a158208a74ab60104f0ba";
        public static final String WX_APP_ID = "";
        public static final String WX_APP_kEY = "";
        public static final String YX_APP_ID = "yxc0614e80c9304c11b0391514d09f13bf";
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }

}
