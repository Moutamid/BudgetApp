package com.moutamid.budgetmanagementapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moutamid.budgetmanagementapp.R;
import com.moutamid.budgetmanagementapp.model.LanguageItem;

import java.util.List;

public class LanguageSpinnerAdapter extends ArrayAdapter<LanguageItem> {

    public LanguageSpinnerAdapter(@NonNull Context context, @NonNull List<LanguageItem> languageList) {
        super(context, 0, languageList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.imageView_flag);
        TextView textViewLanguage = convertView.findViewById(R.id.textView_language);

        LanguageItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getFlagImage());
            textViewLanguage.setText(currentItem.getLanguageName());
        }

        return convertView;
    }
}
