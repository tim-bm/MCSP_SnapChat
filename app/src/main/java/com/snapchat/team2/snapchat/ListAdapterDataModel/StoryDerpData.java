package com.snapchat.team2.snapchat.ListAdapterDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kun on 10/3/2016.
 */
public class StoryDerpData {
    private static final String[] subTitles = {"Pop","Jazz","Rock","Blues","Rap","Rock"};
    private static final String[] titles = {
            "Bruce Lee : We Like Digging?",
            "Marcus Aurelius : Moves like Jagger",
            "Carl Sagan : One More Night",
            "Alhazen : Makes Me Wonder",
            "Jim Rohn : Songs About Jane Tour",
            "Maroom5 : Overexposed"

    };

    private static final String[] imageUrl = {"https://img3.doubanio.com/lpic/s28285121.jpg",
            "https://img3.doubanio.com/lpic/s27554635.jpg",
            "https://img3.doubanio.com/lpic/s28405858.jpg",
            "https://img3.doubanio.com/lpic/s2888347.jpg",
            "https://img3.doubanio.com/lpic/s27319088.jpg",
            "https://img3.doubanio.com/lpic/s27310337.jpg",};

    public static List<DiscoverStoryListItem> getListData() {
        List<DiscoverStoryListItem> data = new ArrayList<>();

        //Repeat process 4 times, so that we have enough data to demonstrate a scrollable
        //RecyclerView
        for (int x = 0; x < 1; x++) {
            //create ListItem with dummy data, then add them to our List
            for (int i = 0; i < imageUrl.length; i++) {
                DiscoverStoryListItem item = new DiscoverStoryListItem();
                item.setTitle(titles[i]);
                item.setText(subTitles[i]);
                item.setImage(imageUrl[i]);
                data.add(item);
            }
        }
        return data;
    }
}