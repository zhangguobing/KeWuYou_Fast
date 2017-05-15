package com.weiwoju.kewuyou.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseActivity;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.context.AppCookie;
import com.weiwoju.kewuyou.controller.UserController;
import com.weiwoju.kewuyou.model.bean.LoginResult;
import com.weiwoju.kewuyou.network.ResponseError;
import com.weiwoju.kewuyou.ui.Display;
import com.weiwoju.kewuyou.util.MD5Utils;
import com.weiwoju.kewuyou.util.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zhangguobing on 2017/4/24.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity<UserController.UserUiCallbacks>
             implements UserController.UserLoginUi{

    @Bind(R.id.et_username)
    EditText mEtUserName;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.root)
    View mRootView;

    private int mScrollHeight;

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);
        mEtUserName.setText(AppCookie.getLastPhone());
        controlKeyboardLayout(mRootView, mBtnLogin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppCookie.isLoggin()){
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getUserController();
    }

    @OnClick(R.id.btn_login)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                login();
                break;
        }
    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
        mBtnLogin.setEnabled(true);
    }

    @Override
    public void userLoginFinish(List<LoginResult.ShopBean> shopList) {
        cancelLoading();
        mBtnLogin.setEnabled(true);
        if(shopList != null && shopList.size() > 1){
            showSelectShopDialog(shopList);
        }else{
            showMain();
        }
    }

    @Override
    public void setShopFinish() {
        cancelLoading();
        showMain();
    }

    private void showSelectShopDialog(final List<LoginResult.ShopBean> shopList){
        final NormalListDialog dialog = new NormalListDialog(this, getShopNamesByList(shopList));
        dialog.title("请选择店铺")
                .layoutAnimation(null)
                .cornerRadius(5)
                .widthScale(0.4f)
                .show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                showLoading(R.string.label_being_something);
                getCallbacks().setShop(shopList.get(position).getId());
            }
        });
    }

    private String[] getShopNamesByList(List<LoginResult.ShopBean> shopList){
        String[] shopNameArrays = new String[shopList.size()];
        for (int i = 0 ; i < shopList.size() ; i++){
            shopNameArrays[i] = shopList.get(i).getName();
        }
        return  shopNameArrays;
    }

    private void showMain(){
        startActivity(new Intent(this, MainActivity.class));
        Display display = getDisplay();
        if (display != null) {
            display.finishActivity();
        }
    }

    private void login(){
        // 验证用户名是否为空
        final String account = mEtUserName.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.show("帐户不能为空");
            return;
        }
        // 验证密码是否为空
        final String password = mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastUtil.show("密码不能为空");
            return;
        }
        // 禁用登录按钮,避免重复点击
        mBtnLogin.setEnabled(false);
        // 显示提示对话框
        showLoading(R.string.label_being_login);
        // 发起登录的网络请求
        try {
            String md5Password = MD5Utils.MD5(password);
            getCallbacks().login(account,md5Password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param root 最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域的底部
                    if(mScrollHeight == 0){
                        mScrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    }
                    root.scrollTo(0, mScrollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }

}
