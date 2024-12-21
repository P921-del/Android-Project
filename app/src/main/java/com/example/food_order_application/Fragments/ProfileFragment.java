package com.example.food_order_application.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_order_application.Domains.Customar;
import com.example.food_order_application.Helpers.Food_Order_Application_Database;
import com.example.food_order_application.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int CustomarID;
    private Food_Order_Application_Database db;
    private EditText editName, editGmail, editAddress, editPhone, editschool, editjob;
    private TextView namee, textphone, textgmail;
    private ImageView avatarImageView;
    private Button updateButton;
    private View editCustomarButton;
    private LinearLayout linearLayout3;
    private Uri imageUri = null; // if the project and allow the customar to upload his photo this value will be changed

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(int param1) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            CustomarID = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        editName = view.findViewById(R.id.EditNameus);
//        editGmail = findViewById(R.id.editGmailus);
        editAddress = view.findViewById(R.id.editAddressus);
        editjob = view.findViewById(R.id.editjob);
        editCustomarButton = view.findViewById(R.id.edit);
        editPhone = view.findViewById(R.id.editPhoneus);
        namee = view.findViewById(R.id.namee);
        updateButton = view.findViewById(R.id.updateinfor);
        avatarImageView = view.findViewById(R.id.Avatar);
        linearLayout3 = view.findViewById(R.id.linearLayout3);
        textphone = view.findViewById(R.id.textPhone);
        textgmail = view.findViewById(R.id.textgmail);
        // load Customar Data on the fragment
        loadUserData();
        //Handle Events on buttons
        editCustomarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLinearLayoutVisibility();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserData(); // update Customar Data on the fragment
            }
        });
        return view;
    }

    private void loadUserData() {
        db = new Food_Order_Application_Database(getActivity());
        Customar currentUser = db.getCustomarByID(CustomarID);
        if (currentUser != null) {
            if (currentUser.getName() != null) {
                namee.setText(currentUser.getName());
                editName.setText(currentUser.getName());
            }

            if (currentUser.getEmail() != null) {
                textgmail.setText(currentUser.getEmail());
                editjob.setText(currentUser.getEmail());
            }

            if (currentUser.getAddress() != null) {
                editAddress.setText(currentUser.getAddress());
            }
            if (String.valueOf(currentUser.getContactNumber()) != "") {
                editPhone.setText(String.valueOf(currentUser.getContactNumber()));
                textphone.setText(String.valueOf(currentUser.getContactNumber()));
            }
        }
        else
        {
            Toast.makeText(getActivity(), "User data not found.", Toast.LENGTH_SHORT).show();
        }

    }

    private void toggleLinearLayoutVisibility() {
        if (linearLayout3.getVisibility() == View.VISIBLE) {
            linearLayout3.setVisibility(View.GONE);
        } else {
            linearLayout3.setVisibility(View.VISIBLE);
        }
    }

    private void updateUserData() {
        db = new Food_Order_Application_Database(getActivity());
        Customar currentUser = db.getCustomarByID(CustomarID);
        if (currentUser != null) {
            String name = editName.getText().toString().trim();
            String email = editjob.getText().toString().trim();
            String address = editAddress.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
             Customar updatedCustomar = new Customar(CustomarID , name , address , Integer.valueOf(phone) , email , currentUser.getPassword());
            if (db.updateCustomar(updatedCustomar)) {
                Toast.makeText(getActivity(), "User data updated successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Failed to update user data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

