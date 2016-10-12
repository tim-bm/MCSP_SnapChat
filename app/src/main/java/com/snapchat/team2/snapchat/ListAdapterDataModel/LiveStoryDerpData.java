package com.snapchat.team2.snapchat.ListAdapterDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kun on 10/10/2016.
 */
public class LiveStoryDerpData {

    private static final String[] imageUrl = {"https://img3.doubanio.com/lpic/s2659208.jpg",
            "https://img3.doubanio.com/lpic/s2059791.jpg",
            "https://img3.doubanio.com/lpic/s3983958.jpg",
            "https://img3.doubanio.com/lpic/s2347562.jpg",
            "https://img3.doubanio.com/lpic/s6962247.jpg",};

    public static List<LiveStoryListItem> getListData() {
        List<LiveStoryListItem> data = new ArrayList<>();

        //Repeat process 4 times, so that we have enough data to demonstrate a scrollable
        //RecyclerView
        for (int x = 0; x < 1; x++) {
            //create ListItem with dummy data, then add them to our List
            for (int i = 0; i < imageUrl.length; i++) {
                LiveStoryListItem item = new LiveStoryListItem();
                item.setLiveStoryUrl(imageUrl[i]);
                data.add(item);
            }
        }
        return data;
    }
}

