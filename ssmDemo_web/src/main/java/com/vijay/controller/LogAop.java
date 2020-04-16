package com.vijay.controller;

import com.vijay.domain.SysLog;
import com.vijay.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    private Date visitTime;//访问时间
    private Class executionClass;//具体访问的类
    private Method executionMethod;//访问的方法

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysLogService sysLogService;

    //前置通知
    @Before("execution(* com.vijay.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime = new Date();//访问时间
        executionClass = jp.getTarget().getClass();//具体访问的类
        String executionMethodName = jp.getSignature().getName();//获取访问的方法名
        Object[] args = jp.getArgs();//获取所执行方法0
        // 的参数
        if(args == null || args.length == 0) {
            executionMethod = executionClass.getMethod(executionMethodName);//只能获取无参的方法
        }else{
            Class[] argsClass = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argsClass[i] = args[i].getClass();
            }
            executionMethod = executionClass.getMethod(executionMethodName, argsClass);//获取带参的方法
        }
    }


    //后置通知
    @After("execution(* com.vijay.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        long executionTime = new Date().getTime() - visitTime.getTime();//访问时长
        if(executionClass != null && executionMethod != null && executionClass != LogAop.class && executionClass != SysLogController.class){
            //获取用户名
            SecurityContext context = SecurityContextHolder.getContext();
            User user = (User) context.getAuthentication().getPrincipal();
            String username = user.getUsername();//获取用户名

            //获取访问的ip地址
            String ip = request.getRemoteAddr();

            //获取访问的url路径，使用反射
            //获取控制器类上的路径@RequestMapping("/role")
            RequestMapping classAnnotation = (RequestMapping) executionClass.getAnnotation(RequestMapping.class);
            if(classAnnotation != null) {
                String[] classValues = classAnnotation.value();
                //获取方法上的url路径
                RequestMapping methodAnnotation = executionMethod.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null) {
                    String[] methodValues = methodAnnotation.value();
                    String url = classValues[0] + methodValues[0];
                    //获取操作的方法全名
                    String className = executionClass.getName();
                    String methodName = executionMethod.getName();
                    String method = "[类名]" + className + "[方法名]" + methodName;

                    //封装SysLog日志对象
                    SysLog sysLog = new SysLog();
                    sysLog.setVisitTime(visitTime);
                    sysLog.setUsername(username);
                    sysLog.setIp(ip);
                    sysLog.setUrl(url);
                    sysLog.setExecutionTime(executionTime);
                    sysLog.setMethod(method);

                    //调用service完成日志存储
                    sysLogService.saveLog(sysLog);
                }
            }
        }
    }

    //
}
