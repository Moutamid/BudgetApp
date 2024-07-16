package com.moutamid.budgetmanagementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.fxn.stash.Stash;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.moutamid.AddIncomeActivity;
import com.moutamid.budgetmanagementapp.activities.AddWalletScreen;
import com.moutamid.budgetmanagementapp.activities.PlannedPaymentActivity;
import com.moutamid.budgetmanagementapp.activities.TransactionHistoryActivity;
import com.moutamid.budgetmanagementapp.activities.UserActivity;
import com.moutamid.budgetmanagementapp.adapter.SlideAdapter;
import com.moutamid.budgetmanagementapp.adapter.SliderModel;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import smartdevelop.ir.eram.showcaseviewlib.GuideView;
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType;
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener;

public class MainActivity extends AppCompatActivity {
    BarChart barChart;
    ProgressBar progress_bar;
    TextView text_view_progress, cash;
    AppCompatButton expensesButton;
    AppCompatButton incomeButton;
    RelativeLayout addWalletButton;
    RelativeLayout transactionHistoryButton;
    RelativeLayout plannedPaymentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        text_view_progress = findViewById(R.id.text_view_progress);
        cash = findViewById(R.id.cash);
        progress_bar = findViewById(R.id.progress_bar);
        cash.setText(Stash.getString("current_cash"));
        progress_bar.setProgress(90);
        text_view_progress.setText(R.string._90_goals_achieved);
//        text_view_progress.setTextColor(0x00ffffff);
        checkApp(MainActivity.this);
        ArrayList<SliderModel> sliderDataArrayList = new ArrayList<>();
        SliderView sliderView = findViewById(R.id.slider);
        sliderDataArrayList.add(new SliderModel(getString(R.string.buy_car), 60));
        sliderDataArrayList.add(new SliderModel(getString(R.string.hostel_fee), 40));
        sliderDataArrayList.add(new SliderModel(getString(R.string.house_rent), 30));
        SlideAdapter adapter = new SlideAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        barChart = findViewById(R.id.barChart);
        // Sample data for income and expenses
        ArrayList<BarEntry> incomeEntries = new ArrayList<>();
        incomeEntries.add(new BarEntry(0, 5000)); // January
        incomeEntries.add(new BarEntry(1, 6000)); // February
        incomeEntries.add(new BarEntry(2, 7000)); // March

        ArrayList<BarEntry> expenseEntries = new ArrayList<>();
        expenseEntries.add(new BarEntry(0, 3000)); // January
        expenseEntries.add(new BarEntry(1, 4000)); // February
        expenseEntries.add(new BarEntry(2, 5000)); // March

        BarDataSet incomeDataSet = new BarDataSet(incomeEntries, "Income");
        incomeDataSet.setColor(getResources().getColor(R.color.appColor));

        BarDataSet expenseDataSet = new BarDataSet(expenseEntries, "Expenses");
        expenseDataSet.setColor(getResources().getColor(R.color.green));

        BarData barData = new BarData(incomeDataSet, expenseDataSet);
        barData.setBarWidth(0.3f); // set custom bar width

        barChart.setData(barData);

        // Formatting the X-Axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"January", "February", "March"}));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        // Formatting the Y-Axis
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setGranularity(1000f);

        barChart.getAxisRight().setEnabled(false);

        barChart.setFitBars(true);
        barChart.animateY(1000);
        barChart.invalidate(); // refresh

