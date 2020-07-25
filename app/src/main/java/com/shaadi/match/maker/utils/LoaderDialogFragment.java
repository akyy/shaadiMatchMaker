package com.shaadi.match.maker.utils;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaadi.match.maker.R;
import com.shaadi.match.maker.databinding.FragmentLoaderBinding;

/**
 * Created by ajay
 */
public class LoaderDialogFragment extends DialogFragment {

    private FragmentLoaderBinding binding;

    public static LoaderDialogFragment newInstance() {
        LoaderDialogFragment fragment = new LoaderDialogFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.AppTheme);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoaderBinding.inflate(LayoutInflater.from(getActivity()));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        setCancelable(false);


        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
