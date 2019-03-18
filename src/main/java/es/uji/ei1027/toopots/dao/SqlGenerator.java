package es.uji.ei1027.toopots.dao;

public class SqlGenerator {
	
	public static String generateAddSql(String table, int numParams){
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		sb.append(table);
		sb.append(" VALUES ");
		sb.append(generateStringParamas(numParams));
		sb.append(";");
		return sb.toString();
	}
	
	private static String generateStringParamas(int numParams){
		StringBuffer sb = new StringBuffer();
		sb.append("(?");
		for (int i = 1; i < numParams; i++){
			sb.append(", ?");
		}
		sb.append(")");
		return sb.toString();
	}

}
