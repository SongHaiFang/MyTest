package demo.song.com.yuekaotest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import demo.song.com.yuekaotest.EndLessOnScrollListener;
import demo.song.com.yuekaotest.R;
import demo.song.com.yuekaotest.adapter.HomeAdapter;
import demo.song.com.yuekaotest.bean.Bean;
import okhttp3.Call;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * data:2017/9/20 0020.
 * Created by ：宋海防  song on
 */

public class OneFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private Bean bean;
    private HomeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_one, container, false);
        initView();
        OkHttpUtils.get()
                .url("http://139.196.140.118:8080/get/%7B%22%5B%5D%22:%7B%22page%22:0,%22count%22:10,%22Moment%22:%7B%22content$%22:%22%2525a%2525%22%7D,%22User%22:%7B%22id@%22:%22%252FMoment%252FuserId%22,%22@column%22:%22id,name,head%22%7D,%22Comment%5B%5D%22:%7B%22count%22:2,%22Comment%22:%7B%22momentId@%22:%22%5B%5D%252FMoment%252Fid%22%7D%7D%7D%7D")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(),"cuole",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        String string = response.toString();
//                        Toast.makeText(getActivity(),string,Toast.LENGTH_SHORT).show();
                        bean = new Gson().fromJson(string, Bean.class);
                        adapter = new HomeAdapter(bean,getActivity());

                        recyclerView.setAdapter(adapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
                            @Override
                            public void onLoadMore(int currentPage) {
                                loadMoreData();
                            }
                        });
                    }
                });
        return view;
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recy);

    }
    //每次上拉加载的时候，给RecyclerView的后面添加了10条数据数据
    private void loadMoreData() {
        for (int i =0; i < 10; i++){
            bean.zqf.add(bean.zqf.get(i));
            adapter.notifyDataSetChanged();
        }
    }
}
