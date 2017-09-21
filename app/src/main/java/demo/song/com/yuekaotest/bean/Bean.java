package demo.song.com.yuekaotest.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * data:2017/9/20 0020.
 * Created by ：宋海防  song on
 */

public class Bean {

    public long code;
    public String msg;
    @SerializedName("[]")
    public List<zqfBean> zqf;

    public static class zqfBean {

        public MomentBean Moment;
        public UserBean User;
        @SerializedName("Comment[]")
        public List<CommentzqfBean> Commentzqf;

        public static class MomentBean {
            public long id;
            public long userId;
            public String date;
            public String content;
            public List<Long> praiseUserIdList;
            public List<String> pictureList;
        }

        public static class UserBean {
            public long id;
            public String name;
            public String head;
        }

        public static class CommentzqfBean {

            public long id;
            public long toId;
            public long userId;
            public long momentId;
            public String date;
            public String content;

            public static CommentzqfBean objectFromData(String str) {
                return new Gson().fromJson(str, CommentzqfBean.class);
            }
        }

        public static class Comment {
            public long id;
            public long toId;
            public long userId;
            public long momentId;
            public String date;
            public String content;
        }
    }
}
