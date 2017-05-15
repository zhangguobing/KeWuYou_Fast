package com.weiwoju.kewuyou.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.wang.avi.AVLoadingIndicatorView;
import com.weiwoju.kewuyou.R;
import com.weiwoju.kewuyou.base.BaseController;
import com.weiwoju.kewuyou.base.BaseFragment;
import com.weiwoju.kewuyou.base.ContentView;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.controller.ProductController;
import com.weiwoju.kewuyou.model.bean.ProductCategory;
import com.weiwoju.kewuyou.model.event.OrderChangeEvent;
import com.weiwoju.kewuyou.model.event.ShoppingCartChangeEvent;
import com.weiwoju.kewuyou.network.ResponseError;
import com.weiwoju.kewuyou.ui.adapter.ProductCategoryAdapter;
import com.weiwoju.kewuyou.ui.adapter.ProductListAdapter;
import com.weiwoju.kewuyou.util.EventUtil;
import com.weiwoju.kewuyou.util.PinYin;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;

/**
 * Created by zhangguobing on 2017/4/28.
 */
@ContentView(R.layout.fragment_product)
public class ProductFragment extends BaseFragment<ProductController.ProductUiCallbacks>
      implements ProductController.ProductUi, TextWatcher{

    @Bind(R.id.et_product_search)
    EditText mProductSearchEt;
    @Bind(R.id.tv_category_name)
    TextView mCateNameTxt;
    @Bind(R.id.product_grid_view)
    GridView mProductGridView;
    @Bind(R.id.category_list_view)
    ListView mCategoryListView;
    @Bind(R.id.product_loading_view)
    AVLoadingIndicatorView mProductLoadingView;
    @Bind(R.id.category_loading_view)
    AVLoadingIndicatorView mCategoryLoadingView;

    private ProductCategoryAdapter mProductCategoryAdapter;
    private ProductListAdapter mProductListAdapter;

    private List<ProductCategory.ProlistBean> mProductList;

    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    private SearchHandler mSearchHandler = new SearchHandler(this);

    @Override
    protected BaseController getController() {
        return AppContext.getContext().getMainController().getProductController();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventUtil.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }

    @Override
    protected void initialViews(Bundle savedInstanceState) {
        super.initialViews(savedInstanceState);

        mProductCategoryAdapter = new ProductCategoryAdapter();
        mCategoryListView.setAdapter(mProductCategoryAdapter);
        mCategoryListView.setItemChecked(0, true);
        mCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mProductListAdapter.setItems(getProductsByCate(mProductList, mProductCategoryAdapter.getItem(position).getId()));
                mCateNameTxt.setText(mProductCategoryAdapter.getItem(position).getName());
            }
        });

        mProductListAdapter = new ProductListAdapter();
        mProductGridView.setAdapter(mProductListAdapter);

        mProductSearchEt.addTextChangedListener(this);

        getCallbacks().loadProductAndCategory();
    }

    @Subscribe
    public void onShoppingCartChange(ShoppingCartChangeEvent event){
        mProductListAdapter.notifyDataSetChanged();
        mProductCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadProductAndCategoryFinish(ProductCategory productCategory) {
        mProductLoadingView.smoothToHide();
        mCategoryLoadingView.smoothToHide();

        if(productCategory.getCatelist().size() > 0){
            mProductList = productCategory.getProlist();
            mProductListAdapter.setItems(getProductsByCate(mProductList, productCategory.getCatelist().get(0).getId()));
            mProductCategoryAdapter.setItems(productCategory.getCatelist());
            mCateNameTxt.setText(productCategory.getCatelist().get(0).getName());
        }
    }

    @Override
    public void onResponseError(ResponseError error) {
        super.onResponseError(error);
        mProductLoadingView.smoothToHide();
        mCategoryLoadingView.smoothToHide();
    }

    private  List<ProductCategory.ProlistBean> getProductsByCate(List<ProductCategory.ProlistBean> prolist,String cateId){
        List<ProductCategory.ProlistBean> list = new ArrayList<>();
        for (ProductCategory.ProlistBean product : prolist){
            if(cateId.equals(product.getCate_id())){
                list.add(product);
            }
        }
        return list;
    }

    public void onProductSearchResult(List<ProductCategory.ProlistBean> list){
        mProductListAdapter.setItems(list);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(final Editable s) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                if(s.toString().trim().length() == 0){
                    Message msg = new Message();
                    msg.obj = getProductsByCate(mProductList, mProductCategoryAdapter.getItem(mCategoryListView.getCheckedItemPosition()).getId());
                    mSearchHandler.sendMessage(msg);
                    return;
                }
                if(mProductList == null || mProductList.size() == 0) return;
                List<ProductCategory.ProlistBean> list = new ArrayList<>();
                String searchKeyWord = s.toString().trim().toLowerCase();
                for (ProductCategory.ProlistBean product : mProductList){
                    if(product.getName().contains(searchKeyWord)){
                        //先匹配名称
                        list.add(product);
                    }else{
                        //再匹配首拼
                        boolean firstLetterMatch = false;
                        String chineseName = filterChinese(product.getName());
                        String firstLetterSmall = PinYin.getFirstLetterSmall(chineseName);
                        if(searchKeyWord.length() <= firstLetterSmall.length()){
                            if(firstLetterSmall.startsWith(searchKeyWord)){
                                list.add(product);
                                firstLetterMatch = true;
                            }
                        }
                        //其次全拼
                        try {
                            if(!firstLetterMatch) {
                                String allPinYin = PinYin.getFirstLetterAllSmall(chineseName);
                                if(allPinYin.length() <= allPinYin.length()){
                                    if(allPinYin.startsWith(searchKeyWord)){
                                        list.add(product);
                                    }
                                }
                            }
                        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                            badHanyuPinyinOutputFormatCombination.printStackTrace();
                        }

                    }
                }
                Message msg = new Message();
                msg.obj = list;
                mSearchHandler.sendMessage(msg);
            }
        });
    }

    private String filterChinese(String chin)
    {
        chin = chin.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        return chin;
    }

    private static class SearchHandler extends Handler {
        private final WeakReference<ProductFragment> mFragment;

        public SearchHandler(ProductFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mFragment.get() == null) {
                return;
            }
            mFragment.get().onProductSearchResult((List<ProductCategory.ProlistBean>) msg.obj);
        }
    }

    @Subscribe
    public void onOrderChanged(OrderChangeEvent event){
        mProductListAdapter.notifyDataSetChanged();
        mProductCategoryAdapter.notifyDataSetChanged();
    }
}
