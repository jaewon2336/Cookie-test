package site.metacoding.testproject.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.testproject.service.UserService;
import site.metacoding.testproject.util.UtilValid;
import site.metacoding.testproject.web.dto.user.JoinReqDto;
import site.metacoding.testproject.web.dto.user.RememberReqDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpServletResponse response;
    private final HttpServletRequest request;

    @GetMapping("/")
    public String main() {
        return "/user/main";
    }

    @PostMapping("/remember")
    public @ResponseBody ResponseEntity<?> remember(@RequestBody RememberReqDto rememberReqDto) {

        String remember = rememberReqDto.getRemember();
        String username = rememberReqDto.getUsername();

        if (remember.equals("on")) {
            System.out.println(remember); // on
            response.addHeader("Set-Cookie", "remember=" + username + ";path=/");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그인 페이지
    @GetMapping("/login-form")
    public String loginForm(Model model) {
        Cookie[] cookies = request.getCookies();

        // 무조건 2번은 아닐텐데 일단은 이렇게 해뒀음
        String name = cookies[2].getName();
        String value = cookies[2].getValue();

        if (name.equals("remember")) {
            model.addAttribute("remember", value);
        }

        return "/user/loginForm";
    }

    // 회원가입 페이지
    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/joinForm";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, BindingResult bindingResult) {
        UtilValid.요청에러처리(bindingResult);
        userService.회원가입(joinReqDto.toEntity());
        return "redirect:/login-form";
    }

}
