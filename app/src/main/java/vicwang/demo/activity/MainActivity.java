package vicwang.demo.activity;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vicwang.demo.R;
import vicwang.demo.adapter.MainHouseListAdapter;
import vicwang.demo.adapter.item.HouseListAnimalItem;
import vicwang.demo.adapter.item.MainHouseListItem;
import vicwang.demo.fragment.AnimalInfoFragment;
import vicwang.demo.fragment.HouseInfoFragment;
import vicwang.demo.mvp.model.ApiRepository;
import vicwang.demo.mvp.presenter.MainBridge;
import vicwang.demo.mvp.presenter.MainPresenter;
import vicwang.demo.util.ViewClick;

public class MainActivity extends BasicActivity implements MainBridge.View, MainView {

    /*
     * Tool or page widget
     * */
    private RecyclerView wRecycleView;

    private FrameLayout wLay_Loading_Area;
    /*
     * Toolbar
     * */
    private TextView wTxt_Toolbar_Title;
    private ImageView wImg_Toolbar_Home;
    private FrameLayout wLay_Btn_Home;

    /*
     * Presenter
     * */
    private MainPresenter mPresenter;

    /*
     * Main Page data
     * */
    private ArrayList<MainHouseListItem> mData = new ArrayList<>();

    private MainHouseListItem mAreaSelectItem;

    private boolean mIsHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setActivityView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void viewInit() {

        wRecycleView = findViewById(R.id.main_recycleview);

        wTxt_Toolbar_Title = findViewById(R.id.main_toolsbar_txt_title);
        wImg_Toolbar_Home = findViewById(R.id.main_toolsbar_img_home);
        wLay_Btn_Home = findViewById(R.id.main_toolsbar_lay_btn_home_click);

        wLay_Loading_Area = findViewById(R.id.main_lay_loading_area);

        mPresenter = new MainPresenter(this, new ApiRepository());
    }

    @Override
    protected void setViewValue() {
        //data loading
        onShowLoadingView(true);
        mPresenter.initHouseData();

        setTitleBar("", true, false);
    }

    @Override
    protected void setViewListener() {
        wLay_Btn_Home.setOnClickListener(mNormalClickListener);
    }

    /*
     * Custom Title Bar
     * */
    private void setTitleBar(String title, boolean menu, boolean ignoreAnim) {
        mIsHome = menu;
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        AnimatedVectorDrawable drawable;
        if (menu) {
            drawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_back_animatable);
        } else {
            drawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_menu_animatable);
        }
        if (!ignoreAnim)
            wImg_Toolbar_Home.setImageDrawable(drawable);
        assert drawable != null;
        drawable.start();

        wTxt_Toolbar_Title.setText(title.equals("") ? getString(R.string.main_taipei_zoo) : title);
    }

    /*
     * Add Fragment in main_fragment_outer_page_container
     * */
    private void onOpenFragment(Fragment f, String fragmentTag) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.fragment_anim_slide_right_in, R.animator.fragment_anim_slide_do_nothing
                        , R.animator.fragment_anim_slide_do_nothing, R.animator.fragment_anim_slide_right_out)
                .add(R.id.main_fragment_outer_page_container, f, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }

    /*
     * Normal Click
     * */
    private ViewClick mNormalClickListener = new ViewClick() {
        @Override
        protected void CustomOnClick(View view) {
            switch (view.getId()) {
                case R.id.main_toolsbar_lay_btn_home_click:
                    if (mIsHome)
                        onCallToast(":)", false);
                    else
                        onBackPressed();
                    break;
            }
        }
    };

    /*
     * Presenter View Area
     * */
    @Override
    public void onShowLoadingView(final boolean isShow) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isShow)
                    wLay_Loading_Area.setVisibility(View.VISIBLE);
                else
                    wLay_Loading_Area.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onShowErrorMsg(final int errorType) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onShowLoadingView(false);
                openErrorDialog(getString(R.string.http_get_error), errorType);
            }
        });
    }

    @Override
    public void onHouseDataLoadSuccess(final String result, final Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onShowLoadingView(false);
                loadListItem((ArrayList<MainHouseListItem>) o);
            }
        });
    }

    @Override
    public void onAnimalDataLoadSuccess(final String result, final Object o, final MainHouseListItem item) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setTitleBar(item.getE_Name(), false, false);
                onShowLoadingView(false);

                onOpenFragment(HouseInfoFragment.newInstance(item, (ArrayList<HouseListAnimalItem>) o)
                        , HouseInfoFragment.FRAGMENT_HOUSE_INFO_TAG);
            }
        });
    }


    /*
     * Loading Main page List Item fun
     * */
    private void loadListItem(ArrayList<MainHouseListItem> data) {
        mData.clear();
        mData = data;
        wRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        MainHouseListAdapter mAdapter = new MainHouseListAdapter(mContext, mData);
        mAdapter.setClickListener(recycleClick);
        wRecycleView.setAdapter(mAdapter);
        wRecycleView.setItemAnimator(new DefaultItemAnimator());
    }

    /*
     * Main Page list click listener
     * */
    private MainHouseListAdapter.ItemClickListener recycleClick = new MainHouseListAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            onShowLoadingView(true);
            mAreaSelectItem = mData.get(position);
            mPresenter.initAnimalData(mData.get(position));
        }
    };

    /*
     * Back Pressed fun
     * */
    private long mFastBackReturn = 0;

    @Override
    public void onBackPressed() {
        if (Math.abs(System.currentTimeMillis() - mFastBackReturn) < 500) {
            return;
        }
        mFastBackReturn = System.currentTimeMillis();
        onBackFragment();
    }

    /*
     * Fragment Callback Area
     * */
    @Override
    public void onCallToast(String str, boolean Long) {
        showToast(str, Long);
    }

    @Override
    public void onBackFragment() {
        HouseInfoFragment fHouse = (HouseInfoFragment) getSupportFragmentManager().findFragmentByTag(HouseInfoFragment.FRAGMENT_HOUSE_INFO_TAG);
        AnimalInfoFragment fAnimalInfo = (AnimalInfoFragment) getSupportFragmentManager().findFragmentByTag(AnimalInfoFragment.FRAGMENT_ANIMAL_INFO_TAG);
        if (fAnimalInfo != null && fAnimalInfo.isVisible()) {
            if (mAreaSelectItem != null)
                setTitleBar(mAreaSelectItem.getE_Name(), false, true);
            getSupportFragmentManager().popBackStack(AnimalInfoFragment.FRAGMENT_ANIMAL_INFO_TAG, 1);
        } else if (fHouse != null && fHouse.isVisible()) {
            setTitleBar("", true, false);
            getSupportFragmentManager().popBackStack(HouseInfoFragment.FRAGMENT_HOUSE_INFO_TAG, 1);
        } else {
            finish();
        }
    }

    @Override
    public void onOpenAnimalInfoPage(HouseListAnimalItem item) {
        setTitleBar(item.getA_Name_Ch(), false, true);
        onOpenFragment(AnimalInfoFragment.newInstance(item)
                , AnimalInfoFragment.FRAGMENT_ANIMAL_INFO_TAG);
    }
}
