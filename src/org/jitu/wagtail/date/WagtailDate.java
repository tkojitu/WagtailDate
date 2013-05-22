package org.jitu.wagtail.date;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

public class WagtailDate extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wagtail_date);
        Intent intent = getIntent();
        if (!"text/plain".equals(intent.getType())) {
            finish();
            return;
        }
        String str = getDateString(intent);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        copyDate(str);
        setupResult(str);
        finish();
    }

    private String getDateString(Intent intent) {
        final String defaultPattern = "yyyyMMdd";
        String pat = intent.hasExtra("pattern") ? intent.getStringExtra("pattern") : defaultPattern;
        SimpleDateFormat formatter;
        try {
            formatter = new SimpleDateFormat(pat, Locale.getDefault());
        } catch (RuntimeException e) {
            formatter = new SimpleDateFormat(defaultPattern, Locale.getDefault());
        }
        return formatter.format(new Date());
    }

    private void copyDate(String str) {
        ClipData data = ClipData.newPlainText("text", str);
        getClipboard(this).setPrimaryClip(data);
    }

    private ClipboardManager getClipboard(Context context) {
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    private void setupResult(String str) {
        Intent ret = new Intent(this, WagtailDate.class);
        ret.putExtra("date", str);
        setResult(RESULT_OK, ret);
    }
}
