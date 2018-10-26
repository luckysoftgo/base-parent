package ${baseFilePackage}.entity.${entityPackage};

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 实体bean
 * 
 * @author bruce
 *
 */
@Document(collection="${tableName}")
public class ${className} implements Serializable {

	${feilds}
	
}

