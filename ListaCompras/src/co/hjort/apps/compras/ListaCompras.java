package co.hjort.apps.compras;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ListaCompras extends Activity {

	private Spinner secao;
	private EditText produto;
	private Button incluir;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		secao = (Spinner) findViewById(R.id.secao);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.secoes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		secao.setAdapter(adapter);
		secao.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO: redesenhar tela com itens
				Toast.makeText(getApplication(),
						"Selecionados produtos da seção " + pos,
						Toast.LENGTH_LONG).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		produto = (EditText) findViewById(R.id.produto);
		incluir = (Button) findViewById(R.id.incluir);
		incluir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				incluirProduto();
			}
		});
		produto.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					incluirProduto();
					return true;
				}
				return false;
			}
		});
	}
	
	private void incluirProduto() {
		Toast.makeText(getApplication(),
				"Incluído produto " + produto.getText(),
				Toast.LENGTH_LONG).show();
		produto.setText("");
//		produto.setFocus();
	}

}