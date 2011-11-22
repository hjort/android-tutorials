package co.hjort.apps.compras;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProdutoDAO {

	private static final String DATABASE_TABLE = "produtos";

	private static ProdutoDAO instance;
	private static SQLiteDatabase db;

	public static ProdutoDAO getInstance(Context context) {
		if (instance == null) {
			synchronized (ProdutoDAO.class) {
				if (instance == null) {
					instance = new ProdutoDAO();
					db = new DatabaseManager(context).open();
				}
			}
		}
		return instance;
	}

	public List<Produto> buscarPorSecao(int secao) {
		List<Produto> lista = new ArrayList<Produto>();
		Cursor cursor = db.query(DATABASE_TABLE, new String[] { "_id", "nome",
				"marcado" }, "secao = " + secao, null, null, null, null);
		while (cursor.moveToNext()) {
			Produto produto = new Produto();
			produto.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
			produto.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
			//prod.setMarcado(cursor.getBoolean( ???
			produto.setSecao(secao);
			lista.add(produto);
		}
		cursor.close();
		return lista;
	}

	public long incluir(int secao, String nome) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("nome", nome);
		initialValues.put("secao", secao);
		initialValues.put("marcado", false);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean excluir(long id) {
		return (db.delete(DATABASE_TABLE, "_id = " + id, null) > 0);
	}

	public boolean marcar(long id, boolean valor) {
		ContentValues values = new ContentValues();
		values.put("marcado", valor);
		return (db.update(DATABASE_TABLE, values, "_id = " + id, null) > 0);
	}

}
