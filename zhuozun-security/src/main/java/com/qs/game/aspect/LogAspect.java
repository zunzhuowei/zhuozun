package com.qs.game.aspect;

import com.alibaba.fastjson.JSON;
import com.qs.game.constant.IntConst;
import com.qs.game.entity.OperationLog;
import com.qs.game.service.IRedisService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 前置通知（@Before）：在某连接点（join point）之前执行的通知，但这个通知不能阻止连接点前的执行（除非它抛出一个异常）
 * 返回后通知（@AfterReturning）：在某连接点（join point）正常完成后执行的通知：例如，一个方法没有抛出任何异常，正常返回
 * 抛出异常后通知（@AfterThrowing）：方法抛出异常退出时执行的通知
 * 后通知（@After）：当某连接点退出的时候执行的通知（不论是正常返回还是异常退出） 环绕通知（@Around）：包围一个连接点（join
 * point）的通知，如方法调用。这是最强大的一种通知类型，环绕通知可以在方法调用前后完成自定义的行为，
 * 它也会选择是否继续执行连接点或直接返回它们自己的返回值或抛出异常来结束执行
 *
 * @Description: 日志记录，添加、删除、修改方法AOP
 */
@Component
@Aspect
public class LogAspect {

    @Autowired
    private IRedisService redisService;

    /**
     * 添加业务逻辑方法切入点 定义在controller包里的以add为前缀的方法的执行
     */
    @Pointcut("execution(* com.qs.*.controller.*.add*(..)) "
            + "|| execution(* com.qs.*.controller.*.save*(..)) "
            + "|| execution(* com.qs.*.controller.*.insert*(..)) "
            + "|| execution(* com.qs.*.controller.*.push*(..)) "
    )
    public void insertServiceCall() {
    }

    /**
     * 修改业务逻辑方法切入点 定义在controller包里的以update为前缀的方法的执行
     */
    @Pointcut("execution(* com.qs.*.controller.*.upd*(..)) "
            + "|| execution(* com.qs.*.controller.*.reset*(..)) "
            + "|| execution(* com.qs.*.controller.*.modif*(..)) "
            + "|| execution(* com.qs.*.controller.*.revise*(..)) "
            + "|| execution(* com.qs.*.controller.*.alter*(..))")
    public void updateServiceCall() {
    }

    /**
     * 删除业务逻辑方法切入点 定义在controller包里以delete为前缀的方法的执行
     */
    @Pointcut("execution(* com.qs.*.controller.*.del*(..)) "
            + "|| execution(* com.qs.*.controller.*.remove*(..)) "
            + "|| execution(* com.qs.*.controller.*.cut*(..)) "
    )
    public void deleteServiceCall() {
    }

    /**
     * 授权业务逻辑方法切入点 定义在controller包里以delete为前缀的方法的执行
     */
    @Pointcut("execution(* com.qs.*.controller.*.permission(..))")
    public void permissionServiceCall() {
    }

    /**
     * 管理员添加操作日志(后置通知)
     *
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    @AfterReturning(value = "insertServiceCall()", argNames = "joinPoint,rtv", returning = "rtv")
    public void insertServiceCallCalls(JoinPoint joinPoint, Object rtv)
            throws Throwable {
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取操作内容
        String opContent = adminOptionContent(joinPoint.getArgs(), methodName);
        // 创建日志对象
        OperationLog operationLog = new OperationLog();
        operationLog.setCreateTime(new Date()).setContent(opContent).setOpreation("insert");
        redisService.lPush("insertServiceCall", IntConst.MONTH, JSON.toJSONString(operationLog));
    }

    /**
     * 管理员修改操作日志(后置通知)
     *
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    @AfterReturning(value = "updateServiceCall()", argNames = "joinPoint,rtv", returning = "rtv")
    public void updateServiceCallCalls(JoinPoint joinPoint, Object rtv)
            throws Throwable {
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取操作内容
        String opContent = adminOptionContent(joinPoint.getArgs(), methodName);
        // 创建日志对象
        OperationLog operationLog = new OperationLog();
        operationLog.setCreateTime(new Date()).setContent(opContent).setOpreation("update");
        redisService.lPush("updateServiceCall", IntConst.MONTH, JSON.toJSONString(operationLog));
    }

    /**
     * 管理员删除操作日志(后置通知)
     *
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    @AfterReturning(value = "deleteServiceCall()", argNames = "joinPoint,rtv", returning = "rtv")
    public void deleteServiceCallCalls(JoinPoint joinPoint, Object rtv)
            throws Throwable {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        StringBuilder rs = new StringBuilder();
        rs.append(methodName);
        String className = null;
        for (Object info : joinPoint.getArgs()) {
            // 获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数1，类型:").append(className).append("，值:(id:").append(joinPoint.getArgs()[0]).append(")");
        }
        // 创建日志对象
        OperationLog operationLog = new OperationLog();
        operationLog.setCreateTime(new Date()).setContent(rs.toString()).setOpreation("delete");
        redisService.lPush("deleteServiceCall", IntConst.MONTH, JSON.toJSONString(operationLog));
    }

    /**
     * 管理员授权操作日志(后置通知)
     *
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    @AfterReturning(value = "permissionServiceCall()", argNames = "joinPoint,rtv", returning = "rtv")
    public void permissionServiceCallCalls(JoinPoint joinPoint, Object rtv)
            throws Throwable {
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取操作内容
        String opContent = adminOptionContent(joinPoint.getArgs(), methodName);
        // 创建日志对象
        OperationLog operationLog = new OperationLog();
        operationLog.setCreateTime(new Date()).setContent(opContent).setOpreation("permission");
        redisService.lPush("permissionServiceCallCalls", IntConst.MONTH, JSON.toJSONString(operationLog));
    }


    /**
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
     */
    public String adminOptionContent(Object[] args, String mName)
            throws Exception {
        if (args == null) {
            return null;
        }
        StringBuilder rs = new StringBuilder();
        rs.append(mName);
        String className = null;
        int index = 1;
        // 遍历参数对象
        for (Object info : args) {
            // 获取对象类型
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数").append(index).append("，类型：").append(className).append("，值：");
            // 获取对象的所有方法
            Method[] methods = info.getClass().getDeclaredMethods();
            // 遍历方法，判断get方法
            for (Method method : methods) {
                String methodName = method.getName();
                // 判断是不是get方法
                if (!methodName.contains("get")) {// 不是get方法
                    continue;// 不处理
                }
                Object rsValue = null;
                try {
                    // 调用get方法，获取返回值
                    rsValue = method.invoke(info);
                    if (rsValue == null) {// 没有返回值
                        continue;
                    }
                } catch (Exception e) {
                    continue;
                }
                // 将值加入内容中
                rs.append("(").append(methodName).append(" : ").append(rsValue).append(")");
            }
            rs.append("]");
            index++;
        }
        return rs.toString();
    }

}
