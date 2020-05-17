package cl.pipecm.forum.user.entity;

public enum Role {
	USER(0),
	MODERATOR(1),
	ADMIN(2);
	
	private final int value;
	
	private Role(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
