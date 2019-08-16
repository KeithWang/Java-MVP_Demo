package vicwang.demo.adapter.item;

import java.io.Serializable;

public class MainHouseListItem implements Serializable {
    private int _id;
    private String E_Pic_URL;
    private String E_Info;
    private String E_no;
    private String E_Category;
    private String E_Memo;
    private String E_Name;
    private String E_URL;

    public MainHouseListItem(int _id, String e_Pic_URL, String e_Info, String e_no, String e_Category, String e_Memo, String e_Name, String e_URL) {
        this._id = _id;
        E_Pic_URL = e_Pic_URL;
        E_Info = e_Info;
        E_no = e_no;
        E_Category = e_Category;
        E_Memo = e_Memo;
        E_Name = e_Name;
        E_URL = e_URL;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getE_Pic_URL() {
        return E_Pic_URL;
    }

    public void setE_Pic_URL(String e_Pic_URL) {
        E_Pic_URL = e_Pic_URL;
    }

    public String getE_Info() {
        return E_Info;
    }

    public void setE_Info(String e_Info) {
        E_Info = e_Info;
    }

    public String getE_no() {
        return E_no;
    }

    public void setE_no(String e_no) {
        E_no = e_no;
    }

    public String getE_Category() {
        return E_Category;
    }

    public void setE_Category(String e_Category) {
        E_Category = e_Category;
    }

    public String getE_Memo() {
        return E_Memo;
    }

    public void setE_Memo(String e_Memo) {
        E_Memo = e_Memo;
    }

    public String getE_Name() {
        return E_Name;
    }

    public void setE_Name(String e_Name) {
        E_Name = e_Name;
    }

    public String getE_URL() {
        return E_URL;
    }

    public void setE_URL(String e_URL) {
        E_URL = e_URL;
    }
}
