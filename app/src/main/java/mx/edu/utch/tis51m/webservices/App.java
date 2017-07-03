package mx.edu.utch.tis51m.webservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class App extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app);

        Intent intento = new Intent(App.this, Preferencias.class);
        startActivity(intento);

    }

    public void elMetodo(View view) {

        Intent intento = new Intent(App.this,Query.class);
        startActivity(intento);
    }

    public void agregaPrro(View view) {
        Intent intento = new Intent(App.this, Agregar.class);
        startActivity(intento);
    }

}
