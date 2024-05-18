package mediaproject.its.domain.dto;

public interface PostInterface {
    Integer getId();
    String getTitle();
    String getUser_name();
    Integer getView_count();
    Integer getLikes_count();
    Integer getComment_count();
    String getHiring_type();
    String getPosition_type();
    String getProcess_type();
    String getRecruiting_type();
    String getTechstack_type();
}
