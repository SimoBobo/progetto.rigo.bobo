package com.example.rigobobo;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_home_content, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        // Set padding for Tiles (not needed for Cards/Lists!)
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView color;
        public TextView name;
        public ImageView img;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_home_item_content, parent, false));

            name = (TextView) itemView.findViewById(R.id.tile_title);
            color = (ImageView) itemView.findViewById(R.id.tile_color);
            img = (ImageView) itemView.findViewById(R.id.tile_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class ContentAdapter
            extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 4;

        private final String[] mNames;
        private final int[] mColors;
        private final Drawable[] mImgs;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();

            mNames = resources.getStringArray(R.array.home_tiles_name);
            mColors = resources.getIntArray(R.array.home_tiles_color);
            TypedArray a = resources.obtainTypedArray(R.array.home_tiles_img);
            mImgs = new Drawable[a.length()];
            for (int i=0; i<mImgs.length; i++){
                mImgs[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(
                    LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.name.setText(mNames[position % mNames.length]);
            holder.color.setBackgroundColor(mColors[position % mColors.length]);
            holder.img.setImageDrawable(mImgs[position % mImgs.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

}
