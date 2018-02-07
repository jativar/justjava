/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */
package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.URI;
import java.text.NumberFormat;

import static android.R.attr.name;
import static android.R.attr.value;
import static com.example.android.justjava.R.string.order_summary_email_subject;
//import static com.example.android.justjava.R.id.name_field;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAGWhipped = "WhippedCreamClicked";
    private static final String TAGChocolate = "ChocolateCreamClicked";
    private static final String TAGincrement = "increment method";
    private static final String TAGdecrement = "decrement method";
    String emailSubject = "";
    String emailBody = "";


    int quantity = 1;
    /* quantity is the number of coffees ordered!
     it is now a global variable
    initial value is set to 1
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

        //chocoImageView = (ImageView) findViewById(R.id.cream_choco_icon);
        //chocoImageView.setVisibility(View.INVISIBLE);

    }


    /**
     * This method is called when the '+' add button is clicked.
     */

    public void increment(View view) {
        quantity = quantity + 1;

        if (quantity > 10) {
//            Context context = getApplicationContext();
              CharSequence text = "too_many_coffees.";
//            int duration = Toast.LENGTH_SHORT;
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.too_many_coffees), Toast.LENGTH_LONG).show();

            quantity = 10;
            Log.v(TAGincrement, (String) text);
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the '-' minus button is clicked.
     */

    public void decrement(View view) {
        quantity = quantity - 1;

        if (quantity < 1) {
//            Context context = getApplicationContext();
              CharSequence text = "too_few_coffees.";
//            int duration = Toast.LENGTH_SHORT;
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.too_few_coffees), Toast.LENGTH_LONG).show();

            quantity = 1;
            Log.v(TAGdecrement, (String) text);
        }

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
        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        Log.v("MainActivity", "Has chocolate cream: " + hasChocolate);

        EditText text = (EditText) findViewById(R.id.name);
        String clientName = text.getText().toString(); // Return an Editable object, calling methods
        Log.v("MainActivity", "Get name: " + clientName);

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Prepare the email body
        emailBody = createOrderSummary(clientName, price, hasWhippedCream, hasChocolate);

        // Call to the Intent email, future implementation create sendmail()...

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this


        emailSubject = getString(R.string.order_summary_email_subject);
        emailSubject += " ";
        emailSubject += clientName;

        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        Log.v("MainActivity",emailSubject);

        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }






    /* onCheckboxWhippedCreamClicked
       This method is called to check whether the whippedCreamCheckBox was checked.
       This code segment is doing anything yet.  Keep it for future use.
    */
    public void onCheckboxWhippedCreamClicked(View view) {

      // Is the whippedCreamCheckBox view now checked?

        Log.v(TAGWhipped, "CheckboxWhippedCreamClicked." );
    }

    /* onCheckboxChocolateCreamClicked
       This method is called to check whether the CheckboxChocolateCream was checked.
       This code segment is doing anything yet.  Keep it for future use.
    */
    public void onCheckboxChocolateClicked(View view) {


        // Is the CheckboxChocolateCream view now checked?

        Log.v(TAGChocolate, "CheckboxChocolateClicked." );

    }


    /**
     * Calculates the price of the order.
     * @param addCream is whether or not the client wants whipped cream topping
     * @param addChocolate is whether or not the client wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean addCream, boolean addChocolate) {


        // Calculate the Base Price
        // Base Price = (Cost of a Cup of Coffe + Cost of Whipped Cream + Cost of Chocolate)
        // Total Price = Base Price x Number of Cups of Cofee
        int basePrice = 5;

        // if has whipped cream, add $1
        if (addCream) {
            basePrice += 1;
        }

        // if has chocolate, add $2
        if (addChocolate) {
            basePrice += 2;
        }

        // calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }




    /**
     * Create the order summary.
     * @param price of the order
     * @param addWhippedCream  is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param name is the name of the client
     * @return priceMessage text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {



        // isChecked has the status of the whipped checkbox state
        // that could be either True ot False

        //String priceMessage = "Name: ";
        // priceMessage += addName;

        // Extract all hardcoded strings from the
        // XML and java files into a default
        // res/values/strings/xml file.

        // Update the affected XML and Java files
        // to refer to the appropiate string resources

        // Provide alternate translations in
        // another language for the strings in your app.
        // You can use the Spanish translations we provide.


        String priceMessage = getString(R.string.order_summary_name, name);

        // priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        if (addWhippedCream){
            priceMessage += "\n" + getString(R.string.order_summary_whipped_cream_true);
        } else {
            priceMessage += "\n" + getString(R.string.order_summary_whipped_cream_false);
        }

        // priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        if (addChocolate){
            priceMessage += "\n" + getString(R.string.order_summary_chocolate_true);
        } else {
            priceMessage += "\n" + getString(R.string.order_summary_chocolate_false);
        }



        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
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


// EXAMPLE OF CODE HOW TO USE THE Maps Intent
// Show a location on a map using the Maps Intent
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            Uri geoLocation;
//
//            geoLocation = Uri.parse("geo:47.6, -122.3");
//            intent.setData(geoLocation);
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivity(intent);
//            }


//    public void showMap (Uri geolocation) {
//        return;
//
//    }


// EXAMPLE OF CODE HOW TO USE THE Email intent
// Send e-mail using the Intent email
//    public void sendEmail() {
//        String recipient = emailAddress;
//        Intent email = new Intent(Intent.ACTION_SEND,Uri.parse("mailto:"));
//        email.setType("message/rfc822");
//        email.putExtra(Intent.EXTRA_EMAIL, recipient);
//        email.putExtra(Intent.EXTRA_SUBJECT,emailSubject);
//        email.putExtra(Intent.EXTRA_TEXT, emailBody);
//
//        try {
//            // The user can choose the email client
//            startActivity(Intent.createChooser(email, "Choose an email client from ..."));
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(MainActivity.this, "No email client installed.", Toast.LENGTH_LONG).show();
//
//        }
//    }
