package com.mobile.bitgo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class send_sheet extends BottomSheetDialogFragment
        implements View.OnClickListener {

    public static final String TAG = "ActionBottomDialog";

    private ItemClickListener mListener;
    ImageView mayb;

    public static send_sheet newInstance() {
        return new send_sheet();
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.send_sheet, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.done).setOnClickListener(this);

        mayb = view.findViewById(R.id.imageView37);
        mayb.setBackgroundColor(getResources().getColor(android.R.color.transparent));

    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(),R.layout.send_sheet, null);
        dialog.setContentView(contentView);
      //  RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        ((View) contentView.getParent()).setBackgroundResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    private  void changeFragment() {
        Fragment fragment = new Home();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override public void onClick(View view) {
        Button done = (Button) view;
        startActivity(new Intent(getContext(), Profile.class));
        //mListener.onItemClick(tvSelected.getText().toString());
      //  changeFragment();
        dismiss();
    }

    public interface ItemClickListener {
        void onItemClick(String item);
    }
}
