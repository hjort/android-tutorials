package co.hjort.apps.compras;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class ListaCompras extends Activity {

	private Spinner secao;
	private EditText produto;
	private Button incluir;
	
	private LinearLayout itens;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		secao = (Spinner) findViewById(R.id.secao);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.secoes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		secao.setAdapter(adapter);
		secao.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				popularItens(pos);
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
		
		itens = (LinearLayout) findViewById(R.id.itens);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	private void popularItens(int secao) {
		itens.removeAllViews();
		
		for (int ii = 0; ii < 5; ii++) {
			View itemView = inflater.inflate(R.layout.item, null);
			CheckBox check = (CheckBox) itemView.findViewById(R.id.item01);
			Button button = (Button) itemView.findViewById(R.id.remove01);
			check.setId(ii);
			button.setId(ii);
			check.setText("Produto " + ((secao + 1) * 100 + (ii + 1)));
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					// TODO: mostrar diálogo de confirmação
					itens.removeViewAt(v.getId());
					
					Toast.makeText(getApplication(), String.valueOf(v.getId()),
							Toast.LENGTH_LONG).show();
				}
			});
			itens.addView(itemView);
		}
	}
	
	private void incluirProduto() {
		View itemView = inflater.inflate(R.layout.item, null);
		CheckBox check = (CheckBox) itemView.findViewById(R.id.item01);
		Button button = (Button) itemView.findViewById(R.id.remove01);
		int id = (int) (Math.random() * 1e5);
		String nome = produto.getText().toString();
		check.setId(id);
		button.setId(id);
		check.setText(nome);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplication(), String.valueOf(v.getId()),
						Toast.LENGTH_LONG).show();
			}
		});
		itens.addView(itemView);
		
		Toast.makeText(getApplication(),
				"Incluído produto " + produto.getText(),
				Toast.LENGTH_LONG).show();
		produto.setText("");
//		produto.setFocus();
	}

}