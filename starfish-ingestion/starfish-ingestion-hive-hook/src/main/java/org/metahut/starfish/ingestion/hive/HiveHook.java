package org.metahut.starfish.ingestion.hive;

import org.metahut.starfish.ingestion.utils.JSONUtils;

import org.apache.hadoop.hive.ql.QueryPlan;
import org.apache.hadoop.hive.ql.hooks.Entity;
import org.apache.hadoop.hive.ql.hooks.ExecuteWithHookContext;
import org.apache.hadoop.hive.ql.hooks.HookContext;
import org.apache.hadoop.hive.ql.hooks.HookContext.HookType;
import org.apache.hadoop.hive.ql.hooks.ReadEntity;
import org.apache.hadoop.hive.ql.hooks.WriteEntity;
import org.apache.hadoop.hive.ql.plan.HiveOperation;
import org.mortbay.log.Log;

import java.util.HashSet;
import java.util.Set;




public class HiveHook implements ExecuteWithHookContext {

    // 存储Hive的SQL操作类型
    private static final HashSet<String> OPERATION_NAMES = new HashSet<>();

    // HiveOperation是一个枚举类，封装了Hive的SQL操作类型
    // 监控SQL操作类型
    static {
        // 建表
        OPERATION_NAMES.add(HiveOperation.CREATETABLE.getOperationName());
    }

    @Override
    public void run(HookContext hookContext) throws Exception {
        assert (hookContext.getHookType() == HookType.POST_EXEC_HOOK);
        // 执行计划
        QueryPlan plan = hookContext.getQueryPlan();
        // 操作名称
        String operationName = plan.getOperationName();
        Log.info("execute Hive SQL: " + plan.getQueryString());
        Log.info("operation name: " + operationName);
        if (OPERATION_NAMES.contains(operationName) && !plan.isExplain()) {
            Log.info("monitor is starting...");

            Set<ReadEntity> inputs = hookContext.getInputs();
            Set<WriteEntity> outputs = hookContext.getOutputs();

            for (Entity entity : inputs) {
                Log.info("Hook metadata input: " + JSONUtils.toJSONString(entity));
            }

            for (Entity entity : outputs) {
                Log.info("Hook metadata output: " + JSONUtils.toJSONString(entity));
            }

        } else {
            Log.info("not exists in monitor's scope ,please ignore this hook!");
        }

    }


}
