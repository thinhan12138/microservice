package com.xh.microservice.flow_service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.microservice.flow_entity.FlowModel;
import com.xh.microservice.flow_mapper.FlowModelMapper;
import com.xh.microservice.flow_service.FlowModelService;
import org.springframework.stereotype.Service;

@Service
public class FlowModelServiceImpl extends ServiceImpl<FlowModelMapper, FlowModel> implements FlowModelService {
}
