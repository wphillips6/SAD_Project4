package proj4.common;

public class Administrator {

	private String m_uID;
	private String m_name;
	
	public Administrator(){
		
	}
	
	public Administrator(String uID, String name){
		this.m_uID = uID;
		this.m_name = name;
	}
	
	public String getUID(){
		return m_uID;
	}
	
	public void setUID(String id){
		this.m_uID = id;
	}
	
	public String getName(){
		return m_name;
	}
}
