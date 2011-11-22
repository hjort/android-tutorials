package co.hjort.apps.compras;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager {

	private static final String DATABASE_NAME = "data";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table produtos ("
			+ "_id integer primary key autoincrement, "
			+ "nome text not null, " + "secao integer not null, "
			+ "marcado boolean not null);";

	private final Context context;

	private SQLiteDatabase database;
	private DatabaseHelper helper;

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param context
	 *            the Context within which to work
	 */
	public DatabaseManager(Context context) {
		this.context = context;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		private final Context context;
		
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
			
			ProdutoDAO dao = ProdutoDAO.getInstance(this.context);
			for (int ii = 1; ii < 5; ii++) {
				dao.incluir(ii, "produto " + ii);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(this.getClass().getSimpleName(),
					"Upgrading database from version " + oldVersion + " to "
							+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS produtos");
			onCreate(db);
		}
	}

	/**
	 * Open the notes database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */
	public SQLiteDatabase open() throws SQLException {
		helper = new DatabaseHelper(context);
		database = helper.getWritableDatabase();
		return database;
	}

	public void close() {
		database.close();
		helper.close();
	}

}
