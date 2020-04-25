package edu.csulb.cecs.lavilla;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

import edu.csulb.cecs.lavilla.ui.makeorder.Data.Item;
import edu.csulb.cecs.lavilla.ui.makeorder.Data.RestaurantOrder;
import edu.csulb.cecs.lavilla.ui.makeorder.MakeOrderViewModel;
import edu.csulb.cecs.lavilla.ui.makeorder.adapters.CartAdapter;


public class MakeOrderCart extends Fragment {

    private MakeOrderViewModel mViewModel;


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
        ListView cartListView = (ListView) view.findViewById(R.id.cart_listview);
        CartAdapter cartAdapter = new CartAdapter(getContext(), R.layout.cart_adapter_view_layout, mViewModel.getOrder().getPickedItems());
        cartListView.setAdapter(cartAdapter);
        TextView tvOrderTotal = view.findViewById(R.id.order_subTotal);
        tvOrderTotal.setText("$" + String.format("%.2f", mViewModel.getOrder().getTotal()));
        Button checkout_button = view.findViewById(R.id.checkout_button);
        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo check whether user has payment method and implement logic
                //mViewModel.postOrder();

                int total = (int) (mViewModel.getOrder().getTotal() * 100);
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                HashMap<String, Integer> items = new HashMap<>();
                for (Item i : mViewModel.getOrder().getPickedItems()){
                    items.put(Integer.toString(i.getItemId()), i.getQuantity());
                }
                RestaurantOrder orderDetail = new RestaurantOrder(userId, items, mViewModel.getLocationSelected().getLocationId(), mViewModel.getOrderType().name(), total);
                intent.putExtra("orderDetail", orderDetail);
                startActivity(intent);
            }
        });
        Button cancel_checkout_button = view.findViewById(R.id.cancel_checkout_button);
        cancel_checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo check whether user has payment method and implement logic
                //mViewModel.postOrder();
                Intent intent = new Intent(getActivity(), UserHome.class);
                startActivity(intent);
            }
        });
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
