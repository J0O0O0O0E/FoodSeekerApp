package com.example.myapplication.ui.foodbank;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodBank;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Adapter class for displaying a list of FoodBank objects in a ListView.
 * This adapter provides views for each FoodBank item in the list, including
 * the name, status, street, and distance of the FoodBank.
 * @author Si Chen u7756543
 */
public class FoodBankAdapterNew extends BaseAdapter {
    private Context mContext;  // The context in which the adapter is running
    private List<FoodBank> mFoodBankList;  // The list of FoodBank objects to display

    private static final int LENGTH_KM = 1000;  // Conversion factor from meters to kilometers

    /**
     * Default constructor.
     */
    public FoodBankAdapterNew() {
    }

    /**
     * Constructor to initialize the adapter with a context and a list of FoodBank objects.
     *
     * @param mContext The context in which the adapter is running.
     * @param mFoodBankList The list of FoodBank objects to display.
     */
    public FoodBankAdapterNew(Context mContext, List<FoodBank> mFoodBankList) {
        this.mContext = mContext;
        this.mFoodBankList = mFoodBankList;
    }

    /**
     * Sets the context for the adapter.
     *
     * @param mContext The context in which the adapter is running.
     */
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Sets the list of FoodBank objects for the adapter.
     *
     * @param mFoodBankList The list of FoodBank objects to display.
     */
    public void setmFoodBankList(List<FoodBank> mFoodBankList) {
        this.mFoodBankList = mFoodBankList;
    }

    /**
     * Returns the number of items in the data set represented by this adapter.
     *
     * @return The number of items in the data set.
     */
    @Override
    public int getCount() {
        return mFoodBankList.size();
    }

    /**
     * Returns the data item associated with the specified position in the data set.
     *
     * @param position The position of the item within the adapter's data set.
     * @return The data item at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mFoodBankList.get(position);
    }

    /**
     * Returns the row ID associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set.
     * @return The ID of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.).
     *
     * This method inflates a layout for each item in the list and populates the view with
     * the corresponding FoodBank's data. It sets the name, status, street, and distance
     * of the FoodBank to the respective TextViews.
     *
     * @param position The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible (null if a new view is needed).
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout for the list item
        View view = LayoutInflater.from(mContext).inflate(R.layout.foodbankinfo_list, null);

        // Get references to the TextViews in the layout
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_status = view.findViewById(R.id.tv_status);
        TextView tv_street = view.findViewById(R.id.tv_street);
        TextView tv_distance = view.findViewById(R.id.tv_distance);

        // Get the FoodBank object for the current position
        FoodBank foodBank = mFoodBankList.get(position);

        // Set the name of the FoodBank
        tv_name.setText(foodBank.getName());

        // Set the status of the FoodBank (open or close) and the text color
        if (foodBank.isStatus()) {
            tv_status.setText("open");
            tv_status.setTextColor(Color.parseColor("#32CD32"));  // Green color for open status
        } else {
            tv_status.setText("close");
            tv_status.setTextColor(Color.parseColor("#FF2400"));  // Red color for close status
        }

        // Set the street of the FoodBank
        tv_street.setText(foodBank.getStreet());

        // Set the distance of the FoodBank to the user, formatted as meters or kilometers
        tv_distance.setText(formatDistance(foodBank.getDistanceToUser()));

        return view;
    }

    /**
     * Formats a distance value into a human-readable string.
     *
     * This method takes a distance value in meters and formats it as a string. If the distance is
     * less than the defined LENGTH_KM, it formats the distance in meters (m). If the distance is
     * equal to or greater than LENGTH_KM, it converts the distance to kilometers (km) and formats it
     * with one decimal place, using commas as thousand separators.
     *
     * @param input The distance value in meters to be formatted.
     * @return A formatted string representing the distance in meters or kilometers.
     */
    private static String formatDistance(double input) {
        // If the distance is less than LENGTH_KM, format it in meters
        if (input < LENGTH_KM) {
            return String.format("%.0fm", input);
        } else {
            // Convert the distance to kilometers
            double km = input / LENGTH_KM;
            // Create a NumberFormat object for formatting numbers,
            // ensuring that every three digits are separated by commas
            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            nf.setMaximumFractionDigits(1);  // Set up to one decimal place
            nf.setMinimumFractionDigits(1);  // Set to at least one decimal place
            return nf.format(km) + "km";
        }
    }
}
