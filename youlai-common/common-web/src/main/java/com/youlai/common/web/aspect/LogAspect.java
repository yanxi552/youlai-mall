package com.youlai.common.web.aspect;

import cn.hutool.json.JSONUtil;
import com.youlai.common.web.pojo.domain.OptLog;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HXR
 * @date 2021-03-01 16:47
 */
@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void Log() {
    }

    @Around("Log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 时间统计
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime =  System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        // 获取方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String description = signature.getMethod().getAnnotation(ApiOperation.class).value();

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestIp=request.getRemoteUser();
        String requestUrl=request.getRequestURL().toString();
        String requestMethod=request.getMethod();

        OptLog optLog = new OptLog();
        optLog.setStartTime(startTime);
        optLog.setElapsedTime(elapsedTime);
        optLog.setDescription(description );
        optLog.setRequestIp(requestIp);
        optLog.setRequestUrl(requestUrl);
        optLog.setRequestMethod(requestMethod);
        optLog.setResult(result);
        log.info(JSONUtil.toJsonStr(optLog));
        return result;
    }
}
