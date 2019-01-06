package bedsole.katrina.androidcodingchallenge;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bedsole.katrina.androidcodingchallenge.databinding.FragmentHarryPotterBinding;

public class HarryPotterMerchFragment extends Fragment implements HarryPotterMerchOperation.HPMerchDataListener {

    private FragmentHarryPotterBinding layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layout = DataBindingUtil.inflate(
                inflater, R.layout.fragment_harry_potter, container, false);

        // start operation to get JSON data from provided URL
        HarryPotterMerchOperation hpOperation = new HarryPotterMerchOperation(getActivity());
        hpOperation.setHpMerchDataListener(this);
        hpOperation.getHPData();

        // TODO: setup adapter for recycler view

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
        // TODO: set data for adapter here

        if (hpMerchList == null) {
            return;
        }

        for (HPMerch hpMerchObj : hpMerchList) {
            Log.d("TAG", hpMerchObj.toString());
        }
    }

}
