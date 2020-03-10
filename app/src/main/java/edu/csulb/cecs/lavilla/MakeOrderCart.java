package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.adapters.CartAdapter;


public class MakeOrderCart extends Fragment {

    ListView cartListView;
    MakeOrderViewModel mViewModel;
    CartAdapter cartAdapter;

    public MakeOrderCart() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_order_cart, container, false);
        mViewModel = new ViewModelProvider(getActivity()).get(MakeOrderViewModel.class);
        cartListView = (ListView) view.findViewById(R.id.cart_listview);
        cartAdapter = new CartAdapter(getContext(), R.layout.cart_adapter_view_layout, mViewModel.getOrder().getPickedItems());
        cartListView.setAdapter(cartAdapter);
        TextView tvOrderTotal = view.findViewById(R.id.order_total);
        tvOrderTotal.setText("$ "+Float.toString(mViewModel.getOrder().getTotal()));
        return view;
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
