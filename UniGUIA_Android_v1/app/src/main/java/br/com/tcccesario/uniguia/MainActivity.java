package br.com.tcccesario.uniguia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btScan = findViewById(R.id.btn);
        final Activity activity = this;

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        alert("Idioma não suportado");
                    } else {
                        tts.setPitch(0.6f);
                        tts.setSpeechRate(0.8f);
                        speak("Bem vindo ao UniGuia. Toque no centro da tela para iniciar a leitura do código.");
                    }
                }
            }
        });
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setCameraId(0);
                integrator.initiateScan();
                speak("Direcione a câmera trazeira do seu telefone para o código");
            }
        });

    }

    private void speak(String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    /**
     * Esse metodo controla o leitor do QRCode
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() != null) {
                alert(intentResult.getContents());
            } else {
                alert("A leitura do código foi cancelada pelo usuário");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //Mensagem que aparece ao ler o QRCode

    /*aqui onde eu vou declarar os arquivos de midia. Quero que o programa procure o seguinte: quando ele receber na função acima
    o valor em string vai ter a comparação. if for igual ao nome do arquivo de midie essa midia será executada*/
    private void alert(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        speak(msg);
    }
}
