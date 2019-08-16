package vicwang.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import vicwang.demo.R;
import vicwang.demo.adapter.item.HouseListAnimalItem;
import vicwang.demo.adapter.item.MainHouseListItem;
import vicwang.demo.util.ViewClick;

public class AnimalListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_TOP = 0;
    private final static int TYPE_PLANT = 1;

    private MainHouseListItem mTopAreaData;
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemClickListener mClickListener;
    private ArrayList<HouseListAnimalItem> mAnimalData;


    public AnimalListAdapter(Context context, MainHouseListItem data, ArrayList<HouseListAnimalItem> animalData) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mTopAreaData = data;
        this.mAnimalData = animalData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        if (type == TYPE_TOP) {
            View view = mInflater.inflate(R.layout.house_list_top_item, viewGroup, false);
            return new ViewHolderTop(view);
        } else {
            View view = mInflater.inflate(R.layout.house_list_plant_item, viewGroup, false);
            return new ViewHolderAnimal(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_TOP) {
            /*
             * Top Area
             * */
            Glide.with(mContext)
                    .load(mTopAreaData.getE_Pic_URL())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(((ViewHolderTop) viewHolder).wImg_Top_Pic);
            ((ViewHolderTop) viewHolder).wTxt_Top_Info.setText(mTopAreaData.getE_Info());
            ((ViewHolderTop) viewHolder).wTxt_Top_Rest.setText(mTopAreaData.getE_Memo().equals("") ? mContext.getString(R.string.main_house_no_rest_date) : mTopAreaData.getE_Memo());
            ((ViewHolderTop) viewHolder).wTxt_Top_Area.setText(mTopAreaData.getE_Category());

            String link = "<a href=\"" + mTopAreaData.getE_URL() + "\">" + mContext.getString(R.string.house_list_item_top_open_web) + "</a>";
            ((ViewHolderTop) viewHolder).wTxt_Top_Open_Web.setText(Html.fromHtml(link));
            ((ViewHolderTop) viewHolder).wTxt_Top_Open_Web.setMovementMethod(LinkMovementMethod.getInstance());

            ((ViewHolderTop) viewHolder).wTxt_Top_No_Plant.setVisibility(mAnimalData.size() == 0 ? View.VISIBLE : View.GONE);

        } else {

            /*
             * Animal data Area
             * */

            Glide.with(mContext)
                    .load(mAnimalData.get((position - 1)).getA_Pic01_URL().equals("") ? mAnimalData.get((position - 1)).getA_Pic02_URL() : mAnimalData.get((position - 1)).getA_Pic01_URL())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            ((ViewHolderAnimal) viewHolder).wProcessBar.setVisibility(View.GONE);
                            ((ViewHolderAnimal) viewHolder).wImg_Animal_Pic.setBackground(mContext.getDrawable(android.R.drawable.ic_notification_clear_all));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            ((ViewHolderAnimal) viewHolder).wProcessBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(((ViewHolderAnimal) viewHolder).wImg_Animal_Pic);

            ((ViewHolderAnimal) viewHolder).wTxt_Name.setText(mAnimalData.get((position - 1)).getA_Name_Ch());
            ((ViewHolderAnimal) viewHolder).wTxt_AlsoKnow.setText(mAnimalData.get((position - 1)).getA_Feature().equals("") ? mContext.getString(R.string.house_list_item_no_feature) : mAnimalData.get((position - 1)).getA_Feature());

        }


    }

    @Override
    public int getItemCount() {
        return (mAnimalData.size() + 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_TOP;
        else
            return TYPE_PLANT;
    }

    public class ViewHolderTop extends RecyclerView.ViewHolder {
        ImageView wImg_Top_Pic;
        TextView wTxt_Top_Info;
        TextView wTxt_Top_Rest;
        TextView wTxt_Top_Area;
        TextView wTxt_Top_Open_Web;
        TextView wTxt_Top_No_Plant;

        ViewHolderTop(@NonNull View itemView) {
            super(itemView);
            wImg_Top_Pic = itemView.findViewById(R.id.house_list_item_top_img_house);
            wTxt_Top_Info = itemView.findViewById(R.id.house_list_item_top_txt_house_info);
            wTxt_Top_Rest = itemView.findViewById(R.id.house_list_item_top_txt_house_rest_time);
            wTxt_Top_Area = itemView.findViewById(R.id.house_list_item_top_txt_house_area);
            wTxt_Top_Open_Web = itemView.findViewById(R.id.house_list_item_top_txt_house_open_web);
            wTxt_Top_No_Plant = itemView.findViewById(R.id.house_list_item_top_txt_no_plant_show);

        }

    }

    public class ViewHolderAnimal extends RecyclerView.ViewHolder {
        ImageView wImg_Animal_Pic;
        TextView wTxt_Name;
        TextView wTxt_AlsoKnow;
        ProgressBar wProcessBar;
        RelativeLayout wLay_Click_Area;

        ViewHolderAnimal(@NonNull View itemView) {
            super(itemView);
            wImg_Animal_Pic = itemView.findViewById(R.id.house_list_animal_item_img_pic);
            wTxt_Name = itemView.findViewById(R.id.house_list_plant_item_txt_name);
            wTxt_AlsoKnow = itemView.findViewById(R.id.house_list_plant_item_txt_alsoknow);
            wProcessBar = itemView.findViewById(R.id.house_list_animal_item_loading_processbar);
            wLay_Click_Area = itemView.findViewById(R.id.house_list_plant_item_lay_click_area);

            wLay_Click_Area.setOnClickListener(nCustomClickListener);
        }

        private ViewClick nCustomClickListener = new ViewClick() {

            @Override
            protected void CustomOnClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(view, getAdapterPosition());
                }
            }
        };

    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
