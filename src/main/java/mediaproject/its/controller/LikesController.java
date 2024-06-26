package mediaproject.its.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.auth.CustomUserDetails;
import mediaproject.its.domain.dto.LikesDto;
import mediaproject.its.domain.entity.Likes;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.service.LikesService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/its/api/likes/{postId}")
    public CommonResponseDto<?> addPostLikes(@PathVariable int postId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        // int userId = customUserDetails.getUser().getId();
        String username = customUserDetails.getUser().getUsername();

        // todo : 왜 userId 무조건 0 나오지?
        // System.out.println("============ user ID : " + userId);


        likesService.addPostLikes(username, postId);

        LikesDto.Response likesResponseDto = new LikesDto.Response(username, postId, 0);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .data(likesResponseDto)
                .message("게시글 좋아요 등록 성공")
                .build();
    }

    @DeleteMapping("/its/api/likes/{likesId}")
    public CommonResponseDto<?> deletePostLikes(@PathVariable int likesId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String username = customUserDetails.getUser().getUsername();

        Likes deletedLikes = likesService.deletePostLikes(username, likesId);
        LikesDto.Response likesResponseDto = new LikesDto.Response(deletedLikes);

        return CommonResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .data(likesResponseDto)
                .message("게시글 좋아요 삭제 성공")
                .build();
    }
}