        // Create the guide views
        if (!Stash.getBoolean("all_check", false)) {
            Stash.put("all_check", true);
            showGuideViewsSequentially();
        }
        expensesButton = findViewById(R.id.expenses);
        incomeButton = findViewById(R.id.income);
        addWalletButton = findViewById(R.id.add_Wallet);
        transactionHistoryButton = findViewById(R.id.transaction_history);
        plannedPaymentButton = findViewById(R.id.planned_payment);
        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Stash.getBoolean("expense_check", false)) {
                    Stash.put("expense_check", true);
                    createGuideView(expensesButton, getString(R.string.expenses), getString(R.string.tap_here_to_add_expenses_to_make_a_record_of_your_transactions), null);
                } else {
                    startActivity(new Intent(MainActivity.this, AddExpenseActivity.class));
                }
            }
        });incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Stash.getBoolean("income_check", false)) {
                    Stash.put("income_check", true);
                    createGuideView(incomeButton, getString(R.string.income), getString(R.string.tap_here_to_add_income_to_make_a_record_of_your_transactions), null);
                } else {
                    startActivity(new Intent(MainActivity.this, AddIncomeActivity.class));
                }
            }

        });addWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Stash.getBoolean("wallet_check", false)) {
                    Stash.put("wallet_check", true);
                    createGuideView(addWalletButton, getString(R.string.add_wallet), getString(R.string.tap_here_to_add_money_to_your_wallet), null);
                } else {
                    startActivity(new Intent(MainActivity.this, AddWalletScreen.class));
                }
            }
        });transactionHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Stash.getBoolean("trans_check", false)) {
                    Stash.put("trans_check", true);
                    createGuideView(transactionHistoryButton, getString(R.string.transaction_history), getString(R.string.tap_here_to_view_transaction_history_to_see_how_much_you_get_and_spend), null);
                } else {
                    startActivity(new Intent(MainActivity.this, TransactionHistoryActivity.class));
                }
            }
        });plannedPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Stash.getBoolean("planned_check", false)) {
                    Stash.put("planned_check", true);
                    createGuideView(plannedPaymentButton, getString(R.string.planned_payment), getString(R.string.tap_here_to_view_planned_payments_for_future_goals), null);
                } else {
                    startActivity(new Intent(MainActivity.this, PlannedPaymentActivity.class));
                }
            }
        });
    }
    public void user(View view) {
        startActivity(new Intent(this, UserActivity.class));

    }

    public static void checkApp(Activity activity) {
        String appName = "BudgetBee";

        new Thread(() -> {
            URL google = null;
            try {
                google = new URL("https://raw.githubusercontent.com/Moutamid/Moutamid/main/apps.txt");
            } catch (final MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(google != null ? google.openStream() : null));
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String input = null;
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if ((input = in != null ? in.readLine() : null) == null) break;
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(input);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
            String htmlData = stringBuffer.toString();

            try {
                JSONObject myAppObject = new JSONObject(htmlData).getJSONObject(appName);

                boolean value = myAppObject.getBoolean("value");
                String msg = myAppObject.getString("msg");

                if (value) {
                    activity.runOnUiThread(() -> {
                        new AlertDialog.Builder(activity)
                                .setMessage(msg)
                                .setCancelable(false)
                                .show();
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void settings(View view) {
        startActivity(new Intent(this, UserActivity.class));
    }

    public void add_Wallet(View view) {
        startActivity(new Intent(this, AddWalletScreen.class));

    }

    public void income(View view) {
        startActivity(new Intent(this, AddIncomeActivity.class));

    }

    public void expenses(View view) {
        startActivity(new Intent(this, AddExpenseActivity.class));

    }

    public void planned_payment(View view) {
        startActivity(new Intent(this, PlannedPaymentActivity.class));

    }

    public void transaction_history(View view) {
        startActivity(new Intent(this, TransactionHistoryActivity.class));

    }

    private void showGuideViewsSequentially() {
        TextView usernameText;
        ImageView settingsIcon;
        LinearLayout cashText;
        LinearLayout cashFlowText;
        SliderView slider;

        usernameText = findViewById(R.id.username_text);
        settingsIcon = findViewById(R.id.settings_icon);
        cashText = findViewById(R.id.cash_layout);
        cashFlowText = findViewById(R.id.cash_flow_layout);
        slider = findViewById(R.id.slider);

        createGuideView(settingsIcon, getString(R.string.settings), getString(R.string.tap_here_to_access_settings), () ->
                        createGuideView(usernameText, getString(R.string.username), getString(R.string.this_is_your_username), () ->
                                        createGuideView(cashText, getString(R.string.cash), getString(R.string.this_is_your_available_cash), () ->
                                                        createGuideView(cashFlowText, getString(R.string.cash_flow_), "This shows your cash that you spend", () ->
                                                                        createGuideView(slider, getString(R.string.saving_goals), getString(R.string.here_are_your_saving_goals_and_you_can_also_create_new_saving_goals_here), null
//                                                createGuideView(expensesButton, "Expenses", "Tap here to add expenses to make a record of your transactions", () ->
//                                                        createGuideView(incomeButton, "Income", "Tap here to add income to make a record of your transactions", () ->
//                                                                createGuideView(addWalletButton, "Add Wallet", "Tap here to add money to your wallet", () ->
//                                                                        createGuideView(transactionHistoryButton, "Transaction History", "Tap here to view transaction history to see how much you get and spend", () ->
//                                                                                createGuideView(plannedPaymentButton, "Planned Payment", "Tap here to view planned payments for future goals", null)
//                                                                        )
//                                                                )
//                                                        )
//                                                )
                                                                        )
                                                        )
                                        )
                        )
        );
    }

    private void createGuideView(View targetView, String title, String contentText, Runnable onDismiss) {
        targetView.setFocusable(true);
        new GuideView.Builder(this)
                .setTitle(title)
                .setContentText(contentText)
                .setDismissType(DismissType.anywhere) // optional - default DismissType.targetView
                .setTargetView(targetView)
                .setContentTextSize(12) // optional
                .setTitleTextSize(14) // optional
                .setGuideListener(new GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        if (onDismiss != null) {
                            onDismiss.run();
                        }
                    }
                })
                .build()
                .show();
    }
}