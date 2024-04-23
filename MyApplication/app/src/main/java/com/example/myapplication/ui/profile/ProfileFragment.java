package com.example.myapplication.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.example.myapplication.model.User;
import com.example.myapplication.repository.UserRepository;
import com.example.myapplication.ui.home.HomeViewModel;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private EditText editTextUserName;
    private EditText editTextContactNumber;

    private TextView email;
    private boolean isUserNameChanged = false;
    private boolean isContactNumberChanged = false;




    @SuppressLint("WrongViewCast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

//        profileViewModel.getUserName().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String userName) {
////                if(userName == null)
//
//            }
//
//
//        });
        editTextUserName = root.findViewById(R.id.edit_text_name);
        editTextContactNumber = root.findViewById(R.id.edit_text_number);
        email=root.findViewById(R.id.email_address);

        email.setText(UserRepository.getInstance().getUserEmail());


        String userName = UserRepository.getInstance().getUser().getUserName();
        String contactNumber = UserRepository.getInstance().getUser().getContactNumber();

        if (!Objects.equals(userName, "") || !userName.isEmpty()) {
            editTextUserName.setText(userName);
        }

        if (!Objects.equals(contactNumber, "") || !contactNumber.isEmpty()) {
            editTextContactNumber.setText(contactNumber);
        }



        editTextUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                isUserNameChanged = true;
            }


        });

        editTextUserName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && isUserNameChanged) {
                profileViewModel.updateUserNameToRepository(editTextUserName.getText().toString());

                isUserNameChanged = false;
            }
        });


        editTextContactNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                isContactNumberChanged = true;
            }


        });

        editTextContactNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && isContactNumberChanged) {
                profileViewModel.updateContactNumberToRepository(editTextContactNumber.getText().toString());

                isContactNumberChanged = false;
            }
        });




//        final TextView textView = binding.textProfile;
//        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        editTextUserName = null;
        editTextContactNumber = null;
        email = null;


    }
}