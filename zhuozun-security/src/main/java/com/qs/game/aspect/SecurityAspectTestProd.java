package com.qs.game.aspect;

import com.qs.game.annotation.IgnoreSecurity;
import com.qs.game.auth.TokenManager;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.constant.Env;
import com.qs.game.enum0.Code;
import com.qs.game.utils.Constants;
import com.qs.game.utils.ResponseType;
import com.qs.game.utils.WebContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by zun.wei on 2018/8/14 12:37.
 * Description: 安全检查切面(是否登录检查)   通过验证Token维持登录状态
 */
@Slf4j
@Aspect
@Profile({Env.TEST, Env.PROD})
@Configuration
public class SecurityAspectTestProd {

    @Resource(name = "redisTokenManager")
    private TokenManager tokenManager;


    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.PostMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.GetMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.PutMapping)"
            + " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)"
    )
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        // 从切点上获取目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        log.info("methodSignature : " + methodSignature);
        Method method = methodSignature.getMethod();
        log.info("Method : " + method.getName() + " : " + method.isAnnotationPresent(IgnoreSecurity.class));
        // 若目标方法忽略了安全性检查,则直接调用目标方法
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return pjp.proceed();
        }

        // 从 request header 中获取当前 token
        String token = WebContextUtil.getRequest().getHeader(Constants.DEFAULT_TOKEN_NAME);
        // 检查 token 有效性
        if (!tokenManager.checkToken(token)) {
            String message = String.format("token [%s] is invalid", token);
            log.info("message : " + message);
            //throw new TokenException(message);
            String simpleName = methodSignature.getReturnType().getSimpleName();
            boolean re =  ResponseType.ResponseEntity.isType(simpleName);
            if (re) return ResponseEntity.ok(BaseResult.getBuilder().setCode(Code.ERROR_1000)
                    .setMessage("Token is invaild").setSuccess(false).build());

            /*boolean mav =  ResponseType.ModelAndView.isType(simpleName);
            if (mav){
                Class<?> clzz = Class.forName(methodSignature.getReturnType().getName());
                return clzz.newInstance();
            }*/
            return null;
        }

        // 调用目标方法
        return pjp.proceed();
    }



}
