package com.mobile.bitgo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet extends BottomSheetDialogFragment {
    private BottomsheetListener mListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        Button   button = v.findViewById(R.id.new_wallet);
       // Button button2 = v.findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Button");
                dismiss();
                //Toast.makeText(BottomSheet.this, "On Construction", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    public  interface BottomsheetListener{
        void onButtonClicked(String text);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mListener = (BottomsheetListener) context;
        }catch (ClassCastException e){
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }

    }
}
