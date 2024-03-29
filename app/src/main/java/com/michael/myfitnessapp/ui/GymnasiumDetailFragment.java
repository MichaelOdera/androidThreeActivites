package com.michael.myfitnessapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.myfitnessapp.R;
import com.michael.myfitnessapp.models.Business;
import com.michael.myfitnessapp.models.Category;
import com.michael.myfitnessapp.models.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("NonConstantResourceId")
public class GymnasiumDetailFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.gymnasiumNameTextView) TextView gymnasiumName;
    @BindView(R.id.gymnasiumImageView) ImageView gymnasiumImage;
    @BindView(R.id.ratingTextView) TextView gymnasiumRating;
    @BindView(R.id.categoryTextView) TextView gymnasiumService;
    @BindView(R.id.phoneTextView) TextView gymnasiumPhoneNumber;
    @BindView(R.id.addressTextView) TextView gymnasiumAddress;
    @BindView(R.id.saveGymnasiumButton) Button saveGymnasiumButton;
    @BindView(R.id.websiteTextView) TextView gymnasiumWebSite;

    private Business mGymnasium;
    String mSaved;



    public GymnasiumDetailFragment() {
        // Required empty public constructor
    }

    public static GymnasiumDetailFragment newInstance(Business gym, String saved){
        GymnasiumDetailFragment gymnasiumDetailFragment = new GymnasiumDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable("gym", Parcels.wrap(gym));
        arguments.putString("saved", saved);
        gymnasiumDetailFragment.setArguments(arguments);
        return gymnasiumDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        mGymnasium = Parcels.unwrap(getArguments().getParcelable("gym"));
        mSaved = getArguments().getString("saved");

    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gymnasium_detail, container, false);
        ButterKnife.bind(this, view);
        if(!mGymnasium.getImageUrl().isEmpty()){
            Picasso.get().load(mGymnasium.getImageUrl()).into(gymnasiumImage);
        }
        else{
            gymnasiumImage.setImageResource(R.drawable.bulls);
        }

        if(mSaved !=null){
            if(mSaved.equals("saved")){
                saveGymnasiumButton.setVisibility(View.GONE);
            }

        }



        List<String> categories = new ArrayList<>();

        for(Category category: mGymnasium.getCategories()){
            categories.add(category.getTitle());
        }


        gymnasiumName.setText(mGymnasium.getName());
        gymnasiumService.setText(android.text.TextUtils.join(",",categories));
        gymnasiumPhoneNumber.setText(mGymnasium.getPhone());
        gymnasiumRating.setText(Double.toString(mGymnasium.getRating())+"/5");
        gymnasiumAddress.setText(mGymnasium.getLocation().toString());


        gymnasiumPhoneNumber.setOnClickListener(this);
        gymnasiumAddress.setOnClickListener(this);
        gymnasiumWebSite.setOnClickListener(this);
        gymnasiumImage.setOnClickListener(this);
        saveGymnasiumButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == gymnasiumImage){
            Toast.makeText(getContext(), "Gymnasium's Photo", Toast.LENGTH_SHORT).show();
        }

        if(v == gymnasiumWebSite){
            Intent webSiteIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mGymnasium.getUrl()));
            startActivity(webSiteIntent);
        }
        if(v == gymnasiumAddress){
            Intent addressIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mGymnasium.getCoordinates().getLatitude()
                            + "," + mGymnasium.getCoordinates().getLongitude()
                            + "?q=(" + mGymnasium.getName() + ")"));
            startActivity(addressIntent);
        }
        if(v == gymnasiumPhoneNumber){
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mGymnasium.getPhone()));
            startActivity(phoneIntent);
        }

        if(v == saveGymnasiumButton){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            assert user != null;
            String userID = user.getUid();

            DatabaseReference gymnasiumRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_GYMNASIUMS)
                    .child(userID);


            gymnasiumRef.push().setValue(mGymnasium);
            Toast.makeText(getContext(), "Saved Gymnasium", Toast.LENGTH_SHORT).show();
        }

    }
}
