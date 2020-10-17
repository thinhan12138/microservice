package com.xh.microservice.flow_entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("flow_model")
public class FlowModel implements Serializable {

    private static final long serialVersionId = 1L;

    @TableId("model_id")
    private String modelId;

    @TableField("model_name")
    private String modelName;

    @TableField("model_level")
    private Integer modelLevel;

    @TableField("model_desc")
    private String modelDesc;

    @TableField("definition_key")
    private String definitionKey;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getModelLevel() {
        return modelLevel;
    }

    public void setModelLevel(Integer modelLevel) {
        this.modelLevel = modelLevel;
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    public String getDefinitionKey() {
        return definitionKey;
    }

    public void setDefinitionKey(String definitionKey) {
        this.definitionKey = definitionKey;
    }
}
