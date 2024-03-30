package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.CommentDto;
import mediaproject.its.domain.dto.PostDto;
import mediaproject.its.domain.dto.request.PostRequestDto;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.domain.entity.Post;
import mediaproject.its.domain.dto.request.UpdatePostRequestDto;
import mediaproject.its.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // todo : 설계원칙4에 따라 좋은 설계를 위한 절충이 필요하다?? (컨트롤러에서 일정량의 로직 넣은 것에 대하여. for 서비스단에서 Post타입 리턴 일관성 유지)
    // todo : 생각해보고 수정하려면 해보자 그냥 서비스단에서 리턴타입을 변경하는 편이 그나마 더 나은 방법인가??
    @GetMapping("/its/posts")
    public ResponseEntity<?> getPost(){
        List<Post> posts = postService.getAllPost();
        List<PostDto.Response> postsResponseDto = new ArrayList<>();

        for(Post p : posts){
            PostDto.Response postsDto = PostDto.Response.builder()
                    .postId(p.getId())
                    .title(p.getTitle())
                    .content(p.getContent())
                    .username(p.getUser().getUsername())
                    .comments(p.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList()))
                    .build();
            postsResponseDto.add(postsDto);
        }
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 조회 성공")
                .data(postsResponseDto)
                .build()
        );
    }

    @GetMapping("/its/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id){
        Post post = postService.getPostById(id);
        PostDto.Response postResponseDto = new PostDto.Response(post);

        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 조회(단건) 성공")
                .data(postResponseDto)
                .build());
    }

    // todo : 게시글 작성 시 코멘트가 null이기 때문에, post작성 자체는 되지만 rest api error 발생. comment 초기값을 지정해주면 되나??
    @PostMapping("/its/api/post")
    public ResponseEntity<?> postPost(@RequestBody PostDto.Request postRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        String username = customUserDetails.getUser().getUsername();
        Post newPost = postService.postPost(postRequest,username);

        PostDto.Response postResponseDto = new PostDto.Response(newPost);

        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("포스트 등록 성공")
                .data(postResponseDto)
                .build());
    }

    @PutMapping("/its/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable int id, @RequestBody UpdatePostRequestDto updatePostRequestDto){
        Post updatedPost = postService.updatePost(id, updatePostRequestDto);
        PostDto.Response updatedPostResponseDto = new PostDto.Response(updatedPost);

        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 수정 성공")
                .data(updatedPostResponseDto)
                .build());
    }

    @DeleteMapping("/its/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id){
        postService.deletePost(id);
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .message("포스트 삭제 성공")
                .data(null)
                .build());
    }

}
