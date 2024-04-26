package com.news.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class Extensions {

    static String NEWSDATA = "newsData";
    static String NEWSLIST = "newsList";

    /**
     * This Extension has been using for showing Toast messages
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This Extension has been using for hide keyboard
     */
    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * This Extension has been using for set padding for safe area
     */
    public static void adjustFullScreen(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * This method is used to check if a string is empty or null.
     */
    public static boolean checkEmptyString(Object obj) {
        if (obj == null) return true;
        else return TextUtils.isEmpty(obj.toString().trim());
    }

    private static ProgressDialog dialog;

    /**
     * This function is used for showing a progress dialog
     */
    public static void showProgress(Context context) {
        dialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        dialog.setMessage(context.getString(R.string.Please_wait));
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * This function is used for hiding the progress dialog
     */
    public static void hideProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * This function is used for checking internet connection
     */
    public static boolean isConnect(Context context) {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    isConnected = true;
                }
            }
        } else {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }

        if (!isConnected) {
            hideProgress(); // Assuming HideProgress and DebugToast are methods in your context
            showToast(context, context.getString(R.string.check_internet));
        }
        return isConnected;
    }

    /**
     * This method has been used to load an image into an ImageView.
     *
     * @param imageView The ImageView to load the image into.
     * @param imageUrl  The URL of the image to load.
     */
    public static void loadImage(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            Context context = imageView.getContext();
            Glide.with(context).load(imageUrl).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).centerCrop().into(imageView);
        }
    }

    public static ArrayList<Article> getRandomItems(ArrayList<Article> itemList, int count) {
        // Create a Random object
        Random random = new Random();

        // Create a list to store the randomly selected items
        ArrayList<Article> randomItems = new ArrayList<>();

        // Loop to select random items
        for (int i = 0; i < count; i++) {
            // Generate a random index within the range of the list
            int randomIndex = random.nextInt(itemList.size());

            // Add the item at the random index to the result list
            randomItems.add(itemList.get(randomIndex));

            // Remove the selected item from the original list to avoid duplicates
//            itemList.remove(randomIndex);
        }

        return randomItems;
    }
}