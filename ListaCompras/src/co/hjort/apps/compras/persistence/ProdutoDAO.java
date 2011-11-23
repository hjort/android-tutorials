package co.hjort.apps.compras.persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import co.hjort.apps.compras.domain.Produto;

public class ProdutoDAO {

	private static final String DATABASE_TABLE = "produtos";

	private static ProdutoDAO instance;
	private static SQLiteDatabase db;

	public static ProdutoDAO getInstance(Context context) {
		synchronized (ProdutoDAO.class) {
			if (instance == null) {
				instance = new ProdutoDAO();
				db = new DatabaseManager(context).open();
			}
		}
		return instance;
	}

	private ProdutoDAO() {
	}

	public List<Produto> buscarPorSecao(int secao) {
		List<Produto> lista = new ArrayList<Produto>();
		Cursor cursor = db.query(DATABASE_TABLE, new String[] { "_id", "nome",
				"marcado" }, "secao = " + secao, null, null, null, null);
		while (cursor.moveToNext()) {
			Produto produto = new Produto();
			produto.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
			produto.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
			produto.setMarcado(cursor.getInt(cursor.getColumnIndexOrThrow("marcado")) > 0);
			produto.setSecao(secao);
			lista.add(produto);
		}
		cursor.close();
		return lista;
	}

	public int incluir(int secao, String nome) {
		ContentValues values = new ContentValues();
		values.put("nome", nome);
		values.put("secao", secao);
		values.put("marcado", 0);
		return (int) db.insert(DATABASE_TABLE, null, values);
	}

	public boolean excluir(int id) {
		return (db.delete(DATABASE_TABLE, "_id = " + id, null) > 0);
	}

	public boolean marcar(int id, boolean valor) {
		ContentValues values = new ContentValues();
		values.put("marcado", valor ? 1 : 0);
		return (db.update(DATABASE_TABLE, values, "_id = " + id, null) > 0);
	}

}
