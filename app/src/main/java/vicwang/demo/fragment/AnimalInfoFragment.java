package vicwang.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import vicwang.demo.R;
import vicwang.demo.activity.MainView;
import vicwang.demo.adapter.item.HouseListAnimalItem;

public class AnimalInfoFragment extends Fragment {

    public final static String FRAGMENT_ANIMAL_INFO_TAG = "FRAGMENT_ANIMAL_INFO_TAG";

    private static final String GET_ANIMAL_ITEM_KEY = "GET_ANIMAL_ITEM_KEY";

    private View mView;
    private MainView mCallback;

    private ImageView wImg_Header_Img;
    private TextView wTxt_Ch_Name;
    private TextView wTxt_En_Name;
    private TextView wTxt_Feature;
    private TextView wTxt_Habitat;
    private TextView wTxt_Diet;
    private TextView wTxt_Update_Time;

    public static AnimalInfoFragment newInstance(HouseListAnimalItem item) {
        AnimalInfoFragment fragment = new AnimalInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GET_ANIMAL_ITEM_KEY, item);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (MainView) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_animal_info, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setViewValue();
    }

    private void initView() {
        wImg_Header_Img = mView.findViewById(R.id.animal_info_img_header_img);
        wTxt_En_Name = mView.findViewById(R.id.animal_info_txt_en_name);
        wTxt_Ch_Name = mView.findViewById(R.id.animal_info_txt_ch_name);
        wTxt_Feature = mView.findViewById(R.id.animal_info_txt_feature);
        wTxt_Habitat = mView.findViewById(R.id.animal_info_txt_habitat);
        wTxt_Diet = mView.findViewById(R.id.animal_info_txt_diet);
        wTxt_Update_Time = mView.findViewById(R.id.animal_info_txt_update_time);
    }

    private void setViewValue() {
        if (getArguments() != null) {
            HouseListAnimalItem mAnimalItem = (HouseListAnimalItem) getArguments().getSerializable(GET_ANIMAL_ITEM_KEY);

            assert mAnimalItem != null;
            Glide.with(getActivity())
                    .load(mAnimalItem.getA_Pic01_URL().equals("") ? mAnimalItem.getA_Pic02_URL() : mAnimalItem.getA_Pic01_URL())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            if (getActivity() != null)
                                wImg_Header_Img.setBackground(getActivity().getDrawable(android.R.drawable.ic_notification_clear_all));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(wImg_Header_Img);
            wTxt_En_Name.setText(mAnimalItem.getA_Name_En().equals("") ? getString(R.string.no_data_show) : mAnimalItem.getA_Name_En());
            wTxt_Ch_Name.setText(mAnimalItem.getA_Name_Ch().equals("") ? getString(R.string.no_data_show) : mAnimalItem.getA_Name_Ch());
            wTxt_Feature.setText(mAnimalItem.getA_Feature().equals("") ? getString(R.string.no_data_show) : mAnimalItem.getA_Feature());
            wTxt_Habitat.setText(mAnimalItem.getA_Habitat().equals("") ? getString(R.string.no_data_show) : mAnimalItem.getA_Habitat());
            wTxt_Diet.setText(mAnimalItem.getA_Diet().equals("") ? getString(R.string.no_data_show) : mAnimalItem.getA_Diet());
            wTxt_Update_Time.setText(mAnimalItem.getA_Update().equals("") ? getString(R.string.no_data_show) : mAnimalItem.getA_Update());
        } else {
            mCallback.onCallToast("Item Error!!", true);
            mCallback.onBackFragment();
        }
    }

}
