package mediaproject.its.service;

import lombok.RequiredArgsConstructor;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.entity.Comment;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.entity.User;
import mediaproject.its.domain.repository.CommentRepository;
import mediaproject.its.domain.repository.PostRepository;
import mediaproject.its.domain.repository.UserRepository;
import mediaproject.its.response.error.CommonErrorCode;
import mediaproject.its.response.exception.CustomRestApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void saveComment(CommentDto commentDto, int postId, String username){

        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new CustomRestApiException(CommonErrorCode.NOT_FOUND.getMessage(),CommonErrorCode.NOT_FOUND);
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomRestApiException(CommonErrorCode.NOT_FOUND.getMessage(),CommonErrorCode.NOT_FOUND));

        Comment newComment = Comment.builder()
                .user(user)
                .post(post)
                .content(commentDto.getContent())
                .build();

        commentRepository.save(newComment);
    }

    @Transactional
    public void editComment(){

    }

    @Transactional
    public void deleteComment(){

    }
}