package ness.edu.xmlandfirebase;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A fragment representing a list of Ynet Items.
 */
public class YnetArticleFragment extends Fragment implements YnetDataSource.OnYnetArrivedListener {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView)
                inflater.inflate(R.layout.fragment_ynetarticle, container, false);


        YnetDataSource.getYnet(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return recyclerView;
    }

    @Override
    public void onYnetArrived(final List<YnetDataSource.Ynet> data, final Exception e) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (e == null) {
                    recyclerView.setAdapter(new YnetAdapter(data, getActivity()));
                } else {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static class YnetAdapter extends RecyclerView.Adapter<YnetAdapter.YnetViewHolder> {

        private List<YnetDataSource.Ynet> data;
        private Context context;
        private LayoutInflater inflater;

        public YnetAdapter(List<YnetDataSource.Ynet> data, Context context) {
            this.data = data;
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public YnetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.ynet_item, parent, false);
            return new YnetViewHolder(v);
        }


        @Override
        public void onBindViewHolder(YnetViewHolder holder, int position) {
            YnetDataSource.Ynet ynet = data.get(position);
            holder.tvTitle.setText(ynet.getTitle());
            holder.tvContent.setText(ynet.getDescription());
            Picasso.with(context).load(ynet.getImage()).into(holder.ivThumbnail);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class YnetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView ivThumbnail;
            TextView tvTitle;
            TextView tvContent;

            public YnetViewHolder(View v) {
                super(v);
                ivThumbnail = (ImageView) v.findViewById(R.id.ivThumbnail);
                tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                tvContent = (TextView) v.findViewById(R.id.tvContent);

                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (context instanceof AppCompatActivity) {
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();

                    int position = getAdapterPosition();
                    YnetDataSource.Ynet ynet = data.get(position);
                    YnetDetailFragment detailFragment = YnetDetailFragment.newInstance(ynet.getLink());

                    fm.beginTransaction()
                            .replace(R.id.frame, detailFragment)
                            .addToBackStack("details")
                            .commit();

                }

            }
        }
    }
}


