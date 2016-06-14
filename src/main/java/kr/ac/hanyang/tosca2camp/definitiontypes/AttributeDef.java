package kr.ac.hanyang.tosca2camp.definitiontypes;

public class AttributeDef implements Cloneable{

	private String name;
	private String type;
	private String description; // description are treated as their own type but for now they will be string
	private Object propertyValue;
	private Object defaultVal;  //TODO
	private String status; 
	private EntrySchemaDef entry_schema;
	
	public static class Builder{
		private String name;
		private String type;
		private String description; // description are treated as their own type but for now they will be string
		private Object propertyValue;
		private Object defaultVal;  //TODO
		private String status; 
		private EntrySchemaDef entry_schema;
		
		public Builder(String name){
			this.name = name;
			//this.type = new DataTypeDef.Builder(type).build();
			//this.defaultVal = "";
		}
		
		public Builder type(String type) {
			this.type = type;
			return this;
		}

		public Builder description(String description){
			this.description = description;
			return this;
		}
		
		public Builder defaultVal(Object defaultVal){
			this.defaultVal = defaultVal;
			return this;
		}
		
		public Builder status(String status){
			this.status = status;
			return this;
		}
		
		public Builder entry_schema(EntrySchemaDef entry_schema){
			this.entry_schema = entry_schema;
			return this;
		}
		
		public AttributeDef build(){
			return new AttributeDef(this);
		}
	}
	
	public Object clone(){
		try{
			AttributeDef toReturn = (AttributeDef) super.clone();
			//toReturn.propertyValue = (DataTypeDef) propertyValue.clone();
			//toReturn.defaultVal = (DataTypeDef) defaultVal.clone();
			if (entry_schema != null) toReturn.entry_schema = (EntrySchemaDef) entry_schema.clone();
			return toReturn;
		}catch(CloneNotSupportedException e){
			return null;
		}	
	}
	
	
//	public static AttributeDef clone(AttributeDef origAttr){
//		AttributeDef.Builder copyBuilder = new AttributeDef.Builder(origAttr.name, origAttr.type.getTypeName());
//		if (origAttr.entry_schema != null)
//			copyBuilder.entry_schema(EntrySchemaDef.clone(origAttr.entry_schema));
//		return copyBuilder.description(origAttr.description)
//				   		  .defaultVal(origAttr.defaultVal)
//				   		  .status(origAttr.status)
//				   		  .build();
//	}
	
	private AttributeDef(Builder builder){
		this.name = builder.name;
		this.type = builder.type;
		this.description = builder.description;
		this.defaultVal = builder.defaultVal;
		this.status = builder.status;
		this.entry_schema = builder.entry_schema;
	}
	
	public Builder getBuilder(String name){ 
		Builder builder = new Builder(name);
		builder.type = type;
		builder.description = this.description;
		builder.defaultVal = this.defaultVal;
		builder.status = this.status;
		builder.entry_schema = this.entry_schema;
		return builder;
	}
	
	public String getName(){return name;}
	public String getType(){return this.type;}	
	public String getDescription(){return description;}
	public Object getDefaultVal(){return defaultVal;}
	

//	public boolean validate(AttributeAs attribute){
//		boolean valid = false;
//		if (attribute != null){
//			if (name.equals(attribute.getName())){
//				//the type is valid
//				if (type.equals(attribute.getValue().getClass().getName())){
//					valid = true;
//				}
//			}
//		}
//		return valid;
//	}	
	
	public String toString(){
		return "name: "+name+"\n"+
			   "type: "+type+"\n";
	}
	
}

