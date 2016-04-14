package kr.ac.hanyang.tosca2camp.datatypes.capabilities;

public class AttachmentCapability extends RootCapability{
	
	public static class Builder extends RootCapability.Builder{
		
		public Builder(String desc){
			super("attachment",desc);
		}
		
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
