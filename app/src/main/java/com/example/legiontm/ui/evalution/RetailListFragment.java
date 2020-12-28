 package com.example.legiontm.ui.evalution;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.legiontm.R;
import com.example.legiontm.databinding.FragmentRetailListBinding;
import com.example.legiontm.model.Retailer;
import com.example.legiontm.model.User;


import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

 public class RetailListFragment extends Fragment {
     static Bitmap bitmap;
     SweetAlertDialog pDialog;
     SharedPreferences sharedPreferences;
     SweetAlertDialog sweetAlertDialog;
     RecyclerView recyclerView;
     DataAdapter mAdapter;
     private final ArrayList<Retailer> dataList = new ArrayList<Retailer>();
     private final ArrayList<Retailer> mainRetailList = new ArrayList<Retailer>();
     User user;
     FragmentRetailListBinding binding;
     public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
         binding = FragmentRetailListBinding.inflate(getLayoutInflater(),container,false);
         return binding.getRoot();
     }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);
         initialize();
     }

     private void initialize() {

         user = User.getInstance();
         if(user.getUserId()==null)
         {
             user.setValuesFromSharedPreference(requireActivity().getSharedPreferences("user",MODE_PRIVATE));
         }

         recyclerView = binding.retailListRecycler;
         mAdapter = new DataAdapter(dataList);
         recyclerView.setItemViewCacheSize(20);
         recyclerView.setDrawingCacheEnabled(true);
         recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
         recyclerView.setHasFixedSize(true);
         RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
         recyclerView.setLayoutManager(mLayoutManager);
         recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
         recyclerView.setItemAnimator(new DefaultItemAnimator());
         recyclerView.setAdapter(mAdapter);

         getRetailList();


         binding.searchRetail.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 getNewSearchedList(charSequence);
             }

             @Override
             public void afterTextChanged(Editable editable) {

             }
         });


     }

     private void getNewSearchedList(CharSequence s) {
         ArrayList<Retailer> newList = new ArrayList<>();
         if(s.equals(""))
         {
             dataList.clear();
             dataList.addAll(mainRetailList);
         }
         else
         {
             for(int i = 0;i < mainRetailList.size(); i++)
             {
                 if(mainRetailList.get(i).getRetailerName().toLowerCase().contains(s.toString().toLowerCase())
                         || mainRetailList.get(i).getRetailerDmsCode().toLowerCase().contains(s.toString().toLowerCase())
                         || mainRetailList.get(i).getRetailerAddress().toLowerCase().contains(s.toString().toLowerCase()))
                 {
                     newList.add(mainRetailList.get(i));
                 }
             }
         }

         dataList.clear();
         dataList.addAll(newList);
         mAdapter.notifyDataSetChanged();
     }

     private void getRetailList() {


         dataList.clear();
         /*
         for (int i = 1; i<=50;i++)
         {
             dataList.add(new Retailer("S Alam Telecom "+i, "DHk101322"+i,"43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                     "80.23","89.33","1"));
         }

          */
         dataList.add(new Retailer("S Alam Telecom ", "DHk101322","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("Maa Telecom ", "CTG342523","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("Rocket Shop ", "DHk101322","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("Akash Flexi", "DHk2314982","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("S Alam Telecom ", "DHk098081","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("S Alam Telecom ", "DHk101322","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("S Alam Telecom ", "DHk101322","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("S Alam Telecom ", "DHk101322","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("S Alam Telecom ", "DHk101322","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("S Alam Telecom ", "DHk101322","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("S Alam Telecom ", "DHk101322","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         dataList.add(new Retailer("S Alam Telecom ", "DHk101322","43A, 14Rd, Nikunjo 2, Khilkhet, Dhaka",
                 "80.23","89.33","1"));
         mainRetailList.addAll(dataList);
         mAdapter.notifyDataSetChanged();


         /*
         sweetAlertDialog = new SweetAlertDialog(requireContext(), 5);
         sweetAlertDialog.setTitleText("Loading");
         sweetAlertDialog.show();
         MySingleton.getInstance(requireContext()).addToRequestQue(new StringRequest(1, "https://fresh.atmdbd.com/api/android/get_brand_list.php", new Response.Listener<String>() {
             public void onResponse(String response) {
                 try {
                     sweetAlertDialog.dismiss();
                     Log.e("response", response);
                     jsonObject = new JSONObject(response);
                     String code = jsonObject.getString("success");
                     if (code.equals("true")) {
                         JSONArray jsonArray = jsonObject.getJSONArray("brandList");
                         for (int i = 0; i<jsonArray.length();i++)
                         {
                             priorBrandList.add(jsonArray.getJSONObject(i).getString("name"));
                             brandMap.put(i,jsonArray.getJSONObject(i).getString("id"));
                         }
                         priorBrandAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, priorBrandList);
                         binding.priorBrandSpinner.setAdapter(priorBrandAdapter);
                     }
                     else
                         CustomUtility.showError(requireContext(), "No data found", "Failed");
                 } catch (JSONException e) {
                     CustomUtility.showError(requireContext(), e.getMessage(), "Getting Response");
                 }
             }
         }, new Response.ErrorListener() {
             public void onErrorResponse(VolleyError error) {
                 sweetAlertDialog.dismiss();
                 final SweetAlertDialog s = new SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE);
                 s.setConfirmText("Ok");
                 s.setTitleText("Network Error, try again!");
                 s.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                     public void onClick(SweetAlertDialog sweetAlertDialog) {
                         s.dismissWithAnimation();
                         startActivity(requireActivity().getIntent());
                         requireActivity().finish();
                     }
                 });
                 s.show();
             }
         }) {
             public Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> params = new HashMap<>();
                 params.put("UserId", user.getUserId());
                 return params;
             }
         });

          */
     }

     @Override
     public void onDetach() {
         super.onDetach();
         //requireContext().stopService(intent);
     }
     // data adapter class for showing the list
     public static class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

         private final List<Retailer> dataList;

         public DataAdapter(List<Retailer> dataList) {
             this.dataList = dataList;
         }

         @NonNull
         @Override
         public DataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             View itemView = LayoutInflater.from(parent.getContext())
                     .inflate(R.layout.retailer_row_layout, parent, false);
             return new MyViewHolder(itemView);
         }

         @Override
         public void onBindViewHolder(@NonNull DataAdapter.MyViewHolder holder, int position) {
             final Retailer data = dataList.get(position);
             holder.retailerName.setText(data.getRetailerName());
             holder.retailerDmsCode.setText(data.getRetailerDmsCode());
             holder.retailerVisitCount.setText(data.getRetailerVisitCount());
             holder.retailerAddress.setText(data.getRetailerAddress());
         }

         @Override
         public int getItemCount() {
             return dataList.size();
         }

         public static class MyViewHolder extends RecyclerView.ViewHolder {
             TextView retailerName, retailerDmsCode, retailerAddress, retailerVisitCount;
             public MyViewHolder(View convertView) {
                 super(convertView);
                 retailerName = convertView.findViewById(R.id.retail_name);
                 retailerDmsCode = convertView.findViewById(R.id.dms_code);
                 retailerAddress = convertView.findViewById(R.id.retailer_address);
                 retailerVisitCount = convertView.findViewById(R.id.retailer_visit_count);
             }
         }
     }


 }