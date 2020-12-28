package com.example.legiontm.ui.attendance;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.legiontm.MainActivity;
import com.example.legiontm.R;
import com.example.legiontmsup.databinding.FragmentAttendanceBinding;
import com.example.legiontm.model.User;
import com.example.legiontm.utils.CustomUtility;
import com.example.legiontm.utils.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class AttendanceFragment extends Fragment {

    FragmentAttendanceBinding binding;
    String photoName = "", imageString = "";
    Boolean photoFlag = false;

    Uri photoURI;
    static final int REQUEST_IMAGE_CAPTURE = 6;
    String currentPhotoPath = "";
    static Bitmap bitmap;

    SharedPreferences sharedPreferences;

    boolean network = false;
    SweetAlertDialog sweetAlertDialog, pDialog;
    User user;
    ProgressDialog progressDialog;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String activeBtn = "", fromDate = "", toDate = "",place = "",leaveType = "",retailerCode = "",reason = "",deviceDate = "";


    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();

    String[] optionList = new String[] {"Distribution House", "Retail Point", "Head Office","In Transit","Others"};
    String[] retailerList = new String[] {"N/A"};
    String[] leaveTypeList = new String[] {"Casual Leave", "Half Day Leave", "Sick Leave", "On Training", "On Meeting"};
    List<String> DMSCode = new ArrayList<String>();
    String typeId = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAttendanceBinding.inflate(getLayoutInflater(),container,false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inititalize();
    }

    public void inititalize()
    {
        user = User.getInstance();
        if(user.getUserId()==null)
        {
            user.setValuesFromSharedPreference(requireActivity().getSharedPreferences("user",MODE_PRIVATE));
        }


        binding.inbtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                activeBtn = "in";
                binding.leavebtn.setBackgroundResource(R.drawable.inactive);
                binding.inbtn.setBackgroundResource(R.drawable.active);
                binding.outbtn.setBackgroundResource(R.drawable.inactive);
                binding.inOutLayout.setVisibility(View.VISIBLE);
                binding.leavelay.setVisibility(View.GONE);
                //optionSpinner.setAdapter(adapter);
                photoFlag = false;
                binding.takeSelfieText.setText("Take selfie");
                binding.takeSelfieText.getResources().getColor(R.color.text_color);
            }
        });

        binding.outbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeBtn = "out";
                binding.leavebtn.setBackgroundResource(R.drawable.inactive);
                binding.inbtn.setBackgroundResource(R.drawable.inactive);
                binding.outbtn.setBackgroundResource(R.drawable.active);
                binding.inOutLayout.setVisibility(View.VISIBLE);
                binding.leavelay.setVisibility(View.GONE);
                //optionSpinner.setAdapter(adapter);
                photoFlag = false;
                binding.takeSelfieText.setText("Take selfie");
                binding.takeSelfieText.getResources().getColor(R.color.text_color);
            }
        });
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, leaveTypeList);
        binding.leaveTypeSpinner.setAdapter(adapter2);

        binding.leavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeBtn = "leave";
                binding.leavebtn.setBackgroundResource(R.drawable.active);
                binding.inbtn.setBackgroundResource(R.drawable.inactive);
                binding.outbtn.setBackgroundResource(R.drawable.inactive);
                binding.inOutLayout.setVisibility(View.GONE);
                binding.leavelay.setVisibility(View.VISIBLE);
            }
        });


        binding.hideGpsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.gpsText.setText(MainActivity.presentAcc);
            }
        });
        binding.camera.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                String photoName = CustomUtility.getDeviceDate() + "image.jpeg";
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        final DatePickerDialog.OnDateSetListener fromdate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFromDate();
            }

        };

        binding.frombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(requireContext(), fromdate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        final DatePickerDialog.OnDateSetListener todate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if(myCalendar.compareTo(myCalendar2) == 1)
                {
                    binding.todate.setText("");
                    CustomUtility.showWarning(requireContext(),"Select correct date","Failed");
                }
                else{
                    updateToDate();
                }

            }

        };

        binding.tobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fromDate.equals(""))
                {
                    CustomUtility.showWarning(requireContext(),"Select from date first","Failed");
                }
                else
                {
                    new DatePickerDialog(requireContext(), todate, myCalendar2
                            .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                            myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
                }

            }
        });


        binding.leaveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leaveType = binding.leaveTypeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network = CustomUtility.haveNetworkConnection(requireContext());
                boolean flag = chekFeilds();
                if(flag)
                {
                    SweetAlertDialog confirm = new SweetAlertDialog(requireContext(),SweetAlertDialog.WARNING_TYPE);
                    confirm.setTitle("Are you sure?");
                    confirm.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            confirm.dismissWithAnimation();
                            upload();
                        }
                    });
                    confirm.setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            confirm.dismissWithAnimation();
                        }
                    });
                    confirm.show();
                }

            }
        });
    }
    //after finishing camera intent whether the picture was save or not
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            photoFlag = true;
            binding.takeSelfieText.setText(R.string.take_image_done);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageString = CustomUtility.imageToString(imageBitmap);
        }
    }

    private void updateFromDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Log.e("FromdDate",sdf.format(myCalendar.getTime()));
        fromDate = sdf.format(myCalendar.getTime());
        binding.fromdate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateToDate() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        toDate = sdf.format(myCalendar2.getTime());
        binding.todate.setText(sdf.format(myCalendar2.getTime()));
    }

    public String getDeviceDate()
    {
        String myFormat = "yyyy-MM-dd H:m:s"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date date = new Date();
        return  sdf.format(date);
    }
    private boolean chekFeilds()
    {
        if (!network)
        {
            CustomUtility.showWarning(requireContext(),"Please turn on internet connection","No inerternet connection!");
            return false;
        }
        if(activeBtn.equals(""))
        {
            CustomUtility.showWarning(requireContext(),"Select attendance type In, Out or Leave","Required feild!");
            return false;
        }
        else if(MainActivity.presentAcc.equals(""))
        {
            CustomUtility.showWarning(requireContext(),"Please wait for the gps","Required fields");
            return false;
        }
        else if((activeBtn.equals("in") | activeBtn.equals("out") ) & !photoFlag)
        {
            CustomUtility.showWarning(requireContext(),"Take selfie","Required feild!");
            return false;
        }
        else if(activeBtn.equals("leave") & (fromDate.equals("") | toDate.equals("")))
        {
            CustomUtility.showWarning(requireContext(),"Select form and to date","Required feild!");
            return false;
        }
        else if(activeBtn.equals("leave") & leaveType.equals(""))
        {
            CustomUtility.showWarning(requireContext(), "Select leave type","Required feild!");
            return false;
        }
        else if(activeBtn.equals("leave") & reason.equals(""))
        {
            CustomUtility.showWarning(requireContext(),"Write the reason","Required feild!");
            return false;
        }
        return true;
    }



    private void upload()
    {
        pDialog = new SweetAlertDialog(requireContext(),SweetAlertDialog.PROGRESS_TYPE);
        pDialog.show();

        String upLoadServerUri = "https://rocket.atmdbd.com/api/android/insert_attendance.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, upLoadServerUri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.e("response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            if(code.equals("true"))
                            {
                                code = "Successful";
                                new SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Successful")
                                        .setContentText("")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                startActivity(requireActivity().getIntent());
                                                requireActivity().finish();
                                            }
                                        })
                                        .show();
                            }
                            else
                            {
                                code = "Failed";
                                CustomUtility.showError(requireContext(),message,code);
                                //CustomUtility.showError(AttendanceActivity.this,"You allready submitted in",code);
                            }


                        } catch (JSONException e) {
                            CustomUtility.showError(requireContext(), e.getMessage(), "Failed");
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.e("response",error.toString());
                CustomUtility.showError(requireContext(), "Network slow, try again", "Failed");

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId",user.getUserId());
                params.put("AttendanceType",activeBtn);
                if(activeBtn.equals("leave"))
                {
                    params.put("LeaveType",leaveType);
                    params.put("LeaveDateStart",fromDate);
                    params.put("LeaveDateEnd",toDate);
                    params.put("Reason",binding.reason.getText().toString());
                }
                else
                {
                    params.put("DeviceDate",getDeviceDate());
                    params.put("Latitude", MainActivity.presentLat);
                    params.put("Longitude",MainActivity.presentLon);
                    params.put("Accuracy",MainActivity.presentAcc);
                    params.put("PictureData",imageString);
                    params.put("Remark",binding.lateRemark.getText().toString());
                }
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(requireContext()).addToRequestQue(stringRequest);
    }
}
