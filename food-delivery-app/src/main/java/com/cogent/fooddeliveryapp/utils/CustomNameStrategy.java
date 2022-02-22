package com.cogent.fooddeliveryapp.utils;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * modify tables name
 * @author Dan
 *
 */
public class CustomNameStrategy extends PhysicalNamingStrategyStandardImpl {
	
	/**
	 * all tables name add "_TBL"
	 */
	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		// TODO Auto-generated method stub
		String newName = name.getText().concat("_TBL");
		
		return Identifier.toIdentifier(newName);
	}
}
