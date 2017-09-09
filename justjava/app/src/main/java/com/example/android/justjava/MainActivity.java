/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */
package com.example.android.justjava;


import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAGWhipped = "WhippedCreamClicked";

    int quantity = 2;
    /* quantity is the number of coffees ordered!
     it is now a global variable
    initial value is set to 2
    Global variables has a purple color, local variables has black color.
    That is the way to identify Global variables from Local variables
    */


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }


    /**
     * This method is called when the '+' add button is clicked.
     */

    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the '-' minus button is clicked.
     */

    public void decrement(View view) {
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     * despliega la cantidad ordenada y el costo de lo ordenado
     */
    public void submitOrder(View view) {


        // If CheckBox isn't defined in onCreate() method then have to use something like this
        // http://stackoverflow.com/questions/9411540/android-get-checked-checkbox-values
        // When the button is clicked, find the CheckBox view,
        // get checked state from the CheckBox, and store the checked state value
        // in a new boolean variable.
        // boolean isChecked = ((CheckBox) findViewById(R.id.whippedCreamCheckBox)).isChecked();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        int price = calculatePrice();

        displayMessage(createOrderSummary(price, hasWhippedCream));
    }



    /* whippedCreamCheckBox
       This method is called to check whether the whippedCreamCheckBox was checked.
    */
    public void onCheckboxWhippedCreamClicked(View view) {


      // Is the whippedCreamCheckBox view now checked?

        //Log.v(TAGWhipped, "I am in CheckBox" );
    }




    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice() {
        return quantity * 10;
    }




    /**
     * Create the order summary.
     * @param price of the order
     * @param addWhippedCream  is whether or not the user wants whipped cream topping
     * @return priceMessage text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream) {

        // isChecked has the status of the whipped checkbox state
        // that could be either True ot False

        String priceMessage = "Name: Kaptain Kunal";
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you";
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     * Identify the quantity_text_view on the main view, then
     * set the number to string format, that
     * will be displayed as string in the textView
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
        quantityTextView.setVisibility(View.VISIBLE);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


    /* AUTOGENERATED FUNCTIONS BY ANDROID STUDIO */

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
