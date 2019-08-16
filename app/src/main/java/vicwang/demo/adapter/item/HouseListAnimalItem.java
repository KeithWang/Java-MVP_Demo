package vicwang.demo.adapter.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HouseListAnimalItem implements Serializable {

    @SerializedName("\uFEFFA_Name_Ch")
    private String A_Name_Ch;//中文名稱
    private String A_Name_En;//英文名稱
    private String A_AlsoKnown;//別名
    private String A_Interpretation;//簡介
    private String A_Behavior;//行為
    private String A_Diet;//飲食
    private String A_Feature;//特徵
    private String A_Habitat;//棲息地
    private String A_Update;//最後更新時間
    private String A_Pic01_URL;//相片圖示 1
    private String A_Pic02_URL;//相片圖示 2

    public HouseListAnimalItem(String a_Name_Ch, String a_Name_En, String a_AlsoKnown, String a_Interpretation, String a_Behavior, String a_Diet, String a_Feature, String a_Habitat, String a_Update, String a_Pic01_URL, String a_Pic02_URL) {
        A_Name_Ch = a_Name_Ch;
        A_Name_En = a_Name_En;
        A_AlsoKnown = a_AlsoKnown;
        A_Interpretation = a_Interpretation;
        A_Behavior = a_Behavior;
        A_Diet = a_Diet;
        A_Feature = a_Feature;
        A_Habitat = a_Habitat;
        A_Update = a_Update;
        A_Pic01_URL = a_Pic01_URL;
        A_Pic02_URL = a_Pic02_URL;
    }

    public String getA_Name_Ch() {
        return A_Name_Ch;
    }

    public void setA_Name_Ch(String a_Name_Ch) {
        A_Name_Ch = a_Name_Ch;
    }

    public String getA_Name_En() {
        return A_Name_En;
    }

    public void setA_Name_En(String a_Name_En) {
        A_Name_En = a_Name_En;
    }

    public String getA_AlsoKnown() {
        return A_AlsoKnown;
    }

    public void setA_AlsoKnown(String a_AlsoKnown) {
        A_AlsoKnown = a_AlsoKnown;
    }

    public String getA_Interpretation() {
        return A_Interpretation;
    }

    public void setA_Interpretation(String a_Interpretation) {
        A_Interpretation = a_Interpretation;
    }

    public String getA_Behavior() {
        return A_Behavior;
    }

    public void setA_Behavior(String a_Behavior) {
        A_Behavior = a_Behavior;
    }

    public String getA_Diet() {
        return A_Diet;
    }

    public void setA_Diet(String a_Diet) {
        A_Diet = a_Diet;
    }

    public String getA_Feature() {
        return A_Feature;
    }

    public void setA_Feature(String a_Feature) {
        A_Feature = a_Feature;
    }

    public String getA_Habitat() {
        return A_Habitat;
    }

    public void setA_Habitat(String a_Habitat) {
        A_Habitat = a_Habitat;
    }

    public String getA_Update() {
        return A_Update;
    }

    public void setA_Update(String a_Update) {
        A_Update = a_Update;
    }

    public String getA_Pic01_URL() {
        return A_Pic01_URL;
    }

    public void setA_Pic01_URL(String a_Pic01_URL) {
        A_Pic01_URL = a_Pic01_URL;
    }

    public String getA_Pic02_URL() {
        return A_Pic02_URL;
    }

    public void setA_Pic02_URL(String a_Pic02_URL) {
        A_Pic02_URL = a_Pic02_URL;
    }
}
