package com.android.ordering;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.android.ordering.Adapter.LeftAdapter;

import com.android.ordering.Bean.LeftBean;

import java.util.ArrayList;
import java.util.List;

public class LeftFragment extends Fragment {

    private List<LeftBean> chooseList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ListView listView = view.findViewById(R.id.listView_left);
        chooseList = new ArrayList<>();
        chooseList.add(new LeftBean(R.drawable.ic_baseline_thumb_up_alt_24, "推荐"));
        chooseList.add(new LeftBean(R.drawable.ic_baseline_local_fire_department_24, "热销"));
        LeftAdapter adapter = new LeftAdapter(getContext(), R.layout.listview_item, chooseList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view2, position, id) -> {
            for (LeftBean bean:chooseList){
                bean.setSelect(false);
            }
            chooseList.get(position).setSelect(true);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}