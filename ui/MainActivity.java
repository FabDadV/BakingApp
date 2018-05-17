package fdv.ba.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fdv.ba.R;
import fdv.ba.AppExecutors;
import fdv.ba.data.db.AppDB;

public class MainActivity extends AppCompatActivity {
    private AppDB appDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
