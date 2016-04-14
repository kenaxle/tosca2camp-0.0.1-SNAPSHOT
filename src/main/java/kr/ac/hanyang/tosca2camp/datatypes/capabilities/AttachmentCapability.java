package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class AttachmentCapability<V> extends RootCapability<V>{
	
	public static class Builder extends RootCapability.Builder{
		
		public Builder(String desc){
			super("attachment",desc);
		}
		
		public Builder(String type, String desc){
			super(type,desc);
		}
		
		@SuppressWarnings("rawtypes")
		public AttachmentCapability build(){
			return new AttachmentCapability(this);
		}
	}
	
	protected AttachmentCapability(Builder builder){
		super(builder);
	}
	
	public String toString(){
		return super.toString();
	}
}
