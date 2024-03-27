package mediaproject.its.auth.controller;

import lombok.RequiredArgsConstructor;
import mediaproject.its.response.dto.CommonResponseDto;
import mediaproject.its.domain.dto.JoinDto;
import mediaproject.its.auth.service.JoinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<?> joinUser(JoinDto joinDto) {
        boolean result = joinService.joinUser(joinDto);
        if (!result) {
            return ResponseEntity.ok().body(CommonResponseDto.builder()
                    .statusCode(HttpStatus.BAD_REQUEST)
                    .message("회원가입 실패 : 이미 존재하는 이름입니다")
                    .data(joinDto)
                    .build());
        }

        System.out.println("회원가입 성공이 여기 로그");
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("회원가입 성공")
                .data(joinDto)
                .build());

    }

    @PostMapping("/join/admin")
    public ResponseEntity<?> joinAdmin(JoinDto joinDto){
        boolean result = joinService.joinAdmin(joinDto);
        if(!result){
            if(!result){
                return ResponseEntity.ok().body(CommonResponseDto.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .message("회원가입 실패 : 이미 존재하는 이름입니다")
                        .data(joinDto)
                        .build());
            }
        }
        return ResponseEntity.ok().body(CommonResponseDto.builder()
                .statusCode(HttpStatus.CREATED)
                .message("회원가입 성공")
                .data(joinDto)
                .build());
    }

}
