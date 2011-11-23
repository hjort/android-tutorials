package co.hjort.apps.compras.view;

import java.util.List;

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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import co.hjort.apps.compras.domain.Produto;
import co.hjort.apps.compras.persistence.ProdutoDAO;

public class ListaCompras extends Activity {

	private Spinner spnSecao;
	private EditText txtProduto;
	private Button btnIncluir;
	
	private LinearLayout itens;
	private LayoutInflater inflater;
	
	private RemoverItemListener removerItemListener;
	private CheckItemListener checkItemListener;

	private ProdutoDAO dao;
	
	private int codigoSecao;
	private int qtdeProdutos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		spnSecao = (Spinner) findViewById(R.id.secao);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.secoes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnSecao.setAdapter(adapter);
		spnSecao.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				popularItensSecao(pos + 1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		txtProduto = (EditText) findViewById(R.id.produto);
		btnIncluir = (Button) findViewById(R.id.incluir);
		btnIncluir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				incluirProduto();
			}
		});
		txtProduto.setOnKeyListener(new OnKeyListener() {
			
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
		removerItemListener = new RemoverItemListener();
		checkItemListener = new CheckItemListener();
		
		dao = ProdutoDAO.getInstance(this);
	}
	
	private void popularItensSecao(int secao) {
		codigoSecao = secao;
		qtdeProdutos = 0;
		
		itens.removeAllViews();
		
		List<Produto> lista = dao.buscarPorSecao(secao);
		for (Produto produto : lista) {
			View viewItem = inflater.inflate(R.layout.item, null);
			CheckBox chkProduto = (CheckBox) viewItem.findViewById(R.id.item01);
			Button btnRemover = (Button) viewItem.findViewById(R.id.remove01);
			int id = (int) produto.getId();
			
			viewItem.setId(id);
			
			chkProduto.setId(id);
			chkProduto.setText(produto.getNome());
			chkProduto.setChecked(produto.isMarcado());
			chkProduto.setOnCheckedChangeListener(checkItemListener);

			btnRemover.setId(id);
			btnRemover.setOnClickListener(removerItemListener);
			
			itens.addView(viewItem);
			qtdeProdutos++;
		}
		
		final String msg = getString(R.string.quantidade_produtos, qtdeProdutos);
		Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show();
	}
	
	private class RemoverItemListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			int id = view.getId();
			if (dao.excluir(id)) {
				itens.removeView(itens.findViewById(id));
				Toast.makeText(getApplication(), R.string.produto_removido,
						Toast.LENGTH_SHORT).show();
				qtdeProdutos--;
			}
		}
	}
	
	private class CheckItemListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			dao.marcar(buttonView.getId(), isChecked);
		}
	}
	
	private void incluirProduto() {
		String nome = txtProduto.getText().toString();
		
		if (nome == null || "".equals(nome))
			return;
		
		View viewItem = inflater.inflate(R.layout.item, null);
		CheckBox chkProduto = (CheckBox) viewItem.findViewById(R.id.item01);
		Button btnRemover = (Button) viewItem.findViewById(R.id.remove01);
		
		int id = dao.incluir(codigoSecao, nome);

		viewItem.setId(id);
		
		chkProduto.setId(id);
		chkProduto.setText(nome);
		chkProduto.setOnCheckedChangeListener(checkItemListener);

		btnRemover.setId(id);
		btnRemover.setOnClickListener(removerItemListener);

		itens.addView(viewItem);
		
		Toast.makeText(getApplication(), R.string.incluido_produto, Toast.LENGTH_SHORT).show();
		txtProduto.setText("");
		txtProduto.requestFocus();
	}

}