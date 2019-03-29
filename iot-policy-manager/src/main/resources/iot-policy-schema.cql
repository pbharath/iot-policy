// Create Keyspace
CREATE KEYSPACE IF NOT EXISTS IotPolicySpace WITH replication = { 'class':'SimpleStrategy', 'replication_factor':1};

// Drop existing tables
DROP TABLE IF EXISTS IotPolicySpace.Content_By_Age_Policy_Rule;
DROP TABLE IF EXISTS IotPolicySpace.Tower_Carrier_Platform_Policy_Rule;

// Create tables
CREATE TABLE IF NOT EXISTS IotPolicySpace.Content_By_Age_Policy_Rule(id UUID, start_age INT, end_age INT, content_types set<TEXT>, created_by TEXT, created_timeStamp TIMESTAMP, modified_by TEXT, modified_timeStamp TIMESTAMP, PRIMARY KEY(id));
CREATE TABLE IF NOT EXISTS IotPolicySpace.Tower_Carrier_Platform_Policy_Rule(id UUID, tower_name TEXT, carrier_name TEXT, platform_name TEXT, is_active BOOLEAN, created_by TEXT, created_timeStamp TIMESTAMP, modified_by TEXT, modified_timeStamp TIMESTAMP, PRIMARY KEY((tower_name), carrier_name, platform_name));