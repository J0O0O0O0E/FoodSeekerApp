package com.example.myapplication.ui.profile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentProfileBinding;
import com.example.myapplication.repository.UserRepository;
import com.example.myapplication.ui.subscribedFoodBanks.SubscribedFoodBanksActivity;

import java.util.Objects;

import com.bumptech.glide.Glide;


/**
 * ProfileFragment is responsible for displaying and managing the user's profile information,
 * including username, contact number, email, and profile image.
 *
 * @author Shuhui Yang, Zhi Li
 */

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private EditText editTextUserName;
    private EditText editTextContactNumber;

    private ImageView profileImg;


    private TextView email;
    private boolean isUserNameChanged = false;
    private boolean isContactNumberChanged = false;



    // Launcher to get content from the device storage (specifically an image)
    private final ActivityResultLauncher<String> getContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    UserRepository.getInstance().uploadImageToFirebase(uri, getContext());
                    displayImageWithGlide(uri.toString());  // Immediately display the chosen image
                }
            }
    );




    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @SuppressLint("WrongViewCast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        editTextUserName = root.findViewById(R.id.edit_text_name);
        editTextContactNumber = root.findViewById(R.id.edit_text_number);
        profileImg = root.findViewById(R.id.profile_photo);
        email=root.findViewById(R.id.email_address);

        email.setText(UserRepository.getInstance().getUserEmail());
        Glide.with(this).load(UserRepository.getInstance().getuserimg()).into(profileImg);



        String userName = UserRepository.getInstance().getUser().getUserName();
        String contactNumber = UserRepository.getInstance().getUser().getContactNumber();

        if (!Objects.equals(userName, "") || !userName.isEmpty()) {
            editTextUserName.setText(userName);
        }

        if (!Objects.equals(contactNumber, "") || !contactNumber.isEmpty()) {
            editTextContactNumber.setText(contactNumber);
        }




        // Add text change listener to detect username changes
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


        // Set up the button to select a new profile image
        Button subscribedFoodBanksButton = root.findViewById(R.id.button);

        subscribedFoodBanksButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SubscribedFoodBanksActivity.class);
            startActivity(intent);
        });


        Button selectImageButton = root.findViewById(R.id.button_select_image);
        selectImageButton.setOnClickListener(v -> getContent.launch("image/*"));


        return root;
    }


    /**
     * Display the selected image using Glide.
     *
     * @param imageUrl The URL of the image to be displayed.
     */
    private void displayImageWithGlide(String imageUrl) {
        Glide.with(this).load(imageUrl).into(profileImg);
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