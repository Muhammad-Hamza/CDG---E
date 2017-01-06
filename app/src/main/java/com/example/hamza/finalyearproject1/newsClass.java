package com.example.hamza.finalyearproject1;

/**
 * Created by hamza on 1/6/2017.
 */

public class newsClass {



    private String mHeadline ;
    private String mDate ;
    private static final int NO_RESOURCE_ID = -1;
    private int  mImageId=NO_RESOURCE_ID;



    public newsClass(String headline,String date ,int imageID)
    {
        mHeadline = headline;
        mDate = date;
        mImageId = imageID;

    }


    public String getHeadline()
    {
        return mHeadline;
    }

    public String getDate()
    {
        return mDate;

    }
    public int getImageId(){
        if (mImageId != NO_RESOURCE_ID){
        return mImageId;}
        else{
            return R.drawable.noimage;
        }
    }
    public boolean hasImage()
    {

        return mImageId != NO_RESOURCE_ID ;
    }
}
