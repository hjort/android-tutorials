package co.hjort.apps.compras;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
	private RemoverItemListener removerItemListener;

	private ProdutoDAO dao;
	
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
					
					String nome = produto.getText().toString();
					if (nome != null && !"".equals(nome)) {
						incluirProduto();
					}
					return true;
				}
				return false;
			}
		});
		
		itens = (LinearLayout) findViewById(R.id.itens);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		removerItemListener = new RemoverItemListener(this);
		
		dao = ProdutoDAO.getInstance(this);
	}
	
	private void popularItens(int secao) {
		itens.removeAllViews();
		
		List<Produto> lista = dao.buscarPorSecao(secao);
		for (Produto produto : lista) {
			View itemView = inflater.inflate(R.layout.item, null);
			CheckBox check = (CheckBox) itemView.findViewById(R.id.item01);
			Button button = (Button) itemView.findViewById(R.id.remove01);
			int id = (int) produto.getId();
			check.setId(id);
			button.setId(id);
			check.setText(produto.getNome());
			button.setOnClickListener(removerItemListener);
			itens.addView(itemView);
		}
		
		/*
		for (int ii = 0; ii < 5; ii++) {
			View itemView = inflater.inflate(R.layout.item, null);
			CheckBox check = (CheckBox) itemView.findViewById(R.id.item01);
			Button button = (Button) itemView.findViewById(R.id.remove01);
			check.setId(ii);
			button.setId(ii);
			check.setText("Produto " + ((secao + 1) * 100 + (ii + 1)));
			button.setOnClickListener(removerItemListener);
			itens.addView(itemView);
		}
		*/
	}
	
	private class RemoverItemListener implements OnClickListener {

		@SuppressWarnings("unused")
		private Context context;

		public RemoverItemListener(Context context) {
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			ConfirmarRemoverItemListener confirmar = new ConfirmarRemoverItemListener(v);
			confirmar.onClick(null, 0);
			/*
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("Deseja mesmo remover?")
					.setCancelable(false)
					.setPositiveButton("Sim", confirmar)
					.setNegativeButton("Não",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
			*/
		}
	}
	
	private class ConfirmarRemoverItemListener implements DialogInterface.OnClickListener {

		private View view;
		
		public ConfirmarRemoverItemListener(View view) {
			this.view = view;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			itens.removeViewAt(view.getId());
			Toast.makeText(getApplication(), String.valueOf(view.getId()),
					Toast.LENGTH_LONG).show();
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