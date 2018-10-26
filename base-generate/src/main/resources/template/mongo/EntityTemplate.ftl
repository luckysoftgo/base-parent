package ${baseFilePackage}.entity.${entityPackage};

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 实体bean
 * 
 * @author 孤狼
 *
 */
@Document(collection="${tableName}")
public class ${className} implements Serializable {

	${feilds}
	
}

