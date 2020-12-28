package com.example.legiontm.ui.logout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.legiontm.R;
import com.example.legiontm.model.User;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

public class LogoutFragment extends Fragment {

    User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = User.getInstance();
        final SweetAlertDialog log = new SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE);
        log.setTitleText("Are you sure to Sign Out?");
        log.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("user",MODE_PRIVATE).edit();
                //editor.clear();
                //editor.apply();
                user.clear(editor);
                log.dismissWithAnimation();
                getActivity().finish();
            }
        });
        log.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                log.dismissWithAnimation();
                //FragmentManager fm = getParentFragmentManager();
                //fm.popBackStack();
                requireActivity().onBackPressed();
            }
        });
        log.setCancelText("No");
        log.setConfirmText("Ok");
        log.show();
    }
}