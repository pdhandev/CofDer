        package com.example.android.justjava;

        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.NumberFormat;

        /**
         * This app displays an order form to order coffee.
         */
        public class MainActivity extends ActionBarActivity {
            int quantity=0;
            //boolean addWhippedCream;
            //boolean addChocolate;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
            }


            /**
             * This method is called when the order button is clicked.
             */
            public void submitOrder(View view) {
                CheckBox whipped = (CheckBox) findViewById(R.id.whipped_cream__check_box);
                boolean addWhippedCream = whipped.isChecked();

                CheckBox choc = (CheckBox) findViewById(R.id.chocolate_check_box);
                boolean addChocolate = choc.isChecked();

                EditText name = (EditText)findViewById(R.id.name_edit_text);
                String nameCustomer = name.getText().toString();


                int price = calculatePrice(addWhippedCream, addChocolate);
                String summary = createOrderSummary(price,addWhippedCream, addChocolate, nameCustomer );
                directToEmail(nameCustomer, summary);
            }
            /**
             * This method is called to Calculate price.
             * @return total price
             * @param checkWhippedCream is whether to add whipped cream or not
             */

            private int calculatePrice(boolean checkWhippedCream, boolean checkChocolate){
                int totalPrice;
                if ((checkWhippedCream)&&(checkChocolate)){
                    totalPrice = (5 + 1 + 2)*quantity;
                }
                else if (checkWhippedCream){
                    totalPrice = (5 + 1)*quantity;
                }
                else if (checkChocolate){
                    totalPrice = (5 + 2)*quantity;
                }
                else{
                    totalPrice = 5*quantity;
                }

                return totalPrice;
            }

            /**
             * This method is called to Create Order Summary.
             * @param totalPrice of the order
             * @return Summary of order
             */

            private String createOrderSummary(int totalPrice, boolean addWhippedCream, boolean addChocolate, String nameCustomer){
                String summary = getString(R.string.order_summary_name, nameCustomer)+ "\n " +
                                 getString(R.string.order_summary_chocolate, addChocolate)+ "\n" +
                                 getString(R.string.order_summary_whip, addWhippedCream)+ "\n" +
                                 getString(R.string.order_summary_quantity, quantity)+ "\n" +
                                 getString(R.string.total,totalPrice) + "\n" + getString(R.string.thank_you);               ;
                return summary;
            }
            /**
             *
             * This method is called when the + button is clicked.
             */
            public void increment(View view) {
                if (quantity==100){
                    Toast toastInc = Toast.makeText(getApplicationContext(), getString(R.string.increment_toast), Toast.LENGTH_SHORT);
                    toastInc.show();
                    return;
                }
                else{
                    quantity +=1;
                    displayQuantity(quantity);
                }
            }
            /**
             * This method is called when the - button is clicked.
             */
            public void decrement(View view) {
                if (quantity<=1){
                    Toast toastDec = Toast.makeText(getApplicationContext(), getString(R.string.decrement_toast), Toast.LENGTH_SHORT);
                    toastDec.show();
                    return;
                }

                else {
                    quantity -=1;
                    displayQuantity(quantity);
                }
            }

            /**
             * This method displays the given quantity value on the screen.
             */
            private void displayQuantity(int numberOfCoffees) {
                TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
                quantityTextView.setText("" + numberOfCoffees);
            }

            /**
             * This method displays the given message on the screen.

            private void displayMessage(String message) {
                TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
                orderSummaryTextView.setText(message);
            }
             */


            private void directToEmail(String nameCustomer, String message){
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_email_sub, nameCustomer));
                intent.putExtra(Intent.EXTRA_TEXT, message);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        }