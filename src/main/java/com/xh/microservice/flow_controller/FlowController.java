package com.xh.microservice.flow_controller;

import com.xh.microservice.common.result.R;
import com.xh.microservice.flow_entity.FlowModel;
import com.xh.microservice.flow_service.FlowModelService;
import org.flowable.engine.*;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flow")
public class FlowController {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private FlowModelService flowModelService;

    @GetMapping("/modelList")
    public R listFlowModel(){
        final List<FlowModel> flowModelList = flowModelService.list();
        return R.data(flowModelList);
    }

    /**
     * 启动流程
     */
    @PostMapping("/start")
    public R startProcessInstanceByKey(String processDefinitionKey, String businessKey, @RequestBody Map<String, Object> params){
        //设置流程启动用户
        identityService.setAuthenticatedUserId(null);

        //设置流程审批人信息
        List<Map<String, String>> assignees = (List<Map<String, String>>) params.get("assignees");
        if(null != assignees && assignees.size()>0){
            int flag = 1;
            for(Map<String, String> map : assignees){
                final String type = map.get("type");
                if ("用户".equals(type) ){
                    params.put("assignee_1", null);
                    params.put("group_1", null);
                } else {
                    params.put("assignee_1", null);
                    params.put("group_1", null);
                }
                flag ++;
            }
        }

        //开启流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, params);
        return R.data(processInstance);
    }

}
