package org.footoo.sns;

public interface PublishCompleteListener {
	public static final int PUBLISH_SUCCESS = 0;
	public static final int PUBLISH_FAILED = 1;
	public static final int REPEAT_CONTENT = 2;
	
	public abstract void publishComplete(int flag);
}
