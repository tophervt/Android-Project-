package edu.gatech.seclass.sdpencryptor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText sourceTextID;
    private EditText slopeInputID;
    private EditText offsetInputID;
    private Button transformButtonID;
    private EditText transformedTextID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        transformButtonID = (Button) findViewById(R.id.transformButtonID);
        sourceTextID = (EditText) findViewById(R.id.sourceTextID);
        slopeInputID = (EditText) findViewById(R.id.slopeInputID);
        slopeInputID.setText("1");
        offsetInputID = (EditText) findViewById(R.id.offsetInputID);
        offsetInputID.setText("1");
        transformedTextID = (EditText) findViewById(R.id.transformedTextID);
        transformButtonID.setOnClickListener(this::handleClick);
    }
    public void handleClick(View view) {
        String result;
        String source = sourceTextID.getText().toString();   //sourceTextID.getText().toString();
        String sI = slopeInputID.getText().toString();
        if (sI.equals("")) {
            slopeInputID.setError("Invalid Slope Input");
        }
        String oF = offsetInputID.getText().toString();
        if (oF.equals("")) {
            slopeInputID.setError("Invalid Slope Input");
        }
        int sInput = Integer.parseInt(sI);
        if (sInput < 1 || sInput > 61 || gcd(sInput, 62) != 1) {
            slopeInputID.setError("Invalid Slope Input");
        }
        int offInput = Integer.parseInt(oF);
        if (offInput < 1 || offInput > 61) offsetInputID.setError("Invalid Offset Input");
        result = encrypt(source, sInput, offInput);
        transformedTextID.setText(result);
    }
    private static boolean containsLetterOrNumber(String str) {
        String lettersAndNumbers = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (char c : str.toCharArray()) {
            if (lettersAndNumbers.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }
    private int countAlphabeticWords() {
        int wordCount = 0;
        int letCount = 0;
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String sT = sourceTextID.getText().toString();;
        for (int i = 0; i < sT.length(); i++) {
            if (letters.indexOf(sT.charAt(i)) != -1) {
                letCount++;
            }
            else if (letCount > 0 || (letCount > 0 && i == sT.length()-1)) {
                wordCount++;
                letCount = 0;
            }
        }
        if (letCount > 0) wordCount++;
        return wordCount;
    }
    //change ints to Strings from text
    private String encrypt(String source, int sInput, int offInput) {
        //source = s.getText().toString();
        if (source.isEmpty() ||!containsLetterOrNumber(source)) {
            sourceTextID.setError("Invalid Source Text");
            return "";
        }
//        if (sl == null ||sl.isEmpty() ) {
//            slopeInputID.setError("Invalid Slope Input");
//            return "";
//        }
//        if (of == null || of.isEmpty()) {
//            offsetInputID.setError("Invalid Offset Input");
//            return "";
//        }
//        int sInput = Integer.parseInt(sl);
//        int offInput = Integer.parseInt(of);
        if (sInput < 1 || sInput > 61 || gcd(sInput, 62) != 1) {
            slopeInputID.setError("Invalid Slope Input");
            return "";
        }
        if (offInput < 1 || offInput > 61) {
            offsetInputID.setError("Invalid Offset Input");
            return "";
        }
        String alphaB = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
        StringBuilder reString = new StringBuilder();
        for (char c : source.toCharArray()) {
            int i = alphaB.indexOf(c);
            if (i != -1) {
                reString.append(alphaB.charAt((i*sInput+offInput)%62));
            }
            else {
                reString.append(c);
            }
        }
        return reString.toString();
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}












