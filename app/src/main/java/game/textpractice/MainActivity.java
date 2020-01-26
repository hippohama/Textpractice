package game.textpractice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvMessage;
    private Button btnChoiceA, btnChoiceB, btnChoiceC;

    private ArrayList<Stage> stages;
    private Stage currentStage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.tvMessage);
        btnChoiceA = findViewById(R.id.btnChoiceA);
        btnChoiceB = findViewById(R.id.btnChoiceB);
        btnChoiceC = findViewById(R.id.btnChoiceC);

        btnChoiceA.setOnClickListener(this);
        btnChoiceB.setOnClickListener(this);
        btnChoiceC.setOnClickListener(this);

        // load stages from raw file
        InputStream xmlStream = getResources().openRawResource(R.raw.stages);
        try {
            this.stages = StageParser.parse(xmlStream);
            xmlStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error occured. Invalid xml format.", Toast.LENGTH_SHORT).show();
            finish();
        }

        loadStage("start");
    }

    public void loadStage(String name) {
        Stage targetStage = null;
        for(Stage stage : this.stages) {
            if(stage.name.equals(name)) {
                targetStage = stage;
                break;
            }
        }

        if(targetStage == null) {
            Toast.makeText(this, "Error occured. Invalid stage name.", Toast.LENGTH_SHORT).show();
            finish();
        }

        if(targetStage.choices.size() != 3) {
            Toast.makeText(this, "Error occured. Invalid stage format.", Toast.LENGTH_SHORT).show();
            finish();
        }

        tvMessage.setText(targetStage.message);
        btnChoiceA.setText(targetStage.choices.get(0).text);
        btnChoiceB.setText(targetStage.choices.get(1).text);
        btnChoiceC.setText(targetStage.choices.get(2).text);

        this.currentStage = targetStage;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnChoiceA:
                loadStage(this.currentStage.choices.get(0).nextStageName);
                break;
            case R.id.btnChoiceB:
                loadStage(this.currentStage.choices.get(1).nextStageName);
                break;
            case R.id.btnChoiceC:
                loadStage(this.currentStage.choices.get(2).nextStageName);
                break;
        }
    }
}
