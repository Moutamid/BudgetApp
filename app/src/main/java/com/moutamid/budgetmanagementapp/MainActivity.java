package com.moutamid.budgetmanagementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    private BarChart barChart;
    ProgressBar progress_bar;
    TextView text_view_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_view_progress = findViewById(R.id.text_view_progress);
        progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setProgress(90);
        text_view_progress.setText("90%\n Goals Achieved");
//        text_view_progress.setTextColor(0x00ffffff);
        checkApp(MainActivity.this);
        ArrayList<SliderModel> sliderDataArrayList = new ArrayList<>();
        SliderView sliderView = findViewById(R.id.slider);
        sliderDataArrayList.add(new SliderModel("Buy car", 60));
        sliderDataArrayList.add(new SliderModel("Hostel Fee", 40));
        sliderDataArrayList.add(new SliderModel("House Rent", 30));
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
        expenseEntries.add(new BarEntry(2, 2000)); // March

        BarDataSet incomeDataSet = new BarDataSet(incomeEntries, "Income");
        incomeDataSet.setColor(getResources().getColor(R.color.income_color));

        BarDataSet expenseDataSet = new BarDataSet(expenseEntries, "Expenses");
        expenseDataSet.setColor(getResources().getColor(R.color.expense_color));

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

    } public void income(View view) {
        startActivity(new Intent(this, AddIncomeActivity.class));

    } public void expenses(View view) {
        startActivity(new Intent(this, AddExpenseActivity.class));

    } public void planned_payment(View view) {
        startActivity(new Intent(this, PlannedPaymentActivity.class));

    }
}