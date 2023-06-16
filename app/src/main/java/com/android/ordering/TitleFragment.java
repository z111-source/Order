package com.android.ordering;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class TitleFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.title_food, container, false);
        TextView textView = view.findViewById(R.id.text_phone);
        ImageView imageView = view.findViewById(R.id.image_dian);
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(30));//图片圆角为20
        Glide.with(this).load(R.drawable.dian) //图片地址
                .apply(options)
                .into(imageView);
        textView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:188226666"));
        startActivity(intent);
    }
}
