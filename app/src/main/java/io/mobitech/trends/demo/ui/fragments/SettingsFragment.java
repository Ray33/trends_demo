package io.mobitech.trends.demo.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import io.mobitech.trends.demo.R;
import io.mobitech.trends.demo.utils.SharedPreferencesMapper;

/**
 * Created by Viacheslav Titov on 08.11.2016.
 */

public class SettingsFragment extends Fragment {

    public static final String TAG = SettingsFragment.class.getSimpleName();

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Switch switchHotSearchButton = (Switch) view.findViewById(R.id.switchHotSearchButton);
        switchHotSearchButton.setChecked(SharedPreferencesMapper.needUsedUserId(getActivity()));
        switchHotSearchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferencesMapper.switchUsedUserId(getActivity(), b);
            }
        });
    }

}
