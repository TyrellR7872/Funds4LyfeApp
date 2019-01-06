package com.troberts.funds4lyfeapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.troberts.funds4lyfeapp.Accounts.Account;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private ArrayList<Account> accounts;
    private ArrayList<String> accountNames;
    private Context context;

    public interface ItemClicked {
        void onItemClicked(int i);
    }

    public AccountAdapter(Context context, ArrayList<Account> accounts, ArrayList<String> accountNames){
        this.context = context;
        this.accounts = accounts;
        this.accountNames = accountNames;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    activity.onItemClicked(accounts.indexOf(v.getTag()));

                }
            });
        }
    }

    @NonNull
    @Override
    public AccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_layout,viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.ViewHolder viewHolder, int i) {

        viewHolder.itemView.setTag(accounts.get(i));

        viewHolder.tvName.setText(accountNames.get(i));
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }
}
