package bedsole.katrina.androidcodingchallenge;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bedsole.katrina.androidcodingchallenge.databinding.FragmentHarryPotterBinding;
import bedsole.katrina.androidcodingchallenge.databinding.ItemHpMerchBinding;

public class HarryPotterMerchFragment extends Fragment implements HarryPotterMerchOperation.HPMerchDataListener {

    private FragmentHarryPotterBinding layout;

    private HarryPotterMerchAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = DataBindingUtil.inflate(
                inflater, R.layout.fragment_harry_potter, container, false);

        // start operation to get JSON data from provided URL
        HarryPotterMerchOperation hpOperation = new HarryPotterMerchOperation(getActivity());
        hpOperation.setHpMerchDataListener(this);
        hpOperation.getHPData();

        // setup adapter for harry potter list data
        layout.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HarryPotterMerchAdapter();
        layout.recyclerView.setAdapter(adapter);

        return layout.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onHPDataLoaded(List<HPMerch> hpMerchList) {
        if (hpMerchList == null) {
            return;
        }

        adapter.setData(hpMerchList);
    }


    /**
     * LIST ADAPTER
     */
    class HarryPotterMerchAdapter extends RecyclerView.Adapter<HarryPotterMerchAdapter.HarryPotterMerchViewHolder> {

        private List<HPMerch> hpMerchList;

        public HarryPotterMerchAdapter() {
        }

        public void setData(List<HPMerch> hpMerchData) {
            if (hpMerchData != null) {
                hpMerchList = hpMerchData;
                notifyDataSetChanged();
            }
        }

        @Override
        public HarryPotterMerchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemHpMerchBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_hp_merch, parent, false);
            return new HarryPotterMerchViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(HarryPotterMerchViewHolder holder, int position) {
            HPMerch hpMerchObj = hpMerchList.get(position);

            if (hpMerchObj == null) {
                return;
            }

            holder.titleTextView.setText(hpMerchObj.title);

            if (hpMerchObj.author != null) {
                holder.authorTextView.setText(hpMerchObj.author);
            } else {
                holder.authorTextView.setVisibility(View.GONE);
            }

            if (hpMerchObj.imageURL != null && !hpMerchObj.imageURL.isEmpty()) {
                Picasso.get()
                        .load(hpMerchObj.imageURL)
                        .placeholder(R.drawable.hp_default)
                        .into(holder.thumbnailImageView);
            }

        }

        @Override
        public int getItemCount() {
            if (hpMerchList == null) {
                return 0;
            }
            return hpMerchList.size();
        }


        /**
         * ITEM VIEW HOLDER
         */
        class HarryPotterMerchViewHolder extends RecyclerView.ViewHolder {

            TextView titleTextView;
            TextView authorTextView;
            ImageView thumbnailImageView;

            public HarryPotterMerchViewHolder(ItemHpMerchBinding binding) {
                super(binding.getRoot());
                titleTextView = binding.titleTextView;
                authorTextView = binding.authorTextView;
                thumbnailImageView = binding.thumbnailImageView;
            }
        }
    }

}
