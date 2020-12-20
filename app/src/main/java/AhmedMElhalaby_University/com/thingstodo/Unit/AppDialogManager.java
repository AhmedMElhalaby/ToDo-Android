package AhmedMElhalaby_University.com.thingstodo.Unit;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


import java.util.concurrent.atomic.AtomicReference;

import AhmedMElhalaby_University.com.thingstodo.CallBack.InstallCallback;
import AhmedMElhalaby_University.com.thingstodo.CallBack.InstallTwoStringCallback;
import AhmedMElhalaby_University.com.thingstodo.R;


public class AppDialogManager {


    public static void showErrorDialog(Context context, String error) {
        if (context != null) {
            AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.error))
                    .setMessage(error)
                    .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();


            TextView textView = alertDialogBuilder.findViewById(android.R.id.message);
            TextView alertTitle = alertDialogBuilder.findViewById(R.id.alertTitle);
            Button button1 = alertDialogBuilder.findViewById(android.R.id.button1);
            Button button2 = alertDialogBuilder.findViewById(android.R.id.button2);
            if (textView != null) {
                textView.setLineSpacing(0, 1.5f);
            }


        }
    }


    public static void showErrorDialog(Activity context, String error, DialogInterface.OnClickListener okClickListener) {
        if (context != null && !context.isFinishing()) {
            AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                    .setTitle(R.string.error).setMessage(error).setPositiveButton(R.string.ok, okClickListener).show();

            TextView textView = alertDialogBuilder.findViewById(android.R.id.message);
            TextView alertTitle = alertDialogBuilder.findViewById(R.id.alertTitle);
            Button button1 = alertDialogBuilder.findViewById(android.R.id.button1);
            Button button2 = alertDialogBuilder.findViewById(android.R.id.button2);
            if (textView != null) {
                textView.setLineSpacing(0, 1.5f);
            }

        }
    }


    public static void showSuccessDialog(Activity context, String title, String error,
                                         DialogInterface.OnClickListener okClickListener,
                                         DialogInterface.OnClickListener cancelClickListener) {
        if (context != null && !context.isFinishing()) {
            AlertDialog alertDialogBuilder = new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(error)
                    .setPositiveButton(context.getString(R.string.confirm), okClickListener).setNegativeButton(context.getString(R.string.cancel), cancelClickListener)
                    .show();

            TextView textView = alertDialogBuilder.findViewById(android.R.id.message);
            TextView alertTitle = alertDialogBuilder.findViewById(R.id.alertTitle);
            Button button1 = alertDialogBuilder.findViewById(android.R.id.button1);
            Button button2 = alertDialogBuilder.findViewById(android.R.id.button2);
            if (textView != null) {
                textView.setLineSpacing(0, 1.5f);
            }


        }
    }


    public static void showCustomErrorDialog(Activity context, String title, String error) {
        LayoutInflater inflater = context.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_custom_error_layout, null);
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context).create();
        alertDialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alertDialogBuilder.setView(dialoglayout);
        TextView text_title_error_dialog = dialoglayout.findViewById(R.id.text_title_error_dialog);
        TextView text_body_error_dialog = dialoglayout.findViewById(R.id.text_body_error_dialog);
        TextView close_dialog = dialoglayout.findViewById(R.id.close_dialog);

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder.dismiss();
            }
        });

        if (!title.isEmpty())
            text_title_error_dialog.setText(title);
        text_body_error_dialog.setText(error);
        if (!((Activity) context).isFinishing()) {
            if (context != null)
                try {
                    alertDialogBuilder.show();
                } catch (WindowManager.BadTokenException e) {
                    //use a log message
                }
        }

    }

    public static void showTwoActionDialog(Activity context, String title, String error, String actionName, final InstallCallback callback) {
        LayoutInflater inflater = context.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_two_action_layout, null);
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context).create();
        alertDialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alertDialogBuilder.setView(dialoglayout);
        TextView text_title_error_dialog = dialoglayout.findViewById(R.id.text_title_error_dialog);
        TextView text_body_error_dialog = dialoglayout.findViewById(R.id.text_body_error_dialog);
        TextView close_dialog = dialoglayout.findViewById(R.id.close_dialog);
        TextView accept_dialog = dialoglayout.findViewById(R.id.accept_dialog);
        accept_dialog.setText(actionName);
        accept_dialog.setTextColor(context.getResources().getColor(R.color.white));

        accept_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onStatusDone("yes");
                alertDialogBuilder.dismiss();
            }
        });

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder.dismiss();
            }
        });

        if (!title.isEmpty())
            text_title_error_dialog.setText(title);
        text_body_error_dialog.setText(error);
        if (!((Activity) context).isFinishing()) {
            if (context != null)
                try {
                    alertDialogBuilder.show();
                } catch (WindowManager.BadTokenException e) {
                    //use a log message
                }
        }

    }


    public static void showCustomErrorDialogNotCancel(Activity context, String title, String error, final InstallCallback installCallback) {
        LayoutInflater inflater = context.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_custom_error_layout, null);
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context).create();
        alertDialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alertDialogBuilder.setView(dialoglayout);
        TextView text_title_error_dialog = dialoglayout.findViewById(R.id.text_title_error_dialog);
        TextView text_body_error_dialog = dialoglayout.findViewById(R.id.text_body_error_dialog);
        TextView close_dialog = dialoglayout.findViewById(R.id.close_dialog);


        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder.dismiss();
                installCallback.onStatusDone("Close");
            }
        });


        if (!title.isEmpty())
            text_title_error_dialog.setText(title);
        text_body_error_dialog.setText(error);
        if (!((Activity) context).isFinishing()) {
            if (context != null)
                try {
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.show();
                } catch (WindowManager.BadTokenException e) {
                    //use a log message
                }
        }

    }


    public static void showAddNewCategoryDialog(Activity context, InstallTwoStringCallback installCallback) {
        LayoutInflater inflater = context.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_add_category_layout, null);
        final AlertDialog alertDialogBuilder = new AlertDialog.Builder(context).create();
        alertDialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alertDialogBuilder.setView(dialoglayout);
        EditText edit_dialog_title = dialoglayout.findViewById(R.id.edit_dialog_title);
        ImageView color1 = dialoglayout.findViewById(R.id.color1);
        ImageView color2 = dialoglayout.findViewById(R.id.color2);
        ImageView color3 = dialoglayout.findViewById(R.id.color3);
        ImageView color4 = dialoglayout.findViewById(R.id.color4);
        ImageView color5 = dialoglayout.findViewById(R.id.color5);
        AtomicReference<String> color = new AtomicReference<>("");
        color1.setOnClickListener(view -> {
            color1.setImageResource(R.drawable.ic_baseline_check_circle_16);
            color2.setImageDrawable(null);
            color3.setImageDrawable(null);
            color4.setImageDrawable(null);
            color5.setImageDrawable(null);
            color.set(RootManager.ColorCategory1);
        });

        color2.setOnClickListener(view -> {
            color1.setImageDrawable(null);
            color2.setImageResource(R.drawable.ic_baseline_check_circle_16);
            color3.setImageDrawable(null);
            color4.setImageDrawable(null);
            color5.setImageDrawable(null);
            color.set(RootManager.ColorCategory2);
        });


        color3.setOnClickListener(view -> {
            color1.setImageDrawable(null);
            color2.setImageDrawable(null);
            color3.setImageResource(R.drawable.ic_baseline_check_circle_16);
            color4.setImageDrawable(null);
            color5.setImageDrawable(null);
            color.set(RootManager.ColorCategory3);
        });


        color4.setOnClickListener(view -> {
            color1.setImageDrawable(null);
            color2.setImageDrawable(null);
            color3.setImageDrawable(null);
            color4.setImageResource(R.drawable.ic_baseline_check_circle_16);
            color5.setImageDrawable(null);
            color.set(RootManager.ColorCategory4);
        });


        color5.setOnClickListener(view -> {
            color1.setImageDrawable(null);
            color2.setImageDrawable(null);
            color3.setImageDrawable(null);
            color4.setImageDrawable(null);
            color5.setImageResource(R.drawable.ic_baseline_check_circle_16);
            color.set(RootManager.ColorCategory5);
        });


        TextView close_dialog = dialoglayout.findViewById(R.id.close_dialog);
        TextView text_color = dialoglayout.findViewById(R.id.text_color);

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit_dialog_title.getText().toString().trim())) {
                    edit_dialog_title.setError(context.getResources().getString(R.string.required_field));
                    edit_dialog_title.requestFocus();
                }
                if (TextUtils.isEmpty(color.get().toString().trim())) {
                    text_color.setError(context.getResources().getString(R.string.required_field));
                    text_color.requestFocus();
                } else {
                    String title = edit_dialog_title.getText().toString().trim();
                    installCallback.onStatusDone(title, color.get());
                    alertDialogBuilder.dismiss();
                }
            }
        });


        if (!((Activity) context).isFinishing()) {
            if (context != null)
                try {
                    Window window = alertDialogBuilder.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    alertDialogBuilder.show();
                } catch (WindowManager.BadTokenException e) {
                    //use a log message
                }
        }

    }


}
