package com.application.base.mongo.spring.factory;

import com.mongodb.*;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 实例化mongo集合
 * @author 孤狼
 */
public class MongoFactory extends AbstractFactoryBean<Mongo> {

	private String[] serverStrings;
	private MongoOptions mongoOptions;
	private boolean readSecondary = false;

	private WriteConcern writeConcern = WriteConcern.SAFE;
	public Mongo mongo;

	@Override
	public Class<?> getObjectType() {
		return Mongo.class;
	}

	@Override
	protected Mongo createInstance() throws Exception {
		if (this.mongo == null) {
			this.mongo = initMongo();
			if (this.readSecondary) {
				this.mongo.setReadPreference(ReadPreference.secondaryPreferred());
			}
			this.mongo.setWriteConcern(this.writeConcern);
		}
		return this.mongo;
	}

	private Mongo initMongo() throws Exception {
		Mongo mongo = null;
		List serverList = getServerList();
		if (serverList.size() == 0) {
			mongo = new Mongo();
		}
		else if (serverList.size() == 1) {
			if (this.mongoOptions != null) {
				mongo = new Mongo((ServerAddress) serverList.get(0), this.mongoOptions);
			} else {
				mongo = new Mongo((ServerAddress) serverList.get(0));
			}
		}
		else if (this.mongoOptions != null) {
			mongo = new Mongo(serverList, this.mongoOptions);
		}
		else {
			mongo = new Mongo(serverList);
		}

		return mongo;
	}

	private List<ServerAddress> getServerList() throws Exception {
		List serverList = new ArrayList();
		try {
			for (String serverString : this.serverStrings) {
				String[] temp = serverString.split(":");
				String host = temp[0];
				if (temp.length > 2) {
					throw new IllegalArgumentException("Invalid server address string: " + serverString);
				}
				if (temp.length == 2) {
					serverList.add(new ServerAddress(host, Integer.parseInt(temp[1])));
				}
				else {
					serverList.add(new ServerAddress(host));
				}
			}
			return serverList;
		}
		catch (Exception e) {
			throw new Exception("Error while converting serverString to ServerAddressList", e);
		}
	}

	public String[] getServerStrings() {
		return this.serverStrings;
	}

	public void setServerStrings(String[] serverStrings) {
		this.serverStrings = serverStrings;
	}

	public MongoOptions getMongoOptions() {
		return this.mongoOptions;
	}

	public void setMongoOptions(MongoOptions mongoOptions) {
		this.mongoOptions = mongoOptions;
	}

	public boolean isReadSecondary() {
		return this.readSecondary;
	}

	public void setReadSecondary(boolean readSecondary) {
		this.readSecondary = readSecondary;
	}

	public WriteConcern getWriteConcern() {
		return this.writeConcern;
	}

	public void setWriteConcern(WriteConcern writeConcern) {
		this.writeConcern = writeConcern;
	}
}