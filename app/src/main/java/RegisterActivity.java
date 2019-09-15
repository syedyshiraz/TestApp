import android.os.Bundle;
import com.example.testapp.R;

public class RegisterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getSharedPreferences("settings", MODE_PRIVATE).getBoolean("dark", true) ? R.style.AppTheme : R.style.LightTheme);
        setContentView(R.layout.activity_register);
}
