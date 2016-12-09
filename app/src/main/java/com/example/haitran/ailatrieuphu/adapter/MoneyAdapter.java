package com.example.haitran.ailatrieuphu.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haitran.ailatrieuphu.R;
import com.example.haitran.ailatrieuphu.activity.HomeActivity;

/**
 * Created by Hai Tran on 10/26/2016.
 */

public class MoneyAdapter extends BaseAdapter {
    private String[] arrMoney;

    public MoneyAdapter(String[] arrMoney) {
        this.arrMoney = arrMoney;
    }

    @Override
    public int getCount() {
        return arrMoney.length;
    }

    @Override
    public String getItem(int i) {
        return arrMoney[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_money, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.txtMoney = (TextView) view.findViewById(R.id.txt_money_navigation);
            viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.linear_money);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i == HomeActivity.positionNavigation) {
            viewHolder.linearLayout.setVisibility(View.VISIBLE);

        } else if (i > HomeActivity.positionNavigation) {
            viewHolder.txtMoney.setAlpha((float) 0.3);
            viewHolder.linearLayout.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.linearLayout.setVisibility(View.INVISIBLE);
        }
        if (i == 0 || i == 5 || i == 10) {
            viewHolder.txtMoney.setTextColor(Color.parseColor("#FFD54F"));
            viewHolder.txtMoney.setText(arrMoney[i]);
        } else
            viewHolder.txtMoney.setText(arrMoney[i]);
        return view;
    }

    private class ViewHolder {
        TextView txtMoney;
        LinearLayout linearLayout;
    }


}
