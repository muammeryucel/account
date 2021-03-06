package com.myucel.account.postgresql;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.type.descriptor.sql.BinaryTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

public class AxonPostgreSQLDialect extends PostgreSQL95Dialect {

	public AxonPostgreSQLDialect() {
	    super();
	    this.registerColumnType(Types.BLOB, "BYTEA");
	  }

	  @Override
	  public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
	    if (sqlTypeDescriptor.getSqlType() == java.sql.Types.BLOB) {
	      return BinaryTypeDescriptor.INSTANCE;
	    }
	    return super.remapSqlTypeDescriptor(sqlTypeDescriptor);
	  }

//	@Override
//	public boolean useInputStreamToInsertBlob() {
//		return false;
//	}
	
}
