package com.android.ordering.Bean;

public class LeftBean {

    private String name;
    private int imageId;
    private Boolean isSelect ;

    public LeftBean( int imageId,String name) {
        this.name = name;
        this.imageId = imageId;
    }

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
