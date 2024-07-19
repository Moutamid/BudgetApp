package com.moutamid.budgetmanagementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.annotation.NonNull;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.AddIncomeActivity;
import com.moutamid.budgetmanagementapp.activities.AddWalletScreen;
import com.moutamid.budgetmanagementapp.activities.PlannedPaymentActivity;
import com.moutamid.budgetmanagementapp.activities.TransactionHistoryActivity;
import com.moutamid.budgetmanagementapp.activities.UserActivity;
import com.moutamid.budgetmanagementapp.adapter.SlideAdapter;
import com.moutamid.budgetmanagementapp.model.Income;
import com.moutamid.budgetmanagementapp.model.SavingGoal;
import com.moutamid.budgetmanagementapp.model.SliderModel;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private DatabaseReference incomeReference;
    private DatabaseReference expensesReference;
    private List<Income> incomes;
    private List<Income> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        text_view_progress = findViewById(R.id.text_view_progress);
        cash = findViewById(R.id.cash);
        progress_bar = findViewById(R.id.progress_bar);
        cash.setText(Stash.getString("current_cash"));
//        text_view_progress.setTextColor(0x00ffffff);
        checkApp(MainActivity.this);
        barChart = findViewById(R.id.barChart);
        incomeReference = FirebaseDatabase.getInstance().getReference("BudgetingApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("incomes");
        expensesReference = FirebaseDatabase.getInstance().getReference("BudgetingApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("expenses");

        incomes = new ArrayList<>();
        expenses = new ArrayList<>();

        fetchTransactionData();
        fetchSavingGoals();
        barChart = findViewById(R.id.barChart);

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

    private void fetchTransactionData() {
        incomeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                incomes.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Income income = postSnapshot.getValue(Income.class);
                    incomes.add(income);
                }
                fetchExpensesData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load income data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchExpensesData() {
        expensesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expenses.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Income expense = postSnapshot.getValue(Income.class);
                    expenses.add(expense);
                }
                displayBarChart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load expenses data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayBarChart() {
        List<BarEntry> incomeEntries = new ArrayList<>();
        List<BarEntry> expenseEntries = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        // Map to hold date and index
        Map<String, Integer> dateIndexMap = new HashMap<>();
        int index = 0;

        // Process incomes
        for (Income income : incomes) {
            String date = income.getDate(); // Assuming you have a getDate() method in your Income class
            if (!dateIndexMap.containsKey(date)) {
                dateIndexMap.put(date, index);
                dates.add(date);
                index++;
            }
            incomeEntries.add(new BarEntry(dateIndexMap.get(date), Float.parseFloat(income.getAmount())));
        }

        // Process expenses
        for (Income expense : expenses) {
            String date = expense.getDate(); // Assuming you have a getDate() method in your Expense class
            if (!dateIndexMap.containsKey(date)) {
                dateIndexMap.put(date, index);
                dates.add(date);
                index++;
            }
            expenseEntries.add(new BarEntry(dateIndexMap.get(date), Float.parseFloat(expense.getAmount())));
        }

        BarDataSet incomeDataSet = new BarDataSet(incomeEntries, "Income");
        incomeDataSet.setColor(ColorTemplate.COLORFUL_COLORS[0]);
        incomeDataSet.setValueTextColor(Color.BLACK); // Set text color for values inside bars
        incomeDataSet.setValueTextSize(7f); // Set text size for values inside bars

        BarDataSet expenseDataSet = new BarDataSet(expenseEntries, "Expenses");
        expenseDataSet.setColor(ColorTemplate.COLORFUL_COLORS[1]);
        incomeDataSet.setValueTextColor(Color.BLACK); // Set text color for values inside bars
        incomeDataSet.setValueTextSize(7f); // Set text size for values inside bars

        BarData barData = new BarData(incomeDataSet, expenseDataSet);

        // Set the bar width
        float barWidth = 0.3f; // Adjust bar width as needed
        barData.setBarWidth(barWidth);

        // Configure the X axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // Ensure labels are not skipped
        xAxis.setLabelRotationAngle(-45); // Rotate labels for better readability
        xAxis.setLabelCount(dates.size(), true); // Set label count to match the number of dates

        // Set group spacing
        float groupSpace = 0.4f;
        float barSpace = 0.05f; // Adjust bar space to fit within group

        barChart.setData(barData);

        // Set the axis limits based on the group width
        xAxis.setAxisMinimum(-barWidth); // Set the minimum value to include space for the bars
        xAxis.setAxisMaximum(dates.size()); // Set the maximum value to fit all bars

        // Group the bars
        barChart.groupBars(-barWidth, groupSpace, barSpace);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.animateY(1000);
        barChart.invalidate();
    }

    private void fetchSavingGoals() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BudgetingApp").child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("SavingGoals");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<SliderModel> sliderDataArrayList = new ArrayList<>();
                int totalAmount = 0;
                int totalAlreadySaved = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String goalName = snapshot.child("goalName").getValue(String.class);
                    String goalid = snapshot.child("id").getValue(String.class);
                    int amount = Integer.parseInt(snapshot.child("amount").getValue(String.class));
                    int alreadySaved = Integer.parseInt(snapshot.child("alreadySaved").getValue(String.class));
                    sliderDataArrayList.add(new SliderModel(goalName, amount, alreadySaved, goalid));

                    // Calculate totals
                    totalAmount += amount;
                    totalAlreadySaved += alreadySaved;
                }

                // Calculate total percentage
                int totalPercentage = (totalAmount == 0) ? 0 : (totalAlreadySaved * 100) / totalAmount;
                displayTotalPercentage(totalPercentage);

                // Set up the slider
                SlideAdapter adapter = new SlideAdapter(MainActivity.this, sliderDataArrayList);
                SliderView sliderView = findViewById(R.id.slider);
                sliderView.setSliderAdapter(adapter);
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                sliderView.setScrollTimeInSec(3);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    private void displayTotalPercentage(int totalPercentage) {
        text_view_progress.setText(totalPercentage+"%\n"+getString(R.string._90_goals_achieved));
        progress_bar.setProgress(totalPercentage);

    }
}