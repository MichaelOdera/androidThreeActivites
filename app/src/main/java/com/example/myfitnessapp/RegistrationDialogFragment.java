package com.example.myfitnessapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class RegistrationDialogFragment extends DialogFragment {

    private EditText mNameOfUser;



    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {




        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Would You like to continue still?");


        builder.setPositiveButton("CONTINUE NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setTitle(null);
                builder.setMessage(null);
                builder.setPositiveButton(null, null);

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

                final View nameFormLayout = getLayoutInflater().inflate(R.layout.registration, null);
                Button submitNameButton = (Button) nameFormLayout.findViewById(R.id.submitNameButton);

                submitNameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNameOfUser = (EditText) nameFormLayout.findViewById(R.id.nameEditText);
                        String gottenUserName = mNameOfUser.getText().toString();
                        Log.d("Now this is", gottenUserName);
                        Intent intent = new Intent(v.getContext(), DashboardActivity.class);
                        intent.putExtra("userName",gottenUserName);
                        v.getContext().startActivity(intent);

                    }

                });

                builder.setView(nameFormLayout);
                builder.show();
            }
        });


        builder.setNegativeButton("COME BACK LATER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }
}
